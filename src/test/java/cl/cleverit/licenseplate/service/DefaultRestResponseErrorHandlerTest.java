package cl.cleverit.licenseplate.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;

import cl.cleverit.licenseplate.exception.BackendServiceException;
import cl.cleverit.licenseplate.exception.InternalServerErrorException;

@RunWith(SpringRunner.class)
public class DefaultRestResponseErrorHandlerTest {

    private DefaultRestResponseErrorHandler defaultRestResponseErrorHandler = new DefaultRestResponseErrorHandler();

    @Mock
    private ClientHttpResponse clientHttpResponse;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void InitialData() {
        clientHttpResponse = new AbstractClientHttpResponse() {
            @Override public int getRawStatusCode() { return 0; }

            @Override public String getStatusText() { return null; }

            @Override public void close() { }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("{\"type\": \"InvalidParameterException\",\"description\": \"InvalidParameterException\"}".getBytes());
            }

            @Override public HttpHeaders getHeaders() { return null; }
        };
    }

    @Test
    public void shouldThrowInternalServerErrorExceptionCallingHandleError() throws URISyntaxException, IOException {
        thrown.expect(InternalServerErrorException.class);
        clientHttpResponse = new AbstractClientHttpResponse() {
            @Override public int getRawStatusCode() { return 0; }

            @Override public String getStatusText() { return null; }

            @Override public void close() { }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("{\"error\": \"InvalidServiceStateException\",\"message\": \"InvalidServiceStateException\"}".getBytes());
            }

            @Override public HttpHeaders getHeaders() { return null; }
        };
        defaultRestResponseErrorHandler.handleError(new URI("url"), HttpMethod.GET,clientHttpResponse);
    }

    @Test
    public void shouldThrowGenericExceptionCallingHandleError() throws URISyntaxException, IOException {
        thrown.expect(InternalServerErrorException.class);
        clientHttpResponse = new AbstractClientHttpResponse() {
            @Override public int getRawStatusCode() { return 0; }

            @Override public String getStatusText() { return null; }

            @Override public void close() { }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("{\"error\": \"InternalServerErrorException\",\"message\": \"InternalServerErrorException\"}".getBytes());
            }

            @Override public HttpHeaders getHeaders() { return null; }
        };
        defaultRestResponseErrorHandler.handleError(new URI("url"), HttpMethod.GET,clientHttpResponse);
    }

    @Test
    public void shouldThrowResourceAccessExceptionCallingHandleError() throws URISyntaxException, IOException {
        thrown.expect(BackendServiceException.class);
        clientHttpResponse = new AbstractClientHttpResponse() {
            @Override public int getRawStatusCode() { return 0; }

            @Override public String getStatusText() { return null; }

            @Override public void close() { }

            @Override
            public InputStream getBody() {
                return new ByteArrayInputStream("{\"error\": \"ResourceAccessException\",\"message\": \"ResourceAccessException\"}".getBytes());
            }

            @Override public HttpHeaders getHeaders() { return null; }
        };
        defaultRestResponseErrorHandler.handleError(new URI("url"), HttpMethod.GET,clientHttpResponse);
    }


}
