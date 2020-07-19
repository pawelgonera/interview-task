package pl.pawelgonera.atmotermtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){

        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> customExceptionHandler(Exception e, WebRequest request)
    {
        CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(customErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
