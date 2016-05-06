package devinfosys.controller.project;

import java.util.List;

import devinfosys.console.IConsole;
import devinfosys.dao.IProjectDAO;
import devinfosys.dao.IProjectTypeDAO;
import devinfosys.model.ProjectInfo;
import devinfosys.model.ProjectType;
import devinfosys.view.Startable;

public class ProjectCreatorController implements Startable {
	private IConsole console;
	private IProjectDAO projectDAO;
	private IProjectTypeDAO projectTypeDAO;
	
	public void start() {
		boolean properInput = false;
		List<ProjectType> projectTypes;
		int selection = -1;
		
		do {
			console.write("Type the number corresponding to the desired project type and push enter:");
			projectTypes = projectTypeDAO.getProjectTypes();
			for (int i = 0; i < projectTypes.size(); i++) {
				console.write((i + 1) + ". " + projectTypes.get(i).getTypeName());
			}
			try {
				selection = Integer.parseInt(console.read()) - 1;
				properInput = true;
			}
			catch (NumberFormatException e) {
				properInput = false;
			}

			if (selection < 0 || selection >= projectTypes.size()) {
				properInput = false;
			}
			
			if (!properInput)
				console.write("Improper input");
		}
		while (!properInput);
		
		
		ProjectType projectType = projectTypes.get(selection);
		
		console.write("Type in the project name and push enter:");
		String projectName = console.read();
		
		console.write("Type in the project description and push enter:");
		String password = console.read();
		
		boolean success = projectDAO.create(new ProjectInfo(projectName, password, projectType));
		
		if (success)
			console.write("Created project with name:" + projectName + " and type:" + projectType.getTypeName());
		else
			console.write("Unable to create project");
	}
	
	public IConsole getConsole() {
		return console;
	}
	public void setConsole(IConsole console) {
		this.console = console;
	}
	public IProjectDAO getProjectDAO() {
		return projectDAO;
	}
	public void setProjectDAO(IProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}
	public IProjectTypeDAO getProjectTypeDAO() {
		return projectTypeDAO;
	}
	public void setProjectTypeDAO(IProjectTypeDAO projectTypeDAO) {
		this.projectTypeDAO = projectTypeDAO;
	}
	
	
}
