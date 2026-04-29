import java.util.ArrayList;
import java.util.List;

// =============================================
// CLASSE JOGADOR: jogador ou dealer
// =============================================
public class Jogador {
    private String nome;
    private List<Carta> mao;         // As cartas na mao
    private boolean ehDealer;        // True se for o dealer (banca)

    public Jogador(String nome, boolean ehDealer) {
        this.nome = nome;
        this.mao = new ArrayList<>();
        this.ehDealer = ehDealer;
    }

    // Adiciona carta na mao
    public void receberCarta(Carta carta) {
        if (carta != null) mao.add(carta);
    }

    // Calcula a pontuacao total da mao
    public int calcularPontos() {
        int total = 0;
        int quantidadeAs = 0;

        for (Carta carta : mao) {
            total += carta.getPontos();
            if (carta.getValor().equals("A")) quantidadeAs++;
        }

        // Se estourou e tem As, muda o As de 11 para 1 (subtrai 10)
        while (total > 21 && quantidadeAs > 0) {
            total -= 10;
            quantidadeAs--;
        }

        return total;
    }

    // Verifica se passou de 21
    public boolean estourou() {
        return calcularPontos() > 21;
    }

    // Retorna texto com o status da pontuacao
    public String statusPontuacao() {
        int pontos = calcularPontos();
        if (pontos > 21)       return "ACIMA de 21 - Estourou!";
        else if (pontos == 21) return "IGUAL a 21 - BlackJack!";
        else                   return "ABAIXO de 21 - Ainda pode comprar.";
    }

    // Imprime as cartas na mao
    public void mostrarMao(boolean esconderPrimeira) {
        System.out.print(nome + " => ");
        for (int i = 0; i < mao.size(); i++) {
            if (i == 0 && ehDealer && esconderPrimeira) {
                System.out.print("[CARTA OCULTA]");
            } else {
                System.out.print(mao.get(i));
            }
            if (i < mao.size() - 1) System.out.print(" | ");
        }
        if (!esconderPrimeira || !ehDealer) {
            System.out.println(" => " + calcularPontos() + " pontos");
        } else {
            System.out.println();
        }
    }

    // Dealer compra ate ter pelo menos 17 pontos
    public boolean deveContinuarComprando() {
        return ehDealer && calcularPontos() < 17;
    }

    // Limpa a mao para nova rodada
    public void limparMao() {
        mao.clear();
    }

    public String getNome() { return nome; }
}
