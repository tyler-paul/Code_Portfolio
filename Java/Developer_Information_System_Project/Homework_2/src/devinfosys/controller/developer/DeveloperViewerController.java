package devinfosys.controller.developer;

import java.util.List;

import devinfosys.console.IConsole;
import devinfosys.dao.IDeveloperDAO;
import devinfosys.model.Developer;
import devinfosys.view.Startable;

public class DeveloperViewerController implements Startable {
	private IConsole console;
	private IDeveloperDAO developerDAO;
	
	@Override
	public void start() {
		List<Developer> developers = developerDAO.getAll();
		for (Developer developer : developers)
			console.write(developer.toString());
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
