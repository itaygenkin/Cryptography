import java.util.ArrayList;
import java.util.HashMap;

public class Arithmetics {

    public static void main(String []args){

        int mod = 857;
        int base = 548;
        int power = 109;
        int r = 1;

        System.out.println(moduloPower(base,power,mod));
        System.out.println(moduloPower(base,power,mod));

    }

    // return the square of 'x' modulo 'mod'
    public static int naiveSqrt (int x, int mod) {
        int output = -1;
        for (int i=2; i<mod & output<0; i=i+1) {
            if ( (i * i) % mod == x )
                output = i;
        }
        return output;
    }

//    public static int moduloPower (int base, int power, int mod) { //calculates power in finite field
//        int ans = 1;
//        for (int i = 0; i < power; i++) {
//            ans = ans * base;
//            ans = ans % mod;
//        }
//        return ans;
//    }

    // Recursively calculates power in finite field
    public static int moduloPower(int base, int power, int mod){
        int ans = 1;
        if ( power > 0 ) {
            ans = moduloPower(base, power / 2, mod);
            ans = (ans * ans) % mod;
            if ( power % 2 == 1 )
                ans = (ans * base) % mod;
        }
        return ans;
    }

    // returns the least integer, 'i', that perform r^(2^i)=1 mod p
    public static int leastPow (int r, int mod) {
        int i = 0;
        boolean found = false;
        for (int j = 0; j <= mod & !found; j++) {
            if ( r == 1 )
                found = true;
            else {
                r = r * r;
                r = r % mod;
                i = i + 1;
            }
        }
        return i;
    }

    // returns the least integer, 'i', that perform r^(2^i)=1 mod p
    public static int LeastPow (int r, int mod) {
        int i = 0;
        for (int j = 0; j <= mod; j++) {
            if ( r == 1 )
                return i;
            else {
                r = r * r;
                r = r % mod;
                i ++;
            }
        }
        return i;
    }

    // check whether 'x' is a QuadraticResidue in finite field
    public static boolean isQuadraticResidue (int x, int mod) {
        boolean ans = false;
        int a = moduloPower(x, (mod-1)/2, mod);
        if ( a == 1 )
            ans = true;
        return ans;
    }

    // return true/false rather g is a generator in the multiplicative group Z/n
    public static boolean isGenerator (int n, int g){
        int[] divisors = divisors(n-1);
        for (int i = 0; i < divisors.length && divisors[i] != 0; i++){
            if ( moduloPower(g, divisors[i], n) == 1 )
                return false;
        }
        return true;
    }

    // return an array of the divisors of n except 1 and n
    public static int[] divisors (int n){
        int[] array = new int[n/2];
        int index = 0;
        for (int d = 2; d <= n/2; d++){
            if ( n % d == 0 ){
                array[index] = d;
                index ++;
            }
        }
        int[] arr = new int[index];
        for (int i = 0; i < arr.length; i++)
            arr[i] = array[i];

        return arr;
    }

    // returns a two-dimension array of all the prime divisors and its power
    public static int[][] primeDivisors(int n){
        int[][] array = new int[n/2][2];//
        int index = 0;
        int d = 2;
        while ( n > 1 ){
            int counter = 0;
            if ( n % d == 0 ){
                array[index][0] = d;
                while ( n % d == 0 ){
                    n = n / d;
                    counter ++;
                }
                array[index][1] = counter;
                index ++;
            }
            d ++;
        }
        int[][] output = new int[index][2]; //
        for (int i=0; i < output.length; i++ ){ //
            output[i][0] = array[i][0]; //
            output[i][1] = array[i][1]; //
        } //
        return output;
    }

    public static HashMap<Integer, Integer> primeDivisor (int n){
        HashMap<Integer, Integer> divisors = new HashMap<>();
        int d = 2;
        while (n > 1) {
            int counter = 0;
            while (n % d == 0) {
                n = n / d;
                counter++;
            }
            if (counter > 0)
                divisors.put(d, counter);
            d++;
        }
        return divisors;
    }

    public static void printArray (int[] array){
        if ( array.length == 0 )
            System.out.println("{}");
        else{
            System.out.print("{");
            for (int i = 0; i < array.length - 1; i++){
                System.out.print(array[i] + ",");
            }
            System.out.println(array[array.length-1] + "}");
        }
    }

    // return first integer that is quadratic non-residue in F_p where p is power of an odd prime number
    public static int findQuadraticNonResidue (int p){
        //TODO: optimize for every integer greater than 2
        int ans = 2;
        while ( ans < p && isQuadraticResidue(ans, p) )
            ans ++;
        return ans;
    }

    // iterative gcd
    public static int gcd (int n, int k){
        int r = n % k;
        while ( r != 0 ){
            n = k;
            k = r;
            r = n % k;
        }
        return k;
    }

    // recursive gcd
    public static int GCD (int n, int k){
        if ( k == 0 )
            return n;
        else
            return GCD(k, n % k);
    }

    public static int[] euclid (int m, int n){
        if ( m < n )
            return euclid(n, m);
        int d = gcd(m, n);
        int[] mArray = {1,0};
        int[] nArray = {0,1};
        int q = m / n;
        int r = m % n;
        if ( r == 0 ){
            if ( m > n )
                return nArray;
            else
                return mArray;
        }
        int[] ans = extractArr(mArray, nArray, q);
        while ( r != d ){
            m = n;
            n = r;
            q = m / n;
            r = m % n;
            mArray = nArray;
            nArray = ans;
            ans = extractArr(mArray, nArray, q);
        }
        return ans;
    }

    protected static int[] extractArr (int[] arr1, int[] arr2, int q){
        int n = Math.min(arr1.length, arr2.length);
        int[] output = new int[n];
        for (int i = 0; i < n ; i++){
            output[i] = arr1[i] - q * arr2[i];
        }
        return output;
    }

    public static int inverseNumber (int n, int p){ // return the inverse number of 'n' in F_p
        int num = euclid(p, n)[1];
        if ( num < 0 )
            num = ( num + p ) % p;
        return num;
    }

    //TODO: euclid for polynomials and inverse-polynomial

}
