package devinfosys.controller.project;

import devinfosys.console.IConsole;
import devinfosys.dao.IProjectDAO;
import devinfosys.model.Project;
import devinfosys.view.Startable;

public class ProjectDeleterController implements Startable {
	private IConsole console;
	private IProjectDAO projectDAO;

	public void start() {
		console.write("Type the name of the project to delete and push enter:");
		String projectName = console.read();
		
		Project project = projectDAO.getProject(projectName);
		boolean success = projectDAO.delete(project);
		
		if (success)
			console.write("Deleted project with name:" + projectName);
		else
			console.write("Unable to delete project");
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
