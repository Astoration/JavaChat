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
	public static Main main; //프로그램 전체를 관리할 Main 객체 입니다
	JFrame window; //프로그램 전체의 UI 창입니다
	JPanel layout; //전체 UI를 관리할 Panel입니다
	JPanel InfoLayout; //호스트와 클라이언트를 고를 판넬입니다.
	CheckboxGroup group; //체크박스 그룹입니다
	Checkbox host; //호스트 체크박스입니다
	Checkbox client; //클라이언트 체크박스입니다
	Label IPLabel; 
	Label PORTLabel; //IP와 포트 설명글입니다
	JTextField IPField; 
	JTextField PORTField; //IP와 포트를 입력하는 텍스트 필드입니다
	JPanel buttonLayout; //시작버튼 레이아웃을 관리할 판넬입니다
	JButton startButton; // 시작버튼입니다
	ChattingFrame chattingRoom; //채팅창 객체입니다
	JPanel IPPanel; 
	JPanel PORTPanel; //IP와 PORT를 관리할 판넬입니다
	public void setView(boolean b){ //메인창을 전역객체에서 메인창을 관리할 함수 입니다
		window.setVisible(b);
	}
	public Main(){ //메인의 생성자입니다.
		onCreate();
	}
	void onCreate(){ //생성시 레이아웃을 구성하는 함수입니다.
		window = new JFrame("채팅"); //“채팅”이란 TItle의 윈도우 창을 생성합니다.
		window.setSize(250,400); //윈도우창의 사이즈를 지정합니다
		layout = new JPanel();
		InfoLayout = new JPanel();
		layout.setLayout(new GridLayout(4,1));
		group = new CheckboxGroup();
		host = new Checkbox("호스트",true,group);
		host.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) { //호스트가 선택되면 IP필드를 비활성화 하는 리스너입니다
				boolean checked = host.getState();
				if(checked) IPField.setEditable(false);
				else IPField.setEditable(true);
			}
		});
		client = new Checkbox("클라이언트",false,group);
		client.addItemListener(new ItemListener(){  //호스트가 선택되면 IP 필드를 비활성화하는 리스너입니다.
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
	public static void main(String[] args){ //프로그램의 진입점입니다.
		main =new Main();
	}
}