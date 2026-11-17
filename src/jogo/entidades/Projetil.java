package jogo.entidades;

import java.awt.*;

public class Projetil {
    private static final int VELOCIDADE = 27;
    private static final int LARGURA = 8;
    private static final int ALTURA = 8;
    private static final Color COR = Color.RED;
    private double posicaoX, posicaoY;
    private double velocidadeX, velocidadeY;
    private boolean colidiu = false;

    public Projetil(double posicaoX, double posicaoY, double angulo) {
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        //mantem na diagonal, cos e sin
        this.velocidadeX = Math.cos(angulo) * VELOCIDADE;
        this.velocidadeY = Math.sin(angulo) * VELOCIDADE;


    }

    public void desenhar(Graphics2D g) {
        g.setColor(COR);
        g.fillOval((int) posicaoX, (int) posicaoY, LARGURA, ALTURA);
    }

    public void mover() {
        posicaoX += velocidadeX;
        posicaoY += velocidadeY;
    }

    //Retorna as medidas do projétil para ser usado na verificação de colisão.
    public Rectangle getRetangulo() {
        return new Rectangle((int) posicaoX, (int) posicaoY, LARGURA, ALTURA);
    }

    public void marcarColidiu() {
        this.colidiu = true;
    }

    public boolean isAtivo() {
        return !colidiu;
    }

}
