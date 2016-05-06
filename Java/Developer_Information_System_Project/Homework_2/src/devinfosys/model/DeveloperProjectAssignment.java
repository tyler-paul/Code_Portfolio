package devinfosys.model;

public class DeveloperProjectAssignment {
	private String developerName;
	private String projectName;
	
	public DeveloperProjectAssignment(String developerName, String projectName) {
		super();
		this.developerName = developerName;
		this.projectName = projectName;
	}
	
	public String getDeveloperName() {
		return developerName;
	}
	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
}
