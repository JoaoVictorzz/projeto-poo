package jogo.mecanicas;

import jogo.entidades.inimigos.Inimigo;
import jogo.entidades.inimigos.InimigoCinza;
import jogo.entidades.inimigos.InimigoNave;
import java.util.Random;

public class FabricaInimigos {
    private Random random = new Random();

    //Usa o metodo sortearTipoInimigo para definir qual inimigo será criado.
    public Inimigo criarInimigo(int larguraTela, int alturaTela) {
        switch (sortearTipoInimigo()) {
            case 0 -> {
                //metodo disso dps
                double posicaoXInimigo = random.nextDouble(0,
                        larguraTela - InimigoNave.LARGURA);

                return new InimigoNave(posicaoXInimigo, -InimigoNave.ALTURA);

            }
            case 1 -> {
                boolean ladoNaEsquerda = random.nextBoolean();
                double posicaoYinimigo = random.nextDouble(0, alturaTela - InimigoCinza.ALTURA);
                if(ladoNaEsquerda) {
                    return new InimigoCinza(-InimigoCinza.LARGURA, posicaoYinimigo, 1);
                }
                else {
                    return new InimigoCinza(larguraTela, posicaoYinimigo, -1);

                }
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
        else {
            return 0;
        }
    }
}
