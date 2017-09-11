package dominando.android.loteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
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

        Map<Integer, Integer> maisSorteados = sortMaisSorteados(contagem);
        for (Integer i : maisSorteados.keySet()) {
            textoMaisSorteados += i + " apareceu " + maisSorteados.get(i) + " vez(es)\n";
        }

        Map<Integer, Integer> menosSorteados = sortMenosSorteados(contagem);
        for (Integer i : menosSorteados.keySet()) {
            textoMenosSorteados += i + " apareceu " + menosSorteados.get(i) + " vez(es)\n";
        }

        TextView textEstatistica = (TextView) findViewById(R.id.estatistica);
        textEstatistica.setText("Mais Sorteados: \n" + textoMaisSorteados + "\n\nMenos Sorteados: \n" + textoMenosSorteados);

        TextView textPercentual = (TextView) findViewById(R.id.percentual);
        textPercentual.setText("Total de sorteios: " + sorteios.size() +"\nPercentual de ganhadores: " + (int)((double)totalGanhadores / sorteios.size() * 100) + "%");
    }

    private static HashMap sortMaisSorteados(Map<Integer, Integer> map) {
        List<Integer> list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });


        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());

            if (sortedHashMap.size() == 6) {
                break;
            }
        }
        return sortedHashMap;
    }

    private static HashMap sortMenosSorteados(Map<Integer, Integer> map) {
        List<Integer> list = new LinkedList(map.entrySet());

        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return -((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });


        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());

            if (sortedHashMap.size() == 6) {
                break;
            }
        }
        return sortedHashMap;
    }
}