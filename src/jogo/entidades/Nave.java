package jogo.entidades;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Nave implements ObjetoJogo{
    private static final double VELOCIDADE = 12;
    private static final int VIDA_INICIAL = 3;
    private static final int LARGURA = 60;
    private static final int ALTURA = 50;
    private static final String CAMINHO_IMAGEM = "/recursos/imagensInimigos/NaveJogador1.png";
    private final BufferedImage imagemNave;
    private double posicaoX, posicaoY;
    private int vida;
    private boolean vivo;
    private double rotacao;

    public Nave(double posicaoX, double posicaoY) {
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.vida = VIDA_INICIAL;
        this.imagemNave = carregarImagem();
    }

    //Lê a imagem da nave, captura erros que ocorrerem na leitura da imagem.
    //Devolve null se falhar.
    private BufferedImage carregarImagem() {
        try {
            return ImageIO.read(getClass().getResource(CAMINHO_IMAGEM));
        } catch (IOException e) {
            System.out.println("Erro na leitura da imagem: " + CAMINHO_IMAGEM);
            return null;
        }
    }

    //Desenha a nave girada em relação ao seu centro, depois descarta a cópia de Graphics2D.
    @Override
    public void desenhar(Graphics2D g) {
        Graphics2D gCopia = (Graphics2D) g.create();
        gCopia.rotate(rotacao, getCentroX(), getCentroY());
        gCopia.drawImage(imagemNave, (int) posicaoX, (int) posicaoY, LARGURA, ALTURA, null);
        gCopia.dispose();
    }

    @Override
    public boolean isAtivo() {
        return vivo;
    }

    @Override
    public Rectangle getRetangulo() {
        return new Rectangle((int) posicaoX, (int) posicaoY, LARGURA, ALTURA);
    }

    // Move a nave na direção indicada (-1, 0 ou 1 por eixo) e a mantém dentro da tela.
    // larguraTela e alturaTela são o tamanho atual da área de jogo.
    public void mover(int direcaoX, int direcaoY, int larguraTela, int alturaTela) {
        posicaoX += direcaoX * VELOCIDADE;
        posicaoY += direcaoY * VELOCIDADE;

        posicaoX = Math.max(0, Math.min(posicaoX, larguraTela - LARGURA));
        posicaoY = Math.max(0, Math.min(posicaoY, alturaTela - ALTURA));
    }

    //Gira a ponta da nave para o mouse, facilita a jogabilidade
    public void guiarDirecaoNave(int mouseX, int mouseY) {
        rotacao = Math.atan2(mouseY - getCentroY(), mouseX - getCentroX());
    }

    //Retorna o ângulo atual da nave, usado para posicionar o projétil corretamente.
    public double getAngulo() {
        return rotacao;
    }

    public int getVida() {
        return vida;
    }

    public void receberDano() {
        vida -= 1;
    }

    //Retornam respectivamente as posições x e y do centro da nave
    public double getCentroX() {
        return posicaoX + LARGURA / 2.0;
    }

    public double getCentroY() {
        return posicaoY + ALTURA / 2.0;
    }

    //Retornam as posições das pontas da nave, onde nascem os projéteis da nave.
    public double getPontaX() {
        return getCentroX() + Math.cos(rotacao) * (LARGURA / 2.0);
    }

    public double getPontaY() {
        return getCentroY() + Math.sin(rotacao) * (LARGURA / 2.0);
    }
}