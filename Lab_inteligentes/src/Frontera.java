import java.util.PriorityQueue;

public class Frontera {
	private PriorityQueue<Nodo> p_nodos;
	private int max_nodos;

	public Frontera() {
		p_nodos = new PriorityQueue<Nodo>();
		max_nodos = 100000;
	}

	public Nodo peek() {
		return p_nodos.peek();
	}

	public void push(Nodo nodo){
		p_nodos.add(nodo);
	}

	public Nodo pop(){
		return p_nodos.remove();
	}

	public boolean EsVacia(){
		return p_nodos.isEmpty();
	}

	public PriorityQueue<Nodo> getFrontera() {
		return p_nodos;
	}

	public int getMax_nodos() {
		return max_nodos;
	}

	public void setMax_nodos(int max_nodos) {
		this.max_nodos = max_nodos;
	}
}
