package jogo;

import base.Jogo;
import cenario.Cenario;
import jogo.entidades.Nave;
import jogo.entidades.ObjetoJogo;
import jogo.entidades.Projetil;
import jogo.entidades.inimigos.Inimigo;
import jogo.mecanicas.Controles;
import jogo.mecanicas.Dificuldade;
import jogo.mecanicas.FabricaInimigos;
import jogo.mecanicas.EstadosDoJogo;
import telas.TelaFimDeJogo;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class JogoProjeto extends Jogo {

    private static final int MARGEM_FORA_DA_TELA = 100;

    private final Dificuldade dificuldade = new Dificuldade();
    private final FabricaInimigos fabricaInimigos = new FabricaInimigos();
    private final Controles controles = new Controles();

    private Cenario cenario;
    private Nave nave;
    private EstadosDoJogo estado = EstadosDoJogo.JOGO_EM_ANDAMENTO;

    private int pontuacao;
    private long instanteUltimoDanoRecebidoPorColisao = 0;
    private static final long COOLDOWN_DANO_RECEBIDO = 1_000_000_000L;
    private long instanteUltimoDisparo = 0;
    private static final long COOLDOWN_TIRO_NAVE = 500_000_000L;

    private int framesDesdeUltimoSpawn = 0;

    private List<Projetil> projeteisJogador = new ArrayList<>();
    private List<Inimigo> inimigos = new ArrayList<>();

    @Override
    public void inicializar(int largura, int altura) {
        super.inicializar(largura, altura);
        cenario = new Cenario();
        nave = new Nave((double) getLargura() / 2, (double) getAltura() / 2);

    }

    @Override
    public void atualizar() {
        if (estado != EstadosDoJogo.JOGO_EM_ANDAMENTO) {
            return;
        }
        framesDesdeUltimoSpawn++;
        moverNave();
        nave.guiarDirecaoNave(controles.getMouseX(), controles.getMouseY());


        for (Projetil p : projeteisJogador) {
            p.mover();
        }

        for (Inimigo inimigo : inimigos) {
            inimigo.mover();
        }
        verificarAtaqueInimigoPorColisao();

        if (nave.getVida() <= 0) {
            estado = EstadosDoJogo.FIM_DE_JOGO;
            return;
        }

        verificarColisoesTiroJogador();

        removerObjetosInativos(inimigos);
        removerObjetosInativos(projeteisJogador);

        if (framesDesdeUltimoSpawn > dificuldade.intervaloSpawn(pontuacao)
                && dificuldade.deveSpawnar(pontuacao)) {

            inimigos.add(fabricaInimigos.criarInimigo(getLargura(), getAltura(), nave));
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
        g.setFont(new Font("Monospaced", Font.BOLD, 26));
        g.drawString("Pontuação: " + pontuacao, 30, 40);
        g.drawString("Vidas: " + nave.getVida(), 30, 90);

        if (estado == EstadosDoJogo.FIM_DE_JOGO) {
            TelaFimDeJogo.desenhar(g, getLargura(), getAltura(), pontuacao);
        }
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
        long agora = System.nanoTime();
        if (m.getButton() == MouseEvent.BUTTON1
                && agora - instanteUltimoDisparo > COOLDOWN_TIRO_NAVE) {
            adicionarProjetilJogador();
            instanteUltimoDisparo = agora;
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
                        p.marcarColidiu();
                        break;
                    }
                }
            }
        }
    }

    //Todo inimigo causa dano por colisão, verifica se houve colisão entre a nave e qualquer inimigo.
    private void verificarAtaqueInimigoPorColisao() {
        long agora = System.nanoTime();
        for (Inimigo inimigo : inimigos) {
            if (inimigo.getRetangulo().intersects(nave.getRetangulo())
                    && agora - instanteUltimoDanoRecebidoPorColisao > COOLDOWN_DANO_RECEBIDO
                    && inimigo.isAtivo()) {
                inimigo.atacar(nave);
                instanteUltimoDanoRecebidoPorColisao = agora;

            }
        }
    }

    //Remove objetos inativos ou que saíram da tela.
    private void removerObjetosInativos(List<? extends ObjetoJogo> lista) {
        lista.removeIf(objetoJogo -> !objetoJogo.isAtivo() ||
                saiuDaTela(objetoJogo.getRetangulo()));
    }

    //Retorna true se o retângulo passou da tela, considera uma margem adicional (usada para o inimigo
    //nascer acima da tela sem ser removido).
    private boolean saiuDaTela(Rectangle r) {
        return r.x + r.width < -MARGEM_FORA_DA_TELA || r.y + r.height < -MARGEM_FORA_DA_TELA ||
                r.x > getLargura() + MARGEM_FORA_DA_TELA || r.y > getAltura() + MARGEM_FORA_DA_TELA;

    }
}
