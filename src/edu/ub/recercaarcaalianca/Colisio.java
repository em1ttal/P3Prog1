package edu.ub.recercaarcaalianca;

import edu.ub.recercaarcaalianca.actors.Actor;

/**
 * Les dades d'una collisio.
 *
 * @author
 */
public class Colisio {

    private final Actor actor;

    /**
     * Nova collisio.
     *
     * @param actor l'actor amb qui es collisiona.
     */
    public Colisio(Actor actor) {
        this.actor = actor;
    }

    /**
     * L'actor amb qui es collisiona.
     *
     * @return l'actor
     */
    public Actor getActor() {
        return actor;
    }


}
