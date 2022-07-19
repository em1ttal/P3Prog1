package edu.ub.recercaarcaalianca.actors;

import edu.ub.recercaarcaalianca.Colisio;
import edu.ub.recercaarcaalianca.Context;
import edu.ub.recercaarcaalianca.Renderable;

import java.awt.*;

/**
 * Inteficie que defineix els metodes d'un Actor.
 *
 * @author
 */
public interface Actor extends Renderable {

    /**
     * Inicialitza les dades de l'actor.
     */
    void inicialitzar();

    /**
     * Estableix la posicio inicial de l'actor.
     *
     * @param x coordenada horizontal en pixels
     * @param y coordenada vertical en pixels
     */
    void setPosicioInicial(int x, int y);

    /**
     * Obte la posicio inicial de l'actor.
     *
     * @return un array de tamany 2 amb la posicio x al primer element i la y
     * al segon
     */
    int[] getPosicioInicial();

    /**
     * Estableix la posicio de l'actor.
     *
     * @param x coordenada horizontal en pixels
     * @param y coordenada vertical en pixels
     */
    void setPosicio(int x, int y);

    /**
     * Obte la posicio actual de l'actor.
     *
     * @return un array de tamany 2 amb la posicio x al primer element i la y
     * al segon
     */
    int[] getPosicio();

    /**
     * Actualitza les dades de l'actor segons l'estat actual del joc.
     *
     * @param context el context del joc en el moment d'actualitzar
     */
    void actualitzar(Context context);

    /**
     * Obte la posicio i mida d'un actor en el moment actual
     *
     * @return un rectangle amb la posicio x,y i l'amplada i alcada
     */
    Rectangle getLimits();

    /**
     * Gestiona la collisio d'aquest actor amb un altre.
     *
     * @param colisio les dades de la collisio.
     */
    void tractarColisio(Colisio colisio);

    /**
     * Obte l'estat
     *
     * @return una de les constants <code>Constants.ACTIU</code>,
     * <code>Constants.INACTIU</code>
     */
    int getEstat();

    /**
     * Estableix l'estat.
     *
     * @param estat
     */
    void setEstat(int estat);

}
