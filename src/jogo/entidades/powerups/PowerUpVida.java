package jogo.entidades.powerups;

import jogo.entidades.Nave;


public class PowerUpVida extends PowerUp {
    private static final int Largura = 28;
    private static final int Altura = 28;
    private static final String CAMINHO_IMAGEM = "/imagempowerups/Vida.png";

    public PowerUpVida(double posicaoX, double posicaoY) {
        super(posicaoX, posicaoY, Largura, Altura, CAMINHO_IMAGEM);
    }
    @Override
    public void aplicarEfeito(Nave nave){
        nave.ganharVida();
    }
}