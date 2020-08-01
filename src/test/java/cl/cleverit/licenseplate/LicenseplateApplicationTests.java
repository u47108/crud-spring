package cl.cleverit.licenseplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("classpath:/local.properties")
public class LicenseplateApplicationTests {

	@Test
	public void contextLoads() {
	  LicenseplateApplication app=new LicenseplateApplication();
	  app.rest();
	  app.corsFilter();
	  app.licenceApi();
    app.main(new String[] {"arg1", "arg2", "arg3"});
	}

}
