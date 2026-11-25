package jogo.entidades.inimigos;

import jogo.entidades.Nave;

import java.awt.*;

public class InimigoTanque extends Inimigo {
    public static final int LARGURA = 90;
    public static final int ALTURA = 90;
    private static final int VELOCIDADE = 8;
    private static final int PONTOS = 15;
    private int vidas = 3;
    private double rotacao;
    private final Nave alvo;

    public InimigoTanque(double posicaoX, double posicaoY, Nave alvo) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA, ALTURA,
                PONTOS, "/recursos/imagensInimigos/InimigoTanque.png");
        this.alvo = alvo;
    }

    //O kamikaze vai seguir a nave
    @Override
    public void mover() {
        this.guiarDirecaoInimigoTanque(alvo.getCentroY(), alvo.getCentroX());

        double distanciaX = alvo.getCentroX() - (posicaoX + LARGURA / 2.0);
        double distanciaY = alvo.getCentroY() - (posicaoY + ALTURA / 2.0);
        double distancia = Math.hypot(distanciaX, distanciaY);

        if (distancia > 0) {
            posicaoX += distanciaX / distancia * velocidade;
            posicaoY += distanciaY / distancia * velocidade;
        }
    }
    @Override
    public void receberDano() {
        vidas--;
        if (vidas <= 0) {
            super.receberDano();
        }
    }

    @Override
    public void desenhar(Graphics2D g) {
        double centroX = posicaoX + LARGURA / 2.0;
        double centroY = posicaoY + ALTURA / 2.0;
        double angulo = Math.atan2(alvo.getCentroY()- centroY, alvo.getCentroX()- centroX);

        Graphics2D gCopia = (Graphics2D) g.create();
        gCopia.rotate(angulo - Math.PI/2,centroX,centroY);
        gCopia.drawImage(imagemInimigo, (int) posicaoX, (int) posicaoY, LARGURA, ALTURA, null);
        gCopia.dispose();
    }

    public void guiarDirecaoInimigoTanque(double centroY, double centroX) {
        rotacao = Math.atan2(centroY - getCentroY(), centroX - getCentroX());
    }

    //Quando encosta na nave vai tirar uma vida e vai se destruir
    @Override
    public void atacar(Nave nave) {
        super.atacar(nave);
        receberDano();
    }

    public double getCentroX() {
        return posicaoX + LARGURA / 2.0;
    }

    public double getCentroY() {
        return posicaoY + ALTURA / 2.0;
    }

}
