package devinfosys.model;

public class ProjectInfo {
	private String name;
	private String description;
	private ProjectType projectType;
	
	
	public ProjectInfo(String name, String description, ProjectType projectType) {
		super();
		this.name = name;
		this.description = description;
		this.projectType = projectType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProjectType getProjectType() {
		return projectType;
	}
	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}
	
	
}
