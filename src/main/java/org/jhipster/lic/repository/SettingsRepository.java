package org.jhipster.lic.repository;

import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Madalin on 9/3/2018.
 */
public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings findByUserCode(String userCode);

    Settings findById(Integer settingId);
}
