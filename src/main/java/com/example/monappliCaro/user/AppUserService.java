package com.example.monappliCaro.user;

import com.example.monappliCaro.registration.token.ConfirmationToken;
import com.example.monappliCaro.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = " l'utilisateur avec l'adresse email %s n'existe pas";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(USER_NOT_FOUND_MSG));
    }

    public String signUpUser(AppUser appUser){
        boolean userExits = userRepository.findByEmail(appUser.getEmail())
                .isPresent();

        if (userExits){
            // TODO Check if attributes are the same and
            // TODO if email not confirmed send another config email
            throw new IllegalStateException("email already taken");
        }else {
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
            userRepository.save(appUser);
            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    appUser

            );
            confirmationTokenService.saveConfirmationToken(
                    confirmationToken);
            return token;
        }

    }

    public void enableAppUser(String email) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

        user.setEnabled(true); // Activer l'utilisateur
        userRepository.save(user); // Sauvegarder les modifications dans la base de données
    }
}
