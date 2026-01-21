package SFG.Street_Food_Go.Rest.ErrorPackage;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception ex, HttpServletRequest request) throws Exception {

        String path = request.getRequestURI();
        String acceptHeader = request.getHeader("Accept");

        // ελεγχεισ αν ειναι api path και αν ειναι request απο browser ή swagger
        boolean isApi = path.startsWith("/api/");

        // αν ειναι απο browser τοτε γυρνα το αρχειο για τα errors που ειναι σε μορφη html και οχι κατι json
        boolean isNotBrowser = acceptHeader == null || !acceptHeader.contains("text/html");

        if (isApi && isNotBrowser) {

            // αν ειναι απο swagger τοτε γυρνα το αρχειο για τα errors που ειναι σε μορφη json και οχι κατι html
            Map<String, Object> body = new HashMap<>();
            body.put("status", 500);
            body.put("error", "Internal Server Error");
            body.put("message", ex.getMessage() != null ? ex.getMessage() : "Unknown Error occurred");
            body.put("path", path);

            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        throw ex;
    }
}
