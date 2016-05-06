package devinfosys.model;

public class Project {
	private int id;
	private ProjectInfo projectInfo;
	public Project(int id, ProjectInfo projectInfo) {
		super();
		this.id = id;
		this.projectInfo = projectInfo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	
	public String toString() {
		return "Project id:" + id + ", name:" + projectInfo.getName() + ", description:" 
				+ projectInfo.getDescription() 
				+ ", type:" + projectInfo.getProjectType().getTypeName();
	}
}
