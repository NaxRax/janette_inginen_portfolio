package JonoPino;

public class PainotettuGraafi {
    public class Kaari {
        int koordinaattiX;
        int koordinaattiY;
        int nykIntEro;
        
        // tyhjä rakentaja
        public Kaari() {
        	
        }
        
        // Rakentaja
        public Kaari(int koordinaattiX, int koordinaattiY, int nykIntEro) {
            this.koordinaattiX = koordinaattiX;
            this.koordinaattiY = koordinaattiY;
            this.nykIntEro = nykIntEro;
        }
        
        // palauttaa kaaren tiedot
        public int[] haeTiedot() {
        	int[] tiedot = {koordinaattiX, koordinaattiY, nykIntEro};
        	return tiedot;
        }
    }

    public class Graafi {
        int solmut;
        Jono vierekkyyslista;
        
        // Rakentaja
        public Graafi(int solmut) {
            this.solmut = solmut;
            vierekkyyslista = new Jono(solmut);
            
        }
        
        // Luo uuden kaaren, lisää sen meidän vierekkyyslista jonoon ja palauttaa kaaren.
        public Kaari lisaaKaari(int koordinaattiX, int koordinaattiY, int nykIntEro) {
            Kaari edge = new Kaari(koordinaattiX, koordinaattiY, nykIntEro);
            vierekkyyslista.enqueue(edge);
            return edge;
        }
        
        // palauttaa graafin viimeiseksi lisätyn kaaren tiedot
        public int[] haeKaari(){
        	
        	Kaari kaariK = (Kaari)vierekkyyslista.front();
        	return kaariK.haeTiedot();
        	
        	
        }
    }
}
