package JonoPino;

public class Jono {
	
	// Luodaan muuttujat
	public static final int defaultN = 1000;
	private int N;
	private int first;
	private int rear;
	private int items;
	
	private Object Q[];
	
	// Rakentajat
	
	// Parametritön rakentaja
	public Jono(){
		this(defaultN);
	}
	
	// Parametrillinen rakentaja
	public Jono(int size) {
		first = 0;
		rear = 0;
		items = 0;
		N = size;
		Q = new Object[N];
	}
	
	// Metodit
	
	// Metodi lisää olion jonon loppuun. Heittää virheen, jos jono täynnä.
	public void enqueue(Object o) throws IllegalArgumentException {
		// Suoritetaan, jos jono ei ole täynnä. Muuten heitetään virhe.
		if (items < N) {
			Q[rear] = o;
			rear = (rear + 1) % N;
			items++;
		}
		else {
			throw new IllegalArgumentException("In metodi enqueue() found that Queue is full.");
		}
	}
	
	// Metodi poistaa olion jonon alusta. Heittää virheen, jos jono tyhjä.
	public Object dequeue() throws IllegalArgumentException{
		Object result = null;
		// Suoritetaan, jos jono ei ole tyhjä. Muuten heitetään virhe.
		if (! IsEmpty ())
		{
			result = Q[first];
			Q[first] = null;
			first = (first + 1) % N;
			items--;
		}
		else {
			throw new IllegalArgumentException("In metodi dequeue() found that Queue is empty.");
		}
		return result;
	}
	
	// Tarkistaa onko jono tyhjä.
	public boolean IsEmpty () {
		return items == 0;
	}
	
	// Metodi tulostaa jonon ensimmäisen alkion. Heittää virheen, jos jono tyhjä.
	public Object front() throws IllegalArgumentException {
		Object result = null;
		// Suoritetaan, jos jono ei ole tyhjä. Muuten heitetään virhe.
		if (! IsEmpty ())
		{
			result = Q[first];
		}
		else {
			throw new IllegalArgumentException("In metodi front() found that Queue is empty.");
		}
		return result;  
	}
	
	// Tulostaa jonon koon.
	public int size() {
		return (N-first+rear) % N;
	}
}
