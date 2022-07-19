package edu.ub.recercaarcaalianca;


import edu.ub.recercaarcaalianca.actors.Actor;

/**
 * Mante les dades del joc. la Temple i l'Arqueologa.
 *
 * @author ub.edu
 */
public class Joc {

    private Temple temple;
    private Actor arqueologa;


    /**
     * Constructor.
     */
    public Joc() {
    }

    public Joc(Temple Temple, Actor arqueologa) {
        this.temple = Temple;
        this.arqueologa = arqueologa;
    }

    public void iniciar() {
        temple.setNivell(0);
        temple.setNumCambra(0);

        // inicialitzar actors
        arqueologa.inicialitzar();
        for (int i = 0; i < temple.getNumNivells(); i++) {
            Cambra[] arr = temple.getCambres(i);
            if (arr != null) {
                for (Cambra h : arr) {
                    if (h != null) {
                        for (Actor actor : h.getActors()) {
                            actor.inicialitzar();
                        }
                    }
                }
            }
        }
    }

    public Temple getTemple() {
        return temple;
    }

    /**
     * Estableix la temple de los Morc.
     *
     * @param temple la temple
     */
    public void setTemple(Temple temple) {
        this.temple = temple;
    }

    public Actor getArqueologa() {
        return arqueologa;
    }

    /**
     * Estableix l'Arqueologa.
     *
     * @param arqueologa
     */
    public void setArqueologa(Actor arqueologa) {
        this.arqueologa = arqueologa;
    }


}
