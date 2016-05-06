package devinfosys.view;

import java.util.List;

import devinfosys.console.IConsole;

public class Menu implements Startable {
	private String introduction;
	private List<String> menuItemLabels;
	private List<Startable> menuItems;
	private IConsole console;
	
	public void start() {
		
		boolean properInput = false;
		int selectionNo = -1;
		
		do {
			if (menuItemLabels.size() != menuItems.size())
				return;
			
			console.write(introduction);
			for (int i = 0; i < menuItems.size(); i++) {
				console.write((i + 1) + "." +  menuItemLabels.get(i));
			}
			String selection = console.read();
			
			//check users input
			try {
				selectionNo = Integer.parseInt(selection);
				properInput = true;
			}
			catch (Exception e) {
				properInput = false;
			}
			if (selectionNo <= 0 || selectionNo > menuItems.size()) {
				properInput = false;
			}
			if (!properInput)
				console.write("Improper input");
		
		} while (!properInput);
		
		
		menuItems.get(selectionNo - 1).start();
	}
	
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public List<String> getMenuItemLabels() {
		return menuItemLabels;
	}
	public void setMenuItemLabels(List<String> menuItemLabels) {
		this.menuItemLabels = menuItemLabels;
	}
	public List<Startable> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<Startable> menuItems) {
		this.menuItems = menuItems;
	}
	public IConsole getConsole() {
		return console;
	}
	public void setConsole(IConsole console) {
		this.console = console;
	}
}
