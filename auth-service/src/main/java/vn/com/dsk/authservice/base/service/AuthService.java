package vn.com.dsk.authservice.base.service;


import vn.com.dsk.authservice.base.dto.request.LoginRequest;
import vn.com.dsk.authservice.base.dto.request.SignupRequest;
import vn.com.dsk.authservice.base.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse signup(SignupRequest signupRequest);

    JwtResponse login(LoginRequest loginRequest);

    String verifyExpiration(String refreshToken);
}
