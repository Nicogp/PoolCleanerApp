package com.example.nicols.poolcleaner;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class Main2Activity extends AppCompatActivity implements  OnSocketListener, Handler.Callback{
    private Channel channel;
    private InetSocketAddress direccion;
    private ListView messageListV;
    private ArrayAdapter<String> messageAdapter;
    //private ProgressBar barAgua;
    //private ProgressBar barCalentador;
    private TextView txtDeseada;
    private TextView txtCloro;
    private TextView txtPH;
    private TextView txtAgua;
    private TextView txtCalentador;
    private CustomGauge gaugeCalentador;
    private CustomGauge gaugeAgua;

    String ip;
    int puerto;

    private Button btnDesconectarse;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null && bundle.getString("ip")!=null && bundle.get("puerto")!=null){
            btnDesconectarse = (Button) findViewById(R.id.btnDesconectarse);
            //barAgua = (ProgressBar) findViewById(R.id.barAgua);
            //barCalentador=(ProgressBar)findViewById(R.id.barCalentador);
            txtAgua=(TextView)findViewById(R.id.txtAguaView);
            txtCalentador =(TextView)findViewById(R.id.txtCalentadorView);
            txtCloro=(TextView)findViewById(R.id.txtCloro);
            txtDeseada=(TextView)findViewById(R.id.txtDeseada);
            txtPH=(TextView)findViewById(R.id.txtPH);
            ip = bundle.getString("ip");
            puerto = bundle.getInt("puerto");
            direccion = new InetSocketAddress(ip, puerto);
            gaugeCalentador = (CustomGauge)findViewById(R.id.gaugeCalentador);
            gaugeAgua = (CustomGauge)findViewById(R.id.gaugeAgua);

            //messageAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_message, R.id.messageTV);
            //messageListV = (ListView) findViewById(R.id.messageListView);
            //messageListV.setAdapter(messageAdapter);
            handler = new Handler(this);
            //Toast.makeText(Main2Activity.this, "desde la otra vista "+ ip + " "+ puerto, Toast.LENGTH_LONG).show();
        }
        else{
            //Toast.makeText(Main2Activity.this, "It is empty", Toast.LENGTH_LONG).show();
        }

        btnDesconectarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    channel.stop();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(channel==null){
            try {
                channel = new Channel(this);
                channel.bind(ip, puerto);
                channel.start();
            }
            catch (IOException e){
                e.printStackTrace();
                finish();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (channel!=null){
            try {
                channel.stop();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void recibido(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] datos = msg.split(";");
                for (String indice : datos) {
                    String[] dato = indice.split("=");
                    String clave = dato[0];
                    String valor = dato[1];
                    switch (clave) {
                        case "AGUA":
                            try{
                                Double val = Double.parseDouble(valor);
                                txtAgua.setText(valor);
                                gaugeAgua.setValue(val.intValue());
                                //Toast.makeText(Main2Activity.this, "val: "+ val.intValue(), Toast.LENGTH_SHORT).show();
                            }catch (NumberFormatException e){
                                //Toast.makeText(Main2Activity.this, "Error valueof agua", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case "CALENTADOR":
                            try{
                                Double val1 = Double.parseDouble(valor);
                                txtCalentador.setText(valor);
                                gaugeCalentador.setValue(val1.intValue());
                                //Toast.makeText(Main2Activity.this, "val1: "+ val1.intValue(), Toast.LENGTH_SHORT).show();
                            }catch (NumberFormatException e){
                                //Toast.makeText(Main2Activity.this, "Error valueof calentador", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        case "PH":
                            //ph=valor;
                            txtPH.setText(valor);
                            //txtPH.setText(valor);
                            break;
                        case "CLORO":
                            //cloro=valor;
                            txtCloro.setText(valor);
                            //txtCloro.setText(valor);
                            break;
                        case "DESEADA":
                            //deseada=valor;
                            try{
                                Double val2 = Double.parseDouble(valor);
                                txtDeseada.setText(valor);
                                //barAgua.setSecondaryProgress(28);
                            }catch (NumberFormatException e){
                                Toast.makeText(Main2Activity.this, "Error valueof deseada", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        /*
        Bundle bundle = msg.getData();
        String clave = bundle.getString("clave");
        String valor = bundle.getString("valor");
        switch (clave){
            case "AGUA":
                //int val = Integer.parseInt(valor);
                txtAgua.setText(valor);
                //barAgua.setProgress(val);
                break;
            case "CALENTADOR":
                //int val1 = Integer.parseInt(valor);
                txtCalentador.setText(valor);
                //barCalentador.setProgress(val1);
                break;
            case "PH":
                txtPH.setText(valor);
                break;
            case "CLORO":
                txtCloro.setText(valor);
                break;
            case "DESEADA":
                txtDeseada.setText(valor);
                break;
            default:
                break;
        }

        //messageAdapter.add(txt);
        //messageListV.smoothScrollToPosition(messageAdapter.getCount()-1);*/
        return false;
    }
}
