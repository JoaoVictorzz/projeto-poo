package jogo.mecanicas;

import java.awt.*;

public class Botao {
    private double x, y;
    private int largura, altura;
    private Rectangle limites;
    private String texto;


    public Botao(double x, double y, int largura, int altura, String texto) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.texto = texto;

        limites = new Rectangle((int) x, (int) y, largura, altura);
    }

    public void desenhar(Graphics2D g) {
        g.setStroke(new BasicStroke(1));
        g.drawRect((int) x, (int) y, largura, altura);
        FontMetrics fm = g.getFontMetrics();
        int posicaoX =  (int) x + (largura - fm.stringWidth(texto)) / 2;
        int posicaoY = (int) y + (altura + fm.getAscent()) / 2;
        g.drawString(texto, posicaoX,  posicaoY);
    }

    public boolean detectarClique(int mouseX, int mouseY) {
        if(limites.contains(mouseX, mouseY)) {
            return true;
        }
        return false;
    }
}
