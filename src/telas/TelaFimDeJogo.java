
package telas;

import jogo.mecanicas.Botao;

import java.awt.*;

public class TelaFimDeJogo {
    private  float opacidade = 0.0f;
    private Botao reiniciar = new Botao(680, 360, 140, 50, "REINICIAR");
    private Botao menu = new Botao(680, 440, 140, 50, "MENU");

    public float getOpacidade() {
        return opacidade;
    }

    public  void aumentarOpacidade() {
        if (opacidade < 1.0f) {
            opacidade += 0.01f;
            if(opacidade > 1.0f) {
                opacidade = 1.0f;
            }
        }
    }

    public void opacidadePadrao() {
        this.opacidade = 0.0f;
    }

    public void desenhar(Graphics2D g, int largura,
                                int altura, int pontuacao) {

        g.setPaint(new LinearGradientPaint(
                0, 0, 0, altura,
                new float[]{0f, 0.5f, 1f},
                new Color[]{Color.BLACK, new Color(36, 40, 30), Color.BLACK}
        ));
        g.fillRect(0, 0, largura, altura);

        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));

        String mensagem = "FIM DE JOGO";
        FontMetrics fm = g.getFontMetrics();

        int x = (largura - fm.stringWidth(mensagem)) / 2 - 120;
        int y = altura / 2 - 70;

        g.drawString(mensagem, x, y);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 30));

        String pontos = "Pontuação: " + pontuacao;
        fm = g.getFontMetrics();

        x = (largura - fm.stringWidth(pontos)) / 2 - 120;

        g.drawString(pontos, x, y + 60);
    }

    public boolean clicouReiniciar(int mouseX, int mouseY) {
        return reiniciar.detectarClique(mouseX, mouseY);
    }
    public boolean clicouMenu(int mouseX, int mouseY) {
        return menu.detectarClique(mouseX, mouseY);
    }
}
