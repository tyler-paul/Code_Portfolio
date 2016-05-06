package devinfosys.controller.project;

import devinfosys.console.IConsole;
import devinfosys.dao.IProjectDAO;
import devinfosys.model.Project;
import devinfosys.view.Startable;

public class ProjectUpdaterController implements Startable {
	private IConsole console;
	private IProjectDAO projectDAO;

	public void start() {
		console.write("Type the name of the project to update and push enter:");
		String projectName = console.read();
		Project project = projectDAO.getProject(projectName);
		
		console.write("Type the new name of the project and push enter:");
		String newProjectName = console.read();
		
		console.write("Type the new description of the project and push enter:");
		String newDescription = console.read();
		
		if (project != null) {
			project.getProjectInfo().setName(newProjectName);
			project.getProjectInfo().setDescription(newDescription);
		}
		
		boolean success = projectDAO.update(project);
		if (success)
			console.write("Updated project with name:" + projectName + " to name:" + newProjectName);
		else
			console.write("Unable to update project");
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
}
