package telas;

import jogo.mecanicas.Botao;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TelaInicial {
    private static float opacidade = 0.0f;
        private Botao jogar = new Botao(680, 360, 140, 50, "JOGAR");
        private Botao sair = new Botao(680, 440, 140, 50, "SAIR");
        private BufferedImage imagemFundo;

    public TelaInicial() {
        this.imagemFundo = carregarImagem("/recursos/imagemTelaInicial.png");
    }

    public static float getOpacidade() {
        return opacidade;
    }

    public static void aumentarOpacidade() {
        if (opacidade < 1.0f) {
            opacidade += 0.01f;
            if (opacidade > 1.0f) {
                opacidade = 1.0f;
            }
        }
    }

    public void desenhar(Graphics2D g, int largura,
                         int altura) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, largura, altura);
        g.drawImage(imagemFundo, 0, 0, largura, altura, null);

        g.setFont(new Font("Monospaced", Font.BOLD, 70));
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.WHITE);

        String titulo = "SPACE SURVIVOR";

        int x = (largura - fm.stringWidth(titulo)) / 2;
        int y = 300;
        g.drawString(titulo, x, y);

        g.setFont(new Font("Arial", Font.PLAIN, 30));
        jogar.desenhar(g);
        sair.desenhar(g);
    }

    private BufferedImage carregarImagem(String caminho) {
        try {
            return ImageIO.read(getClass().getResource(caminho));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem: " + caminho, e);
        }
    }
    public boolean clicouJogar(int mouseX, int mouseY) {
        return jogar.detectarClique(mouseX, mouseY);
    }
    public boolean clicouSair(int mouseX, int mouseY) {
        return sair.detectarClique(mouseX, mouseY);
    }
}
