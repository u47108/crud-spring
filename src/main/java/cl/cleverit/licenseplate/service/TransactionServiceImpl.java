package cl.cleverit.licenseplate.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cl.cleverit.licenseplate.exception.InternalServerErrorException;
import cl.cleverit.licenseplate.repository.VehiculosRepository;
import cl.cleverit.licenseplate.util.Transform;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
  private String endpoint;
  private final RestTemplate restTemplate;

  @Autowired
  public TransactionServiceImpl(RestTemplate restTemplate, @Value("${endpoint}") String endpoint) {
    this.restTemplate = restTemplate;
    this.endpoint = endpoint;
  }

  @Autowired
  private VehiculosRepository repository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ServiceStatus saveLicensePlate() {
    ServiceStatus respuesta = new ServiceStatus();
    License[] rs;
    log.info("saveLicensePlate ");
    HttpEntity<?> requestEntity = new HttpEntity<>(null, getHeaders());
    ResponseErrorHandler responseErrorHandler = new DefaultRestResponseErrorHandler();
    restTemplate.setErrorHandler(responseErrorHandler);
    try {
      log.info("GET URL service ... {}", endpoint);
      ResponseEntity<License[]> responseEntity = restTemplate.exchange(endpoint, HttpMethod.GET, requestEntity,
          License[].class);
      if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
        rs = responseEntity.getBody();
        if (rs != null && rs.length > 0) {
          List<cl.cleverit.licenseplate.entity.Movil> savedEntities = 
              repository.saveAll(Transform.converToEnttity(rs));
          if (!savedEntities.isEmpty()) {
            respuesta.setCode(HttpStatus.CREATED.value());
            respuesta.setMessage(HttpStatus.CREATED.name());
          } else {
            respuesta.setCode(HttpStatus.CONFLICT.value());
            respuesta.setMessage(HttpStatus.CONFLICT.name());
          }
        } else {
          log.warn("No data received from external API");
          respuesta.setCode(HttpStatus.NO_CONTENT.value());
          respuesta.setMessage("No data to save");
        }
      }

    } catch (RestClientException e) {
      log.error("Error in service: " + e.getMessage(), e);
      throw new InternalServerErrorException(e.getMessage(), e);
    }

    return respuesta;
  }

  private HttpHeaders getHeaders() {
    HttpHeaders requestHeader = new HttpHeaders();
    requestHeader.setContentType(MediaType.APPLICATION_JSON);
    requestHeader.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    return requestHeader;
  }

}
