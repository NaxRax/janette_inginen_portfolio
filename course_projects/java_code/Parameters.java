import java.util.*;
public class Parameters {
    public static void main(String[] args){
        // String[] testargs = {"Chad", "Benin", "Angola", "hone" };
        //  "some", "thing", "great", "may", "happen", "true", 
        String longestparameter = "";
        int numberofargs = 0;
        for (String item : args){
            if (item.length() > longestparameter.length()){
                longestparameter = item;
            }
            numberofargs++;
        }
        // System.out.println(longestparameter);
        // System.out.println(numberofargs);
        Arrays.sort(args);
        int len =longestparameter.length();
        boolean argsoverten = false;
        boolean argsoverhundred = false;
        // Luodaan muuttuja c seuraamaan kuinka leveä taulukosta tulee tulostusta varten
        int c = 8;
        if(numberofargs >= 100){
            argsoverhundred = true;
            c = 10;
        }
        else if (numberofargs >= 10){
            argsoverten = true;
            c = 9;
        }
        // Tästä alkaa taulukon tulostus
        for (int j=0 ; j < len+c ; j++){
            System.out.print("#");
        }
        System.out.println();
        // käytetään numeroiden tulostuksessa
        int count = 1;
        // käytetään laskemaan onko menossa taulukkorivi vai tulostusrivi
        int row = 1;
        for (int i=0 ; i < numberofargs*2-1 ; i++){
            System.out.print("#");
            if(row%2==0){
                // Tulostetaan taulukon rakenne rivi
                if (argsoverhundred){
                    System.out.print("-----+");
                }
                else if(argsoverten){
                    System.out.print("----+");
                }
                else{
                    System.out.print("---+");
                }
                
                for (int j=0 ; j < len+2 ; j++){
                    System.out.print("-");
                }
                System.out.println("#");
            }
            else{
                // Tulostetaan rivi joka sisältää listan alkion
                if (argsoverhundred){
                    System.out.format("%4d",count);
                }
                else if (argsoverten){
                    System.out.format("%3d",count);
                }
                else if (count < 10){
                    System.out.format("%2d",count);
                }
                else {
                    System.out.print("what now ");
                }
                System.out.print(" | ");
                int lenx = -len-1;
                System.out.format("%"+lenx+"s", args[count-1]);
                System.out.println("#");
                count++;
            }
            row++;
        }
        for (int j=0 ; j < len+c ; j++){
            System.out.print("#");
        }
    }
}
