package jogo.mecanicas;

import jogo.entidades.inimigos.Inimigo;
import jogo.entidades.inimigos.InimigoCinza;
import jogo.entidades.inimigos.InimigoAzul;
import jogo.entidades.inimigos.InimigoKamikaze;
import jogo.entidades.Nave;
import java.util.Random;

public class FabricaInimigos {
    private Random random = new Random();

    //Usa o metodo sortearTipoInimigo para definir qual inimigo será criado.
    public Inimigo criarInimigo(int larguraTela, int alturaTela, Nave nave) {
        switch (sortearTipoInimigo()) {
            case 0 -> {
                double posicaoXInimigo = random.nextDouble(0,
                        larguraTela - InimigoAzul.LARGURA);

                return new InimigoAzul(posicaoXInimigo, -InimigoAzul.ALTURA);

            }
            case 1 -> {
                //Se for true, o inimigo nasce no lado esquerdo.
                boolean ladoNaEsquerda = random.nextBoolean();
                double posicaoYinimigo = random.nextDouble(0, alturaTela - InimigoCinza.ALTURA);

                if(ladoNaEsquerda) {
                    return new InimigoCinza(-InimigoCinza.LARGURA, posicaoYinimigo, 1);
                }
                else {
                    return new InimigoCinza(larguraTela, posicaoYinimigo, -1);

                }
            }
            case 2 -> {
                double posicaoXInimigo = random.nextDouble(0, larguraTela-InimigoKamikaze.LARGURA);
                return new InimigoKamikaze(posicaoXInimigo, -InimigoKamikaze.ALTURA, nave);
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
        else {
            return 0;
        }
    }
}
