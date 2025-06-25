package api.yandexgo.app.controller;


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

    @GetMapping("/registration/email-verification/{profileId}")
    public ResponseEntity<String> emailVerification(@PathVariable("profileId") Integer profileId){
        return ResponseEntity.ok().body(authService.registrationEmailVerification(profileId));
    }

}