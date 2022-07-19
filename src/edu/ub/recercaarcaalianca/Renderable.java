package edu.ub.recercaarcaalianca;

import java.awt.*;

/**
 * Tots els objectes que es puguin dibuixar (fer el render) per pantalla han
 * d'implementar aquesta interficie.
 *
 * @author ub.edu
 */
public interface Renderable {

    void render(Graphics2D g);

}
