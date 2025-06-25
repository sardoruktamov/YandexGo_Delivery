package api.yandexgo.app.entity;

import api.yandexgo.app.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false,updatable = false)
    private ProfileEntity profile;

    @Column(name = "profile_id")
    private Integer profileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private ProfileRole roles;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public ProfileRole getRoles() {
        return roles;
    }

    public void setRoles(ProfileRole roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
