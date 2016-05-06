package devinfosys.dao;

import java.util.ArrayList;
import java.util.List;

import devinfosys.model.Developer;
import devinfosys.model.Project;
import devinfosys.model.ProjectInfo;
import devinfosys.model.ProjectType;

public class ProjectExampleDAO implements IProjectDAO {

	private List<Project> projects;
	private static int nextId;
	
	public ProjectExampleDAO() {
		projects = new ArrayList<Project>();
		nextId = 0;
	}
	
	
	@Override
	public boolean create(ProjectInfo projectInfo) {
		//check if there is already a project with the given name
		for (Project project : projects) {
			if (project.getProjectInfo().getName().equals(projectInfo.getName()))
				return false;
		}
		
		projects.add(new Project(nextId++, projectInfo));
		
		return true;
	}

	@Override
	public boolean update(Project project) {
		
		if (project == null) {
			return false;
		}
		
		boolean updated = false;
		for (int i = 0; i < projects.size(); i++) {
			Project aProject = projects.get(i);
			if (aProject.getId() == project.getId()) {
				aProject.getProjectInfo().setName(project.getProjectInfo().getName());
				aProject.getProjectInfo().setDescription(project.getProjectInfo().getDescription());
				updated = true;
				break;
			}
		}
		
		return updated;
	}

	@Override
	public boolean delete(Project project) {
		if (project == null) {
			return false;
		}
		
		boolean deleted = false;
		for (int i = 0; i < projects.size(); i++) {
			Project aProject = projects.get(i);
			if (aProject.getId() == project.getId()) {
				projects.remove(i);
				deleted = true;
				break;
			}
		}

		return deleted;
	}

	@Override
	public List<Project> getAll() {
		return projects;
	}

	@Override
	public Project getProject(String projectName) {
		for (Project project : projects) {
			if (project.getProjectInfo().getName().equals(projectName))
				return project;
		}
		return null;
	}

}
