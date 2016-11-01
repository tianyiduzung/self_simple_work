package com.yijia.server;

import java.io.*;
import java.net.*;
import java.util.Vector;

import com.yijia.client.XiangQi;

public class ServerAgentThread extends Thread{

	Server father;
	Socket sc;
	DataInputStream din;
	DataOutputStream dout;
	boolean flag=true;
	public ServerAgentThread(Server father,Socket sc){
		this.father=father;
		this.sc=sc;
		try {
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	public void run(){
		while(flag){
			try {
				String msg=din.readUTF().trim();
				if(msg.startsWith("<#NICK_NAME#>")){
					this.nick_name(msg);
				}else if(msg.startsWith("<#CLIENT_LEAVE#>"))
				{
					this.client_leave(msg);
				}else if(msg.startsWith("<#TIAO_ZHAN#>"))
				{
					this.tiao_zhan(msg);
				}else if(msg.startsWith("<#TONG_YI#>"))
				{
					this.tong_yi(msg);
				}else if(msg.startsWith("<#BUTONG_YI#>"))
				{
					this.butong_yi(msg);
				}else if(msg.startsWith("<#BUSY#>"))
				{
					this.busy(msg);
				}else if(msg.startsWith("<#MOVE#>"))
				{
					this.move(msg);
				}else if(msg.startsWith("<#RENSHU#>"))
				{
					this.renshu(msg);
				}else if(msg.startsWith("<#SEND#>")){
					this.send(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	private void send(String msg) {
		// TODO Auto-generated method stub
		try {
			String ms[]=msg.split("<#SEND#>");
			String name1=this.getName();
			String name2=ms[1];
			String text=ms[2];
			Vector v=father.onlineList;
			int size=v.size();
			for(int i=0;i<size;i++){
				ServerAgentThread tempSat=(ServerAgentThread)v.get(i);
				if(tempSat.getName().equals(name2)){
					tempSat.dout.writeUTF("<#SEND#>"+name1+"<#SEND#>"+text);
					break;
				}
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void nick_name(String msg) {
		// TODO Auto-generated method stub
		try{
			String name=msg.substring(13);
			this.setName(name);
			Vector v=father.onlineList;
			boolean isChongMing=false;
			int size=v.size();
			for(int i=0;i<size;i++){
				ServerAgentThread tempSat=(ServerAgentThread)v.get(i);
				if(tempSat.getName().equals(name)){
					isChongMing=true;
					break;
				}
			}
			if(isChongMing==true){
				dout.writeUTF("<#NAME_CHONGMING#>");
				din.close();
				dout.close();
				sc.close();
				flag=false;
			}else{
				v.add(this);
				father.refreshList();
				String nickListMsg="";
				size=v.size();
				for(int i=0;i<size;i++){
					ServerAgentThread tempSat=(ServerAgentThread)v.get(i);
					nickListMsg=nickListMsg+"|"+tempSat.getName();
				}
				nickListMsg="<#NICK_LIST#>"+nickListMsg;
				Vector tempV=father.onlineList;
				size=tempV.size();
				for(int i=0;i<size;i++){
					ServerAgentThread satTemp=(ServerAgentThread)tempV.get(i);
					satTemp.dout.writeUTF(nickListMsg);
					if(satTemp!=this){
						satTemp.dout.writeUTF("<#MSG#>"+this.getName()+"上线了...");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void client_leave(String msg){
		try{
			Vector tempv=father.onlineList;//获得在线列表
			tempv.remove(this);//移除该用户
			int size=tempv.size();
			String nl="<#NICK_LIST#>";
			for(int i=0;i<size;i++){//遍历在线列表
				ServerAgentThread satTemp=(ServerAgentThread)tempv.get(i);
				//向各个客户端发送用户离线信息
				satTemp.dout.writeUTF("<#MSG#>"+this.getName()+"离线了...");
				//组织信息的在线用户列表
				nl=nl+"|"+satTemp.getName();
			}
			for(int i=0;i<size;i++){//将最新的列表信息发送到各个客户端
				ServerAgentThread satTemp=(ServerAgentThread)tempv.get(i);
				satTemp.dout.writeUTF(nl);
			}
			this.flag=false;//终止该服务器代理线程
			father.refreshList();//更新服务器在线用户列表
		}
		catch(IOException e){e.printStackTrace();}
	}
	public void tiao_zhan(String msg)
	{
		try
		{
			String name1=this.getName();//获得发出挑战信息用户的名字
			String name2=msg.substring(13);//获得被挑战的用户名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++)
			{//遍历列表，搜索被挑战的用户
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name2))
				{//向该用户发送挑战信息，附带提出挑战用户的名字
					satTemp.dout.writeUTF("<#TIAO_ZHAN#>"+name1);
					break;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void tong_yi(String msg){
		try{
			String name=msg.substring(11);//获得提出挑战的用户的名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++){//遍历列表，搜索提出挑战的用户
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name)){//向该用户发送对方接受挑战的信息
					satTemp.dout.writeUTF("<#TONG_YI#>");
					break;
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	public void butong_yi(String msg){
		try{
			String name=msg.substring(13);//获得提出挑战的用户的名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++)
			{//遍历列表，搜索提出挑战的用户
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name)){//向该用户发送对方拒绝挑战的信息
					satTemp.dout.writeUTF("<#BUTONG_YI#>");
					break;
				}
			}
		}
		catch(IOException e){e.printStackTrace();}
	}
	public void busy(String msg){
		try{
			String name=msg.substring(8);//获得提出挑战的用户的名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++){//遍历列表，搜索提出挑战的用户
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name)){//向该用户发送对方正在忙的信息
					satTemp.dout.writeUTF("<#BUSY#>");
					break;
				}
			}
		}
		catch(IOException e){e.printStackTrace();}
	}
	public void move(String msg){
		try{
			String name=msg.substring(8,msg.length()-4);//获得接收方的名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++){//遍历列表，搜索接收方
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name)){//将该信息转发给接收方
					satTemp.dout.writeUTF(msg);
					break;
				}
			}
		}
		catch(IOException e){e.printStackTrace();}
	}
	public void renshu(String msg){
		try{
			String name=msg.substring(10);//获得接收方的名字
			Vector v=father.onlineList;//获得在线用户列表
			int size=v.size();//获得在线用户列表的大小
			for(int i=0;i<size;i++){//遍历列表，搜索接收方
				ServerAgentThread satTemp=(ServerAgentThread)v.get(i);
				if(satTemp.getName().equals(name)){//将该信息转发给接收方
					satTemp.dout.writeUTF(msg);
					break;
				}
			}
		}
		catch(IOException e){e.printStackTrace();}
	}
}

