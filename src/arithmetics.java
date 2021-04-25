public class arithmetics {

    public static void main(String []args){

        int mod = 5;
        int x = 7;
        int base = 3;
        int power = 3;
        int r = 1;
        int p = 1;


//        System.out.println(moduloPower(base,power,mod));
//        System.out.println(base + "^" + power + " mod " + mod + " equals " + moduloPower(base,power,mod));
//        System.out.println("The least power which perform r^(2^i)=1 mod " + mod + " is " + leastPow(r,mod));
//        System.out.println(isQuadricResidue(x,mod));
        System.out.println(isGenerator(1187, 429));
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

    public static boolean isQuadricResidue (int x, int mod) { //check whether 'x' is a QuadricResidue in finite field
        boolean ans = false;
        int a = moduloPower(x, (mod-1)/2, mod);
        if ( a == 1 ) {
            ans = true;
        }
        return ans;
    }

    public static boolean isGenerator (int p, int g){ //return true/false rather g is a generator in the multiplying group Fp
        boolean ans = true;
        p = p - 1;
        int[] divisors = divisors(p);
        for (int i=0; i<divisors.length && divisors[i] != 0 && ans; i++){
            if ( moduloPower(g, divisors[i], p) == 1 )
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

}
