package devinfosys.controller.assignment;

import java.util.Set;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperProjectAssignmentDAO;
import devinfosys.model.Developer;
import devinfosys.view.Startable;

public class AssignmentViewerController implements Startable {
	private IConsole console;
	private IDeveloperProjectAssignmentDAO developerProjectAssignmentDAO;
	
	public void start() {
		console.write("Type the name of the project to view developers in:");
		String projectName = console.read();
		Set<Developer> developers = developerProjectAssignmentDAO.getDevelopersInAProject(projectName);
		if (developers != null)
			for (Developer developer : developers)
				console.write(developer.toString());
	}

	public IConsole getConsole() {
		return console;
	}

	public void setConsole(IConsole console) {
		this.console = console;
	}

	public IDeveloperProjectAssignmentDAO getDeveloperProjectAssignmentDAO() {
		return developerProjectAssignmentDAO;
	}

	public void setDeveloperProjectAssignmentDAO(IDeveloperProjectAssignmentDAO developerProjectAssignmentDAO) {
		this.developerProjectAssignmentDAO = developerProjectAssignmentDAO;
	}
}
