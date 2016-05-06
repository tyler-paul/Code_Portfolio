#pragma once
#include <string>
#include "Actions.h"

namespace Game {
	/// <summary>ActionMap acts as a map to retrieve action functions from the name of the function given as a string.</summary>
	class ActionMap
	{
	public:
		ActionMap();
		~ActionMap();
		/// <summary>Get a pointer to an action function.</summary>
		/// <param name="actionName">Name of the action function to retrieve</param>
		/// <returns>a pointer to the action function</returns>
		ActionFuncPtr getAction(std::string actionName);
	};
}

