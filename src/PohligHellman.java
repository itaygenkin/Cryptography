import java.util.Scanner;

public class PohligHellman {

    public static void main (String[] args){
        Scanner myScan = new Scanner(System.in);
        int[] polynom1 = {2,6,1};
        int[] polynom2 = {3,1,3,6};
        int[] polynomMod = {6,3,3,3,1};

        int[] pol = powerPolynomial(polynom1, polynomMod, 5, 7);

        arithmetics.printArray(pol);

        myScan.close();
    }

    public static int[] multiplyPolynomials(int[] pol1, int[] pol2, int mod){ // Multiplying 2 polynomials in finite field
        int n = pol1.length + pol2.length - 1;
        int[] ans = new int[n];
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

    public static int[] powerPolynomial (int[] pol, int[] polMod, int power, int mod){
        int[] ans = {1};
        for (int i=1; i <= power ; i++){
            ans = multiplyPolynomials(ans, pol, mod);
            polynomialRemainder(ans, polMod, mod);
            positiveDisplay(ans, mod);
            ans = reduceSize(ans);
        }
        return reduceSize(ans);
    }

    public static int[] polynomialRemainder (int[] pol, int[] polMod, int mod){
        if ( pol.length >= polMod.length ){
            int[] temp = dividePolynomials(pol, polMod, mod);
            temp = multiplyPolynomials(temp, polMod, mod);
            return extractPolynomials(pol, temp, mod);
        }
        return reduceSize(pol);
    }

    public static int[] extractPolynomials(int[] pol1, int[] pol2, int mod){
        int n = Math.min(pol1.length, pol2.length);
        for (int i=0; i < n ; i++ ){
            int curr = ( pol1[i]-pol2[i] ) % mod;
            pol1[i] = curr;
        }
        return reduceSize(pol1);
    }

    public static int[] dividePolynomials (int[] pol1, int[] pol2, int mod){
        if ( mod < 2 )
            throw new IllegalArgumentException("modulo must be greater than 1");
        // validating that the polynomials are well defined
        pol1 = reduceSize(pol1);
        positiveDisplay(pol1, mod);
        pol2 = reduceSize(pol2);
        positiveDisplay(pol2, mod);

        if ( pol1.length < pol2.length ) // it means that deg('pol1') < deg('pol2') so the quotient will be zero
            return new int[]{0};
        else{
            int index = pol1.length - pol2.length;
            int currIndex = 0;
            int[] ans = new int[index + 1];

            while ( index >= 0 ){
                int k = pol1[pol1.length-1]; // current coefficient we wish to divide
                int m = pol2[pol2.length-1]; // the leading coefficient divisor
                m = arithmetics.inverseNumber(m, mod); // the inverse number of 'm'
                ans[index] = (k * m) % mod;
                int[] temp = new int[index+1]; // 'temp' is the 'current quotient'
                temp[index] = ans[index];
                temp = multiplyPolynomials(pol2, temp ,mod);
                pol1 = extractPolynomials(pol1, temp, mod);
                positiveDisplay(pol1, mod); // we would rather positive-display of the coefficient of the polynomial
                index = index - 1;
                currIndex = currIndex + 1;
            }
            return ans;
        }
    }

    public static int[] reduceSize(int[] array){ //reduce the the length of an array which ends with series of 0's
        int counter = 0;
        int index = array.length - 1;
        while ( index > 0 && array[index] == 0 ){ // until index==1 because we don't want an empty array
            counter = counter + 1;
            index = index - 1;
        }
        int[] output = new int[array.length - counter];
        for (int i=0; i<output.length; i++){
            output[i] = array[i];
        }
        return output;
    }

    public static void positiveDisplay (int[] pol, int mod){ // verifying that a polynomial is well displayed
        for (int i=0; i<pol.length; i++){
            while ( pol[i] < 0 )
                pol[i] = pol[i] + mod;
            pol[i] = pol[i] % mod;
        }
    }

}
