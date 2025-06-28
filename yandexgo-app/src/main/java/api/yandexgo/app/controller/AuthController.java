package api.yandexgo.app.controller;


import api.yandexgo.app.dto.AuthDTO;
import api.yandexgo.app.dto.ProfileDTO;
import api.yandexgo.app.dto.RegistrationDTO;
import api.yandexgo.app.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto){
        return ResponseEntity.ok().body(authService.registration(dto));
    }

    @GetMapping("/registration/email-verification/{token}")
    public ResponseEntity<String> emailVerification(@PathVariable("token") String token){
        return ResponseEntity.ok().body(authService.registrationEmailVerification(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO dto){
        return ResponseEntity.ok().body(authService.login(dto));
    }

}