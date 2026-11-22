package jogo.entidades.inimigos;

import jogo.entidades.Nave;

public class InimigoKamikaze extends Inimigo {
    public static final int LARGURA = 40;
    public static final int ALTURA = 30;
    private static final int VELOCIDADE = 8;
    private static final int PONTOS = 3;

    private final Nave alvo;

    public InimigoKamikaze(double posicaoX, double posicaoY, Nave alvo) {
        super(posicaoX, posicaoY, VELOCIDADE, LARGURA, ALTURA,
                PONTOS, "/recursos/imagens/InimigoKamikaze.png");
        this.alvo = alvo;
    }

    //O kamikaze vai seguir a nave
    @Override
    public void mover() {
        double distanciaX = alvo.getCentroX() - (posicaoX + LARGURA / 2.0);
        double distanciaY = alvo.getCentroY() - (posicaoY + ALTURA / 2.0);
        double distancia = Math.hypot(distanciaX, distanciaY);

        if (distancia > 0) {
            posicaoX += distanciaX / distancia * velocidade;
            posicaoY += distanciaY / distancia * velocidade;
        }
    }

    //Quando encosta na nave vai tirar uma vida e vai se destruir
    @Override
    public void atacar(Nave nave) {
        super.atacar(nave);
        receberDano();
    }

}
