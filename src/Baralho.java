import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// =============================================
// CLASSE BARALHO: cria e gerencia as 52 cartas
// =============================================
public class Baralho {
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
