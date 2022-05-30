# settlers-installer
Installer for settlers-remake - see https://github.com/paulwedeck/settlers-remake

# Why?

If you want to run settlers-remake, you need to
- install the Java Runtime
- install settlers-remake
- have a copy of of your original Settlers 3 installation
- remember the command line how to correctly invoke the game

This project wraps all these steps and provides an easy way to install 
settlers-remake as native package for Linux, MacOS, and Windows:

- If need be, you get prompted for the installation CD and can create the Settlers 3 installation even with non-Windows computers.
- You get an application icon / menu shortcut to run the game.
- You get updates installed automatically.

You need more? Help improving this project to ease playing that world class real time strategy game.

# Installation

## Debian/Ubuntu Linux

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

## MacOS

Install the DMG package you can download from 
https://github.com/HiranChaudhuri/settlers-installer/releases. Once done, search 
for the application SettlersRemake in your desktop environment and click the 
icon.

On startup, the application will download the latest available release of 
Settlers-Remake (remember, you actually downloaded the installer only). It also
ensures you have the game files from the original release at the right location.
If not, you are prompted to insert the installation CD.

After all these prerequisites are met, the SettlersRemake game is started.
Have fun!

## Windows

Install the executable installer you can download from 
https://github.com/HiranChaudhuri/settlers-installer/releases. Once done, search 
for the application SettlersRemake in your desktop environment and click the 
icon.

# Usage

## Preparation

When starting up settlers-installer the first time, chances are high you do neither
have games nor the necessary data files installed. The installer window will show
you a list of available game releases. The status column indicates the release
is locally installed or just available online (the cloud symbol). You will have
to select one game version to run. If it is not yet installed, it will automatically
be downloaded for you before running it.

Also the installer will point out that the game files are still missing. Click
the 'Install Data' button to get this fixed. But you have to select a source where
to obtain the files from. Use the ellipsis button to browse for the directory 
yourself. Valid choices are either an existing Settlers 3 installation folder or
the installation CD you placed in one of your drives.
Alternatively you can make the installer scan your filesystem by clicking the
'Suggest' button. The filesystem scan will take some time, and actually it will
never end. Directories potentially holding the required data will be pointed out,
and simply choose one of them and click the Ok button to continue.

## Playing

Once you selected a game version and have the data files installed, a green
'Play' button will show up. Hit that one and the game will start.

## Toolbox

In case you want to dig a bit into settlers-remake, use the toolbox that lets you
choose which of the tools to run: 
- DAT File Viewer
- Movable Model Window
- Building Creator

## Configuration

### Games

The installer can be configured as of which game versions it should offer for
playing (just releases, also pre-releases or even build artifacts). The difference
is the level of testing they have undergone. The last item requires authentication
on GitHub (see below).

### Misc

You can also choose to have bug reporting support. During gameplay the installer
offers a red bug button. Hit that when you think something is just not right.
Instead of having to leave the game, just enter a short title and text, choose
which content should get added to the issue and hit Ok. The issue is created in
GitHub automatically, and you can continue playing. This requires authentication
on GitHub (see below).

Note: Adding screenshots and logfiles is still WIP.

### Authentication on GitHub

Settlers-installer will authenticate on GitHub using your username and your
personal access token. Follow the [GitHub documentation](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) 
to generate one yourself.

# Behaviour

The installer will host the necessary files all under $HOME/.jsettlers/managed.
In case you are interested, the folders underneath are used like this:

- data - Hosts the original game files necessary for playing.
- game - Hosts the extracted releases of settlers-remake.
- logs - Hosts logfiles created by settlers-installer. This is to not mix them
up with logfiles from settlers-remake.
- temp - A temporary directory where downloads are stored until they get extracted
- var - Variable data created by settlers-remake, such as logfiles and savegames.

When running settlers-remake, var is the current working directory.

# Special Usecases

## Troubleshooting

If you are unsure about the installer's behaviour or want to know about it's 
decisions check the logs. These are presented on STDOUT and in logfiles.

- You should see STDOUT if you launch a terminal and run 
/opt/slettersremake/bin/SettlersRemake.
- Otherwise, check for logfiles in $HOME/.jsettlers.

## Developing graphics

Run the DAT File Viewer from the settlers-installer toolbox. From the menu,
choose 'Export Images'/'from all files' to read images from Bluebytes data format.
The game files shall get written to the folder $HOME/.jsettlers/managed/var/graphics.
Since the export dialog already starts in your home directory, just specify
`.jsettlers/managed/var/graphics` as the target directory.