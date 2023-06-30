package com;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
public class SendData extends Thread{
	Circle source;
	Simulator node;
	int index;
public SendData(int i,Circle source,Simulator n){
	index = i;
	this.source = source;
	node = n;
	start();
}

public void run(){
	try{
		for(int k=0;k<6;k++){
			node.setSource(index,source);
			node.option=1;
			node.repaint();
			sleep(500);
			node.option=0;
			node.repaint();
			sleep(500);
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}
}
}