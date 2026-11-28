package telas;

import jogo.mecanicas.Botao;

import java.awt.*;

public class TelaVitoria {
    private static final int BOTAO_LARGURA = 160;
    private static final int BOTAO_ALTURA = 50;
    private static final int TITULO_Y = 200;
    private static final int DISTANCIA_ENTRE_ELEMENTOS = 100;
    private static final Color COR_FUNDO_VITORIA = new Color(10, 10, 15);
    private final Botao botaoMenu;

    public TelaVitoria(int larguraTela, int alturaTela) {
        int xBotao = (larguraTela - BOTAO_LARGURA) / 2;
        int yBotao = (alturaTela - BOTAO_ALTURA) / 2;

        botaoMenu = new Botao(xBotao, yBotao, BOTAO_LARGURA, BOTAO_ALTURA, "MENU");
    }

    public void desenhar(Graphics2D g, int larguraTela, int alturaTela, int pontuacao) {
        g.setPaint(COR_FUNDO_VITORIA);
        g.fillRect(0, 0, larguraTela, alturaTela);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        UtilidadesTelas.desenharTextoCentralizado(g, "VITÓRIA", larguraTela / 2, TITULO_Y);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        UtilidadesTelas.desenharTextoCentralizado(g, "Pontuação: " + pontuacao,
                larguraTela / 2, TITULO_Y + DISTANCIA_ENTRE_ELEMENTOS);

        botaoMenu.desenhar(g);

    }

    public boolean clicouMenu(int mouseX, int mouseY) {
        return botaoMenu.detectarClique(mouseX, mouseY);
    }

}
