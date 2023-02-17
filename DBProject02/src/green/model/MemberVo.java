package green.model;

public class MemberVo {
	// Fields
	private String   userid;
	private String   passwd;
	private String   username;
	private String   job;
	private String   gender;
	private String   intro;
	private String   indate;
	
	// Constructor
	public MemberVo() {}
	public MemberVo(String userid, String passwd, String username, String job, String gender, String intro,
			String indate) {
		this.userid = userid;
		this.passwd = passwd;
		this.username = username;
		this.job = job;
		this.gender = gender;
		this.intro = intro;
		this.indate = indate;
	}
	
	// Getter / Setter
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	
	//toString
	@Override
	public String toString() {
		return "MemberVo [userid=" + userid + ", passwd=" + passwd + ", username=" + username + ", job=" + job
				+ ", gender=" + gender + ", intro=" + intro + ", indate=" + indate + "]";
	}
	
}
