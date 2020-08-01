package cl.cleverit.licenseplate.service;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionDTOTest {
  public static final String MUST_BE_A_NOTNULL = "can't be a null value";

  @Test
  public void testExceptionDTO() throws Exception {
    ExceptionDTO pojo = new ExceptionDTO();
    pojo.setError("error");
    pojo.setMessage("error");
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo);
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getError());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.getMessage());

    ExceptionDTO one = pojo;
    ExceptionDTO two = new ExceptionDTO();
    Assert.assertEquals("These should be equal", one, pojo);
    Assert.assertNotEquals(one, two);
    int oneCode = one.hashCode();
    Assert.assertEquals("HashCodes should be equal", oneCode, pojo.hashCode());
    Assert.assertEquals("HashCode should not change", oneCode, one.hashCode());
    Assert.assertEquals("HashCode should not change", oneCode, one.hashCode());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, pojo.toString());
    Assert.assertNotNull(MUST_BE_A_NOTNULL, new ExceptionDTO() );
    Assert.assertNotNull(MUST_BE_A_NOTNULL, new ExceptionDTO("asd","Asd") );
    Assert.assertNotNull(MUST_BE_A_NOTNULL, one.equals(two));
  }

}
