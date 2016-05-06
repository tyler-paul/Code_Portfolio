package devinfosys.controller.assignment;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperProjectAssignmentDAO;
import devinfosys.model.DeveloperProjectAssignment;
import devinfosys.view.Startable;

public class AssignmentCreatorController implements Startable {
	private IConsole console;
	private IDeveloperProjectAssignmentDAO developerProjectAssignmentDAO;
	
	public void start() {
		console.write("Type the name of the developer to assign a project to:");
		String developerName = console.read();
		
		console.write("Type in the name of the project to assign the developer to:");
		String projectName = console.read();
		
		DeveloperProjectAssignment assignment = new DeveloperProjectAssignment(developerName, projectName);
		boolean success = developerProjectAssignmentDAO.create(assignment);
		
		if (success)
			console.write("Created assignment of " + developerName + " to " + projectName);
		else
			console.write("Unable to create assignment");
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
