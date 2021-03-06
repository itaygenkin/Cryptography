import java.util.Scanner;

public class Primes {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //int n = scanner.nextInt();
        int s = 1, d = 1, k = 1;
        int p = 2011;
        System.out.println(isNaivePrime(p));
        System.out.println(millerRabinPrimes(p, 128));

        scanner.close();
    }

    public static boolean isNaivePrime (int p) {
        boolean isPrime = true;
        for (int d = 2; d * d <= p && isPrime; d++ ){
            if ( p % d == 0 )
                isPrime = false;
        }
        return isPrime;
    }

    // check for prime number (good for numbers up to 1368)
    public static boolean isSmallPrime (int p) {
        if ( p > 1368 )
            throw new IllegalArgumentException("Number is too big");
        //small known primes to run faster in small cases
        int[] smallPrimeArray = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (int d = 0; d < smallPrimeArray.length; d++) {
            if (p % smallPrimeArray[d] == 0)
                return false;
        }
        return true;
    }

    public static boolean millerRabinPrimes (int n, int s, int d, int k) {
        //TODO: adding notes and variables meaning
        boolean ans = true;

        for (int i = 1; i <= k && ans; i++ ){
            double random = Math.random();
            random = random * (n-2) + 2;
            int b = (int) random;
            int b2 = b;

            for (int j = 1; j < d; j++)
                b = (b * b2) % n;

            if ( b != 1 && b != n - 1 )
                ans = false;

            for (int j = 1; j < s && !ans; j++){
                b = (b * b) % n;
                b = b % n;
                if ( b == n - 1 )
                    ans = true;
            }
        }
        return ans;
    }

    public static boolean millerRabinPrimes (int n, int k) {
        //TODO: tests, adding notes and variables meaning
        int s = TonelliShanks.evenOdd(n)[0];
        int d = TonelliShanks.evenOdd(n)[1];
        boolean ans = true;

        for (int i = 1; i <= k && ans; i++ ){
            double random = Math.random();
            random = random * (n-2) + 2;
            int b = (int) random;
            int b2 = b;

            for (int j = 1; j < d; j++)
                b = (b * b2) % n;

            if ( b != 1 && b != (n - 1) )
                ans = false;

            for (int j = 1; j < s && !ans; j++){
                b = (b * b) % n;
                if ( b == n - 1 )
                    ans = true;
            }
        }
        return ans;
    }

}
