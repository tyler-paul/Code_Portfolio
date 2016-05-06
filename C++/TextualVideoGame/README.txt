Author: Tyler Paul

Overview:
The game is a text-based game where the player discovers their mind has been uploaded into a computer in order to defeat an artificial intelligence entity. 
The game involves the player learning about the world they are in by selecting dialog choices. Additionally the player will need to complete puzzles at a 
certain point in the game. The game can be completed in about 20 or 30 minutes. To select a dialog choice you need to type the corresponding number of 
your dialog choice and then push enter. 

1. Building
An executable for 32-bit Windows and Linux are provided (which are statically linked to the C/C++ standard library). 
If you need to recompile simply run 'make' for Windows or 'make -f Makefile_Linux' for Linux.

2. Launching
Launch the executable Main.exe

3. Game-play
This code implements a simple text-based game where the player has multiple dialog options to choose. Additionally, there are
puzzle once the player reaches a certain part in the game.
To play the full game (including story and puzzles) choose 'New Game (Include Story)' from the main menu in the game.
To instead skip directly to the puzzles choose 'New Game (Skip Story)' from the main menu in the game.

4. Notes
The open source rapidxml parser was used to parse XML in this project (a license for which is included in the include folder).
All other code was written by Tyler Paul.