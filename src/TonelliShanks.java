public class TonelliShanks {

    public static void main (String[] args){
        System.out.println(ShanksAlgorithm(127, 30));
    }

    //return one of the quadratic residues of 'a' modulo 'p' (for the second residue compute p-<return value>)
    public static int ShanksAlgorithm (int p, int a){
        int n = evenOdd(p)[0];
        int k = evenOdd(p)[1];
        int t = Arithmetics.recModuloPower(a, (k+1)/2, p);
        int r = Arithmetics.recModuloPower(a, k, p);
        int i = Arithmetics.leastPow(r, p);
        int q = Arithmetics.findQuadraticNonResidue(p);
        while ( i != 0 ){
            int power = (int) (k * (Math.pow(2,n-i-1)));
            int u = Arithmetics.recModuloPower(q, power, p);
            t = (t * u) % p;
            r = (((r * u) % p) * u) % p;
            i = Arithmetics.leastPow(r, p);
        }
        if ( t == 1 )
            return p-1;
        return t;
    }

    public static int[] evenOdd (int p){ // return 'n' and 'k',respectively, s.t p-1=k*2^n
        if ( p <= 2 )
            throw new IllegalArgumentException("'p' is too small");
        int[] output = new int[2];
        p = p - 1;
        while ( p % 2 == 0 ){
            output[0] = output[0] + 1;
            p = p / 2;
        }
        output[1] = p;
        return output;
    }

}
