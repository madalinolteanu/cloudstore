package org.jhipster.lic.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.jhipster.lic.domain.CloudStore;
import org.jhipster.lic.domain.User;
import org.jhipster.lic.repository.UserRepository;
import org.jhipster.lic.security.SecurityUtils;
import org.jhipster.lic.service.MailService;
import org.jhipster.lic.service.UserService;
import org.jhipster.lic.service.dto.CloudStoreDTO;
import org.jhipster.lic.service.dto.UserDTO;
import org.jhipster.lic.web.rest.errors.*;
import org.jhipster.lic.web.rest.vm.ManagedUserVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;
import javax.validation.Valid;
import org.jhipster.lic.service.dto.PasswordChangeDTO;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api/cloudstore")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final MailService mailService;

    public AccountController(UserService userService, MailService mailService) {

        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param userDTO the managed user View Model
     * @throws InvalidPasswordException 400 (Bad Request) if the password is incorrect
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws LoginAlreadyUsedException 400 (Bad Request) if the login is already used
     */
    @PostMapping("/account/register")
    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    public CloudStoreDTO registerAccount(@Valid @RequestBody UserDTO userDTO) {
        CloudStoreDTO response = new CloudStoreDTO();
        if (!checkPasswordLength(userDTO.getPassword())) {
            throw new InvalidPasswordException();
        }

        UserDTO user = userService.findOneByUserCode(userDTO.hashCode());
        if(user != null) {
            throw new LoginAlreadyUsedException();
        } else {
            user = userService.createUser(userDTO);
            mailService.sendActivationEmail(user);
            response.setSuccessMessage("User successfully added!");
            response.setSuccessCode(200);
            response.setUserDTO(user);
        }

        return response;
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/account/activate")
    @Timed
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new InternalServerErrorException("No user was found for this activation key");
        }
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @return the login if the user is authenticated
     */
    @PostMapping("/account/login")
    @Timed
    public CloudStoreDTO login(@Valid @RequestBody UserDTO user) {
        CloudStoreDTO response = new CloudStoreDTO();
        log.debug("REST request to check if the current user is authenticated");
        try {
            UserDTO userDTO = userService.getByUsernameAndPassword(user);
            if( userDTO != null){
                response.setUserDTO(userDTO);
                response.setSuccessMessage("User Found!");
                response.setSuccessCode(200);
            }
        } catch (FailedLoginException e){
            response.setErrorCode(500);
            response.setErrorMessage("User not Found!");
        }
        return response;
    }

    @PostMapping("/account/logout")
    @Timed
    public CloudStoreDTO logout(@Valid @RequestBody String token) {
        CloudStoreDTO response = new CloudStoreDTO();
        log.debug("REST request to check if the current user is authenticated");

        UserDTO userDTO = userService.removeUserToken(token);
        if( userDTO != null){
            if(userDTO.getToken() == null){
                response.setUserDTO(userDTO);
                response.setSuccessMessage("Token Removed!");
                response.setSuccessCode(200);
            } else {
                response.setSuccessMessage("Token not Removed!");
                response.setSuccessCode(500);
            }
        }
        return response;
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the current user
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account/get-account")
    @Timed
    public UserDTO getAccount() {
        return new UserDTO();
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @throws EmailAlreadyUsedException 400 (Bad Request) if the email is already used
     * @throws RuntimeException 500 (Internal Server Error) if the user login wasn't found
     */
    @PostMapping("/account/save-account")
    @Timed
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
//        final String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new InternalServerErrorException("Current user login not found"));
//        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getUserCode().equalsIgnoreCase(userLogin))) {
//            throw new EmailAlreadyUsedException();
//        }
//        Optional<User> user = userRepository.findOneByUserCode(userLogin);
//        if (!user.isPresent()) {
//            throw new InternalServerErrorException("User could not be found");
//        }
//        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail()
//            , userDTO.getImageUrl());
   }

    /**
     * POST  /account/change-password : changes the current user's password
     *
     * @param passwordChangeDto current and new password
     * @throws InvalidPasswordException 400 (Bad Request) if the new password is incorrect
     */
    @GetMapping(path = "/account/reset-password")
    @Timed
    public String changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        return null;
   }

    /**
     * POST   /account/reset-password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @throws EmailNotFoundException 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset-password")
    @Timed
    public void requestPasswordReset(@RequestBody String mail) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(mail)
               .orElseThrow(EmailNotFoundException::new)
       );
    }

    @PostMapping(path = "/account/forget-password")
    @Timed
    public void forgetPassword(@RequestBody String mail) {
//        mailService.sendPasswordForgotMail(
//            userService.requestForgotPassword(mail)
//                .orElseThrow(EmailNotFoundException::new)
//        );
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}
