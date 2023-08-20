import java.util.TreeMap;

public class MapStructure {
    //就算是引用类型，也是值的比较。但是自定义类型，是引用的比较
    public static void main(String[] args){
        TreeMap<Integer, String> treeMap1 = new TreeMap<>();
        treeMap1.put(3, "its 3");
        treeMap1.put(4, "its 4");
        treeMap1.put(8, "its 8");
        treeMap1.put(5, "its 5");
        treeMap1.put(7, "its 7");
        treeMap1.put(1, "its 1");
        treeMap1.put(2, "its 2");
        //HashMap的增删改查这里都适用,如get(), containsKey()。
        //但TreeMap更强大的地方在于能查找最大最小值：因为它有序
        System.out.println(treeMap1.firstKey());//最小的key
        System.out.println(treeMap1.lastKey());//最大的key
        // <=5; output: 5
        System.out.println(treeMap1.floorKey(5));
        // >=6; output: 7
        System.out.println(treeMap1.ceilingKey(6));
    }


}
