import java.util.*;
import java.util.regex.Pattern;

// Clase principal que gestiona el mundo de LightBot, almacena la cuadrícula, el robot y las funciones definidas
// Permite parsear funciones, ejecutar programas y reiniciar el estado cuando sea necesario
public class LightBot {
    private char[][] grid;
    private Robot robot;

    private char[][] originalGrid;
    private int originalRow;
    private int originalCol;
    private Robot.Direccion originalDir;

    private Map<String, List<String>> functionBodies = new HashMap<>(); // Cuerpos de funciones: nombre -> lista de instrucciones
    private Map<String, List<String>> functionParams = new HashMap<>(); // Parámetros de funciones: nombre -> lista de nombres de parámetros

    // Constructor: recibe las líneas de texto que describen el mundo y la posición inicial del robot
    // Lee el mapa, crea la cuadrícula, guarda la posición y dirección originales para permitir reinicios posteriores
    public LightBot(String[] mundoLineas) {
        MapParser parser = new MapParser(mundoLineas);
        this.grid  = parser.getGrid();
        this.robot = parser.getRobot();
        int rows = grid.length;
        int cols = grid[0].length;
        originalGrid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(grid[r], 0, originalGrid[r], 0, cols);
        }
        originalRow = robot.row;
        originalCol = robot.col;
        originalDir = robot.dir;
    }

    // Reinicia el mundo y el robot a su estado inicial guardado en el constructor
    // Útil para volver a ejecutar el mismo programa sin arrastrar efectos previos
    public void reset() {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int r = 0; r < rows; r++) {
            System.arraycopy(originalGrid[r], 0, grid[r], 0, cols);
        }
        robot.row = originalRow;
        robot.col = originalCol;
        robot.dir = originalDir;
    }

    // Ejecuta un programa completo: limpia definiciones antiguas, parsea funciones y
    // luego ejecuta las instrucciones principales
    public void runProgram(String[] instrucciones) {
        functionBodies.clear();
        functionParams.clear();
        List<String> inst = Arrays.asList(instrucciones);
        parseFunctions(inst);
        ejecutarBloque(inst);
    }

    // Parsea todas las instrucciones para detectar bloques FUNCTION/ENDFUNCTION
    // Guarda nombre, parámetros y cuerpo en los mapas correspondientes
    private void parseFunctions(List<String> inst) {
        int i = 0;
        while (i < inst.size()) {
            String comando = inst.get(i);
            if (comando.startsWith("FUNCTION")) {
                String sig = comando.substring(8).trim();
                String name;
                List<String> params = new ArrayList<>();
                int pstart = sig.indexOf('(');
                if (pstart >= 0) {
                    name = sig.substring(0, pstart).trim();
                    int pend = sig.indexOf(')', pstart);
                    String plist = sig.substring(pstart + 1, pend).trim();
                    if (!plist.isEmpty()) for (String p : plist.split(",")) params.add(p.trim());
                } else name = sig;
                int level = 1;
                int j = i + 1;
                List<String> body = new ArrayList<>();
                while (j < inst.size() && level > 0) {
                    String s = inst.get(j);
                    if (s.startsWith("FUNCTION")) level++;
                    else if (s.equals("ENDFUNCTION")) level--;
                    if (level > 0) body.add(s);
                    j++;
                }
                functionBodies.put(name, body);
                functionParams.put(name, params);
                i = j;
            } else i++;
        }
    }

    // Recorre un bloque de instrucciones y ejecuta cada comando
    // Soporta giros, movimientos, iluminación, repeticiones y llamadas a funciones
    private void ejecutarBloque(List<String> inst) {
        int i = 0;
        while (i < inst.size()) {
            String comando = inst.get(i);
            if (comando.equals("LEFT")) {
                robot.girarIzquierda();
                i++;
            } else if (comando.equals("RIGHT")) {
                robot.girarDerecha();
                i++;
            } else if (comando.equals("FORWARD")) {
                robot.caminar(grid);
                i++;
            } else if (comando.equals("LIGHT")) {
                robot.luz(grid);
                i++;
            } else if (comando.startsWith("REPEAT")) {
                String[] parts = comando.split(" ");
                int veces = Integer.parseInt(parts[1]);
                int level = 1;
                int start = i + 1;
                int j = start;
                while (j < inst.size() && level > 0) {
                    String s = inst.get(j);
                    if (s.startsWith("REPEAT")) level++;
                    else if (s.equals("ENDREPEAT")) level--;
                    j++;
                }
                List<String> sub = inst.subList(start, j - 1);
                for (int k = 0; k < veces; k++) ejecutarBloque(sub);
                i = j;
            } else if (comando.equals("ENDREPEAT")) {
                return;
            } else if (comando.startsWith("FUNCTION")) {
                int level = 1;
                int j = i + 1;
                while (j < inst.size() && level > 0) {
                    if (inst.get(j).startsWith("FUNCTION")) level++;
                    else if (inst.get(j).equals("ENDFUNCTION")) level--;
                    j++;
                }
                i = j;
            } else if (comando.startsWith("CALL")) {
                String call = comando.substring(4).trim();
                String name;
                List<String> args = new ArrayList<>();
                int pstart = call.indexOf('(');
                if (pstart >= 0) {
                    name = call.substring(0, pstart).trim();
                    int pend = call.lastIndexOf(')');
                    String as = call.substring(pstart + 1, pend).trim();
                    if (!as.isEmpty()) for (String a : as.split(",")) args.add(a.trim());
                } else name = call;
                List<String> body = functionBodies.get(name);
                List<String> params = functionParams.get(name);
                if (body == null) throw new IllegalArgumentException("Function not defined: " + name);
                List<String> sub = new ArrayList<>();
                for (String line : body) {
                    String newline = line;
                    if (params != null) {
                        for (int idx = 0; idx < params.size(); idx++) {
                            newline = newline.replaceAll("\\b" + Pattern.quote(params.get(idx)) + "\\b", args.get(idx));
                        }
                    }
                    sub.add(newline);
                }
                ejecutarBloque(sub);
                i++;
            } else i++;
        }
    }

    // Devuelve la posición actual del robot en formato [columna, fila]
    // Útil para comprobar el resultado tras la ejecución
    public int[] getRobotPosition() {
        return new int[]{ robot.col, robot.row };
    }

    // Devuelve el mapa actual como array de cadenas, mostrando el estado de las celdas
    public String[] getMap() {
        String[] out = new String[grid.length];
        for (int r = 0; r < grid.length; r++) out[r] = new String(grid[r]);
        return out;
    }
}

// Representa al robot dentro del mundo, con posición (fila, columna) y dirección actual
// Ofrece métodos para girar, mover y cambiar la iluminación de las celdas
class Robot {
    public int row;
    public int col;
    public Direccion dir;

    enum Direccion { UP, DOWN, LEFT, RIGHT }

    // Constructor: fija posición inicial y dirección de enfrentamiento
    Robot(int row, int col, Direccion dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    // Gira el robot 90 grados hacia la izquierda según su dirección actual
    public void girarIzquierda() {
        switch (dir) {
            case UP:    dir = Direccion.LEFT;  break;
            case LEFT:  dir = Direccion.DOWN;  break;
            case DOWN:  dir = Direccion.RIGHT; break;
            default:    dir = Direccion.UP;    break;
        }
    }

    // Gira el robot 90 grados hacia la derecha según su dirección actual
    public void girarDerecha() {
        switch (dir) {
            case UP:    dir = Direccion.RIGHT; break;
            case RIGHT: dir = Direccion.DOWN;  break;
            case DOWN:  dir = Direccion.LEFT;  break;
            default:    dir = Direccion.UP;    break;
        }
    }

    // Mueve al robot una casilla en la dirección actual; los bordes se conectan (wrap-around)
    public void caminar(char[][] grid) {
        int dr = 0, dc = 0;
        switch (dir) {
            case UP:    dr = -1; break;
            case DOWN:  dr = +1; break;
            case LEFT:  dc = -1; break;
            case RIGHT: dc = +1; break;
        }
        int nr = (row + dr + grid.length) % grid.length;
        int nc = (col + dc + grid[0].length) % grid[0].length;
        row = nr;
        col = nc;
    }

    // Cambia el estado de la celda actual: si está apagada la enciende y si está encendida la apaga
    public void luz(char[][] grid) {
        char c = grid[row][col];
        if (c == 'O')      grid[row][col] = 'X';
        else if (c == '.') grid[row][col] = 'x';
    }

    public void repeat() {

    }

    public void function() {

    }

}

class MapParser {
    private char[][] grid;
    private Robot robot;

    public MapParser(String[] rows) {
        int rcount = rows.length;
        int ccount = rows[0].length();
        grid = new char[rcount][ccount];
        for (int r = 0; r < rcount; r++) {
            for (int c = 0; c < ccount; c++) {
                char ch = rows[r].charAt(c);
                switch (ch) {
                    case 'U': robot = new Robot(r, c, Robot.Direccion.UP);    grid[r][c] = '.'; break;
                    case 'D': robot = new Robot(r, c, Robot.Direccion.DOWN);  grid[r][c] = '.'; break;
                    case 'R': robot = new Robot(r, c, Robot.Direccion.RIGHT); grid[r][c] = '.'; break;
                    case 'L': robot = new Robot(r, c, Robot.Direccion.LEFT);  grid[r][c] = '.'; break;
                    default:  grid[r][c] = ch;
                }
            }
        }
        if (robot == null) throw new IllegalArgumentException("No se encontro posicion de inicio del robot");
    }

    public char[][] getGrid() { return grid; }
    public Robot getRobot()   { return robot; }
}
