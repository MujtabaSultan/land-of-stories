package com.stories.stories.services;

import com.stories.stories.mailing.AccountVerificationEmailContext;
import com.stories.stories.mailing.EmailService;
import com.stories.stories.models.Profile;
import com.stories.stories.models.User;
import com.stories.stories.models.UserDto;
import com.stories.stories.repositories.ProfileRepository;
import com.stories.stories.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private ProfileRepository profileRepository;
    private EmailService emailService;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,EmailService emailService,
                       ProfileRepository profileRepository){
        this.passwordEncoder=passwordEncoder;
        this.emailService=emailService;
        this.profileRepository=profileRepository;
        this.userRepository=userRepository;
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



        return newUser;
    }

    public User findUserByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        return user;
    }
}
