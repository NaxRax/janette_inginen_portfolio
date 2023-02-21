//
// Janette Inginen (janette.inginen(at)tuni.fi)
// Laki 2 Harjoitustyö

import java.util.Scanner;
import java.io.*;

public class BimEdit {
   
   // Tarkistaa onko ohjelma kutsuttu oikein ja voiko ohjelman käyttöä jatkaa.
   public static int tarkistaKutsu (String[] kutsu) {
      if (kutsu.length == 2) {
         if (kutsu[1].equals("echo")) {
            return 2;
         }
         tulostaVirheilmoitus(1);
         return 0;
      }
      else if (kutsu.length == 1) {
         return 1;
      }
      else {
         tulostaVirheilmoitus(1);
         return 0;
      }
      
   }
   
   // Tarkistaa onko taulukko oikeanlainen.
   public static boolean tarkistaTaulu( char[][] taulu,  int[] riviSarakelkm, char[] merkit) {
      if (taulu == null) {
         return false;
      }
      if (riviSarakelkm[0] != taulu.length || riviSarakelkm[1] != taulu[0].length){
         return false;
      }
      if (riviSarakelkm[0]>2 && riviSarakelkm[1]>2) {
         // Tarkistetaan, että taulukossa on oikeanlaiset merkit.
         for (int riviInd = 0; riviInd <riviSarakelkm[0]; riviInd++){
            for (int sarakeInd = 0; sarakeInd < riviSarakelkm[1]; sarakeInd++){
               if (taulu[riviInd][sarakeInd] == merkit[0] || taulu[riviInd][sarakeInd] == merkit[1]){
                  // Jos ohjelmaa pääsee tämän iffin sisälle kaikki on hyvin, muuten palautetaan false.
               }
               else {
                  return false;
               }
               
            }
         }
         return true;
      }
      else {
         return false;
      }
      
   }
   
   // Vaihtaa taulukon merkkien paikat.
   public static boolean vaihdaMerkit ( char[][] taulu,  char[] merkit,  int[] riviSarakelkm) {
      
      if (taulu == null) {
         return false;
      }
      if (taulu.length == 0) {
         return false;
      }
      if (taulu[0].length == 0) {
         return false;
      }
      else {
         
         for (int riviInd = 0; riviInd < riviSarakelkm[0]; riviInd++) {
            for (int sarakeInd = 0; sarakeInd < riviSarakelkm[1]; sarakeInd++) {
               if (taulu[riviInd][sarakeInd] == merkit[0]) {
                  taulu[riviInd][sarakeInd] = merkit[1];
               }
               else if (taulu[riviInd][sarakeInd] == merkit[1]) {
                  taulu[riviInd][sarakeInd] = merkit[0];
               }
            }
         }
         return true;
         
      }
      
   }
   
   /* Kun ollaan todettu, että ohjelmaa on kutsuttu oikein tarkistaKutsu-metodilla,
    * kutsutaan lueKomento-metodi ja luetaan käyttäjän komentokehonte. Metodi sitten palauttaa
    * komentoa vastaavan luvun pääohjelmalle.
    */
   public static int[] lueKomento(boolean tosi, String[] syote, int[] riviSarakelkm, int luku) {
      
      int[] palautus = new int[2];
      
      System.out.println("print/info/invert/dilate/erode/load/quit?");
      
      Scanner lukija = new Scanner(System.in);
      String k = lukija.nextLine(); // lyhennetty k = komento
      syote = k.split(" ");
      if (syote.length == 2) {
         palautus[1] = Integer.parseInt(syote[1]);
      }
      // Metodia kutsutaan falsella, jos ohjelmaa on kutsuttu echolla ja
      // halutaan tulostaa komento kahdesti.
      if(!tosi) {
         System.out.println(k);
      }
      // Yksiosainen syöte voi olla vain yksi komennoista, mutta ei dilaatio tai eroosio.
      if (syote.length == 1 && k.equals("print")){
         palautus[0] = 1;
         return palautus;
      }
      else if (syote.length == 1 && k.equals("info")){
         palautus[0] = 2;
         return palautus;
      }
      else if (syote.length == 1 && k.equals("invert")){
         palautus[0] = 3;
         return palautus;
      }
      // Syötteen on oltava kaksiosainen, jos halutaan dilaatiota tai eroosiota.
      else if (syote.length == 2 && (syote[0].equals("dilate") || syote[0].equals("erode"))){
         luku = Integer.parseInt(syote[1]);
         if (luku >= 3 && luku % 2 == 1 && luku <= riviSarakelkm[0] && luku <= riviSarakelkm[1]) {
            if (syote[0].equals("dilate")){
               palautus[0] = 4;
               return palautus;
            }
            else {
               palautus[0] = 5;
               return palautus;
            }
         }
         else {
            palautus[0] = 0;
            return palautus;
         }
         
      }
      else if (syote.length == 1 && k.equals("load")){
         palautus[0] = 6;
         return palautus;
      }
      else if (syote.length == 1 && k.equals("quit")){
         // lukija.close();
         palautus[0] = 7;
         return palautus;
      }
      // Jos komentokehotetta ei löydy, palautetaan pääohjelmalle 0, joka tarkoittaa, että
      // komentokehote on syötetty väärin ja pääohjelmasta kutsutaan virheilmoitus metodia.
      else {
         palautus[0] = 0;
         return palautus;
      }
      
   }
   
   
   /* Jos jokin metodi löytää virheen, kutsutaan tulostaVirheilmoitusmetodia virhekoodilla 
    * ja tulostetaan virheilmoitus.
    */
   public static void tulostaVirheilmoitus(int virhekoodi) {
      // oli hyödyllinen ohjelman alkuvaiheessa
      if (virhekoodi == 0){
         System.out.println("ei virhettä");
      } 
      // virhekoodi 1 eli ohjelmaa on kutsuttu väärin ja ohjelma pysäytetään
      else if (virhekoodi == 1) {
         tervehdi(false);
         System.out.println("Invalid command-line argument!");
         System.out.println("Bye, see you soon.");
      }
      // virhekkodi 2 eli kuvatiedostossa on jonkinlainen virhe ja ohjelma pysäytetään
      else if (virhekoodi == 2) {
         tervehdi(false);
         System.out.println("Invalid image file!");
         System.out.println("Bye, see you soon.");
      }
      // virhekoodi 3 eli komentokehote on syötetty väärin
      else if (virhekoodi == 3){
         System.out.println("Invalid command!");
      }
      else {
         
      }
      
   }
   
   public static boolean tervehdi(boolean totuusarvo) {
      // Tervehditään käyttäjää.
      System.out.println("-----------------------");
      System.out.println("| Binary image editor |");
      System.out.println("-----------------------");
      
      if (totuusarvo) {
         return true;
      }
      return false;
   }
   
   /* Saa pääohjelmalta taulukon ja tulostaa sen rivit ja sarakkeet oikein. 
    * Jos taulukko on null, metodi kutsuu tulostaVirheilmoitus metodia.
    */
   public static void tulosta2d (char[][] taulu, int[] riviSarakelkm) {
      if (taulu != null) {
         
         for (int rivi = 0; rivi < riviSarakelkm[0]; rivi++){
            for (int sarake = 0; sarake < riviSarakelkm[1]; sarake++) {
               
               System.out.print(taulu[rivi][sarake]);
            }
            System.out.println();
         }
         
      }
      else {
         // väliaikainen virhekoodi !!!!!
         System.out.println("virhe tulosta2d");
      }
      
   }
   
   // Toteuttaa info komennon. Tulostaa rivien ja sarakkeiden määrän ja 
   // laskee molempien merkkien lukumäärät.
   public static void infoa (char[][] taulu, char[] merkit, int[] riviSarakelkm){
      // alkuarvot merkkien lukumäärille
      int merkki1lkm = 0;
      int merkki2lkm = 0;
      // tulostetaan rivien ja sarakkeiden määrä
      System.out.println(riviSarakelkm[0] + " x " + riviSarakelkm[1]);
      
      // Käydään jokainen merkki läpi. Jos se on taustaväri se lisätään siihen laskuriin ja 
      // jos merkki on edustaväri se lisätään toiseen laskuriin.
      for (int rivi = 0; rivi < riviSarakelkm[0]; rivi++){
         for (int sarake = 0; sarake < riviSarakelkm[1]; sarake++) {
            if (taulu[rivi][sarake] == merkit[0]) {
               merkki1lkm = merkki1lkm+1;
            }
            else if (taulu[rivi][sarake] == merkit[1]) {
               merkki2lkm = merkki2lkm+1;
            }
         }           
      }
      // Merkit ja niiden lukumäärät tulostetaan.
      System.out.println(merkit[0] + " " + merkki1lkm);
      System.out.println(merkit[1] + " " + merkki2lkm);
      
   }
   
   // 
   public static char[][] muunnaTekstiTauluksi(String tiedostonNimi, char[] värit, int[] riviSarakeLkm) {
      
      char[][] taulu = null;
      Scanner tiedostonLukija = null;

      // Kokeillaan onnistuuko taulukon luominen tekstitiedostosta, jos ei, siirrytään catchiin.
      try {
         File tiedosto = new File(tiedostonNimi);
         
         tiedostonLukija = new Scanner(tiedosto);
         
         int rivlkm = Integer.parseInt(tiedostonLukija.nextLine());
         int sarlkm = Integer.parseInt(tiedostonLukija.nextLine());
         
         riviSarakeLkm[0] = rivlkm;
         riviSarakeLkm[1] = sarlkm;
         
         if (rivlkm >= 3 && sarlkm >= 3 && värit.length == 2) {
            
            taulu = new char[rivlkm][sarlkm];
            
            värit[0] = tiedostonLukija.nextLine().charAt(0);
            värit[1] = tiedostonLukija.nextLine().charAt(0);
            
            int rivind = 0;

            // Luetaan kuvan merkkejä riveittäin ja sijoitetaan merkit tauluun.
            while (tiedostonLukija.hasNextLine()) {
               String rivi = tiedostonLukija.nextLine();
               
               int sarind = 0;
               while (sarind < sarlkm) {
                  taulu[rivind][sarind] = rivi.charAt(sarind);
                  sarind++;
               }
               rivind++;
            }
            tiedostonLukija.close();
         }
      }
      // Jos taulukon luominen ei onnistunut nollataan taulukko ja kutsutaan virheilmoitus metodia.
      catch (Exception e) {
         taulu = null;
         if (tiedostonLukija != null) {
            tiedostonLukija.close();
         }
      }
      return taulu;
   }
   
   // Metodi suorittaa dilaatio tai eroosio komennon. 
   public static void dilaatioEroosio (boolean tosi, char[][] taulu, char[] merkit, int[] riviSarakelkm, int luku) {
      // Luodaan taulu, jota muokataan, jotta vanha taulu jäisi sellaisekseen.
      char[][] vanhaTaulu = new char[taulu.length][taulu[0].length];
      
      vanhaTaulu = kopioi2dTaulu(taulu);
      //tulosta2d(vanhaTaulu, riviSarakelkm);
      int ind = (luku-1)/2;
      char merkki1 = 0;
      if (tosi) {
         merkki1 = merkit[1];
      }
      else {
         merkki1 = merkit[0];
      }
      
      for (int indeksi=ind; indeksi < vanhaTaulu.length-ind; indeksi++) {
         for (int jindeksi=ind; jindeksi < vanhaTaulu[0].length-ind; jindeksi++) {
            // 
            for (int i = indeksi-ind; i < indeksi+ind+1; i++) {
               for (int j = jindeksi-ind; j < jindeksi+ind+1; j++) {
                  if (vanhaTaulu[i][j] == merkki1){
                     taulu[indeksi][jindeksi] = merkki1;
                     
                  }
               }
            }
            
         }
      }
      
   }
   
   //
   // Saattaa olla turha metodi!?
   //
   public static char[][] kopioi2dTaulu(char[][] vanhaTaulukko) {
      // Luodaan muuttujat taulukon rivien ja sarakkeiden määrälle.
       int rivit = vanhaTaulukko.length;
       int sarakkeet = vanhaTaulukko[0].length;
      // Luodaan tyhjä taulukko.
       char[][] uusiTaulukko = new char[rivit][sarakkeet]; 
      // Kopioidaan taulukko.
      for (int rivi = 0; rivi < rivit; rivi++) {
         for (int sarake = 0; sarake < sarakkeet; sarake++) {
         uusiTaulukko[rivi][sarake] = vanhaTaulukko[rivi][sarake];
         }
      }
      // Palautetaan valmis taulu.
      return uusiTaulukko;
   }
   
   
   
   
   public static void main (String[] args) {
      
      boolean jatketaan = true;
      boolean echo = false;
      char[][] kuvaTaulu = null;
      // luodaan taulu värejä kuvaavia merkkejä varten
      char[] varit = new char[2]; // ois varmaan pitänyt nimetä merkeiksi
      // luodaan taulu rivi ja sarakelukumääriä varten
      int[] riviSarakelkm = new int[2];
      String[] syote = new String[2];
      boolean tarkistettu = false;
      boolean dilaatioTosi = false;
      boolean tervehditty = false;
      int luku = 0;
      int[] komento = new int[2];
      int tarkistusInd = 0;
      int invertInd = 1;
      tarkistusInd = tarkistaKutsu(args);
      if (tarkistusInd == 0){
         jatketaan = false;
      }
      else {
         if (tarkistusInd == 2) {
            echo = true;
         }
         // saadaan tekstitiedostosta taulu kuvalle
         kuvaTaulu = muunnaTekstiTauluksi(args[0], varit, riviSarakelkm);
         tarkistettu = tarkistaTaulu(kuvaTaulu, riviSarakelkm, varit);
         if (kuvaTaulu == null || !tarkistettu){
            tulostaVirheilmoitus(2);
            jatketaan = false;
         }
         else{
            tervehditty = tervehdi(true);
         }
         
         if (tervehditty) {
            
         }
         else {
            jatketaan = false;
         }
      }
      while (jatketaan) {
         if (echo) {
            komento = lueKomento(false, syote, riviSarakelkm, luku);
         }
         else {
            komento = lueKomento(true, syote, riviSarakelkm, luku);
         }
         luku = komento[1];
         // Jos lueKomento-metodi epäonnistui ja palauttaa arvon null 
         // tämä if on varmuuden vuoksi avuksi tunnistamaan tällainen tilanne
         if (komento[0] == 8) { // Jos lueKomento-metodin arvo on null, paluuarvo on 8
            System.out.println("null");
         }
         else if (komento[0] == 0){ // 0 eli virhe
            tulostaVirheilmoitus(3);
         }
         else if (komento[0] == 1){ // 1 eli print
            tulosta2d(kuvaTaulu, riviSarakelkm);
         }
         else if (komento[0] == 2){ // 2 eli info
            infoa(kuvaTaulu, varit, riviSarakelkm);
         }
         else if (komento[0] == 3){ // 3 eli invert
            vaihdaMerkit(kuvaTaulu, varit, riviSarakelkm);
            if (invertInd == 1) {
               invertInd = 2;
            }
            else {
               invertInd = 1;
            }
         }
         else if (komento[0] == 4 || komento[0] == 5){ // 4 eli dilate ja 5 eli erode
            if (invertInd == 1){
               dilaatioTosi = false;
            }
            else {
               dilaatioTosi = true;
            }
            if (komento[0] == 4) {
               if (invertInd == 1) {
                  dilaatioTosi = true;
               }
               else{
                  dilaatioTosi = false;
               }
            }
            dilaatioEroosio(dilaatioTosi, kuvaTaulu, varit, riviSarakelkm, luku);
         }
         else if (komento[0] == 6) { // 6 eli load
            kuvaTaulu = muunnaTekstiTauluksi(args[0], varit, riviSarakelkm);
            invertInd = 1;
         }
         else if (komento[0] == 7){ // 7 eli quit
            System.out.println("Bye, see you soon.");
            jatketaan = false;
         }
         
         
      }
      
   }
}

