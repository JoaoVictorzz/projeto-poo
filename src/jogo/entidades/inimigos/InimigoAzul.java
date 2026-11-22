package jogo.entidades.inimigos;

import jogo.entidades.Nave;

public class InimigoAzul extends Inimigo {
    public static final int LARGURA = 70;
    public static final int ALTURA = 60;
    private static final int VELOCIDADE = 11;
    private static final int PONTOS = 5;

    public InimigoAzul(double posicaoX, double posicaoY) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA, ALTURA,
                PONTOS, "/recursos/imagens/inimigoAzul.png");
    }

    //Inimigo desce em linha reta.
    @Override
    public void mover() {
        posicaoY += velocidade;
    }

}