# JavaCourse — Claude Context

## What this repo is

A self-directed Java course (Java 17 LTS) for a working backend engineer who wants deep fluency in OOP, the standard library, concurrency, design patterns, and testing — without leaning on AI to write code.

**Scope:** Core Java + JUnit 5 / Mockito. No Spring, no build-tool deep-dives.

## Key files

- `Content/JavaCourseOutline.md` — full course outline with all submodules and DIY exercises
- `Content/` — all module folders and submodule lesson files live here
- `PROGRESS.md` — tracks completion status for each submodule (⬜ / 🔄 / ✅)

## How the agent builds the course

`PROGRESS.md` tracks what the **agent has built**, not what the student has learned.

When building a submodule:
1. **Directory:** `Content/<N> <Module Title>/<N.M> <Submodule Title>.md` — e.g., `Content/1 Object-Oriented Programming Foundations/1.1 Classes, Objects, and Encapsulation.md`
2. **Output:** One Markdown file per submodule explaining the concepts concisely.
3. **No DIY code.** The student writes all exercise code themselves.

## Module ordering note

Testing (Module 2) was deliberately moved ahead of the rest of the standard library so that every DIY from Module 3 onward can be verified with a real JUnit test. Submodule **1.8 Packages, Imports, and the Classpath** is the gateway: it gives the student the package + classpath knowledge JUnit requires.

## DIY exercise conventions

Until submodule **1.8 Packages, Imports, and the Classpath** is built, the student runs DIY exercises in **single-file source mode** (Java 11+): no `package` declaration, file lives alongside the lesson's `Content.md`, run with `java BankAccount.java`. This sidesteps the fact that the course directory names (spaces, leading digits) are not legal Java package identifiers.

Starting with 1.8, multi-file exercises graduate to a single repo-root source tree: production code under `src/main/java/<package>/` with short flat package names, compiled to `out/main/`. From Module 2 onward, exercises also include a `src/test/java/` tree with JUnit 5 tests run from the classpath (no Maven/Gradle). See `README.md` for the exact build, run, and test commands.
