package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Directory entity.
 */
@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    List<Directory> findAllByUserCode(String userCode);

    Directory findByIdAndUserCode(Long id, String userCode);

    List<Directory> findAllByUserCodeAndDirectoryParent(String userCode, Long parentId);

    List<Directory> findByDirectoryParentAndUserCode(Long parent, String userCode);

    boolean deleteByUserCodeAndId(String userCode, Long id);
}
