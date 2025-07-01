package api.yandexgo.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "delivery_package")
public class DeliveryPackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double weight; // kg yoki tonnada saqlang

    private String description; // mahsulot haqida ma'lumot

    @OneToOne(mappedBy = "deliveryPackage")
    private DeliveryOrderEntity deliveryOrder; // 1:1 teskari bog'lanish
}
