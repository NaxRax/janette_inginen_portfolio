import java.io.FileInputStream;
import java.io.*;
import java.util.*;
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;  
import javafx.scene.image.PixelReader; 
import javafx.scene.image.PixelWriter; 
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color; 
import javafx.scene.image.ImageView; 
import javafx.stage.Stage;  
import javafx.scene.control.Button;
import javafx.scene.control.TextField; 
import javafx.scene.text.Text; 
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.event.ActionEvent;
import java.util.Random;
import java.io.FileWriter; 
import java.io.IOException;
import JonoPino.*;
//Tira 2020 harjoitustyön pohja. 
//Muokattu https://www.tutorialspoint.com/javafx/index.htm esimerkeistä.

public class project2020 extends Application {  
   Button button1;
   Button button2;
   Button button3;
   Button button4;
   Image image;
   WritableImage wImage;
   double width;
   double height;
   // Lisättiin tänne kaikenlaisia muuttujia, ettei niitä tarvi joka kerta tuoda tai hakea jostain.
   int hiirenX;
   int hiirenY;
   int intEro;
   int kokEro;
   int[][] greyscale;
   ImageView imageView1;
   ImageView imageView2;
   @Override 
   public void start(Stage stage) throws FileNotFoundException {         
      //Creating an image 	  	  
       image= new Image(new FileInputStream("Testikuva.jpg"));  
       width=image.getWidth();
	   height=image.getHeight();	   
      //Setting the image view 1 
       imageView1 = new ImageView(image); 
      
      //Setting the position of the image 
	  //HUOM! Tämä vaikuttaa hiiren koordinaatteihin kuvassa.
      imageView1.setX(50); 
      imageView1.setY(25); 
      
      //setting the fit height and width of the image view 
      imageView1.setFitHeight(height/2); 
      imageView1.setFitWidth(width/2);         
      
      //Setting the preserve ratio of the image view 
      imageView1.setPreserveRatio(true); 
         
      //Setting the image view 2 
      imageView2 = new ImageView(image);
      
      //Setting the position of the image 
      imageView2.setX(width/2+60); 
      imageView2.setY(25); 
      
      //setting the fit height and width of the image view 
      imageView2.setFitHeight(height/2); 
      imageView2.setFitWidth(width/2);          
      
      //Setting the preserve ratio of the image view 
      imageView2.setPreserveRatio(true);          
      int delta=50;
	  Text text1 = new Text("Anna sallittu intensiteettiero");  
	  text1.setLayoutX(50);
	  text1.setLayoutY(height/2+delta);
	  TextField textField1 = new TextField(); 
	  textField1.setText("5");
	  textField1.setLayoutX(50);
	  textField1.setLayoutY(height/2+1.3*delta);
	  
	  Text text2 = new Text("Anna sallittu kokonaisero");  
	  text2.setLayoutX(50);
	  text2.setLayoutY(height/2+2.8*delta);
	  TextField textField2 = new TextField();
	  textField2.setText("5");
	  textField2.setLayoutX(50);
	  textField2.setLayoutY(height/2+3.1*delta);
	  
      button1 = new Button("Hae yksi komponentti syvyyshaulla");
	  button1.setLayoutX(50);
	  button1.setLayoutY(height/2+4*delta);
	  
	  button2 = new Button("Hae yksi komponentti leveyshaulla");
	  button2.setLayoutX(50);
	  button2.setLayoutY(height/2+4.8*delta);
	  
	  button3 = new Button("Hae yhden komponentin minimiVPuu");
	  button3.setLayoutX(50);
	  button3.setLayoutY(height/2+5.5*delta);
	  
	  button4 = new Button("Hae kaikki komponentit");
	  button4.setLayoutX(50);
	  button4.setLayoutY(height/2+6.2*delta);
	  
	  EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { //Luetaan tekstikenttien tiedot.
			  String txt1=textField1.getText();
			  String txt2=textField2.getText();
			  
			  intEro = Integer.parseInt(txt1);
			  kokEro = Integer.parseInt(txt2);
			  
			  //Valitaan suoritettava tehtävä.
			  if(e.getSource()==button1)
                Syvyyshaku(); 
			  if(e.getSource()==button2)
				  Leveyshaku();
			  if(e.getSource()==button3)
				  MinVPuu();			  
			  if(e.getSource()==button4)
				  Kaikki();
				System.out.println("Eka teksti " + txt1);
				System.out.println("Toka teksti "+ txt2);
				
            } 
        }; 
	  
	  button1.setOnAction(event);
	  button2.setOnAction(event);
	  button3.setOnAction(event);
	  button4.setOnAction(event);
	  
	  EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
         @Override 
         public void handle(MouseEvent e) {             
            int hX = (int)e.getX();
	        int hY = (int)e.getY();
	        // Rekalibroitiin meidän hiiren klikkauksen x ja y
	        hiirenX = hX *2 -100;
	        hiirenY = hY *2 -50;
			System.out.println("Hiiren klikkaus rivilla "+ hiirenY+ " ja sarakkeella "+ hiirenX);
          //HUOM! Näkyvä kuvan korkeus ja leveys on puolet varsinaisesta kuvasta.
		//	Lisäksi näkyvän kuvan vasen yläreuna on kohdassa(50,25).
		//Kuvassa tarvitaan kokonaislukuja.
         } 
      };  
	  
	  imageView1.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
      //Creating a Group object  
      Group root = new Group(imageView1, imageView2, text1, textField1, 
	  text2, textField2,button1,button2,button3,button4);  
      
      //Creating a scene object 
      Scene scene = new Scene(root, width+100, height*0.85);  
      
      //Setting title to the Stage 
      stage.setTitle("Harkka 2020");  
      
      //Adding scene to the stage 
      stage.setScene(scene);  
      
      //Displaying the contents of the stage
      stage.show(); 
   }  
   //Kuvan piirtämistä varten.
   public void ManipulateImage()
   {
	   
	  wImage = new WritableImage((int)width, (int)height);	   
      PixelReader pixelReader = image.getPixelReader();      
      PixelWriter writer = wImage.getPixelWriter();  
	  Random rand = new Random();
	  int rval=rand.nextInt(50);
	  rval=rval+15;
	  // luodaan harmaasävytaulu
	  int[][] greyscaleArray = new int[(int)width][(int)height];
	  //Reading through the image. 
      for(int y = 0; y < height; y++) { 
         for(int x = 0; x < width; x++) { 
            //Retrieving the color of the pixel of the loaded image   
            Color color = pixelReader.getColor(x, y); 
              
            //Setting the color to the writable image 
            //writer.setColor(x, y, color.darker()); 
			//Pelkkä esimerkki yksittäisen kuvapisteen käsittelyä varten.
          //if(Math.abs(x-y)<rval)
		  { 			
	        int r = (int) Math.round(color.getRed() * 255);
            int g = (int) Math.round(color.getGreen() * 255);
            int b = (int) Math.round(color.getBlue() * 255);
			int grayscale=(int) Math.round(0.3*r+0.59*g+0.11*b);
			// lisätään harmaasävyarvo 
			greyscaleArray[x][y] = grayscale;
			
			// System.out.println("Harmaasavy arvo kuvapisteelle on "+grayscale);
			Color vari = Color.rgb(grayscale, grayscale, grayscale);
			//writer.setColor(x, y, vari);
		  }
         }
         
         greyscale = greyscaleArray;
      }	
	  imageView2.setImage(wImage);
	  
	  
   }
  
   // Värittää leveyshaussa määritetyn aluuen siniseksi.
   public void piirra(boolean[][] kaydyt) {
	   
	   PixelWriter writer = wImage.getPixelWriter();
	   
	   for(int n = 0; n < height; n++) { 
	         for(int m = 0; m < width; m++) { 
	             // Käydyt taulu sisältää totuusarvon siinä kohdassa missä oli pikseli, joka sopi meidän hakuun.
	        	 // Jos taulusta löytyy tällainen kohta, joka sopi meidän hakuun niin se väritetään siniseksi.
	        	 if (kaydyt[n][m] == true) {
	        		 writer.setColor(n, m, Color.BLUE);
	        		 
	        	 }
				
			  }
	   }
   }
   
   
   public void Syvyyshaku()
   {
	   ManipulateImage();
   }
   
   public void Leveyshaku()
   {
	   ManipulateImage();
	   // Luodaan Graafi
	   PainotettuGraafi PG = new PainotettuGraafi();
	   // Graafin kooksi laitetaan kuvan pikselimäärä
	   PainotettuGraafi.Graafi valmisG = PG.new Graafi((int)height*(int)width);
	   // Lisätään lähtöpisteen eli hiirenklikkauksen koordinaatit valmiiseen Graafiin.
       valmisG.lisaaKaari((int)hiirenX,(int)hiirenY,0);
       
       
       // Luodaan tarvittavat muuttujat.
       boolean jatketaan = true;
       boolean kayLapi = true;
       int x = hiirenX;
       int y = hiirenY;
       // säilytetty edellisillä kierroksilla kertynyt int ero
       int nykIntEro = 0;
       // käytetään lisäämään kaareen päivittynyt int ero
       int nykIntEro2 = 0;
       
       // luodaan boolean taulu, jonka avulla tarkistetaan mitkä pisteet on jo käyty läpi
       boolean[][] kaydyt = new boolean [(int)height][(int)width];
       
       // Luodaan väliaikaiset jonot J ja temp2. Jonoon J lisätään meidän hiiren klikkauksen koordinaatit (x,y).
  	   Jono J = new Jono((int)height*(int)width);
  	   // Säilytettävät tiedot ovat x-koordinaatti, y-koordinaatti ja kerrytetty intensiteetti ero eli nykIntEro, joka on alussa 0.
  	   int[] solmu = {x, y, 0};
  	   J.enqueue(solmu);
  	   
  	   Jono temp2 = new Jono((int)height*(int)width);
       
	   
	   // Aloitetaan syvyyshaku.
	   while (jatketaan) {
		   // Siirretään J jonon oliot temp 2 jonoon ja tyhjennetään J jono, jotta sinne voi lisätä meidän uudet 
		   while(!J.IsEmpty()) {
			   temp2.enqueue(J.front());
			   J.dequeue();
		   }
		   
		   kayLapi = true;
		   
		   // Ensimmäisellä kierroksella käydään läpi hiiren klikkauksen koordinaatti pisteen ympäröivät 8 pistettä.
		   // Muuten käydään läpi viime kierroksella jonoon lisättyjen pisteiden ympäröiviä pisteitä.
      	   while(kayLapi) { 
      		   	// Luodaan tällä kierroksella käytävän pisteen tiedoille muuttujat ja poistetaan piste(kaari) jonosta.
      			 int[] kierros = (int[])temp2.dequeue();
      			 // nykyinen kertynyt intensiteettiero
      			 nykIntEro = kierros[2];
      			 // (x,y) piste
    	         x = kierros[0];
    	         y = kierros[1];
      			       		   
      		 
      		     // Jokaisessa ifissä tarkistetaan, sopiiko piste meidän hakuun.
    	         // Ensimmäiseksi tarkistetaan että piste on osa greyscalea eikä mene taulun "rajojen" yli.
	        	 if (x-1 >= 0 && y-1 >= 0){
	        		 // Päivitetään nykyistä intensiteetti eroa. Säilytetään vanha ja lisätään siihen uusi int ero.
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x-1][y-1]);
	        		 // Tarkistetaan, että kaikki intensiteettierot on sallittuja ja että pistettä ei ole vielä käyty läpi.
	        		 if ((greyscale[x][y] - greyscale[x-1][y-1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x-1][y-1]) {
	        			 // Luodaan uusi väliaikaine kaari.
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 // Luodaan kaari meidän lopulliseen graafiin ja tallennetaan sen tiedot meidän väliaikaiseen kaareen. 
	        			 // LisaaKaari-metodi palauttaa kaari tyyppisen olion.
	        			 kaari = valmisG.lisaaKaari(x-1, y-1, nykIntEro2);
	        			 // Käytetään tätä väliaikaista kaarta hakemaan meidän juuri luomamme kaaren tiedot taulukkoon.
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 // Lisätään kaari tietojen taulukko meidän väliaikaiseen jonoon.
	        			 J.enqueue(kTiedot);
	        			 // Merkitään, että tämä piste on käyty läpi ja siihen on piirretty kaari.
	        			 kaydyt[x-1][y-1] = true;
	        			 
	        		
	        		 }
	        	 }
	        	 
	        	 if (y-1 >= 0){
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x][y-1]);
	        		 if ((greyscale[x][y] - greyscale[x][y-1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x][y-1]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x, y-1, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x][y-1] = true;
	        		 }
	        	 }
	        	 
	        	 if (x+1 < width && y-1 >= 0){
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x+1][y-1]);
	        		 if ((greyscale[x][y] - greyscale[x+1][y-1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x+1][y-1]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x+1, y-1, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x+1][y-1] = true;
	        		 }
	        	 }
	        	 
	        	 if (x-1 >= 0){
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x-1][y]);
	        		 if ((greyscale[x][y] - greyscale[x-1][y]) < intEro && nykIntEro2 < kokEro && !kaydyt[x-1][y]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x-1, y, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x-1][y] = true;
	        		 }
	        	 }
	        	 
	        	 if (x+1 < width){
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x+1][y]);
	        		 if ((greyscale[x][y] - greyscale[x+1][y]) < intEro && nykIntEro2 < kokEro && !kaydyt[x+1][y]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x+1, y, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x+1][y] = true;
	        		 }
	        	 }
	        	 
	        	 if (x-1 >= 0 && y+1 < height) {
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x-1][y+1]);
	        		 if ((greyscale[x][y] - greyscale[x-1][y+1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x-1][y+1]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x-1, y+1, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x-1][y+1] = true;
	        		 }
	        	 }
	        	 
	        	 if (y+1 < height) {
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x][y+1]);
	        		 if ((greyscale[x][y] - greyscale[x][y+1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x][y+1]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x, y+1, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x][y+1] = true;
	        		 }
	        	 }
	        	 
	        	 if (x+1 < width && y+1 < height) {
	        		 nykIntEro2 = nykIntEro + Math.abs(greyscale[x][y] - greyscale[x+1][y+1]);
	        		 if ((greyscale[x][y] - greyscale[x+1][y+1]) < intEro && nykIntEro2 < kokEro && !kaydyt[x+1][y+1]) {
	        			 PainotettuGraafi.Kaari kaari = PG.new Kaari();
	        			 kaari = valmisG.lisaaKaari(x+1, y+1, nykIntEro2);
	        			 int[] kTiedot = kaari.haeTiedot();
	        			 J.enqueue(kTiedot);
	        			 kaydyt[x+1][y+1] = true;
	        		 }
	        	 }
	        	 // Tarkistetaan, että ollaan käyty kaikki aiemman kierroksen pisteet läpi.
	        	 if (temp2.IsEmpty()) {
	     			   kayLapi = false;
	     			   
	     		   }
	        	 
	         }
      	   // Jos molemmat jonot ovat tyhjiä eli kaikki aikaisemman kierroksen pisteet on käyty läpi JA yksikään uusi piste ei kelvannut hakuun niin
      	   // lopetamme syvyyshaun.
      	   if (J.IsEmpty() && temp2.IsEmpty()) {
			   jatketaan = false;
			   
		   }
      	   // Piirretään hakuun kelvannut alue.
	       piirra(kaydyt); 
	         
	   }
	   
	   
	   
   }
   
   public void MinVPuu()
   {
	  ManipulateImage();
   }
   
   public void Kaikki()
   {
	  try {
      FileWriter myWriter = new FileWriter("Graafi.txt");
	  for(int i=0; i<10; i++)
      myWriter.write("Rivi"+String.valueOf(i)+"\n");
      myWriter.close();
      
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
     
   }
   public static void main(String args[]) { 
      launch(args); 
   } 
}   
