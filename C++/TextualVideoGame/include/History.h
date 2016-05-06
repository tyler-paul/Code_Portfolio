#pragma once
#include <map>
#include <set>

namespace Game {
	extern std::map< std::string, std::set<std::string> > playerHistoryMap;
	///<summary>Returns true if the player has previously chosen the option with tag optionTag in the event with tag eventTag </summary>
	bool mapContains(std::string eventTag, std::string optionTag);
}
