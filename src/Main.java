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
	public static Main main; //���α׷� ��ü�� ������ Main ��ü �Դϴ�
	JFrame window; //���α׷� ��ü�� UI â�Դϴ�
	JPanel layout; //��ü UI�� ������ Panel�Դϴ�
	JPanel InfoLayout; //ȣ��Ʈ�� Ŭ���̾�Ʈ�� �� �ǳ��Դϴ�.
	CheckboxGroup group; //üũ�ڽ� �׷��Դϴ�
	Checkbox host; //ȣ��Ʈ üũ�ڽ��Դϴ�
	Checkbox client; //Ŭ���̾�Ʈ üũ�ڽ��Դϴ�
	Label IPLabel; 
	Label PORTLabel; //IP�� ��Ʈ ������Դϴ�
	JTextField IPField; 
	JTextField PORTField; //IP�� ��Ʈ�� �Է��ϴ� �ؽ�Ʈ �ʵ��Դϴ�
	JPanel buttonLayout; //���۹�ư ���̾ƿ��� ������ �ǳ��Դϴ�
	JButton startButton; // ���۹�ư�Դϴ�
	ChattingFrame chattingRoom; //ä��â ��ü�Դϴ�
	JPanel IPPanel; 
	JPanel PORTPanel; //IP�� PORT�� ������ �ǳ��Դϴ�
	public void setView(boolean b){ //����â�� ������ü���� ����â�� ������ �Լ� �Դϴ�
		window.setVisible(b);
	}
	public Main(){ //������ �������Դϴ�.
		onCreate();
	}
	void onCreate(){ //������ ���̾ƿ��� �����ϴ� �Լ��Դϴ�.
		window = new JFrame("ä��"); //��ä�á��̶� TItle�� ������ â�� �����մϴ�.
		window.setSize(250,400); //������â�� ����� �����մϴ�
		layout = new JPanel();
		InfoLayout = new JPanel();
		layout.setLayout(new GridLayout(4,1));
		group = new CheckboxGroup();
		host = new Checkbox("ȣ��Ʈ",true,group);
		host.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) { //ȣ��Ʈ�� ���õǸ� IP�ʵ带 ��Ȱ��ȭ �ϴ� �������Դϴ�
				boolean checked = host.getState();
				if(checked) IPField.setEditable(false);
				else IPField.setEditable(true);
			}
		});
		client = new Checkbox("Ŭ���̾�Ʈ",false,group);
		client.addItemListener(new ItemListener(){  //ȣ��Ʈ�� ���õǸ� IP �ʵ带 ��Ȱ��ȭ�ϴ� �������Դϴ�.
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
		IPLabel = new Label("������ �ּ� :");
		IPPanel.add(IPLabel);
		IPField = new JTextField("127.0.0.1");
		IPField.setEditable(false);
		IPPanel.add(IPField);
		PORTPanel = new JPanel();
		PORTPanel.setLayout(new FlowLayout());
		PORTLabel = new Label("��Ʈ :");
		PORTPanel.add(PORTLabel);
		PORTField = new JTextField("5000");
		PORTPanel.add(PORTField);
		layout.add(InfoLayout);
		layout.add(IPPanel);
		layout.add(PORTPanel);
		buttonLayout = new JPanel();
		buttonLayout.setLayout(new FlowLayout());
		startButton = new JButton("����");
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
	public static void main(String[] args){ //���α׷��� �������Դϴ�.
		main =new Main();
	}
}