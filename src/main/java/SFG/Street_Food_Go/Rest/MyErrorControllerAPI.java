//package SFG.Street_Food_Go.Rest;
//
//import SFG.Street_Food_Go.Services.DTO.ErrorStatusCodeResultDTO;
//import SFG.Street_Food_Go.Services.HandleErrorPage;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//@RestController
//public class MyErrorControllerAPI implements ErrorController {
//
//    private final HandleErrorPage handleErrorPage;
//
//    public MyErrorControllerAPI(HandleErrorPage handleErrorPage) {
//        this.handleErrorPage = handleErrorPage;
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<ErrorStatusCodeResultDTO> handle404(HttpServletRequest request) {
//        ErrorStatusCodeResultDTO error = handleErrorPage.getErrorStatusCode(request);
//        return ResponseEntity.status(error.getStatusCode()).body(error);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorStatusCodeResultDTO> handle403(HttpServletRequest request) {
//        ErrorStatusCodeResultDTO error = handleErrorPage.getErrorStatusCode(request);
//        return ResponseEntity.status(error.getStatusCode()).body(error);
//    }
//}
