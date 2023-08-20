import java.lang.reflect.Array;
import java.util.Arrays;
//应用：只要能转化为有多少个数比你大的，都可以用。为啥？
//因为在另一个array里（无论左右），只要找到一个比你大的，此array的那个数后面都会比你大，因为此array已经排好序
//比如你在左边，只要右边某个数比你大，那个数后面的都会比你大。反之亦然
public class MergeSort {
    public static void mergeSortRegular(int[]arr){
        //get left index, mid index, right index for merge()
        if(arr == null || arr.length ==1){
            return;
        }
        int len = arr.length;
        int mergeSize = 1;
        while (mergeSize<len){
            int left = 0;
            while(left<len){
                int mid = left + mergeSize -1;//left array: left...mid; right array: mid+1...right
                if(mid>=len){
                    break;
                }
                int right = Math.min(mid+mergeSize, len-1);
                merge(arr, left, mid, right);
                left = right + 1;
            }
//            mergeSize *=2;
            mergeSize <<=1;//位运算比数运算快！
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[]helparr = new int[right-left+1];
        int i = 0;//helparr index
        int leftcur = left;
        int rightcur = mid+1;
        while(leftcur<=mid && rightcur<=right){
            helparr[i++]= arr[leftcur]<=arr[rightcur] ? arr[leftcur++] : arr[rightcur++];
        }
        while(leftcur<=mid){
            helparr[i++] = arr[leftcur++];
        }
        while(rightcur<=right){
            helparr[i++] = arr[rightcur++];
        }
        for(i=0; i<helparr.length; i++){
            arr[left+i] = helparr[i];
        }
    }

    public static void mergeSortRecursive(int[]arr){
        //左边排序，右边排序，merge，左边排序和右边排序就是递归了
        //得把左边的arr拿出来，把右边的arr拿出来，放到mergeSortRecursive里
        //重复用merge(),还是得把left, mid, right拿出来
        if(arr == null || arr.length == 1){
            return;
        }
        int mid = arr.length >>1;
        int[]leftarr = Arrays.copyOfRange(arr, 0, mid);//copyOfRange is not inclusive!!!
        //left: 0...mid-1; right: mid...arr.length-1
        int[]rightarr = Arrays.copyOfRange(arr, mid, arr.length);
        mergeSortRecursive(leftarr);
        mergeSortRecursive(rightarr);
        mergeArr(arr, leftarr, rightarr);
        //如果要应用，你就return上面三者的和。然后mergeArr里，你也要return累计数或者累计数相关的东西
    }
    private static void mergeArr(int[]arr, int[]left, int[]right){
        if(arr == null || arr.length == 1){
            return;
        }
        int leftcur = 0;
        int rightcur = 0;
        int[]helper = new int[arr.length];
        int i = 0;
        while(leftcur<left.length && rightcur<right.length){
            //举个应用的例子，求最小和。
            //res += left[leftcur]<right[rightcur]?(right.length-rightcur+1)*left[leftcur]:0;
            //helper[i++] = left[leftcur]<right[rightcur] ? left[leftcur++] : right[rightcur++];
            helper[i++] = left[leftcur]<=right[rightcur] ? left[leftcur++] : right[rightcur++];
        }
        while(leftcur<left.length){
            helper[i++]=left[leftcur++];
        }
        while(rightcur<right.length){
            helper[i++]= right[rightcur++];
        }
        for(i=0; i<helper.length; i++){
            arr[i]=helper[i];
        }
    }
    public static void mergeSortRecursiveSample(int[]arr){
        if(arr == null || arr.length == 1){
            return;
        }
        process(arr, 0, arr.length-1);
    }
    private static void process(int[]arr, int L, int R){
        if(L==R){
            return;
        }
        int mid = L + ((R-L)>>1);
        process(arr, L,mid);
        process(arr, mid+1, R);
        merge(arr, L, mid, R);
    }

    public static void main(String[] args) {
        int[]arr = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        mergeSortRegular(arr);
        System.out.println("my regular");
        for(int i = 0; i<arr.length; i++){
            System.out.print(arr[i]);
        }
        System.out.println();
        int[]arr1 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        mergeSortRecursive(arr1);
        System.out.println("my recursive");
        for(int i = 0; i<arr1.length; i++){
            System.out.print(arr1[i]);
        }
        System.out.println();
        int[]arr2 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        mergeSortRecursiveSample(arr2);
        System.out.println("sample recursive");
        for(int i = 0; i<arr2.length; i++){
            System.out.print(arr2[i]);
        }
    }
}
