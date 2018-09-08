package org.jhipster.lic.service;

import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.FontRepository;
import org.jhipster.lic.repository.LanguageRepository;
import org.jhipster.lic.repository.SettingsRepository;
import org.jhipster.lic.repository.ThemeRepository;
import org.jhipster.lic.service.dto.SettingsDTO;
import org.jhipster.lic.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.jhipster.lic.config.Constants.*;

/**
 * Created by Madalin on 9/8/2018.
 */
@Service
@Transactional
public class SettingsService {
    private final Logger log = LoggerFactory.getLogger(SettingsService.class);

    private final SettingsRepository settingsRepository;

    private final LanguageRepository languageRepository;

    private final ThemeRepository themeRepository;

    private final FontRepository fontRepository;

    public SettingsService(SettingsRepository settingsRepository,
                           LanguageRepository languageRepository,
                           ThemeRepository themeRepository, FontRepository fontRepository){
        this.settingsRepository = settingsRepository;
        this.languageRepository = languageRepository;
        this.themeRepository = themeRepository;
        this.fontRepository = fontRepository;
    }

    public void addSettings(UserDTO userDTO){
        Settings settings = new Settings();
        settings.setId(getNextSettingsId());
        settings.setLanguage(DEFAULT_LANGUAGE_CODE);
        settings.setDateFormat(DEFAULT_DATE_FORMAT);
        settings.setFontType(DEFAULT_FONT_CODE);
        settings.setTheme(DEFAULT_THEME_CODE);
        settings.setUserCode(userDTO.getUserCode());
        settingsRepository.save(settings);
    }

    private Long getNextSettingsId(){
        List<Settings> allBrList = this.settingsRepository.findAll();
        Settings settings = null;
        Optional<Settings> settingsStream = allBrList.stream()
            .sorted(Comparator.comparing(Settings::getId).reversed()).findFirst();


        if(settingsStream != null && settingsStream.isPresent()){
            settings = settingsStream.get();
        }

        return (settings!=null && settings.getId()!=null ? Math.max(Long.parseLong(settings.getId() + ""), 100000) : 100000)+1;
    }
}
