package jogo.entidades.powerups;

import jogo.entidades.Nave;
import jogo.entidades.ObjetoJogo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class PowerUp implements ObjetoJogo{
    protected double posicaoX,posicaoY;
    protected int largura, altura;
    private boolean ativo = true;
    private BufferedImage imagem;

    public PowerUp(double posicaoX, double posicaoY, int largura, int altura, String caminhoImagem){
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.largura = largura;
        this.altura = altura;
        this.imagem = carregarImagem(caminhoImagem);
    }
    private BufferedImage carregarImagem(String caminho){
    try {
        return ImageIO.read(getClass().getResource(caminho));
    } catch(IOException e) {
        System.out.println("Erro ao tentar carregar a imagem:" + caminho);
        return null;
    }
}
    public abstract void aplicarEfeito(Nave nave);

    public void coletar(Nave nave){
        aplicarEfeito(nave);
        ativo = false;
    }

    @Override
    public void desenhar(Graphics2D g){
    g.drawImage(imagem, (int) posicaoX, (int) posicaoY, largura, altura, null);
    }

    @Override
    public Rectangle getRetangulo(){
        return new Rectangle((int) posicaoX, (int) posicaoY, largura, altura);
    }

    @Override
    public boolean isAtivo(){
    return ativo;
    }
}