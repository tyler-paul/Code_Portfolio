package devinfosys.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import devinfosys.model.Developer;
import devinfosys.model.DeveloperProjectAssignment;
import devinfosys.model.Project;

public class DeveloperProjectAssignmentExampleDAO implements IDeveloperProjectAssignmentDAO {
	private Map<String, Set<Developer>> assignments;
	private IDeveloperDAO developerDAO;
	private IProjectDAO projectDAO;
	
	
	public DeveloperProjectAssignmentExampleDAO() {
		assignments = new HashMap<String, Set<Developer>>();
	}
	
	@Override
	public boolean create(DeveloperProjectAssignment developerProjectAssignment) {
		
		if (developerProjectAssignment == null)
			return false;
		String developerName = developerProjectAssignment.getDeveloperName();
		String projectName = developerProjectAssignment.getProjectName();
		
		Developer developer = developerDAO.getDeveloper(developerName);
		Project project = projectDAO.getProject(projectName);
		
		if (developer == null || project == null)
			return false;
			
		Set<Developer> set;
		if (!assignments.containsKey(projectName)) {
			set = new HashSet<Developer>();
		}
		else {
			set = assignments.get(projectName);
		}
		
		set.add(developer);
		assignments.put(projectName, set);
		
		
		System.out.println("Created assignment for developer:" + developerProjectAssignment.getDeveloperName()
				+ " to project:" + developerProjectAssignment.getProjectName());
		return true;
	}

	@Override
	public boolean delete(DeveloperProjectAssignment developerProjectAssignment) {
		if (developerProjectAssignment == null)
			return false;
		String developerName = developerProjectAssignment.getDeveloperName();
		String projectName = developerProjectAssignment.getProjectName();
		
		Developer developer = developerDAO.getDeveloper(developerName);
		Project project = projectDAO.getProject(projectName);
		
		if (developer == null || project == null)
			return false;
		
		
		Set<Developer> set = assignments.get(projectName);
		
		if (set == null || !set.contains(developer))
			return false;
		
		set.remove(developer);
		assignments.put(projectName, set);
		
		System.out.println("Deleted assignment for developer:" + developerProjectAssignment.getDeveloperName()
			+ " to project:" + developerProjectAssignment.getProjectName());
		
		return true;
	}

	@Override
	public Set<Developer> getDevelopersInAProject(String projectName) {
		return assignments.get(projectName);
	}

	public Map<String, Set<Developer>> getAssignments() {
		return assignments;
	}

	public void setAssignments(Map<String, Set<Developer>> assignments) {
		this.assignments = assignments;
	}

	public IDeveloperDAO getDeveloperDAO() {
		return developerDAO;
	}

	public void setDeveloperDAO(IDeveloperDAO developerDAO) {
		this.developerDAO = developerDAO;
	}

	public IProjectDAO getProjectDAO() {
		return projectDAO;
	}
	public void setProjectDAO(IProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

}
