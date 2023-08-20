public class Heap {
    //左孩子的位置2*i+1; 右孩子的位置2*1+2; 父节点的位置(i-1)/2
    //但是上面的没法使用位运算，想要用位运算，必须放弃0节点；然后左孩子2*i(i<<1);右孩子2*i+1(i<<1|1);父节点1/2(i>>1)
    //大根堆，一棵树的最大值必须是父节点；小根堆
    public static class MyMaxHeap{
        private int[] heap;
        private final int limit;
        private int heapSize;
        public MyMaxHeap(int limit){
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }
        public boolean isEmpty(){
            return heapSize == 0;
        }
        public boolean isFull(){
            return heapSize == limit;
        }
        public void push(int value){
            if(heapSize == limit){
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }
        private void heapInsert(int[] arr, int index){
            while(arr[index]>arr[(index-1)/2]){
                swap(arr, index, (index-1)/2);
                index = (index-1)/2;
            }
        }

        private void swap(int[] arr, int a, int b) {
            int temp;
            temp = arr[a];
            arr[a]=arr[b];
            arr[b] = temp;
        }
        //把大根堆的最大值取出给用户，并把最大值删除，原来堆仍然为大根堆
        //思路：把[0]先记起来，把[heapsize]的数顶替[0]，然后heapsize--，这样意味着任何原本heapsize的数都是无效了
        //但是你顶上来的数不一定是大根堆。=》heapify: 拿俩孩子中值最大的和你PK，比孩子小就对调
        public int pop(){
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }
        private void heapify(int[] arr, int index, int heapSize) {
            int left = 2*index +1;
            while(left<heapSize){
                //左右俩孩子中，谁大，谁把自己的下标给largest
                //1)它得有右孩子；2）右孩子得大于左孩子；这样右孩子才能胜出，否则左孩子胜出
                int largest = left+1<heapSize && arr[left+1] > arr[left] ? left +1 : left;
                largest = arr[largest] > arr[index] ? largest : index;
                if(largest == index){
                    break;
                }
                swap(arr, largest, index);
                index = largest;
                left = 2*index +1;
            }
        }
    }


    public static void main(String[] args){
//        int value = 1000;
//        int limit = 100;
//        int testTimes = 1000000;
//        for (int i = 0; i<testTimes; i++){
//            int curLimit = (int)(Math.random()*limit)+1;
//            MyMaxHeap my = new MyMaxHeap(curLimit);
//            RightMaxHeap test = new RightMaxHeap(curLimit);
//            int curOpTimes = (int)(Math.random()*limit);
//            for(int j = 0; j<curOpTimes; j++){
//                if(my.isEmpty() != test.isEmpty()){
//                    System.out.println("Oops");
//                }
//                if(my.isFull() != test.isFull()){
//                    System.out.println("Oops");
//                }
//                if(my.isEmpty()){
//                    int curValue = (int)(Math.random()*value);
//                    my.push(curValue);
//                    test.push(curValue);
//                }else if(my.isFull()){
//                    if(my.pop() != test.pop()){
//                        System.out.println("Oops");
//                    }
//                }else{
//                    if(Math.random() < 0.5){
//                        int curValue = (int)(Math.random()*value);
//                        my.push(curValue);
//                        test.push(curValue);
//                    }else{
//                        if(my.pop() != test.pop()){
//                            System.out.println("Oops");
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("finish");
    }
}
