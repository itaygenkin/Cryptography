import java.util.Scanner;

public class Primes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int s = 1, d = 1, k = 1;

        System.out.println(isNaivePrime(n));
        System.out.println(millerRabinPrimes(n, s, d, k));

        scanner.close();
    }

    public static boolean isNaivePrime (int p) {
        boolean isPrime = true;
        for (int d=2; d*d <= p && isPrime; d++ ){
            if ( p % d == 0 )
                isPrime = false;
        }
        return isPrime;
    }

    public static boolean millerRabinPrimes (int n, int s, int d, int k) { //TODO: adding notes and variables meaning
        boolean ans = true;

        for (int i=1; i <= k && ans; i++ ){
            double random = Math.random();
            random = random * (n-2) + 2;
            int b = (int) random;
            int b2 = b;

            for (int j=1; j<d; j++){
                b = b * b2;
                b = b % n;
            }
            if ( b != 1 && b!= n-1 )
                ans = false;

            for (int j=1; j<s && !ans; j=j+1){
                b = b * b;
                b = b % n;
                if ( b == n-1 )
                    ans = true;
            }
        }
        return ans;
    }

}