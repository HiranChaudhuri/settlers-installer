= Editing Graphics =

To edit the graphics, you will have to split the S3 directories' DAT files,
change them and finally assemble them back together.

= Splitting the DAT files =

cd into the jsettlers.tools project, then run

    ../gradlew build
    cd build/libs
    java -cp jsettlers.tools.jar:/home/hiran/bin/JSettlers/JSettlers.jar jsettlers.graphics.debug.DatFileViewer

From this GUI, click 'Export' from the menu.