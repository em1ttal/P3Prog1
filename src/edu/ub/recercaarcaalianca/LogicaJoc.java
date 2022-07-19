package edu.ub.recercaarcaalianca;

/**
 * Gestiona la logica del joc definint uns estats i les regles per passar
 * d'un estat a un altre.
 *
 * @author ub.edu
 */
public class LogicaJoc {

    private final Joc joc;
    private EstatJoc estat;
    public LogicaJoc(Joc joc) {
        this.joc = joc;
        this.estat = EstatJoc.MENU;
    }

    public EstatJoc getEstat() {
        return estat;
    }

    public void setEstat(EstatJoc estat) {
        this.estat = estat;
    }

    public void iniciar() {
        if (estat == EstatJoc.MENU) {
            estat = EstatJoc.INICIANT;
            this.joc.iniciar();
            estat = EstatJoc.JUGANT;
        }
    }

    public void continuar() {
        estat = EstatJoc.JUGANT;
    }

    public void pausar() {
        if (this.estat == EstatJoc.JUGANT)
            this.estat = EstatJoc.PAUSAT;
        else if (this.estat == EstatJoc.PAUSAT)
            this.estat = EstatJoc.JUGANT;
    }

    public void exit() {
        if (this.estat == EstatJoc.MENU)
            estat = EstatJoc.EXIT;
        else
            this.estat = EstatJoc.MENU;
    }

    public enum EstatJoc {
        MENU,
        INICIANT,   // el joc s'esta iniciant
        JUGANT,
        PAUSAT,
        GAMEOVER,
        EXIT        // sortida del programa
    }
}
