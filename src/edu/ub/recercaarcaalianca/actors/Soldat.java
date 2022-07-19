package edu.ub.recercaarcaalianca.actors;

import edu.ub.recercaarcaalianca.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import static edu.ub.recercaarcaalianca.Constants.*;

/**
 * Soldats que vigilen el Temple, decrementen la vida de l'arqueologa
 */

public class Soldat extends AbstractActor {


    private static final int INCREMENT_POSX = 1;
    private static final int INCREMENT_POSY = 1;
    private static final int[][] DIRECCIONS = {
            {1, 0},   // EST
            {-1, 0},  // OEST
            {0, 1},   // SUD
            {0, -1}   // NORD
    };
    
    private static final Rectangle DIMENSIONS_ICONA = new Rectangle(0, 0, 50, 30);
    private Image[] icones;
    private final int[] direccio = DIRECCIONS[0];
    private int targetX;
    private int targetY;
    private boolean hasTarget;
    private boolean aturat;
    
    private float forca_soldat; 
    public static enum TipusSoldat{COMMANDANT,CAPITA,RAS};
    private TipusSoldat tipus;
        
    public Soldat(TipusSoldat tipus) {
        this.tipus = tipus;
       
        icones = new Image[2];
        BufferedImage iTmp = null; 
        
        switch (tipus){
            case COMMANDANT:
                iTmp = Util.carregarImatge("coronel.png");
                this.forca_soldat = 20;
                break;
            case CAPITA:
                iTmp = Util.carregarImatge("soldat.png");
                this.forca_soldat = 12;
                break;
            case RAS:
                iTmp = Util.carregarImatge("soldat.png");
                this.forca_soldat = 8;
                break;
        }
        
        icones[0] = Util.flipImatgeHor(iTmp);
        icones[1] = iTmp;
    }

    /**
     * Sobreescriu el metode per canviar de direccio cada cop que xoca amb un
     * mur.
     *
     * @param context
     */
    public void actualitzar(Context context) {

        if (isAturat()) return;

        Rectangle limits = new Rectangle(getX(), getY(), DIMENSIONS_ICONA.width, DIMENSIONS_ICONA.height);

        //estableix el target si no el te o l'ha trobat
        if (!hasTarget || hasReachedTarget(limits, targetX, targetY)) {
            targetX = context.getJoc().getArqueologa().getPosicio()[0];
            targetY = context.getJoc().getArqueologa().getPosicio()[1];
            hasTarget = true;
        }

        int posX = getX();
        int posY = getY();

        //d'aquesta manera no es queden totalment parats quan no poden avancar en diagonal 
        int aleatori = (int) (Math.random() * 2);

        if (aleatori == 0) {
            if (getX() > targetX)
                posX -= INCREMENT_POSX;
            else if (getX() < targetX)
                posX += INCREMENT_POSX;
            else if (getY() > targetY)
                posY -= INCREMENT_POSY;
            else if (getY() < targetY)
                posY += INCREMENT_POSY;
        } else {
            if (getY() > targetY)
                posY -= INCREMENT_POSY;
            else if (getY() < targetY)
                posY += INCREMENT_POSY;
            else if (getX() > targetX)
                posX -= INCREMENT_POSX;
            else if (getX() < targetX)
                posX += INCREMENT_POSX;
        }


        Cambra h = context.getCambra();
        limits = new Rectangle(posX, posY, DIMENSIONS_ICONA.width, DIMENSIONS_ICONA.height);

        if (getXocaContraMurs(limits, h)) {
            hasTarget = false;
        } else if (!getXocaContraMurs(limits, h)) {
            setPosicio(posX, posY);
        }

    }

    public boolean hasReachedTarget(Rectangle limits, int targetX, int targetY) {
        return limits.getX() <= targetX && limits.getX() + limits.width >= targetX &&
                limits.getY() <= targetY && limits.getY() + limits.height >= targetY;
    }

    public Rectangle getLimits() {
        return new Rectangle(getX(), getY(), DIMENSIONS_ICONA.width, DIMENSIONS_ICONA.height);
    }

    public void tractarColisio(Colisio colisio) {
        Actor actor = colisio.getActor();
        // TODO alumnes, gestio de la colisio amb l'arqueologa amb el Soldat 
        if (actor instanceof Arqueologa) {
          Arqueologa arqueologa = (Arqueologa) actor;  
          //ja pots utilitzar arqueologa per accedir als seus mètodes        
            
            
        }
    }

    public void render(Graphics2D g) {
        int currentIcona = 0;
        g.drawImage(icones[currentIcona], getX(), getY(), observer);
    }

    public boolean isAturat() {
        return aturat;
    }

    public void setAturat(boolean aturat) {
        this.aturat = aturat;
    }
    
    /**
     * Obte el nivell de forca del soldat.
     *
     * @return un numero entre 0 i 100, 0 es mort.
     */
    public float getForca() {
        return forca_soldat;
    }
    
    /**
     * Estableix el nivell de forca.
     *
     * @param Forca un numero enter 0 i 100
     */
    public void setForca(float Forca) {
        this.forca_soldat = Math.max(0, Math.min(100, Forca));
    }
    
    
    // *********************************************************
    // ****** private methods***********************************
    // *********************************************************

    private void init() {
        
    }

    private boolean getXocaContraMurs(Rectangle limits, Cambra h) {
        boolean xoca = false;
        int[][] celes = h.getCelesIntersectades(limits);
        int i = 0;
        while (i < celes.length && !xoca) {
            if (h.getValor(celes[i][0], celes[i][1]) == SIMBOL_MUR)
                xoca = true;
            i++;
        }
        return xoca;
    }

 
}
