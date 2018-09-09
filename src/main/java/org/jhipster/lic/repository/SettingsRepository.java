package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Madalin on 9/3/2018.
 */
@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings findByUserCode(String userCode);
}
