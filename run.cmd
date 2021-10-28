@echo off

cls

rem   In this example, any third-party library JARs are located in 'lib' and added to the classpath [-classpath option].
rem   Remove this -classpath "lib\*" if you're not using any.

rem   This example is using the built-in splash-image mechanism of the JVM.
rem   Remove this -splash option if you're not using it.
java -splash:images/team.png -classpath whoami-1.0.jar;"lib\*" com.games.whoami.client.Play