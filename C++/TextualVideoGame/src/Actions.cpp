#include "Actions.h"
#include <sstream>
#include <bitset>
#include <sstream>
#include "ConsoleCommunicator.h"
#include <vector>
namespace {
	typedef unsigned char BYTE;
}

/////////////////////////////////////////////////////////////////////////////////////////
/////////No ACTION
/////////////////////////////////////////////////////////////////////////////////////////
void Game::noAction(std::string argument) {
	//do nothing
}

/////////////////////////////////////////////////////////////////////////////////////////
/*                             BIT PUZZLE ACTION
The point of this game is for the player to perform operations on an array of bytes
in an attempt to transform each byte into a byte of 0s (b00000000). The allowed operations
are as follows:
1. A Bit Transfer: This operation acts on two bytes a and b. The 1s in byte a are moved
to the same position in byte b. This operation is only valid if there exists a position in
both bytes which both have a 1.
Examples ByteTransfer(00101001, 10000010) = 10101011
2. A Bit Flip: This operation flips all bits in the byte.
Example: BitFlip(00101001) = 11010110
*/
/////////////////////////////////////////////////////////////////////////////////////////
namespace {
	void bitFlip(BYTE* a);
	bool bitTransfer(BYTE* a, BYTE* b, int len);
	bool hasWon(BYTE* a, int len);

	void bitPuzzleAction(BYTE originalData[], int len) {
		Game::ConsoleCommunicator* consoleCommunicator = Game::ConsoleCommunicator::getInstance();
		BYTE* data = new BYTE[len];
		for (int i = 0; i < len; i++)
			data[i] = originalData[i];

		std::stringstream ss;
		ss.str("");
		while (!hasWon(data, len)) {
			//display an image respresenting the byte array the player can act on
			consoleCommunicator->clear();
			for (int i = 0; i < len; i++)
				ss << "-----------";
			ss << "-";
			consoleCommunicator->writeToPlayerWithoutEffects(ss.str());
			ss.str("");

			for (int i = 0; i < len; i++)
				ss << "| " << std::bitset<8>(data[i]) << " ";
			ss << "|";
			consoleCommunicator->writeToPlayerWithoutEffects(ss.str());
			ss.str("");

			for (int i = 0; i < len; i++)
				ss << "-----------";
			ss << "-";
			consoleCommunicator->writeToPlayerWithoutEffects(ss.str());
			ss.str("");

			for (int i = 0; i < len; i++)
				ss << "|    " << i + 1 << "     ";
			ss << "|";
			consoleCommunicator->writeToPlayerWithoutEffects(ss.str());
			ss.str("");

			for (int i = 0; i < len; i++)
				ss << "-----------";
			ss << "-" << std::endl;
			consoleCommunicator->writeToPlayerWithoutEffects(ss.str());
			ss.str("");

			//print the operations the player can choose
			consoleCommunicator->writeToPlayerWithoutEffects("1. Bit Transfer");
			consoleCommunicator->writeToPlayerWithoutEffects("2. Bit Flip");
			consoleCommunicator->writeToPlayerWithoutEffects("3. Reset");

			//get response of the player and perform the action
			int choice;
			do {
				choice = consoleCommunicator->getInput();
			} while (choice <= 0 || choice > 3);
			if (choice == 1) {
				consoleCommunicator->writeToPlayerWithoutEffects("Select the number of the byte to transfer from");
				int choice1;
				do {
					choice1 = consoleCommunicator->getInput();
				} while (choice1 < 1 || choice1 > len);

				consoleCommunicator->writeToPlayerWithoutEffects("Select the number of the byte to transfer to");
				int choice2;
				do {
					choice2 = consoleCommunicator->getInput();
				} while (choice2 < 1 || choice2 > len);

				if (!bitTransfer(&data[choice1 - 1], &data[choice2 - 1], len)) {
					consoleCommunicator->writeToPlayerWithoutEffects("Operation not valid. Type Enter to continue...");
					consoleCommunicator->pause();
				}
			}
			else if (choice == 2) {
				consoleCommunicator->writeToPlayerWithoutEffects("Select the number of the byte to flip");
				int choice1;
				do {
					choice1 = consoleCommunicator->getInput();
				} while (choice1 < 1 || choice1 > len);
				bitFlip(&data[choice1 - 1]);
			}
			else if (choice == 3) {
				for (int i = 0; i < len; i++)
					data[i] = originalData[i];
			}
		}
	}

	//player has won the game if the byte array consists of all 0s
	bool hasWon(BYTE* a, int len) {
		for (int i = 0; i < len; i++)
			if (a[i]) {
				return false;
			}
		return true;
	}

	//transfer bytes from a to b. Returns false if the operation
	//is not valid
	bool bitTransfer(BYTE* a, BYTE* b, int len)
	{
		std::bitset<8> aBits = std::bitset<8>(*a);
		std::bitset<8> bBits = std::bitset<8>(*b);

		//pass to determine if operation is valid
		for (int i = 0; i < 8; i++)
			if (aBits[i] && bBits[i])
				return false;

		//pass to perform operation
		for (int i = 0; i < 8; i++)
			if (aBits[i]) {
				bBits[i] = aBits[i];
				aBits[i] = 0;
			}
		unsigned long i = bBits.to_ulong();
		*b = static_cast<unsigned char>(i);
		i = aBits.to_ulong();
		*a = static_cast<unsigned char>(i);
		return true;
	}

	//Flip each bit in the byte
	void bitFlip(BYTE* a)
	{
		*a = ~(*a);
	}
}

void Game::bitPuzzleAction(std::string argument) {
	//parse argument into an array of BYTEs
	std::stringstream s(argument);
	std::string token;
	std::bitset<8> bits;
	std::vector<BYTE> bytes;
	while (getline(s, token, ' '))
	{
		bits = std::bitset<8>(token);
		BYTE b = static_cast<unsigned char>(bits.to_ulong());
		bytes.push_back(b);
	}

	//start up the puzzle
	::bitPuzzleAction(&bytes[0], bytes.size());
}
/////////////////////////////////////////////////////////////////////////////////////////
/////////END OF BIT PUZZLE ACTION
/////////////////////////////////////////////////////////////////////////////////////////