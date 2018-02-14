

public class SqrtTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)2,(double)0.01);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1.4166666666666665", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)2,(double)0.5);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1.5", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)2,(double)0.3);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("1.5", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)4,(double)0.2);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("2", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)27,(double)0.01);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("5.196164639727311", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_5() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)33,(double)0.05);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("5.744627526262464", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_6() throws java.lang.Exception {
        Object result = correct_java_programs.SQRT.sqrt((double)170,(double)0.03);
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,false);
        org.junit.Assert.assertEquals("13.038404876679632", resultFormatted);
    }
}

