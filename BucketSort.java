//计数排序：只有[min, max]范围很窄的时候适用，否则桶太多的话，不经济
//基数排序：使用条件：非负，且十进制数。桶的数据结构是队列，先进先出。这是不基于比较的排序，一旦要求稍有升级，改写代价很大。所以不具有推广性！我觉得不需要掌握，了解即可
//先准备10个桶[0,9]; 从左往右，通过个位数进桶；从左边的桶开始往外倒，先进的先倒出；（这样子个位数就排了序）
//接着根据十位数进桶（接着，十位数排了序，同时因为先进的先出，能保证个位数的顺序。先进来的都是个位数小的；先出后能保证（在十位数相同的前提下）个位数小的先出来
public class BucketSort {
    public static void countSort(int[]arr){//计数排序: only for 0~200 value
        if(arr == null || arr.length<2){
            return;
        }
        int max = Integer.MIN_VALUE;
        for(int i=0; i<arr.length; i++){
            max = Math.max(max, arr[i]);
        }
        int[]bucket = new int[max+1];//记得加一，比如0-9，最大值9，长度是10
        for(int i = 0; i<arr.length; i++){
            bucket[arr[i]]++;//进桶了
        }
        int i=0;//arr的index
        for(int j = 0; j<bucket.length; j++){//出桶了
            while(bucket[j]>0){
                arr[i] = j;
                i++;
                bucket[j]--;
            }
        }
    }

    public static void radixSort(int[]arr){
        if(arr==null || arr.length<2){
            return;
        }
        radixSort(arr, 0, arr.length-1, maxbits(arr));
    }
    //找出数组中的最大值的位数，就是下面方法中的digit
    public static int maxbits(int[]arr){
        int max = Integer.MIN_VALUE;
        for(int i = 0; i<arr.length; i++){
            max = Math.max(max, arr[i]);
        }
        int digit = 0;
        while(max!=0){
            max/=10;
            digit ++;
        }
        return digit;
    }
    //在arr的[L,R]区间上排序，最大值有多少位数则为digit；比如3, 44, 12, 100；最大值是100，100是三位数，所以digit=3
    public static void radixSort(int[]arr, int L, int R, int digit){
        final int radix = 10;//基底。十进制的数，以10为基底
        int i = 0, j = 0;
        //首先你按个位数装桶，数的类型为0~9，所以准备10个桶：count[],固定长度为10（0-9）；count[i]值就是数频或词频
        //现在把count[]变成count'[]，就是把此index前面的数和自己的值累加起来。此时的值count'[j]意思是个位数中<=j的数的个数，而个数其实也就是倒出来后放进的help[]的index+1
        //把原队列里的数，从右往左顺序，在count'[]数组里找到个位数匹配的值，根据index（就是前面说的个数-1）放入help[]数组里，同时把count'[]的值-1，因为这个index已经填充了，接下来没法再填到该index里了
        //为啥要从右往左顺序呢？因为你放到的index是此时<=j中最大数的index，也就是在0-index之间的最大值，最大值肯定在原队列的右边
        //换句话说，<=j的数=index+1，但是你不知道左边界(就是<=j的最小值）在哪里，但是你知道右边界，右边界就是j，j是在0-index范围内，个位数中的最大值
        int[]help = new int[R-L+1];//最后得把得到的数组复制回给arr，所以你得准备一个和arr等长或等区间的数组
        for(int d =1; d<=digit; d++){//从个位数开始往高位数遍历，装桶，出桶
            int[]count = new int[radix];
            for(i=L; i<R; i++){
                j=getDigit(arr[i], d);//把第d位数的数字取出来；比如103, d = 1（个位数），j=3
                count[j] ++;
            }
            for(j=1; j<radix; j++){
                count[j]=count[j]+count[j-1];//count[]自己变成count'[]
            }
            //help的index; count[j]-1=index；j是arr从右往左，第d位数的数字; help[index]=arr[index]
            for(i = R; i>=L; i--){
                j=getDigit(arr[i], d);
                help[count[j]-1] = arr[i];
                count[j]--;
            }
            for(i=L, j=0; i<=R; i++, j++){
                arr[i] = help[j];
            }
        }
    }
    public static int getDigit(int x, int d){
        return ((x/((int)Math.pow(10, d-1)))%10);
    }
}
