# JavaCourse

A self-directed Java 17 course for a working backend engineer. Core Java + JUnit 5 / Mockito. No Spring, no build-tool deep-dives.

## Compiling and Running

### CLI

Assumes you are using the Main class and method.

```
# compile all production code
javac -d out/main $(find src/main/java -name '*.java')

# run (adjust the main class to your exercise)
java -cp out/main app.Main
```

## Testing (Module 2 onward)

Module 2+ DIYs include JUnit 5 tests. With no build tool, testing is set up by hand — Maven, Gradle, or an IDE would otherwise handle all of this for you (see submodule 2.1).

Download `junit-platform-console-standalone` once into `lib/`. It is git-ignored (`*.jar`), so re-fetch it on a fresh clone:

```
curl -sL -o lib/junit-platform-console-standalone-1.14.4.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.14.4/junit-platform-console-standalone-1.14.4.jar
```

Production code lives under `src/main/java/`, tests under `src/test/java/` with the same package names, compiled to `out/`:

```
# compile production code
javac -d out/main $(find src/main/java -name '*.java')

# compile tests (production classes + JUnit jar on the classpath)
javac -cp out/main:lib/junit-platform-console-standalone-1.14.4.jar -d out/test $(find src/test/java -name '*.java')

# run all tests
java -jar lib/junit-platform-console-standalone-1.14.4.jar execute -cp out/main:out/test --scan-class-path
```

## DIY workflow

- Before 1.8: single-file source mode (`java BankAccount.java`) — early exercises can still be run this way.
- From 1.8 onward: production code lives under `src/main/java/<package>/`, compiled to `out/main/`.
- From Module 2 onward: tests live under `src/test/java/<package>/` (same package names), run from the classpath with JUnit 5 — see Testing above.
