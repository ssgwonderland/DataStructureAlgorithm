import java.util.*;

//每个方法都必须熟练写。都是常考的基本结构
//二叉树：先序：头左右（1245367）；中序：左头右（4251637）；后序：左右头（4526731）（先中后指的是对子树来说头子树的位置；头子树在前就为先）
//递归序：每个节点都会到达三次，看下面的f()函数；那么先序就是第一次到达打印；中序是第二次到达打印；后序是第三次到达打印
public class BinaryTree {
    class Node{
        int value;
        Node left;
        Node right;
        public Node(int val){
            value = val;
        }
    }
    public void f(Node head){
        if(head == null){
            return;
        }
        //1
        f(head.left);
        //2
        f(head.right);
        //3
        //每个节点都要经过这三次到达
    }
    //先序遍历: 1245367头->左子树->右子树
    public void preRecursive(Node head){
        if(head == null){
            return;
        }
        System.out.println(head.value);
        preRecursive(head.left);
        preRecursive(head.right);
    }
    //中序遍历：4251637
    public void inRecursive(Node head){
        if(head == null){
            return;
        }
        inRecursive(head.left);
        System.out.println(head.value);
        inRecursive(head.right);
    }
    public void postRecursive(Node head){
        if(head == null){
            return;
        }
        postRecursive(head.left);
        postRecursive(head.right);
        System.out.println(head.value);
    }
    //用非递归实现、就是用栈来实现
    //先序头左右：1）弹出就打印；2）如有右孩子，压入右；3）如有左孩子压入左
    //0）压入1；1）弹出1，打印；2）压入3；3）压入2；1）弹出2，打印；2）压入5；3）压入4；1）弹出4，打印；2，3）没有孩子；1）弹出5，打印。。。。
    public void preStack(Node head){
        if(head == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()){
            head = stack.pop();//1）弹出
            System.out.println(head.value);//1) 打印
            if(head.right != null){
                stack.push(head.right);//2）如有右孩子，压入右；
            }
            if(head.left != null){
                stack.push(head.left);//3）如有左孩子压入左
            }
        }
    }
    //奇妙的是，如果你把上面的先序改成头右左，你会得到1376254，就是后序4526731的逆序！所以要实现后序就只需在先序上改个顺序再加个栈实现逆序！牛逼！
    public void post2Stacks(Node head){
        if(head == null){
            return;
        }
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        s1.push(head);
        while(!s1.isEmpty()){
            head = s1.pop();
            s2.push(head);
            if(head.left != null){
                s1.push(head.left);
            }
            if(head.right != null){
                s1.push(head.right);
            }
        }
        while(!s2.isEmpty()){
            System.out.println(s2.pop().value);
        }
    }
    //我的想法：若只能用一个栈呢？先压右，再压左，全压完再弹出打印。想法很好，但是你怎么回到左边去压左？你得通过栈顶端一个来找到头节点啊
    //教程思路：因为左右头，所以如果打印的是左孩子，接下来该去搞右孩子了（但是你不要动头，所以只能peek，不能pop)；
    //如果打印的是右孩子，说明左右孩子都搞完了，接下来该打印头了，就是stack里最上面的那个；现在你就可以pop了
    //问题来了，你咋知道你打印的是左孩子还是右孩子呢？你需要引入一个变量来帮你标记是你打印的节点，通过和它的父节点（就是stack.peek())比较；
    //也就是说，你每打印一个节点，只要stack不为空，你都得peek一下，然后比较。所以while(!stack.isEmpty())里，第一步就得peek，第二步就比较
    //就是教程里的head。同时你也需要一个遍历的节点c
    public void post1stack(Node head){
        if(head == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node c = head;//教程用的null。随便了，反正会被重新赋值
        while(!stack.isEmpty()){
            c = stack.peek();
            if(c.left != null && head != c.left && head != c.right){//左右孩子都没打印过，压入
                stack.push(c.left);//c.left != null是针对第一次经过节点；head != c.left是针对第二次经过节点（从c.left回到c)
                //head != c.right是针对第三次经过节点(从c.right回到c);所以这三个条件一个不能少
            }else if(c.right != null && head != c.right){//右孩子没打印过，压入。换句话说，左孩子处理完，第二次经过节点
                stack.push(c.right);
            }else{//c.left == null
                head = stack.pop();//4;
                System.out.println(head.value);
            }
            //感觉这个方法的条件不好写。怎么写？你就想着什么时候可以压栈，压左节点和压右节点的条件是啥，再结合f()函数三次经过节点来写条件
        }
    }

    //中序：1）如有左孩子压入左 2）弹出就打印；并来到右孩子处
    public void inStack(Node head){
        if(head == null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()){
//            while(head != null){//你这样写很蠢。已经在while里了，没必要再套一层while了，直接利用外面的那层while就可以了
//                stack.push(head);
//                head = head.left;
//            }//head = null
//            while(head == null){
//                head = stack.pop();//head = 4; head = 2;                    head=5;     head=1;
//                System.out.println(head.value);
//                head = head.right;//head = null; head = 5; =>then push(5)   head=null;  head=3;=>push(3)
//            }
            if(head != null){
                stack.push(head);
                head = head.left;
            }else{
                head = stack.pop();
                System.out.println(head.value);
                head = head.right;
            }
        }
    }

    //按层遍历（1234567）：因为先进先出，用队列queue
    //1出，1.left; 1.right进；2 出，2.left;2.right进
    public void level(Node head){
        if(head == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while(!queue.isEmpty()){
            Node cur = queue.poll();//教程里是另外设了个变量cur。我觉得我直接用head也可以
            System.out.println(cur.value);
            if(cur.left != null){
                queue.add(cur.left);
            }
            if(cur.right != null){
                queue.add(cur.right);
            }
        }
    }
    //宽度优先遍历:找出宽度最大的那一层，返回节点数。所以得在按层遍历的基础上，发现层的开始和结束。
    //我想着用hashmap简单啊，但是额外空间复杂度高了。教程里说引入flag变量来标记层的结束
    public int maxWidthMapMy(Node head){
        if(head == null){
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        HashMap<Node, Integer> map = new HashMap<Node, Integer>();
        int level = 0;//教程里设置初始值为1
        map.put(head, level);
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur.left != null){
                queue.add(cur.left);
                map.put(cur.left, map.get(cur)+1);
            }
            if(cur.right != null){
                queue.add(cur.right);
                map.put(cur.right, map.get(cur)+1);
            }
        }
        //你下面的这种写法比较麻烦，而且增加了时间复杂度。教程是直接在上面queue的while loop里面统计的。加完map就统计一下
        //因为你遍历map value得到的level num其实就是map.get(head)
        int maxnodes = 0;
        int currentlevel = 0;
        int currentnodes = 0;
        for(Integer levelnum : map.values()){
            if(currentlevel == levelnum){
                currentnodes++;
            }else{
                maxnodes = Math.max(maxnodes, currentnodes);
                currentlevel ++;//为啥你能层数相等就currentnodes++；不等就currentlevel++？因为它是按层遍历的啊；不会有不同层相叉的情况
                currentnodes = 1;//这里不是0啊！
            }
        }
        return maxnodes;
    }
    public int maxWidthMapTutorial(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        HashMap<Node, Integer> map = new HashMap<Node, Integer>();
        int level = 0;//教程里设置初始值为1
        int currentLevel = 0;
        int levelNum = 0;//就是教程的curNodeLevel
        map.put(head, level);
        int currentNodes = 1;
        int maxNodes = 1;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            levelNum = map.get(cur);
            if (cur.left != null) {
                queue.add(cur.left);
                map.put(cur.left, map.get(cur) + 1);
            }
            if (cur.right != null) {
                queue.add(cur.right);
                map.put(cur.right, map.get(cur) + 1);
            }
            if(currentLevel == levelNum){
                currentNodes ++;
            }else{
                maxNodes = Math.max(maxNodes, currentNodes);
                currentLevel ++;
                currentNodes = 1;//这里不是0啊！
            }
        }
        return maxNodes;
    }
    //我不会！怎么判断层结束？教程思路：你没发现有个规律，当上一层全部弹出时，正好下一层全部入列！！！就是教程里的cur == curEnd
    //引入两个变量Node curEnd=head当前层最右节点是谁。Node nextEnd = null如果有下一层，下一层最右节点是谁
    //queue.add的时候就让nextEnd跟着跑，
    //和map比，少了当前在统计哪一层
    //保留currentNodes,还是弹出时候统计
    public int maxWidthNoMap(Node head){
        if(head == null){
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head;
        Node nextEnd = null;
        int max =0;
        int currentNodes = 0;//弹出时候统计
        while(!queue.isEmpty()){
            Node cur = queue.poll();
            if(cur.left != null){
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if(cur.right != null){
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            currentNodes++;
            if(cur == curEnd){//说明nextEnd已经来到最右边;开始结算
                max = Math.max(max, currentNodes);
                currentNodes = 0;
                //接着要来到next level了，所以curEnd得来到nextEnd
                curEnd = nextEnd;
            }
        }
        return max;
    }
    //二叉树的序列化和反序列化（不会写）
    //你可以选择先序||中序||后序的顺序序列化。但反序列化必须用同意的顺序。且不要忽略空节点，包括最后一行没有子树的，也不要忽略null的两个子树
    //序列化怎么实现？node.value->String => String.valueOf(任何类型的变量都可以)
    //先序序列化，把先序递归的sout改成queue.add()就可以了
    public Queue<String> preSerial(Node head){
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }
    public void pres(Node head, Queue<String> ans){
        if(head == null){
            return;
        }
        ans.add(String.valueOf(head.value));
        pres(head.left, ans);
        pres(head.right, ans);
    }
    //现在得反序列化了，不仅仅是重回head.value，更重要的是，你得恢复二叉树的结构
    public Node buildByPreQueue(Queue<String> prelist){
        if(prelist == null || prelist.isEmpty()){//prelist.isEmpty()可以换成prelist.size() == 0; prelist = null不代表它是空啊。小心
            return null;
        }
        return preb(prelist);
    }
    public Node preb(Queue<String> prelist){
        String value = prelist.poll();
        if(value == null){//没想到还需要检查空。其实这个空不是给第一个节点的，是给递归后的最后一个节点的
            return null;
        }
        Node head = new Node(Integer.valueOf(value));//如果中序，就这句话和下一句话对调一下
        head.left = preb(prelist);
        head.right = preb(prelist);
        return head;
    }
    //那如果按层序列化和反序列化呢？这时候可不是递归了。我感觉也是用queue将序列化的加入，弹出时逆序列化
    public Queue<String> levelSerial(Node head){
        Queue<String> ans = new LinkedList<>();
        if(head == null){
            ans.add(null);
        }else{
            Queue<Node> queue = new LinkedList<>();
            queue.add(head);
            ans.add(String.valueOf(head.value));
            while(!queue.isEmpty()){
                head = queue.poll();
                if(head.left != null){
                    queue.add(head.left);
                    ans.add(String.valueOf(head.left.value));
                }else{
                    ans.add(null);//head.left = null时，你得加入ans。小心这里
                }
                if(head.right != null){
                    queue.add(head.right);
                    ans.add(String.valueOf(head.right.value));
                }else{
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    //反序列化：问题是，要把头节点保存了;用一个辅助queue来保存每次levelList弹出的node。虽然写出来了，但连蒙带猜，得重写几遍
    public Node buildByLevelQueue(Queue<String> levelList) {
        if(levelList == null || levelList.isEmpty()){
            return null;
        }
        Node head = generateNode(levelList.poll());//1
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur = null;
        while(!queue.isEmpty()){
            cur = queue.poll();
            cur.left = generateNode(levelList.poll());//2
            if(cur.left != null){
                queue.add(cur.left);
            }
            cur.right = generateNode(levelList.poll());//3
            if(cur.right != null){
                queue.add(cur.right);
            }
        }
        return head;
    }
    public Node generateNode(String val){
        if(val == null){
            return null;
        }
        Node node = new Node(Integer.valueOf(val));
        return node;
    }
}
