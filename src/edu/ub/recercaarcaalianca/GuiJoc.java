package edu.ub.recercaarcaalianca;

import edu.ub.recercaarcaalianca.actors.Actor;
import edu.ub.recercaarcaalianca.actors.Arqueologa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * Classe principal que controla i coordina el joc.
 */
public class GuiJoc implements KeyListener {

    private static final int NS_PER_FRAME = 1000 * 1000 * 1000 /
            Constants.FRAMES_PER_SEGON;
    int x;
    int y;
    private JFrame frame;
    private Canvas canvas;
    private Context context;
    private Marcador marcador;
    private final Joc joc;
    private final LogicaJoc logica;

    /**
     * Constructor que posa en marxa el joc proporcionat.
     *
     * @param joc el joc
     */
    public GuiJoc(Joc joc) {
        this.joc = joc;
        this.logica = new LogicaJoc(joc);
        init();
        run();
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * Listener dels events del teclat.
     *
     * @param e l'event
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                logica.exit();
                break;
            case KeyEvent.VK_M:
                logica.pausar();
                break;
            case KeyEvent.VK_S:
                logica.iniciar();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                context.updateKeys(e, true);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        context.updateKeys(e, false);
    }

    // private methods *********************************************************

    private void init() {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        frame = new JFrame("UB :: Programacio I- Cindy a la recerca de l'Arca de l'Aliança-");
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Constants.AMPLADA_FINESTRA,
                Constants.ALCADA_FINESTRA));

        frame.setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.addKeyListener(this);
        frame.pack();

        Point cp = ge.getCenterPoint();
        cp.translate(-frame.getWidth() / 2, -frame.getHeight() / 2);
        frame.setLocation(cp.x, cp.y);

        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        canvas.requestFocus();

        marcador = new Marcador();

        context = new Context(joc);
    }

    private void run() {
        boolean contentsLost;

        long tempsFramePrevi = System.currentTimeMillis();
        long tempsFrameFinal;

        while (logica.getEstat() != LogicaJoc.EstatJoc.EXIT) {

            long ara = System.currentTimeMillis();
            long duracioFrame = (ara - tempsFramePrevi);
            tempsFramePrevi = ara;
            tempsFrameFinal = System.nanoTime() + GuiJoc.NS_PER_FRAME;

            BufferStrategy bufferStrategy = this.canvas.getBufferStrategy();
            context.setTempsFrame(duracioFrame);

            // aixo controla els estats del joc
            switch (logica.getEstat()) {
                case MENU:
                    mostrarMenu(bufferStrategy);
                    break;
                case INICIANT:
                    break;
                case JUGANT:
                    actualitzarJoc();
                    doRender(bufferStrategy);
                    break;
                case GAMEOVER:
                    mostrarGameOver(bufferStrategy);
                    break;
                case PAUSAT:
                    break;
            }

            contentsLost = bufferStrategy.contentsLost();
            if (contentsLost) {
                if (bufferStrategy.contentsRestored())
                    contentsLost = false;
            } else {
                bufferStrategy.show();
            }

            esperarFiFrame(tempsFrameFinal);
        }
        System.out.println("Fins aviat!");
        System.exit(0);
    }

    private void actualitzarJoc() {
        Arqueologa arqueologa = (Arqueologa) joc.getArqueologa();
       //TODO pels alumnes, aqui s'ha de posar el final del joc
       // .... feu el codi aqui
       
        arqueologa.actualitzar(context);
        Cambra h = joc.getTemple().getCambra();
        for (Actor actor : h.getActors()) {
            actor.actualitzar(context);
                if (actor.getEstat() == Constants.ESTAT_ACTIU &&
                    arqueologa.getLimits().intersects(actor.getLimits())) {
                    Colisio colisio = new Colisio(arqueologa);
                    actor.tractarColisio(colisio);
                }
        }
        
       
    }

    private void esperarFiFrame(long tempsFrameFinal) {
        Thread.yield();
        while (System.nanoTime() < tempsFrameFinal) {
            Thread.yield();
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }

    private void doRender(BufferStrategy bufferStrategy) {
        Graphics g = bufferStrategy.getDrawGraphics();

        Graphics2D g2 = (Graphics2D) g;
        joc.getTemple().render(g2);
        Cambra h = joc.getTemple().getCambra();
        for (Actor actor : h.getActors()) {
            if (actor.getEstat() != Constants.ESTAT_INACTIU)
                actor.render(g2);
        }

        joc.getArqueologa().render(g2);

        marcador.render(context, g2);
    }

    private void mostrarMenu(BufferStrategy bufferStrategy) {
        Graphics g = bufferStrategy.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        dibuixarMarc(g2, Color.LIGHT_GRAY);

        Image image = Util.carregarImatge("cindy_low.png");
        g2.drawImage(image, 80, 50, frame);

        Font f = new Font("Dialog", Font.PLAIN, 30);
        g2.setFont(f);
        g2.setColor(Color.white);
        g2.drawString("* Cindy a la recerca de l'Arca de l'Aliança *", 160, 80);

        f = new Font("Dialog", Font.PLAIN, 16);
        g2.setFont(f);
        g2.drawString("'S' Nova Partida", 180, 110);
        g2.drawString("'ESC' Sortir", 330, 110);

        image = Util.carregarImatge("portada.jpg", 0, 0, 650, 400);
        g2.drawImage(image, 75, 135, frame);
    }


    private void mostrarGameOver(BufferStrategy strategy) {
        Graphics g = strategy.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        dibuixarMarc(g2, Color.RED);
        Arqueologa arqueologa = (Arqueologa) joc.getArqueologa();
        Font f = new Font("Dialog", Font.PLAIN, 30);
        String missatge = "";

        // TODO: actualitzar la variable missatge amb el que correspongui quan s'ha acabat el joc 
        // Situacions: 1. cindy ha trobat l'arca, 2. Cindy s'ha mort 
        // ... feu el codi aqui


        g2.setFont(f);
        g2.setColor(Color.white);
        g2.drawString(missatge, 40, 90);
        f = new Font("Dialog", Font.PLAIN, 14);
        g2.setFont(f);

        f = new Font("Dialog", Font.PLAIN, 14);
        g2.setFont(f);
        g2.drawString("'ESC' Sortir", 180, 180);
    }

    private void dibuixarMarc(Graphics2D g2, Color color) {
        Rectangle r = canvas.getBounds();
        r.setBounds((int) r.getX() + 20, (int) r.getY() + 20, (int) (r.getWidth() - 40),
                (int) (r.getHeight() - 40));

        g2.setColor(Color.BLACK);
        g2.fill(canvas.getBounds());
        g2.setColor(color);
        g2.setStroke(new BasicStroke(10.f, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND, 20.0f));
        g2.draw(r);
    }
}
