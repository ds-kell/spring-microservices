package vn.com.dsk.authservice.base.utils.response;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtils {

    /**
     * Response api
     */
    public ResponseEntity<Response> created() {
        return new ResponseEntity<>(Response.of(201, "Created", null), HttpStatus.CREATED);
    }

    public ResponseEntity<Response> ok() {
        return ResponseEntity.ok(Response.of(201, StringUtils.EMPTY, null));
    }

    public ResponseEntity<Response> ok(String msg) {
        return ResponseEntity.ok(Response.of(201, msg, null));
    }

    public ResponseEntity<Response> ok(Object data) {
        return ResponseEntity.ok(Response.of(201, "success", data));
    }

    public ResponseEntity<Response> ok(String msg, Object data) {
        return ResponseEntity.ok(Response.of(201, msg, data));
    }

    public ResponseEntity<Response> ok(Integer statusCode, String msg, Object data) {
        return ResponseEntity.ok(Response.of(statusCode, msg, data));
    }

    /**
     * Response error
     */
    public ResponseEntity<Response> badRequest(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Response> unauthorized(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Response> methodNotSupported(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public ResponseEntity<Response> mediaTypeNotSupported(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public ResponseEntity<Response> internalErr(ErrorResponse error) {
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Response> notSuccess(HttpStatus httpStt, ErrorResponse error) {
        return new ResponseEntity<>(error, httpStt);
    }
}
