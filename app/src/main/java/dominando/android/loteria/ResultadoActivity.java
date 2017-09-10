package dominando.android.loteria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent it = getIntent();
        List<Sorteio> sorteios = (List<Sorteio>) it.getSerializableExtra("sorteios");
        String texto = "";

        for (Sorteio sorteio : sorteios) {
            texto += sorteio + "\n\n";
        }

        TextView textView = (TextView) findViewById(R.id.resultado);
        textView.setText(texto);

    }
}
