
public class Sucesor {

	private String accionRealizada;
	private Accion accion;
	private Estado newEstado;
	private int costo; // por ahora consideraremos el coste como 1 para cada sucesor

	public Sucesor(Accion accion, int costo, String accionRealizada) {
		super();
		this.accion = accion;
		this.costo = costo;
		this.newEstado = new Estado();
		this.accionRealizada = accionRealizada;

	}

	public String getAccionRealizada() {
		return accionRealizada;
	}

	public Accion getAccion() {
		return accion;
	}

	public Estado getEstado() {
		return newEstado;
	}

	public int getCosto() {
		return costo;
	}

	public void generarSucesor() {
		accion.accion();
	}

	public void asignarEstado(Estado estado) {
		newEstado = estado;
	}

}
