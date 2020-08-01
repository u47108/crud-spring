package cl.cleverit.licenseplate.entity;

import org.junit.Assert;
import org.junit.Test;

public class MovilTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";
  @Test
  public void testMovil() throws Exception {
    Movil pojo = new Movil();
    pojo.setColor("asd");
    pojo.setId("1");
    pojo.setKey(Long.MAX_VALUE);
    pojo.setPatente("asdasd");
    pojo.setTipoAuto("auto");
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo);
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getId());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getColor());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getPatente());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getTipoAuto());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getKey());
  }

}
