# JavaCourse

A self-directed Java 17 course for a working backend engineer. Core Java + JUnit 5 / Mockito. No Spring, no build-tool deep-dives.

## Structure

- `JavaCourseOutline.md` — full course outline (9 modules, with DIY exercises and extended-practice challenges)
- `PROGRESS.md` — agent build status per submodule
- `<N> <Module Title>/<N.M> <Submodule Title>/Content.md` — per-submodule lesson notes

## Module order

1. OOP Foundations (ends with 1.8 Packages + Classpath — the gateway to package-based code)
2. Testing Fundamentals (JUnit 5, Mockito) — placed early so every later DIY can be verified
3. Beyond OOP Basics: Idiomatic Java
4. The Standard Library You'll Actually Use
5. Functional Java
6. Concurrency (includes 6.5 Testing Concurrent Code)
7. Design Patterns
8. Annotations, Reflection, and the Module System
9. JVM and Performance Awareness

## DIY workflow

- Before 1.8: single-file source mode (`java BankAccount.java`), file next to the lesson.
- From 1.8 onward: per-submodule `src/` with a flat package, compiled to `out/`.
- From Module 2 onward: tests live in `src/test/` and run against the classpath with JUnit 5.
