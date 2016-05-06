package devinfosys.dao;

import java.util.ArrayList;
import java.util.List;

import devinfosys.model.DeveloperType;

public class DeveloperTypeExampleDAO implements IDeveloperTypeDAO {

	@Override
	public List<DeveloperType> getDeveloperTypes() {
		List<DeveloperType> types = new ArrayList<DeveloperType>();
		types.add(new DeveloperType("Designer"));
		types.add(new DeveloperType("Coder"));
		types.add(new DeveloperType("Tester"));
		return types;
	}

}
