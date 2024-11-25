package Modelo;

import Modelo.Pieza;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa el tablero de ajedrez y gestiona su estado.
 * Maneja la disposición de las piezas y el registro de movimientos.
 */
public class Tablero {
    /** Matriz 8x8 que representa el estado actual del tablero */
    private Pieza[][] tablero;
    /** Registro de movimientos realizados en notación PGN */
    private List<String> historialMovimientos;

    /**
     * Constructor que inicializa un nuevo tablero con la disposición
     * inicial estándar de las piezas de ajedrez.
     */
    public Tablero() {
        tablero = new Pieza[8][8];
        historialMovimientos = new ArrayList<>();
        inicializarTablero();
    }

    /**
     * Coloca todas las piezas en sus posiciones iniciales estándar.
     * Configura las piezas blancas en las filas 1 y 2, y las negras en las filas 7 y 8.
     */
    private void inicializarTablero() {
        // Inicializar piezas blancas
        tablero[0][0] = new Pieza("Torre", 'B', "a1");
        tablero[0][1] = new Pieza("Caballo", 'B', "b1");
        tablero[0][2] = new Pieza("Alfil", 'B', "c1");
        tablero[0][3] = new Pieza("Dama", 'B', "d1");
        tablero[0][4] = new Pieza("Rey", 'B', "e1");
        tablero[0][5] = new Pieza("Alfil", 'B', "f1");
        tablero[0][6] = new Pieza("Caballo", 'B', "g1");
        tablero[0][7] = new Pieza("Torre", 'B', "h1");

        for (int i = 0; i < 8; i++) {
            tablero[1][i] = new Pieza("Peón", 'B', (char)('a' + i) + "2");
        }

        // Inicializar piezas negras
        tablero[7][0] = new Pieza("Torre", 'N', "a8");
        tablero[7][1] = new Pieza("Caballo", 'N', "b8");
        tablero[7][2] = new Pieza("Alfil", 'N', "c8");
        tablero[7][3] = new Pieza("Dama", 'N', "d8");
        tablero[7][4] = new Pieza("Rey", 'N', "e8");
        tablero[7][5] = new Pieza("Alfil", 'N', "f8");
        tablero[7][6] = new Pieza("Caballo", 'N', "g8");
        tablero[7][7] = new Pieza("Torre", 'N', "h8");

        for (int i = 0; i < 8; i++) {
            tablero[6][i] = new Pieza("Peón", 'N', (char)('a' + i) + "7");
        }
    }

    /**
     * Procesa y ejecuta un movimiento en notación PGN.
     * Interpreta la notación, identifica la pieza a mover y actualiza el estado del tablero.
     * @param movimientoPGN Movimiento en notación PGN (ej: "e4", "Nf3", "exd5")
     */
    public void realizarMovimiento(String movimientoPGN) {
        if (movimientoPGN == null || movimientoPGN.isEmpty()) {
            return;
        }

        // Limpiar la notación de símbolos especiales
        movimientoPGN = movimientoPGN.replaceAll("[+#]", "").trim();

        // Manejar enroques
        if (movimientoPGN.equals("O-O") || movimientoPGN.equals("O-O-O")) {
            realizarEnroque(movimientoPGN);
            historialMovimientos.add(movimientoPGN);
            return;
        }

        try {
            // Determinar el tipo de pieza
            char tipoPieza = 'P';
            int startIndex = 0;
            if (Character.isUpperCase(movimientoPGN.charAt(0)) && movimientoPGN.charAt(0) != 'O') {
                tipoPieza = movimientoPGN.charAt(0);
                startIndex = 1;
            }

            // Procesar captura
            boolean esCaptura = movimientoPGN.contains("x");
            movimientoPGN = movimientoPGN.replace("x", "");

            // Obtener casilla destino
            String destino = movimientoPGN.substring(movimientoPGN.length() - 2);
            int columnaDestino = destino.charAt(0) - 'a';
            int filaDestino = 8 - Character.getNumericValue(destino.charAt(1));

            // Buscar la pieza correcta para mover
            Pieza piezaAMover = encontrarPiezaParaMovimiento(tipoPieza, columnaDestino, filaDestino,
                    movimientoPGN.substring(startIndex,
                            movimientoPGN.length() - 2));

            if (piezaAMover != null) {
                // Encontrar posición actual de la pieza
                int[] posicionActual = encontrarPosicionPieza(piezaAMover);
                if (posicionActual != null) {
                    // Realizar el movimiento
                    tablero[filaDestino][columnaDestino] = piezaAMover;
                    tablero[posicionActual[0]][posicionActual[1]] = null;
                    piezaAMover.setPosicion(String.format("%c%d", (char)('a' + columnaDestino), 8 - filaDestino));
                    historialMovimientos.add(movimientoPGN);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar movimiento: " + movimientoPGN);
            e.printStackTrace();
        }
    }

    /**
     * Encuentra la pieza correcta para realizar el movimiento.
     */
    private Pieza encontrarPiezaParaMovimiento(char tipoPieza, int columnaDestino, int filaDestino, String especificador) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieza pieza = tablero[i][j];
                if (pieza != null && esPiezaCorrecta(pieza, tipoPieza)) {
                    if (puedeMoverse(i, j, filaDestino, columnaDestino) &&
                            coincideEspecificador(pieza, i, j, especificador)) {
                        return pieza;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Verifica si una pieza coincide con el tipo especificado.
     */
    private boolean esPiezaCorrecta(Pieza pieza, char tipoPieza) {
        switch (tipoPieza) {
            case 'P': return pieza.getNombre().equals("Peón");
            case 'N': return pieza.getNombre().equals("Caballo");
            case 'B': return pieza.getNombre().equals("Alfil");
            case 'R': return pieza.getNombre().equals("Torre");
            case 'Q': return pieza.getNombre().equals("Dama");
            case 'K': return pieza.getNombre().equals("Rey");
            default: return false;
        }
    }

    /**
     * Verifica si una pieza puede moverse a una posición destino.
     */
    private boolean puedeMoverse(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        Pieza pieza = tablero[filaOrigen][columnaOrigen];
        if (pieza == null) return false;

        // Implementar reglas básicas de movimiento
        switch (pieza.getNombre()) {
            case "Caballo":
                int difFila = Math.abs(filaDestino - filaOrigen);
                int difColumna = Math.abs(columnaDestino - columnaOrigen);
                return (difFila == 2 && difColumna == 1) || (difFila == 1 && difColumna == 2);
            case "Alfil":
                return Math.abs(filaDestino - filaOrigen) == Math.abs(columnaDestino - columnaOrigen);
            case "Torre":
                return filaOrigen == filaDestino || columnaOrigen == columnaDestino;
            case "Dama":
                return filaOrigen == filaDestino || columnaOrigen == columnaDestino ||
                        Math.abs(filaDestino - filaOrigen) == Math.abs(columnaDestino - columnaOrigen);
            case "Rey":
                return Math.abs(filaDestino - filaOrigen) <= 1 && Math.abs(columnaDestino - columnaOrigen) <= 1;
            case "Peón":
                int direccion = pieza.getColor() == 'B' ? 1 : -1;
                if (columnaOrigen == columnaDestino) {
                    return filaDestino == filaOrigen + direccion ||
                            (filaOrigen == (pieza.getColor() == 'B' ? 1 : 6) &&
                                    filaDestino == filaOrigen + 2 * direccion);
                }
                return Math.abs(columnaDestino - columnaOrigen) == 1 &&
                        filaDestino == filaOrigen + direccion;
            default:
                return true;
        }
    }

    /**
     * Verifica si la pieza coincide con el especificador de movimiento.
     */
    private boolean coincideEspecificador(Pieza pieza, int fila, int columna, String especificador) {
        if (especificador == null || especificador.isEmpty()) {
            return true;
        }

        for (char c : especificador.toCharArray()) {
            if (Character.isLetter(c) && columna != (c - 'a')) {
                return false;
            }
            if (Character.isDigit(c) && fila != (8 - Character.getNumericValue(c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Encuentra la posición actual de una pieza en el tablero.
     */
    private int[] encontrarPosicionPieza(Pieza pieza) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tablero[i][j] == pieza) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Realiza un movimiento de enroque.
     */
    private void realizarEnroque(String movimiento) {
        int fila = 0;
        char color = 'B';

        // Determinar si es enroque de blancas o negras
        if (tablero[0][4] == null || !tablero[0][4].getNombre().equals("Rey")) {
            fila = 7;
            color = 'N';
        }

        if (movimiento.equals("O-O")) {
            // Enroque corto
            tablero[fila][6] = tablero[fila][4]; // Mover rey
            tablero[fila][5] = tablero[fila][7]; // Mover torre
            tablero[fila][4] = null;
            tablero[fila][7] = null;
        } else {
            // Enroque largo
            tablero[fila][2] = tablero[fila][4]; // Mover rey
            tablero[fila][3] = tablero[fila][0]; // Mover torre
            tablero[fila][4] = null;
            tablero[fila][0] = null;
        }
    }

    /**
     * @return Estado actual del tablero como matriz de piezas
     */
    public Pieza[][] getTablero() {
        return tablero;
    }

    /**
     * Actualiza el estado completo del tablero
     * @param tablero Nueva configuración del tablero
     */
    public void setTablero(Pieza[][] tablero) {
        this.tablero = tablero;
    }

    /**
     * @return Lista de movimientos realizados en notación PGN
     */
    public List<String> getHistorialMovimientos() {
        return historialMovimientos;
    }

    /**
     * Agrega un nuevo movimiento al historial
     * @param movimiento Movimiento en notación PGN
     */
    public void agregarMovimiento(String movimiento) {
        historialMovimientos.add(movimiento);
    }
}