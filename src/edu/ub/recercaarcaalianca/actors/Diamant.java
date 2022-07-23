package edu.ub.recercaarcaalianca.actors;

import edu.ub.recercaarcaalianca.*;

import java.awt.*;

public class Diamant extends AbstractActor {
    private Image image;
    private final int amplada = 20;
    private final int alcada = 37;
    private Constants.COLORS_DIAMANTS color;

    public Diamant(Constants.COLORS_DIAMANTS color) {
        switch (color) {
            case VERMELL: //red
                image = Util.carregarImatge("Diamond.png", new Color(255, 0, 0, 0));
                break;
            case VERD: //green
                image = Util.carregarImatge("Diamond.png", new Color(0, 255, 0, 0));
                break;
            case BLAU: //blue
                image = Util.carregarImatge("Diamond.png", new Color(0, 0, 255, 0));
                break;
            case GROC: //yellow
                image = Util.carregarImatge("Diamond.png", new Color(255, 255, 0, 0));
                break;
            case TARONJA: //orange
                image = Util.carregarImatge("Diamond.png", new Color(255, 128, 64, 0));
                break;
        }
    }

    public Constants.COLORS_DIAMANTS getColor() {
        return color;
    }
    public void setColor(Constants.COLORS_DIAMANTS color){
        this.color = color;
    }

    public void actualitzar(Context context) {
        // no fem res, es estatic (no se mou)
    }
    public Rectangle getLimits() {
        // es important per tractar les colisions des de la classe GuiJoc al metode actualizarJoc
        return new Rectangle(getX(), getY(), amplada, alcada);
    }

    public void tractarColisio(Colisio colisio) {
        Actor actor = colisio.getActor();
        if (actor instanceof Arqueologa) {
            Arqueologa arqueologa = (Arqueologa) actor;

        }
    }

    public void render(Graphics2D g) {
        //Com dibuixar a la pantalla principal
        g.drawImage(image, getX(), getY(), observer);
    }
}