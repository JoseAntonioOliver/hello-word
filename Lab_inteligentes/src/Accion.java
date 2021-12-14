public class Accion {

	private Botella botella_origen;
	private Botella botella_destino;
	private int cantidad;

	public Accion() {
	}

	public Accion(Botella botella_origen, Botella botella_destino, int cantidad) {
		super();
		this.botella_origen = botella_origen;
		this.botella_destino = botella_destino;
		this.cantidad = cantidad;
	}

	public Botella getLiquidos_origen() {
		return botella_origen;
	}

	public Botella getLiquidos_destino() {
		return botella_destino;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCoste(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean ES_accion_posible() {

		if (botella_origen.cantidadLiquido() >= cantidad /* && botella_origen.cantidadLiquido() != 0 */
				&& (botella_destino.getCantidadMax() - botella_destino.cantidadLiquido()) >= cantidad) {
			return true;

		}
		return false;
	}

	public boolean accionValida() {
		if ((botella_origen.getLiquidos().peek().getAmount() == cantidad)
				&& ((botella_destino.cantidadLiquido() == 0) || (botella_destino.getLiquidos().peek()
						.getColour() == botella_origen.getLiquidos().peek().getColour()))) {
			return true;
		}
		return false;
	}

	public boolean SameColor() {

		if (botella_origen.getLiquidos().peek().getColour() == botella_destino.getLiquidos().peek().getColour()) {
			return true;
		}
		return false;
	}

	public Botella accion() {

		Liquido liquido_1 = new Liquido();
		Liquido liquido_2 = new Liquido();

		// mientras que cantidad sea mayor que cero, tendremos que sacar de la pila
		while (cantidad != 0) {
			// sacamos el primer liquido de la botella origen para ver que cantidad tiene
			// sin removerlo de la pila
			liquido_1 = botella_origen.getLiquidos().peek();

			if (liquido_1.getAmount() < cantidad && !botella_destino.getLiquidos().isEmpty()) {

				if (SameColor()) {
					botella_destino.getLiquidos().peek()
							.setAmount(botella_destino.getLiquidos().peek().getAmount() + liquido_1.getAmount());

				} else {
					botella_destino.getLiquidos().push(liquido_1); // metemos el liquido en la nueva botella
				}

				cantidad = cantidad - botella_origen.getLiquidos().pop().getAmount(); // sacamos de la pila origen el
																						// liquido
				// que vamos a mover y restamos cantidad

			} else if ((cantidad == liquido_1.getAmount()) && !botella_destino.getLiquidos().isEmpty()) {

				if (SameColor()) {

					botella_destino.getLiquidos().peek()
							.setAmount(botella_destino.getLiquidos().peek().getAmount() + cantidad);

				} else {
					botella_destino.getLiquidos().push(liquido_1); // metemos el liquido en la nueva botella
				}

				botella_origen.getLiquidos().pop(); // sacamos de la pila origen el liquido que vamos a mover
				cantidad = 0;

			} else if (botella_destino.getLiquidos().isEmpty() && liquido_1.getAmount() <= cantidad) {

				botella_destino.getLiquidos().push(liquido_1); // metemos el liquido en la nueva botella
				cantidad = cantidad - liquido_1.getAmount(); // sacamos de la pila origen el liquido
				botella_origen.getLiquidos().pop(); // sacamos de la pila origen el liquido que vamos a mover

			} else {

				liquido_1 = botella_origen.getLiquidos().peek();

				botella_destino.getLiquidos().push(liquido_2);
				botella_destino.getLiquidos().peek().setColour(liquido_1.getColour());
				botella_destino.getLiquidos().peek().setAmount(cantidad);

				// como hemos sacado menos liquido del que hay de ese color, tenemos que
				// actualizar la cantidad del liquido que queda
				botella_origen.getLiquidos().peek()
						.setAmount(botella_origen.getLiquidos().peek().getAmount() - cantidad);
				cantidad = 0;

			}

		}

		return botella_destino;
	}

	@Override
	public String toString() {
		return "Accion [botella_origen=" + botella_origen + ", botella_destino=" + botella_destino + ", cantidad="
				+ cantidad + "]";
	}

}
