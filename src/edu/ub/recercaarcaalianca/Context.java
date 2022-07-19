package edu.ub.recercaarcaalianca;

import java.awt.event.KeyEvent;

/**
 * El context del joc representa l'estat del joc en un moment especific en el
 * temps.
 *
 * @author ub.edu
 */
public class Context {

    public static final int KEY_UP = 0;
    public static final int KEY_DOWN = 1;
    public static final int KEY_LEFT = 2;
    public static final int KEY_RIGHT = 3;
    private long tempsFrame;
    private final Joc joc;
    private final boolean[] keyMap = {false, false, false, false};
    private long tempsEnemicInmovilitzat;

    public Context(Joc joc) {
        this.joc = joc;
    }

    /**
     * Obte el joc.
     *
     * @return el joc
     */
    public Joc getJoc() {
        return joc;
    }

    /**
     * Obte l'Cambra actual.
     *
     * @return l'Cambra
     */
    public Cambra getCambra() {
        return joc.getTemple().getCambra();
    }

    public boolean isKeyPressed(int key) {
        return keyMap[key];
    }

    public void updateKeys(KeyEvent e, boolean pressed) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                keyMap[KEY_UP] = pressed;
                break;
            case KeyEvent.VK_DOWN:
                keyMap[KEY_DOWN] = pressed;
                break;
            case KeyEvent.VK_LEFT:
                keyMap[KEY_LEFT] = pressed;
                break;
            case KeyEvent.VK_RIGHT:
                keyMap[KEY_RIGHT] = pressed;
                break;
        }
    }

    /**
     * Obte el temps transcorregut en milisegons entre el frame anterior i
     * l'actual.
     *
     * @return el temps en ms.
     */
    public long getTempsEnemicInmovilitzat() {
        return tempsEnemicInmovilitzat;
    }

    public void setTempsEnemicInmovilitzat(long timer) {
        this.tempsEnemicInmovilitzat = timer;
    }

    public long getTempsFrame() {
        return tempsFrame;
    }

    public void setTempsFrame(long tempsFrame) {
        this.tempsFrame = tempsFrame;
    }

}
