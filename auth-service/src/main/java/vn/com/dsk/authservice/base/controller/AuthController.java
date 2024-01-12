package vn.com.dsk.authservice.base.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.dsk.authservice.base.dto.request.LoginRequest;
import vn.com.dsk.authservice.base.dto.request.SignupRequest;
import vn.com.dsk.authservice.base.service.AuthService;
import vn.com.dsk.authservice.base.utils.response.Response;
import vn.com.dsk.authservice.base.utils.response.ResponseUtils;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("public/auth/login")
    public ResponseEntity<Response> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseUtils.ok(authService.login(loginRequest));
    }

    @PostMapping("public/auth/signup")
    public ResponseEntity<Response> registerAccount(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseUtils.ok(authService.signup(signupRequest));
    }
    @GetMapping("private/auth/refresh-token")
    public ResponseEntity<Response> refreshToken(@Valid @RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        final String space = "\\s+";
        return ResponseUtils.ok("verified", authService.verifyExpiration(refreshToken.split(space)[1]));
    }

}
