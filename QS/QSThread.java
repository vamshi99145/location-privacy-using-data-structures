package com;
public class QSThread extends Thread{
	QS server;
public QSThread(QS server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}