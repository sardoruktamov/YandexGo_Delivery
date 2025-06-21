package api.yandexgo.app.repository;

import api.yandexgo.app.entity.ProfileRoleEntity;
import api.yandexgo.app.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity,Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer integer);

    @Query("select p.roles from ProfileRoleEntity p where p.profileId = ?1")
    List<ProfileRole> getAllRolesListByProfileId(Integer profileId);
}
