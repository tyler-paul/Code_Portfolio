package devinfosys.model;

public class DeveloperInfo {
	private String name;
	private String password;
	private DeveloperType developerType;
	
	public DeveloperInfo(String name, String password, DeveloperType developerType) {
		super();
		this.name = name;
		this.password = password;
		this.developerType = developerType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DeveloperType getDeveloperType() {
		return developerType;
	}
	public void setDeveloperType(DeveloperType developerType) {
		this.developerType = developerType;
	}
	
	
}
