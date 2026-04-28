import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// =============================================
// CLASSE CARTA: representa uma única carta
// =============================================
class Carta {
    private String naipe;  // Ex: Copas, Espadas
    private String valor;  // Ex: A, 2, 3, ..., K

    public Carta(String naipe, String valor) {
        this.naipe = naipe;
        this.valor = valor;
    }

    // Retorna o valor em pontos da carta no BlackJack
    public int getPontos() {
        switch (valor) {
            case "A":              return 11; // As vale 11 (ou 1, tratado no Jogador)
            case "J": case "Q":
            case "K":              return 10; // Figuras valem 10
            default:               return Integer.parseInt(valor); // Numericas valem o numero
        }
    }

    public String getValor() { return valor; }

    // Como a carta aparece impressa no terminal
    @Override
    public String toString() {
        return valor + " de " + naipe;
    }
}

// =============================================
// CLASSE BARALHO: cria e gerencia as 52 cartas
// =============================================
class Baralho {
    private List<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();

        String[] naipes = {"Copas", "Ouros", "Paus", "Espadas"};
        String[] valores = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        // Cria todas as combinacoes de naipe x valor
        for (String naipe : naipes) {
            for (String valor : valores) {
                cartas.add(new Carta(naipe, valor));
            }
        }
    }

    // Embaralha aleatoriamente
    public void embaralhar() {
        Collections.shuffle(cartas);
        System.out.println("Baralho embaralhado!");
    }

    // Remove e retorna a primeira carta do topo
    public Carta puxarCarta() {
        if (cartas.isEmpty()) return null;
        return cartas.remove(0);
    }
}

// =============================================
// CLASSE JOGADOR: jogador ou dealer
// =============================================
class Jogador {
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

// =============================================
// CLASSE PRINCIPAL: roda o jogo
// =============================================
public class Blackjack {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Jogador jogador = new Jogador("Igor", false);
        Jogador dealer  = new Jogador("Dealer", true);

        int vitoriasJogador = 0;
        int vitoriasDealer  = 0;

        String jogarNovamente;

        do {
            // Prepara nova rodada
            Baralho baralho = new Baralho();
            baralho.embaralhar();
            jogador.limparMao();
            dealer.limparMao();

            // Distribui 2 cartas para cada um
            jogador.receberCarta(baralho.puxarCarta());
            dealer.receberCarta(baralho.puxarCarta());
            jogador.receberCarta(baralho.puxarCarta());
            dealer.receberCarta(baralho.puxarCarta());

            System.out.println("\n========== NOVA RODADA ==========");
            dealer.mostrarMao(true);   // Esconde 1a carta do dealer
            jogador.mostrarMao(false);
            System.out.println("Status: " + jogador.statusPontuacao());

            // --- TURNO DO JOGADOR ---
            String opcao = "";
            while (!jogador.estourou() && !opcao.equals("P")) {
                System.out.print("\n[C] Comprar carta  |  [P] Parar: ");
                opcao = sc.next().toUpperCase();

                if (opcao.equals("C")) {
                    jogador.receberCarta(baralho.puxarCarta());
                    jogador.mostrarMao(false);
                    System.out.println("Status: " + jogador.statusPontuacao());
                }
            }

            // --- TURNO DO DEALER (so joga se jogador nao estourou) ---
            if (!jogador.estourou()) {
                System.out.println("\n--- Vez do Dealer ---");
                dealer.mostrarMao(false);
                while (dealer.deveContinuarComprando()) {
                    System.out.println("Dealer compra uma carta...");
                    dealer.receberCarta(baralho.puxarCarta());
                    dealer.mostrarMao(false);
                }
            }

            // --- RESULTADO ---
            System.out.println("\n========== RESULTADO ==========");
            jogador.mostrarMao(false);
            dealer.mostrarMao(false);

            int ptsJogador = jogador.calcularPontos();
            int ptsDealer  = dealer.calcularPontos();

            if (jogador.estourou()) {
                System.out.println("Voce estourou! Dealer vence.");
                vitoriasDealer++;
            } else if (dealer.estourou()) {
                System.out.println("Dealer estourou! Voce vence!");
                vitoriasJogador++;
            } else if (ptsJogador > ptsDealer) {
                System.out.println("Voce vence! " + ptsJogador + " x " + ptsDealer);
                vitoriasJogador++;
            } else if (ptsDealer > ptsJogador) {
                System.out.println("Dealer vence! " + ptsDealer + " x " + ptsJogador);
                vitoriasDealer++;
            } else {
                System.out.println("Empate! Ambos com " + ptsJogador + " pontos.");
            }

            System.out.println("Placar: Voce " + vitoriasJogador + " x " + vitoriasDealer + " Dealer");

            System.out.print("\nJogar novamente? [S/N]: ");
            jogarNovamente = sc.next().toUpperCase();

        } while (jogarNovamente.equals("S"));

        System.out.println("\nObrigado por jogar! Ate a proxima!");
        sc.close();
    }
}