GdxTest
=======

Testing the GDX Framework


##Prerequisites

To successfully compile this project you need to install the tween libary to your local maven repository. In Netbenas IDE, right click on the main project, go to custom and select the install_tween goal.
All other dependencies should download and install automatically. 


##Modify code

To modify the code of the Projekt, just set the build profile to desktop, then open the core module. This is where the code is placed.

##Run it

To run the project open the desktop module and run it. In Netbeans IDE you can go to Run -> Set Main Project -> GdxTest Desktop. You can now hit F6 (or the Play button) to run the Project at any time. The desktop project doesn't have to be opened.

##Add Assets

To add assets (images, sounds, sprites, etc) to the project, just place them in the assets folder. Everytime you change something in that folder, you need to build the parent project. This step is neccessary because the assets need to be packed into a .jar file to be accessed from the code. 
