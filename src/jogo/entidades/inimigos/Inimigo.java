package jogo.entidades.inimigos;

import jogo.entidades.Nave;
import jogo.entidades.ObjetoJogo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Inimigo implements ObjetoJogo {
    protected double posicaoX, posicaoY;
    protected final double velocidade;
    private final int largura, altura;
    private final int pontos;
    private boolean vivo = true;
    private final BufferedImage imagemInimigo;

    public Inimigo(double posicaoX, double posicaoY,
                   double velocidade, int largura, int altura,
                   int pontos, String caminhoImagem) {
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.velocidade = velocidade;
        this.largura = largura;
        this.altura = altura;
        this.pontos = pontos;
        this.imagemInimigo = carregarImagem(caminhoImagem);
    }

    //Lê a imagem do InimigoNave, captura erros que ocorrerem na leitura da imagem.
    //Devolve null se falhar.
    private BufferedImage carregarImagem(String caminho) {
        try {
            return ImageIO.read(getClass().getResource(caminho));
        }
        catch (IOException  e) {
            System.out.println("Erro na leitura da imagem: " + caminho);
            return null;
        }
    }

    @Override
    public void desenhar(Graphics2D g) {
        g.drawImage(imagemInimigo, (int) posicaoX, (int) posicaoY, largura, altura, null);
    }

    @Override
    //Retorna as medidas de InimigoNave, usado no teste de colisões.
    public Rectangle getRetangulo() {
        return new Rectangle((int) posicaoX, (int) posicaoY, largura, altura);
    }

    @Override
    public boolean isAtivo() {
        return vivo;
    }

    public abstract void mover();

    public void atacar(Nave nave) {
        nave.receberDano();
    };

    //Sinaliza que Inimigo morreu.
    public void receberDano() {
        this.vivo = false;
    }

    public int getPontos() {
        return pontos;
    }

    public BufferedImage getImagemInimigo() {
        return imagemInimigo;
    }
}