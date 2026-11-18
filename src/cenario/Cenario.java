package cenario;

import java.awt.*;
import java.util.Random;


public class Cenario {

    private Random random = new Random();
    private static final int QUANTIDADE_ESTRELAS = 150;
    private static final int TAMANHO_MAXIMO_ESTRELA = 3;
    private static final int GRADIENTE_CENTRO_X = 180;
    private static final int GRADIENTE_CENTRO_Y = 220;
    private static final int RAIO_GRADIENTE = 180;
    private static final int NEBULOSA_POSICAOX = 180;
    private static final int NEBULOSA_POSICAOY = 100;
    private static final int NEBULOSA_LARGURA = 400;
    private static final int NEBULOSA_ALTURA = 300;
    private double[] posicaoEstrelasX = new double[QUANTIDADE_ESTRELAS];
    private double[] posicaoEstrelasY = new double[QUANTIDADE_ESTRELAS];
    private int[] tamanhoEstrelas = new int[QUANTIDADE_ESTRELAS];

    public Cenario() {
        for(int i = 0; i < QUANTIDADE_ESTRELAS; i++) {
            posicaoEstrelasX[i] = random.nextDouble();
            posicaoEstrelasY[i] = random.nextDouble();
            tamanhoEstrelas[i] = random.nextInt(TAMANHO_MAXIMO_ESTRELA) + 1;
        }
    }

    public void desenhar(Graphics2D g, int largura, int altura) {

        g.setPaint(new LinearGradientPaint(
                0, 0, 0, altura,
                new float[]{0f, 0.5f, 1f},
                new Color[]{Color.BLACK, new Color(Color.DARK_GRAY.getBlue()), Color.BLACK}
        ));
        g.fillRect(0, 0, largura, altura);

        g.setColor(Color.WHITE);
        for(int i = 0; i < QUANTIDADE_ESTRELAS; i++) {
            g.fillOval(
                    (int) (posicaoEstrelasX[i] * largura), (int) (posicaoEstrelasY[i] * altura),
                    tamanhoEstrelas[i], tamanhoEstrelas[i]
            );
        }

        g.setPaint(new RadialGradientPaint(
                GRADIENTE_CENTRO_X, GRADIENTE_CENTRO_Y, RAIO_GRADIENTE,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(130, 70, 180, 50),
                        new Color(130, 30, 240, 0)
                }
        ));

        g.fillOval( NEBULOSA_POSICAOX,
                NEBULOSA_POSICAOY,
                NEBULOSA_LARGURA,
                NEBULOSA_ALTURA );
    }
}
