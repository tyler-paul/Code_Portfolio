#include "StoryEventFetcher.h"
#include "rapidxml.hpp"
#include "rapidxml_utils.hpp"
#include <list>

using namespace std;
using namespace rapidxml;

Game::StoryEventFetcher::StoryEventFetcher()
{
	actionMap = new ActionMap();
	storyEventMap = new std::map<std::string, StoryEvent>();
}

Game::StoryEventFetcher::~StoryEventFetcher()
{
	delete actionMap;
	delete storyEventMap;
}

Game::StoryEvent Game::StoryEventFetcher::getNextStoryEvent(std::string eventTag)
{
	return (*storyEventMap)[eventTag];
}

void Game::StoryEventFetcher::load(std::string filePath)
{
	xml_document<> doc; 
	rapidxml::file<> xmlFile(filePath.c_str());
	doc.parse<0>(xmlFile.data());
	xml_node<> *node = doc.first_node("root");

	//for each event in the xml document
	for (xml_node<> *eventNode = node->first_node(); eventNode; eventNode = eventNode->next_sibling())
	{
		//parse the tag for the event, the text, options available to the player, and the tag of the event to redirect to after the option is completed
		string eventTag = eventNode->first_attribute()->value();
		string text;
		std::vector<StoryOption> optionList;
		std::string redirectTag;
		for (xml_node<> *eventNodeChild = eventNode->first_node(); eventNodeChild; eventNodeChild = eventNodeChild->next_sibling())
		{
			if (eventNodeChild->name() == string("text")) {
				text = eventNodeChild->value();
			}
			else if (eventNodeChild->name() == string("option")) {
				//parse the tag of the option, text for the option, the name of the action function to execute if the player chooses the option,
				//the argument for the action, the tag of the next story event after this option, a list of tags of options which will cause the
				//current option to be enabled if all those options were chosen previously, and a list of tags of options which will cause the
				//current option to be disabled if one of those options were chosen previously.
				string optionTag;
				string optionText;
				string actionName;
				string actionArgument;
				string next;
				std::set<std::string> enableAfterSet;
				std::set<std::string> disableAfterSet;
				for (xml_node<> *optionNodeChild = eventNodeChild->first_node(); optionNodeChild; optionNodeChild = optionNodeChild->next_sibling())
				{
					if (eventNodeChild->first_attribute() != nullptr)
						optionTag = eventNodeChild->first_attribute()->value();
					if (optionNodeChild->name() == string("text")) {
						optionText = optionNodeChild->value();
					}
					else if (optionNodeChild->name() == string("action")) {
						for (xml_node<> *actionNodeChild = optionNodeChild->first_node(); actionNodeChild; actionNodeChild = actionNodeChild->next_sibling()) {
							if (actionNodeChild->name() == string("name")) {
								actionName = actionNodeChild->value();
							}
							else if (actionNodeChild->name() == string("argument")) {
								actionArgument = actionNodeChild->value();
							}
						}
					}
					else if (optionNodeChild->name() == string("next")) {
						next = optionNodeChild->value();
					}
					else if (optionNodeChild->name() == string("enableAfter")) {
						enableAfterSet.insert(optionNodeChild->value());
					}
					else if (optionNodeChild->name() == string("disableAfter")) {
						disableAfterSet.insert(optionNodeChild->value());
					}
				}
				optionList.push_back(StoryOption(optionTag, optionText, actionMap->getAction(actionName), actionArgument, next, enableAfterSet, disableAfterSet));
			}
			else if (eventNodeChild->name() == string("redirect")) {
				redirectTag = eventNodeChild->value();
			}
		}
		//add event to the map
		(*storyEventMap)[eventTag] = StoryEvent(eventTag, text, optionList, redirectTag);
	}
}
