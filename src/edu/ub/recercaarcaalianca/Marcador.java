package edu.ub.recercaarcaalianca;

import edu.ub.recercaarcaalianca.actors.Arqueologa;

import java.awt.*;

/**
 * Representa el marcador de vida, i si ha trobat els diamants l'arqueologa (no implementat)
 *
 * @author ub.edu
 */
public class Marcador {

    private final int y;

    private final Font fontNormal = new Font("Dialog", Font.PLAIN, 12);
    private final Font fontPetita = new Font("Dialog", Font.BOLD, 10);

    public Marcador() {
        y = Constants.ALCADA_FINESTRA - 30;
    }

    /**
     * Dibuixa el seu contingut.
     *
     * @param ctx context del joc
     * @param g   grafics de sortida
     */
    public void render(Context ctx, Graphics2D g) {
        pintarFons(g);
        pintarNivellForca(ctx, g);
        pintarCambra(ctx, g);
        pintarDiamants(ctx, g);
        if (ctx.getTempsEnemicInmovilitzat() > 0)
            pintarTimer(ctx, g);
    }


    // private methods *********************************************************

    private void pintarFons(Graphics2D g) {
        g.setPaint(new GradientPaint(
                Constants.AMPLADA_FINESTRA / 2.f, y, Color.DARK_GRAY,
                Constants.AMPLADA_FINESTRA / 2.f, y + 40.f, Color.BLACK));

        Rectangle r = new Rectangle(0, y,
                Constants.AMPLADA_FINESTRA, Constants.ALCADA_FINESTRA - y);
        g.fill(r);
    }

    private void pintarNivellForca(Context ctx, Graphics2D g) {
        g.setPaint(Color.white);
        g.setFont(fontNormal);
        g.drawString("forca: ", 5, y + 15);

        Rectangle r = new Rectangle(40, y + 5, 100, 12);
        g.setColor(Color.BLACK);
        g.fill(r);

        Rectangle t = new Rectangle();
        Arqueologa cindy = (Arqueologa) ctx.getJoc().getArqueologa(); 
       
       // TODO  alumnes, cal fer les funcions de cindy i descomentar el codi
       // t.setRect(r.getX(), r.getY(), cindy.getVida(), (int) r.getHeight());  
        t.setRect(r.getX(), r.getY(), 100, (int) r.getHeight()); // eliminar aquesta linia quan estigui la de sobre implementada
        int nivell = 100 ; // (int) (cindy.getVida());
        g.setColor(nivell < 50 ? Color.RED : Color.BLUE);
        g.fill(t);
        g.setColor(Color.white);
        g.draw(r);
        g.setFont(fontPetita);

        g.drawString(nivell + "%", 60, y + 15);
    }


    private void pintarCambra(Context ctx, Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(fontNormal);
        int Nivell = ctx.getJoc().getTemple().getNivell();
        int cambra = ctx.getJoc().getTemple().getNumCambra();
        g.drawString("Nivell: " + Nivell + " - Cambra: " + cambra, 200, y + 15);
    }

    private void pintarDiamants(Context ctx, Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(fontNormal);
        Arqueologa arqueologa = (Arqueologa) ctx.getJoc().getArqueologa();
        // TODO alumnes de la funció corresponent i descomentar el codi
        //g.drawString("Ha trobat tots els diamants? " + arqueologa.haTrobatElsDiamants(), 350, y +15); 
    }

    private void pintarTimer(Context ctx, Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(fontNormal);
        Arqueologa arqueologa = (Arqueologa) ctx.getJoc().getArqueologa();
        g.drawString("Enemic immobilitzat!: " + ctx.getTempsEnemicInmovilitzat(), 550, y + 15);
    }
}
