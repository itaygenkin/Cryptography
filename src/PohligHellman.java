import java.util.Scanner;

public class PohligHellman {

    public static void main (String[] args){
        Scanner myScan = new Scanner(System.in);
        int[] polynom1 = {2,1,6,3,5,1,2,1};
        int[] polynom2 = {3,1};
        int[] polynomMod = {6,3,3,3,1};

        int[] pol = dividePolynomials(polynom1, polynomMod, 7);

        arithmetics.printArray(pol);
//        arithmetics.printArray(powerPolynom(polynom1, polynomMod, 15, 7));

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

    public static int[] powerPolynom (int[] pol, int[] polMod, int power, int mod){
        //Todo: might be completed when 'polynomialRemainder' is completed
        int[] ans = {1};
        for (int i=0; i<power; i++){
            ans = multiplyPolynomials(ans, pol, mod);
            polynomialModulo(ans, polMod, mod);
        }
        return reduceSize(ans);
    }

    public static int[] polynomialRemainder (int[] pol, int[] polMod, int mod){
        //Todo: complete
        if ( pol.length >= polMod.length ){
            int index = polMod.length-1;
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
        if ( pol1.length < pol2.length )
            return new int[]{0};
        else if ( pol1.length == pol2.length ){ // It means that the degree of the quotient is 0
            int n = pol1.length-1;
            int a = pol1[n];
            int b = arithmetics.inverseNumber(pol2[n], mod);
            return new int[] { (a * b) % mod };
        }
        else{
            int index = pol1.length - pol2.length;
            int currIndex = 0;
            int[] ans = new int[index + 1];

            while ( index >= 0 ){
                int k = pol1[pol1.length-currIndex-1]; // current coefficient we wish to divide
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
        while ( index > 0 && array[index] == 0 ){ // until index==1 beacuse we don't want an empty array
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