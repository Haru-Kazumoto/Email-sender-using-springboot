package app.dev.pack.service;

import app.dev.pack.controller.token.ConfirmationToken;
import app.dev.pack.controller.token.service.ConfirmationTokenService;
import app.dev.pack.model.UserEntity;
import app.dev.pack.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s doesn't exists!";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final ConfirmationTokenService tokenService;

    //Constructor bean
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, ConfirmationTokenService tokenService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUp(UserEntity request){
        boolean isEmailExists = userRepository
                .findByEmail(request.getEmail())
                .isPresent();

        if(isEmailExists){
            log.warn("Email = ["+request.getEmail()+"] already taken!");
            throw new IllegalStateException("Email already taken");
        }

        String encodedPassword = encoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        userRepository.save(request);

        //UUID
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                request
        );

        tokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableAppUser(String email) {
        userRepository.enableAppUser(email);
    }
}
