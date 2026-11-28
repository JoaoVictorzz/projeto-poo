package jogo.entidades.inimigos;

import jogo.entidades.Nave;

import java.awt.*;

public class InimigoKamikaze extends Inimigo {
    public static final int LARGURA = 60;
    public static final int ALTURA = 60;
    private static final int VELOCIDADE = 10;
    private static final int PONTOS = 9;
    private double rotacao;

    private final Nave alvo;

    public InimigoKamikaze(double posicaoX, double posicaoY, Nave alvo) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA, ALTURA,
                PONTOS, "/imagensInimigos/inimigoKamikaze.png");
        this.alvo = alvo;
    }

    //O kamikaze vai seguir a nave
    @Override
    public void mover() {
        this.guiarDirecaoInimigoKamikaze(alvo.getCentroY(), alvo.getCentroX());

        double distanciaX = alvo.getCentroX() - (posicaoX + LARGURA / 2.0);
        double distanciaY = alvo.getCentroY() - (posicaoY + ALTURA / 2.0);
        double distancia = Math.hypot(distanciaX, distanciaY);

        if (distancia > 0) {
            posicaoX += distanciaX / distancia * velocidade;
            posicaoY += distanciaY / distancia * velocidade;
        }
    }

    @Override
    public void desenhar(Graphics2D g) {
        Graphics2D gCopia = (Graphics2D) g.create();
        gCopia.rotate(rotacao, getCentroX(), getCentroY());
        gCopia.drawImage(getImagemInimigo(), (int) posicaoX, (int) posicaoY, LARGURA, ALTURA, null);
        gCopia.dispose();
    }

    public void guiarDirecaoInimigoKamikaze(double centroY, double centroX) {
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
