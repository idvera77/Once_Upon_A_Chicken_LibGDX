package com.ivan.popollo_adventures;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import basededatos.MiOpenHelper;

public class Registro extends AppCompatActivity {
    private EditText usuario, password;
    private MiOpenHelper moh;
    private SQLiteDatabase database;
    private Boolean repetido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        moh = new MiOpenHelper(this);
        database = moh.getWritableDatabase();

        usuario = findViewById(R.id.campoUsuario);
        password = findViewById(R.id.campoPassword);
    }

    public void button(View view) {
        repetido = moh.nombreRepetido(usuario.getText().toString());
        if(!repetido){
            moh.guardarJugador(usuario.getText().toString(), password.getText().toString());
        }else{
            System.out.println("Repetido");
        }
    }
}