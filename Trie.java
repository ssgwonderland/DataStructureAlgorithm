import java.util.HashMap;
//拓展：节点不止可以封装pass, end, nexts之类的。只要需要，你可以自行封装一些东西。应用：前缀树
public class Trie {
    public static class Node1{
        int pass;
        int end;
        Node1[] nexts;
        public Node1(){
            pass = 0;
            end = 0;
            nexts = new Node1[26];//nexts[i] == null 路不存在
        }
    }
    public static class Trie1{
        private Node1 root;//只留头结点
        public Trie1(){
            root = new Node1();
        }
        public void insert(String word){
            if(word ==null){
                return;
            }
            char[] str = word.toCharArray();
            Node1 node = root;//先准备一个指向头结点的指针
            node.pass++;//我现在要新加字符串了，所以头结点的pass值先++
            int path = 0;
            for(int i=0; i<str.length; i++){
                path = str[i] - 'a';//由字符，对应走向哪条路
                if(node.nexts[path] == null){
                    node.nexts[path] = new Node1();
                }
                node = node.nexts[path];
                node.pass++;
            }
            node.end++;
        }
        public int search(String word){
            if(word == null){
                return 0;
            }
            char[] str = word.toCharArray();
            Node1 node = root;
            int path = 0;
            for(int i=0; i<str.length; i++){
                path = str[i] - 'a';
                if(node.nexts[path] == null){
                    return 0;
                }
                node=node.nexts[path];
            }
            return node.end;
        }
        //所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre){
            if(pre == null){
                return 0;
            }
            char[] str = pre.toCharArray();
            Node1 node = root;
            int path = 0;
            for(int i=0; i<str.length; i++){
                path = str[i]-'a';
                if(node.nexts[path] == null){
                    return 0;
                }
                node = node.nexts[path];
            }
            return node.pass;
        }
        public void delete(String word){
            if(search("word") != 0){
                char[]str = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int path = 0;
                for(int i = 0; i<str.length; i++){
                    path = str[i] - 'a';
                    if(--node.nexts[path].pass == 0){
                        node.nexts[path] = null;//不用管这时候是不是遍历完了，因为节点处只有一个，把这个节点删了后，后面没遍历完的字符也没了
                        return;
                    }
                    node = node.nexts[path];
                }
                node.end--;
            }
        }
    }
    public static class Node2 {
        int pass;
        int end;
        HashMap<Integer, Node2> nexts;//当字符种类很多的时候用hashmap表示路

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }
    public static class Trie2{
        private Node2 root;
        public Trie2(){
            root = new Node2();
        }
        public void insert(String word){
            if(word == null){
                return;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            int path = 0;
            node.pass++;
            for(int i=0; i<str.length; i++){
                path = (int) str[i];//这是HashMap的key
                if(!node.nexts.containsKey(path)){
                    node.nexts.put(path, new Node2());
                }
                node = node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }
        public void delete(String word){
            if(search(word) != 0){
                char[] str =  word.toCharArray();
                Node2 node = root;
                node.pass--;
                int path = 0;
                for(int i=0; i<str.length; i++){
                    path = (int) str[i];
//                    if(node.nexts.get(path).pass == 1){
//                        node.nexts.get(path).pass--;

                    if(--node.nexts.get(path).pass == 0){
                        node.nexts.remove(path);
                        return;
                    }
                    node = node.nexts.get(path);
                }
                node.end--;
            }
        }
        public int search(String word) {
            if(word == null){
                return 0;
            }
            char[] str = word.toCharArray();
            Node2 node = root;
            int path = 0;
            for(int i=0; i<str.length; i++){
                path = (int)str[i];
                if(!node.nexts.containsKey(path)){
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end;
        }
        public int prefixNumber(String pre){
            if(pre == null){
                return 0;
            }
            char[] str = pre.toCharArray();
            Node2 node = root;
            int path = 0;
            for(int i = 0; i<str.length; i++){
                path = (int)str[i];
                if(!node.nexts.containsKey(path)){
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.pass;
        }
    }
}
