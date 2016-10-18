package com.example.usuario.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Lectura extends AppCompatActivity implements View.OnClickListener{
    public static final String NUMERO = "numero";
    public static final String VALOR = "valor.txt";
    public static final String DATO = "dato.txt";
    public static final String DATO_SD = "dato_sd.txt";
    public static final String OPERACIONES = "operaciones.txt";

    EditText operando1, operando2, operando3, operando4;
    Button btnSumar;
    TextView operacion;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_ficheros);

        operando1 = (EditText) findViewById(R.id.edt_raw);
        operando2 = (EditText) findViewById(R.id.edt_assets);
        operando3 = (EditText) findViewById(R.id.edt_interna);
        operando4 = (EditText) findViewById(R.id.edt_externa);

        btnSumar = (Button) findViewById(R.id.btn_sumar);
        btnSumar.setOnClickListener(this);

        operacion = (TextView) findViewById(R.id.txv_resultado);

        miMemoria = new Memoria(getApplicationContext());

        iniciar();
    }

    private void iniciar() {
        Resultado r;

        //Lee el archivo de la carpeta raw del proyecto.
        r = miMemoria.leerRaw(NUMERO);
        if (r.getCodigo()){
            operando1.setText(r.getContenido());
        } else {
            Toast.makeText(this, r.getMensaje(), Toast.LENGTH_SHORT).show();
            operando1.setText("0");
        }

        //Lee el archivo de la carpeta assets del proyecto.
        r = miMemoria.leerAsset(VALOR);
        if (r.getCodigo()){
            operando2.setText(r.getContenido());
        } else {
            Toast.makeText(this, r.getMensaje(), Toast.LENGTH_SHORT).show();
            operando2.setText("0");
        }

        //Creo el fichero en interna y luego lo lee
        if (miMemoria.escribirInterna(DATO, "5", false, "UTF-8")) {
            r = miMemoria.leerInterna(DATO, "UTF-8");
            if (r.getCodigo()) {
                operando3.setText(r.getContenido());
            } else {
                Toast.makeText(this, r.getMensaje(), Toast.LENGTH_SHORT).show();
                operando3.setText("0");
            }
        } else
            Toast.makeText(this, "Error al escribir en memoria interna", Toast.LENGTH_SHORT).show();

        //Creo el fichero en externa y luego lo lee
        if (miMemoria.disponibleEscritura()) {
            if (miMemoria.escribirExterna(DATO_SD, "1", false, "UTF-8")){
                r = miMemoria.leerExterna(DATO_SD, "UTF-8");

                if (r.getCodigo())
                    operando4.setText(r.getContenido());
                else {
                    Toast.makeText(this, r.getMensaje(), Toast.LENGTH_SHORT).show();
                    operando4.setText("0");
                }
            } else
                Toast.makeText(this, "Error al escribir en memoria externa", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Memoria externa no disponible", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String op1 = operando1.getText().toString();
        String op2 = operando2.getText().toString();
        String op3 = operando3.getText().toString();
        String op4 = operando4.getText().toString();
        int r = 0;
        String texto = "0";
        String mensaje = "";

        if (v == btnSumar) {
            try {
                r = Integer.parseInt(op1) + Integer.parseInt(op2) + Integer.parseInt(op3) + Integer.parseInt(op4);
                texto = op1 + "+" + op2 + "+" + op3 + "+" + op4 + "=" + r;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            operacion.setText(String.valueOf(r));

            if (miMemoria.disponibleEscritura()){
                if (miMemoria.escribirExterna(OPERACIONES, texto + "\n", true, "UTF-8"))
                    mensaje = "Fichero " + OPERACIONES + "escrito OK";
                else
                    mensaje = "Error al escribir en memoria externa";
            } else {
                mensaje = "Memoria externa no disponible";
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
