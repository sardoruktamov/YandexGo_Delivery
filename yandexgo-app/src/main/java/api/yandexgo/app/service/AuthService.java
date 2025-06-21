package api.yandexgo.app.service;

import api.yandexgo.app.dto.RegistrationDTO;
import api.yandexgo.app.entity.ProfileEntity;
import api.yandexgo.app.enums.GeneralStatus;
import api.yandexgo.app.exceptions.AppBadException;
import api.yandexgo.app.repository.ProfileRepository;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String registration(RegistrationDTO dto){

        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()){
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
//                profileRoleService.deleteRoles(profile.getId());  //TODOOOO
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
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        // Insert Role
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);

        // Usernameni tekshirish email yoki phone ekanligiga >>>PASTDA 2-USUL<<<<<
        if (EmailUtil.isEmail(dto.getUsername())){
            // send email
            emailSendingService.sendEmailForRegistration(dto.getUsername(), entity.getId(), lang);
        }else if (PhoneUtil.isPhone(dto.getUsername())){
            // send SMS
            smsSendService.sendRegistrationSms(dto.getUsername(), lang);
        }

        return new AppResponse<>(bundleService.getMessage("email.confirm.send",lang));
    }
}
