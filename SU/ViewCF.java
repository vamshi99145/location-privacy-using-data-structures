package com;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
public class ViewCF extends JFrame{
	
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	Font f1;
	JPanel p1;
	
public ViewCF(){
	super("View Cuckoo Factor");
	setLayout(new BorderLayout());
	f1 = new Font("Times New Roman",Font.BOLD,13);
	p1 = new JPanel();
	p1.setBackground(Color.white);
	p1.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	jsp = new JScrollPane(table);
	table.setFont(f1);
	table.setRowHeight(30);
	dtm.addColumn("Spectrum ID");
	dtm.addColumn("Location");
	dtm.addColumn("Availability");
	p1.add(jsp,BorderLayout.CENTER);

	add(p1,BorderLayout.CENTER);
}
}