package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Madalin on 9/3/2018.
 */
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Theme findByThemeCode(String themeCode);

    List<Theme> findAll();
}
