package edu.ub.recercaarcaalianca;

import edu.ub.recercaarcaalianca.actors.*;

import static edu.ub.recercaarcaalianca.Constants.*;

/**
 * Demo.
 */
public class ExempleMain {

    private final Joc joc;
    private final Temple temple;
    private final Arqueologa cindy_jones;

    private ExempleMain() {

        temple = new Temple(2, 3);
        temple.addCambra(0, 0, crearCambra0Nivell0());
        temple.addCambra(0, 1, crearCambra1Nivell0());
        temple.addCambra(0, 2, crearCambra2Nivell0());
        temple.addCambra(1, 0, crearCambra0Nivell1());
        temple.addCambra(1, 1, crearCambra1Nivell1());
        temple.addCambra(1, 2, crearCambra2Nivell1());

        cindy_jones = new Arqueologa();
        Cambra cam = temple.getCambra(0, 0);
        int[] p = cam.getPosicioCela(10, 10);
        cindy_jones.setPosicioInicial(p[0], p[1]);

        
        distribuirSoldats();
        distribuirDiamants();

        //initialitzacio del joc
        joc = new Joc(temple, cindy_jones);
        GuiJoc gui = new GuiJoc(joc);
    }

    /**
     * Principal.
     *
     * @param args
     */
    public static void main(String[] args) {
        new ExempleMain();
    }

    private Cambra crearCambra0Nivell0() {
        Cambra cam = Util.carregarCambra("c0_0.txt");

        Porta porta = cam.getPorta(14, 24);
        porta.setNumNivellDesti(0);
        porta.setNumCambraDesti(1);
        porta.setPosicioCambraDesti(cam.getPosicioCela(1, 1));

        porta = cam.getPorta(0, 10);
        porta.setNumNivellDesti(0);
        porta.setNumCambraDesti(2);
        porta.setPosicioCambraDesti(cam.getPosicioCela(14, 10));

        return cam;
    }

    private Cambra crearCambra1Nivell0() {
        Cambra h = Util.carregarCambra("c0_1.txt");

        Porta porta = h.getPorta(1, 0);
        porta.setNumNivellDesti(0);
        porta.setNumCambraDesti(0);
        porta.setPosicioCambraDesti(h.getPosicioCela(13, 22));


        return h;
    }

    private Cambra crearCambra2Nivell0() {
        Cambra h = Util.carregarCambra("c0_2.txt");
        Porta porta = h.getPorta(16, 10);
        porta.setNumNivellDesti(0);
        porta.setNumCambraDesti(0);
        porta.setPosicioCambraDesti(h.getPosicioCela(1, 10));

        porta = h.getPorta(0, 22);
        porta.setNumNivellDesti(1);
        porta.setNumCambraDesti(0);
        porta.setPosicioCambraDesti(h.getPosicioCela(14, 3));

        return h;
    }

    private Cambra crearCambra0Nivell1() {
        Cambra h = Util.carregarCambra("c1_0.txt");
        Porta porta = h.getPorta(16, 3);
        porta.setNumNivellDesti(0);
        porta.setNumCambraDesti(2);
        porta.setPosicioCambraDesti(h.getPosicioCela(2, 21));

        porta = h.getPorta(3,24);
        porta.setNumNivellDesti(1);
        porta.setNumCambraDesti(1);
        porta.setPosicioCambraDesti(h.getPosicioCela(10, 2));

        porta = h.getPorta(0, 14);
        porta.setNumNivellDesti(1);
        porta.setNumCambraDesti(2);
        porta.setPosicioCambraDesti(h.getPosicioCela(14, 3));

        return h;
    }

    private Cambra crearCambra1Nivell1() {
        Cambra h = Util.carregarCambra("c1_1.txt");
        Porta porta = h.getPorta(11, 0);
        porta.setNumNivellDesti(1);
        porta.setNumCambraDesti(0);
        porta.setPosicioCambraDesti(h.getPosicioCela(2, 22));

        return h;
    }

    private Cambra crearCambra2Nivell1() {
        Cambra h = Util.carregarCambra("c1_2.txt");
        Porta porta = h.getPorta(16, 3);
        porta.setNumNivellDesti(1);
        porta.setNumCambraDesti(0);
        porta.setPosicioCambraDesti(h.getPosicioCela(2, 14));

        return h;
    }

    private Cambra getRandCambra() {
        int nivell;
        int numCambra;
        Cambra cam;
        nivell = (int) (Math.random() * temple.getNumNivells());
        numCambra = (int) (Math.random() * temple.getNumCambres(nivell));
        cam = temple.getCambra(nivell, numCambra);
        return cam;
    }

    private void distribuirSoldats() {
        int [] num = {1,3,6}; //nombre de soldats a distribuir de cada tipus
         
        for (int i = 0; i < Soldat.TipusSoldat.values().length ; i++) {
            for(int k = 0; k < num[i] ; k++){
                int nivell = (int) (Math.random() * temple.getNumNivells());
                int numCambra = (int) (Math.random() * temple.getNumCambres(nivell));
                Cambra cam = temple.getCambra(nivell, numCambra);
                int[] cela = obtenirCelaLliure(cam);
                Soldat s = new Soldat(Soldat.TipusSoldat.values()[i]);  

                int[] posicio = cam.getPosicioCela(cela[0], cela[1]);
                s.setPosicioInicial(posicio[0], posicio[1]);
                cam.addActor(s);
            }
        }
    }
    private void distribuirDiamants() {
        int [] num = {2, 3, 3, 3, 2};

        for(int i = 0; i < COLORS_DIAMANTS.values().length; i++) {
            for(int j = 0; j < num[i]; j++) {
                int nivell = (int) (Math.random() * temple.getNumNivells());
                int numCambra = (int) (Math.random() * temple.getNumCambres(nivell));
                Cambra cam = temple.getCambra(nivell, numCambra);
                int[] cela = obtenirCelaLliure(cam);
                Diamant d = new Diamant(Constants.COLORS_DIAMANTS.values()[i]);

                int[] posicio = cam.getPosicioCela(cela[0], cela[1]);
                d.setPosicioInicial(posicio[0], posicio[1]);
                cam.addActor(d);
            }
        }
    }

    private int[] obtenirCelaLliure(Cambra cambra) {
        int fila = 0;
        int col = 0;
        int cela[];
        boolean trobada = false;
        boolean terra;

        while (!trobada) {
            terra = false;
            while (!terra) {
                fila = (int) Math.max(0, (Math.random() * NUM_CELES_VERTICALS - 2));
                col = (int) Math.max(0, (Math.random() * NUM_CELES_HORIZONTALS - 2));
                terra = (cambra.getValor(fila, col) == SIMBOL_TERRA &&
                        cambra.getValor(fila + 1, col) == SIMBOL_TERRA &&
                        cambra.getValor(fila, col + 1) == SIMBOL_TERRA &&
                        cambra.getValor(fila + 1, col + 1) == SIMBOL_TERRA);
            }

            // comprovar que cap actor esta dins la cela
            Actor[] actors = cambra.getActorsAsArray();
            int i = 0;
            boolean lliure = true;
            while (i < actors.length && lliure) {
                cela = cambra.getCela(actors[i].getPosicioInicial()[0],
                        actors[i].getPosicioInicial()[1]);
                lliure = fila != cela[0] || col != cela[1];
                i++;
            }

            //comprovar que el cindy_jones no esta dins la cela
            if (lliure) {
                cela = cambra.getCela(cindy_jones.getPosicioInicial()[0],
                        cindy_jones.getPosicioInicial()[1]);
                lliure = fila != cela[0] || col != cela[1];
            }

            trobada = lliure;
        }
        return new int[]{fila, col};
    }

}
