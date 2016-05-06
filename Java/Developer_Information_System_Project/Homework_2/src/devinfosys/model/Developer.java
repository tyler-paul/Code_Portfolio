package devinfosys.model;

public class Developer {
	private int id;
	private DeveloperInfo developerInfo;
	
	public Developer(int id, DeveloperInfo developerInfo) {
		this.id = id;
		this.developerInfo = developerInfo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DeveloperInfo getDeveloperInfo() {
		return developerInfo;
	}
	public void setDeveloperInfo(DeveloperInfo developerInfo) {
		this.developerInfo = developerInfo;
	}
	
	public String toString() {
		return "Developer id:" + id + ", name:" + developerInfo.getName() + ", password:" 
				+ developerInfo.getPassword() 
				+ ", type:" + developerInfo.getDeveloperType().getTypeName();
	}
	
}
