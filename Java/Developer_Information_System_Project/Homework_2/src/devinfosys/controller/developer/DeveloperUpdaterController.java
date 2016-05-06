package devinfosys.controller.developer;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperDAO;
import devinfosys.model.Developer;
import devinfosys.view.Startable;

public class DeveloperUpdaterController implements Startable {
	private IConsole console;
	private IDeveloperDAO developerDAO;
	
	public void start() {
		console.write("Type the name of the developer to update and push enter:");
		String developerName = console.read();
		Developer developer = developerDAO.getDeveloper(developerName);
		
		console.write("Type the new name of the developer and push enter:");
		String newDeveloperName = console.read();
		
		console.write("Type the new password of the developer and push enter:");
		String newPassword = console.read();
		
		if (developer != null) {
			developer.getDeveloperInfo().setName(newDeveloperName);
			developer.getDeveloperInfo().setPassword(newPassword);
		}
		
		boolean success = developerDAO.update(developer);
		if (success)
			console.write("Updated developer with name:" + developerName + " to name:" + newDeveloperName);
		else
			console.write("Unable to update developer");
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
