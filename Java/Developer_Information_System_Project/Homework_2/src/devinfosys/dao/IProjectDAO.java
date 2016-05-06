package devinfosys.dao;

import java.util.List;

import devinfosys.model.Project;
import devinfosys.model.ProjectInfo;


public interface IProjectDAO {
	public abstract boolean create(ProjectInfo projectInfo);
	public abstract boolean update(Project project);
	public abstract boolean delete(Project project);
	public abstract List<Project> getAll();
	public abstract Project getProject(String projectName);
}
