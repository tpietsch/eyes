package security.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rest.v1.models.ErrorJsonResponse;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ErrorJsonResponse unexpectedError(Exception ex) {
        return new ErrorJsonResponse(ex.getMessage());
    }

    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public @ResponseBody
    ErrorJsonResponse notFullyAuthenticated(Exception ex) {
        return new ErrorJsonResponse(ex.getMessage());
    }

    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public @ResponseBody
    ErrorJsonResponse denied(Exception ex) {
        return new ErrorJsonResponse(ex.getMessage());
    }

}