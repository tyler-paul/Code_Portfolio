package devinfosys.controller.developer;


import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperDAO;
import devinfosys.model.Developer;
import devinfosys.view.Startable;

public class DeveloperDeleterController implements Startable {
	private IConsole console;
	private IDeveloperDAO developerDAO;
	
	@Override
	public void start() {
		console.write("Type the name of the developer to delete and push enter:");
		String developerName = console.read();
		
		Developer developer = developerDAO.getDeveloper(developerName);
		boolean success = developerDAO.delete(developer);
	
		if (success)
			console.write("Deleted developer with name:" + developerName);
		else
			console.write("Unable to delete developer");
				
	}

	public IConsole getConsole() {
		return console;
	}

	public void setConsole(IConsole console) {
		this.console = console;
	}

	public IDeveloperDAO getDeveloperDAO() {
		return developerDAO;
	}

	public void setDeveloperDAO(IDeveloperDAO developerDAO) {
		this.developerDAO = developerDAO;
	}
	
}
