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
}
