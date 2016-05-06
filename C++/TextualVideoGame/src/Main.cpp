#include "GameLauncher.h"
#include <iostream>

//driver program to launch the game
int main() {
	Game::GameLauncher* launcher = nullptr;
	try {
		launcher = new Game::GameLauncher();
	}
	catch (...) {
		std::cout << "Unable to locate 'story.xml'. Make sure this file is located in the same directory as the executable for this game." << std::endl;
		std::cout << "Press Enter To Continue..." << std::endl;
		std::cin.get();
		return 0;
	}
	launcher->launch();
}