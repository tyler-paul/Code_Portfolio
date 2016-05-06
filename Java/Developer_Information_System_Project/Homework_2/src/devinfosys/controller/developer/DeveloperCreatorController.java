package devinfosys.controller.developer;

import java.util.List;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperDAO;
import devinfosys.dao.IDeveloperTypeDAO;
import devinfosys.model.DeveloperInfo;
import devinfosys.model.DeveloperType;
import devinfosys.view.Startable;

public class DeveloperCreatorController implements Startable {
	private IConsole console;
	private IDeveloperDAO developerDAO;
	private IDeveloperTypeDAO developerTypeDAO;
	
	
	@Override
	public void start() {
		boolean properInput = false;
		int selection = -1;
		List<DeveloperType> developerTypes;
		do {
			console.write("Type the number corresponding to the desired developer type and push enter:");
			developerTypes = developerTypeDAO.getDeveloperTypes();
			for (int i = 0; i < developerTypes.size(); i++) {
				console.write((i + 1) + ". " + developerTypes.get(i).getTypeName());
			}
			
			try {
				selection = Integer.parseInt(console.read()) - 1;
				properInput = true;
			}
			catch (NumberFormatException e) {
				properInput = false;
			}
			if (selection < 0 || selection >= developerTypes.size()) {
				properInput = false;
			}
			
			if (!properInput)
				console.write("Improper input");
		}
		while (!properInput);
			
		DeveloperType developerType = developerTypes.get(selection);
		
		console.write("Type in the developer name and push enter:");
		String developerName = console.read();
		
		console.write("Type in the developer password and push enter:");
		String password = console.read();
		
		boolean success = developerDAO.create(new DeveloperInfo(developerName, password, developerType));
		
		if (success)
			console.write("Created developer with name:" + developerName + " and type:" + developerType.getTypeName());
		else
			console.write("Unable to create developer");
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

	public IDeveloperTypeDAO getDeveloperTypeDAO() {
		return developerTypeDAO;
	}

	public void setDeveloperTypeDAO(IDeveloperTypeDAO developerTypeDAO) {
		this.developerTypeDAO = developerTypeDAO;
	}
	
}
