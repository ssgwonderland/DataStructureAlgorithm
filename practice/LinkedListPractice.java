package practice;

import java.util.*;

//笔试的时候还是用容器简单。面试的时候用指针比较好
public class LinkedListPractice {
    public static class Node{
        int value;
        Node next;
        public Node(int v){
            value =  v;
        }
    }
    //输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    public static Node midOrUpMidNode(Node head){
        if(head == null || head.next == null || head.next.next == null){
            return head;
        }
        //到这里了，说明该链表有三个节点或以上
        Node fast = head.next.next;//从第三个节点开始，因为小于等于三个节点的情况上面已经判断过了
        Node slow = head.next;
        while(fast.next!=null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    public static Node midOrDownMidNode(Node head){
        //1 node: return head; 2 nodes: return head.next; 3 nodes: return head.next
        if(head == null || head.next == null){
            return head;
        }
        //走到这里的，都是两个节点或以上的
        Node fast = head.next;
        Node slow = head.next;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    public static Node midOrUpMidPreNode(Node head){
        //1 node: return null; 2 nodes: return null; 3 nodes: head
        if(head == null || head.next == null || head.next.next == null){
            return null;
        }
        Node fast = head.next.next;
        Node slow = head;
        //starting from 3 nodes and above: 3 nodes (fast.next = null): return slow (don't get into while);
        //4 nodes(fast.next.next = null): return slow (don't get into while)
        //5 nodes: return head.next; 6 nodes: return head.next
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast =  fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
    public static Node midOrDownMidPreNode(Node head){
        //1 node: null; 2 nodes: head; 3 nodes: head => slow = head; return slow
        if(head == null || head.next == null){
            return null;
        }
        Node slow = head;
        //4 nodes: 2nd node (slow.next: 1 loop); 5 nodes: 2nd node (slow.next: 1 loop)
        //so original fast position: 4 nodes-2 nodes=2 nodes aka head.next
        Node fast = head.next;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    public static Node midOrDownMidPreNodeArray(Node head){
        if(head == null){
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while(cur!= null){
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size()/2);
    }
//给定一个单链表的head，请判断该链表是否为回文结构（就是正反一样，如12321）isPalindrome
    public static boolean isPalindromeMyarr(Node head){
        ArrayList<Integer> arr= new ArrayList<>();
        Node cur = head;
        while(cur != null){
            arr.add(cur.value);
            cur = cur.next;
        }
        for(int i=0; i<= arr.size()/2; i++){
            if(arr.get(i) != arr.get(arr.size()-1-i)){
                return false;
            }
        }
        return true;
    }
    public static boolean isPalindromeTutorialStack(Node head){
        Stack<Integer> helpstack = new Stack<>();
        //Stack<Node> stack = new Stack<Node>(); 教程是把Node放进去
        Node cur = head;
        while(cur != null){
            helpstack.push(cur.value);
            cur = cur.next;
        }
        while(head!= null){
            if(helpstack.pop() != cur.value){
                return false;
            }
            head = head.next;
        }
        return true;
    }
//用O（1）方法来实现extra space：用指针。好难写！！！左程云 112天P7 48分左右
    public static boolean isPalindromeMyO1(Node head){
        if(head == null || head.next == null){
            return true;
        }
        //通过指针找中点; 奇数：mid; 偶数: 上中点
        //2 nodes: slow = head.next; fast = null, not in loop;
        //3 nodes: slow = 2nd node; 4 nodes: slow = 2nd node;=>fast.next = null; fast.next.next = null=>fast = 3rd node
        Node slow = head.next;//n3 in tutorial
        Node fast = head.next.next;//n2 in tutorial
        //等同于fast = head; slow = head；上面的相当于此时fast, slow各走了一步
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        Node right = slow.next; //right part first node
        slow.next = null;
        Node cur = null;
        while(right != null){//reverse right half part linkedlist
            cur = right.next;
            right.next = slow;
            slow = right;//slow move to right
            right = cur;//right move to right
        }
        //right is the head of right half part now (n3)
        Node curleft = head;
        boolean res = true;
        while(curleft != null && cur != null){
            if(cur.next != curleft.next){
                res = false;
                break;
            }
            cur = cur.next;
            curleft = curleft.next;
        }
        //recover list
        Node temp = right.next;
        right.next = null;
        while(temp != null){
            cur = temp.next;
            temp.next = right;
            right = temp;
            temp = cur;
        }
        return res;
    }

    //将单向链表按某值划分为左边小，中间相等，右边大的形式
    //笔试用数组+partition
    public static Node listPartition1(Node head, int pivot){
        if(head == null){
            return head;
        }
        Node cur = head;
        int i = 0;//得拿到链表长度，否则没法构建arraylist
        while(cur.next != null){
            i++;
            cur = cur.next;
        }
        Node[] nodeArr = new Node[i];
        cur = head;
        for(i =0; i < nodeArr.length; i++){
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        //recover linkedlist from arraylist
        for(i = 0; i<nodeArr.length-1; i++){
            nodeArr[i].next = nodeArr[i+1];
        }
        nodeArr[i].next = null;
        return nodeArr[0];
    }
    public static void arrPartition(Node[] nodeArr, int pivot){
        int less = -1;
        int more = nodeArr.length;
        int index = 0;
        while(index < more){
            if(nodeArr[index].value == pivot){
                index++;
            }else if(nodeArr[index].value < pivot){
                swap(nodeArr, ++less, index++);
            }else{
                swap(nodeArr, --more, index);
            }
        }
    }
    private static void swap(Node[] nodeArr, int a, int b){
        Node temp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = temp;
    }
    //面试：用6个变量
    public static Node listPartition2(Node head, int pivot){
        Node sH = null;
        Node sT = null;
        Node eH = null;
        Node eT = null;
        Node mH = null;
        Node mT = null;
        Node next = null;
        while(head != null){
            next = head.next;
            head.next = null;
            if(head.value < pivot){
                if(sH == null){
                    sH = head;
                    sT = head;
                }else{//这边不好理解；你把sT 当做移动光标就行了。
                    sT.next = head;//老尾巴的next指针指向当前节点
                    sT = head;//当前节点变成新尾巴
                }

            }else if(head.value == pivot){
                if(eH == null){
                    eH = head;
                    eT = head;
                }else{
                    eT.next = head;
                    eT = head;
                }
            }else{
                if(mH == null){
                    mH = head;
                    mT = head;
                }else{
                    mT.next = head;
                    mT = head;
                }
            }
            head = next;
        }
        if(sT != null){
            sT.next = eH;
            eT = eT == null ? sT : eT;//下一步，谁去连大于区域的头，谁就变成eT
        }
        if(eT !=null){
            eT.next = mH;
        }
        if(sH != null){
            return sH;
        }else if(eH != null){
            return eH;
        }else{
            return mH;
        }
    }

    //有一种特殊单链表；节点类的定义:在一般Node的定义里加一个Node rand；rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
    //给定一个由Node节点类型组成的无环单链表的头节点head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。时间复杂度O(N)，额外空间复杂度O(1)

    //用哈希表建立老节点和复制节点的联系1-1‘；2-2’ =》1‘要找到2’然后连上；2‘通过哈希表的2找到；2是1.next；牛逼！对1.rand也是同理
    public class CopyListWithRandom{
        class Node2{
            int value;
            Node2 next;
            Node2 rand;
            Node2(int val){value = val;}
        }
        public Node2 copyListWithRand1(Node2 head){
            HashMap<Node2, Node2> map = new HashMap<>();
            Node2 cur = head;
            while(cur != null){
                map.put(cur, new Node2(cur.value));
                cur = cur.next;
            }
            cur = head;
            Node2 curnew;
            while(cur != null){
                curnew = map.get(cur);
                curnew.next = map.get(cur.next);
                curnew.rand = map.get(cur.rand);
                cur = cur.next;
            }
            return map.get(head);
        }
        //不用哈希表的方法：把1’查到1和2之间1-1'-2-2'-3-3'-null; 假设1.rand=3；1‘.rand = 1.rand.next；最后把新老链表分离
        public Node2 copyListWithRand2(Node2 head){
            if(head == null){
                return null;
            }
            Node2 cur = head;
            Node2 temp = null;
            while(cur != null){
                temp = cur.next;
                cur.next = new Node2(cur.value);
                cur.next.next=temp;
                cur = temp;
            }
            cur = head;
            while(cur != null){
                cur.next.rand = cur.rand != null? cur.rand.next : null;
//                cur.next.rand = cur.rand.next;//你这样写不对！如果cur.rand= null，你哪儿来的cur.rand.next
                cur =cur.next.next;
            }
            //分离新老节点
            cur = head;
            Node2 res = head.next;
            while(cur != null){
                temp = cur.next;
                cur.next = cur.next.next; //1->2; 找不到1’了；所以在这之前先把1‘ 存起来，就是cur.next;得存两次，一次返回结果，一次作为光标
                temp.next = cur.next != null? temp.next.next : null;//2 ！= null
                cur = cur.next;
                //你这样子比较乱，好几个的.next.next，最好像教程里next = cur.next.next; curCopy = cur.next; cur.next = next; curCopy.next = next != null? next.next:null; cur = next;
            }
            return res;
        }
    }

    //给定俩个可能有环也可能无环的单链表，头节点head1和head2.请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
    //如果两个链表长度之和为N，时间复杂度要求O(N)，额外空间复杂度要求O(1)

}

