package devinfosys.controller.assignment;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperProjectAssignmentDAO;
import devinfosys.model.DeveloperProjectAssignment;
import devinfosys.view.Startable;

public class AssignmentDeleterController implements Startable {
	private IConsole console;
	private IDeveloperProjectAssignmentDAO developerProjectAssignmentDAO;
	
	public void start() {
		console.write("Type the name of the developer to remove a project from:");
		String developerName = console.read();
		
		console.write("Type in the name of the project to remove the developer from:");
		String projectName = console.read();
		
		DeveloperProjectAssignment assignment = new DeveloperProjectAssignment(developerName, projectName);
		boolean success = developerProjectAssignmentDAO.delete(assignment);
		
		if (success)
			console.write("Deleted assignment of " + developerName + " to " + projectName);
		else
			console.write("Unable to delete assignment");
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
