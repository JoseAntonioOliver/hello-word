import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class Main implements GlobalVals {

	public static void main(String[] args) {

		Estado estado = new Estado();
		Frontera frontera = new Frontera();

		estado = leerJSON2();
		System.out.println(
				"--------------------------Antes de sucesores   Estado inicial--------------------------------------");
		System.out.println(estado.getId() + " " + estado.getInitState());

		// creamos el nodo raiz con los valores nulos salvo el estado
		Nodo nodoRaiz = new Nodo(0, 0.0, estado, -1, "None", 0, -1);
		nodoRaiz.setEstrategia(elegirEstrategia());
		frontera.push(nodoRaiz);
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		Visitados nodosVisitados = new Visitados();
		if (algoritmoBusqueda(frontera, 2000, solucion, nodosVisitados)) {
			System.out.println("Estado final alcanzado");
			Nodo fin = solucion.remove(0);
			solucion.add(fin);
			while (fin.getId() != 0) {
				for (int i = 0; i < nodosVisitados.getNodosVisitados().size(); i++) {
					if (nodosVisitados.getNodosVisitados().get(i).getId() == fin.getId_padre()) {
						fin = nodosVisitados.getNodosVisitados().get(i);
						solucion.add(fin);
					}
				}
			}
			crearTXT(solucion, estado.getId());

		} else {
			System.out.println("No hay solucion para el problema");
		}

	}

	public static ArrayList<Nodo> expandirNodosPadre(Estado estado_ini, Nodo nodoRaiz, int id) {
		ArrayList<Nodo> listNodos = new ArrayList<Nodo>();

		for (int x = 0; x <= estado_ini.getBotellas().size() - 1; x++) {
			for (int y = 0; y <= estado_ini.getBotellas().size() - 1; y++) {
				for (int z = 1; z <= estado_ini.getBottleSize(); z++) {

					Estado estado_sucesor = new Estado();
					estado_sucesor.setBotellas(estado_sucesor(estado_ini.getBotellas()));
					estado_sucesor.setBottleSize(estado_ini.getBottleSize());
					Accion accion = new Accion(estado_sucesor.getBotellas().get(x), estado_sucesor.getBotellas().get(y),
							z);

					if ((x != y) && accion.ES_accion_posible() && accion.accionValida()) {
						String accionRealizada = "(" + x + ", " + y + ", " + z + ")";
						Sucesor sucesor = new Sucesor(accion, 1, accionRealizada);
						sucesor.generarSucesor();
						sucesor.asignarEstado(estado_sucesor);
						sucesor.getEstado().asignarStringEstado();
						sucesor.getEstado().hashMD5();
						estado_ini.addSucesor(sucesor);

						Nodo nodo = new Nodo(++id, nodoRaiz.getCoste() + 1.0, sucesor.getEstado(), nodoRaiz.getId(),
								accionRealizada, nodoRaiz.getProfundidad() + 1, nodoRaiz.getEstrategia());
						listNodos.add(nodo);
					}

				}
			}
		}
		return listNodos;

	}

	public static boolean algoritmoBusqueda(Frontera frontera, int profundidad, ArrayList<Nodo> solucion,
			Visitados nodosVisitados) {
		boolean sol = false;

		nodosVisitados.crear_vacio();
		int id = 0;
		Nodo actual = new Nodo();
		ArrayList<Nodo> listaNodosHijos = new ArrayList<Nodo>();

		while (!sol && !frontera.EsVacia()) {

			actual = frontera.pop();
			System.out.println(actual.toString() + " --> " + actual.getEstado().getInitState());

			if (funcionObjetivo(actual.getEstado())) {
				sol = true;
				solucion.add(actual);
				return sol;

			} else {
				if (!nodosVisitados.pertenece(actual) && actual.getProfundidad() < profundidad) {

					nodosVisitados.insertar(actual);
					listaNodosHijos = expandirNodosPadre(actual.getEstado(), actual, id);

					for (int i = 0; i < listaNodosHijos.size(); i++) {
						frontera.push(listaNodosHijos.get(i));
						id++;

					}
				}
			}
		}
		return sol;
	}

	public static int elegirEstrategia() {
		Scanner sc = new Scanner(System.in);
		int estrategia = 0;

		System.out.println("Elige la Estrategia que quieres utilizar");
		System.out.println("1. DEPTH");
		System.out.println("2. BREADTH");
		System.out.println("3. UNIFORM");
		System.out.println("4. A*");
		System.out.println("5. GREEDY");

		while (estrategia < 1 || estrategia > 5) {
			try {
				estrategia = sc.nextInt();
				if (estrategia < 1 || estrategia > 5)
					System.out.println("Introduce una opcion correcta.");
			} catch (InputMismatchException e) {
				System.out.println("Introduce un n�mero.");
				sc.nextLine();
			}
		}

		sc.close();
		return estrategia;
	}

	public static ArrayList<Botella> estado_sucesor(ArrayList<Botella> bottles) {
		ArrayList<Botella> sucesor_botellas = new ArrayList<Botella>();

		for (int i = 0; i < bottles.size(); i++) {
			Stack<Liquido> liquidos = new Stack<Liquido>();
			for (int j = 0; j < bottles.get(i).getLiquidos().size(); j++) {

				Liquido liquido = new Liquido(bottles.get(i).getLiquidos().get(j).getColour(),
						bottles.get(i).getLiquidos().get(j).getAmount());
				liquidos.add(liquido);
			}
			Botella botella = new Botella(bottles.get(i).getCantidadMax(), liquidos);
			sucesor_botellas.add(botella);
		}

		return sucesor_botellas;
	}

	public static void crearTXT(ArrayList<Nodo> nodos, String problema) {
		String directorio = directorioSolucion;
		String n_problema = problema.replace("\"", "");
		String space = "_";
		String estrategia = "";
		switch (nodos.get(0).getEstrategia()) {

		case 1:
			estrategia = "DEPTH";
			break;
		case 2:
			estrategia = "BREADTH";
			break;
		case 3:
			estrategia = "UNIFORM";
			break;
		case 4:
			estrategia = "A";
			break;
		case 5:
			estrategia = "GREEDY";
			break;
		}
		String ruta = directorio.concat(n_problema.concat(space.concat(estrategia.concat(".txt"))));

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
			for (int i = nodos.size() - 1; i >= 0; i--) {
				bw.write(nodos.get(i).toString());
				bw.write("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean funcionObjetivo(Estado estado) {
		boolean llenas = true;
		boolean color = true;
		ArrayList<Integer> colores = new ArrayList<Integer>();

		for (int i = 0; i < estado.getBotellas().size(); i++) {

			if (!estado.getBotellas().get(i).getLiquidos().isEmpty())
				colores.add(estado.getBotellas().get(i).getLiquidos().peek().getColour());
			for (int j = 0; j < estado.getBotellas().get(i).getLiquidos().size() - 1; j++) {
				if (estado.getBotellas().get(i).getLiquidos().get(j).getColour() != estado.getBotellas().get(i)
						.getLiquidos().get(j + 1).getColour()) {
					llenas = false;
				}
			}

		}

		for (int z = 0; z < colores.size() - 1; z++) {
			for (int z1 = 0; z1 < colores.size(); z1++) {
				if (z != z1 && colores.get(z) == colores.get(z1)) {
					color = false;
				}
			}

		}

		return llenas && color;
	}

	public static Estado leerJSON2() {

		String file = rutaProblema;
		JsonParser parser = new JsonParser();
		JsonArray array;
		JsonPrimitive idP, bottleSizeP;
		Estado estado = null;

		String id;
		int bottleSize;
		String initState;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String linea = "";
			linea = br.readLine();

			// Lectura y asigancion de valores del json
			array = (JsonArray) parser.parse(linea).getAsJsonObject().get("initState");
			initState = array.toString();
			idP = parser.parse(linea).getAsJsonObject().getAsJsonPrimitive("id");
			id = idP.toString();
			bottleSizeP = parser.parse(linea).getAsJsonObject().getAsJsonPrimitive("bottleSize");
			bottleSize = Integer.parseInt(bottleSizeP.toString());

			estado = new Estado(id, bottleSize, initState);
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return estado;
	}

}
