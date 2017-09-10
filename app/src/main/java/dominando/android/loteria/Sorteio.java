package dominando.android.loteria;

import android.os.Parcelable;
import android.os.Parcel;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Wellington on 09/09/2017.
 */

public class Sorteio implements Serializable{
    private static int concurso = 10000;
    private int numConcurso;
    public int qtdadeGanhadores;
    private BigDecimal valor;
    private Set<Integer> numeros;

    public Sorteio() {
        this.numConcurso = concurso;

        concurso++;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getNumConcurso() {

        return numConcurso;
    }

    public int getQtdadeGanhadores() {
        return qtdadeGanhadores;
    }

    public void addGanhador() {
        this.qtdadeGanhadores++;

    }

    public Set<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(Set<Integer> numeros) {
        this.numeros = numeros;
    }

    @Override
    public String toString() {
        String texto = "Resultado Concurso " + this.numConcurso;
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        if (this.qtdadeGanhadores > 0) {
            texto += "\nHouve vencedor(es) " + "\n" + this.numeros + "\nValor: " + formatoMoeda.format(this.valor);
        }
        else {
            texto += "\nAcumulou! " + "\n" + this.numeros + "\nValor: " + formatoMoeda.format(this.valor);
        }

        return texto;
    }
}
