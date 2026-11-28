package jogo;

import base.Jogo;
import cenario.Cenario;
import jogo.entidades.Nave;
import jogo.entidades.ObjetoJogo;
import jogo.entidades.Projetil;
import jogo.entidades.inimigos.Inimigo;
import jogo.mecanicas.*;
import telas.*;

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
    private final Botao pausar = new Botao(70, 700, 100, 40, "PAUSAR");
    private static final int PONTUACAO_FASE_2 = 1300;
    private static final int PONTUACAO_VITORIA = 1800;

    private TelaInicial telaInicial;
    private TelaFimDeJogo telaFimDeJogo;
    private TelaPausa telaPausa;
    private TelaFase1Concluida telaFase1Concluida;
    private TelaVitoria telaVitoria;
    private Cenario cenario;
    private Nave nave;
    private EstadosDoJogo estado = EstadosDoJogo.MENU;
    private int faseAtual = 1;

    private int pontuacao = 0;
    private long instanteUltimoDanoRecebidoPorColisao = 0;
    private static final long COOLDOWN_DANO_RECEBIDO = 1_000_000_000L;
    private long instanteUltimoDisparo = 0;
    private static final long COOLDOWN_TIRO_NAVE = 500_000_000L;

    private int framesDesdeUltimoSpawn = 0;

    private final List<Projetil> projeteisJogador = new ArrayList<>();
    private final List<Inimigo> inimigos = new ArrayList<>();

    @Override
    public void inicializar(int largura, int altura) {
        super.inicializar(largura, altura);
        telaPausa = new TelaPausa(getLargura(), getAltura());
        telaInicial = new TelaInicial(getLargura(), getAltura());
        telaFimDeJogo = new TelaFimDeJogo(getLargura(), getAltura());
        telaFase1Concluida = new TelaFase1Concluida(getLargura(), getAltura());
        telaVitoria = new TelaVitoria(getLargura(), getAltura());
        cenario = new Cenario();
        nave = new Nave((double) getLargura() / 2, (double) getAltura() / 2);

    }

    @Override
    public void atualizar() {

        if (estado == EstadosDoJogo.TRANSICAO) {
            telaInicial.aumentarOpacidade();

            if (telaInicial.getOpacidade() >= 1.0f) {
                estado = EstadosDoJogo.JOGO_EM_ANDAMENTO;
            }

            return;
        }

        if (estado == EstadosDoJogo.FIM_DE_JOGO) {
            telaFimDeJogo.aumentarOpacidade();
            return;
        }

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

        if (pontuacao >= PONTUACAO_VITORIA && faseAtual == 2) {
            estado = EstadosDoJogo.VITORIA;
            return;
        }

        if (pontuacao >= PONTUACAO_FASE_2 && faseAtual < 2) {
            estado = EstadosDoJogo.FASE_CONCLUIDA1;
            return;
        }

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

        if (estado == EstadosDoJogo.MENU ||
                estado == EstadosDoJogo.TRANSICAO) {

            telaInicial.desenhar(g, getLargura(), getAltura());

            if (estado == EstadosDoJogo.TRANSICAO) {
                g.setColor(Color.BLACK);
                g.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER,
                        telaInicial.getOpacidade()
                ));
                g.fillRect(0, 0, getLargura(), getAltura());
                g.setComposite(AlphaComposite.SrcOver);
            }

            return;
        }
        cenario.desenhar(g, getLargura(), getAltura(), faseAtual);
        nave.desenhar(g);

        for (Projetil p : projeteisJogador) {
            p.desenhar(g);
        }

        for (Inimigo inimigo : inimigos) {
            inimigo.desenhar(g);
        }
        if (estado == EstadosDoJogo.PAUSADO) {
            telaPausa.desenhar(g, getLargura(), getAltura(), pontuacao);
            return;
        }

        if (estado == EstadosDoJogo.JOGO_EM_ANDAMENTO) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("Pontuação: " + pontuacao, 30, 40);
            g.drawString("Vidas: " + nave.getVida(), 30, 90);
            pausar.desenhar(g);
        }

        if (estado == EstadosDoJogo.FASE_CONCLUIDA1) {
            telaFase1Concluida.desenhar(
                    g, getLargura(), getAltura(), pontuacao
            );
            return;
        }

        if (estado == EstadosDoJogo.VITORIA) {
            telaVitoria.desenhar(g, getLargura(), getAltura(), pontuacao);
            return;
        }

        if (estado == EstadosDoJogo.FIM_DE_JOGO) {
            Composite compositeOriginal = g.getComposite();

            g.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER,
                    telaFimDeJogo.getOpacidade()
            ));
            telaFimDeJogo.desenhar(g, getLargura(), getAltura(), pontuacao);

            g.setComposite(compositeOriginal);
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

        if (estado == EstadosDoJogo.MENU) {
            if (telaInicial.clicouJogar(m.getX(), m.getY())) {
                telaInicial.reiniciarOpacidade();
                estado = EstadosDoJogo.TRANSICAO;
            } else if (telaInicial.clicouSair(m.getX(), m.getY())) {
                System.exit(0);
            }
            return;
        }

        if (estado == EstadosDoJogo.PAUSADO) {
            if (telaPausa.clicouContinuar(m.getX(), m.getY())) {
                estado = EstadosDoJogo.JOGO_EM_ANDAMENTO;
            } else if (telaPausa.clicouMenu(m.getX(), m.getY())) {
                reiniciar();
                estado = EstadosDoJogo.MENU;
            }
            return;
        }

        if (estado == EstadosDoJogo.FIM_DE_JOGO) {
            if (telaFimDeJogo.clicouReiniciar(m.getX(), m.getY())) {
                reiniciar();
                estado = EstadosDoJogo.JOGO_EM_ANDAMENTO;
            } else if (telaFimDeJogo.clicouMenu(m.getX(), m.getY())) {
                reiniciar();
                estado = EstadosDoJogo.MENU;
            }
            return;
        }

        if (estado == EstadosDoJogo.JOGO_EM_ANDAMENTO) {
            if (pausar.detectarClique(m.getX(), m.getY())) {
                estado = EstadosDoJogo.PAUSADO;
                return;
            }
        }

        if (estado == EstadosDoJogo.FASE_CONCLUIDA1) {
            if (telaFase1Concluida.clicouContinuar(m.getX(), m.getY())) {
                avancarFase2();
                estado = EstadosDoJogo.JOGO_EM_ANDAMENTO;
            }
            return;
        }

        if (estado == EstadosDoJogo.VITORIA) {
            if (telaVitoria.clicouMenu(m.getX(), m.getY())) {
                reiniciar();
                estado = EstadosDoJogo.MENU;
            }
            return;
        }

        long agora = System.nanoTime();
        if (estado == EstadosDoJogo.JOGO_EM_ANDAMENTO
                && m.getButton() == MouseEvent.BUTTON1
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
                        if (!inimigo.isAtivo()) {
                            pontuacao += inimigo.getPontos();
                        }
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

    private void reiniciar() {
        limparDados();
        nave = new Nave(getLargura() / 2.0, getAltura() / 2.0);
        pontuacao = 0;
        telaFimDeJogo.reiniciarOpacidade();
        faseAtual = 1;
    }

    public void avancarFase2() {
        limparDados();
        nave = new Nave(getLargura() / 2.0, getAltura() / 2.0);
        faseAtual = 2;
    }

    private void limparDados() {
        inimigos.clear();
        projeteisJogador.clear();
        framesDesdeUltimoSpawn = 0;
        instanteUltimoDanoRecebidoPorColisao = 0;
        instanteUltimoDisparo = 0;
        controles.reiniciar();
    }

}
