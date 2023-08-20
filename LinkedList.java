public class LinkedList {
    public class Node{
        int value;
        Node next;
        public Node(int data){
            value = data;
        }
    }
    public class DoubleNode{
        int value;
        DoubleNode last;
        DoubleNode next;
        public DoubleNode(int data){
            value = data;
        }
    }
    public Node reverseList(Node head){
        Node res = null;
        Node next = null;
        while(head != null){
            next = head.next;
            head.next = res;
            res = head;
            head = next;
        }
        return res;
    }
    public Node recusive_reverseList(Node head){
        if(head == null || head.next == null){
            return head;
        }
        //reverse everything after the current node
        Node reversed = reverseList(head.next);
        //reverse current node
        head.next.next = head;
        head.next = null;
        return reversed;//return reversed head
    }
    public DoubleNode reverseList(DoubleNode head){
        DoubleNode res = null;
        DoubleNode next = null;
        while(head != null){
            next = head.next;
            head.next = res;
            head.last = next;//compared to Node
            res = head;
            head = next;
        }
        return res;
    }
    public Node removeValue(Node head, int num){
        //the first not-num is the new head
        while(head != null){
            if(head.value != num){
                break;
            }
            head = head.next;//DoubleNode: head.last = null;
        }
        Node pre = null;//tutorial uses pre = head
        Node current = head;
        while(current != null){
            if(current.value == num) {
                pre.next = current.next;
            }else{
                pre = current;
            }
            current = current.next;
        }
        return head;
    }
}
