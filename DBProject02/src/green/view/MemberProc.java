package green.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import green.model.MemberDao;
import green.model.MemberVo;

public class MemberProc extends JFrame {
	
	// 필요한 부품준비
	JPanel            p; 
	JTextField        txtId,  txtName,  txtIndate;
	JPasswordField    txtPwd;
	JComboBox         cbJob;
	JRadioButton      rbMan, rbWoman;
	ButtonGroup       group;
	JTextArea         taIntro;
	
	JButton           btnAdd, btnDelete, btnUpdate, btnCancel, btnFind;
	
	// GridBagLayout  
	GridBagLayout        gb;
	GridBagConstraints   gbc;
	
	MemberList  memberList = null;
	
	// 기본생성자
	public  MemberProc() {
		initComponent();
	}
	
	//인자있는 생성자
	public MemberProc(MemberList memberList) {
		this();  // 기본생성자 호출		
		//initComponent();
		this.memberList = memberList;
	}

	// 인자있는 생성자 : userid를 전달받는다
	public MemberProc( String id, MemberList memberList ) {
		this();
		this.memberList = memberList;
		
		// 넘어온 아이디를 txtId 에 넣고 find 버튼 클릭하면
		txtId.setText( id );
		btnFind.doClick();
	}

	private void initComponent() {
		setTitle("회원정보상세보기");
		
		//component 베치
		gb           =  new GridBagLayout();
		this.setLayout(gb);
		
		gbc          =  new GridBagConstraints();
		gbc.fill     =  GridBagConstraints.BOTH;
		gbc.weightx  =  1.0; 
		gbc.weighty  =  1.0;
		
		// 아이디
		JLabel  lblId = new JLabel("아이디");
		this.txtId    = new JTextField( 20 );
		gbAdd( lblId, 0, 0, 1, 1);
		gbAdd( txtId, 1, 0, 3, 1);
		
		// 암호
		JLabel  lblPwd = new JLabel("암호"); 
		txtPwd         = new JPasswordField(20);
		gbAdd( lblPwd, 0, 1, 1, 1);
		gbAdd( txtPwd, 1, 1, 3, 1);
				
		// 이름
		JLabel  lblName = new JLabel("이름"); 
		txtName         = new JTextField(20);
		gbAdd( lblName, 0, 2, 1, 1);
		gbAdd( txtName, 1, 2, 3, 1);
		
		// 직업
		String [] jobs = { "회사원", "학생", "군인", "없음"};
		JLabel  lblJob  = new JLabel("직업"); 
		cbJob           = new JComboBox( jobs  );
		gbAdd( lblJob, 0, 3, 1, 1);
		gbAdd( cbJob,  1, 3, 3, 1);
		
		// 성별		
		JLabel  lblGender  = new JLabel("성별"); 
		rbMan              = new JRadioButton("남자");
		rbWoman            = new JRadioButton("여자");
		// 라디오버튼그룹 등록 - 두 radiobutton 이 한그룹으로 묶어줌 - 그룹안에서 하나먄 선택가능
		group              = new ButtonGroup();
		group.add( rbMan );
		group.add( rbWoman );
				
		// 패널사용 - 화면배치
		JPanel   pGender  =  new JPanel( new FlowLayout( FlowLayout.LEFT  ) );
		pGender.add( rbMan );
		pGender.add( rbWoman );
		
		gbAdd( lblGender, 0, 4, 1, 1);
		gbAdd( pGender,   1, 4, 3, 1);
		
		// 자기소개
		JLabel       lblIntro  = new JLabel("자기소개"); 
		taIntro                = new JTextArea(5, 20);
		JScrollPane  pane      = new JScrollPane( taIntro );   
		gbAdd( lblIntro, 0, 5, 1, 1);
		gbAdd( pane,     1, 5, 3, 1);
		
		// 자기소개
		JLabel  lblIndate  = new JLabel("가입일"); 
		txtIndate          = new JTextField(20);
		//오늘날짜 출력
		String   today     = LocalDateTime.now().format(
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		txtIndate.setText( today );
		txtIndate.setForeground(Color.red);
		// txtIndate.setBackground(Color.LIGHT_GRAY);
		txtIndate.setEditable(false);
		gbAdd( lblIndate, 0, 6, 1, 1);
		gbAdd( txtIndate, 1, 6, 3, 1);
		
		// 버튼들
		JPanel   pButton  = new JPanel();
		btnAdd            = new JButton("추가");
		btnDelete         = new JButton("삭제");
		btnUpdate         = new JButton("수정");
		btnCancel         = new JButton("취소");
		btnCancel.setForeground( Color.red );
		btnFind           = new JButton("조회");
		
		//-----------------------------
		// 기능 추가 : event 연결
		// txtId 에 Enter 를 입력하면
				
		this.txtId.addKeyListener( new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// 출력(인쇄)할 수 있는 항목만 적용 : 문자적용
				// 보통 사용안함
				//System.out.println("keyTyped:" + e.getKeyCode());				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// 키가 눌려졌다가 올라올때 
			//	System.out.println("keyReleased:" + e.getKeyCode());
				// enter : 10 (KeyEvent.VK_ENTER)
				if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
					btnFind.doClick();  // btnFind 버튼을 클릭
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// 일반적인 키 입력상태: a, enter, esc
			//	System.out.println("keyPressed:" + e.getKeyCode());
				
			}
		});
				
		
		// btnAdd : 추가기능 - 추가버튼을 클릭하면
		btnAdd.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("추가버튼 클릭....");				
				addMember();
				clearViewData();
			}
			
		});
		
		// btnDelete :  삭제기능
		btnDelete.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("삭제버튼 클릭....");	
				removeMember();
			}
		});
		
		// btnUpdate : 수정기능
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("수정버튼 클릭....");	
				editMember();				
			}
		});
		
		// btnCancel : 취소기능
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("취소버튼 클릭....");	
				cancelMember();
				
			}
		});
		
		// btnFind : 조회기능
		btnFind.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("조회버튼 클릭....");	
				findMember();				
			}
		});
		
		//------------------------------
		
		// 버튼들을 panel 에 추가
		pButton.add( btnAdd );
		pButton.add( btnDelete );
		pButton.add( btnUpdate );
		pButton.add( btnCancel );
		pButton.add( btnFind );
		
		// pButton 패널을 화면에 추가
		gbAdd( pButton, 0, 7, 4, 1);
		
		//----------------------------------
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 600);
		setLocation(800, 200);
		setVisible(true);
		
	}
	

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx        = x;
		gbc.gridy        = y;
		gbc.gridwidth    = w;
		gbc.gridheight   = h;
		gb.setConstraints(c, gbc);
		gbc.insets       = new Insets(2, 2, 2, 2);
		this.add( c, gbc );
	}
	
	//------------------------------------------------------------------
	// 메소드들
	// 회원가입
	protected void addMember() {
		MemberDao   mDao    =  new MemberDao();
		MemberVo    vo      =  getViewData();
		int         aftcnt  =  mDao.insertMember( vo );
		
		JOptionPane.showMessageDialog(null, 
			aftcnt + "건 저장되었습니다",
			"추가",
			JOptionPane.OK_OPTION);
		
		// MemberList 에 있는 jTableRefresh() 함수를 호출한다
		memberList.jTableRefresh();
		
	    // 현재 창닫기
		this.dispose();
	}
	
	// 회원삭제
	protected void removeMember() {
		
		String      userid  =  this.txtId.getText();		
		if( userid.trim().equals("") ) 
			return;
		
		MemberDao   mDao    =  new MemberDao();
		// 삭제 확인		
		int     choice  =  JOptionPane.showConfirmDialog(null, 
					userid + "를 삭제하시겠습니까?",
					"삭제확인",
					JOptionPane.OK_CANCEL_OPTION);
		String  msg     = "";   
		if(choice == 0) {   // Ok 클릭
			int  aftcnt  =  mDao.deleteMember( userid );
			if( aftcnt > 0 ) {
				msg  = aftcnt + "건 삭제되었습니다";
			} else {
				msg  = "삭제 되지 않았습니다";
			}      			
		} else {
			msg  = "취소를 클릭하였습니다";
		}
		JOptionPane.showMessageDialog(null, 
				msg,
				"삭제",
				JOptionPane.OK_OPTION);
		
		// MemberList 새로고침
		memberList.jTableRefresh();
		
		this.dispose();  // 창닫기
	}
	
	// 회원수정 
	protected void editMember() {
		
		String      userid  =  this.txtId.getText();
		MemberDao   mDao    =  new MemberDao();
		
		int choice  =   JOptionPane.showConfirmDialog(null, 
				userid + "를 수정하시겠습니까?",
				"수정확인",
				JOptionPane.OK_CANCEL_OPTION);
		int     aftcnt = 0 ;
		String  msg    = "";
		if( choice == 0) {
			MemberVo vo =  getViewData();
			aftcnt      =  mDao.updateMember( vo );
			if( aftcnt > 0 )
				msg     =  userid + "수정되었습니다";
			else
				msg     =  userid + "수정되지 않았습니다";
		} else {
			msg = "취소를 선택하였습니다";
		}
		JOptionPane.showMessageDialog(null, 
				msg,
				"수정",
				JOptionPane.OK_OPTION);		
		
		// MemberList 새로고침
		memberList.jTableRefresh();
		
		this.dispose();  // 창닫기
		
	}
	
	
	// 취소
	protected void cancelMember() {
		clearViewData();
	}
	
	

	// 회원조회 
	protected void findMember() {
		String      userid  =  this.txtId.getText();
		if( userid.trim().equals("") )
			return;
		
		MemberDao   mDao    =  new MemberDao();
		// 조회한 결과를 vo로 돌려받는다
		MemberVo    vo      =  mDao.getMember( userid );
		System.out.println( vo );
		// vo를 화면을 구성하는 component 에 출력
		setViewData( vo );
		
		//mDao.close();
	}
	
	// 화면 모든요소 초기화
	private void clearViewData() {
		
		this.txtId.setText( "" );
		this.txtPwd.setText( "" );
		this.txtName.setText( "" );
		this.cbJob.setSelectedIndex( 0 );
		this.rbMan.setSelected(false);
		this.rbWoman.setSelected(false);
		this.taIntro.setText( "" );
		this.txtIndate.setText( "" );
		
		this.txtId.grabFocus();  // setFocus(), focus() 
	}
	
	// vo 변수 -> view 입력항목(component) 에 저장
	private void setViewData(MemberVo vo) {
		String   userid    =  vo.getUserid();
		String   passwd    =  vo.getPasswd();
		String   username  =  vo.getUsername();
		String   job       =  vo.getJob();     // "회사원", ...
		String   gender    =  vo.getGender();  // "남", "여", ""  
		String   intro     =  vo.getIntro();
		String   indate    =  vo.getIndate();
		
		this.txtId.setText( userid );
		this.txtPwd.setText( passwd );
		this.txtName.setText( username );
		this.cbJob.setSelectedItem( job );		
		switch( gender ) {
		case "남":
			this.rbMan.setSelected(true);
			break;
		case "여":
			this.rbWoman.setSelected(true);
			break;
		}
		this.taIntro.setText(intro);
		this.txtIndate.setText(indate);
				
	}

	// @ : annotaion : compiler 에게 알려주는 정보 - 다음에만 적용된다
	// view 입력항목 -> Vo 변수에 담아준다
	private MemberVo getViewData() {
		String   userid    =  this.txtId.getText(); 
		
		@SuppressWarnings("deprecation")
		String   passwd    =  this.txtPwd.getText();  // 줄표시 : 옛날 명령
		
		//String   passwd    =  this.txtPwd.getPassword().toString(); // char [] -> String
		String   username  =  this.txtName.getText();
		String   job       =  (String) this.cbJob.getSelectedItem();
		String   gender    =  "";
		if( this.rbMan.isSelected() )    gender = "남";
		if( this.rbWoman.isSelected() )  gender = "여";
		String   intro     =  this.taIntro.getText();
		String   indate    =  this.txtIndate.getText();
		
		MemberVo vo        =  new MemberVo(
				userid, passwd, username, job, gender, intro, indate);
		return   vo;
	}

//	public static void main(String[] args) {
//		new MemberProc();
//	}

}






