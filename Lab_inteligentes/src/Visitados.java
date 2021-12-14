import java.util.ArrayList;

public class Visitados {
	private ArrayList<Nodo> nodosVisitados;

	Visitados() {
	}

	public ArrayList<Nodo> getNodosVisitados() {
		return nodosVisitados;
	}

	public void crear_vacio() {
		nodosVisitados = new ArrayList<Nodo>();
	}

	public void insertar(Nodo nodo) {
		nodosVisitados.add(nodo);
	}

	public boolean pertenece(Nodo nodo) {
		boolean pertenece = false;
		if (nodosVisitados.isEmpty()) {
			return pertenece;
		}
		for (int i = 0; i < nodosVisitados.size(); i++) {
			if (nodosVisitados.get(i).getEstado().getHash().compareTo(nodo.getEstado().getHash()) == 0) {
				pertenece = true;
			}
		}
		return pertenece;

	}

}
