package edu.ub.recercaarcaalianca.actors;

import edu.ub.recercaarcaalianca.Constants;
import edu.ub.recercaarcaalianca.Context;
import edu.ub.recercaarcaalianca.Porta;
import edu.ub.recercaarcaalianca.Cambra;
import java.awt.*;


/**
 * Classe base per tota la resta d'actors. Subclasses poden extendre aquesta
 * classe i beneficiar-se d'aquesta implementacio basica.
 *
 * @author ub.edu
 */
public abstract class AbstractActor implements Actor {

    protected int x0;
    protected int y0;

    protected int x;
    protected int y;
    protected int estat;
    protected Canvas observer = new Canvas();

    public void inicialitzar() {
        this.x = x0;
        this.y = y0;
        this.estat = Constants.ESTAT_ACTIU;
    }

    public void setPosicioInicial(int x, int y) {
        this.x0 = x;
        this.y0 = y;
    }

    public int[] getPosicioInicial() {
        return new int[]{x0, y0};
    }
 

    public int getEstat() {
        return estat;
    }

    public void setEstat(int estat) {
        this.estat = estat;
    }

    public void setPosicio(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPosicio() {
        return new int[]{x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Retorna si l'actor en la posicio propocionada collisionaria amb un mur
     * de l'Cambra actual.
     *
     * @param ctx
     * @param x   la posicio x de l'actor
     * @param y   la posicio y de l'actor
     * @return true si en la posicio l'actor collisiona amb el mur.
     */
    public boolean testMur(Context ctx, int x, int y) {
        return testMur(ctx, x, y, getLimits());
    }

    /**
     * Retorna si el rectangle proporcionat intersecta amb un mur
     * de l'Cambra actual.
     *
     * @param ctx
     * @param x      la posicio x de l'actor
     * @param y      la posicio y de l'actor
     * @param limits el rectangle
     * @return true si el rectangle intersecta amb un mur.
     */
    public boolean testMur(Context ctx, int x, int y, Rectangle limits) {
        Cambra cambra = ctx.getJoc().getTemple().getCambra();
        int w = (int) limits.getWidth();
        int h = (int) limits.getHeight();

        Rectangle r = new Rectangle(x, y, w, h);
        int[][] celes = cambra.getCelesIntersectades(r);

        for (int i = 0; i < celes.length; i++) {
            char c = cambra.getValor(celes[i][0], celes[i][1]);
            if (c == Constants.SIMBOL_MUR) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obte la porta que hi collisionaria amb l'actor en un punt determinat de
     * l'espai.
     *
     * @param ctx
     * @param x   la posicio x
     * @param y   la posicio y
     * @return la porta o null si no hi ha cap
     */
    public Porta testPorta(Context ctx, int x, int y) {
        Porta p = null;
        Cambra cambra = ctx.getJoc().getTemple().getCambra();
        Rectangle limits = getLimits();
        int w = (int) limits.getWidth();
        int h = (int) limits.getHeight();

        Rectangle r = new Rectangle(x, y, w, h);
        int[][] celes = cambra.getCelesIntersectades(r);

        int i = 0;
        while (p == null && i < celes.length) {
            p = cambra.getPorta(celes[i][0], celes[i][1]);
            i++;
        }
        return p;
    }

}
