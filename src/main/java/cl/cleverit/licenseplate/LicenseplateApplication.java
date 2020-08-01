package cl.cleverit.licenseplate;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import cl.cleverit.licenseplate.filters.CorsFilter;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;

@SpringBootApplication(exclude = { MultipartAutoConfiguration.class, JmxAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@ComponentScan("cl.cleverit.licenseplate")
@EnableSwagger2
public class LicenseplateApplication {

  private static final int DEFAULT_TIMEOUT = 25000;
  @Value("${timeout}")
  private String beTimeout;
  
	public static void main(String[] args) {
		SpringApplication.run(LicenseplateApplication.class, args);
	}
	
	public Docket licenceApi() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("cl.cleverit.licenseplate.controller")).build();
  }
	/**
   * Initializes the bean RestTemplate
   * 
   * @return RestTemplate
   */
  @Bean
  public RestTemplate rest() {
    try {
      TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
      SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
          .loadTrustMaterial(null, acceptingTrustStrategy).build();

      SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
      CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
      HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
      httpRequestFactory.setHttpClient(httpClient);

      String sTimeout = System.getenv("TIMEOUT");
      // Valor por defecto
      int timeout = DEFAULT_TIMEOUT;
      if (sTimeout != null) {
        timeout = Integer.parseInt(sTimeout);
      }
      httpRequestFactory.setConnectionRequestTimeout(timeout);
      httpRequestFactory.setConnectTimeout(timeout);
      httpRequestFactory.setReadTimeout(timeout);

      return new RestTemplate(httpRequestFactory);
    } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
      throw new BeanInitializationException("Can't generate Rest Template", e);
    }
  }
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
    CorsFilter corsFilter = new CorsFilter();
    registrationBean.setFilter(corsFilter);
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}
