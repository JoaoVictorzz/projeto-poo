
package telas;

import java.awt.*;

public class TelaFimDeJogo {

    public static void desenhar(Graphics2D g, int largura,
                                int altura, int pontuacao) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);

        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));

        String mensagem = "FIM DE JOGO";
        FontMetrics fm = g.getFontMetrics();

        int x = (largura - fm.stringWidth(mensagem)) / 2 - 120;
        int y = altura / 2 - 70;

        g.drawString(mensagem, x, y);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));

        String pontos = "Pontuação: " + pontuacao;
        fm = g.getFontMetrics();

        x = (largura - fm.stringWidth(pontos)) / 2 - 120;

        g.drawString(pontos, x, y + 60);
    }
}
