package api.yandexgo.app.service;

import api.yandexgo.app.entity.ProfileEntity;
import api.yandexgo.app.exceptions.AppBadException;
import api.yandexgo.app.repository.ProfileRepository;
import api.yandexgo.app.repository.ProfileRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private EmailSendingService emailSendingService;
//    @Autowired
//    ResourceBundleService bundleService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private SmsSendService smsSendService;
//
//    @Autowired
//    private SmsHistoryService smsHistoryService;
//    @Autowired
//    private EmailHistoryService emailHistoryService;
//
//    @Autowired
//    private AttachService attachService;
//
//    public AppResponse<String> updateDetail(ProfileDetailUpdateDTO dto, AppLanguage lang){
//        Integer userId = SpringSecurityUtil.getCurrentUserId();
//        profileRepository.updateDetail(userId, dto.getName());
//        return new AppResponse<>(bundleService.getMessage("profile.update.detail.success", lang));
//    }
//
//    public AppResponse<String> updatePassword(ProfilePasswordUpdateDTO dto, AppLanguage lang) {
//        Integer userId = SpringSecurityUtil.getCurrentUserId();
//        ProfileEntity profile = getById(userId, lang);
//        if (!bCryptPasswordEncoder.matches(dto.getCurrentPswd(), profile.getPassword())){
//            throw new AppBadException(bundleService.getMessage("current.password.incorrectly",lang));
//        }
//
//        profileRepository.updatePassword(userId, bCryptPasswordEncoder.encode(dto.getNewPswd()));
//        return new AppResponse<String>(bundleService.getMessage("password.changed.successfully",lang));
//    }
//
//    public AppResponse<String> updateUsername(ProfileUsernameUpdateDTO dto, AppLanguage lang){
//        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
//        // check
//        if (optional.isPresent()){
//            throw new AppBadException(bundleService.getMessage("email.phone.exist", lang));
//        }
//
//        // send sms
//        if (EmailUtil.isEmail(dto.getUsername())){
//            // send email
//            emailSendingService.sendChangeUsernameEmail(dto.getUsername(), lang);
//        }else if (PhoneUtil.isPhone(dto.getUsername())){
//            // send SMS
//            smsSendService.sendUsernameChangeConfirmSms(dto.getUsername(), lang);
//        }
//        Integer userId = SpringSecurityUtil.getCurrentUserId();
//        profileRepository.updateTempUsername(userId, dto.getUsername());
//        String response = bundleService.getMessage("resent.password.code.sent", lang);
//        return new AppResponse<>(String.format(response,dto.getUsername()));
//    }
//
//    public AppResponse<String> updateUsernameConfirm(CodeConfirmDTO dto, AppLanguage lang) {
//        Integer userId = SpringSecurityUtil.getCurrentUserId();
//        ProfileEntity profile = getById(userId, lang);
//        String tempUsername = profile.getTempUsername();
//        // check
//        if (EmailUtil.isEmail(tempUsername)){
//            // send email
//            emailHistoryService.check(tempUsername, dto.getCode(), lang);
//        }else if (PhoneUtil.isPhone(tempUsername)){
//            // send SMS
//            smsHistoryService.check(tempUsername, dto.getCode(), lang);
//        }
//        // update username
//        profileRepository.updateUsername(userId,tempUsername);
//        // response
//        List<ProfileRole> roles = profileRoleRepository.getAllRolesListByProfileId(profile.getId());
//        String jwt = JwtUtil.encode(tempUsername,profile.getId(),roles);
//
//        return new AppResponse<>(jwt, bundleService.getMessage("change.username.succes", lang));
//    }
//
//    public AppResponse<String> updatePhoto(String photoId, AppLanguage lang) {
//        Integer userId = SpringSecurityUtil.getCurrentUserId();
//        ProfileEntity profile = getById(userId,lang);
//        profileRepository.updatePhoto(userId,photoId);
//        if (profile.getPhotoId() != null && !profile.getPhotoId().equals(photoId)){
//            attachService.delete(profile.getPhotoId()); // delete old img
//        }
//        return new AppResponse<>(bundleService.getMessage("change.photo.succes", lang));
//    }



    public ProfileEntity getById(int id){
        // 1-usul
//        Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
//        if(optional.isEmpty()){
//            throw new AppBadException("Profile not found");
//        }
//        return optional.get();
        // 2-usul
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow( () -> {
            throw new AppBadException("profile.not.found");
        });
    }



}
