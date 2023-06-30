package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.BorderLayout;
import org.jfree.ui.RefineryUtilities;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ViewMessage extends JFrame{
	JPanel p1,p2;
	JTextArea area;
	JScrollPane jsp;
	Font f1;
	JButton b1;
public ViewMessage(){
	super("View Message Details");
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	f1 = new Font("Times New Roman",Font.BOLD,16);
	area = new JTextArea();
	area.setEditable(false);
	area.setLineWrap(true);
	area.setFont(f1);
	jsp = new JScrollPane(area);
	p1.add(jsp,BorderLayout.CENTER);

	
	getContentPane().add(p1,BorderLayout.CENTER);
	
}
}