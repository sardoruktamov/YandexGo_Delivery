package api.yandexgo.app.entity;

import api.yandexgo.app.enums.GeneralStatus;
import api.yandexgo.app.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(nullable = false, name = "username",unique = true)
    private String username;
    @Column(name = "temp_username")
    private String tempUsername;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;  // ACTIVE, BLOCK

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileRole role = ProfileRole.ROLE_CLIENT; // Dastlab CLIENT bo'ladi

    @Column(name = "visible",nullable = false)
    private Boolean visible = Boolean.TRUE;   // profil o'chirilgand FALSE bo'ladiauth.http

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private Boolean driverRequest = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTempUsername() {
        return tempUsername;
    }

    public void setTempUsername(String tempUsername) {
        this.tempUsername = tempUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GeneralStatus getStatus() {
        return status;
    }

    public void setStatus(GeneralStatus status) {
        this.status = status;
    }

    public ProfileRole getRole() {
        return role;
    }

    public void setRole(ProfileRole role) {
        this.role = role;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDriverRequest() {
        return driverRequest;
    }

    public void setDriverRequest(Boolean driverRequest) {
        this.driverRequest = driverRequest;
    }
}
