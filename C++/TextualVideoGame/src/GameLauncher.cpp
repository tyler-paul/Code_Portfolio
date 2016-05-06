#include "GameLauncher.h"
#include "History.h"
#include <set>
#include <map>
#include <sstream>

Game::GameLauncher::GameLauncher()
{
	consoleCommunicator = ConsoleCommunicator::getInstance();
	storyEventFetcher = new Game::StoryEventFetcher();
	try {
		storyEventFetcher->load("story.xml");
	}
	catch (...) {
		throw -1;
	}
}

Game::GameLauncher::~GameLauncher()
{
	delete storyEventFetcher;
	delete consoleCommunicator;
}

namespace {
	std::string intToStr(int a)
	{
		std::stringstream ss;
		ss << a;
		return ss.str();
	}
}
void Game::GameLauncher::launch()
{
	int input = 0;
	std::string nextEventTag = "Start";
	bool redirected = false;
	std::map<int, int> optionIndexMap;

	//main game loop
	do {
		//get next story event and display it to the player.
		//The story event message is displayed with effects only if it is 
		//encountered for the first time.
		StoryEvent storyEvent = storyEventFetcher->getNextStoryEvent(nextEventTag);
		consoleCommunicator->clear();
		if (!redirected)
			consoleCommunicator->writeToPlayer(storyEvent.getMessageToPlayer());
		else
			consoleCommunicator->writeToPlayerWithoutEffects(storyEvent.getMessageToPlayer());

		std::vector<StoryOption> optionsList = storyEvent.getOptions();
		if (optionsList.size() == 0) {
			consoleCommunicator->pause();
			nextEventTag = storyEvent.getRedirectTag();
			redirected = true;
		}
		else {
			//print out options to the player
			int i = 0;
			int j = 0;
			consoleCommunicator->writeToPlayerWithoutEffects(""); //newline
			for (std::vector<StoryOption>::iterator it = optionsList.begin(); it != optionsList.end(); it++) {
				//check if option should be enabled or disabled
				std::set<std::string> enableAfterSet = it->getEnableAfterSet();
				std::set<std::string> disableAfterSet = it->getDisableAfterSet();
				bool enabled = true;
				std::set<std::string>::const_iterator iterator;
				for (iterator = enableAfterSet.begin(); iterator != enableAfterSet.end(); ++iterator) {
					if (!mapContains(storyEvent.getTag(), *iterator)) {
						enabled = false;
						break;
					}
				}
				for (iterator = disableAfterSet.begin(); iterator != disableAfterSet.end(); ++iterator) {
					if (mapContains(storyEvent.getTag(), *iterator)) {
						enabled = false;
						break;
					}
				}
				
				//print out option to the player
				if (enabled) {
					consoleCommunicator->writeToPlayerWithoutEffects("--------------------------------------------------------------------------------");
					consoleCommunicator->writeToPlayerWithoutEffects(intToStr(++i) + ". " + it->getPlayersMessage());
					optionIndexMap[i] = j;
				}
				++j;
				
			}
			consoleCommunicator->writeToPlayerWithoutEffects("--------------------------------------------------------------------------------");
			
			//get player input to determine their choice of options and act accordingly
			do {
				input = consoleCommunicator->getInput();
			} while (input <= 0 || input > optionsList.size());
			int index = optionIndexMap[input];
			
			//book-keeping
			optionsList[index].getStoryAction()(optionsList[index].getStoryActionArgument()); //execute story action
			nextEventTag = optionsList[index].getNextStoryEventTag();
			redirected = false;
			playerHistoryMap[storyEvent.getTag()].insert(optionsList[index].getTag()); //mark that the option was taken
			optionIndexMap.clear();
		}
	} while (nextEventTag != "End");
}
