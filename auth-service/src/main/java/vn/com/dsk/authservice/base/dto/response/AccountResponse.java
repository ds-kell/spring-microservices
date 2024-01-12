package vn.com.dsk.authservice.base.dto.response;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String username;
    private String email;
}
