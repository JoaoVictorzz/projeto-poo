package jogo.entidades;

import java.awt.*;

public interface ObjetoJogo {
    void desenhar(Graphics2D g);

    Rectangle getRetangulo();

    boolean isAtivo();


}
