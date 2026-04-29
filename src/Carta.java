// =============================================
// CLASSE CARTA: representa uma única carta
// =============================================
public class Carta {
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
