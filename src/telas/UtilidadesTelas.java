package telas;

import java.awt.*;

public final class UtilidadesTelas {

    public UtilidadesTelas() {

    }

    public static void desenharTextoCentralizado(Graphics2D g, String texto, int centroX, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = centroX - fm.stringWidth(texto) / 2;
        g.drawString(texto, x, y);
    }


    public static float aumentarOpacidade(float opacidade) {
        if (opacidade < 1.0f) {
            opacidade += 0.1f;

            if (opacidade > 1.0f) {
                opacidade = 1.0f;
            }
        }

        return opacidade;
    }

    public static float reiniciarOpacidade() {
        return 0.0f;
    }

    public static boolean opacidadeCompleta(float opacidade) {
        return opacidade >= 1.0f;
    }
}
