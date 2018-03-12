package com.example.nicols.poolcleaner;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Channel implements Runnable
{
    private Socket socket; //private DatagramSocket socket;
    private boolean running;
    private OnSocketListener onSocketListener;
    private String ip;
    private int port;

    public Channel(OnSocketListener onSocketListener)
    {
        this.onSocketListener=onSocketListener;
    }

    public void bind(String ip, int port) throws IOException
    {
        this.ip=ip;
        this.port=port;
    }

    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stop() throws IOException {
        running = false;
        socket.close();
    }

    @Override
    public void run()
    {
        socket = null;
        try {
            socket = new Socket(this.ip, this.port); //DatagramSocket(port);
        }catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msj;
        InputStream is = null;
        try {
            is = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        running = true;
        while(running)
        {
            try
            {
                //Buffer the data coming from the input stream
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //Read data in the input buffer
                msj = br.readLine();
                if (null!=onSocketListener){
                    onSocketListener.recibido(msj);
                }

            }
            catch (IOException e)
            {
                break;
            }
        }
    }

}
