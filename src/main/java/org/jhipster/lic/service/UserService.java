package org.jhipster.lic.service;

import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.UserRepository;
import org.jhipster.lic.security.jwt.TokenProvider;
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
        String originalString = requestedUser.hashCode() + "";
        User user;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                originalString.getBytes(StandardCharsets.UTF_8));
            user = userRepository.findOneByUserCode(bytesToHex(encodedhash));
            String token;
            if(user != null && passwordEncoder.matches(currentClearPassword, user.getPassword())){
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

    public UserDTO createUser(UserDTO userDTO, byte[] encodedhash) {
        User user = new User();
        UserDTO responeUser;
        user.setId(Long.parseLong(getNextUserId() + ""));
        user.setUserType(userDTO.getUserType());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        user.setUsername(userDTO.getUsername());
        user.setUserCode(bytesToHex(encodedhash));
        user.setActivationKey("12345");
        user.setCreationDate(Instant.now());
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setActive(true);
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);

        responeUser = new UserDTO(user);
        settingsService.addSettings(responeUser);
        return responeUser;
    }

    public UserDTO getUserByToken(String token){
        User user = userRepository.findOneByToken(token);
        if(user != null)
            return new UserDTO(user);
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
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String imageUrl) {

//        userRepository::findOneByUserCode(SecurityUtils.getCurrentUserLogin());
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
//        return Optional.of(userRepository
//            .findById(userDTO.getUserId()))
//            .filter(Optional::isPresent)
//            .map(Optional::get)
//            .map(user -> {
//                this.clearUserCaches(user);
//                user.setUserCode(userDTO.getUserCode());
//                user.setFirstName(userDTO.getFirstName());
//                user.setLastName(userDTO.getLastName());
//                user.setEmail(userDTO.getEmail());
//                user.setImageUrl(userDTO.getImageUrl());
//                user.setActive(userDTO.isActive());
//                this.clearUserCaches(user);
//                log.debug("Changed Information for User: {}", user);
//                return user;
//            })
//            .map(UserDTO::new);
        return null;
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
