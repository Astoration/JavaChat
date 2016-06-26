import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Main {
	public static Main main;
	JFrame window;
	JPanel layout;
	JPanel InfoLayout;
	CheckboxGroup group;
	Checkbox host;
	Checkbox client;
	Label IPLabel;
	Label PORTLabel;
	JTextField IPField;
	JTextField PORTField;
	JPanel buttonLayout;
	JButton startButton;
	ChattingFrame chattingRoom;
	JPanel IPPanel;
	JPanel PORTPanel;
	public void setView(boolean b){
		window.setVisible(b);
	}
	public Main(){
		onCreate();
	}
	void onCreate(){
		window = new JFrame("채팅");
		window.setSize(250,400);
		layout = new JPanel();
		InfoLayout = new JPanel();
		layout.setLayout(new GridLayout(4,1));
		group = new CheckboxGroup();
		host = new Checkbox("호스트",true,group);
		host.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				boolean checked = host.getState();
				if(checked) IPField.setEditable(false);
				else IPField.setEditable(true);
			}
		});
		client = new Checkbox("클라이언트",false,group);
		client.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0){
				boolean checked = client.getState();
				if(checked) IPField.setEditable(true);
				else IPField.setEditable(false);
			}
		});
		InfoLayout.setLayout(new FlowLayout());
		InfoLayout.add(host);
		InfoLayout.add(client);
		IPPanel = new JPanel();
		IPPanel.setLayout(new FlowLayout());
		IPLabel = new Label("접속할 주소 :");
		IPPanel.add(IPLabel);
		IPField = new JTextField("127.0.0.1");
		IPField.setEditable(false);
		IPPanel.add(IPField);
		PORTPanel = new JPanel();
		PORTPanel.setLayout(new FlowLayout());
		PORTLabel = new Label("포트 :");
		PORTPanel.add(PORTLabel);
		PORTField = new JTextField("5000");
		PORTPanel.add(PORTField);
		layout.add(InfoLayout);
		layout.add(IPPanel);
		layout.add(PORTPanel);
		buttonLayout = new JPanel();
		buttonLayout.setLayout(new FlowLayout());
		startButton = new JButton("시작");
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				window.setVisible(false);
				chattingRoom=new ChattingFrame(host.getState(),IPField.getText(),Integer.parseInt(PORTField.getText()));
			}
			
		});
		buttonLayout.add(startButton);
		layout.add(buttonLayout);
		window.add(layout);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args){
		main =new Main();
	}
}
