package org.jhipster.lic.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.lic.domain.Language;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.FontRepository;
import org.jhipster.lic.repository.LanguageRepository;
import org.jhipster.lic.repository.SettingsRepository;
import org.jhipster.lic.service.MailService;
import org.jhipster.lic.service.UserService;
import org.jhipster.lic.web.rest.errors.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by Madalin on 9/3/2018.
 */
@RestController
@RequestMapping("/api/cloudstore/settings")
public class SettingsController {

    private SettingsRepository settingsRepository;

    private FontRepository fontRepository;

    private LanguageRepository languageRepository;

    public SettingsController(SettingsRepository settingsRepository,
                              FontRepository fontRepository, LanguageRepository languageRepository) {
        this.settingsRepository = settingsRepository;
        this.fontRepository = fontRepository;
        this.languageRepository = languageRepository;
    }

    @GetMapping("/languages")
    @Timed
    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

}
