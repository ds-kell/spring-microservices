package vn.com.dsk.authservice.base.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.com.dsk.authservice.base.dto.UserDto;
import vn.com.dsk.authservice.base.repository.AccountRepository;
import vn.com.dsk.authservice.base.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AccountRepository accountRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto getUserInfo() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("Not found user with username: " + username));
    }

}
