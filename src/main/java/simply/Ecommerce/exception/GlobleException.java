package simply.Ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobleException {

    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorDetails> handleSellerException(
            SellerException se, WebRequest req){

        ErrorDetails err= new ErrorDetails(se.getMessage(),
                req.getDescription(false),
                LocalDateTime.now());

//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setDetails(req.getDescription(false));
//        errorDetails.setError(se.getMessage());
//        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorDetails> ProductException(
            SellerException se, WebRequest req){

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setDetails(req.getDescription(false));
        errorDetails.setError(se.getMessage());
        errorDetails.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
