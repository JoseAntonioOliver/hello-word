
public class Liquido {

	private int colour;
	private int amount;

	public Liquido() {
	}

	public Liquido(int colour, int amount) {
		this.colour = colour;
		this.amount = amount;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void addColour(int color) {
		this.colour = color;
	}

	public void addAmount(int cantidad) {
		this.amount = cantidad;
	}

	@Override
	public String toString() {
		return "Liquido [colour=" + colour + ", amount=" + amount + "]";
	}

}
