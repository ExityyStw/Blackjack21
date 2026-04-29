import java.util.Scanner;

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
            Baralho baralho = new Baralho();
            baralho.embaralhar();
            jogador.limparMao();
            dealer.limparMao();

            jogador.receberCarta(baralho.puxarCarta());
            dealer.receberCarta(baralho.puxarCarta());
            jogador.receberCarta(baralho.puxarCarta());
            dealer.receberCarta(baralho.puxarCarta());

            System.out.println("\n========== NOVA RODADA ==========");
            dealer.mostrarMao(true);
            jogador.mostrarMao(false);
            System.out.println("Status: " + jogador.statusPontuacao());

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

            if (!jogador.estourou()) {
                System.out.println("\n--- Vez do Dealer ---");
                dealer.mostrarMao(false);
                while (dealer.deveContinuarComprando()) {
                    System.out.println("Dealer compra uma carta...");
                    dealer.receberCarta(baralho.puxarCarta());
                    dealer.mostrarMao(false);
                }
            }

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
