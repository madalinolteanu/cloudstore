package org.jhipster.lic.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.lic.domain.Font;
import org.jhipster.lic.domain.Language;
import org.jhipster.lic.domain.Theme;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.FontRepository;
import org.jhipster.lic.repository.LanguageRepository;
import org.jhipster.lic.repository.SettingsRepository;
import org.jhipster.lic.repository.ThemeRepository;
import org.jhipster.lic.service.MailService;
import org.jhipster.lic.service.SettingsService;
import org.jhipster.lic.service.UserService;
import org.jhipster.lic.service.dto.CloudStoreDTO;
import org.jhipster.lic.service.dto.SettingsDTO;
import org.jhipster.lic.service.dto.UserDTO;
import org.jhipster.lic.web.rest.errors.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Madalin on 9/3/2018.
 */
@RestController
@RequestMapping("/api/cloudstore/settings")
public class SettingsController {

    private final SettingsService settingsService;

    private final FontRepository fontRepository;

    private final LanguageRepository languageRepository;

    private final ThemeRepository themeRepository;

    private final UserService userService;

    public SettingsController(SettingsService settingsService, ThemeRepository themeRepository,
                              FontRepository fontRepository, LanguageRepository languageRepository,
                                UserService userService) {
        this.settingsService = settingsService;
        this.fontRepository = fontRepository;
        this.languageRepository = languageRepository;
        this.themeRepository = themeRepository;
        this.userService = userService;
    }

    @GetMapping("/languages")
    @Timed
    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    @GetMapping("/themes")
    @Timed
    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }

    @GetMapping("/fonts")
    @Timed
    public List<Font> getFonts() {
        return fontRepository.findAll();
    }

    @GetMapping("/find")
    @Timed
    public SettingsDTO getSettings(@RequestParam(value = "token") String token) {
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            return settingsService.getSettingsByUserCode(userDTO);
        }
        return null;
    }

    @PostMapping("/update")
    @Timed
    public CloudStoreDTO updateSettings(@RequestParam(value = "token") String token,
                        @Valid @RequestBody SettingsDTO settingsDTO) {
        CloudStoreDTO response = new CloudStoreDTO();
        UserDTO userDTO = userService.getUserByToken(token);
        if(userDTO != null){
            settingsService.updateSettingByUserCode(settingsDTO, userDTO);
            response.setSuccessCode(200);
            response.setSuccessMessage("Settings Successfully Updated!");
        }
        return null;
    }
}
