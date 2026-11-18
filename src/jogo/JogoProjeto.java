package jogo;

import base.Jogo;
import cenario.Cenario;
import jogo.entidades.Nave;
import jogo.entidades.Projetil;
import jogo.entidades.inimigos.Inimigo;
import jogo.mecanicas.Controles;
import jogo.mecanicas.Dificuldade;
import jogo.mecanicas.FabricaInimigos;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JogoProjeto extends Jogo {

    private static final int MARGEM_FORA_DA_TELA = 100;

    private final Dificuldade dificuldade = new Dificuldade();
    private final FabricaInimigos fabricaInimigos = new FabricaInimigos();
    private final Controles controles = new Controles();

    private Cenario cenario;
    private Nave nave;
    private Font fonte;

    private int pontuacao;

    private int framesDesdeUltimoSpawn = 0;

    private List<Projetil> projeteisJogador = new ArrayList<>();
    private List<Inimigo> inimigos = new ArrayList<>();

    @Override
    public void inicializar(int largura, int altura) {
        super.inicializar(largura, altura);
        cenario = new Cenario();
        nave = new Nave((double) getLargura() / 2, (double) getAltura() / 2);

        
        try {
            fonte = Font.createFont(
                    Font.TRUETYPE_FONT, new File("src/recursos/fontes/orbitron.ttf")
            ).deriveFont(28f);
        }
        catch (FontFormatException e) {
            System.err.println("Formato de fonte inválido.");
            fonte = new Font("Monospaced", Font.BOLD, 28);

        } catch (IOException e) {
            System.err.println("Falha na leitura do arquivo da fonte.");
            fonte = new Font("Monospaced", Font.BOLD, 28);
        }
    }

    @Override
    public void atualizar() {
        framesDesdeUltimoSpawn++;
        moverNave();
        nave.guiarDirecaoNave(controles.getMouseX(), controles.getMouseY());


        for (Projetil p : projeteisJogador) {
            p.mover();
        }

        for (Inimigo inimigo : inimigos) {
            inimigo.mover();
        }

        verificarColisoesTiroJogador();

        removerProjeteisInativos();
        removerInimigosInativos();

        if (framesDesdeUltimoSpawn > dificuldade.intervaloSpawn(pontuacao)
                && dificuldade.deveSpawnar(pontuacao)) {

            inimigos.add(fabricaInimigos.criarInimigo(getLargura(), getAltura()));
            framesDesdeUltimoSpawn = 0;
        }

    }

    @Override
    public void desenhar(Graphics2D g) {
        cenario.desenhar(g, getLargura(), getAltura());
        nave.desenhar(g);

        for (Projetil p : projeteisJogador) {
            p.desenhar(g);
        }

        for (Inimigo inimigo : inimigos) {
            inimigo.desenhar(g);
        }

        g.setColor(Color.WHITE);
        g.setFont(fonte);
        g.drawString("Pontuação: " + pontuacao, 30, 40);
        g.drawString("Vidas: " + nave.getVida(), 30, 90);
    }

    @Override
    public void teclaPressionada(KeyEvent e) {
        controles.teclaPressionada(e);
    }

    @Override
    public void teclaLiberada(KeyEvent e) {
        controles.teclaLiberada(e);
    }

    @Override
    public void mouseMovido(MouseEvent m) {
        controles.atualizarPosicaoMouse(m);
    }

    @Override
    public void mousePressionado(MouseEvent m) {
        if (m.getButton() == MouseEvent.BUTTON1) {
            adicionarProjetilJogador();
        }
    }

    //Move a nave em 4 direções:
    //cima {x = 0, y = -1}
    //baixo {x = 0, y = 1}
    //direita {x = 1, y = 0}
    //esquerda {x = -1, y = 0}
    public void moverNave() {
        if (controles.isCima()) {
            nave.mover(0, -1, getLargura(), getAltura());
        }
        if (controles.isBaixo()) {
            nave.mover(0, 1, getLargura(), getAltura());
        }
        if (controles.isDireita()) {
            nave.mover(1, 0, getLargura(), getAltura());
        }
        if (controles.isEsquerda()) {
            nave.mover(-1, 0, getLargura(), getAltura());
        }
    }

    //Adiciona um projetil à lista de projéteis do jogador,
    //acionado pelo botão esquerdo do mouse.
    public void adicionarProjetilJogador() {
        projeteisJogador.add(new Projetil(
                nave.getPontaX(), nave.getPontaY(), nave.getAngulo()));
    }

    //Verifica se um dos projéteis atingiu algum inimigo,
    //se atingiu, inimigo recebe dano(vivo = false) e adiciona a pontuação do inimigo.
    //O projétil é marcado como colidido.
    private void verificarColisoesTiroJogador() {
        for (Projetil p : projeteisJogador) {
            for (Inimigo inimigo : inimigos) {
                if (p.getRetangulo().intersects(inimigo.getRetangulo())) {
                    if (inimigo.isAtivo()) {
                        inimigo.receberDano();
                        pontuacao += inimigo.getPontos();
                    }
                    p.marcarColidiu();
                    break;
                }
            }
        }
    }

    //Remove projéteis inativos ou que ultrapassaram os limites da tela.
    private void removerProjeteisInativos() {
        projeteisJogador.removeIf(projetil ->
                !projetil.isAtivo() || saiuDaTela(projetil.getRetangulo()));
    }

    //Remove inimigos que foram mortos/atingidos por um projétil ou que passaram da tela.
    private void removerInimigosInativos() {
        inimigos.removeIf(inimigo -> !inimigo.isAtivo() || saiuDaTela(inimigo.getRetangulo()));
    }

    //Retorna true se o retângulo passou da tela, considera uma margem adicional (usada para o inimigo
    //nascer acima da tela sem ser removido).
    private boolean saiuDaTela(Rectangle r) {
        return r.x + r.width < -MARGEM_FORA_DA_TELA || r.y + r.height < -MARGEM_FORA_DA_TELA ||
                r.x > getLargura() + MARGEM_FORA_DA_TELA || r.y > getAltura() + MARGEM_FORA_DA_TELA;

    }
}
