package com.example.usuario.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ficheros_Actvity extends AppCompatActivity implements View.OnClickListener{
    Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficheros__actvity);

        btn1 = (Button) findViewById(R.id.btn_interna);
        btn2 = (Button) findViewById(R.id.btn_externa);
        btn3 = (Button) findViewById(R.id.btn_lectura);
        btn4 = (Button) findViewById(R.id.btn_codificacion);
        btn5 = (Button) findViewById(R.id.btn_exploracion);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if (v == btn1)
            intent = new Intent(this, EscrituraInterna.class);
        if (v == btn2)
            intent = new Intent(this, EscrituraExterna.class);
        if (v == btn3)
            intent = new Intent(this, Lectura.class);
        if (v == btn4)
            intent = new Intent(this, Codificacion.class);
        if (v == btn5)
            intent = new Intent(this, Exploracion.class);

        if (intent != null)
            startActivity(intent);
    }
}
