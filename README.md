# settlers-installer
Installer for settlers-remake - see https://github.com/paulwedeck/settlers-remake

# Why?

If you want to run settlers-remake, you need to
- install the Java Runtime
- install settlers-remake
- have a copy of of your original Settlers 3 installation
- remember the command line how to correctly invoke the game

This project wraps all these steps and provides an easy to install native package for Linux, MacOS, and Windows:
- If need be, you get prompted for the installation CD and can create the Settlers 3 installation even with non-Windows computers.
- You get an application icon / menu shortcut to run the game.
- You get updates installed automatically.

You need more? Help improving this project to ease playing that world class real time strategy game.

# Installation

## Linux

Install the Debian package you can download from 
https://github.com/HiranChaudhuri/settlers-installer/releases. Once done, search 
for the application SettlersRemake in your desktop environment and click the 
icon.

On startup, the application will download the latest available release of 
Settlers-Remake (remember, you actually downloaded the installer only). It also
ensures you have the game files from the original release at the right location.
If not, you are prompted to insert the installation CD.

After all these prerequisites are met, the SettlersRemake game is started.
Have fun!

# Troubleshooting

The installer will host the necessary files all under $HOME/.jsettlers/managed.
In case you are interested, the folders underneath are used like this:

- data - Hosts the original game files necessary for playing.
- game - Hosts the downloaded releases of settlers-remake.
- temp - A temporary directory where downloads are stored until they get extracted
- var - Variable data created by settlers-remake, such as logfiles and savegames.
When running settlers-remake, var is the current working directory.
