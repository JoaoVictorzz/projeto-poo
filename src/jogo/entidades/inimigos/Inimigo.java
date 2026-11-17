package jogo.entidades.inimigos;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Inimigo {
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

    //Lê a imagem do InimigoNave, captura erros se a imagem não for encontrada ou se ocorrer erro na leitura.
    //Devolve null se falhar.
    private BufferedImage carregarImagem(String caminho) {
        try {
            return ImageIO.read(getClass().getResource(caminho));
        }
        catch (IOException  e) {
            System.out.println("Erro na leitura da imagem: " + caminho);
            return null;
        }
        catch(IllegalArgumentException e) {
            System.out.println("Imagem da nave não encontrada: " + caminho);
            return null;
        }
    }

    public void desenhar(Graphics2D g) {
        g.drawImage(imagemInimigo, (int) posicaoX, (int) posicaoY, largura, altura, null);
    }

    public abstract void mover();

    //Sinaliza que InimigoNave morreu.
    public void receberDano() {
        this.vivo = false;
    }

    //Retorna as medidas de InimigoNave, usado no teste de colisões.
    public Rectangle getRetangulo() {
        return new Rectangle((int) posicaoX, (int) posicaoY, largura, altura);
    }

    public int getPontos() {
        return pontos;
    }

    public boolean isAtivo() {
        return vivo;
    }
}