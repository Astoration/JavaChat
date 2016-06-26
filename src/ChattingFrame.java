import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChattingFrame {
	JFrame window;
	JPanel layout;
	public static JTextArea textArea;
	JScrollPane scrollArea;
	JTextField textInput;
	JMenuBar menuOption;
	JMenu OptionMenu;
	JMenu InfoMenu;
	JMenuItem Background;
	JMenuItem Quit;
	JMenuItem ProgramInfo;
	Network socket;
	int connection = -1;
	String InputBuffer="";
	final Dialog ErrorDialog = new Dialog(window,"����",true);
	boolean opn;
	class ReadThread implements Runnable{
		@Override
		public void run() {
			if(connection == -1){
				if(opn){
					writeLine("SYSTEM","���� ������Դϴ�.");
					((Host)socket).listen();
					writeLine("SYSTEM","����Ǿ����ϴ�. ��ſ� ��� ��Ȱ�� �����ô�.");
					((Host)socket).init();
				}
				else{
					writeLine("SYSTEM","������ ������...");
					int errorNo=((Client)socket).init();
					while(errorNo<0){
						errorNo=((Client)socket).init();
					}
					writeLine("SYSTEM","����Ǿ����ϴ�. ��ſ� ��� ��Ȱ�� �����ô�.");
				}
			}
			while(true){
				String string = "";
				string = socket.recv();
				if(!string.equals("")) writeLine("����",string);
			}
		}
	}
	class WriteThread implements Runnable{
		@Override
		public void run(){
			while(true){
				System.out.println("������ �����۵�1");
				if(!InputBuffer.equals("")){
					socket.send(InputBuffer);
					writeLine("��",InputBuffer);
					InputBuffer="";
				}
			}
		}
	}
	void writeLine(String tag,String content){
		String text = textArea.getText();
		text+=("\n"+tag+" : "+content);
		textArea.setText(text);
	}
	public ChattingFrame(boolean opn, String IP, int PORT){
		this.opn=opn;
		ErrorDialog.setSize(200, 100);
		ErrorDialog.setLocation(250, 250);
		ErrorDialog.setLayout(new GridLayout(2,1));
		ErrorDialog.add(new Label("������ �߻��߽��ϴ�.",Label.CENTER));
		Button button = new Button("Ȯ��");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ErrorDialog.setVisible(false);				
			}
			
		});
		ErrorDialog.add(button);
		if(opn){
			socket = new Host(PORT);
		}else{
			socket = new Client(IP,PORT);
		}
		onCreate();
		ChatMain();
	}
	void onCreate(){
		window = new JFrame();
		window.setTitle("ä��");
		window.setSize(500,500);
		layout = new JPanel();
		layout.setLayout(new BorderLayout());
		window.add(layout);
		textArea = new JTextArea("ä���� ���۵Ǿ����ϴ�.");
		textArea.setSize(500, 450);
		textArea.setEditable(false);
		scrollArea = new JScrollPane(textArea);
		scrollArea.setSize(500,450);
		layout.add(scrollArea,"Center");
		textInput = new JTextField();
		textInput.setText("�޼����� �Է����ּ���.");
		textInput.setSize(500,50);
		textInput.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				textInput.setText("");
			}
			
		});
		textInput.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand()!="\n"){
					InputBuffer=textInput.getText();
					System.out.println(InputBuffer);
					textInput.setText("");
				}		
			}
			
		});
		layout.add(textInput,"South");
		menuOption = new JMenuBar();
		OptionMenu = new JMenu("����");
		Background = new JMenuItem("���ȭ�� ����");
		OptionMenu.add(Background);
		Quit = new JMenuItem("����");
		Quit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.main.setView(true);
				window.setVisible(false);
			}
			
		});
		OptionMenu.add(Quit);
		InfoMenu = new JMenu("����");
		InfoMenu.add(new JMenuItem("���α׷� ����"));
		menuOption.add(OptionMenu);
		menuOption.add(InfoMenu);
		window.setJMenuBar(menuOption);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	void ChatMain(){
		new Thread(new ReadThread()).start();
		new Thread(new WriteThread()).start();
	}
}
