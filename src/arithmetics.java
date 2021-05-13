public class arithmetics {

    public static void main(String []args){

        int mod = 11251;
        int x = 7;
        int base = 232;
        int power = 543;
        int r = 1;
        int p = 857;

        printArray(euclid(60, 30));
//        System.out.println(base + "^" + power + " mod " + mod + " equals " + moduloPower(base,power,mod));
//        System.out.println("The least power which perform r^(2^i)=1 mod " + mod + " is " + leastPow(r,mod));
//        System.out.println(isQuadraticResidue(x,mod));
//        System.out.println(isGenerator(1187, 429));
    }

    public static int naiveSqrt (int x, int mod) { //return the square of 'x' modulo 'mod'
        int output = -1;
        for (int i=2; i<mod & output<0; i=i+1) {
            if ( (i * i) % mod == x )
                output = i;
        }
        return output;
    }

    public static int moduloPower (int base, int power, int mod) { //calculates power in finite field
        int ans = 1;
        for (int i=0; i<power; i=i+1) {
            ans = ans * base;
            ans = ans % mod;
        }
        return ans;
    }

    public static int recModuloPower (int base, int power, int mod){ // Recursively calculates power in finite field
        int ans = 1;
        if ( power > 0 ) {
            ans = recModuloPower(base, power / 2, mod);
            ans = (ans * ans) % mod;
            if ( power % 2 == 1 )
                ans = (ans * base) % mod;
        }
        return ans;
    }

    public static int leastPow (int r, int mod) { // returns the least integer, 'i', which perform r^(2^i)=1 mod p
        int i = 0;
        boolean found = false;
        for (int j=0; j<=mod & !found; j++) {
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

    public static boolean isQuadraticResidue (int x, int mod) { //check whether 'x' is a QuadricResidue in finite field
        boolean ans = false;
        int a = recModuloPower(x, (mod-1)/2, mod);
        if ( a == 1 ) {
            ans = true;
        }
        return ans;
    }

    public static boolean isGenerator (int p, int g){ //return true/false rather g is a generator in the multiplicative group Fp
        //TODO: Tests
        boolean ans = true;
        p = p - 1;
        int[] divisors = divisors(p);
        for (int i=0; i<divisors.length && divisors[i] != 0 && ans; i++){
            if ( recModuloPower(g, divisors[i], p) == 1 )
                ans = false;
        }
        return ans;
    }

    private static int[] divisors (int n){ // should be private ??
        int[] array = new int[n/2];
        int counter = 0;
        for (int d=2; d<n; d++){
            if ( n % d == 0 ){
                array[counter] = d;
                counter = counter + 1;
            }
        }
        return array;
    }

    public static void printArray (int[] array){
        System.out.print("{");
        for (int i=0; i<array.length-1; i++){
            System.out.print(array[i] + ",");
        }
        System.out.println(array[array.length-1] + "}");
    }

    public static int findNonQuadraticResidue (int p){ //return an integer that is non-quadratic residue in Zp
        //TODO: Tests
        int ans = 2;
        while ( isQuadraticResidue(ans, p) ){
            ans = (ans + 1) % p;
        }
        return ans;
    }

    public static int gcd (int n, int k){
        int r = n % k;
        while ( r != 0 ){
            n = k;
            k = r;
            r = n % k;
        }
        return k;
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
        for (int i=0; i<n ; i++){
            output[i] = arr1[i] - q * arr2[i];
        }
        return output;
    }

    public static int inverseNumber (int n, int p){ // return the inverse number of 'n' in Zp
        int num = euclid(p, n)[1];
        if ( num < 0 )
            num = ( num + p ) % p;
        return num;
    }

}
