package green.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MemberDao {

	private Connection  conn = null;
	
	// 생성자
	public  MemberDao() {
		conn  =  DBConn.getInstance(); 
	}
	public  void close() {
		try {
			if(conn != null)  conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원 추가
	// "admin", "1234", "관리자", "회사원", "남", "관리자에용"
	// userid,  passwd,   username,  job, gender, intro, indate
	public int insertMember( String userid, String passwd, String username, 
			String job, String gender, String  intro ) {
		String            sql    = "";
		sql  += "INSERT INTO MEMBER ";
		sql  += "  ( USERID,  PASSWD, USERNAME, JOB, GENDER, INTRO ) ";
		sql  += " VALUES ";
		sql  += "  ( ?,       ?,      ?,        ?,   ?,      ?     ) ";		
		PreparedStatement pstmt  = null;
		int               aftcnt = 0;
		try {
			pstmt  = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, passwd);
			pstmt.setString(3, username);
			pstmt.setString(4, job);
			pstmt.setString(5, gender);
			pstmt.setString(6, intro);
			
			aftcnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return  aftcnt;
	}
	public int insertMember(MemberVo vo) {
		
		String   userid   = vo.getUserid();
		String   passwd   = vo.getPasswd();
		String   username = vo.getUsername();
		String   job      = vo.getJob();
		String   gender   = vo.getGender();
		String   intro    = vo.getIntro();
		
		int aftcnt = insertMember(userid, passwd, username, job, gender, intro);
		return aftcnt;
		
	}
	
	// data 삭제
	public int deleteMember(String userid) {
		
		String  sql = "";
		sql += "DELETE FROM  MEMBER";
		sql += " WHERE USERID = ? ";
		
		PreparedStatement  pstmt  = null ;
		int                aftcnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			aftcnt = pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)  pstmt.close();
			} catch (SQLException e) {
			}
		}
		return aftcnt;
	}
	
	// 회원조회
	public MemberVo getMember(String userid) {
		
		MemberVo  vo = null;
			
		String  sql = "";
		sql += "SELECT USERID, PASSWD, USERNAME, JOB, GENDER, INTRO, INDATE ";
		sql += " FROM  MEMBER ";
		sql += " WHERE USERID = ? ";
		PreparedStatement pstmt  =  null;
		ResultSet         rs     =  null;
		try {
			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs    =  pstmt.executeQuery();
			
			if( rs.next() ) {
				String  ouserid   = rs.getString("userid");
				String  passwd    = rs.getString("passwd");
				String  username  = rs.getString("username");
				String  job       = rs.getString("job");
				String  gender    = rs.getString("gender");
				String  intro     = rs.getString("intro");
				String  indate    = rs.getString("indate");
				
				vo   = new MemberVo(ouserid, passwd, username, job, gender, intro, indate);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs    != null)   rs.close();
				if(pstmt != null)   pstmt.close();
			} catch (SQLException e) {		
			}
		}		
				
		return    vo;
	}
	
	// 데이터 수정
	public int updateMember(MemberVo vo) {
		String  sql = "";
		sql  += "UPDATE  MEMBER ";
		sql  += " SET    USERNAME = ?, ";    // 수정항목 , 이름, 암호, 직업, 성별, 자기소개
		sql  += "        PASSWD   = ?, ";
		sql  += "        JOB      = ?, ";
		sql  += "        GENDER   = ?, ";
		sql  += "        INTRO    = ?  ";
		sql  += " WHERE  USERID   = ?";
		
		int  aftcnt  = 0;
		PreparedStatement  pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUsername() );
			pstmt.setString(2, vo.getPasswd() );
			pstmt.setString(3, vo.getJob() );
			pstmt.setString(4, vo.getGender() );
			pstmt.setString(5, vo.getIntro() );
			pstmt.setString(6, vo.getUserid() );
			
			aftcnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null ) pstmt.close();
			} catch (SQLException e) {			
			}
		}		
		return aftcnt;
	}
	
	// Jtable 에 보여줄 data 목록
	public Vector<Vector> getMemberList() {
		
		Vector<Vector>  list = new Vector<Vector>();   // 조회된 결과전체 대응 : rs
		
		String  sql = "";
		sql  +=  "SELECT  USERID, USERNAME, JOB, GENDER, ";
		sql  +=  " TO_CHAR(INDATE, 'YYYY-MM-DD HH24:MI') INDATE";
		sql  +=  " FROM   MEMBER ";
		sql  +=  " ORDER  BY USERID ASC ";
		
		PreparedStatement  pstmt = null;
		ResultSet          rs    = null;
		try {
			pstmt = conn.prepareStatement(sql);
			
			rs    = pstmt.executeQuery();
			while( rs.next() ) {			
				String  userid    =  rs.getString("userid");
			//	String  userid    =  rs.getString(1);           // 1: 칼럼번호(1~)
				String  username  =  rs.getString("username");  // 2
				String  job       =  rs.getString("job");       // 3
				String  gender    =  rs.getString("gender");    // 4
			//	String  indate    =  rs.getString("INDATE");    // 5
				String  indate    =  rs.getString(5);           // 5
				
				Vector  v         = new Vector();  // 안쪽 Vector : 한 줄 Row 를 의미
				v.add( userid );
				v.add( username );
				v.add( job );
				v.add( gender );
				v.add( indate );
				
				list.add( v );  //  전체 목록에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( rs    != null )  rs.close();
				if( pstmt != null )  pstmt.close();
			} catch (SQLException e) {
			}
		}
			
		return  list;
		
	}
	
}










