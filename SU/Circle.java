package com;
import java.awt.Point;
import java.awt.Graphics;
import javax.crypto.SecretKey;
public class Circle{
	int x;           
    int y;           
    int dia;    
    String node;
public Circle(Point center, int radius){
	x   = center.x;
    y   = center.y;
    dia = radius;
}
public void setX(int x){
	this.x=x;
}
public int getX(){
	return x;
}
public void setY(int y){
	this.y=y;
}
public int getY(){
	return y;
}
public void draw(Graphics g,String option){
	if(option.equals("fill"))
		g.fillOval(x, y, dia,dia);
	if(option.equals("empty"))
		g.drawOval(x, y, dia,dia);
}
public void setNode(String node){
	this.node=node;
}
public String getNode(){
	return node;
}
}

