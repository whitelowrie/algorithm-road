package chapter01;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SortTest01 {


    NumberTools tools = new NumberTools(3, 100);

    /**
     * 选择排序
     */
    @Test
    public void test01() {
        var arr = new int[]{5, 1, 4, 2, 3};
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    swap(arr, i, j);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * 冒泡排序
     */
    @Test
    public void test02() {
        var arr = new int[]{5, 1, 4, 2, 3};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++){
                if(arr[j] > arr[j+1]){
                    swap(arr, j, j+1);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 找出只某个数字出现两次并且为偶数
     */
    @Test
    public void test03() {
        var arr = new int[]{17, 18, 19, 5, 18, 19, 17, 5, 19, 5};


        var eor = 0;
        for (int i = 0; i < arr.length; i++) {
             eor ^= arr[i];
        }
        System.out.println(eor);

        // 找到最右侧的1
        /**
         * 10000100
         * 负数就是取反加1也就是 ~eor + 1
         * 01111011 + 1 = 01111100
         *
         * 10000100
         * 01111100
         * 两者与运算那么就是 00000100
         */

        /**
         * 10110
         * 10
         * */
        var number = eor & (-eor);
        System.out.println(number);
        var eor2 = eor;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & number) == 0) {
                eor2 ^= arr[i];
            }
        }
        System.out.println(eor2);
        System.out.println(eor ^ eor2);
    }

    /**
     * 有两个数据出现了奇数次，其他出现了偶数次
     */
    @Test
    public void test04() {
        var arr = new int[]{17, 18, 19, 5, 18, 19, 17, 5, 19, 5};
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        int rightOne = eor & (~eor + 1);// 提取出最右的1
        int onlyOne = 0;//eor
        for (int cur : arr) {
            if ((cur & rightOne) == 0) {
                onlyOne ^= cur;
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

    /**
     * 插入排序
     */
    @Test
    public void test05() {
        var arr = tools.getItems();
        var start = System.currentTimeMillis();
        for(int i = 1; i < arr.length; i++){
            for(int j = i; j > 0; j--){
                if(arr[j] < arr[j-1]){
                    swap(arr, j, j-1);
                }else {
                    break;
                }
            }
        }
        var end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + " ms");
    }


    @Test
    public void test06(){
        var items = tools.getItems();
        tools.print();

        var max = getMax(items, 0, items.length - 1);
        System.out.println(max);
    }

    public int getMax(int[] arr, int i, int j){
        if(i == j) return arr[i];
        var mid = i + ((j - i) >> 1);
        var max1 = getMax(arr, i, mid);
        var max2 = getMax(arr, mid + 1, j);
        return Math.max(max1, max2);
    }

    @Test
    public void test07(){
        var items = tools.getItems();
        var start = System.currentTimeMillis();
        process(items, 0, items.length - 1);
        var end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + " ms");
    }

    public void process(int[] arr, int i, int j){
        if(i == j){
            return;
        }
        var mid = i + ((j - i) >> 1);
        process(arr, i, mid);
        process(arr, mid+1, j);
        merge(arr, i, mid, j);
    }


    public static void merge(int[] arr, int i, int m, int j){
        var help = new int[j - i + 1];
        var d = 0;
        var p1 = i;
        var p2 = m + 1;
        while(p1 <= m && p2 <= j) {
            help[d++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while(p1 <= m) {
            help[d++] = arr[p1++];
        }
        while(p2 <= j) {
            help[d++] = arr[p2++];
        }
        System.arraycopy(help, 0, arr, i, help.length);
    }

    @Test
    public void test08(){
        var items = new int[]{-1, 7, 5, 8};
        System.out.println(Arrays.toString(items));
        var mixSum = new ArrayList<Integer>();
        var start = System.currentTimeMillis();
        process(items, 0, items.length - 1, mixSum);
        System.out.println("数列的小和: " + mixSum);
        var end = System.currentTimeMillis();
        System.out.println(Arrays.toString(items));
        System.out.println("耗时: " + (end - start) + " ms");
    }
    // -3, 7, 5 ;
    // -47,
    public void process(int[] items, int l, int r, ArrayList<Integer> init){
        if(l == r) {
            return;
        }
        var m = l + ((r - l) >> 1);
        process(items, l, m, init);
        process(items, m + 1, r, init);
        merge(items, l, m, r, init);
    }

    public void merge(int[] items, int l, int m, int r, ArrayList<Integer> init) {
        var help = new int[r - l + 1];
        var d = 0;
        var p1 = l;
        var p2 = m + 1;
        while(p1 <= m & p2 <= r){
            if(items[p1] < items[p2]){
                init.add(items[p1]);
                help[d++] = items[p1++];
            } else if(items[p1] >= items[p2]){
                help[d++] = items[p2++];
            }
        }
        while (p1 <= m) {
            help[d++] = items[p1++];
        }

        if(p2 <= r){
            var ls = 0;
            var sl = new ArrayList<Integer>();
            for(int i = 0; i <= r - p2; i++){
                sl.add(items[i]);
            }
            while (p2 <= r) {
                init.addAll(sl);
                help[d++] = items[p2++];
            }
        }

        System.arraycopy(help, 0, items, l, help.length);
    }




}
