

public class GcdTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        Object result = correct_java_programs.GCD.gcd((int)13,(int)13);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("13", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        Object result = correct_java_programs.GCD.gcd((int)37,(int)600);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        Object result = correct_java_programs.GCD.gcd((int)20,(int)100);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("20", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        Object result = correct_java_programs.GCD.gcd((int)624129,(int)2061517);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("18913", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        Object result = correct_java_programs.GCD.gcd((int)3,(int)12);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("3", resultFormatted);
    }
}

