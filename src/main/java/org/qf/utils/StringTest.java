package org.qf.utils;

/*
        When the intern method is invoked, if the pool already contains a
       string equal to this {@code String} object as determined by
     * the {@link #equals(Object)} method, then the string from the pool is
     * returned. Otherwise, this {@code String} object is added to the
     * pool and a reference to this {@code String} object is returned.
     当intern()方法被调用的时候，如果字符串池中包含了与字符串对象相同的字面(通过字符串的equals()来决定的)，然后将池子中
     字符串返回； 如果不包含，这个字符串对象会添加到字符串池中，并将该对象的引用返回。

 */
public class StringTest {
    public static void main(String[] args) {

        String str2 = "ab";

        String str = new String("a") + new String("b");
        // StringBuffer  toString(){new String("ab")};

        String str1 = str.intern();

        System.out.println(str == str1);

//        String str = "abc";
//        String s1 = new String("abc");
//        System.out.println(str.equals(s1));

        //int a = 3;
        // ldh
        // dup

    }
}
