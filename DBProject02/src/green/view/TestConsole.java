package green.view;

import green.model.MemberDao;
import green.model.MemberVo;

public class TestConsole {

	public static void main(String[] args) {
		
		MemberDao   mDao   =  new MemberDao(); 
		/*
		// 회원추가
		int aftcnt  = mDao.insertMember("sky", "1234", "스카이", "군인", "남", "회원입니다");
		if(aftcnt == 0)
			System.out.println("추가되지 않았습니다");
		else
			System.out.println("추가되었습니다");
		*/	
		
		MemberVo  vo = mDao.getMember("sky");
		System.out.println( vo.toString() );
		
	}

}











