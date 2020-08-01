package cl.cleverit.licenseplate.service;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

public class ServiceStatusTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";
  
  @Test
  public void testServiceStatus() {
    ServiceStatus pojo=new ServiceStatus();
    pojo.setCode(1);
    pojo.setMessage("asd");
    pojo.setNativeCode(1);
    pojo.setNativeMessage("asd");
    pojo.setTimeStamp(LocalDateTime.now());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo);
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getCode());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getMessage());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getNativeCode());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getNativeMessage());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getTimeStamp());
  }

}
