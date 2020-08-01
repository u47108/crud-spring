package cl.cleverit.licenseplate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import cl.cleverit.licenseplate.entity.Movil;

@RepositoryRestResource(path = "vehiculos")
public interface VehiculosRepository extends JpaRepository<Movil, Long> {

  Movil findByPatente(@Param("patente") String patente);

  @Override
  @RestResource(exported = false)
  List<Movil> findAll();
  @Override
  @RestResource(exported = false)
  Movil save(Movil s);
}
