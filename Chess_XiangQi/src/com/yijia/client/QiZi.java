package com.yijia.client;
import java.awt.*;

public class QiZi {

	private Color color;
	private String name;
	private int x;
	private int y;
	private boolean focus=false;
	public QiZi(){
	}
	public QiZi(Color color,String name,int x,int y){
		this.color=color;this.name=name;
		this.x=x;this.y=y;this.focus=false;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean getFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}

}

