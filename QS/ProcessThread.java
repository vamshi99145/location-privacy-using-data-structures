package com;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;
import java.util.ArrayList;
public class ProcessThread extends Thread{
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
	JTextArea area;
	
public ProcessThread(Socket soc,JTextArea area){
    socket = soc;
	this.area=area;
    try{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }catch(Exception e){
        e.printStackTrace();
    }
}
@Override
public void run(){
    try{
		Object input[]=(Object[])in.readObject();
        String type=(String)input[0];
		if(type.equals("query")){
			ArrayList<String> query = (ArrayList<String>)input[1];
			Socket soc = new Socket("localhost",3333);
			ObjectOutputStream out1 = new ObjectOutputStream(soc.getOutputStream());
			Object req[]={"qsquery"};
			out1.writeObject(req);
			out1.flush();
			ObjectInputStream in1 = new ObjectInputStream(soc.getInputStream());
			Object res[] = (Object[])in1.readObject();
			ArrayList<String> response = (ArrayList<String>)res[0];
			int selected = -1;
			for(int i=0;i<query.size();i++){
				String qry = query.get(i);
				if(response.contains(qry)){
					selected = i;
					break;
				}
			}
			String msg = "Query result sent to SU";
			Object res1[] = {selected+""};
			out.writeObject(res1);
			area.append(msg+"\n");
		}

		
		
    }catch(Exception e){
        e.printStackTrace();
    }
}
public double getDistance(int n1x,int n1y,int n2x,int n2y) {
	int dx = (n1x - n2x) * (n1x - n2x);
	int dy = (n1y - n2y) * (n1y - n2y);
	int total = dx + dy; 
	return Math.sqrt(total);
}
}
