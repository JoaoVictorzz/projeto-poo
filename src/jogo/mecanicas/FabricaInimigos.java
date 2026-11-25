package jogo.mecanicas;

import jogo.entidades.inimigos.*;
import jogo.entidades.Nave;
import java.util.Random;

public class FabricaInimigos {
    private Random random = new Random();

    //Usa o metodo sortearTipoInimigo para definir qual inimigo será criado.
    public Inimigo criarInimigo(int larguraTela, int alturaTela, Nave nave) {
        switch (sortearTipoInimigo()) {
            case 0 -> {
                double posicaoXInimigo = random.nextDouble(0,
                        larguraTela - InimigoComum.LARGURA);

                return new InimigoComum(posicaoXInimigo, -InimigoComum.ALTURA);

            }
            case 1 -> {
                //Se for true, o inimigo nasce no lado esquerdo.
                boolean ladoNaEsquerda = random.nextBoolean();
                double posicaoYinimigo = random.nextDouble(0, alturaTela - InimigoVeloz.ALTURA);

                if(ladoNaEsquerda) {
                    return new InimigoVeloz(-InimigoVeloz.LARGURA, posicaoYinimigo, 1);
                }
                else {
                    return new InimigoVeloz(larguraTela, posicaoYinimigo, -1);

                }
            }
            case 2 -> {
                double posicaoXInimigo = random.nextDouble(0, larguraTela-InimigoKamikaze.LARGURA);
                return new InimigoKamikaze(posicaoXInimigo, -InimigoKamikaze.ALTURA, nave);
            }
            case 3 -> {
                double posicaoXInimigo = random.nextDouble(0, larguraTela- InimigoTanque.LARGURA);
                return new InimigoTanque(posicaoXInimigo, -InimigoTanque.ALTURA, nave);
            }
            default -> {
                throw new IllegalStateException("Inimigo inválido");
            }
        }

    }

    //Devolve o tipo do inimigo.
    private int sortearTipoInimigo() {
        int indice = random.nextInt(15) + 1;
        if(indice <= 5) {
            return 0;
        }
        else if(indice <= 8) {
            return 1;
        }
        else if(indice <= 11) {
            return 2;
        }
        else if(indice <= 14){
            return 3;
        }
        else {
            return 0;
        }
    }
}
