package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.Random;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import org.jfree.ui.RefineryUtilities;
import javax.swing.JComboBox;
import java.util.HashMap;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
public class Network extends JFrame{
	Simulator node;
	JPanel p1,p2;
	JLabel l1;
	static JLabel l2;
	JComboBox c1;
	Font f1;
	int size;
	JButton b5,b1,b2,b3,b4,b6;
	ArrayList<String> response;
	static long ltime,lqstime;

public Network(int sz){
	super("CRN Networks");
	size = sz;
	f1 = new Font("Courier New",Font.BOLD,14);
	node = new Simulator();
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	p1.add(node,BorderLayout.CENTER);
	p1.setBackground(new Color(119,69,0));
	getContentPane().add(p1,BorderLayout.CENTER);
	p2 = new JPanel();
	p2.setLayout(new MigLayout("wrap 1")); 
	
	l1 = new JLabel("SU Name");
	l1.setFont(f1);
	p2.add(l1,"span,split 7");
	p2.setPreferredSize(new Dimension(800,80));
	c1 = new JComboBox();
	c1.setFont(f1);
	for(int i=1;i<=size;i++){
		c1.addItem("SU"+Integer.toString(i));
	}
	p2.add(c1);

	b5 = new JButton("SU Registration");
	b5.setFont(f1);
	p2.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			register();
		}
	});

	b1 = new JButton("SU LPDB Query");
	b1.setFont(f1);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Runnable r = new Runnable(){
				public void run(){
					sendQuery();
				}
			};
			new Thread(r).start();
		}
	});

	b6 = new JButton("SU LPDBQS Query");
	b6.setFont(f1);
	p2.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Runnable r = new Runnable(){
				public void run(){
					sendQueryServer();
				}
			};
			new Thread(r).start();
		}
	});

	b3 = new JButton("View Filter");
	b3.setFont(f1);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			viewCF();
		}
	});

	b4 = new JButton("Computation Cost");
	b4.setFont(f1);
	p2.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("Computation Cost");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	l2 = new JLabel();
	l2.setFont(f1);
	p2.add(l2);

	
	getContentPane().add(p2,BorderLayout.SOUTH);
	Node.randomNodes(size,800,600,node,400);
	node.option = 0;
	node.repaint();
}

public void register(){
	StringBuilder buffer = new StringBuilder();
	for(int i=0;i<node.circles.size();i++){
		Circle circle = node.circles.get(i);
		buffer.append(circle.getX()+","+circle.getY()+"#");
	}
	buffer.deleteCharAt(buffer.length()-1);
	String register[] = buffer.toString().trim().split("#");
	try{
		Socket socket = new Socket("localhost",3333);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Object req[]={"register",register};
		out.writeObject(req);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Object res[] = (Object[])in.readObject();
		String response = (String)res[0];
		if(response.equals("Registration process completed")){
			JOptionPane.showMessageDialog(Network.this,response);
		}else{
			JOptionPane.showMessageDialog(Network.this,"registration failed");
		}
		Main.area.append(response+"\n");
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void viewCF(){
	String su = c1.getSelectedItem().toString().trim();
	java.util.Date dd = new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(dd.getTime());
	Circle src = null;
	for(int i=0;i<node.circles.size();i++){
		Circle circle = node.circles.get(i);
		if(circle.getNode().equals(su)){
			src = circle;
			break;
		}
	}
	ViewCF vc = new ViewCF();
	String spectrum[] = {"10,10","220,10","500,10"};
	for(int i=0;i<spectrum.length;i++){
		String arr[] = spectrum[i].split(",");
		int x = Integer.parseInt(arr[0]);
		int y = Integer.parseInt(arr[1]);
		int keys[] = {src.getX(),src.getY(),x,y};
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
		String cf = sb.toString().trim();
		System.out.println(cf);
		if(response.contains(cf)){
			Object row[] = {"Spectrum"+(i+1),cf,"true"};
			vc.dtm.addRow(row);
		}else{
			String arr1[] = cf.split("\\s+");
			Object row[] = {"Spectrum"+(1+1),cf,"false"};
			vc.dtm.addRow(row);
		}
	}
	vc.setVisible(true);
	vc.setSize(800,400);
}

public void sendQuery(){
	String su = c1.getSelectedItem().toString().trim();
	long start = System.nanoTime();
	java.util.Date dd = new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(dd.getTime());
	Circle src = null;
	for(int i=0;i<node.circles.size();i++){
		Circle circle = node.circles.get(i);
		if(circle.getNode().equals(su)){
			src = circle;
			break;
		}
	}
	try{
		Main.area.append(su+" sending query to DB at "+time);
		Socket socket = new Socket("localhost",3333);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Object req[]={"query",time.toString()};
		out.writeObject(req);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Object res[] = (Object[])in.readObject();
		response = (ArrayList<String>)res[0];
		String spectrum[] = {"10,10","220,10","500,10"};
		int index = 0;
		for(int i=0;i<spectrum.length;i++){
			String arr[] = spectrum[i].split(",");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);
			int keys[] = {src.getX(),src.getY(),x,y};
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
			String cf = sb.toString().trim();
			if(response.contains(cf)){
				index = i;
				break;
			}
		}
		long end = System.nanoTime();
		ltime = end - start;
		new SendData(index,src,node);
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void sendQueryServer(){
	String su = c1.getSelectedItem().toString().trim();
	long start = System.nanoTime();
	java.util.Date dd = new java.util.Date();
	java.sql.Timestamp time = new java.sql.Timestamp(dd.getTime());
	Circle src = null;
	for(int i=0;i<node.circles.size();i++){
		Circle circle = node.circles.get(i);
		if(circle.getNode().equals(su)){
			src = circle;
			break;
		}
	}
	try{
		Main.area.append(su+" sending query to DB at "+time);
		String spectrum[] = {"10,10","220,10","500,10"};
		int index = 0;
		ArrayList<String> query = new ArrayList<String>();
		for(int i=0;i<spectrum.length;i++){
			String arr[] = spectrum[i].split(",");
			int x = Integer.parseInt(arr[0]);
			int y = Integer.parseInt(arr[1]);
			int keys[] = {src.getX(),src.getY(),x,y};
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
			String cf = sb.toString().trim();
			query.add(HMAC.getHmac(cf,"crnnetwork".getBytes()));
		}
		long end = System.nanoTime();
		lqstime = end - start;
		Socket socket = new Socket("localhost",4444);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		Object req[]={"query",query};
		out.writeObject(req);
		out.flush();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		Object res[] = (Object[])in.readObject();
		String response = (String)res[0];
		SendData sd = new SendData(Integer.parseInt(response),src,node);
		sd.join();
		ViewMessage vm = new ViewMessage();
		vm.setVisible(true);
		vm.setSize(600,400);
		for(int i=0;i<query.size();i++){
			vm.area.append(query.get(i)+"\n");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}
}