package cl.cleverit.licenseplate.util;

import org.junit.Assert;
import org.junit.Test;

import cl.cleverit.licenseplate.service.License;

public class TransformTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";


  @Test
  public void testConverToEnttity() throws Exception {
    License obj=new License();
    obj.setColor("rojo");
    obj.setId("1");
    obj.setPatente("abjxf");
    obj.setTipoAuto("auto");
    License[] list=new License[1];
    list[0]=obj;
    Assert.assertNotNull(MUST_BE_A_NOTNULL,Transform.converToEnttity(list));
    Assert.assertNotNull(MUST_BE_A_NOTNULL,new Transform());
  }

}
