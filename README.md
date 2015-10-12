# javachecker
Tool to check what Java version is required by a single jar file or a directory of them.


#Requirements

It requires Java 1.5+ to work. The class files in the jars are analyzed via InputStream and are not loaded by the JVM.
That means that the tool can inspect JAR files containing classes that were compiled with a higher Java version than the
one that was used to create this tool.
