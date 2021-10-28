#!/usr/bin/env sh

#     Compile the application code [*not* the test code].
#     In this example, any third-party library JARs are located in 'lib' and added to the classpath [-classpath option].
#     Remove this -classpath "lib/*" if you're not using any.
javac -d classes -classpath "lib" src/com/games/whoami/client/*.java src/com/games/whoami/controller/*.java src/com/games/whoami/*.java


#     Build the application JAR.
#     This example adds the directory tree of .class files starting at 'classes'.
jar --create --file whoami-1.0.jar -C classes .


#     Create the Javadoc.
#     The '-package' flag will include non-public classes and all class members except for private ones.
#     Again, any third-party library JARs are located in 'lib' and added to the classpath.
#     And again, remove this -classpath "lib/*" if you're not using any.

#     Running this only makes sense if you've taken the time to write API comments in your code.
#     API (javadoc) comments are important when the "product" is a library for other developers to use.
#     It's not as important when the "product" is a finished application, like with your project.
#     Just leave this commented out if not using.

#     javadoc -d doc --source-path src -classpath "lib/*" -package -windowtitle BlackJack com.games.whoami.client com.games.whoami.controller com.games.whoami