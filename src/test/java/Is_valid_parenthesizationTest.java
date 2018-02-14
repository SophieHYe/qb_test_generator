

public class Is_valid_parenthesizationTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        java.lang.Boolean result = correct_java_programs.IS_VALID_PARENTHESIZATION.is_valid_parenthesization((java.lang.String)"((()()))()");
        org.junit.Assert.assertEquals((java.lang.Boolean)true, result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        java.lang.Boolean result = correct_java_programs.IS_VALID_PARENTHESIZATION.is_valid_parenthesization((java.lang.String)")()(");
        org.junit.Assert.assertEquals((java.lang.Boolean)false, result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        java.lang.Boolean result = correct_java_programs.IS_VALID_PARENTHESIZATION.is_valid_parenthesization((java.lang.String)"((");
        org.junit.Assert.assertEquals((java.lang.Boolean)false, result);
    }
}

