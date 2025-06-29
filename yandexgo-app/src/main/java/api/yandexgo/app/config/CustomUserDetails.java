package api.yandexgo.app.config;

import api.yandexgo.app.entity.ProfileEntity;
import api.yandexgo.app.enums.GeneralStatus;
import api.yandexgo.app.enums.ProfileRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {


    private Integer id;
    private String name;
    private String username;
    private String password;
    private GeneralStatus status;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(ProfileEntity profile,
                             List<ProfileRole> roleList) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.username = profile.getUsername();
        this.password = profile.getPassword();
        this.status = profile.getStatus();
        // 1-usul
        /*List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for(ProfileRole role: roleList){
            roles.add(new SimpleGrantedAuthority(role.name()));
        }
        this.authorities = roles;*/
        //2-usul stream orqali
        this.authorities = roleList.stream()
                .map(
                        item -> new SimpleGrantedAuthority(item.name())
                ).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralStatus.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // true bo'lgani sababi avval tekshirganmiz (VisibleTrue) -> profileRepository.findByUsernameAndVisibleTrue(username)
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
