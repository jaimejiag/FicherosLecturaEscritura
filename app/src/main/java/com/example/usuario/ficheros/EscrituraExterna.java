package com.example.usuario.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EscrituraExterna extends AppCompatActivity implements View.OnClickListener{
    EditText operando1, operando2;
    TextView txvResultado, txvPropiedades;
    Button btnMas;
    Memoria miMemoria;
    public static final String NOMBRE_FICHERO = "operacion.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escritura);

        operando1 = (EditText) findViewById(R.id.edt_operando1);
        operando2 = (EditText) findViewById(R.id.edt_operando2);
        btnMas = (Button) findViewById(R.id.btn_mas);
        btnMas.setOnClickListener(this);
        txvResultado = (TextView) findViewById(R.id.txv_resultado);
        txvPropiedades = (TextView) findViewById(R.id.txv_propiedades);
        miMemoria = new Memoria(getApplicationContext().getFilesDir().toString());
    }

    @Override
    public void onClick(View v) {
        String op1 = operando1.getText().toString();
        String op2 = operando2.getText().toString();
        String texto = "0";
        int resultado = 0;

        if (v == btnMas){
            try {
                resultado = Integer.parseInt(op1) + Integer.parseInt(op2);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                texto = "0";
            }
            texto = String.valueOf(resultado);
            txvResultado.setText(texto);

            if (miMemoria.disponibleEscritura())
                if (miMemoria.escribirExterna(NOMBRE_FICHERO, texto, false, "UTF-8")) {
                    txvPropiedades.setText(miMemoria.mostrarPropiedadesExterna(NOMBRE_FICHERO));
                } else
                    txvPropiedades.setText("Error al escribir el archivo en memoria externa");
            else
                txvPropiedades.setText("Memoria externa no disponible");
        }
    }
}
