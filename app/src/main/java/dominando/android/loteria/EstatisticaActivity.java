package dominando.android.loteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.Preferences;

public class EstatisticaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatistica);

        Intent it = getIntent();
        List<Sorteio> sorteios = (List<Sorteio>) it.getSerializableExtra("sorteios");
        String textoMaisSorteados = "";
        String textoMenosSorteados = "";
        int totalGanhadores = 0;

        Map<Integer, Integer> contagem = new HashMap<>();
        //Passo 1: Montar um mapa que associa o valor a quantas vezes ele pareceu
        for (Sorteio sorteio : sorteios) {
            totalGanhadores += sorteio.getQtdadeGanhadores();

            for (Integer num : sorteio.getNumeros()) {
                if (!contagem.containsKey(num)) {
                    contagem.put(num, 0);
                }
                contagem.put(num, contagem.get(num)+1);
            }
        }

        Set<Map.Entry<Integer, Integer>> maisSorteados = new TreeSet<>(
                new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return -o1.getKey().compareTo(o2.getKey());}});

        Set<Map.Entry<Integer, Integer>> menosSorteados = new TreeSet<>(
                new Comparator<Map.Entry<Integer, Integer>>() {
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return o1.getKey().compareTo(o2.getKey());}});


        for (Map.Entry<Integer, Integer> valor : contagem.entrySet()
             ) {

            if (maisSorteados.size() == 6) {
                break;
            } else {
                maisSorteados.add(valor);
            }
        }

        for (Map.Entry<Integer, Integer> valor : contagem.entrySet()
                ) {

            if (menosSorteados.size() == 6) {
                break;
            } else {
                menosSorteados.add(valor);
            }
        }

        for (Map.Entry<Integer, Integer> num :
             maisSorteados) {
            textoMaisSorteados += num.getKey() + " apareceu " + num.getValue() + " vez(es)\n";
        }

        for (Map.Entry<Integer, Integer> num :
                menosSorteados) {
            textoMenosSorteados += num.getKey() + " apareceu " + num.getValue() + " vez(es)\n";
        }

        TextView textEstatistica = (TextView) findViewById(R.id.estatistica);
        textEstatistica.setText("Mais Sorteados: \n" + textoMaisSorteados + "\n\nMenos Sorteados: \n" + textoMenosSorteados);

        TextView textPercentual = (TextView) findViewById(R.id.percentual);
        textPercentual.setText("Percentual de ganhadores: " + (int)((double)totalGanhadores / sorteios.size() * 100) + "%");

    }
}
