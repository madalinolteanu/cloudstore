package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Right;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RightsRepository extends JpaRepository<Right, Long> {

    Right findById(Integer id);

    List<Right> findAll();
}
