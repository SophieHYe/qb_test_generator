

public class BitcountTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)127);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("7", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)128);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)3005);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("9", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)13);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("3", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)14);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("3", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_5() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)27);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("4", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_6() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)834);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("4", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_7() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)254);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("7", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_8() throws java.lang.Exception {
        Object result = correct_java_programs.BITCOUNT.bitcount((int)256);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1", resultFormatted);
    }
}

