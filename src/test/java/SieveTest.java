

public class SieveTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)1);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList()), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)2);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList(2)), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)4);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList(2,3)), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)7);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList(2,3,5,7)), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)20);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList(2,3,5,7,11,13,17,19)), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_5() throws java.lang.Exception {
        java.util.ArrayList result = correct_java_programs.SIEVE.sieve((java.lang.Integer)50);
        org.junit.Assert.assertEquals(new java.util.ArrayList(java.util.Arrays.asList(2,3,5,7,11,13,17,19,23,29,31,37,41,43,47)), result);
    }
}

