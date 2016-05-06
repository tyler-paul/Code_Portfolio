package devinfosys.controller.project;

import java.util.List;

import devinfosys.console.IConsole;
import devinfosys.dao.IProjectDAO;
import devinfosys.model.Project;
import devinfosys.view.Startable;

public class ProjectViewerController implements Startable {
	private IConsole console;
	private IProjectDAO projectDAO;

	public void start() {
		List<Project> projects = projectDAO.getAll();
		for (Project project : projects)
			console.write(project.toString());
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
