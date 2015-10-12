# Build status
[![Build status](https://travis-ci.org/dawnstrider/javachecker.svg?branch=master)](https://travis-ci.org/dawnstrider/javachecker)


# javachecker
Tool to check what Java version is required by a single jar file or a directory of them.


## Requirements

It requires Java 1.5+ to work. The class files in the jars are analyzed via InputStream and are not loaded by the JVM.
That means that the tool can inspect JAR files containing classes that were compiled with a higher Java version than the
one that was used to create this tool.

Maven is required to build the tool. 

## Build

Check out the project. It comes with eclipse project settings, although you will most likely not need them.

To build the tool, just run

    mvn package
    
## Developing

The project comes with its own eclipse project settings. Just import the project into your workspace. It does not use any specific
maven repository.