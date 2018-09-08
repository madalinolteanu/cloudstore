package org.jhipster.lic.config;

import org.jhipster.lic.domain.Font;
import org.jhipster.lic.domain.Language;
import org.jhipster.lic.domain.Theme;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String SERVER_PATH = "localhost:8080";
    public static final String UPLOAD_PATH = "/uploads";

    /*SETTINGS DEFAULT*/

    public static final String DEFAULT_LANGUAGE_CODE = "EN";
    public static final String DEFAULT_THEME_CODE = "LIGHT";
    public static final String DEFAULT_DATE_FORMAT = "DD/MM/YYYY";
    public static final String DEFAULT_FONT_CODE = "Arial";

    private Constants() {
    }
}
