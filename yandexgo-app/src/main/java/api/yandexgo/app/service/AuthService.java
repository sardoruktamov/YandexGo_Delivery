package api.yandexgo.app.service;

import api.yandexgo.app.dto.RegistrationDTO;
import api.yandexgo.app.entity.ProfileEntity;
import api.yandexgo.app.enums.GeneralStatus;
import api.yandexgo.app.enums.ProfileRole;
import api.yandexgo.app.exceptions.AppBadException;
import api.yandexgo.app.repository.ProfileRepository;
import api.yandexgo.app.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registration(RegistrationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()){
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                profileRoleService.deleteRoles(profile.getId());
                // 1-usul
                profileRepository.delete(profile);
                // 2-usul
                //send sms/email orqali ro'yxatdan o'tishini davom ettirish
            }else {
                throw new AppBadException("email phone exist");
            }
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        // Insert Role
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_CLIENT);
//
//        // Usernameni tekshirish email yoki phone ekanligiga >>>PASTDA 2-USUL<<<<<
//        if (EmailUtil.isEmail(dto.getUsername())){
//            // send email
            emailSendingService.sendEmailForRegistration(dto.getUsername(), entity.getId());
//        }else if (PhoneUtil.isPhone(dto.getUsername())){
//            // send SMS
//            smsSendService.sendRegistrationSms(dto.getUsername(), lang);
//        }
        System.out.println("AUTH SETVICE-" + entity.getUsername() + " id - " + entity.getId());
        return "tasdiqlash uchun emailga kod yuborildi"; //new AppResponse<>(bundleService.getMessage("email.confirm.send",lang));
    }

    public String registrationEmailVerification(String token) {

        try{
            Integer profileId = JwtUtil.decodeVer(token);
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                // 1-usulda barcha fieldlarini update qiladi
//            profile.setStatus(GeneralStatus.ACTIVE);
//            profileRepository.save(profile);
                // 2-usulda faqat status update bo`ladi
                profileRepository.changeStatus(profileId,GeneralStatus.ACTIVE);
                return "success verification!!!";
            }
        }catch (JwtException e){
            System.out.println("JwtException xatooooooooooooooooooooo");
        }
        throw new AppBadException("verification.failed");
    }
}
