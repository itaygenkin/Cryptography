import java.util.HashMap;

public class Main {

    public static void main (String[] args){
        boolean passed = true;
        for (int i = 0; i < 50 & passed; i++){
            int rand = (int) (Math.random() * 1000000 + 2);
            passed = testDivisors(rand, passed);
        }
        if (passed)
            System.out.println("PASSED ALL TESTS!!!");
    }

    public static boolean testDivisors(int n, boolean passed){
        HashMap<Integer, Integer> map = Arithmetics.primeDivisor(n);
        int[][] arr = Arithmetics.primeDivisors(n);
        for (int j = 0; j < arr.length; j ++){
            if (!map.containsKey(arr[j][0])) {
                System.out.println("Failed test for n = " + n + ". Expected: "
                        + arr[j][0] + ", but was Null");
                return false;
            }
            else if (map.get(arr[j][0]) != (arr[j][1])) {
                System.out.println("Failed test for n = " + n + ". Expected: "
                        + arr[j][1] + ", but was: " + map.get(arr[j][0]));
                return false;
            }
        }
        System.out.println("Passed test for n = " + n);
        return true;
    }
}
