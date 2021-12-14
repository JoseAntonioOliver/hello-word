import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Estado {

	private String id;
	private int bottleSize;
	private String hash;
	private String initState;
	private ArrayList<Botella> botellas;
	private ArrayList<Sucesor> sucesores;

	public Estado() {
		this.botellas = new ArrayList<Botella>();
		this.sucesores = new ArrayList<Sucesor>();
	}

	public Estado(String id, int bottleSize, String initState) {
		super();
		this.id = id;
		this.bottleSize = bottleSize;
		this.initState = initState;
		this.botellas = new ArrayList<Botella>();
		this.sucesores = new ArrayList<Sucesor>();
		leerInitState();
		hashMD5();
	}

	public void setBotellas(ArrayList<Botella> botellas) {
		this.botellas = botellas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBottleSize() {
		return bottleSize;
	}

	public void setBottleSize(int bottleSize) {
		this.bottleSize = bottleSize;
	}

	public String getInitState() {
		return initState;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public ArrayList<Botella> getBotellas() {
		return botellas;
	}

	public ArrayList<Sucesor> getSucesores() {
		return sucesores;
	}

	public void addSucesor(Sucesor sucesor) {
		sucesores.add(sucesor);
	}

	public void asignarStringEstado() {
		String estadoFinal = botellas.toString();

		if ((botellas.toString().contains("Botella")) || (botellas.toString().contains("Liquido"))
				|| (botellas.toString().contains("colour=")) || (botellas.toString().contains("amount="))) {
			estadoFinal = botellas.toString().replace("Botella: ", "");
			estadoFinal = estadoFinal.replace("Liquido ", "");
			estadoFinal = estadoFinal.replace("colour=", "");
			estadoFinal = estadoFinal.replace("amount=", "");
			estadoFinal = estadoFinal.replaceAll("\\s", "");
		}

		initState = estadoFinal;

	}

	public void hashMD5() {

		try {
			MessageDigest HashMD5 = MessageDigest.getInstance("MD5");
			byte[] mensajeMatriz = HashMD5.digest(initState.getBytes());
			BigInteger numero = new BigInteger(1, mensajeMatriz);
			StringBuilder hashMD5Salida = new StringBuilder(numero.toString(16));

			while (hashMD5Salida.length() < 32) {
				hashMD5Salida.insert(0, "0");
			}
			hash = hashMD5Salida.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error al obtener el hash: " + e.getMessage());

		}

	}

	public void leerInitState() {

		String aux = "";
		StringTokenizer tokens = new StringTokenizer(initState, "[");
		int color = -1;
		int cantidad = -1;
		char[] arrayChar;

		while (tokens.hasMoreTokens()) {
			aux = tokens.nextToken(",");
			if (aux.contains("[]")) {
				Botella botella = new Botella();
				botella.setCantidadMax(bottleSize);
				botellas.add(botella);
			} else if (aux.contains("[[")) {
				Botella botella = new Botella();
				botella.setCantidadMax(bottleSize);
				arrayChar = aux.toCharArray();
				for (int i = 0; i < arrayChar.length - 1; i++) {
					if (Character.isDigit(arrayChar[arrayChar.length - 1])) {
						color = Character.getNumericValue(arrayChar[arrayChar.length - 1]);
					}
				}
				while (!aux.contains("]]")) {
					aux = tokens.nextToken();
					arrayChar = aux.toCharArray();
					for (int i = 0; i < arrayChar.length - 1; i++) {
						if (Character.isDigit(arrayChar[0])) {
							cantidad = Character.getNumericValue(arrayChar[0]);
							Liquido liquido = new Liquido(color, cantidad);
							botella.addLiquido(liquido);
						} else if (Character.isDigit(arrayChar[arrayChar.length - 1])) {
							color = Character.getNumericValue(arrayChar[arrayChar.length - 1]);
						}
					}
				}
				botella.getLiquidos().pop();
				Botella prueba = new Botella();
				prueba.setCantidadMax(bottleSize);
				while (!botella.getLiquidos().isEmpty()) {
					Liquido liAux = new Liquido(botella.getLiquidos().peek().getColour(),
							botella.getLiquidos().pop().getAmount());
					prueba.getLiquidos().push(liAux);
				}
				botellas.add(prueba);
			}
		}
	}
}
