package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Font;
import org.jhipster.lic.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Madalin on 9/3/2018.
 */
public interface FontRepository extends JpaRepository<Font, Long> {
    Font findByFontCode(String fontCode);

    List<Font> findAll();
}
