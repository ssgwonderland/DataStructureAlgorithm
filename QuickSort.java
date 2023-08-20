public class QuickSort {//把等于某个数的那个区间的第一个index和最后一个index返回
    public static int[] netherlandFlag(int[]arr, int L, int R){
        //<arr[R]....==arr[R}...>arr[R]
        if(L>R){
            return new int[]{-1, -1};
        }
        if(L==R){
            return new int[]{L, R};
        }
        int less = L-1;
        int more = R;
        int index = L;
        while(index<more){
            if(arr[index] == arr[R]){
                index++;
            }else if(arr[index]<arr[R]){
                swap(arr, index++, ++less);//arr[index]和less的后一位对调，然后index 加一
            }else{
                swap(arr, index, --more);//arr[index]和more的前一位对调，但index不变，因为换回来的并没有排序过，得排序
            }
        }
        //最后一个arr[R]并没有排过序，所以让它和more的第一位（more）进行对调
        //L...less      less+1...more-1     more...R-1  R 对调后：
        //L...less      less+1..............more more+1...R
        swap(arr, more, R);
        return new int[]{less+1, more};
    }

    private static void swap(int[] arr, int i, int j) {
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void quickSort1(int[]arr){
        //快排1.0：最后一个数X，取小于等于X的放最左边，取大于X的放中间，X在末尾别动：<=X >X X
        //然后把X换到>X的第一个位置：<=X X >X; （也就是<=X >X）确保了<=X区域里的最后一个数是X
        //接下来排序的时候，X就可以待着不动了，因为前面的都是>=它的，后面的都是小于它的。对它前面和后面的arr进行递归操作就可以
        if(arr == null || arr.length == 1){
            return;
        }
        process1(arr, 0, arr.length-1);
    }

    private static void process1(int[] arr, int L, int R) {
        if(L>R){return;}
        //L..R partition arr[R];结果：[<=arr[R]  arr[R]  >arr[R]];partition函数返回值为arr[R]的位置，就是more的第一个位置呗
        int M = partition(arr, L, R);
        process1(arr, L, M-1);
        process1(arr, M+1, R);
    }

    private static int partition(int[] arr, int l, int r) {
        if(l>r){return -1;}
        if(l == r){return l;}
        int less = l-1;
        int more = r;
        int index = l;
        while (index < more){
            if(arr[index]==arr[r]){
                index++;
            }else if(arr[index]<arr[r]){
                swap(arr, index++, ++less);
            }else{
                swap(arr, index, --more);
            }
        }
        swap(arr, more, r);
        return more;
    }

    public static void quickSort2(int[]arr){
        //你看，快排1.0中的partition只搞定了一个数(return more);而快排2.0对partition进行改进，搞定一批等于arr[R]的数，就是netherlandFlag
        if(arr == null || arr.length == 1){
            return;
        }
        process2(arr, 0, arr.length-1);
    }

    private static void process2(int[] arr, int L, int R) {
        if(L>R){return;}
        int[] equalArea = netherlandFlag(arr, L, R);
        process2(arr, L, equalArea[0]-1);
        process2(arr, equalArea[1]+1, R);
    }

    public static void quickSort3(int[]arr){
        //快排2.0中总是对最后一个数进行对比，快排3.0用了一个随机数来进行对比，将arr里的某个随机数拿出来和arr[R]对调，然后接着快排2.0
        if(arr == null || arr.length == 1){
            return;
        }
        process3(arr, 0, arr.length-1);
    }

    private static void process3(int[] arr, int L, int R) {
        if(L>R){return;}
        swap(arr, L+(int)(Math.random()*(R-L+1)), R);
        int[] equalArea = netherlandFlag(arr, L, R);
        process3(arr, L, equalArea[0]-1);
        process3(arr, equalArea[1]+1, R);
    }

    public static void main(String[] args) {
        int[]arr = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        quickSort1(arr);
        System.out.println("quickSort1");
        for(int i = 0; i<arr.length; i++){
            System.out.print(arr[i]);
        }
        System.out.println();
        int[]arr1 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        quickSort2(arr1);
        System.out.println("quickSort2");
        for(int i = 0; i<arr1.length; i++){
            System.out.print(arr1[i]);
        }
        System.out.println();
        int[]arr2 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        quickSort3(arr2);
        System.out.println("quickSort3");
        for(int i = 0; i<arr2.length; i++){
            System.out.print(arr2[i]);
        }
    }
}
