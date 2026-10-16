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

    public void println(int a){
        var sb = new StringBuffer();
        for(int i = 0; i < 32; i++) {
            sb.insert(0,(a & (1 << i)) == 0 ? '0': '1');
        }
        System.out.println(sb);
    }

    @Test
    public void test02() {
        System.out.println(1 << 4);
    }

    @Test
    public void test03() {
//        var current = ~ -2 + 1;
//        println(Integer.MAX_VALUE);
//        println(Integer.MIN_VALUE);
        println(-2);
        println(~-2 + 1);
//        println(current);
    }

    @Test
    public void test04(){
        var numbers = 100000000;
        var k = 0.5;
        var count = 0;
        for(int i = 0; i < numbers; i ++) {
            if(this.xToPower2() < k) {
                count++;
            }
        }
        System.out.println(k * k);
        System.out.println((double)count / (double) numbers);
    }

    public double xToPower2(){
        return Math.max(Math.random(), Math.random());
    }

    public int gex(){
        return Math.random() < 0.84 ? 0: 1;
    }

    @Test
    public void test05(){
        int numbers = 10000000;
        var count = 0;
        for(int i = 0; i < numbers; i++){
            if(tas() == 0) {
                count++;
            }
        }
        System.out.println(count);
    }

    public int tas(){
        int r = 0;
        do {
            r = gex();
        }while (r == gex());
        return r;
    }

}
