package cl.cleverit.licenseplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { MultipartAutoConfiguration.class, JmxAutoConfiguration.class, 
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ComponentScan("cl.cleverit.licenseplate")
public class LicenseplateApplication {

  @Value("${timeout:25000}")
  private String beTimeout;
  
	public static void main(String[] args) {
		SpringApplication.run(LicenseplateApplication.class, args);
	}
	
	// Swagger/OpenAPI documentation is now handled by Springdoc OpenAPI
	// No need for manual Docket configuration
	
	// SSL/TLS and RestTemplate configuration moved to SecurityRestTemplateConfig
	// CORS filter is now automatically registered via @Component annotation
}
