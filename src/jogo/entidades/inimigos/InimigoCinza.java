package jogo.entidades.inimigos;

import jogo.entidades.Nave;

import java.awt.*;

public class InimigoCinza extends Inimigo{
    public static final int LARGURA = 60;
    public static final int ALTURA = 50;
    public static final int VELOCIDADE = 14;
    public static final int PONTOS = 7;
    //Sentido do movimento horizontal: 1 = direita, -1 = esquerda;
    private final int direcao;

    public InimigoCinza(double posicaoX, double posicaoY, int direcao) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA,
                ALTURA, PONTOS, "/recursos/imagens/inimigoCinza.png");
        this.direcao = direcao;
    }

    //Se move na horizontal.
    @Override
    public void mover() {
        posicaoX += velocidade * direcao;
    }

    @Override
    public void atacar(Nave nave) {
        
    }

}
