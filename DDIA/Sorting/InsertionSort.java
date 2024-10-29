import java.*;

public class insertionSort {

    //best case : O(n), worst case : O(n^2)
    public static void main(String[] args) {
        int arr[]= {12,11,2,3,9,19};
        int length = arr.length;

        for(int i =1;i<length;i++)
        {
            int key = arr[i];
            int j=i-1;
            while(j>0 && arr[j] > key)
            {
                arr[j+1] = arr[j];
                j=j-1;
            }
            arr[j+1] = key;
        }
        System.out.println('sorted array: ');
        for(int i=0;i<length;i++)
        {
            System.out.println(arr[i] + "");
        }
    }
}
