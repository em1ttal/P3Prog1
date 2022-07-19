package edu.ub.recercaarcaalianca;

import edu.ub.recercaarcaalianca.actors.Actor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Cadascuna de les Cambrans guarda una matriu de celles, on cada cella
 * conte un char amb el tipus de cella: mur, porta, o res (espai buit).
 *
 * @author ub.edu
 */
public class Cambra implements Renderable {

    private static final int BLOCK_MUR = 0;
    private static final int BLOCK_TERRA = 1;
    private static final int BLOCK_PORTA = 2;
    private char[][] configuracio =
            new char[Constants.NUM_CELES_VERTICALS][Constants.NUM_CELES_HORIZONTALS];
    private Image blocImage[];
    private final Canvas observer = new Canvas();
    private final List<Actor> actors = Collections.synchronizedList(new ArrayList<Actor>());
    private final List<Porta> portes = Collections.synchronizedList(new ArrayList<Porta>());

    /**
     * Constructor.
     */
    public Cambra() {
        init();
    }

    /**
     * Nova Cambra amb una configuracio.
     *
     * @param configuracio
     */
    public Cambra(char[][] configuracio) {
        this.configuracio = configuracio;
    }

    /*
     * Obte la llista de portes de l'Cambra.
     * 
     * @return la llista
     */
    public List<Porta> getPortes() {
        return portes;
    }

    /**
     * Obte els actors que hi han a l'Cambra.
     *
     * @return els actors que hi ha a l'Cambra
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Obte els actors que hi han a l'Cambra en un array.
     *
     * @return els actors que hi ha a l'Cambra
     */
    public Actor[] getActorsAsArray() {
        return actors.toArray(new Actor[]{});
    }

    /**
     * Afegeix un actor.
     *
     * @param actor l'actor
     */
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    /**
     * Afegeix una porta.
     *
     * @param porta la porta
     */
    public void addPorta(Porta porta) {
        portes.add(porta);
    }

    /**
     * Obte la porta que hi ha a una cella concreta.
     *
     * @param fila    fila de la cella
     * @param columna columna de la cella
     * @return la porta o null si no hi ha cap
     */
    public Porta getPorta(int fila, int columna) {
        Porta p = null;
        char c = configuracio[fila][columna];
        if (Constants.SIMBOL_PORTA == c) {
            Iterator<Porta> i = portes.iterator();
            while (p == null && i.hasNext()) {
                Porta aux = i.next();
                if (aux.getFila() == fila && aux.getColumna() == columna) {
                    p = aux;
                }
            }
        }
        return p;
    }

    /**
     * Retorna la fila i columna de la cella que esta al punt de l'espai
     * proporcionat (en pixels).
     */
    public int[] getCela(int x, int y) {
        int j = x / Constants.AMPLADA_CELA;
        int i = y / Constants.ALCADA_CELA;
        return new int[]{i, j};
    }

    /**
     * Donats la fila i columna d'una cella retorna la posicio x i y (en pixels)
     * on es troba.
     *
     * @param fila
     * @param col
     * @return un array de 2 components, al primer la x i al segon la y
     */
    public int[] getPosicioCela(int fila, int col) {
        return new int[]{
                col * Constants.AMPLADA_CELA,
                fila * Constants.ALCADA_CELA
        };
    }

    /**
     * Obte el rectangle amb els limits de la cella.
     *
     * @param fila
     * @param columna
     * @return el rectangle
     */
    public Rectangle getLimitsCela(int fila, int columna) {
        return new Rectangle(columna * Constants.AMPLADA_CELA,
                fila * Constants.ALCADA_CELA,
                Constants.AMPLADA_CELA,
                Constants.ALCADA_CELA);
    }

    /**
     * Obte les celles que intersecten amb una rectangle.
     *
     * @param r el rectangle
     * @return una array on cada element es un altre array de longitud 2 amb
     * la fila i columna de la cella intersectada
     */
    public int[][] getCelesIntersectades(Rectangle r) {
        int[] ci = getCela((int) r.getX(), (int) r.getY());
        int[] cf = getCela((int) (r.getX() + r.getWidth()),
                (int) (r.getY() + r.getHeight()));

        List<int[]> list = new ArrayList<int[]>();
        for (int fila = ci[0]; fila <= cf[0]; fila++) {
            for (int col = ci[1]; col <= cf[1]; col++) {
                list.add(new int[]{fila, col});
            }
        }

        int[][] arr = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    /*
     * Estableix el valor d'una cella
     * 
     * @param fila la fila de la cella
     * @param columna la columna de la cella
     * @param valor el caracter
     */
    public void setValor(int fila, int columna, char valor) {
        configuracio[fila][columna] = valor;
        if (Constants.SIMBOL_PORTA == valor) {
            Porta p = getPorta(fila, columna);
            if (p == null) {
                p = new Porta(fila, columna);
                portes.add(p);
            }
        }
    }

    /**
     * Obte el valor d'una cella.
     *
     * @param fila    la fila de la cella
     * @param columna la columna de la cella
     * @return el caracter que hi ha a la cella
     */
    public char getValor(int fila, int columna) {
        return configuracio[fila][columna];
    }

    /**
     * Dibuixa la Cambra per pantalla.
     *
     * @param g l'objecte de sortida
     */
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, Constants.AMPLADA_FINESTRA, Constants.ALCADA_FINESTRA);

        for (int i = 0; i < configuracio.length; i++) {
            char[] arr = configuracio[i];

            for (int j = 0; j < arr.length; j++) {
                char c = configuracio[i][j];
                int x = j * Constants.AMPLADA_CELA;
                int y = i * Constants.ALCADA_CELA;

                switch (c) {
                    case Constants.SIMBOL_MUR:
                        g.drawImage(blocImage[BLOCK_MUR], x, y, observer);
                        break;
                    case Constants.SIMBOL_PORTA:
                        g.drawImage(blocImage[BLOCK_PORTA], x, y, observer);
                        break;
                    case Constants.SIMBOL_TERRA:
                        g.drawImage(blocImage[BLOCK_TERRA], x, y, observer);
                    default:
                        // per ara, res ...
                }
            }
        }
    }

    // private methods *********************************************************

    private void init() {
        blocImage = new Image[3];
        blocImage[BLOCK_MUR] = Util.carregarImatge("mur32.png");
        blocImage[BLOCK_TERRA] = Util.carregarImatge("terra32.png");
        blocImage[BLOCK_PORTA] = Util.carregarImatge("porta2.png");
    }


}
