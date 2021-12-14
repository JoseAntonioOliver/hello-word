import java.util.Stack;

public class Botella {
	private int cantidadMax;
	private Stack<Liquido> botella;

	public Botella() {
		this.botella = new Stack<Liquido>();
	}

	public Botella(int cantidadMax, Stack<Liquido> botella) {
		this.botella = botella;
		this.cantidadMax = cantidadMax;
	}

	public int getCantidadMax() {
		return cantidadMax;
	}

	public void setCantidadMax(int cantidadMax) {
		this.cantidadMax = cantidadMax;
	}

	public void setBotella(Stack<Liquido> botella) {
		this.botella = botella;
	}

	public Stack<Liquido> getLiquidos() {
		return botella;
	}

	public void addLiquido(Liquido liquido) {
		botella.add(liquido);
	}

	public Liquido removeLiquido() {
		return botella.pop();
	}

	public int cantidadLiquido() {
		int cantidadTotal = 0;
		for (int i = 0; i < botella.size(); i++) {
			cantidadTotal = cantidadTotal + botella.get(i).getAmount();
		}
		return cantidadTotal;
	}

	public String toString() {
		Stack<Liquido> prueba = new Stack<Liquido>();
		for (int i = botella.size() - 1; i >= 0; i--) {
			Liquido liquido = new Liquido(botella.get(i).getColour(), botella.get(i).getAmount());
			prueba.push(liquido);
		}
		return "Botella: " + prueba;
	}

}
