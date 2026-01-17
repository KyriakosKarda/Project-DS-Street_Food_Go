package SFG.Street_Food_Go.Services.impl;

import SFG.Street_Food_Go.Services.DTO.ErrorStatusCodeResultDTO;
import SFG.Street_Food_Go.Services.HandleErrorPage;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HandleErrorPageImpl implements HandleErrorPage {

    @Override
    public ErrorStatusCodeResultDTO getErrorStatusCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode.equals(HttpStatus.NOT_FOUND.value())) {
                return new ErrorStatusCodeResultDTO(statusCode, "Resource Not Found!!!");
            }
            else if(statusCode.equals(HttpStatus.FORBIDDEN.value())) {
                return new ErrorStatusCodeResultDTO(statusCode, "No permissions!!!");
            }
            else if(statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                return new ErrorStatusCodeResultDTO(statusCode, "Server Error!!!");
            }
        }
        return new ErrorStatusCodeResultDTO(HttpStatus.NOT_FOUND.value(), "Not Found!!!");
    }

    @Override
    public ErrorStatusCodeResultDTO getErrorStatusCode(String errorMessage) {
        return new ErrorStatusCodeResultDTO(HttpStatus.NOT_FOUND.value(), errorMessage);
    }
}
