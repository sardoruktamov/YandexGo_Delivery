package api.yandexgo.app.config;


import api.yandexgo.app.entity.ProfileEntity;
import api.yandexgo.app.enums.ProfileRole;
import api.yandexgo.app.repository.ProfileRepository;
import api.yandexgo.app.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// bu class orqali Userlarni qaysi DB orqali olib kelishini belgilab qoyamiz
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(username);
        if (optional.isEmpty()){
            throw new UsernameNotFoundException("Username not found~");
        }
        ProfileEntity profile = optional.get();
        List<ProfileRole> roleList = profileRoleRepository.getAllRolesListByProfileId(profile.getId());
        return new CustomUserDetails(profile, roleList);
    }
}
