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
	ArrayList<String> location;
public ProcessThread(Socket soc,JTextArea area,ArrayList<String> l){
    socket = soc;
	location = l;
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
		if(type.equals("register")){
			String loc[] = (String[])input[1];
			location.clear();
			for(int i=0;i<loc.length;i++){
				location.add(loc[i]);
				area.append("SU "+(i+1)+" register successfully\n"); 
			}
			String msg = "Registration process completed";
			Object res[] = {msg};
			out.writeObject(res);
			area.append(msg+"\n");
		}

		if(type.equals("query")){
			String time = (String)input[1];
			area.append("DB received query at : "+time+"\n");
			ArrayList<String> cf = new ArrayList<String>();
			for(int i=0;i<location.size();i++){
				String arr[] = location.get(i).split(",");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				double dis1 = getDistance(x,y,10,10);
				double dis2 = getDistance(x,y,220,10);
				double dis3 = getDistance(x,y,500,10);
				int selected = 1;
				int xa = 10;
				int ya = 10;
				if(dis2 < dis1){
					dis1 = dis2;
					selected = 2;
					xa = 220;
					ya = 10;
				}
				if(dis3 < dis1){
					dis1 = dis3;
					selected = 3;
					xa = 500;
					ya = 10;
				}
				int keys[] = {x,y,xa,ya};
				int n = keys.length;
				Cuckoo.cuckoo(keys,n);
				StringBuilder sb = new StringBuilder();
				for(int k=0;k<Cuckoo.ver;k++){
					for(int m=0;m<Cuckoo.MAXN;m++){
						if(Cuckoo.hash[k][m] != -1){
							sb.append(Cuckoo.hash[k][m]+" ");
						}
					}
				}
				cf.add(sb.toString().trim());
			}
			Object res[] = {cf};
			out.writeObject(res);
			area.append("Cuckoo Factor sent to SU\n");
		}

		if(type.equals("qsquery")){
			java.util.Date dd = new java.util.Date();
			java.sql.Timestamp time = new java.sql.Timestamp(dd.getTime());
			area.append("DB received query at : "+time.toString()+"\n");
			ArrayList<String> cf = new ArrayList<String>();
			for(int i=0;i<location.size();i++){
				String arr[] = location.get(i).split(",");
				int x = Integer.parseInt(arr[0]);
				int y = Integer.parseInt(arr[1]);
				double dis1 = getDistance(x,y,10,10);
				double dis2 = getDistance(x,y,220,10);
				double dis3 = getDistance(x,y,500,10);
				int selected = 1;
				int xa = 10;
				int ya = 10;
				if(dis2 < dis1){
					dis1 = dis2;
					selected = 2;
					xa = 220;
					ya = 10;
				}
				if(dis3 < dis1){
					dis1 = dis3;
					selected = 3;
					xa = 500;
					ya = 10;
				}
				int keys[] = {x,y,xa,ya};
				int n = keys.length;
				Cuckoo.cuckoo(keys,n);
				StringBuilder sb = new StringBuilder();
				for(int k=0;k<Cuckoo.ver;k++){
					for(int m=0;m<Cuckoo.MAXN;m++){
						if(Cuckoo.hash[k][m] != -1){
							sb.append(Cuckoo.hash[k][m]+" ");
						}
					}
				}
				cf.add(HMAC.getHmac(sb.toString().trim(),"crnnetwork".getBytes()));
			}
			Object res[] = {cf};
			out.writeObject(res);
			area.append("Cuckoo Factor sent to SU\n");
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
