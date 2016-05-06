#pragma once
#include "StoryEventFetcher.h"
#include "ConsoleCommunicator.h"

namespace Game {
	/// <summary>Game represents the entire video game.</summary>
	class GameLauncher
	{
		StoryEventFetcher* storyEventFetcher;
		ConsoleCommunicator* consoleCommunicator;
	public:
		/// <summary>Construct the Game object and initialize resources.</summary>
		GameLauncher();
		/// <summary>Free up resources.</summary>
		~GameLauncher();
		/// <summary>Launch the video game.</summary>
		void launch();
	};
}

