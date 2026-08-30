package chapter01;

import java.util.Arrays;

public class NumberTools {

    private int[] items;

    public NumberTools(int maxSize, int maxValue){
        this.items = generateItems(maxSize, maxValue);
    }

    private int[] generateItems(int maxSize, int maxValue) {
        int[] arr = new int[maxSize];
        for(int i= 0; i < arr.length; i++){
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }
        return arr;
    }

    public int[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    public boolean equal(int[] items){
        Arrays.sort(this.items);
        return Arrays.equals(this.items, items);
    }

    public void print(){
        System.out.println(Arrays.toString(items));
    }

}
