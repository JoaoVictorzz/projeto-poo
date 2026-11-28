package jogo.entidades.inimigos;

public class InimigoComum extends Inimigo {
    public static final int LARGURA = 70;
    public static final int ALTURA = 60;
    private static final int VELOCIDADE = 11;
    private static final int PONTOS = 5;

    public InimigoComum(double posicaoX, double posicaoY) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA, ALTURA,
                PONTOS, "/imagensInimigos/inimigoComum.png");
    }

    //Inimigo desce em linha reta.
    @Override
    public void mover() {
        posicaoY += velocidade;
    }

}