package com;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
public class Simulator extends JComponent{
	public int option=0;
	public ArrayList<Circle> circles = new ArrayList<Circle>();
	float dash1[] = {10.0f};
	BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f, dash1, 0.0f);
	BasicStroke rect=new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,1f,new float[] {2f},0f);
	Dimension dim;
	int index;
	Circle src;
public void setSource(int i,Circle c){
	index = i;
	src = c;
}
public Simulator() {
	super.setBackground(new Color(255,0,255));
	this.setBackground(new Color(255,0,255));
}
public ArrayList<Circle> getList(){
	return circles;
}
public void removeAll(){
	option=0;
	circles.clear();
	repaint();
}
public void paintComponent(Graphics g1){
	super.paintComponent(g1);
	GradientPaint gradient = new GradientPaint(0, 0, Color.green, 175, 175, Color.yellow,true); 
	Graphics2D g = (Graphics2D)g1;
	g.setPaint(gradient);
	g.setStroke(rect);
	Rectangle2D rectangle = new Rectangle2D.Double(10,10,170,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Spectrum1",90,40);
	rectangle = new Rectangle2D.Double(220,10,170,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Spectrum2",300,40);
	rectangle = new Rectangle2D.Double(500,10,170,40);
	g.setStroke(rect);
	g.draw(rectangle);
	g.drawString("Spectrum3",580,40);
	
	if(option == 0){
		for(int i=0;i<circles.size();i++){
			Circle circle = circles.get(i);
			circle.draw(g,"fill");
			g.drawString(circle.getNode(),circle.x+10,circle.y+50);
		}
	}
	if(option == 1){
		for(int i=0;i<circles.size();i++){
			Circle circle = circles.get(i);
			circle.draw(g,"fill");
			g.drawString(circle.getNode(),circle.x+10,circle.y+50);
		}
		g.setStroke(dashed);
		if(index == 0){
			g.drawLine(src.x+10,src.y+10,50,40);
		}
		if(index == 1){
			g.drawLine(src.x+10,src.y+10,260,40);
		}
		if(index == 2){
			g.drawLine(src.x+10,src.y+10,540,40);
		}
	}
	
}	


}