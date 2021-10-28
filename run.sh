#!/usr/bin/env sh

clear

#     In this example, any third-party library JARs are located in 'lib' and added to the classpath [-classpath option].
#     Remove this -classpath "lib/*" if you're not using any.

#     This example is using the built-in splash-image mechanism of the JVM.
#     Remove this -splash option if you're not using it.
java -splash:images/team.png -classpath whoami-1.0.jar;"lib" com.games.whoami.client.Play
