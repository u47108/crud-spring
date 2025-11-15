package cl.cleverit.licenseplate.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Configuration for RestTemplate with secure SSL/TLS handling.
 * 
 * IMPORTANT: The development profile uses a trust-all strategy which is INSECURE.
 * NEVER use this in production environments.
 */
@Configuration
public class SecurityRestTemplateConfig {

    private static final int DEFAULT_TIMEOUT = 25000;

    @Value("${timeout:25000}")
    private int timeout;

    /**
     * RestTemplate for PRODUCTION with proper SSL certificate validation.
     * This bean is only active in production profile.
     */
    @Bean
    @Profile("production")
    public RestTemplate restTemplateProduction() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(timeout);
        factory.setConnectionRequestTimeout(timeout);
        factory.setReadTimeout(timeout);
        
        // Use default SSL context which validates certificates properly
        return new RestTemplate(factory);
    }

    /**
     * RestTemplate for DEVELOPMENT/TEST with trust-all SSL.
     * WARNING: This is INSECURE and should NEVER be used in production!
     * This bypasses SSL certificate validation.
     */
    @Bean
    @Profile({"!production", "dev", "test", "local"})
    public RestTemplate restTemplateDevelopment() {
        try {
            // WARNING: This is insecure and should only be used in development
            org.apache.http.conn.ssl.TrustStrategy acceptingTrustStrategy = 
                (java.security.cert.X509Certificate[] chain, String authType) -> true;
            
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy).build();

            org.apache.http.conn.ssl.SSLConnectionSocketFactory csf = 
                new org.apache.http.conn.ssl.SSLConnectionSocketFactory(sslContext);
            
            org.apache.http.impl.client.CloseableHttpClient httpClient = 
                org.apache.http.impl.client.HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();
            
            HttpComponentsClientHttpRequestFactory httpRequestFactory = 
                new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setHttpClient(httpClient);
            httpRequestFactory.setConnectionRequestTimeout(timeout);
            httpRequestFactory.setConnectTimeout(timeout);
            httpRequestFactory.setReadTimeout(timeout);

            return new RestTemplate(httpRequestFactory);
        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
            throw new BeanInitializationException("Can't generate Rest Template for development", e);
        }
    }
}

