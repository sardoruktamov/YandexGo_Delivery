package api.yandexgo.app.service;

import api.yandexgo.app.entity.ProfileRoleEntity;
import api.yandexgo.app.enums.ProfileRole;
import api.yandexgo.app.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void create(Integer profileId, ProfileRole role){
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRoles(role);
        entity.setCreatedDate(LocalDateTime.now());
        profileRoleRepository.save(entity);
    }

    public void deleteRoles(Integer profileRoleId) {
        profileRoleRepository.deleteByProfileId(profileRoleId);
    }
}
