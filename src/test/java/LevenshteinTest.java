

public class LevenshteinTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"electron",(java.lang.String)"neutron");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("3", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"kitten",(java.lang.String)"sitting");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("3", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"rosettacode",(java.lang.String)"raisethysword");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("8", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"amanaplanacanalpanama",(java.lang.String)"docnoteidissentafastneverpreventsafatnessidietoncod");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("42", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"abcdefg",(java.lang.String)"gabcdef");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("2", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_5() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"",(java.lang.String)"");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("0", resultFormatted);
    }

    @org.junit.Test(timeout = 2000)
    public void test_6() throws java.lang.Exception {
        Object result = correct_java_programs.LEVENSHTEIN.levenshtein((java.lang.String)"hello",(java.lang.String)"olleh");
        String resultFormatted = junit_test_generator.QuixFixLauncher.format(result,true);
        org.junit.Assert.assertEquals("4", resultFormatted);
    }
}

