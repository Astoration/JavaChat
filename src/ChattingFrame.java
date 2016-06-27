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
	Network socket;
	int connection = -1;
	String InputBuffer="";
	final Dialog InfoDialog = new Dialog(window,"����",true);
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
				
				if(!InputBuffer.equals("")){
					socket.send(InputBuffer);
					writeLine("��",InputBuffer);
					InputBuffer="";
				}else{
					System.out.println("������ �����۵�1");
				}
			}
		}
	}
	class ImageTextArea extends JTextArea{
		private Image BackgroundImage;
		public ImageTextArea(String string) {
			super(string);
			setOpaque(false);
		}
		public void setBackgroundImage(Image image){
			this.BackgroundImage=image;
			this.repaint();
		}
		@Override
		protected void paintComponent(Graphics g){
	        g.setColor(getBackground());
	        g.fillRect(0, 0, getWidth(), getHeight());
	        if (BackgroundImage != null) {
	        	int x = (this.getWidth()-BackgroundImage.getWidth(this))/2;
	        	int y = (this.getHeight()-BackgroundImage.getHeight(this));
	            g.drawImage(BackgroundImage, x, y, this);    
	        }
	        super.paintComponent(g);
		}
	}
	void writeLine(String tag,String content){
		String text = textArea.getText();
		text+=("\n"+tag+" : "+content);
		textArea.setText(text);
		scrollArea.setScrollPosition(new Point(0,textArea.getHeight()));
	}
	public ChattingFrame(boolean opn, String IP, int PORT){
		this.opn=opn;
		InfoDialog.setSize(300, 100);
		InfoDialog.setLocation(250, 250);
		InfoDialog.setLayout(new GridLayout(4,1));
		InfoDialog.add(new Label("������ : ������",Label.CENTER));
		InfoDialog.add(new Label("Java ������",Label.CENTER));
		InfoDialog.add(new Label("�� �̰� �ָ������",Label.CENTER));
		Button button = new Button("Ȯ��");
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
	void onCreate(){
		window = new JFrame();
		window.setTitle("ä��");
		window.setSize(500,500);
		layout = new JPanel();
		layout.setLayout(new BorderLayout());
		window.add(layout);
		textArea = new ImageTextArea("ä���� ���۵Ǿ����ϴ�.");
		textArea.setSize(500, 450);
		textArea.setEditable(false);
		scrollArea = new ScrollPane();
		scrollArea.add(textArea);
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
					textInput.setText("");
				}		
			}
			
		});
		layout.add(textInput,"South");
		menuOption = new JMenuBar();
		OptionMenu = new JMenu("����");
		Background = new JMenuItem("���ȭ�� ����");
		Background.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(System.getProperty("user.home"));
                int returnVal = fc.showOpenDialog(window);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Image image = ImageIO.read(fc.getSelectedFile());
                        if (image != null){
                        	int height = image.getHeight(textArea);
                        	float ratio = (float)scrollArea.getHeight()/(float)height;
                        	image = image.getScaledInstance((int)(image.getWidth(textArea)*ratio), (int)(image.getHeight(textArea)*ratio), 400);
                            textArea.setBackgroundImage(image);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
			}
			
		});
		
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
		ProgramInfo = new JMenuItem("���α׷� ����");
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
	void ChatMain(){
		new Thread(new ReadThread()).start();
		new Thread(new WriteThread()).start();
	}
}
