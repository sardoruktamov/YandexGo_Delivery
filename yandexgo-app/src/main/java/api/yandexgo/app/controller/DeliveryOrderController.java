package api.yandexgo.app.controller;

import api.yandexgo.app.dto.DeliveryOrder.DeliveryOrderCreateDTO;
import api.yandexgo.app.dto.DeliveryOrder.DeliveryOrderResponseDTO;
import api.yandexgo.app.dto.DriverAcceptOrderDTO;
import api.yandexgo.app.entity.DeliveryOrderEntity;
import api.yandexgo.app.service.DeliveryOrderService;
import api.yandexgo.app.service.Mapper.DeliveryOrderMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/delivery-order")
@Slf4j
public class DeliveryOrderController {

    @Autowired
    private DeliveryOrderService deliveryOrderService;
    @Autowired
    private DeliveryOrderMapper deliveryOrderMapper;


    @PostMapping("/create")
    public ResponseEntity<DeliveryOrderResponseDTO> createOrder(@RequestBody @Valid DeliveryOrderCreateDTO dto) {


        DeliveryOrderEntity order = deliveryOrderService.createOrder(dto);
        DeliveryOrderResponseDTO responseDTO = deliveryOrderMapper.toResponseDTO(order);


        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOrderResponseDTO> getOrder(@PathVariable Long id) {
        DeliveryOrderEntity order = deliveryOrderService.getOrder(id);
        DeliveryOrderResponseDTO dto = deliveryOrderMapper.toResponseDTO(order);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/accept")
    public ResponseEntity<DeliveryOrderResponseDTO> acceptOrder(@RequestBody DriverAcceptOrderDTO dto) {

        DeliveryOrderEntity order = deliveryOrderService.driverAcceptOrder(dto.getOrderId());
        DeliveryOrderResponseDTO responseDTO = deliveryOrderMapper.toResponseDTO(order);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/complete/{id}")
    public ResponseEntity<DeliveryOrderResponseDTO> completeOrder(@PathVariable Long id) {
        DeliveryOrderEntity order = deliveryOrderService.completeOrder(id);
        DeliveryOrderResponseDTO dto = deliveryOrderMapper.toResponseDTO(order);
        return ResponseEntity.ok(dto);
    }
}
