package devinfosys.dao;

import java.util.ArrayList;
import java.util.List;

import devinfosys.model.ProjectType;

public class ProjectTypeExampleDAO implements IProjectTypeDAO {
	@Override
	public List<ProjectType> getProjectTypes() {
		List<ProjectType> types = new ArrayList<ProjectType>();
		types.add(new ProjectType("Web Application"));
		types.add(new ProjectType("Desktop"));
		types.add(new ProjectType("Console"));
		return types;
	}
}
