package com.example.monappliCaro.registration;

import com.example.monappliCaro.email.EmailSender;
import com.example.monappliCaro.registration.token.ConfirmationToken;
import com.example.monappliCaro.user.AppUser;
import com.example.monappliCaro.user.AppUserRole;
import com.example.monappliCaro.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.example.monappliCaro.registration.token.ConfirmationTokenService;


@Service
@AllArgsConstructor

public class RegistrationService {
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    @Autowired // Injection de ConfirmationTokenService
    private ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    public String register(RegistrationRequest request) {
      boolean isValidEmail = emailValidator.test(request.getEmail());
      if (!isValidEmail){
          throw new IllegalStateException("email not valid");
      }
        String token = appUserService.signUpUser(
                new AppUser(
                    request.getName(),
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    AppUserRole.USER
                )
        );
        String link = "http://localhost:8081/api/v1/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getName(),link ) );
        return token;
    }
@Transactional
    public String confirmToken(String token){

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed;");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail()
        );
        return "confirmed";
    }

    private String buildEmail(String name, String link ){
        String emailContent = "<html><body style=\"font-family: Arial, sans-serif;\">";
        emailContent += "<h2 style=\"color: #007bff;\">Bonjour " + name + ",</h2>";
        emailContent += "<p>Merci de vous être inscrit sur notre application.</p>";
        emailContent += "<p>Veuillez cliquer sur le lien ci-dessous pour confirmer votre adresse e-mail :</p>";
        emailContent += "<p><a href=\"" + link + "\" style=\"background-color: #007bff; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px;\">Confirmer mon adresse e-mail</a></p>";
        emailContent += "<p>Si vous n'avez pas demandé cette inscription, vous pouvez ignorer cet e-mail.</p>";
        emailContent += "</body></html>";
        return emailContent;
    }
}
