package devinfosys.dao;

import java.util.ArrayList;
import java.util.List;

import devinfosys.model.Developer;
import devinfosys.model.DeveloperInfo;

public class DeveloperExampleDAO implements IDeveloperDAO {

	private List<Developer> developers;
	private static int nextId;
	
	public DeveloperExampleDAO() {
		developers = new ArrayList<Developer>();
		nextId = 0;
	}
	
	@Override
	public boolean create(DeveloperInfo developerInfo) {
		//check if there is already a developer with the given name
		for (Developer developer : developers) {
			if (developer.getDeveloperInfo().getName().equals(developerInfo.getName()))
				return false;
		}
		
		developers.add(new Developer(nextId++, developerInfo));
		
		return true;
	}

	@Override
	public boolean update(Developer developer) {
		
		if (developer == null) {
			return false;
		}
		
		boolean updated = false;
		for (int i = 0; i < developers.size(); i++) {
			Developer aDeveloper = developers.get(i);
			if (aDeveloper.getId() == developer.getId()) {
				aDeveloper.getDeveloperInfo().setName(developer.getDeveloperInfo().getName());
				aDeveloper.getDeveloperInfo().setPassword(developer.getDeveloperInfo().getPassword());
				updated = true;
				break;
			}
		}
		
		return updated;
	}

	@Override
	public boolean delete(Developer developer) {
		
		if (developer == null) {
			return false;
		}
		
		boolean deleted = false;
		for (int i = 0; i < developers.size(); i++) {
			Developer aDeveloper = developers.get(i);
			if (aDeveloper.getId() == developer.getId()) {
				developers.remove(i);
				deleted = true;
				break;
			}
		}
		
		return deleted;
	}

	@Override
	public List<Developer> getAll() {
		return developers;
	}

	@Override
	public Developer getDeveloper(String developerName) {
		
		for (Developer developer : developers) {
			if (developer.getDeveloperInfo().getName().equals(developerName))
				return developer;
		}
		return null;
	}

}
