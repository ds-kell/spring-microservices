package vn.com.dsk.authservice.base.utils.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    protected Integer statusCode = 500;

    protected String message = "success";

    protected Object data;

    public static Response of (Integer statusCode, String message, Object data) {
        return Response.builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }

}
