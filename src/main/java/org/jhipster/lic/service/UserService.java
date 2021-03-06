package org.jhipster.lic.service;

import afu.org.checkerframework.checker.igj.qual.I;
import org.jhipster.lic.domain.Settings;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.UserRepository;
import org.jhipster.lic.security.jwt.TokenProvider;
import org.jhipster.lic.service.dto.SettingsDTO;
import org.jhipster.lic.service.util.RandomUtil;
import org.jhipster.lic.service.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.jhipster.lic.web.rest.errors.InvalidPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.FailedLoginException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final CacheManager cacheManager;

    private final SettingsService settingsService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CacheManager cacheManager,
                       TokenProvider tokenProvider, SettingsService settingsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
        this.tokenProvider = tokenProvider;
        this.settingsService = settingsService;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
//        return userRepository.findOneByActivationKey(key)
//            .map(user -> {
//                // activate given user for the registration key.
//                user.setActive(true);
//                user.setActivationKey(null);
//                this.clearUserCaches(user);
//                log.debug("Activated user: {}", user);
//                return user;
//            });
        return  null;
    }

    public UserDTO getByUsernameAndPassword(UserDTO requestedUser) throws FailedLoginException {
        UserDTO responseUser;
        String currentClearPassword = requestedUser.getPassword();
        User user;
        try {
            user = userRepository.findOneByUsername(requestedUser.getUsername());
            String token;
            if(passwordEncoder.matches(currentClearPassword, user.getPassword())){
                user = userRepository.findOneByUsernameAndPassword(requestedUser.getUsername(), user.getPassword());
                responseUser = new UserDTO(user);
                token = tokenProvider.createUserToken(responseUser.getUserCode(), true, responseUser.getUserType());
                user.setToken(token);
                userRepository.save(user);
                responseUser.setToken(token);
                return responseUser;
            } else {
                throw new FailedLoginException();
            }
        } catch (Exception e) {
            throw new FailedLoginException();
        }
    }

    public UserDTO findOneByUserCode(byte[] userCode){

        User user = userRepository.findOneByUserCode(bytesToHex(userCode) + "");
        if(user != null)
            return new UserDTO(user);
        else
            return null;
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return null;
    }

    public Optional<UserDTO> requestPasswordReset(String mail) {
        return null;
    }

    public String createUser(UserDTO userDTO, byte[] encodedhash) {
        User user = new User();
        UserDTO responseUser;
        user.setId(Long.parseLong(getNextUserId() + ""));
        user.setUserCode(bytesToHex(encodedhash));
        user.setUserType(userDTO.getUserType());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if(userRepository.findOneByEmail(userDTO.getEmail()) != null){
            if(userRepository.findOneByEmail(userDTO.getEmail()).getUserCode().equals(user.getUserCode()))
                return "502";
        }
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl() + "/" + user.getUserCode() + "/" + user.getUserCode());
        if(userRepository.findOneByUsername(userDTO.getUsername()) != null) {
            if(userRepository.findOneByUsername(userDTO.getUsername()).getUserCode().equals(user.getUserCode()))
                return "501";
        }
        user.setUsername(userDTO.getUsername());
        user.setActivationKey("12345");
        user.setCreationDate(Instant.now());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setActive(true);
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);

        responseUser = new UserDTO(user);
        settingsService.addSettings(responseUser);
        return user.getUserCode();
    }

    public UserDTO getUserByToken(String token){
        User user = userRepository.findOneByToken(token);
        UserDTO responseUser;
        if(user != null){
            responseUser = new UserDTO(user);
            SettingsDTO settingsDTO = settingsService.getSettingsByUserCode(responseUser);
            responseUser.setSettings(settingsDTO);
            return responseUser;
        }
        return null;
    }

    public UserDTO removeUserToken(String token) {

        User user = userRepository.findOneByToken(token);
        user.setToken(null);
        return new UserDTO(userRepository.save(user));
    }

    public String getUserCodeByToken(String token) {
        return userRepository.findOneByToken(token).getUserCode();
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Integer updateUser(UserDTO userDTO){
        User user = userRepository.findOneByUserCode(userDTO.getUserCode());
        if(userDTO.getUserType() != null)
            user.setUserType(userDTO.getUserType());
        if(userDTO.getFirstName() != null)
            user.setFirstName(userDTO.getFirstName());
        if(userDTO.getLastName() != null)
            user.setLastName(userDTO.getLastName());
        if(!userRepository.findOneByEmail(userDTO.getEmail()).getUserCode().equals(userDTO.getUserCode())){
            return 501;
        } else {
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getOldPassword() != null){
            if(passwordEncoder.matches(userDTO.getOldPassword(),
                userRepository.findOneByUserCode(userDTO.getUserCode()).getPassword()))
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            else {
                return 502;
            }
        }
        userRepository.save(user);
        settingsService.updateSettingByUserCode(userDTO.getSettings(), userDTO);
        return null;
    }

    public UserDTO getUserByUserCode(String userCode){
        return new UserDTO(userRepository.findOneByUserCode(userCode));
    }

    public void deleteUser(String userCode) {
//        userRepository.findOneByUserCode(userCode).ifPresent(user -> {
//            userRepository.delete(user);
//            this.clearUserCaches(user);
//            log.debug("Deleted User: {}", user);
//        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
//        SecurityUtils.getCurrentUserLogin()
//            .flatMap(userRepository::findOneByUserCode)
//            .ifPresent(user -> {
//                String currentEncryptedPassword = user.getPassword();
//                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
//                    throw new InvalidPasswordException();
//                }
//                String encryptedPassword = passwordEncoder.encode(newPassword);
//                user.setPassword(encryptedPassword);
//                this.clearUserCaches(user);
//                log.debug("Changed password for User: {}", user);
//            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return null;
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
//        List<User> users = userRepository.findAllByActiveIsFalseAndCreationDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
//        for (User user : users) {
//            log.debug("Deleting not activated user {}", user.getUserCode());
//            userRepository.delete(user);
//            this.clearUserCaches(user);
//        }
    }


    private void clearUserCaches(User user) {
//        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getUserCode());
//        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

    private Integer getNextUserId(){
        List<User> allBrList = this.userRepository.findAll();
        User user = null;
        Optional<User> userStream = allBrList.stream()
            .sorted(Comparator.comparing(User::getId).reversed()).findFirst();


        if(userStream != null && userStream.isPresent()){
            user = userStream.get();
        }

        return (user!=null && user.getId()!=null ? Math.max(Integer.parseInt(user.getId() + ""), 100000) : 100000)+1;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
