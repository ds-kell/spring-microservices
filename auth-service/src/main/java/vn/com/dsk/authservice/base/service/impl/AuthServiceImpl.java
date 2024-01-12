package vn.com.dsk.authservice.base.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.dsk.authservice.base.dto.request.LoginRequest;
import vn.com.dsk.authservice.base.dto.request.SignupRequest;
import vn.com.dsk.authservice.base.dto.response.JwtResponse;
import vn.com.dsk.authservice.base.exception.EntityNotFoundException;
import vn.com.dsk.authservice.base.exception.ServiceException;
import vn.com.dsk.authservice.base.model.Account;
import vn.com.dsk.authservice.base.model.Authority;
import vn.com.dsk.authservice.base.repository.AccountRepository;
import vn.com.dsk.authservice.base.repository.AuthorityRepository;
import vn.com.dsk.authservice.base.security.jwt.JwtUtils;
import vn.com.dsk.authservice.base.service.AuthService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public JwtResponse signup(SignupRequest signupRequest) {
        if (accountRepository.existsByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail()))
            throw new ServiceException("Email or username is existed in system", "err.api.email-username-is-existed");
        Account user = new Account();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> listAuthority = signupRequest.getAuthorities();
        Set<Authority> authorities = new HashSet<>();

        if (listAuthority != null && !listAuthority.isEmpty()) {
            listAuthority.forEach(permission -> authorities.add(authorityRepository.findByName(permission).orElseThrow(() -> new EntityNotFoundException(AuthorityRepository.class.getName(), permission))));
        }
        user.setAuthorities(authorities);
        try {
            accountRepository.save(user);
            return new JwtResponse(
                    jwtUtils.generateAccessToken(signupRequest.getUsername()),
                    jwtUtils.generateRefreshToken(signupRequest.getUsername()),
                    "Bearer",
                    signupRequest.getUsername(),
                    listAuthority != null ? listAuthority.stream().toList() : null);
        } catch (DataAccessException e) {
            log.error("Error saving user to the database", e);
            throw new ServiceException("Failed to add user", "err.api.failed-to-add-user");
        }
    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new JwtResponse(
                    jwtUtils.generateAccessToken(userDetails),
                    jwtUtils.generateRefreshToken(userDetails),
                    "Bearer",
                    userDetails.getUsername(),
                    authorities);

        } catch (AuthenticationException authenticationException) {
            throw new ServiceException("Username or password is invalid", "err.authorize.unauthorized");
        }
    }

    @Override
    public String verifyExpiration(String refreshToken) {
        if (jwtUtils.validateToken(refreshToken)) {
            return refreshToken;
        } else {
            throw new ServiceException("Login session has expired", "err.token.expired");
        }
    }
}

