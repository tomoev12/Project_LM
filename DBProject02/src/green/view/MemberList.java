package green.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import green.model.MemberDao;

public class MemberList  
	extends     JFrame 
	implements  ActionListener, MouseListener  {

	// Fields 
	JButton       btnInsert,  btnRefresh, btnToExcel;
	JPanel        topPane;
	JTable        jTable;
	JScrollPane   pane; 
	
	MemberProc           mProc = null;  // 회원가입창 전역변수
	static MemberList    mList = null;  // 자신의 정보를 담을 변수(현재 memberList )
	
	public  MemberList() {
		initComponent();
	}
	
	// 부품(component) 배치, 배치방법(layout), 기능연결(addaction event 지정)
	private  void initComponent() {
		setTitle("회원관리 프로그램 v1.0");
		
		topPane      =  new JPanel();
		btnInsert    =  new JButton("회원가입");  
		btnRefresh   =  new JButton("새로고침");  
		btnToExcel   =  new JButton("엑셀로 저장");

		topPane.add( btnInsert );
		topPane.add( btnRefresh );
		topPane.add( btnToExcel );
		
		// 버튼에 기능 부여
		// 회원가입버튼 클릭
		btnInsert.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입창(MemberProc)을 연다
				System.out.println("회원가입클릭");
				if(  mProc != null )
					mProc.dispose();  // 강제로 닫는다
				System.out.println("this:" + this);
				System.out.println("mList:" + mList);
				//mProc = new MemberProc( this );   // this : 현재 실행중인 MemberList
				mProc = new MemberProc( mList );   // this : 현재 실행중인 MemberList 				
			}
		});
	//	btnInsert.addActionListener( this );
		
		   // 이벤트핸들러(이벤트발생시 수행할 함수 - actionPreformed() )를 등록
		// 새로고침버튼 클릭
		btnRefresh.addActionListener( this );
		// 엑셀로 저장 버튼 클릭
		btnToExcel.addActionListener( this );
		
		this.add(topPane, BorderLayout.NORTH);
				
		// -----------------------		
		jTable      =   new  JTable();		
		// data 를 model 에 담아서 채움
		jTable.setModel(
			new DefaultTableModel( getDataList() , getColumnList() ) {				
				// 기본 option 설정 - 각 cell 에 대한 편집가능여부 :isCellEditable
				@Override
				public boolean isCellEditable(int row, int column) {
				//	int  currLine = jTable.getSelectedRow();  // 선택한 줄만 수정가능
				//	if( row == currLine  )
				//		return true;			
					return false;   // 모든 cell 편집불가능
				}				
			}	
		);
		
		//jTable 의 Row 이 더블클릭(마우스 동작연결)되면
		jTable.addMouseListener( this );
		
		
		pane  = new JScrollPane( jTable );
		this.add( pane );
				
		//----------------------------------------------		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 500);
		setLocation(200, 200);
		setVisible(true);
		
	}
	
	// data 목록
	//  <? extends Vector> : Vector 를 상속받은 Type 만 가능하다
	// Vector<? extends Vector> : Vector안에 Vector 타입만 저장가능 
	//  - 2차원배열 : ResultSet을 받기위해 
	//  Vector< Vector > : 안쪽   Vector : ResultSet 의 한 Row - Record
	//                   : 바깥쪽 Vector : ResultSet 전체  Table - Record 배열
	private Vector<Vector> getDataList() {
		MemberDao       dao   =  new MemberDao();
		Vector<Vector>  list  =  dao.getMemberList();
		return  list;
	}

	// jTable 에 제목줄 생성 - 크기변경가능한 배열 : ArrayList -> Vector(swing)
	// ? : class Type 
	private Vector<String> getColumnList() {
		Vector<String>  cols = new Vector<>();  // 문자배열 대신 사용
		cols.add("아이디");
		cols.add("이름");
		cols.add("직업");
		cols.add("성별");
		cols.add("가입일");
		return  cols;
	}

	public static void main(String[] args) {
		mList = new MemberList();
	}

	//---------------------------------------------------
	
	// implements ActionListener 를 위한 구현
	// 버튼들의 이벤트 처리
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch( e.getActionCommand() ) {  // 눌러진 버튼의 글자
		case "회원가입":
			// 회원가입창(MemberProc)을 연다
			System.out.println("회원가입클릭");
			if(  mProc != null )
				mProc.dispose();  // 강제로 닫는다
			mProc = new MemberProc( this );   // this : 현재 실행중인 MemberList 
			break;
		case "새로고침":
			// 새로고침 클릭
			System.out.println("새로고침 클릭");
			jTableRefresh();			
			break;
		case "엑셀로 저장":
			System.out.println("엑셀로 저장....");
			LocalDateTime  now   =  LocalDateTime.now(); 
			int            year  =  now.getYear();
			int            mm    =  now.getMonthValue();
			int            dd    =  now.getDayOfMonth();
			int            hh    =  now.getHour();
			int            mi    =  now.getMinute();
			int            ss    =  now.getSecond();
			
			String  fmt      = "d:\\ws\\java\\DBProject02\\src\\";
			fmt             += "jTable_%4d%02d%02d%02d%02d%02d.xlsx";
			String  filepath = String.format(fmt, year, mm, dd, hh, mi, ss );
			
			System.out.println( filepath );
		    excelWrite( filepath );
			
			break;
		}
			
	}

	// jTable 새로고침 - data를 변경
	public void jTableRefresh() {
		
		jTable.setModel(
			new DefaultTableModel( getDataList(),  getColumnList()  ) {

				@Override
				public boolean isCellEditable(int row, int column) {					
					return false;
				}
				
			}
		);  // jtable 새로운 데이터를 지정
		
		jTable.repaint();  // jtable을 새로 그린다
	}

	//----------------------------------------------------------
	// MouseListener 관련 함수들
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// 마우스를 클릭하면
		// button=1 : 왼쪽, button=2 : 가운데, button=3 : 오른쪽
		int     row = jTable.getSelectedRow();
		// int     col = jTable.getSelectedColumn();
		String  id  = (String) jTable.getValueAt(row, 0); 
		System.out.println( e );	
		if ( mProc != null)
			mProc.dispose();
		mProc = new MemberProc( id, this );
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 마우스버튼 누르고 있는 동안
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 마우스버튼이 눌러졌다가 놓는 순간
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 마우스 커서가 특정공간안으로 들어갈때(진입)
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 마우스 커서가 특정공간에서 밖으로 나갈때(이탈)
		
	}
	
	//-----------------------------------------------------
	// excel 로 저장
	// Workbook -> .xlsx
	// -> Sheet 
	//  -> Row
	//   -> Cell
	private void excelWrite(String filepath) {
		XSSFWorkbook  workbook =  new XSSFWorkbook();
		XSSFSheet     sheet    =  workbook.createSheet("Data");
		
		// data 저장 : swing jTable -> Excel Sheet
		getWorkbook_Data( sheet );
		
		// 파일 저장
		FileOutputStream  fos = null;
		try {
			fos = new FileOutputStream( filepath );
			workbook.write(fos);
			System.out.println("저장완료");
		} catch (IOException e) {
			System.out.println("저장Fail");			
			e.printStackTrace();
		} finally {
			try {
				if(fos != null)fos.close();
			} catch (IOException e) {
			}
		}
		
	}

	private void getWorkbook_Data(XSSFSheet sheet) {
		XSSFRow     row   =  null;
		XSSFCell    cell  =  null;
		
		// 제목줄 처리
		Vector<String>  cols =  getColumnList();
		row          =  sheet.createRow( 0 );
		for (int i = 0; i < cols.size(); i++) {
			cell     =  row.createCell(i);
			cell.setCellValue(  cols.get(i) );    
		}
				
		// Data 줄 처리
		Vector< Vector >  dataList = getDataList();
		for (int i = 0; i < dataList.size(); i++) {
			row    =   sheet.createRow(i + 1);
			for (int j = 0; j < dataList.get(i).size() ; j++) {
				Vector  v  =  dataList.get(i);
				cell       =  row.createCell( j );
				cell.setCellValue( (String) v.get( j ) );
			}
			
		}
		
		
	}

}








