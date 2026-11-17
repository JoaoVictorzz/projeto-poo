import base.Janela;
import jogo.JogoProjeto;

public class Main {
    public static void main(String[] args) {
        Janela janela = new Janela(new JogoProjeto(), "Space", 1537, 800);
        janela.exibir();
    }
}
