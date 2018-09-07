package org.jhipster.lic.repository;

import org.jhipster.lic.domain.File;
import org.jhipster.lic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the File entity.
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    File findByUserCode(String userCode);

    List<File> findAllByDirectoryID(Long id);

    List<File> findAllByUserCodeAndDirectoryID(String userCode, Long directoryId);
}
