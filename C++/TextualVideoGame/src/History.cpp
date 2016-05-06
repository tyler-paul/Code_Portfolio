#include "History.h"
#include <algorithm>

std::map<std::string, std::set<std::string>> Game::playerHistoryMap;

bool Game::mapContains(std::string eventTag, std::string optionTag)
{
	return (std::find(playerHistoryMap[eventTag].begin(), playerHistoryMap[eventTag].end(), optionTag) != playerHistoryMap[eventTag].end());
}
