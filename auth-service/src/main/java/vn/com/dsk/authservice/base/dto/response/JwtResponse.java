package vn.com.dsk.authservice.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String type;

    private String username;

    private List<String> authorities;

    public JwtResponse() {

    }
}