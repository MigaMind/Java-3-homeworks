import java.util.Arrays;

public class ArrayMethods {
    public int[] elementsAfterTheLastFour(int[] arr) throws RuntimeException{
        int lastPosition = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 4) {
                lastPosition = i + 1;
            }
        }
        if(lastPosition == 0) {
            throw new RuntimeException("Number '4' not found in array");
        }

        int[] newArr = new int[arr.length - lastPosition];
        System.arraycopy(arr, lastPosition, newArr, 0, newArr.length);
        return newArr;
    }

    public boolean isContainsNumbers(int[] arr) {
        boolean containsOne = false;
        boolean containsFour = false;
        for (int value : arr) {
            if (value == 1) {
                containsOne = true;
            }
            if (value == 4) {
                containsFour = true;
            }
        }
        return containsOne && containsFour;
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, 4, 2, 5, 4, 8, 2};
        System.out.println(Arrays.toString(new ArrayMethods().elementsAfterTheLastFour(arr)));
        System.out.println(new ArrayMethods().isContainsNumbers(arr));
    }
}
