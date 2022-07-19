package edu.ub.recercaarcaalianca;

import java.awt.*;

/**
 * La Temple conte les cambres distribuides per Nivells on es succeeix l'accio,
 * tambe mante el numero de Nivell i Cambra actuals on es troba el
 * jugador.
 *
 * @author ub.edu
 */
public class Temple implements Renderable {

    /**
     * Numero de Nivells de la Temple.
     */
    public static final int NUM_NIVELLS= 3;
    /**
     * Numero d'cambres per cada Nivell.
     */
    public static final int NUM_CAMBRES_PER_NIVELL = 4;
    private int numNivell;
    private int numCambra;
    private final Cambra[][] cambres;

    /**
     * Constructor.
     */
    public Temple(int numNivells, int numCambresPerNivell) {
        cambres = new Cambra[numNivells][numCambresPerNivell];
    }

    /**
     * Incialitzacions.
     */
    public void inicialitzar() {
        numNivell = 0;
        numCambra = 0;
    }

    /***
     * Afegeix una Cambra.
     *
     * @param Nivell numero de Nivell
     * @param numCambra numero d'Cambra
     * @param Cambra Cambra
     */
    public void addCambra(int nivell, int numCambra, Cambra cambra) {
        cambres[nivell][numCambra] = cambra;
    }

    /**
     * Obte el numero de Nivells.
     *
     * @return el numero de Nivells que te la Temple
     */
    public int getNumNivells() {
        return cambres.length;
    }

    /**
     * Obte el numero d'cambres.
     *
     * @param Nivell la Nivell
     * @return el numero d'cambres que te la Nivell
     */
    public int getNumCambres(int Nivell) {
        return cambres[Nivell].length;
    }

    public int getNivell() {
        return numNivell;
    }

    /**
     * Estableix la Nivell actual.
     *
     * @param Nivell el numero de Nivell
     */
    public void setNivell(int Nivell) {
        this.numNivell = Nivell;
    }

    public int getNumCambra() {
        return numCambra;
    }

    /**
     * Estableix la Cambra actual.
     *
     * @param Cambra el numero d'Cambra
     */
    public void setNumCambra(int cambra) {
        this.numCambra = cambra;
    }

    /**
     * Dibuixa l'Cambra actual.
     *
     * @param g
     */
    public void render(Graphics2D g) {
        getCambra().render(g);
    }

    /**
     * Obte l'Cambra actual.
     *
     * @return l'Cambra
     */
    public Cambra getCambra() {
        return cambres[numNivell][numCambra];
    }

    /**
     * Obte una Cambra determinada.
     *
     * @param numNivell    el numero de Nivell
     * @param numCambra el numero d'Cambra dins de la Nivell
     */
    public Cambra getCambra(int numNivell, int numCambra) {
        return cambres[numNivell][numCambra];
    }

    /**
     * Obte totes les cambres d'una Nivell.
     *
     * @param numNivell el numero de Nivell
     * @return el array d'cambres
     */
    public Cambra[] getCambres(int numNivell) {
        return cambres[numNivell];
    }

}
