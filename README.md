# JavaCourse

A self-directed Java 17 course for a working backend engineer. Core Java + JUnit 5 / Mockito. No Spring, no build-tool deep-dives.

## Compiling and Running

### CLI

Assumes you are using the Main class and method.

```
// adjust to your files if desired
javac -d out src/**/*.java

// run
java -cp out app.Main
```

## DIY workflow

- Before 1.8: single-file source mode (`java BankAccount.java`), file next to the lesson.
- From 1.8 onward: per-submodule `src/` with a flat package, compiled to `out/`.
- From Module 2 onward: tests live in `src/test/` and run against the classpath with JUnit 5.
