import java.util.*;

public class GrafoMatrizAdyacencia {
    private int[][] matriz;
    private boolean dirigido;

    public GrafoMatrizAdyacencia(int n, boolean dirigido) {
        this.matriz = new int[n][n];
        this.dirigido = dirigido;
    }

    public void agregarArista(int v1, int v2) {
        matriz[v1][v2] = 1;
        if (!dirigido) {
            matriz[v2][v1] = 1;
        }
    }

    public boolean esConexo() {
        boolean[] visitados = new boolean[matriz.length];
        dfs(0, visitados);

        for (boolean visitado : visitados) {
            if (!visitado) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int vertice, boolean[] visitados) {
        visitados[vertice] = true;

        for (int i = 0; i < matriz.length; i++) {
            if (matriz[vertice][i] == 1 && !visitados[i]) {
                dfs(i, visitados);
            }
        }
    }

    public boolean esCompleto() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if (i != j && matriz[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Integer> gradoVertices() {
        List<Integer> grados = new ArrayList<>();

        for (int i = 0; i < matriz.length; i++) {
            int grado = 0;
            for (int j = 0; j < matriz.length; j++) {
                grado += matriz[i][j];
            }
            grados.add(grado);
        }

        return grados;
    }

    public List<Integer> camino(int origen, int destino) {
        List<Integer> camino = new ArrayList<>();
        boolean[] visitados = new boolean[matriz.length];

        dfsCamino(origen, destino, visitados, camino);

        return camino;
    }

    private boolean dfsCamino(int vertice, int destino, boolean[] visitados, List<Integer> camino) {
        visitados[vertice] = true;
        camino.add(vertice);

        if (vertice == destino) {
            return true;
        }

        for (int i = 0; i < matriz.length; i++) {
            if (matriz[vertice][i] == 1 && !visitados[i]) {
                if (dfsCamino(i, destino, visitados, camino)) {
                    return true;
                }
            }
        }
        camino.remove(camino.size() - 1);
        return false;
    }

    public int numeroComponentesConexos() {
        int componentesConexos = 0;
        boolean[] visitados = new boolean[matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            if (!visitados[i]) {
                dfs(i, visitados);
                componentesConexos++;
            }
        }
        return componentesConexos;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        GrafoMatrizAdyacencia grafo = null;

        while (true) {
            System.out.println("Menú principal:");
            System.out.println("1. Crear grafo");
            System.out.println("2. Salir");
            System.out.print("Ingrese la opción deseada: ");
            opcion = scanner.nextInt();

            if (opcion == 2) {
                break;
            }

            if (opcion == 1) {
                System.out.print("Ingrese el número de vértices: ");
                int n = scanner.nextInt();
                System.out.print("¿Es dirigido? (true/false): ");
                boolean dirigido = scanner.nextBoolean();
                grafo = new GrafoMatrizAdyacencia(n, dirigido);
            }

            while (opcion != 6) {
                System.out.println("\nMenú del grafo:");
                System.out.println("1. Agregar arista");
                System.out.println("2. Es conexo");
                System.out.println("3. Es completo");
                System.out.println("4. Grado de vértices");
                System.out.println("5. Camino entre dos vértices");
                System.out.println("6. Regresar al menú principal");
                System.out.print("Ingrese la opción deseada: ");
                opcion = scanner.nextInt();

                if (opcion == 1) {
                    System.out.print("Ingrese el vértice de origen: ");
                    int origen = scanner.nextInt();
                    System.out.print("Ingrese el vértice de destino: ");
                    int destino = scanner.nextInt();
                    grafo.agregarArista(origen, destino);
                } else if (opcion == 2) {
                    System.out.println("¿Es conexo? " + grafo.esConexo());
                } else if (opcion == 3) {
                    System.out.println("¿Es completo? " + grafo.esCompleto());
                } else if (opcion == 4) {
                    System.out.println("Grado de vértices: " + grafo.gradoVertices());
                } else if (opcion == 5) {
                    System.out.print("Ingrese el vértice de origen: ");
                    int origen = scanner.nextInt();
                    System.out.print("Ingrese el vértice de destino: ");
                    int destino = scanner.nextInt();
                    System.out.println("Camino entre " + origen + " y " + destino + ": " + grafo.camino(origen, destino));
                }
            }
        }
    }
}
