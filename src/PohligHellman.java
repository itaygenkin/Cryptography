import java.util.Scanner;

public class PohligHellman {

    public static void main (String[] args){
        Scanner myScan = new Scanner(System.in);
        int[] polynom1 = {0,4,4,5};
        int[] polynom2 = {3,1};
        int[] polynomMod = {6,3,3,3,1};


        arithmetics.printArray(powerPolynom(polynom1, polynomMod, 15, 7));

        myScan.close();
    }

    public static void polynomialModulo(int[] pol, int[] polMod, int mod){
        if ( pol.length == polMod.length ){
            int reducer = pol[pol.length-1];
            for (int i=0; i<polMod.length; i++){
                pol[i] = pol[i] - reducer * polMod[i];
                while ( pol[i] < 0 )
                    pol[i] = (pol[i] + mod) % mod;
                pol[i] = pol[i] % mod;
            }
        }
    }

    public static int[] multiplyPolynomials(int[] pol1, int[] pol2, int mod){ //multiplying 2 polynomials in finite field
        int[] ans = new int[pol1.length+pol2.length-1];
        for (int i=0; i<ans.length; i++){
            int curr = 0;
            for (int j=0; j<=i; j++){
                if ( j < pol1.length && i-j < pol2.length ){
                    curr = curr + pol1[j] * pol2[i-j];
                    curr = curr % mod;
                }
            }
            ans[i] = curr;
        }
        return reduceSize(ans);
    }

    public static int[] powerPolynom (int[] pol, int[] polMod, int power, int mod){
        //Todo: might be completed when 'polynomialRemainder' is completed
        int[] ans = {1};
        for (int i=0; i<power; i++){
            ans = multiplyPolynomials(ans, pol, mod);
            polynomialModulo(ans, polMod, mod);
        }
        return reduceSize(ans);
    }

    public static int[] reduceSize(int[] array){ //reduce the the length of an array which ends with series of 0's
        int counter = 0;
        int index = array.length - 1;
        while ( array[index] == 0 ){
            counter = counter + 1;
            index = index - 1;
        }
        int[] output = new int[array.length - counter];
        for (int i=0; i<output.length; i++){
            output[i] = array[i];
        }
        return output;
    }

    public static int[] polynomialRemainder (int[] pol, int[] polMod, int mod){
        //Todo: complete
        if ( pol.length >= polMod.length ){
            int index = polMod.length-1;

        }
        return reduceSize(pol);
    }

    public static int[] extractPolynoms (int[] pol1, int[] pol2, int mod){
        int n = Math.min(pol1.length, pol2.length);
        for (int i=0; i<n; i++){
            int curr = (pol1[i]-pol2[i]) % mod;
            pol1[i] = curr;
        }
        return pol1;
    }

    public static int[] dividePolynomials (int[] pol1, int[] pol2, int mod){
        if ( pol1.length < pol2.length )
            return new int[]{0};
        else if ( pol1.length == pol2.length ){
            int n = pol1.length-1;
            return new int[] { pol1[n] / pol2[n] };
        }
        else{
            int index = pol1.length-pol2.length;
            int currIndex = 0;
            int[] ans = new int[pol1.length];
            while ( index >= 0 ){
                ans[index] = pol1[pol1.length-currIndex-1] / pol2[pol2.length-currIndex-1]; //Todo: change it to 'multiply by the inverse number'
            }
        }
        return pol1;
    }

}
/*
    public static int[] powerPolynom (int[] pol, int[] polMod, int power, int mod){
        int[] ans = new int[polMod.length];
        ans[0] = 1;
        if ( power > 0 ){
            ans = powerPolynom(pol, polMod, power / 2, mod);
            ans = multiplyPolynomials(ans, ans, mod);
            polynomialModulo(ans, polMod, mod);
            if ( power % 2 == 1 ){
                ans = multiplyPolynomials(pol, ans, mod);
                polynomialModulo(ans, polMod, mod);
            }
        }
        return reduceSize(ans);
    }
 */

/*
    public static int[] recorrectPolynomialLength (int[] pol, int size){ //return the same polynom with increased array length
        if ( pol.length > size )
            pol = reduceSize(pol);
        if ( pol.length < size ){
            int[] newPol = new int[size];
            for (int i=0; i<pol.length; i++){
                newPol[i] = pol[i];
            }
            return newPol;
        }
        return pol;
    }
 */