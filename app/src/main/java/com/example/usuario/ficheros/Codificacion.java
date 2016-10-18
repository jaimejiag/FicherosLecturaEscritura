package com.example.usuario.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Codificacion extends AppCompatActivity implements View.OnClickListener{
    public static final String UTF_8 = "UTF-8";
    public static final String UTF_16 = "UTF-16";
    public static final String ISO = "ISO-8859-15";

    EditText ficheroLectura, ficheroEscritura, contenido;
    Button btnLeer, btnEscribir;
    RadioButton rbtnUTF8, rbtnUTF16, rbtnISO;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificacion);

        iniciar();
    }

    private void iniciar() {
        ficheroLectura = (EditText) findViewById(R.id.edt_fichero);
        ficheroEscritura = (EditText) findViewById(R.id.edt_nuevoFichero);
        contenido = (EditText) findViewById(R.id.edt_contenido);
        btnLeer = (Button) findViewById(R.id.btn_leer);
        btnLeer.setOnClickListener(this);

        btnEscribir = (Button) findViewById(R.id.btn_guardar);
        btnEscribir.setOnClickListener(this);

        rbtnUTF8 = (RadioButton) findViewById(R.id.rbtn_utf8);
        rbtnUTF16 = (RadioButton) findViewById(R.id.rbtn_utf16);
        rbtnISO = (RadioButton) findViewById(R.id.rbtn_iso);
        miMemoria = new Memoria(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        String codigo = UTF_8;
        String nombreFichero = "";
        String mensaje = "";
        Resultado r;

        if (rbtnUTF16.isChecked())
            codigo = UTF_16;
        else if (rbtnISO.isChecked())
            codigo = ISO;

        if (v == btnLeer) {
            nombreFichero = ficheroLectura.getText().toString();

            if (nombreFichero.isEmpty())
                Toast.makeText(getApplicationContext(), "Introduzca un nombre de fichero", Toast.LENGTH_SHORT).show();
            else {
                r = miMemoria.leerExterna(nombreFichero,codigo);

                if (r.getCodigo())
                    contenido.setText(r.getContenido());
                else {
                    Toast.makeText(this, r.getMensaje(), Toast.LENGTH_SHORT).show();
                    contenido.setText("");
                }
            }
        }

        if (v == btnEscribir) {
            nombreFichero = ficheroEscritura.getText().toString();

            if (nombreFichero.isEmpty())
                mensaje = "Introduzca un nombre de fichero";
            else {
                if (miMemoria.disponibleEscritura())
                    if (miMemoria.escribirExterna(nombreFichero, contenido.getText().toString(), false, codigo))
                        mensaje = "Fichero escrito OK";
                    else
                        mensaje = "Error al escribir en memoria externa";
                else
                    mensaje = "Memoria externa no disponible";
            }

            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }
}
