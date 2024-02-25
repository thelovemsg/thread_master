package src.stream;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        int[] nums = {1,5,3,-2,9,12};

        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }

        int asInt = Arrays.stream(nums).reduce((a, b) -> a + b).getAsInt();
        System.out.println("asInt = " + asInt);


    }
}
