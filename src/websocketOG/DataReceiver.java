/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocketOG;

import java.net.URI;

/**
 *
 * @author RaySis
 */
public class DataReceiver{
    
    public String WebSocketPort;
    public String SerialPort;
    public Client WebSocketClient;
    public Boolean WebSocketActive = false;

    Thread WSThread;
    
    public DataReceiver(String WebSocketPort, String SerialPort){
        this.WebSocketPort = WebSocketPort;
        this.SerialPort = SerialPort;
        WebSocketClient = new Client(URI.create("ws://localhost:"+WebSocketPort+"/"+SerialPort));
        try{
            ReadData();
        }catch(Exception e){}     
    }
    
    public void ReadData(){
        if(WebSocketClient != null){
            WSThread = new Thread(() -> {
                WebSocketClient.connect();
                WebSocketActive = true;
                while (WebSocketActive == true) { }
            });
            WSThread.start();
        }   
    }
    
    
}