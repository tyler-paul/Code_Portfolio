#pragma once
#include <iostream>

namespace Game {
	typedef void(*ActionFuncPtr)(std::string argument);

	void noAction(std::string argument);
	void bitPuzzleAction(std::string argument);
}