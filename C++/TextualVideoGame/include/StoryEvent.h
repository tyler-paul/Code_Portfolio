#pragma once
#include <string>
#include "Actions.h"
#include <vector>
#include <set>

namespace Game {
	/// <summary>StoryOption describes a possible response the player can take.</summary>
	class StoryOption
	{
		/// <summary>Tag used to uniquely identify this option.</summary>
		std::string tag;
		/// <summary>Message that the player will respond with if this option is chosen.</summary>
		std::string playersMessage;
		/// <summary>The action that will occur if this option is chosen.</summary>
		ActionFuncPtr storyAction;
		/// <summary>The argument to be passed to the action.</summary>
		std::string storyActionArgument;
		/// <summary> The tag of the story event which is to follow the current story event.</summary>
		std::string nextStoryEventTag;
		/// <summary> The set of option tags which should cause this option to be enabled if all of the 
		/// options were previously chosen by the user.</summary>
		std::set<std::string> enableAfterSet;
		/// <summary>The set of option tags which should cause this option to be disabled if any of the 
		/// options were previously chosen by the user. </summary>
		std::set<std::string> disableAfterSet;
	public:
		StoryOption(std::string tag, std::string playersMessage, ActionFuncPtr storyAction, std::string storyActionArgument,
			std::string nextStoryEventTag, std::set<std::string> enableAfterSet, std::set<std::string> disableAfterSet) {
			this->tag = tag;
			this->playersMessage = playersMessage;
			this->storyAction = storyAction;
			this->storyActionArgument = storyActionArgument;
			this->nextStoryEventTag = nextStoryEventTag;
			this->enableAfterSet = enableAfterSet;
			this->disableAfterSet = disableAfterSet;
		}
		/// <summary>Get the tag for the option.</summary>
		/// <returns>the tag</returns>
		std::string getTag() {
			return tag;
		}
		/// <summary>Get the message that the player can respond with.</summary>
		/// <returns>the message</returns>
		std::string getPlayersMessage()
		{
			return playersMessage;
		}
		/// <summary>Get a pointer to a function that should be executed if 
		/// this story option is chosen by the player.</summary>
		/// <returns>the pointer</returns>
		ActionFuncPtr getStoryAction()
		{
			return storyAction;
		}
		/// <summary>Get the string argument to be passed to the action function</summary>
		/// <returns>the argument</returns>
		std::string getStoryActionArgument()
		{
			return storyActionArgument;
		}
		/// <summary>Get the tag of the story event which is to follow the current story event.</summary>
		/// <returns>the tag</returns>
		std::string getNextStoryEventTag()
		{
			return nextStoryEventTag;
		}
		/// <summary>Get the set of option tags which should cause this option to be enabled if all of the 
		/// options were previously chosen by the user.</summary>
		/// <returns>the set of tags</returns>
		std::set<std::string> getEnableAfterSet()
		{
			return enableAfterSet;
		}
		/// <summary>Get the set of option tags which should cause this option to be disabled if any of the 
		/// options were previously chosen by the user.</summary>
		/// <returns>the set of tags</returns>
		std::set<std::string> getDisableAfterSet()
		{
			return disableAfterSet;
		}
	};

	/// <summary>StoryEvent describes an event in the main story.</summary>
	class StoryEvent
	{
		std::string tag;
		std::string messageToPlayer;
		std::vector<StoryOption> options;
		std::string redirectTag;
	public:
		StoryEvent(std::string tag, std::string messageToPlayer, std::vector<StoryOption> options, std::string redirectTag)
		{
			this->tag = tag;
			this->messageToPlayer = messageToPlayer;
			this->options = options;
			this->redirectTag = redirectTag;
		}
		StoryEvent()
		{
		}
		/// <summary>Get the tag for the event.</summary>
		/// <returns>the tag</returns>
		std::string getTag()
		{
			return tag;
		}
		/// <summary>Get the message that is to be displayed to the player.</summary>
		/// <returns>the message</returns>
		std::string getMessageToPlayer()
		{
			return messageToPlayer;
		}
		/// <summary>Get the options that the player can choose to respond to for this event.</summary>
		/// <returns>the options</returns>
		std::vector<StoryOption> getOptions()
		{
			return options;
		}
		/// <summary>Get the tag of the event that this event will redirect to if this event does not have any options.</summary>
		/// <returns>the tag</returns>
		std::string getRedirectTag()
		{
			return redirectTag;
		}
	};
}