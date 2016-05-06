package devinfosys.dao;

import java.util.List;
import java.util.Set;

import devinfosys.model.Developer;
import devinfosys.model.DeveloperProjectAssignment;

public interface IDeveloperProjectAssignmentDAO {
	public abstract boolean create(DeveloperProjectAssignment developerProjectAssignment);
	public abstract boolean delete(DeveloperProjectAssignment developerProjectAssignment);
	public abstract Set<Developer> getDevelopersInAProject(String projectName);
}
