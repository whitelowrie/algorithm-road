package chapter01;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SortTest04 {


    static class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
        }
    }


    @Test
    public void test01(){
        var node1 = new Node(1);
        var node2 = new Node(2);
        var node3 = new Node(3);
        var node4 = new Node(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        node1 = reverseNode(node1);
        while(node1 != null) {
            System.out.println(node1.value);
            node1 = node1.next;
        }
    }

    public static Node reverseNode(Node head){
        Node pre = null;
        while(head != null) {
            var next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

}
