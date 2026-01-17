package SFG.Street_Food_Go.Services;

import SFG.Street_Food_Go.Services.DTO.ErrorStatusCodeResultDTO;
import jakarta.servlet.http.HttpServletRequest;


public interface HandleErrorPage {
    ErrorStatusCodeResultDTO getErrorStatusCode(HttpServletRequest request);

    ErrorStatusCodeResultDTO getErrorStatusCode(String errorMessage);
}
