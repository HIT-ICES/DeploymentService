package com.hitices.deployment.repository;

import com.hitices.deployment.entity.SchemeEntity;
import org.hibernate.annotations.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

/**
 * @author wangteng
 * @email willtynn@outlook.com
 * @date 2024/3/11 18:24
 */
@Repository
public interface SchemeRepository extends JpaRepository<SchemeEntity, InternalError> {
    List<SchemeEntity> findAllByOrderByStatusAsc();

    List<SchemeEntity> findAllByNamespaceOrderByStatusAsc(String namespace);

    List<SchemeEntity> findAllByNameContainingIgnoreCaseOrderByStatusAsc(String name);

    List<SchemeEntity> findAllByNameContainingIgnoreCaseAndNamespaceOrderByStatusAsc(String name, String namespace);
}
