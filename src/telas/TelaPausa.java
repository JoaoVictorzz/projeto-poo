package telas;

import jogo.mecanicas.Botao;

import java.awt.*;

public class TelaPausa {
    private static final int PAINEL_LARGURA = 550;
    private static final int PAINEL_ALTURA = 450;
    private static final int BOTAO_ALTURA = 50;
    private static final int BOTAO_LARGURA = 160;
    private static final int RAIO_BORDA_PAINEL = 15;
    private static final int TITULO_Y = 90;
    private static final int PONTUACAO_Y = 410;
    private static final Color COR_PAINEL = new Color(12, 15, 23);
    private final int painelX;
    private final int painelY;
    private final Botao botaoContinuar;
    private final Botao botaoMenu;

    public TelaPausa(int larguraTela, int alturaTela) {
        painelX = (larguraTela - PAINEL_LARGURA) / 2;
        painelY = (alturaTela - PAINEL_ALTURA) / 2;

        int xBotao = painelX + (PAINEL_LARGURA - BOTAO_LARGURA) / 2;
        botaoContinuar = new Botao(xBotao, painelY + 160, BOTAO_LARGURA, BOTAO_ALTURA, "CONTINUAR");
        botaoMenu = new Botao(xBotao, painelY + 240, BOTAO_LARGURA, BOTAO_ALTURA, "MENU");
    }

    public void desenhar(Graphics2D g, int largura, int altura, int pontuacao) {
        Composite compositeOriginal = g.getComposite();

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);
        g.setComposite(compositeOriginal);

        g.setColor(COR_PAINEL);
        g.setStroke(new BasicStroke(5));
        g.fillRoundRect(painelX, painelY, PAINEL_LARGURA, PAINEL_ALTURA, RAIO_BORDA_PAINEL, RAIO_BORDA_PAINEL);
        g.setColor(Color.WHITE);
        g.drawRoundRect(painelX, painelY, PAINEL_LARGURA, PAINEL_ALTURA, RAIO_BORDA_PAINEL, RAIO_BORDA_PAINEL);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        UtilidadesTelas.desenharTextoCentralizado(g, "PAUSADO",
                largura / 2, TITULO_Y + painelY);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        botaoContinuar.desenhar(g);
        botaoMenu.desenhar(g);
        UtilidadesTelas.desenharTextoCentralizado(g, "Pontuação: " + pontuacao,
                largura / 2,PONTUACAO_Y + painelY);
    }

    public boolean clicouContinuar(int mouseX, int mouseY) {
        return botaoContinuar.detectarClique(mouseX, mouseY);
    }

    public boolean clicouMenu(int x, int y) {
        return botaoMenu.detectarClique(x, y);
    }

}
