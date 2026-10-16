package chapter01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SortTest03 {



    @Test
    public void test01(){
        var arrs = new int[] {10, 11, 13, 14, 14, 15, 15, 18};
        System.out.println(this.getNumberIndex(arrs, 14));
    }

    public int getNumberIndex(int[] arrs, int number){
        int l = 0;
        int r = arrs.length - 1;
        int index = -1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if(arrs[mid] == number) {
                return mid;
            } else if(arrs[mid] > number) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return index;
    }

    @Test
    public void test02(){
        var arrs = new int[] {10, 11, 14, 14, 15, 15, 15, 18};
        System.out.println(this.getRightNumberIndex(arrs, 15));
    }

    public int getRightNumberIndex(int[] arrs, int number){
        int index = -1;
        if(arrs == null || arrs.length == 0){
            return index;
        }
        if(arrs.length == 1) {
            if(arrs[0] == number) {
                index = 0;
            }
            return index;
        }
        int l = 0;
        int r = arrs.length - 1;
        while (l <= r) {
            int mid = l + ((r - l) >> 1);
            if(arrs[mid] <= number) {
                // 注意这里，限制了下标
                index = mid;
                l = mid + 1;
            } else if(arrs[mid] > number) {
                r = mid - 1;
            }
        }
        return index;
    }

    @Test
    public void test03(){
        var arrs = new int[] { 14, 13, 11, 10, 9,  7, 13, 19, 20};
        System.out.println(this.getPartMinValue(arrs));
    }

    public int getPartMinValue(int[] arrs){
        int idx = 0;
        if(arrs == null || arrs.length == 0) {
            return idx;
        }
        if(arrs.length == 1) {
            return 0;
        }

        int l = 0;
        int r = arrs.length - 1;

        if(arrs[1] > arrs[0]) return 0;
        if(arrs[r] < arrs[r - 1]) return r;

        while(l <= r) {
            int mid = l + ((r - l) >> 1);
            if(arrs[mid] < arrs[mid - 1] && arrs[mid] < arrs[mid + 1]) {
                idx = mid;
                break;
            } else {
                /**
                 * mid > mid - 1 && mid > mid + 1
                 * mid > mid - 1 && mid < mid + 1
                 * mid < mid - 1 && mid > mid + 1
                 */
                if(arrs[mid] > arrs[mid - 1]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
        }
        return idx;
    }

    @Test
    public void test04(){

        int times = 100000;
        for (int i = 0; i < times; i++) {
            var arrs = this.randomArrs(10, 20);
            var index = this.getPartMinValue(arrs);
            if(!this.check(arrs, index)) {
                System.out.println("error");
                System.out.println(Arrays.toString(arrs));
                System.out.println(index);
                break;
            }
        }
        var temp = this.randomArrs(10, 20);
        System.out.println(Arrays.toString(temp));
        var indx = this.getPartMinValue(temp);
        System.out.println(indx);
    }

    public boolean check(int[] arrs, int index){
        if(arrs.length == 1) {
            return index == 0;
        }
        if(arrs[0] < arrs[1]) {
            return index == 0;
        }
        if(arrs[arrs.length - 1] < arrs[arrs.length - 2]) {
            return index == arrs.length - 1;
        }
        return arrs[index] < arrs[index - 1] && arrs[index] < arrs[index + 1];
    }

    public int[] randomArrs(int maxLen, int maxValue){
        int length = (int)(Math.random() * maxLen) + 1;
        int[] arrs = new int[length];
        for (int i = 0; i < length; i ++) {
            do {
                arrs[i] = (int)(Math.random() * maxValue);
            } while (i > 0 && arrs[i - 1] == arrs[i]);
        }
        return arrs;
    }

}
