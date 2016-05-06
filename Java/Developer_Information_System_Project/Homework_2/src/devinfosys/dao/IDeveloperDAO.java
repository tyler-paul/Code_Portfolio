package devinfosys.dao;

import java.util.List;

import devinfosys.model.Developer;
import devinfosys.model.DeveloperInfo;

public interface IDeveloperDAO {
	public abstract boolean create(DeveloperInfo developerInfo);
	public abstract boolean update(Developer developer);
	public abstract boolean delete(Developer developer);
	public abstract List<Developer> getAll();
	public abstract Developer getDeveloper(String developerName);
}
