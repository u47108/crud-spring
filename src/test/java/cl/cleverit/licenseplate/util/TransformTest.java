package cl.cleverit.licenseplate.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cl.cleverit.licenseplate.entity.Movil;
import cl.cleverit.licenseplate.service.License;

/**
 * Unit tests for Transform utility class.
 * Migrado a JUnit 5 con mejores prácticas.
 */
@DisplayName("Transform Utility Tests")
class TransformTest {

	@Test
	@DisplayName("Should convert License array to Movil list successfully")
	void testConverToEnttity_Success() {
		// Arrange
		License license1 = createLicense("1", "ABC123", "Sedan", "Rojo");
		License license2 = createLicense("2", "XYZ789", "SUV", "Azul");
		License[] licenses = { license1, license2 };

		// Act
		List<Movil> result = Transform.converToEnttity(licenses);

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		
		Movil movil1 = result.get(0);
		assertEquals("1", movil1.getId());
		assertEquals("ABC123", movil1.getPatente());
		assertEquals("Sedan", movil1.getTipoAuto());
		assertEquals("Rojo", movil1.getColor());
		
		Movil movil2 = result.get(1);
		assertEquals("2", movil2.getId());
		assertEquals("XYZ789", movil2.getPatente());
		assertEquals("SUV", movil2.getTipoAuto());
		assertEquals("Azul", movil2.getColor());
	}

	@Test
	@DisplayName("Should return empty list when array is empty")
	void testConverToEnttity_EmptyArray() {
		// Arrange
		License[] emptyArray = {};

		// Act
		List<Movil> result = Transform.converToEnttity(emptyArray);

		// Assert
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	@DisplayName("Should throw NullPointerException when array is null")
	void testConverToEnttity_NullArray() {
		// Act & Assert
		NullPointerException exception = assertThrows(
			NullPointerException.class,
			() -> Transform.converToEnttity(null),
			"Should throw NullPointerException when array is null");
		
		assertEquals("License array cannot be null", exception.getMessage());
	}

	@Test
	@DisplayName("Should skip null licenses in array")
	void testConverToEnttity_WithNullElements() {
		// Arrange
		License license1 = createLicense("1", "ABC123", "Sedan", "Rojo");
		License license2 = null;
		License license3 = createLicense("3", "DEF456", "Pickup", "Negro");
		License[] licenses = { license1, license2, license3 };

		// Act
		List<Movil> result = Transform.converToEnttity(licenses);

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("1", result.get(0).getId());
		assertEquals("3", result.get(1).getId());
	}

	@Test
	@DisplayName("Should handle single license correctly")
	void testConverToEnttity_SingleLicense() {
		// Arrange
		License license = createLicense("1", "ABC123", "Sedan", "Rojo");
		License[] licenses = { license };

		// Act
		List<Movil> result = Transform.converToEnttity(licenses);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.size());
		Movil movil = result.get(0);
		assertEquals("1", movil.getId());
		assertEquals("ABC123", movil.getPatente());
	}

	@Test
	@DisplayName("Should handle large array efficiently")
	void testConverToEnttity_LargeArray() {
		// Arrange - Crear array grande para verificar rendimiento
		int size = 1000;
		License[] licenses = new License[size];
		for (int i = 0; i < size; i++) {
			licenses[i] = createLicense(String.valueOf(i), "PAT" + i, "Type" + i, "Color" + i);
		}

		// Act
		long startTime = System.nanoTime();
		List<Movil> result = Transform.converToEnttity(licenses);
		long endTime = System.nanoTime();

		// Assert
		assertNotNull(result);
		assertEquals(size, result.size());
		
		// Verificar que la conversión fue eficiente (menos de 1 segundo para 1000 elementos)
		long durationMs = (endTime - startTime) / 1_000_000;
		assertTrue(durationMs < 1000, "Conversion should be fast, took: " + durationMs + "ms");
	}

	private License createLicense(String id, String patente, String tipoAuto, String color) {
		License license = new License();
		license.setId(id);
		license.setPatente(patente);
		license.setTipoAuto(tipoAuto);
		license.setColor(color);
		return license;
	}
}
