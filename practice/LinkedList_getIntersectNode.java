import java.util.HashSet;
import java.util.Set;

public class LinkedList_getIntersectNode {

    class Node {
        int value;
        Node next;
        public Node(int val){
            value = val;
        }
    }
    public Node getIntersectNodeSet(Node head1, Node head2){//这样做的额外空间复杂度肯定不是O(1)
        //need to check if a ring or not
        Set<Node> set1 = new HashSet<>();
        Node cur1 = head1;
        while(cur1 != null){
            if(set1.contains(cur1)){
                break;//linkedlist 1 has a ring
            }
            set1.add(cur1);
            cur1 = cur1.next;
        }
        Node cur2 = head2;
        while(cur2 != null){
            if(set1.contains(cur2)){
                return cur2;
            }
            cur2 = cur2.next;
        }
        return null;
    }

    public Node getIntersectNodePointer(Node head1, Node head2){//额外空间复杂度为O(1)
        if(head1 == null || head2 == null){
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if(loop1 == null && loop2 == null){//no loop
            return noLoop(head1, head2);
        }
        if(loop1 != null && loop2 != null){//both loop
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;//如果loop1,loop2 只有一个为null，那肯定不会相交了
    }
    //如何用指针判断闭环链表的进环节点？两个指针，快指针一次挪2步，慢指针一次挪1步。有环时他们肯定会相遇。
    //在相遇处，慢指针留原地，快指针回head；然后俩指针每次挪一步；再次相遇的地方就是进环节点
    public Node getLoopNode(Node head){
        if(head == null || head.next == null || head.next.next == null){//还得判断head.next.next，因为while里面需要调fast.next
            return null;
        }
        Node slow = head.next;//这样做的前提是head != null。所以前面要加一步判断head == null
        Node fast = head.next.next;//同上
        //为啥不是同时在head处，而是都往前挪了一次呢？因为一会儿要进while(slow != fast);如果他们一开始都在head处就进不了while()了
        while(slow != fast){
            if(fast.next == null || fast.next.next == null){
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    //如果两个链表无环，返回第一个相交节点，如果不相交，返回null：因为如果相交，他们的end node一定相同；
    //拿到各自长度，如100 和80; 让长的先走20步，来到剩余相同长度的地方，然后一起走，当两个node相同时，肯定时他们相交的节点
    public Node noLoop(Node head1, Node head2){
        if(head1 == null || head2 == null){
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n =0;
        while(cur1.next != null){
            n++;
            cur1 = cur1.next;
        }
        while(cur2.next != null){
            n--;
            cur2 = cur2.next;
        }
        if(cur1 != cur2){//如果相交，他们的end node一定相同
            return null;
        }
        cur1 = n >0 ? head1 : head2;
        cur2 = n <0 ? head1 : head2; //或者cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while(n > 0){
            cur1 = cur1.next;
            n--;
        }
        while(cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }
    //三种情况：1 在环外相交；2 在入环节点相交；3 在环内相交
    public Node bothLoop(Node head1, Node loop1, Node head2, Node loop2){
        //在环外相交
        Node cur1;
        Node cur2;
        if(loop1 == loop2){
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1){
                n++;
                cur1 = cur1.next;
            }
            while(cur2 != loop2){
                n--;
                cur2 = cur2.next;
            }
            cur1 = n>0 ? head1 : head2;
            cur2 = cur1 == head1? head2 : head1;
            n = Math.abs(n);
            while(n != 0){
                cur1 = cur1.next;
                n--;
            }
            while(cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        }else{//在环内相交
            cur1 = loop1.next;//因为如果cur1 = loop1的话，你一会儿进不了while loop，所以得先走一步
            while (cur1 != loop1){
                if(cur1 == loop2){
                    return cur1;//其实这里返回啥都行，比如loop1, loop2。因为两个表都在环里转，你咋定义他俩在环上哪个点相交的？
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }
}