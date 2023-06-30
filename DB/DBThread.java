package com;
public class DBThread extends Thread{
	DB server;
public DBThread(DB server){
	this.server=server;
	start();
}
public void run(){
	server.start();
}
}