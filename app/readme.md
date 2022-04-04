Installer for settlers-remake

= Why? =

Settlers-remake is a nice project but does not (yet) come as a fully playable version.
This installer shall make it easier for users to assemble the necessary filesystem
structure and manage savegames and support requests.

= What filesystem structure? =

What you need on the disk is the original game's data and the release files:
- JSettlers v1
- JSettlers v2
- JSettlers v...
- S3 game data
- Game Music

While the release files can be downloaded from Github, the original game data
would come either
- from an existing S3 installation (requires some Windows machine with S3 installed)
- from demo files
- Music would reside as audio tracks on the CD but can be downloaded from the
internet archive

= Filesystem layout =
$HOME
$HOME/.jsettlers/managed
$HOME/.jsettlers/managed/data
$HOME/.jsettlers/managed/data/Gfx
$HOME/.jsettlers/managed/data/Snd
$HOME/.jsettlers/managed/data/Music
$HOME/.jsettlers/managed/game
$HOME/.jsettlers/managed/game/v1
$HOME/.jsettlers/managed/game/v2
...
