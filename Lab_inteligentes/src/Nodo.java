import java.util.ArrayList;

public class Nodo implements Comparable<Nodo> {

	private int id;
	private double coste;
	private Estado estado;
	private int profundidad;
	private String accion;
	private int id_padre;
	private double valor;
	private double heuristica;
	private int estrategia;

	public Nodo() {
	}

	public Nodo(int id, double coste, Estado estado, int id_padre, String accion, int prof, int estrategia) {
		this.id = id;
		this.coste = coste;
		this.estado = estado;
		this.id_padre = id_padre;
		this.accion = accion;
		this.profundidad = prof;
		this.estrategia = estrategia;
		heuristica = calcularHeuristica();
		valor = estrategiaAlgoritmo(estrategia);
	}

	public double estrategiaAlgoritmo(int estrategia) {
		double valor = 0;
		if (estrategia == 1) /* Estrategia 1 --> DEPTH */
			valor = 1.0 / (profundidad + 1);
		else if (estrategia == 2) /* Estrategia 2 --> BREADTH */
			valor = profundidad;
		else if (estrategia == 3) /* Estrategia 3 --> UNIFORM */
			valor = coste;
		else if (estrategia == 4) /* Estrategia 4 --> A */
			valor = coste + heuristica;
		else if (estrategia == 5) /* Estrategia 5 --> GREEDY */
			valor = heuristica;

		return valor;
	}

	public double calcularHeuristica() {
		double heurist = 0;
		boolean meterVisitado = false;
		ArrayList<Integer> colorVisitado = new ArrayList<Integer>();

		for (int i = 0; i < estado.getBotellas().size(); i++) {
			heurist = heurist + estado.getBotellas().get(i).getLiquidos().size();

			if (!estado.getBotellas().get(i).getLiquidos().isEmpty()) {
				meterVisitado = true;
				for (int j = 0; j < colorVisitado.size(); j++) {

					if (estado.getBotellas().get(i).getLiquidos().peek().getColour() == colorVisitado.get(j)) {
						heurist = heurist + 1.0;
						meterVisitado = false;

					}
				}
				if (meterVisitado)
					colorVisitado.add(estado.getBotellas().get(i).getLiquidos().peek().getColour());

			} else {
				heurist = heurist + 1.0;
			}
		}

		return heurist - estado.getBotellas().size();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCoste() {
		return coste;
	}

	public void setCoste(double coste) {
		this.coste = coste;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public int getId_padre() {
		return id_padre;
	}

	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public double getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(float heuristica) {
		this.heuristica = heuristica;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public int getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(int estrategia) {
		this.estrategia = estrategia;
	}

	public int compareTo(Nodo n) {
		if (valor > n.getValor()) {
			return 1;
		} else if (valor < n.getValor()) {
			return -1;
		} else {
			if (id < n.getId()) {
				return -1;
			} else if (id > n.getId()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public String toString() {
		if (id == 0) {
			return "[" + id + "][" + coste + ", " + estado.getHash() + ", " + "None" + ", " + "None" + ", "
					+ profundidad + ", " + String.format("%.2f", heuristica) + ", " + String.format("%.2f", valor)
					+ "]";
		} else {
			return "[" + id + "][" + coste + ", " + estado.getHash() + ", " + id_padre + ", " + accion + ", "
					+ profundidad + ", " + String.format("%.2f", heuristica) + ", " + String.format("%.2f", valor)
					+ "]";
		}

	}
}
