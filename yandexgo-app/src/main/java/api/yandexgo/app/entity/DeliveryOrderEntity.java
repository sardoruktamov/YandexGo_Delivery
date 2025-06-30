package api.yandexgo.app.entity;

import api.yandexgo.app.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "delivery_order")
public class DeliveryOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String pickupAddress;

    private String deliveryAddress;

    private Double distanceKm;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private BigDecimal price;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime deliveryDate;
    /**
     * Bu buyurtmani kim (qaysi client) yaratganini saqlaydi.
     * ProfileEntity bilan FK ishlatishingiz mumkin, lekin oddiy id saqlash sodda bo'ladi.
     */
    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    /**
     * Buyurtmani qaysi driver qabul qilganini saqlaydi.
     * Driver hali qabul qilmagan bo'lsa NULL bo'ladi.
     */
    @Column(name = "driver_id")
    private Integer driverId;

    /**
     * Buyurtmaga biriktirilgan DeliveryPackage bilan 1:1 bog'lanish
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_package_id")
    private DeliveryPackageEntity deliveryPackage; // 1:1 aloqasi
}
