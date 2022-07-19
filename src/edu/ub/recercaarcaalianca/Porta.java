package edu.ub.recercaarcaalianca;

/**
 * Cada porta es situa en un punt dintre del mapa (fila i columna) i conte
 * la informacio de la cambra amb la que es comunica, aixi com la posicio x,y
 * a l'entrar.
 *
 * @author ub.edu
 */
public class Porta {

    public static final short PORTA_TANCADA = 0;
    public static final short PORTA_OBERTA = 1;
    private final int fila;
    private final int columna;
    private int numNivellDesti = -1;
    private int numCambraDesti = -1;
    private int[] posicioCambraDesti;
    private boolean oberta = true;

    public Porta(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public int getNumCambraDesti() {
        return numCambraDesti;
    }

    public void setNumCambraDesti(int numCambraDesti) {
        this.numCambraDesti = numCambraDesti;
    }

    public int[] getPosicioCambraDesti() {
        return posicioCambraDesti;
    }

    public void setPosicioCambraDesti(int[] posicioCambraDesti) {
        this.posicioCambraDesti = posicioCambraDesti;
    }

    public boolean isOberta() {
        return oberta;
    }

    public void tancar() {
        oberta = false;
    }

    public void obrir() {
        oberta = true;
    }

    public int getNumNivellDesti() {
        return numNivellDesti;
    }

    public void setNumNivellDesti(int numNivellDesti) {
        this.numNivellDesti = numNivellDesti;
    }

}
