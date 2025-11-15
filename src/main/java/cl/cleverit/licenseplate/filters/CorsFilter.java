package cl.cleverit.licenseplate.filters;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CorsFilter implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CorsFilter.class);
  
  @Value("${cors.allowed-origins:http://localhost:4200,http://localhost:3000}")
  private String allowedOrigins;
  
  private List<String> allowedOriginsList;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.info("Init CORS filter");
    if (allowedOrigins != null) {
      allowedOriginsList = Arrays.asList(allowedOrigins.split(","));
    }
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    
    String origin = httpRequest.getHeader("Origin");
    
    // Validar origen permitido
    if (isAllowedOrigin(origin)) {
      httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
      httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }
    
    // Headers de seguridad adicionales
    httpServletResponse.setHeader("Access-Control-Allow-Headers", 
        "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, " +
        "Access-Control-Request-Headers, x-api-key, Authorization");
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
    httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
    
    // Headers de seguridad HTTP
    httpServletResponse.setHeader("X-Content-Type-Options", "nosniff");
    httpServletResponse.setHeader("X-Frame-Options", "DENY");
    httpServletResponse.setHeader("X-XSS-Protection", "1; mode=block");
    httpServletResponse.setHeader("Content-Security-Policy", "default-src 'self'");
    
    // Manejar preflight OPTIONS
    if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
      httpServletResponse.setStatus(HttpServletResponse.SC_OK);
      return;
    }
    
    chain.doFilter(request, response);
  }

  private boolean isAllowedOrigin(String origin) {
    if (origin == null || allowedOriginsList == null || allowedOriginsList.isEmpty()) {
      return false;
    }
    return allowedOriginsList.contains(origin.trim());
  }

  @Override
  public void destroy() {
    LOGGER.info("Destroy CORS filter");
  }

}
