CC=g++
CFLAGS=-std=gnu++0x -m32 -DWIN32 -I include
LFLAGS=-static-libgcc -static-libstdc++

all: Main

Main: Main.o GameLauncher.o ActionMap.o ConsoleCommunicator.o Actions.o History.o StoryEventFetcher.o
	$(CC) $(LFLAGS) obj/Main.o obj/GameLauncher.o obj/ActionMap.o obj/ConsoleCommunicator.o obj/Actions.o obj/History.o obj/StoryEventFetcher.o -o Main_Windows.exe

Main.o: src/Main.cpp
	$(CC) $(CFLAGS) -c src/Main.cpp -o obj/Main.o

GameLauncher.o: src/GameLauncher.cpp
	$(CC) $(CFLAGS) -c src/GameLauncher.cpp -o obj/GameLauncher.o

ActionMap.o: src/ActionMap.cpp
	$(CC) $(CFLAGS) -c src/ActionMap.cpp -o obj/ActionMap.o

ConsoleCommunicator.o: src/ConsoleCommunicator.cpp
	$(CC) $(CFLAGS) -c src/ConsoleCommunicator.cpp -o obj/ConsoleCommunicator.o

Actions.o: src/Actions.cpp
	$(CC) $(CFLAGS) -c src/Actions.cpp -o obj/Actions.o

History.o: src/History.cpp
	$(CC) $(CFLAGS) -c src/History.cpp -o obj/History.o

StoryEventFetcher.o: src/StoryEventFetcher.cpp
	$(CC) $(CFLAGS) -c src/StoryEventFetcher.cpp -o obj/StoryEventFetcher.o

clean:
	rm obj/*o Main_Windows.exe
