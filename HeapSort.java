import java.util.PriorityQueue;

public class HeapSort {
    //思路：先把数组排成大根堆，然后把[0]的数和最后一个数对调，这样最后一个数就是最大值了。再把heapSize--;循环直到最后一个数是[0]
    //额外空间复杂度O(1)
    //从上到下（heapInsert)时间复杂度O(N*logN);从下到上(heapify)时间复杂度O(N)
    public static void heapSort(int[]arr){
        if(arr == null || arr.length <2){
            return;
        }
//        for(int i = 0; i<arr.length; i++) {//O(N)
//            heapInsert(arr, i);//O(logN)
//        }//大根堆了
        //对前面的for循环大根堆进行优化：O(N)
        for(int i= arr.length-1; i>=0; i--){
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while(heapSize>0){
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }
    private static void heapInsert(int[] arr, int i){
        while(arr[i]>arr[(i-1)/2]){
            swap(arr, i, (i-1)/2);
            i = (i-1)/2;
        }
    }
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index*2+1;
        while(left<heapSize){
            int largest = (left+1)<heapSize && arr[left+1] > arr[left] ? left+1 : left;
            largest = arr[index]>arr[largest] ? index : largest;
            if(index == largest){
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index *2 +1;
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp;
        temp = arr[a];
        arr[a]=arr[b];
        arr[b] = temp;
    }

    //应用：已知一个几乎有序的数组，几乎有序是指，如果把数组排好顺序，每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
    //生成一个小根堆，小根堆放的永远是当前最小的可能性。(0~k)先排好前k+1个数的序；
    public void sortedArrDistanceLessK (int[]arr, int k){
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        //先把前k+1个数放堆里; 根据堆的特性，它会自己排成小根堆；所以前k+1个排好了
        for(; index <= Math.min(arr.length-1, k); index++){//小心数列长度小于k的情况
            heap.add(arr[index]);
        }
        int i = 0;
        for(; index < arr.length; i++, index++){//从k+2开始，先加一个进堆，把堆的第一个弹给arr；先弹再加也可以
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }
        while(!heap.isEmpty()){//最后把堆里剩余的都谈给arr
            arr[i++] = heap.poll();
        }
    }

    public static void main(String[] args) {
        int[]arr = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
        heapSort(arr);
        System.out.println("my regular");
        for(int i = 0; i<arr.length; i++){
            System.out.print(arr[i]);
        }
//        System.out.println();
//        int[]arr1 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
//        mergeSortRecursive(arr1);
//        System.out.println("my recursive");
//        for(int i = 0; i<arr1.length; i++){
//            System.out.print(arr1[i]);
//        }
//        System.out.println();
//        int[]arr2 = {3, 5, 1, 7, 9,2, 0, 2, 1, 8};
//        mergeSortRecursiveSample(arr2);
//        System.out.println("sample recursive");
//        for(int i = 0; i<arr2.length; i++){
//            System.out.print(arr2[i]);
//        }
    }
}
