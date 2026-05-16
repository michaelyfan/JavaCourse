# JavaCourse — Claude Context

## What this repo is

A self-directed Java course (Java 17 LTS) for a working backend engineer who wants deep fluency in OOP, the standard library, concurrency, design patterns, and testing — without leaning on AI to write code.

**Scope:** Core Java + JUnit 5 / Mockito. No Spring, no build-tool deep-dives.

## Key files

- `JavaCourseOutline.md` — full course outline with all submodules and DIY exercises
- `PROGRESS.md` — tracks completion status for each submodule (⬜ / 🔄 / ✅)

## How the agent builds the course

`PROGRESS.md` tracks what the **agent has built**, not what the student has learned.

When building a submodule:
1. **Directory:** `<N> <Module Title>/<N.M> <Submodule Title>/Content.md` — e.g., `1 Object-Oriented Programming Foundations/1.1 Classes, Objects, and Encapsulation/Content.md`
2. **Output:** One Markdown file per submodule explaining the concepts concisely.
3. **No DIY code.** The student writes all exercise code themselves.
