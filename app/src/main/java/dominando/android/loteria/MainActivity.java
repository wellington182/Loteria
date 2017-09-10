package dominando.android.loteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private BigDecimal valor = new BigDecimal(1000000);
    private List<Sorteio> sorteios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSortear = (Button) findViewById(R.id.btnSortear);
        btnSortear.setOnClickListener(this);

        Button btnResultados = (Button) findViewById(R.id.btnResultados);
        btnResultados.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSortear:
                sortear();
                break;
            case R.id.btnResultados:
                Intent it = new Intent(this, ResultadoActivity.class);
                it.putExtra("sorteios", (Serializable) sorteios);
                startActivity(it);
                break;
        }
    }

    public void sortear() {
        Random random = new Random();
        Set<Integer> numerosSorteados = new TreeSet<>();
        List<Set<Integer>> apostas = new ArrayList<>();
        Set<Integer> numerosApostados = new TreeSet<>();

        Sorteio sorteio = new Sorteio();
        sorteio.setValor(valor);

        while (apostas.size() < 10) {
            while (numerosApostados.size() < 6) {
                numerosApostados.add(random.nextInt(60) + 1);
            }

            apostas.add(numerosApostados);
            numerosApostados = new TreeSet<>();
        }

        while (numerosSorteados.size() < 6) {
            numerosSorteados.add(random.nextInt(60) + 1);
        }

        sorteio.setNumeros(numerosSorteados);

        for (Set<Integer> aposta : apostas) {
             if (aposta.equals(numerosSorteados)) {
                 sorteio.addGanhador();
             }
             else if (sorteio.getQtdadeGanhadores() < 5 && (random.nextInt(30) == 7)) {
                     sorteio.addGanhador();
             }
        }

        TextView textView = (TextView) findViewById(R.id.resultadoConcurso);
        textView.setText(sorteio.toString());

        //Toast.makeText(this, sorteio.toString(), Toast.LENGTH_LONG).show();

        if (sorteio.getQtdadeGanhadores() == 0) {
            valor = valor.add(new BigDecimal(1000000));
        }
        else {
            valor = new BigDecimal(1000000);
        }

        sorteios.add(sorteio);
    }
}
