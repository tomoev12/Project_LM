package exam04;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.KeyAdapter;

public class WelcomeFrame extends JFrame{

	private static JLabel		  lbl;
	private static JButton		  btn;
	private static JTextField	  txt;

	public WelcomeFrame () {
		this.setTitle("Welcome Frame");

		initComponent();

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(500, 300);
		this.setLocation(200, 300);
		this.setVisible(true);
	}

	private void initComponent() {
		// 패널1 : 텍스트필드, 버튼
		JPanel  pan1 = new JPanel(); 
		pan1.setLayout( new GridLayout(1, 2) );
		
		txt = new JTextField(10);
		txt.setToolTipText("이름을 입력하세요");
		pan1.add(txt);
		
		btn = new JButton("확인");
		btn.setFont(new Font("굴림", Font.PLAIN, 15));
		pan1.add(btn);

		// 패널2 : 라벨
		JPanel  pan2 = new JPanel(); 
		pan2.setLayout( new FlowLayout() );
		lbl = new JLabel(" ");
		pan2.add(lbl);
		
		// 패널3 = 패널1 + 패널2
		JPanel  pan3 = new JPanel(); 
		pan3.setLayout( new GridLayout(2, 1) );
		pan3.add(pan1);
		pan3.add(pan2);
		
		getContentPane().add(pan3);
		
		ActionListener greet = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = txt.getText();
			lbl.setText("반갑습니다 " + name + "님");
			}

		};
		this.btn.addActionListener( greet );
		
		KeyListener enter = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					btn.doClick();		
					txt.setText("");
				}
			}
		};
		this.txt.addKeyListener(enter);

	}

	public static void main(String[] args) {

		new WelcomeFrame();

	}

}
