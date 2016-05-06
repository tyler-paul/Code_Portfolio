#include "ActionMap.h"

Game::ActionMap::ActionMap()
{
}

Game::ActionMap::~ActionMap()
{
}

Game::ActionFuncPtr Game::ActionMap::getAction(std::string actionName)
{
	//I wish c++ had reflection...
	if (actionName == "BitPuzzleAction")
		return bitPuzzleAction;
	else
		return noAction;
}