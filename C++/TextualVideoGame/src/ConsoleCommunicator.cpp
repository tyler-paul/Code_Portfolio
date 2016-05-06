#include "ConsoleCommunicator.h"
#include <iostream>
#include <sstream>
#include <cstdlib>
#ifdef WIN32
#include <windows.h>
#else
#include <unistd.h>
#endif

#define CONSOLE_WIDTH 79
#define DELAY_MS 20

Game::ConsoleCommunicator* Game::ConsoleCommunicator::consoleCommunicator = nullptr;

Game::ConsoleCommunicator::ConsoleCommunicator()
{
}

Game::ConsoleCommunicator* Game::ConsoleCommunicator::getInstance()
{
	if (!consoleCommunicator)
		consoleCommunicator = new ConsoleCommunicator();

	return consoleCommunicator;
}

namespace {
	std::string formatString(std::string s) {
		if (s.size() < CONSOLE_WIDTH)
			return s;
		std::stringstream ss;
		int i = CONSOLE_WIDTH;
		int iold = 0;
		while (1) {
			while (s[i] != ' ' && i > iold) i--;
			if (i == iold) {
				i = iold + CONSOLE_WIDTH;
				ss << s.substr(iold, i - iold);
				iold = i;
			}
			else {
				ss << s.substr(iold, i - iold) << std::endl;
				iold = i + 1;
			}
			i = iold + CONSOLE_WIDTH;
			if (i >= s.size()) {
				ss << s.substr(iold, s.size() - iold) << std::endl;
				break;
			}
		}
		return ss.str();
	}
}

void Game::ConsoleCommunicator::writeToPlayer(std::string s)
{
	s = ::formatString(s);
	//display string using a typewriter-like effect
	for (int i = 0; i < s.size(); i++) { 
		std::cout << s[i] <<std::flush;
#ifdef WIN32
		Sleep(DELAY_MS);
#else
		usleep(DELAY_MS * 1000);   // usleep takes sleep time in us (1 millionth of a second)
#endif
	}
	std::cout << std::endl;
}
void Game::ConsoleCommunicator::writeToPlayerWithoutEffects(std::string s)
{
	std::cout << ::formatString(s) << std::endl;
}

int Game::ConsoleCommunicator::getInput()
{
	std::cout << "Enter response: ";
	int input;
	if (std::cin >> input)
		return input;
	else {
		std::cin.clear();
		std::cin.ignore();
		return -1;
	}
}

void Game::ConsoleCommunicator::pause()
{
	std::cout << "Press Enter To Continue..." << std::endl;
	std::cin.ignore();
	std::cin.get();
}

void Game::ConsoleCommunicator::clear()
{
//	for (int i = 0; i < 25; i++)
//		std::cout << std::endl;
#ifdef WIN32
	std::system("cmd.exe /c cls");
#else
    std::system ("clear");
#endif
}
