package telas;

import jogo.mecanicas.Botao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TelaInicial {
    private static final int BOTAO_LARGURA = 160;
    private static final int BOTAO_ALTURA = 50;
    private static final int TITULO_Y = 300;
    private static final int DISTANCIA_ENTRE_ELEMENTOS = 100;
    private static float opacidade = 0.0f;
    private final Botao botaoJogar;
    private final Botao botaoSair;
    private final BufferedImage imagemFundo;


    public TelaInicial(int larguraTela, int alturaTela) {
        imagemFundo = carregarImagem("/imagemTelaInicial.png");
        int xBotao = (larguraTela - BOTAO_LARGURA) / 2;
        int yBotao = (alturaTela - BOTAO_ALTURA) / 2;
        botaoJogar = new Botao(xBotao, yBotao, BOTAO_LARGURA, BOTAO_ALTURA, "JOGAR");
        botaoSair = new Botao(xBotao, yBotao + DISTANCIA_ENTRE_ELEMENTOS, BOTAO_LARGURA, BOTAO_ALTURA, "SAIR");
    }

    public void desenhar(Graphics2D g, int largura, int altura) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);
        g.drawImage(imagemFundo, 0, 0, largura, altura, null);

        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.setColor(Color.WHITE);
        UtilidadesTelas.desenharTextoCentralizado(g, "SPACE SURVIVOR", largura / 2, TITULO_Y);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        botaoJogar.desenhar(g);
        botaoSair.desenhar(g);
    }

    private BufferedImage carregarImagem(String caminho) {
        try {
            return ImageIO.read(getClass().getResource(caminho));
        } catch (IOException e) {
            System.out.println("Erro ao carregar imagem: " + caminho);
            return null;
        }
    }

    public boolean clicouJogar(int mouseX, int mouseY) {
        return botaoJogar.detectarClique(mouseX, mouseY);
    }

    public boolean clicouSair(int mouseX, int mouseY) {
        return botaoSair.detectarClique(mouseX, mouseY);
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
