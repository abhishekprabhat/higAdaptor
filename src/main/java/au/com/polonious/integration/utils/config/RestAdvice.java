package au.com.polonious.integration.utils.config;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class RestAdvice {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> interceptException(RuntimeException ex){
        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(ex.getMessage());
        ex.printStackTrace();
        return responseEntity;
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException ex, HttpServletResponse response) {

        //TODO: it should create proper exception
        response.setStatus(ex.status());
        ResponseEntity<Object> responseEntity = ResponseEntity.status(ex.status())
                .body(ex.getMessage());
        ex.printStackTrace();
        return responseEntity;
    }
}
