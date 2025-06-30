package api.yandexgo.app.entity;

import api.yandexgo.app.enums.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "driver")
public class DriverEntity {

    @Id
    private Integer userId; // ProfileEntity bilan 1:1

    @Enumerated(EnumType.STRING)
    private DriverStatus driverStatus = DriverStatus.FREE;

    private Double latitude;
    private Double longitude;

    private String carModel;
    private String carNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private ProfileEntity user;
}
