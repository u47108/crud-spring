package cl.cleverit.licenseplate.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.cleverit.licenseplate.exception.BackendServiceException;
import cl.cleverit.licenseplate.exception.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultRestResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        String errorMessage = getErrorMessageFromInputStream(response.getBody());
        log.error(errorMessage);
        throwException(convertToDTO(errorMessage));
    }

    @Override
    protected boolean hasError(HttpStatus statusCode) {
        return HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode);
    }

    private ExceptionDTO convertToDTO(String jsonResponse) throws IOException {
        ExceptionDTO exception;
        ObjectMapper mapper = new ObjectMapper();
        exception = mapper.readValue(jsonResponse, ExceptionDTO.class);

        return exception;
    }

    private static String getErrorMessageFromInputStream(InputStream inputStream) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder strBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            strBuilder.append(line);
        }

        bufferedReader.close();
        return strBuilder.toString();
    }
    
    private void throwException(ExceptionDTO exception) {
        if(exception.getError() !=null) {
            switch(exception.getError()) {
                case "InternalServerErrorException":
                    throw new InternalServerErrorException(exception.getMessage());     
                case "ResourceAccessException":
                    throw new BackendServiceException(exception.getMessage());                      
                default :
                    throw new InternalServerErrorException(exception.getMessage());
            }

        }
    }
}
