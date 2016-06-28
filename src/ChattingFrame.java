import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
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
	BufferedImage Image=null;
	String ImagePath = "";
	public static ImageTextArea textArea;
	ScrollPane scrollArea;
	JTextField textInput;
	JMenuBar menuOption;
	JMenu OptionMenu;
	JMenu InfoMenu;
	JMenuItem Background;
	JMenuItem Quit;
	JMenuItem ProgramInfo;
	Network socket; //네트워크를 갖는 소켓객체를 할당합니다
	int connection = -1;
	String InputBuffer="";//입력버퍼입니다
	final Dialog InfoDialog = new Dialog(window,"정보",true); //정보 다이얼로그 객체입니다
	boolean opn;
	class ReadThread implements Runnable{ //출력 쓰레드입니다
		@Override
		public void run() {
			if(connection == -1){
				if(opn){ //호스트와 클라이언트를 분기하여 처리합니다.
					writeLine("SYSTEM","연결 대기중입니다.");
					((Host)socket).listen();
					writeLine("SYSTEM","연결되었습니다. 즐거운 언어 생활을 가집시다.");
					((Host)socket).init();
				}
				else{
					writeLine("SYSTEM","서버에 접속중...");
					int errorNo=((Client)socket).init();
					while(errorNo<0){
						errorNo=((Client)socket).init();
					}
					writeLine("SYSTEM","연결되었습니다. 즐거운 언어 생활을 가집시다.");
				}
			}
			while(true){  //메세지를 수신하고 출력합니다.
				String string = "";
				string = socket.recv();
				if(!string.equals("")) writeLine("상대방",string);
			}
		}
	}
	class WriteThread implements Runnable{ //출력 쓰레드입니다.
		@Override
		public void run(){
			while(true){
				
				if(!InputBuffer.equals("")){ //입력버퍼가 차있으면 전송합니다.
					socket.send(InputBuffer);
					writeLine("나",InputBuffer);
					InputBuffer="";
				}else{
					System.out.println("쓰레드 정상작동1");
				}
			}
		}
	}
	class ImageTextArea extends JTextArea{ //배경화면을 가질 수 있는 TextArea객체를 JTextArea를 상속하여 만듭니다.
		private Image BackgroundImage; //배경화면 이미지 객체입니다.
		public ImageTextArea(String string) {
			super(string);
			setOpaque(false);
		}
		public void setBackgroundImage(Image image){ //배경화면을 지정합니다.
			this.BackgroundImage=image;
			this.repaint();
		}
		@Override
		protected void paintComponent(Graphics g){
	        g.setColor(getBackground());
	        g.fillRect(0, 0, getWidth(), getHeight());
	        if (BackgroundImage != null) {
	        	int x = (this.getWidth()-BackgroundImage.getWidth(this))/2; //중앙정렬을 계산합니다.
	        	int y = (this.getHeight()-BackgroundImage.getHeight(this));
	            g.drawImage(BackgroundImage, x, y, this); //배경화면을 그립니다.
	        }
	        super.paintComponent(g);
		}
	}
	void writeLine(String tag,String content){ //채팅을 기록하는 출력 함수입니다.
		String text = textArea.getText();
		text+=("\n"+tag+" : "+content); //말하는 사람과 그 내용을 더합니다.
		textArea.setText(text);
		scrollArea.setScrollPosition(new Point(0,textArea.getHeight())); //스크롤을 아래로 강제합니다.
	}
	public ChattingFrame(boolean opn, String IP, int PORT){ //채팅창 생성자입니다. 호스트여부와 IP와 PORT를 받습니다.
		this.opn=opn;
		InfoDialog.setSize(300, 100);
		InfoDialog.setLocation(250, 250);
		InfoDialog.setLayout(new GridLayout(4,1));
		InfoDialog.add(new Label("만든사람 : 정유빈",Label.CENTER));
		InfoDialog.add(new Label("Java 수행평가",Label.CENTER));
		InfoDialog.add(new Label("아 이거 왜만들었지",Label.CENTER));
		Button button = new Button("확인");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InfoDialog.setVisible(false);				
			}
		});
		InfoDialog.add(button);
		if(opn){
			socket = new Host(PORT);
		}else{
			socket = new Client(IP,PORT);
		}
		onCreate();
		ChatMain();
	}
	void onCreate(){ //레이아웃을 구성하는 생성함수입니다.
		window = new JFrame();
		window.setTitle("채팅");
		window.setSize(500,500);
		layout = new JPanel();
		layout.setLayout(new BorderLayout());
		window.add(layout);
		textArea = new ImageTextArea("채팅이 시작되었습니다.");
		textArea.setSize(500, 450);
		textArea.setEditable(false);
		scrollArea = new ScrollPane();
		scrollArea.add(textArea);
		scrollArea.setSize(500,450);
		layout.add(scrollArea,"Center");
		textInput = new JTextField();
		textInput.setText("메세지를 입력해주세요.");
		textInput.setSize(500,50);
		textInput.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				textInput.setText("");
			}
			
		});
		textInput.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand()!="\n"){
					InputBuffer=textInput.getText();
					textInput.setText("");
				}		
			}
			
		});
		layout.add(textInput,"South");
		menuOption = new JMenuBar();
		OptionMenu = new JMenu("설정");
		Background = new JMenuItem("배경화면 설정");
		Background.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System.getProperty("user.home")); //파일 선택창을 호출합니다.
                int returnVal = fc.showOpenDialog(window);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Image image = ImageIO.read(fc.getSelectedFile()); //이미지를 불러옵니다.
                        if (image != null){
                        	int height = image.getHeight(textArea);
                        	float ratio = (float)scrollArea.getHeight()/(float)height;
                        	image = image.getScaledInstance((int)(image.getWidth(textArea)*ratio), (int)(image.getHeight(textArea)*ratio), 400); //이미지를 계산하여 사이즈에 맞게 리사이징합니다.
                            textArea.setBackgroundImage(image); //배경이미지를 지정합니다.
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
			}
			
		});
		
		OptionMenu.add(Background);
		Quit = new JMenuItem("종료");
		Quit.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.main.setView(true);
				window.setVisible(false);
			}
			
		});
		OptionMenu.add(Quit);
		InfoMenu = new JMenu("정보");
		ProgramInfo = new JMenuItem("프로그램 정보");
		ProgramInfo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				InfoDialog.setVisible(true);
			}
		});
		InfoMenu.add(ProgramInfo);
		menuOption.add(OptionMenu);
		menuOption.add(InfoMenu);
		window.setJMenuBar(menuOption);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	void ChatMain(){ //쓰레드를 시작하여 실제로 채팅이 이루어지는 채팅 메인입니다.
		Thread read = new Thread(new ReadThread());
		Thread write= new Thread(new WriteThread());
		read.start();
		write.start();
	}
}