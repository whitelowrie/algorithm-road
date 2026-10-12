package chapter01;

import org.junit.jupiter.api.Test;

public class SortTest02 {

    @Test
    public void test01() {
        var number = 4;
        var sb = new StringBuffer();
        for(int i = 0; i <= 31; i++){
            var t1 = 1 << i;
            var t2 = number & t1;
            sb.append(t2 == 0 ? "0" : "1");
        }
        System.out.println(sb.reverse());
    }

    @Test
    public void test02() {
        System.out.println(1 << 4);
    }
}
