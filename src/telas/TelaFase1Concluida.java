
package telas;

import jogo.mecanicas.Botao;

import java.awt.*;

public class TelaFase1Concluida {

    private final Botao botaoContinuar;

    public TelaFase1Concluida(int larguraTela, int alturaTela) {
        int botaoLargura = 180;
        int botaoAltura = 55;

        int botaoX = (larguraTela - botaoLargura) / 2;
        int botaoY = alturaTela / 2 + 80;

        botaoContinuar = new Botao(
                botaoX, botaoY,
                botaoLargura, botaoAltura,
                "CONTINUAR"
        );
    }

    public void desenhar(Graphics2D g, int largura, int altura, int pontuacao) {
        Composite compositeOriginal = g.getComposite();

        g.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.90f));

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);

        g.setComposite(compositeOriginal);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));

        UtilidadesTelas.desenharTextoCentralizado(
                g, "FASE 1 CONCLUÍDA",
                largura / 2, altura / 2 - 100
        );

        g.setFont(new Font("Arial", Font.BOLD, 25));

        UtilidadesTelas.desenharTextoCentralizado(
                g, "Pontuação: " + pontuacao,
                largura / 2, altura / 2 - 20
        );

        g.setFont(new Font("Arial", Font.BOLD, 20));
        botaoContinuar.desenhar(g);
    }

    public boolean clicouContinuar(int mouseX, int mouseY) {
        return botaoContinuar.detectarClique(mouseX, mouseY);
    }
}
