#pragma once
#include "StoryEvent.h"
#include "ActionMap.h"
#include <map>

namespace Game {
	/// <summary>StoryEventFetcher reads story events from an xml file and returns 
	/// objects encapsulating those story events.</summary>
	class StoryEventFetcher
	{
		std::map<std::string, StoryEvent>* storyEventMap;
		Game::ActionMap* actionMap;
	public:
		StoryEventFetcher();
		~StoryEventFetcher();
		/// <summary>Get the story event corresponding to the specified tag.</summary>
		/// <param name="eventTag">Tag of the story event to retrieve</param>
		/// <returns>the story event corresponding to the tag</returns>
		StoryEvent getNextStoryEvent(std::string eventTag);
		/// <summary>Load an XML file containing story events into this fetcher.</summary>
		/// <param name="filePath">Path of the file to load</param>
		void load(std::string filePath);
	};
}

