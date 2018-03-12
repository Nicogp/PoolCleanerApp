package com.example.nicols.poolcleaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.app.Activity;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {
    private EditText editTextIP;
    private EditText editTextPuerto;
    private TextView textViewIP;
    private TextView textViewPuerto;
    private Button buttonConectarse;
    private String ip;
    private int puerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIP = (EditText)findViewById(R.id.editTextIP);
        editTextPuerto = (EditText) findViewById(R.id.editTextPuerto);
        textViewIP = (TextView)  findViewById(R.id.textViewIP);
        textViewPuerto = (TextView) findViewById(R.id.textViewPuerto);
        buttonConectarse = (Button) findViewById(R.id.buttonConectar);

        buttonConectarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextIP.getText()== null || editTextIP.getText().equals("")){
                    Toast.makeText(MainActivity.this, "Debe ingresar una IP", Toast.LENGTH_LONG).show();
                }
                else {
                    if (editTextPuerto.getText() == null || editTextPuerto.getText().equals("")) {
                        Toast.makeText(MainActivity.this, "Debe ingresar un numero Puerto", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, editTextIP.getText()+  "  " + editTextPuerto.getText(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        ip=editTextIP.getText().toString();
                        puerto= Integer.parseInt(editTextPuerto.getText().toString());
                        intent.putExtra("ip", ip );
                        intent.putExtra("puerto", puerto);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}
