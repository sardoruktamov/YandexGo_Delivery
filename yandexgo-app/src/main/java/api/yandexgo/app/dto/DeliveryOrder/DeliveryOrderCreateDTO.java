package api.yandexgo.app.dto.DeliveryOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderCreateDTO {

    @NotBlank(message = "Pickup Address required")
    private String pickupAddress;       // Qayerdan olish kerak
    @NotBlank(message = "Delivery Address required")
    private String deliveryAddress;     // Qayerga yetkazish kerak
    private Double distanceKm;          // Masofa km da
    @NotBlank(message = "WeightKg required")
    private Double weightKg;            // Yuk vazni (optional, og‘ir yuk uchun)
    @NotBlank(message = "Description required")
    private String description;         // Yuk haqida qo‘shimcha izoh (optional)
    @NotBlank(message = "Receiver Name required")
    private String receiverName;        // Yukni oluvchi odam ismi (optional)
    @NotBlank(message = "Receiver Phone required")
    private String receiverPhone;       // Yukni oluvchi telefon raqami (optional)
}
