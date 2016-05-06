#pragma once
#include <string>

namespace Game {
	/// <summary>ConsoleCommunicator interacts with the console so that the rest of the system is decoupled from cin and cout.</summary>
	class ConsoleCommunicator
	{
		static ConsoleCommunicator* consoleCommunicator;
		ConsoleCommunicator();
	public:
		/// <summary>Get an instance of ConsoleCommunicator. This ensures that only one object is created (Singleton pattern).</summary>
		/// <returns>The instance</returns>
		static ConsoleCommunicator* getInstance();
		/// <summary>Write a string to the console to the player. The string is written using a typewriter-like effect.</summary>
		/// <param name="s">string to write</param>
		void writeToPlayer(std::string s);
		/// <summary>Write a string to the console to the player without a typewriter-like effect.</summary>
		/// <param name="s">string to write</param>
		void writeToPlayerWithoutEffects(std::string s);
		/// <summary>Get a positive integer response from the player.</summary>
		/// <returns>The integer received from the player or -1 if the player typed anything but a positive integer.</returns>
		int getInput();
		/// <summary>Pause the console until the player pushes enter.</summary>
		void pause();
		/// <summary>Clear the console.</summary>
		void clear();
	};
}
