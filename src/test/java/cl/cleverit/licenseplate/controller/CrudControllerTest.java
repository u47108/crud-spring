package cl.cleverit.licenseplate.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import cl.cleverit.licenseplate.service.ServiceStatus;
import cl.cleverit.licenseplate.service.TransactionService;

@RunWith(SpringRunner.class)
public class CrudControllerTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";
  @Mock
  private TransactionService service;
  @InjectMocks
  private CrudController crudController;

  @Before
  public void init() {
    crudController = new CrudController(service);

  }

  @Test
  public void testSaveAllVechiculos() throws Exception {
    ServiceStatus response=new ServiceStatus(200, "ok");
    Mockito.when(service.saveLicensePlate()).thenReturn(response);
     ResponseEntity<ServiceStatus> rs = crudController.saveAllVechiculos();
    Assert.assertNotNull(MUST_BE_A_NOTNULL, rs);
  }

}
