package vn.com.dsk.authservice.base.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.com.dsk.authservice.base.utils.response.ErrorResponse;
import vn.com.dsk.authservice.base.utils.response.Response;
import vn.com.dsk.authservice.base.utils.response.ResponseUtils;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> unknownErrorHandler(Exception e) {
        log.error("Unexpected Exception", e);
        String errKey = "err.sys.unexpected-exception";
        String errMsg = "Unknown error";
        return ResponseUtils.internalErr(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Response> ServiceErrorHandler(ServiceException e) {
        String errKey = e.getMessage();
        String errMsg = e.getErrMsg();
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> EntityNotFoundErrorHandler(EntityNotFoundException e) {
        String errKey = e.getMessage();
        String errMsg = "Can not find " + e.getEntityName() + " with id: " + e.getEntityId();
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        return new ErrorMessage("NOT FOUND");
//    }
}
