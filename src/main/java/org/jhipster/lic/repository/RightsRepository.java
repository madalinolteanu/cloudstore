package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RightsRepository extends JpaRepository<Right, Long> {

    Right findById(Integer id);

    List<Right> findAll();
}
