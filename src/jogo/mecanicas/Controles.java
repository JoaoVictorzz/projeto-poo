package jogo.mecanicas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controles {
    private int mouseX;
    private int mouseY;
    private boolean cima, baixo, esquerda, direita;

    //Identifica se alguma das 4 teclas foi pressionada.
    //Se sim, atribui true para a variável de sua direção.
    public void teclaPressionada(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            cima = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            baixo = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            esquerda = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            direita = true;
        }
    }

    //Identifica se alguma das 4 teclas foi liberada.
    //Se sim, atribui false para a variável de sua direção.
    public void teclaLiberada(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            cima = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            baixo = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            esquerda = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            direita = false;
        }
    }

    //Atualiza a posição do cursor e armazena suas coordenadas X e Y.
    public void atualizarPosicaoMouse(MouseEvent m) {
        mouseY = m.getY();
        mouseX = m.getX();
    }

    public boolean isCima() {
        return cima;
    }

    public boolean isBaixo() {
        return baixo;
    }

    public boolean isEsquerda() {
        return esquerda;
    }

    public boolean isDireita() {
        return direita;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
