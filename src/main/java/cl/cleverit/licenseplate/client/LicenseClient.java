package cl.cleverit.licenseplate.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cl.cleverit.licenseplate.service.License;

/**
 * Feign Client for License Plate API.
 * Replaces RestTemplate with declarative HTTP client.
 */
@FeignClient(name = "licenseClient", url = "${endpoint}")
public interface LicenseClient {

	/**
	 * Get licenses from external API.
	 * 
	 * @return array of License objects
	 */
	@GetMapping
	License[] getLicenses();
}

