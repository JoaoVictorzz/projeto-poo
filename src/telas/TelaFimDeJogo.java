
package telas;

import jogo.mecanicas.Botao;

import java.awt.*;

public class TelaFimDeJogo {
    private static final int BOTAO_LARGURA = 160;
    private static final int BOTAO_ALTURA = 50;
    private static final int TITULO_Y = 200;
    private static final int DISTANCIA_ENTRE_ELEMENTOS = 100;
    private static final Color COR_TITULO = new Color(120, 10, 10);
    private static final Color COR_FUNDO_FIM_DE_JOGO = new Color(10, 10, 15);
    private float opacidade = 0.0f;
    private final Botao botaoReiniciar;
    private final Botao botaoMenu;

    public TelaFimDeJogo(int larguraTela, int alturaTela) {
        int xBotao = (larguraTela - BOTAO_LARGURA) / 2;
        int yBotao = (alturaTela - BOTAO_ALTURA) / 2;

        botaoReiniciar = new Botao(xBotao, yBotao, BOTAO_LARGURA, BOTAO_ALTURA, "REINICIAR");
        botaoMenu = new Botao(xBotao, yBotao + DISTANCIA_ENTRE_ELEMENTOS, BOTAO_LARGURA, BOTAO_ALTURA, "MENU");
    }

    public void desenhar(Graphics2D g, int largura,
                         int altura, int pontuacao) {
        g.setColor(COR_FUNDO_FIM_DE_JOGO);
        g.fillRect(0, 0, largura, altura);

        g.setColor(COR_TITULO);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        UtilidadesTelas.desenharTextoCentralizado(g, "FIM DE JOGO", largura / 2, TITULO_Y);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        UtilidadesTelas.desenharTextoCentralizado(g, "Pontuação: " + pontuacao,
                largura / 2, TITULO_Y + DISTANCIA_ENTRE_ELEMENTOS);

        botaoReiniciar.desenhar(g);
        botaoMenu.desenhar(g);
    }

    public boolean clicouReiniciar(int mouseX, int mouseY) {
        return botaoReiniciar.detectarClique(mouseX, mouseY);
    }

    public boolean clicouMenu(int mouseX, int mouseY) {
        return botaoMenu.detectarClique(mouseX, mouseY);
    }

    public float getOpacidade() {
        return opacidade;
    }

    public void aumentarOpacidade() {
        opacidade = UtilidadesTelas.aumentarOpacidade(opacidade);
    }

    public void reiniciarOpacidade() {
        opacidade = 0.0f;
    }
}
