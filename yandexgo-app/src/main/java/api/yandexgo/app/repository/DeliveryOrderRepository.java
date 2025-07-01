package api.yandexgo.app.repository;

import api.yandexgo.app.entity.DeliveryOrderEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface DeliveryOrderRepository extends CrudRepository<DeliveryOrderEntity,Long> {

    Long countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}
