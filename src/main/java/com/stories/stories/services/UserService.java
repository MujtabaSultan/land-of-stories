package com.stories.stories.services;

import com.stories.stories.mailing.AccountVerificationEmailContext;
import com.stories.stories.mailing.EmailService;
import com.stories.stories.models.*;
import com.stories.stories.repositories.ProfileRepository;
import com.stories.stories.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${site.base.url.https}")
    private String baseurl;
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;
    private final SecureTokenService secureTokenService;
    private EmailService emailService;

    public UserService(UserRepository userRepository
            ,PasswordEncoder passwordEncoder,SecureTokenService secureTokenService
            ,EmailService emailService,
                       ProfileRepository profileRepository){
        this.passwordEncoder=passwordEncoder;
        this.emailService=emailService;
        this.profileRepository=profileRepository;
        this.userRepository=userRepository;
        this.secureTokenService=secureTokenService;
    }

    public void sendConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);
        AccountVerificationEmailContext context = new AccountVerificationEmailContext();
        context.init(user);
        context.setToken(secureToken.getToken());
        context.buildVerificationUrl(baseurl, secureToken.getToken());

        System.out.println("sending email to " + user.getEmailAddress());
        emailService.sendMail(context);
    }

    public User createUser(UserDto userObj){

        User existingUser = userRepository.findByEmailAddress(userObj.getEmailAddress());


        User newUser = new User();
        String hashedPass = passwordEncoder.encode(userObj.getPassword());
        newUser.setPassword(hashedPass);
        newUser.setEmailAddress(userObj.getEmailAddress());
        newUser.setUserName(userObj.getUserName());


        Profile inputProfile = new Profile();
        inputProfile.setFirstName(userObj.getFirstName());
        inputProfile.setProfileDescription(userObj.getProfileDescription());
        inputProfile.setLastName(userObj.getLastName());

        newUser.setProfile(inputProfile);
        userRepository.save(newUser);
        sendConfirmationEmail(newUser);

        return newUser;
    }

    public void validate(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        User user = secureToken.getUser();
        user.setAccountVerified(true);
        userRepository.save(user);
    }

    public User findUserByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        return user;
    }

    public void resetPassword(String emailAddress) {
    }
}
