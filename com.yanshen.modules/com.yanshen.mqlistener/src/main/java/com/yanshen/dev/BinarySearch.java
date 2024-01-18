package com.yanshen.dev;

/**
 * 二分法算法实现
 *
 * @Auther: cyc
 * @Date: 2023/4/4 13:05
 * @Description: 二分法算法实现
 */
public class BinarySearch {



    /**
     * 使用递归的二分查找
     *title:recursionBinarySearch
     *@param arr 有序数组
     *@param key 待查找关键字
     * @param  low 左下标
     * @param  high 右下标
     *@return 找到的位置
     */
    public static int recursionBinarySearch(int[] arr,int key,int low,int high){

        if(key < arr[low] || key > arr[high] || low > high){
            return -1;
        }

        int middle = (low + high) / 2;			//初始中间位置
        if(arr[middle] > key){
            //比关键字大则关键字在左区域
            return recursionBinarySearch(arr, key, low, middle - 1);
        }else if(arr[middle] < key){
            //比关键字小则关键字在右区域
            return recursionBinarySearch(arr, key, middle + 1, high);
        }else {
            return middle;
        }

    }


    /**
     * 不使用递归的二分查找
     *title:commonBinarySearch
     *@param arr
     *@param key
     *@return 关键字位置
     */
    public static int commonBinarySearch(int[] arr,int key){
        int low = 0;
        int high = arr.length - 1;
        int middle = 0;			//定义middle

        if(key < arr[low] || key > arr[high] || low > high){
            return -1;
        }
        int times =0;
        while(low <= high){
            times++;
            middle = (low + high) / 2;
            if(arr[middle] > key){
                //比关键字大则关键字在左区域
                high = middle - 1;
            }else if(arr[middle] < key){
                //比关键字小则关键字在右区域
                low = middle + 1;
            }else{
                System.out.println("一共循环了:"+times+"次");
                return middle;
            }
        }

        return -1;		//最后仍然没有找到，则返回-1
    }

    /**
     * 非递归的实现
     * @param a
     * @param key
     * @return
     */
   public static int bsearchWithoutRecursion(int a[], int key) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (a[mid] > key)
                high = mid - 1;//key在左边
            else if (a[mid] < key)
                low = mid + 1;//key在当前区间右边
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {0,1,2,3,4,5,6,7,8,9,10,11,12};

        int low =0;
        int high =array.length-1;
        int key =11;
       // int position =recursionBinarySearch(array,key,low,high);
        int position = commonBinarySearch(array, key);
        System.out.println("查找到的位置是: "+position+",对应的值是:"+array[position]);
    }
}
