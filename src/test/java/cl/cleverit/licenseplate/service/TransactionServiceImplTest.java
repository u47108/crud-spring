package cl.cleverit.licenseplate.service;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.cleverit.licenseplate.entity.Movil;
import cl.cleverit.licenseplate.repository.VehiculosRepository;
import cl.cleverit.licenseplate.util.Transform;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Mock
  private RestTemplate restTemplate;

  @Mock
  private VehiculosRepository repository;
  @InjectMocks
  @Spy
  private TransactionServiceImpl transactionServiceImpl;
  //private MockRestServiceServer mockServer;
  private ObjectMapper mapper = new ObjectMapper();
  License pojo;

  @Before
  public void init() {
    transactionServiceImpl = new TransactionServiceImpl(restTemplate, "localhost");
    pojo = new License();
    ReflectionTestUtils.setField(transactionServiceImpl, "restTemplate", restTemplate);
    //mockServer = MockRestServiceServer.createServer(restTemplate);

  }

  @Test
  public void testSaveLicensePlate() throws Exception {
    License[] data = { pojo };
    ResponseEntity<License[]> entityResponse;
    entityResponse = new ResponseEntity<License[]>(data, HttpStatus.OK);
    // entityResponse.
    List<Movil> saveRs = new ArrayList<>();
   /* mockServer.expect(ExpectedCount.once(), 
        requestTo(new URI("http://localhost:8080")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(mapper.writeValueAsString(data))
      );        **/                           
    Mockito.when(restTemplate.getForEntity("http://localhost", License[].class))
        .thenReturn(new ResponseEntity(data, HttpStatus.OK));
    Mockito.when(repository.saveAll(Transform.converToEnttity(data))).thenReturn(saveRs);
    ServiceStatus rs = transactionServiceImpl.saveLicensePlate();
    Assert.assertNotNull(MUST_BE_A_NOTNULL, rs);
  }

}
