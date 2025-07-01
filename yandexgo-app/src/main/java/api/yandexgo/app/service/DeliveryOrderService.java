package api.yandexgo.app.service;

import api.yandexgo.app.config.CustomUserDetails;
import api.yandexgo.app.dto.DeliveryOrder.DeliveryOrderCreateDTO;
import api.yandexgo.app.entity.DeliveryOrderEntity;
import api.yandexgo.app.entity.DeliveryPackageEntity;
import api.yandexgo.app.enums.OrderStatus;
import api.yandexgo.app.enums.ProfileRole;
import api.yandexgo.app.exceptions.AppBadException;
import api.yandexgo.app.repository.DeliveryOrderRepository;
import api.yandexgo.app.util.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DeliveryOrderService {
    private final DeliveryOrderRepository deliveryOrderRepository;


    @Transactional
    public DeliveryOrderEntity createOrder(DeliveryOrderCreateDTO dto) {
        Integer currentUserId = SpringSecurityUtil.getCurrentUserId(); // JWT dan userId olindi

        DeliveryOrderEntity order = new DeliveryOrderEntity();
        order.setOrderNumber(generateOrderNumber());
        order.setPickupAddress(dto.getPickupAddress());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setDistanceKm(dto.getDistanceKm());
        order.setStatus(OrderStatus.NEW);
        order.setCreatedDate(LocalDateTime.now());
        order.setClientId(currentUserId);

        BigDecimal price = calculatePrice(dto.getDistanceKm(), dto.getWeightKg());
        order.setPrice(price);

        // Agar yuk ma'lumotlari kiritilgan bo'lsa DeliveryPackage qo'shish
        if (dto.getWeightKg() != null || dto.getDescription() != null) {
            DeliveryPackageEntity deliveryPackage = DeliveryPackageEntity.builder()
                    .weight(dto.getWeightKg())
                    .description(dto.getDescription())
                    .build();
            order.setDeliveryPackage(deliveryPackage);
        }

        return deliveryOrderRepository.save(order);
    }

    @Transactional
    public DeliveryOrderEntity driverAcceptOrder(Long orderId) {
        DeliveryOrderEntity order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(() -> new AppBadException("Order not found"));

        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentProfile();

        if (!currentUser.getAuthorities().contains(ProfileRole.ROLE_DRIVER)) {
            throw new AppBadException("Only drivers can accept orders");
        }

        if (order.getStatus() != OrderStatus.NEW) {
            throw new AppBadException("Order is not available for acceptance");
        }

        order.setDriverId(currentUser.getId());
        order.setStatus(OrderStatus.IN_PROGRESS);

        return deliveryOrderRepository.save(order);
    }

    @Transactional
    public DeliveryOrderEntity completeOrder(Long orderId) {
        DeliveryOrderEntity order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(() -> new AppBadException("Order not found"));

        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentProfile();

        if (!currentUser.getAuthorities().contains(ProfileRole.ROLE_DRIVER)) {
            throw new AppBadException("Only drivers can complete orders");
        }

        if (!currentUser.getId().equals(order.getDriverId())) {
            throw new AppBadException("You are not assigned to this order");
        }

        if (order.getStatus() != OrderStatus.IN_PROGRESS) {
            throw new AppBadException("Order cannot be completed in current status");
        }

        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now());

        return deliveryOrderRepository.save(order);
    }


    public BigDecimal calculatePrice(Double distanceKm, Double weightKg) {
        BigDecimal basePrice = BigDecimal.valueOf(10000); // 10_000 so'm
        BigDecimal perKmPrice = BigDecimal.valueOf(3000); // 3_000 so'm per km
        BigDecimal maxWeightPrice = BigDecimal.valueOf(5000); // 5_000 so'm

        BigDecimal totalPrice = basePrice;

        if (distanceKm > 2) {
            double additionalKm = distanceKm - 2;
            BigDecimal additionalPrice = perKmPrice.multiply(BigDecimal.valueOf(additionalKm));
            totalPrice = totalPrice.add(additionalPrice);
        }

        if (weightKg != null && weightKg > 20) {
            totalPrice = totalPrice.add(maxWeightPrice);
        }

        return totalPrice;
    }

    @Transactional(readOnly = true)
    public DeliveryOrderEntity getOrder(Long id) {
        DeliveryOrderEntity order = deliveryOrderRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Order not found"));

        CustomUserDetails currentUser = SpringSecurityUtil.getCurrentProfile();

        if (currentUser.getAuthorities().contains(ProfileRole.ROLE_ADMIN) || currentUser.getAuthorities().contains(ProfileRole.ROLE_SUPERADMIN)) {
            return order; // Adminlar barcha buyurtmalarni ko'radi
        }

        if (currentUser.getAuthorities().contains(ProfileRole.ROLE_CLIENT)) {
            if (!order.getClientId().equals(currentUser.getId())) {
                throw new AppBadException("Access denied: Not your order");
            }
            return order;
        }

        if (currentUser.getAuthorities().contains(ProfileRole.ROLE_DRIVER)) {
            if (order.getStatus() == OrderStatus.NEW ||
                    (order.getDriverId() != null && order.getDriverId().equals(currentUser.getId()))) {
                return order;
            }
            throw new AppBadException("Access denied: Driver not assigned");
        }

        throw new AppBadException("Access denied: Invalid role");
    }


    /**
     * Har bir yangi buyurtma uchun unikal orderNumber generatsiya qiladi.
     */
    public String generateOrderNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 20250624

        // Shu kunda yaratilgan buyurtmalar sonini aniqlash
        Long count = deliveryOrderRepository.countByCreatedDateBetween(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().plusDays(1).atStartOfDay()
        );

        String sequence = String.format("%04d", count + 1); // 0001, 0002, ...

        return "ORD-" + datePart + "-" + sequence; // ORD-20250624-0001, ORD-20250624-0002, ...
    }
}
