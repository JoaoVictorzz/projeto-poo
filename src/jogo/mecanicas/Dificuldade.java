package jogo.mecanicas;

import java.util.Random;

public class Dificuldade {
    private static final double[] CHANCES_DE_SPAWN = {0.02, 0.08, 0.11, 0.14};
    private static final int[] INTERVALOS_DE_FRAME = {35, 30, 20, 15};
    private static final int[] LIMITES_DE_NIVEL = {200, 600, 1000};
    private Random random = new Random();

    //Níveis de 0 - 3, determina o nível de dificuldade com base na pontuação.
    private int calcularNivel(int pontuacao) {
        for(int i = 0; i < LIMITES_DE_NIVEL.length; i++) {
            if(pontuacao <= LIMITES_DE_NIVEL[i]) {
                return i;
            }
        }
        return LIMITES_DE_NIVEL.length;
    }

    //Calcula a probabilidade de spawn de inimigos pela quantidade de pontos.
    public double chanceSpawn(int pontuacao) {
        return CHANCES_DE_SPAWN[calcularNivel(pontuacao)];
    }

    //Calcula o intervalo de frames entre o surgimento de dois inimigos.
    //Esse intervalo diminui conforme a pontuação aumenta, fazendo os inimigos surgirem mais rapidamente.
    public int intervaloSpawn(int pontuacao) {
        return INTERVALOS_DE_FRAME[calcularNivel(pontuacao)];
    }

    //Decide se um inimigo deve spawnar.
    public boolean deveSpawnar(int pontuacao) {
        return random.nextDouble() < chanceSpawn(pontuacao);
    }
}
