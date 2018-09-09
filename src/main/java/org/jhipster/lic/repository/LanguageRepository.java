package org.jhipster.lic.repository;

import org.jhipster.lic.domain.File;
import org.jhipster.lic.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Madalin on 9/3/2018.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findByLanguageCode(String languageCode);

    List<Language> findAll();
}
