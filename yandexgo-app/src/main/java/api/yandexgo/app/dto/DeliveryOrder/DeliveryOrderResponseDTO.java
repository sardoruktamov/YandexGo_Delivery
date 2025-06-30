package api.yandexgo.app.dto.DeliveryOrder;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryOrderResponseDTO {

    private Long id;

    private String orderNumber;

    private String pickupAddress;

    private String deliveryAddress;

    private Double distanceKm;

    private BigDecimal price;

    private String status;

    private LocalDateTime createdDate;

    private LocalDateTime deliveryDate;
}
