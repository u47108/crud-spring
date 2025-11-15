package cl.cleverit.licenseplate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cl.cleverit.licenseplate.entity.Movil;
import cl.cleverit.licenseplate.exception.InternalServerErrorException;
import cl.cleverit.licenseplate.repository.VehiculosRepository;
import cl.cleverit.licenseplate.util.Transform;

/**
 * Unit tests for TransactionServiceImpl.
 * Migrado a JUnit 5 con mejores prácticas.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionServiceImpl Tests")
class TransactionServiceImplTest {

  private static final String TEST_ENDPOINT = "http://localhost:8080/api/license";
  private static final String MUST_BE_NOT_NULL = "can't be a null value";

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private VehiculosRepository repository;

  @InjectMocks
  private TransactionServiceImpl transactionServiceImpl;

  private License testLicense;

  @BeforeEach
  void setUp() {
    transactionServiceImpl = new TransactionServiceImpl(restTemplate, TEST_ENDPOINT);
    ReflectionTestUtils.setField(transactionServiceImpl, "restTemplate", restTemplate);
    
    testLicense = new License();
    testLicense.setId("123");
    testLicense.setPatente("ABC123");
    testLicense.setTipoAuto("Sedan");
    testLicense.setColor("Rojo");
  }

  @Test
  @DisplayName("Should save license plates successfully when API returns data")
  void testSaveLicensePlate_Success() {
    // Arrange
    License[] data = { testLicense };
    ResponseEntity<License[]> responseEntity = new ResponseEntity<>(data, HttpStatus.OK);
    List<Movil> savedEntities = new ArrayList<>();
    Movil savedMovil = new Movil();
    savedMovil.setId("123");
    savedEntities.add(savedMovil);

    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenReturn(responseEntity);
    when(repository.saveAll(any(List.class))).thenReturn(savedEntities);

    // Act
    ServiceStatus result = transactionServiceImpl.saveLicensePlate();

    // Assert
    assertNotNull(result, MUST_BE_NOT_NULL);
    assertEquals(HttpStatus.CREATED.value(), result.getCode());
    assertEquals(HttpStatus.CREATED.name(), result.getMessage());
    verify(restTemplate, times(1)).exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class));
    verify(repository, times(1)).saveAll(any(List.class));
  }

  @Test
  @DisplayName("Should return conflict status when no entities are saved")
  void testSaveLicensePlate_NoEntitiesSaved() {
    // Arrange
    License[] data = { testLicense };
    ResponseEntity<License[]> responseEntity = new ResponseEntity<>(data, HttpStatus.OK);
    List<Movil> emptyList = new ArrayList<>();

    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenReturn(responseEntity);
    when(repository.saveAll(any(List.class))).thenReturn(emptyList);

    // Act
    ServiceStatus result = transactionServiceImpl.saveLicensePlate();

    // Assert
    assertNotNull(result, MUST_BE_NOT_NULL);
    assertEquals(HttpStatus.CONFLICT.value(), result.getCode());
    assertEquals(HttpStatus.CONFLICT.name(), result.getMessage());
  }

  @Test
  @DisplayName("Should return no content status when API returns empty data")
  void testSaveLicensePlate_EmptyData() {
    // Arrange
    License[] emptyData = {};
    ResponseEntity<License[]> responseEntity = new ResponseEntity<>(emptyData, HttpStatus.OK);

    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenReturn(responseEntity);

    // Act
    ServiceStatus result = transactionServiceImpl.saveLicensePlate();

    // Assert
    assertNotNull(result, MUST_BE_NOT_NULL);
    assertEquals(HttpStatus.NO_CONTENT.value(), result.getCode());
    assertEquals("No data to save", result.getMessage());
  }

  @Test
  @DisplayName("Should throw InternalServerErrorException when RestClientException occurs")
  void testSaveLicensePlate_RestClientException() {
    // Arrange
    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenThrow(new RestClientException("Connection failed"));

    // Act & Assert
    assertThrows(InternalServerErrorException.class, 
        () -> transactionServiceImpl.saveLicensePlate(),
        "Should throw InternalServerErrorException when RestClientException occurs");
    verify(restTemplate, times(1)).exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class));
    verify(repository, times(0)).saveAll(any());
  }

  @Test
  @DisplayName("Should handle null response from API")
  void testSaveLicensePlate_NullResponse() {
    // Arrange
    ResponseEntity<License[]> nullResponse = new ResponseEntity<>(null, HttpStatus.OK);

    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenReturn(nullResponse);

    // Act
    ServiceStatus result = transactionServiceImpl.saveLicensePlate();

    // Assert
    assertNotNull(result, MUST_BE_NOT_NULL);
    assertEquals(HttpStatus.NO_CONTENT.value(), result.getCode());
  }

  @Test
  @DisplayName("Should handle multiple licenses correctly")
  void testSaveLicensePlate_MultipleLicenses() {
    // Arrange
    License license1 = new License();
    license1.setId("1");
    license1.setPatente("ABC123");
    
    License license2 = new License();
    license2.setId("2");
    license2.setPatente("XYZ789");
    
    License[] data = { license1, license2 };
    ResponseEntity<License[]> responseEntity = new ResponseEntity<>(data, HttpStatus.OK);
    List<Movil> savedEntities = List.of(new Movil(), new Movil());

    when(restTemplate.exchange(
        eq(TEST_ENDPOINT), 
        eq(HttpMethod.GET), 
        any(), 
        eq(License[].class)))
        .thenReturn(responseEntity);
    when(repository.saveAll(any(List.class))).thenReturn(savedEntities);

    // Act
    ServiceStatus result = transactionServiceImpl.saveLicensePlate();

    // Assert
    assertNotNull(result, MUST_BE_NOT_NULL);
    assertEquals(HttpStatus.CREATED.value(), result.getCode());
    verify(repository, times(1)).saveAll(any(List.class));
  }

}
