import java.util.Stack;

public class Queue {
    public class Node<T>{
        T value;
        Node<T> last;
        Node<T> next;
        public Node(T data){
            value = data;
        }
    }
    public class DoubleEndsQueue<T>{//双向链表实现栈和队列。T是自定义泛型
        Node<T> head;
        Node<T> tail;
        public void addFromHead(T value){
            Node<T> newQueue =  new Node<T>(value);//建立要加入的节点
            if(head == null){
                head = newQueue;
                tail = newQueue;
            }else{
                newQueue.next = head;
                head.last = newQueue;
                head = newQueue;
            }
        }
        public T popFromHead(){
            if(head == null){
                return null;
            }
            Node<T> popNode = head;
            if(head == tail){
                head = null;
                tail = null;
            }else{
                head = head.next;
                head.last = null;
                popNode.next = null;
            }
            return popNode.value;
        }
        public T popFromBottom(){
            if(head == null){
                return null;
            }
            Node<T> popNode = tail;
            if(head == tail){
                head = null;
                tail = null;
            }else{
                tail = tail.last;//你用tail = popNode.last也一样
                tail.next = null;
                popNode.last = null;
            }
            return popNode.value;
        }
    }
    public class RingArray {//last in first out or first in last out => index++ for both push and pull
        public class MyQueue{
            private int[] arr;
            private int pushIndex;
            private int pullIndex;
            private int size;
            private final int limit;
            public MyQueue(int limit){
                arr = new int[limit];
                pushIndex = 0;
                pullIndex = 0;
                size = 0;
                this.limit = limit;
            }
            public int nextIndex(int i){
                return i < limit -1 ? i+1 : 0;
            }
            public void push(int value){
                if(size == limit){
                    throw new RuntimeException("full, cannot add more");
                }
                size++;
                arr[pushIndex] = value;
                pushIndex = nextIndex(pushIndex);
            }
            public int pop(){
                if(size == 0){
                    throw new RuntimeException("empty, nothing to pop");
                }
                int value = arr[pullIndex];
                size--;
                pullIndex = nextIndex(pullIndex);
                return value;
            }
        }
    }
    //实现一个特殊栈，在基本功能的基础上，再实现返回栈中的最小元素的功能；pop, pull, getMin都是O（1）的时间复杂度
    //思路：在基础栈的基础上再加上一个同步最小栈，同步添加同步弹出
    public class GetMinStack{
        public class MyStack2{
            private Stack<Integer> stackData;
            private Stack<Integer> stackMin;
            public MyStack2(){
                this.stackData = new Stack<Integer>();//基础功能栈
                this.stackMin = new Stack<Integer>();//最小栈
            }
            public int getmin(){
                if(this.stackMin.isEmpty()){
                    throw new RuntimeException("stack is empty");
                }
                return this.stackMin.peek();
            }
            public void push(int newNum){
                if(this.stackMin.isEmpty()){
                    this.stackMin.push(newNum);
                }else if(newNum < this.stackMin.peek()){
                    this.stackMin.push(newNum);
                }else{
                    int newMin = this.stackMin.peek();
                    this.stackMin.push(newMin);
                }
                this.stackData.push(newNum);
            }
            public int pop(){
                if(this.stackData.isEmpty()){
                    throw new RuntimeException("stack is empty");
                }
                this.stackMin.pop();
                return this.stackData.pop();
            }
        }
    }
    //如何用队列结构实现first in first out栈？用两个队列来实现，一个放data，一个放help，两个互倒
    //如何用两个栈实现队列结构？
    public static class TwoStacksQueue{
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;
        public TwoStacksQueue(){
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }
        //push栈向pop栈倒入数据
        private void pushToPop(){
            if(stackPop.empty()){
                while(!stackPush.empty()){
                    stackPop.push(stackPush.pop());
                }
            }
        }
        public void add(int pushInt){
            stackPush.push(pushInt);
            pushToPop();//多次添加数据咋办？没事儿，就放在push里，等pop那边空了再倒过去
        }
        public int pop(){
            if(stackPop.empty() && stackPush.empty()){
                throw new RuntimeException("Queue is empty");
            }
            pushToPop();
            return stackPop.pop();
        }
        public int peek(){
            if(stackPop.empty() && stackPush.empty()){
                throw new RuntimeException("Queue is empty");
            }
            pushToPop();
            return stackPop.peek();
        }
    }
    //图：一般是宽度性遍历，用队列来实现，深度性遍历，用栈来实现。如果要倒过来宽度用栈，深度用队列怎么搞，看上面的例子，两个队列拼成栈，两个栈拼成队列

}
