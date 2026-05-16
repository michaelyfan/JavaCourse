# 1.1 Classes, Objects, and Encapsulation

A class is a blueprint; an object is an instance with its own state. Java's job in this submodule is to make sure that state is *only* manipulated through code paths you have approved. That is what encapsulation actually buys you — not aesthetics, not "best practice," but the ability to *guarantee invariants* about your object no matter who calls it.

---

## Fields, methods, constructors, `this`

A class typically declares:

- **Fields** — the state each instance carries.
- **Methods** — the operations defined on that state.
- **Constructors** — code that runs once when an object is created, responsible for putting the new object into a *valid* initial state.

```java
public final class Temperature {
    private final double celsius;

    public Temperature(double celsius) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException("below absolute zero: " + celsius);
        }
        this.celsius = celsius;
    }

    public double toFahrenheit() {
        return celsius * 9.0 / 5.0 + 32.0;
    }
}
```

A few things worth noticing:

- The constructor *rejects* invalid input. Once a `Temperature` exists, you know it represents a physically possible temperature. Code downstream never needs to re-check.
- `this.celsius = celsius` uses `this` to disambiguate the field from the parameter of the same name. `this` is the implicit reference to the current instance available inside every non-static method and constructor.
- The field is `final`, so it cannot be reassigned after the constructor finishes. Combined with the validation above, the invariant "celsius ≥ −273.15" holds for the entire lifetime of every instance. This is what encapsulation *for*.

### Constructor chaining with `this(...)`

A constructor can delegate to another constructor in the same class via `this(...)`. The delegation must be the first statement. This is the standard way to express "convenience constructor that fills in defaults":

```java
public Temperature() {
    this(0.0); // freezing point of water
}
```

Avoid duplicating validation logic across constructors — funnel them through one "primary" constructor that does the real work.

---

## Access modifiers — what each one *actually* protects you from

Java has four access levels. The intuition "private = hidden, public = exposed" is correct but shallow. The useful framing is: *who can break my invariants if I choose this modifier?*

| Modifier | Visible to | What it costs you to widen |
|---|---|---|
| `private` | Same class (and its nested classes) | Nothing — you control all callers. |
| *(package-private, no modifier)* | Same package | Anyone who can add a class to your package can poke at it. In most projects this is "your team." In a library, packages cross trust boundaries. |
| `protected` | Same package **plus** subclasses anywhere | Subclasses you've never seen can read/write. Inheritance becomes part of your public API. |
| `public` | Everyone | Once shipped, you cannot change the signature without breaking callers. |

Three rules worth internalizing:

1. **Default to `private` for fields.** A public field is a contract that the value can be anything of that type, at any time, with no notification — i.e., no invariant.
2. **`protected` is not "a little more private than public."** It exposes you to subclass authors, who are often more dangerous than ordinary callers because they can also override methods you call internally (see "fragile base class" in 1.4).
3. **Package-private is the most undersold modifier.** If a class is only meaningful inside one package, leaving it package-private keeps it out of your public API surface. You can refactor it freely later.

---

## Encapsulation as invariant protection

The cliché is "make fields private and add getters and setters." That advice is half wrong. If every private field has a public getter *and* a public setter, you have re-exposed the field — you just made the syntax slightly uglier. You have not encapsulated anything.

Real encapsulation means: **decide what invariants your object must always satisfy, then only expose operations that preserve those invariants.**

Concretely, this often means:

- No public setters when the value is part of an invariant. Update happens through an operation (`deposit`, `withdraw`, `markPaid`) that knows the rules.
- Return immutable views from getters when the field is a mutable collection (`List.copyOf(items)` or `Collections.unmodifiableList(items)`), so callers can't mutate your internals through the reference you handed them.
- Validate at construction. An object that exists is, by construction, in a valid state.

The `BankAccount` DIY exercise at the end of this section is built around exactly this principle: there is no `setBalance`, because no caller has any business setting an arbitrary balance. There are `deposit` and `withdraw` methods, and both enforce non-negative balance.

---

## Static vs. instance members

A member declared `static` belongs to the *class*, not to any particular instance. There is exactly one copy, and it is accessible without an instance.

```java
public final class Temperature {
    public static final double ABSOLUTE_ZERO_C = -273.15; // class-level constant

    public static Temperature fromFahrenheit(double f) {  // static factory
        return new Temperature((f - 32.0) * 5.0 / 9.0);
    }

    private final double celsius;
    // ...
}
```

`static` is the right answer when:

- **Constants** (`public static final`) — the value is the same for everyone.
- **Static factory methods** — `Temperature.fromFahrenheit(...)`, `List.of(...)`, `Optional.empty()`. These give you a named, validated way to build instances, and they can return a cached value, a subtype, or even `null` semantics (`Optional.empty()` returns a singleton). They're often nicer than constructors. More in Module 6.
- **Pure utility functions** that genuinely have no per-instance state (`Math.max`, `Collections.sort`).

`static` is a *smell* when:

- It hides **mutable global state** — `static` fields that get reassigned are shared across the entire JVM, surviving every request, every test, every thread. They are a leading cause of test flakiness and concurrency bugs.
- It's being used to **avoid passing dependencies**. A static method call is invisible to the caller's signature, so it becomes impossible to substitute (e.g., for testing) without resorting to mocking frameworks that patch static calls.
- It's a **"manager" or "helper" class with only static methods** that really should have been an object you pass around. Static code is hard to compose, hard to test, and hard to evolve.

The rule of thumb: `static` for things that are genuinely per-class (constants, factories, pure functions), instance for things that have or operate on state. When in doubt, prefer instance — it keeps your options open.

---

## Constructor patterns and common mistakes

A few things that bite people:

- **Doing real work in a constructor.** Constructors should put the object into a valid state, not perform I/O, start threads, or call overridable methods. Calling an overridable method from a constructor is a classic bug: the subclass's override runs before the subclass's own fields are initialized.
- **Forgetting that fields are zero-initialized.** Before your constructor runs, every field is `0`, `false`, or `null`. If the constructor throws after partially initializing, you must not let a half-built object escape (don't store `this` in a static collection mid-construction, don't pass `this` to another object before construction completes).
- **Telescoping constructors.** Five overloads of the same constructor with different parameter counts is hard to read and hard to call correctly. When you reach that point, reach for the Builder pattern (Module 6) or `record` (1.5).

---

## DIY Exercise

Build a `BankAccount` class that:

- Enforces non-negative balance at all times.
- Supports `deposit(BigDecimal amount)` and `withdraw(BigDecimal amount)`.
- Exposes a `balance()` reader but **no public setter** for the balance.
- Rejects negative or zero amounts on deposit/withdraw with a clear exception.
- Rejects construction with a negative initial balance.

Things to think about while you write it:

- What field types? (Hint: `double` is wrong for money — preview of 3.1.)
- Should the balance field be `final`? Why or why not?
- What happens if two threads call `withdraw` simultaneously? (We're not solving this here — but notice the question. It returns in Module 5.)
- Could you write a test that proves the invariant "balance is never negative" holds across *any* sequence of valid operations?
