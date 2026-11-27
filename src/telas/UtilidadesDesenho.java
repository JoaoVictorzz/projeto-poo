package telas;

import java.awt.*;

public final class UtilidadesDesenho {

    public UtilidadesDesenho() {

    }

    public static void desenharTextoCentralizado(Graphics2D g, String texto, int centroX, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = centroX - fm.stringWidth(texto) / 2;
        g.drawString(texto, x, y);
    }
}
