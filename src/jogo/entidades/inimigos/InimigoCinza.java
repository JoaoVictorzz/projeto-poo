package jogo.entidades.inimigos;

public class InimigoCinza extends Inimigo{
    public static final int LARGURA = 70;
    public static final int ALTURA = 60;
    public static final int VELOCIDADE = 15;
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

}
