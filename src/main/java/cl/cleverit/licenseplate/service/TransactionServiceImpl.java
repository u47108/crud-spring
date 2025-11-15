package cl.cleverit.licenseplate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.cleverit.licenseplate.client.LicenseClient;
import cl.cleverit.licenseplate.exception.InternalServerErrorException;
import cl.cleverit.licenseplate.repository.VehiculosRepository;
import cl.cleverit.licenseplate.util.Transform;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {
  private final LicenseClient licenseClient;

  @Autowired
  public TransactionServiceImpl(LicenseClient licenseClient) {
    this.licenseClient = licenseClient;
  }

  @Autowired
  private VehiculosRepository repository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ServiceStatus saveLicensePlate() {
    ServiceStatus respuesta = new ServiceStatus();
    log.info("saveLicensePlate ");
    try {
      log.info("Calling License API via Feign Client");
      License[] rs = licenseClient.getLicenses();
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
    } catch (Exception e) {
      log.error("Error in service: " + e.getMessage(), e);
      throw new InternalServerErrorException(e.getMessage(), e);
    }

    return respuesta;
  }

}
