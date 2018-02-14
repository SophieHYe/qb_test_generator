

public class FlattenTest {
    @org.junit.Test(timeout = 2000)
    public void test_0() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[[1,[],[2,3]],[[4]],5]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[1,2,3,4,5]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_1() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[[],[],[],[],[]]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_2() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[[],[],1,[],1,[],[]]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[1,1]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_3() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[1,2,3,[[4]]]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[1,2,3,4]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_4() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[1,4,6]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[1,4,6]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_5() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[\"moe\",\"curly\",\"larry\"]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[\"moe\",\"curly\",\"larry\"]"), result);
    }

    @org.junit.Test(timeout = 2000)
    public void test_6() throws java.lang.Exception {
        java.lang.Object result = correct_java_programs.FLATTEN.flatten(new com.google.gson.JsonParser().parse("[\"a\",\"b\",[\"c\"],[\"d\"],[[\"e\"]]]"));
        org.junit.Assert.assertEquals(new com.google.gson.JsonParser().parse("[\"a\",\"b\",\"c\",\"d\",\"e\"]"), result);
    }
}

