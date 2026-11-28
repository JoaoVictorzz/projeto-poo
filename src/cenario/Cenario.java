package cenario;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


public class Cenario {

    private Random random = new Random();
    private static final int QUANTIDADE_ESTRELAS = 150;
    private static final int TAMANHO_MAXIMO_ESTRELA = 3;
    private double[] posicaoEstrelasX = new double[QUANTIDADE_ESTRELAS];
    private double[] posicaoEstrelasY = new double[QUANTIDADE_ESTRELAS];
    private int[] tamanhoEstrelas = new int[QUANTIDADE_ESTRELAS];
    private BufferedImage imagemPlanetaRoxo;
    private BufferedImage imagemPlanetaAmarelo;

    public Cenario() {
        for (int i = 0; i < QUANTIDADE_ESTRELAS; i++) {
            posicaoEstrelasX[i] = random.nextDouble();
            posicaoEstrelasY[i] = random.nextDouble();
            tamanhoEstrelas[i] = random.nextInt(TAMANHO_MAXIMO_ESTRELA) + 1;
        }

        imagemPlanetaRoxo = carregarImagem("/imagensPlanetas/planetaRoxo.png");
        imagemPlanetaAmarelo = carregarImagem("/imagensPlanetas/planetaAmarelo.png");
    }

    public void desenhar(Graphics2D g, int largura, int altura, int faseAtual) {
        if (faseAtual == 1) {
            g.setPaint(new LinearGradientPaint(
                    0, 0, 0, altura,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{Color.BLACK, new Color(36, 19, 56), Color.BLACK}
            ));
            g.fillRect(0, 0, largura, altura);

            g.setColor(Color.WHITE);
            for (int i = 0; i < QUANTIDADE_ESTRELAS; i++) {
                g.fillOval(
                        (int) (posicaoEstrelasX[i] * largura), (int) (posicaoEstrelasY[i] * altura),
                        tamanhoEstrelas[i], tamanhoEstrelas[i]
                );
            }

            g.drawImage(imagemPlanetaRoxo, 80, 20, 300, 300, null);
        }
        else if(faseAtual == 2) {
            g.setPaint(new LinearGradientPaint(
                    0, 0, 0, altura,
                    new float[]{0f, 0.5f, 1f},
                    new Color[]{Color.BLACK, new Color(45, 0, 0), Color.BLACK}
            ));
            g.fillRect(0, 0, largura, altura);

            g.setColor(Color.YELLOW);
            for (int i = 0; i < QUANTIDADE_ESTRELAS; i++) {
                g.fillOval(
                        (int) (posicaoEstrelasX[i] * largura), (int) (posicaoEstrelasY[i] * altura),
                        tamanhoEstrelas[i], tamanhoEstrelas[i]
                );
            }

            g.drawImage(imagemPlanetaAmarelo, 1200, -300, 700, 700, null);
        }
        else {
            throw new IllegalStateException("Fase inexistente: " + faseAtual);
        }

    }

    private BufferedImage carregarImagem(String caminho) {
        try {
            return ImageIO.read(getClass().getResource(caminho));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar imagem: " + caminho, e);
        }
    }
}
