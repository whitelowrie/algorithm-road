package chapter01;

import org.junit.jupiter.api.Test;

public class SortTest05 {


    static class Node {
        int value;
        Node last;
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
        node2.last = node1;
        node2.next = node3;
        node3.last = node2;
        node3.next = node4;
        node4.last = node3;

        node1 = reverseNode2(node1);
        while(node1 != null) {
            System.out.println(node1.value);
            if(node1.next == null) {
                break;
            }
            node1 = node1.next;
        }

        while (node1 != null) {
            System.out.println(node1.value);
            node1 = node1.last;
        }
    }

    public static Node reverseNode1(Node head){
        while(head != null) {
            var next = head.next;
            head.next = head.last;
            head.last = next;
            if(next == null) {
                break;
            }
            head = next;
        }
        return head;
    }

    public static Node reverseNode2(Node head){
        Node pre = null;
        while(head != null) {
            var next = head.next;
            head.next = pre;
            if(pre != null) {
                pre.last = head;
            }
            pre = head;
            head = next;
        }
        pre.last = null;
        return pre;
    }

}
