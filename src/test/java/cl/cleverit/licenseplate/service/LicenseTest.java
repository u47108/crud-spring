package cl.cleverit.licenseplate.service;

import org.junit.Assert;
import org.junit.Test;

public class LicenseTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";

  @Test
  public void testLicense() throws Exception {

    License pojo = new License();
    pojo.setColor("asd");
    pojo.setId("1");
    pojo.setPatente("asdasd");
    pojo.setTipoAuto("auto");
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo);
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getId());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getColor());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getPatente());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getTipoAuto());
    License one = pojo;
    License two = new License();
    Assert.assertEquals("These should be equal", one, pojo);
    Assert.assertNotEquals(one, two);
    int oneCode = one.hashCode();
    Assert.assertEquals("HashCodes should be equal", oneCode, pojo.hashCode());
    Assert.assertEquals("HashCode should not change", oneCode, one.hashCode());
    Assert.assertEquals("HashCode should not change", oneCode, one.hashCode());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.toString());
  }

}
