package edu.ub.recercaarcaalianca.actors;

import edu.ub.recercaarcaalianca.*;

import java.awt.image.BufferedImage;
import java.awt.*;

/**                                 
 * Representa la nostra Arqueologa.
 */
public class Arqueologa extends AbstractActor {
    public static final int AMPLADA = 40;
    public static final int ALCADA = 71;
    private static final int FRAMES_CHANGE = 1;
    private static final float DANY_PER_SEGON = 1.0f;
    int lastImage;
    private Image[] imatges;
    private boolean bPosChanged;
    private int nFramesToChange = FRAMES_CHANGE;
    private int direccio = -1;
   
    // ******************************************
    // TODO alumnes: afegir a sota ATRIBUTS PROPIS DEL PROBLEMA A SOLUCIONAR
    // ******************************************
    
    
   
    /**
     * Constructor.
     */
    public Arqueologa() {
        init();      
    }

    public void inicialitzar() {
        super.inicialitzar();             
    }  
  
   
    /**
     * Obte un rectangle ambs el limits de l'Arqueologa.
     *
     * @return els limits, x,y, amplada i alcada
     */
    public Rectangle getLimits() {
        return new Rectangle(x, y, AMPLADA, ALCADA);
    }

    public void actualitzar(Context ctx) {
        calcularNivellVida(ctx);
        int desX = 0;
        int desY = 0;
        bPosChanged = false;

        if (ctx.isKeyPressed(Context.KEY_DOWN)) {
            desY = 1;
            direccio = Constants.DIRECCIO_SUD;
            bPosChanged = true;
        } else if (ctx.isKeyPressed(Context.KEY_UP)) {
            desY = -1;
            direccio = Constants.DIRECCIO_NORD;
            bPosChanged = true;
        } else if (ctx.isKeyPressed(Context.KEY_LEFT)) {
            desX = -1;
            direccio = Constants.DIRECCIO_OEST;
            bPosChanged = true;
        } else if (ctx.isKeyPressed(Context.KEY_RIGHT)) {
            desX = 1;
            direccio = Constants.DIRECCIO_EST;
            bPosChanged = true;
        }

        int deltaX = 10;
        int auxX = x + deltaX * desX;
        int deltaY = 8;
        int auxY = y + deltaY * desY;

        Porta porta = testPorta(ctx, auxX, auxY);
        if (porta != null && porta.getNumNivellDesti() != -1 &&
                porta.getNumCambraDesti() != -1) {

            Temple temple = ctx.getJoc().getTemple();
            temple.setNivell(porta.getNumNivellDesti());
            temple.setNumCambra(porta.getNumCambraDesti());
            int[] posicio = porta.getPosicioCambraDesti();
            if (posicio != null) {
                x = posicio[0];
                y = posicio[1];
            }
        } else if (!testMur(ctx, auxX, auxY)) {
            x = auxX;
            y = auxY;
        }
    }

    public void tractarColisio(Colisio colisio) {
    }

    public void render(Graphics2D g) {
        int nImg = lastImage;

        if (bPosChanged) {
        	nFramesToChange--;
        	if (nFramesToChange == 0) {
        		nFramesToChange = FRAMES_CHANGE;
        	}
        }
        
        if (direccio == Constants.DIRECCIO_EST) {
        	nImg = 1;
        } else if(direccio == Constants.DIRECCIO_OEST) {
        	nImg = 0;
        }

        Image image = imatges[nImg];
        g.drawImage(image, x, y, observer);
        lastImage = nImg;
    }
    
    public void randomTeleport(Context context) {
        Temple temple = context.getJoc().getTemple();
        Actor arqueologa = context.getJoc().getArqueologa();
        Cambra novaCambra;
        Cambra h = context.getCambra();
        Cambra[] cambres = temple.getCambres(temple.getNivell());

        if (cambres.length > 1) {
            boolean trobada = false;
            int fila = 0;
            int col = 0;
            int numCambra = 0;
            int[] posicio = null;

            while (!trobada) {
                numCambra = (int) (Math.random() * cambres.length);
                while (h == cambres[numCambra]) {
                    numCambra = (int) (Math.random() * cambres.length);
                }
                novaCambra = temple.getCambra(temple.getNivell(), numCambra);
                boolean lliure = false;
                while (!lliure) {
                    fila = (int) Math.max(0, (Math.random() * Constants.NUM_CELES_VERTICALS - 2));
                    col = (int) Math.max(0, (Math.random() * Constants.NUM_CELES_HORIZONTALS - 2));
                    char c1 = novaCambra.getValor(fila, col);
                    char c2 = novaCambra.getValor(fila + 1, col);
                    char c3 = novaCambra.getValor(fila, col + 1);
                    char c4 = novaCambra.getValor(fila + 1, col + 1);
                    lliure = (c1 == Constants.SIMBOL_TERRA &&
                            c2 == Constants.SIMBOL_TERRA &&
                            c3 == Constants.SIMBOL_TERRA &&
                            c4 == Constants.SIMBOL_TERRA);

                    // comprovar que cap actor esta dins la cela
                    Actor[] actors = novaCambra.getActorsAsArray();
                    int i = 0;

                    int cela[];
                    while (i < actors.length && lliure) {
                        cela = novaCambra.getCela(actors[i].getPosicioInicial()[0],
                                actors[i].getPosicioInicial()[1]);
                        lliure = fila != cela[0] || col != cela[1];
                        i++;
                    }
                }

                posicio = novaCambra.getPosicioCela(fila, col);
                trobada = !testMur(context, posicio[0], posicio[1], arqueologa.getLimits());
            }
            temple.setNumCambra(numCambra);
            arqueologa.setPosicio(posicio[0], posicio[1]);
        }
    }

    // *********************************************************
    // ****** private methods***********************************
    // *********************************************************

    private void init() {
        imatges = new Image[2];
        BufferedImage iTmp = Util.carregarImatge("cindy_low.png"); 
        imatges[0] = Util.flipImatgeHor(iTmp);
        imatges[1] = iTmp;
        
        
    }

    private void calcularNivellVida(Context ctx) {
        long t = ctx.getTempsFrame();
        float dany = DANY_PER_SEGON * t / 1000.f;
        // TODO alumnes: descomentar i implementar les funcions que falten
        // setVida(Math.max(0, getVida() - dany)); 
    }
    
    
}
