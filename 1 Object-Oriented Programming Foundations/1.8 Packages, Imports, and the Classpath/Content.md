# 1.8 Packages, Imports, and the Classpath

Up to this point every DIY has been a single `.java` file run with `java Foo.java` — Java 11's single-file source mode. That mode is a teaching convenience; real Java programs are organized into **packages**, compiled to `.class` files, and run by pointing the JVM at a **classpath**. This submodule is the bridge. After it, every DIY moves into a per-submodule `src/` folder with a real package layout, and Module 2 can introduce JUnit on top.

This is mostly mechanics, but the mechanics are load-bearing. Misunderstanding the package-to-directory rule or the classpath is the source of a depressing share of "why won't my program compile / run" questions.

---

## The `package` declaration

Every Java type lives in a package. If a source file has no `package` declaration at the top, the type is in the *unnamed package* — fine for throwaway scripts, not allowed once you start importing other named-package types.

```java
package com.example.banking;

public class BankAccount {
    // ...
}
```

The package name has two jobs:

1. **Namespace.** `com.example.banking.BankAccount` is a different type from `com.other.BankAccount`. Reverse-DNS naming (`com.yourcompany.product.module`) is convention, not rule — it just keeps your packages from colliding with anyone else's.
2. **Access scope.** Package-private members (the default — no modifier) are visible only to other types declared with the same `package` line. Package-private is a *real* access boundary, not a syntactic one (1.1).

### The directory-must-match-package rule

A type declared `package com.example.banking;` must live at `com/example/banking/BankAccount.java` *relative to a source root*. The compiler enforces this when you build multi-file programs. If the file is in the wrong directory, `javac` either can't find it or emits a "class is public, should be declared in a file named ..." error.

The "source root" is whichever directory you hand to `javac` (or that's implied by your current working directory). A common layout:

```
src/
  com/
    example/
      banking/
        BankAccount.java
        Main.java
```

You compile from the project root with `javac -d out src/com/example/banking/*.java`, and the compiler writes the corresponding `.class` files under `out/com/example/banking/`. The package hierarchy is mirrored in both source and output.

### Legal package identifiers (and why this course's directories aren't)

Package name segments must be legal Java identifiers: start with a letter (or `_`/`$`), no spaces, no leading digits, not a reserved word. `com.example.banking` is fine. `1 Object-Oriented Programming Foundations.1.8 Packages` is **not** — it leads with a digit, contains spaces, and uses a hyphen.

That's the reason every DIY so far has lived in single-file source mode without a `package` line: the course's lesson directory names are friendly to humans but illegal to Java. Starting with this submodule, DIY code moves into a child directory whose name *is* a legal identifier — typically `src/` containing a short flat package like `banking/` — so packages and the classpath work normally.

---

## `import`

`import` is a *compile-time convenience*. It does not "load" or "include" anything; it just lets you write the short name `BankAccount` instead of the fully qualified `com.example.banking.BankAccount`.

```java
package com.example.app;

import com.example.banking.BankAccount;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BankAccount a = new BankAccount(100);
        List<BankAccount> all = List.of(a);
    }
}
```

A few rules and side notes:

- **`java.lang` is implicit.** `String`, `Integer`, `Math`, `Object`, `Thread`, `RuntimeException`, etc. need no import.
- **Same-package types need no import.** Anything in `com.example.app` can refer to other `com.example.app` types by short name automatically.
- **Wildcard imports** (`import java.util.*;`) bring in every *top-level* public type from a package. They do **not** descend into subpackages — `import java.util.*` does not import `java.util.concurrent.*`. They also don't slow compilation or bloat the resulting `.class` file (no runtime cost), but they hurt readability and risk silent name clashes when a future JDK release adds a class to the package. Most teams default to single-type imports.
- **`import static`** brings in static members so you can drop the class qualifier:

  ```java
  import static java.lang.Math.PI;
  import static java.lang.Math.sqrt;

  double hypot = sqrt(a * a + b * b);   // not Math.sqrt
  double circ  = 2 * PI * r;
  ```

  Heavily used in test code (`import static org.junit.jupiter.api.Assertions.*` — coming in 2.1). Outside of tests, use sparingly; calls like `assertEquals(x, y)` read fine, but a bare `min(a, b)` with no `Math.` is harder to scan.

### Fully qualified names and name clashes

If two types you need have the same short name, you can import one and fully qualify the other:

```java
import java.util.Date;

public class Demo {
    java.sql.Date sqlDate;   // fully qualified — no import for this one
    Date utilDate;
}
```

You cannot `import` both `java.util.Date` and `java.sql.Date` in the same file — Java has no `import ... as ...` alias syntax. Pick one to import and qualify the other inline.

---

## What the compiler actually does with imports

This trips people up: `import` does not produce any bytecode. After compilation, every type reference in your `.class` file is stored as a fully qualified name. `import com.example.banking.BankAccount` just tells the compiler "when I write `BankAccount` in this file, I mean `com.example.banking.BankAccount`." The resulting `.class` file is identical whether you used the import or wrote out the fully qualified name every time.

The practical consequence: imports are not a runtime dependency mechanism. If you import a class but never use it, the compiler is free to ignore the import entirely (and your IDE will mark it unused). If you do use it, the *class itself* must be on the classpath at runtime — the import does nothing to make that happen. That's the classpath's job.

---

## The classpath

The **classpath** is the list of directories and JAR files where the JVM looks for `.class` files at runtime. `javac` also uses it to resolve types referenced by code being compiled.

You pass it with `-cp` (or the longer `-classpath`), with entries separated by `:` on macOS/Linux and `;` on Windows:

```
# compile two packages into out/
javac -d out src/com/example/banking/*.java src/com/example/app/*.java

# run, telling the JVM that out/ is the root of the compiled tree
java -cp out com.example.app.Main
```

The argument after `-cp` is a **list of roots**. The JVM does *not* search recursively for `.class` files — it expects each root to contain a package hierarchy whose structure matches the fully qualified names. So if `out/com/example/app/Main.class` exists, `-cp out` plus `com.example.app.Main` resolves; `-cp out/com/example` does not.

A classpath entry can be:

- A **directory** (treated as a package root, as above).
- A **JAR file** (treated as a zipped package root — the JVM reads `.class` files directly out of the archive).
- A **wildcard** matching JARs: `-cp "lib/*"` picks up every `*.jar` directly inside `lib/` (not recursively, and the quotes matter so your shell doesn't glob first).

If `-cp` is omitted entirely, the default is the current directory (`.`). This is why `java com.example.app.Main` "just works" when you happen to be standing in `out/` — but relying on the default classpath is a bad habit.

### Common classpath mistakes

- **Pointing at the package directory instead of the root.** `java -cp out/com/example/app Main` fails because the JVM is looking for a class literally named `Main` in the default package, not `com.example.app.Main`. The classpath entry must be the *root above* the package hierarchy.
- **Forgetting the fully qualified name.** `java -cp out Main` fails for the same reason — `Main` is not in the default package, it's in `com.example.app`.
- **Confusing `-cp` with the directory you're in.** Your shell's current working directory is irrelevant to the JVM's class lookup (beyond resolving the relative path you pass to `-cp`). Standing in `src/` doesn't help.
- **Mixing source files and class files.** `javac` compiles `.java`, not `.class`. The classpath you pass `javac` is for *already-compiled* types it needs to reference; the source files you want to compile are positional arguments.

---

## Single-file source mode — what it actually does

Since Java 11, `java BankAccount.java` works without a separate `javac` step. The launcher compiles the source file in memory and executes its `main` method. It's perfect for scripts and tiny exercises.

It comes with restrictions that make it unsuitable for anything multi-file:

- **One source file only.** It cannot resolve references to types in other source files you haven't already compiled. (You can reference types from JARs on the classpath via `-cp`, but not sibling `.java` files.)
- **The file's `package` declaration, if any, is ignored at the directory level.** The launcher doesn't care that your file claims `package com.example.banking;` while sitting at `~/scratch/BankAccount.java`. This is fine for a script and confusing if you're learning the package rules — which is exactly why this submodule asks you to leave single-file mode behind.
- **No persistent `.class` files.** Every run recompiles. Fine for a 50-line script, miserable for a project.

The right time to outgrow it is now. After this submodule, DIYs use real packages and a real classpath, and Module 2 layers JUnit on top.

---

## Preview: the module path

Java 9 introduced the **module system** (JPMS) and a parallel concept to the classpath called the **module path**, used with `-p` (or `--module-path`). A module is a JAR with a `module-info.class` at its root that declares which packages it `exports` and which other modules it `requires`. The JVM uses that metadata to enforce strong encapsulation: code in one module cannot reach into a non-exported package of another module, even via reflection, without explicit permission.

In day-to-day application code most people still use the classpath, and mixing the two is supported (classpath code runs in the "unnamed module" with relaxed rules). You'll see `module-info.java` files in many libraries, and the JDK itself is modularized — that's why `java.base`, `java.sql`, `java.net.http` show up as module names. Module 8.3 covers JPMS properly. For now: know it exists, know `-p` is its flag, and don't be surprised when you see `module-info.java` in someone else's project.

---

## DIY Exercise

Convert your Module 1.1 `BankAccount` into a proper multi-package program and compile it with `javac`.

The starting point: pick *any* legal-identifier root directory (e.g., a `src/` folder inside this submodule's directory). Inside it, create two packages — one for the domain class, one for a `Main` that uses it.

A workable layout:

```
src/
  banking/
    BankAccount.java         // package banking;
  app/
    Main.java                // package app;  imports banking.BankAccount
out/                         // created by javac -d out
```

Tasks:

1. **Compile and run from the classpath.** Build with `javac -d out src/banking/*.java src/app/*.java`, then run with `java -cp out app.Main`. Confirm it works.
2. **Break it deliberately and read the errors.** Try each of these in turn, observe the exact compiler or runtime message, then fix it:
   - Move `BankAccount.java` into the wrong directory (e.g., into `src/app/` while keeping `package banking;`). What does `javac` say?
   - Remove the `import banking.BankAccount;` line from `Main.java`. What does `javac` say? How does the message change if you also remove the explicit `new BankAccount(...)` references? Try fixing it by using the fully qualified name `new banking.BankAccount(...)` *instead* of an import.
   - Compile successfully, then run with `java -cp out/banking app.Main`. Why does it fail, and what error do you get?
   - Run with `java -cp out Main` (forgetting the package prefix). What error do you get?
3. **Try a wildcard import.** Change `Main.java` to `import banking.*;` and recompile. Confirm it still works. Then add a second class in `banking/` (e.g., `Transaction.java`) and use it from `Main` — note that you didn't need a new import line.
4. **Try `import static`.** Add a `public static final BigDecimal MINIMUM_DEPOSIT = ...` to `BankAccount`. Import it statically into `Main` and use the bare name `MINIMUM_DEPOSIT`. Decide whether you'd actually keep this in real code.

Things to think about:

- Why does the JVM need the *root* of the package tree on the classpath rather than the package directory itself? (Hint: think about how it would resolve `app.Main` versus `banking.BankAccount` from a single entry.)
- If you wanted to ship this program as a single JAR, what would the JAR's internal directory structure need to look like? (Hint: same shape as `out/`.)
- What changes if `BankAccount`'s constructor is package-private (no modifier) instead of `public`? Try it. Where does the compile error appear, and why is *that* the line that fails?
- The course will soon add a `src/test/` tree alongside `src/main/` for JUnit. Given what you now know about the classpath, predict how the test command will need to differ from the production-code command. (Module 2.1 will confirm.)

This is the last submodule of Module 1. From here, you have packages, the classpath, and a real build command — enough to bring JUnit into the picture and start verifying every DIY with tests instead of `main`.
