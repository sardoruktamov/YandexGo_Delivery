package api.yandexgo.app.service.Mapper;

import api.yandexgo.app.dto.DeliveryOrder.DeliveryOrderResponseDTO;
import api.yandexgo.app.entity.DeliveryOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderMapper {

    public DeliveryOrderResponseDTO toResponseDTO(DeliveryOrderEntity order) {
        DeliveryOrderResponseDTO dto = new DeliveryOrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setPickupAddress(order.getPickupAddress());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setDistanceKm(order.getDistanceKm());
        dto.setPrice(order.getPrice());
        dto.setStatus(order.getStatus().name());
        dto.setCreatedDate(order.getCreatedDate());
        dto.setDeliveryDate(order.getDeliveryDate());
        return dto;
    }
}
