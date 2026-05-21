# Java: Beginner to Pro — Course Outline

> **Target version:** Java 17 (LTS)
> **Audience:** Working backend engineer comfortable with Java syntax/semantics, but wants deep fluency in OOP, the standard library, concurrency, design patterns, and testing — without leaning on generative AI to write code.
> **Scope:** Core Java + JUnit 5 / Mockito testing. No frameworks (Spring), no build tools deep-dive.
> **How to use this outline:** Each section ends with a **DIY Exercise** — a small, self-contained coding task to cement the concepts. Don't skip them. After the main sections, an **Extended Practice** set offers harder, multi-concept problems.

> **Why testing comes early (Module 2):** Once Module 1 lands packages and the classpath, every later DIY can be verified with a real JUnit test instead of an ad-hoc `main`. Testing is taught right after the foundations so you can use it for the rest of the course.

---

## Module 1 — Object-Oriented Programming Foundations

### 1.1 Classes, Objects, and Encapsulation
- Fields, methods, constructors, `this`
- Access modifiers (`public`, `protected`, package-private, `private`) and what each one actually protects you from
- Encapsulation as an invariant-protection tool, not just "make fields private"
- Static vs. instance members; when `static` is the right answer and when it's a smell
- **DIY Exercise:** Build a `BankAccount` class that enforces non-negative balance, supports deposit/withdraw, and exposes only the operations needed — no public setters.

### 1.2 Inheritance, Polymorphism, and `Object`
- `extends`, method overriding, `super`, constructor chaining
- Dynamic dispatch and the difference between compile-time and runtime types
- The `Object` contract: `equals`, `hashCode`, `toString`, `clone` — and why misimplementing `equals`/`hashCode` breaks `HashMap`
- `final` classes and methods; when to forbid extension
- **DIY Exercise:** Model a small `Shape` hierarchy (`Circle`, `Rectangle`, `Triangle`) with correct `equals`/`hashCode`/`toString`. Drop instances into a `HashSet` and prove deduplication works.

### 1.3 Abstract Classes and Interfaces
- Abstract classes vs. interfaces — capability vs. identity
- Default and static methods on interfaces (Java 8+) and the diamond problem
- Marker interfaces, functional interfaces (preview for Module 5)
- **DIY Exercise:** Define a `Repository<T, ID>` interface with default `findOrThrow` and `existsById` methods built on top of two abstract methods (`findById`, `save`). Implement an in-memory version.

### 1.4 Composition vs. Inheritance
- "Favor composition over inheritance" — what it actually means and when inheritance still wins
- The fragile base class problem
- Delegation patterns
- **DIY Exercise:** Refactor a `LoggingArrayList extends ArrayList` design (which silently breaks because `addAll` calls `add`) into a composition-based `LoggingList` wrapper that logs every public operation correctly.

### 1.5 Records, Sealed Classes, and Modern Modeling (Java 17)
- `record` for immutable data carriers; compact constructors and validation
- `sealed` / `non-sealed` / `permits` — controlled hierarchies for ADT-like modeling
- Pattern matching for `instanceof`
- Switch expressions (returning a value, exhaustiveness on sealed types) and pattern matching for `switch` (preview in 17, finalized in 21 — know what's stable)
- **DIY Exercise:** Model a `Result<T>` type as a sealed interface with `Success<T>` and `Failure` records. Write a method that uses pattern matching in a `switch` expression to unwrap or rethrow — no `if`/`instanceof` chains.

### 1.6 Enums in Depth
- Enums as full classes: fields, constructors, methods
- Constant-specific method bodies (per-constant overrides)
- `EnumSet` and `EnumMap` — why these exist and when they crush `HashSet`/`HashMap` on tiny key spaces
- Strategy-via-enum: encoding behavior on the constant itself
- Enum singletons revisited (preview for 7.1)
- **DIY Exercise:** Model `Operation` as an enum with `PLUS`, `MINUS`, `TIMES`, `DIVIDE`, each implementing an `apply(double, double)` method on the constant itself. Then write a parser that maps strings to operations using an `EnumMap`.

### 1.7 Nested, Inner, and Anonymous Classes
- Static nested classes vs. inner classes — what the implicit outer reference costs you
- Local classes; when they're useful
- Anonymous classes vs. lambdas — what each can do that the other can't (state, multiple methods, `this`)
- Real-world sightings: `Map.Entry`, builder patterns with mutable state, custom iterators
- **DIY Exercise:** Implement an `Iterable<Integer>` `RangeIterable(int start, int endExclusive)` whose `iterator()` returns an anonymous inner class. Then rewrite the iterator as a static nested class and explain why the static version is generally preferable.

### 1.8 Packages, Imports, and the Classpath
- The `package` declaration and the directory-must-match-package rule
- Legal package identifiers (and why the course's lesson directories aren't)
- `import`, `import static`, wildcard imports — and what the compiler actually does with them
- Fully qualified names vs. imports; name clashes
- The classpath: `javac -d out`, `java -cp out pkg.Main`, building multi-file programs
- Single-file source mode (`java Foo.java`) — what it skips and when to outgrow it
- Preview of the module path (covered in depth in 8.3)
- **DIY Exercise:** Take a previous single-file DIY (e.g., `BankAccount`) and split it into a multi-class program across two packages — one for the domain class, one for a small `Main` that uses it. Compile to an `out/main/` directory and run it from the classpath. Then deliberately break it (wrong directory for the package, missing import) and read the compiler errors. **This DIY is also the gateway to Module 2: from here on, exercises live in the repo's `src/main/java/` source tree and are verifiable with JUnit.**

---

## Module 2 — Testing Fundamentals

> Placed early so every later DIY can be verified with a real test. (Concurrent-test techniques live with concurrency in 6.5.)

### 2.1 JUnit 5 Fundamentals
- Test lifecycle: `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`
- Assertions: `assertEquals`, `assertThrows`, `assertAll`
- Parameterized tests: `@ParameterizedTest`, `@ValueSource`, `@MethodSource`, `@CsvSource`
- Nested tests, display names, tags
- Project layout for tests: `src/main/java` vs. `src/test/java`, compiling and running with `junit-platform-console-launcher` from the classpath (no Maven/Gradle required)
- **DIY Exercise:** Write a parameterized test suite for a `RomanNumeralConverter` covering 20 inputs from `@CsvSource`, plus error cases via `assertThrows`. Then go back and add a JUnit test for the Module 1 `BankAccount` DIY that pins down the non-negative-balance invariant.

### 2.2 Mockito and Test Doubles
- Stubs, mocks, spies, fakes — the differences
- `when(...).thenReturn(...)`, `verify`, argument captors
- Mocking statics and finals (and why you usually shouldn't need to)
- **DIY Exercise:** Test a `CheckoutService` that depends on `InventoryClient`, `PaymentGateway`, and `OrderRepository`. Mock all three. Verify the right calls happen on the happy path and that nothing is charged when inventory is short.

---

## Module 3 — Beyond OOP Basics: Idiomatic Java

### 3.1 Exceptions and Error Handling
- Checked vs. unchecked — the philosophy and the criticism
- `try-with-resources` and `AutoCloseable`
- Exception chaining; preserving the cause
- Anti-patterns: swallowing exceptions, throwing `Exception`, exceptions as control flow
- **DIY Exercise:** Implement a `FileLineCounter` that uses try-with-resources, wraps low-level `IOException` in a domain `CountingException`, and never leaks file handles even on partial reads.

### 3.2 Generics
- Type parameters on classes and methods
- Bounded types: `<T extends Comparable<T>>`
- Wildcards: `? extends`, `? super`, and the PECS rule (Producer Extends, Consumer Super)
- Type erasure and what it forbids (no `new T[]`, no `instanceof T`)
- **DIY Exercise:** Write a generic `Pair<A, B>` and a `Pairs.zip(List<A>, List<B>)` utility. Then write `copy(List<? extends T> src, List<? super T> dst)` and articulate why each wildcard is needed.

### 3.3 The Collections Framework
- `List`, `Set`, `Map`, `Queue`, `Deque` — pick-the-right-one decision tree
- `ArrayList` vs. `LinkedList`, `HashMap` vs. `TreeMap` vs. `LinkedHashMap`
- Immutable collections (`List.of`, `Map.of`, `Collections.unmodifiableList`)
- Iteration pitfalls: `ConcurrentModificationException`, fail-fast vs. fail-safe
- **DIY Exercise:** Build a `LRUCache<K, V>` on top of `LinkedHashMap` with a fixed capacity that evicts the oldest accessed entry.

### 3.4 Equality, Comparability, and Ordering
- `equals`/`hashCode` contract revisited with collections in mind
- `Comparable` vs. `Comparator`; `Comparator.comparing`, `thenComparing`, `reversed`
- Natural ordering vs. total ordering
- **DIY Exercise:** Sort a `List<Employee>` by department ascending, then salary descending, then name — once with a chained `Comparator`, once by implementing `Comparable<Employee>`. Discuss which approach belongs where.

### 3.5 Modern Syntax You Should Be Reaching For
- `var` — when it improves readability and when it hides intent
- Text blocks (`"""`) for multi-line strings, JSON literals, SQL
- Switch expressions as a default over switch statements
- **DIY Exercise:** Take a 60-line method that builds a multi-line SQL string with `+` concatenation, uses verbose generic types, and has a fall-through `switch` statement. Rewrite it using text blocks, `var` where appropriate, and a switch expression. Justify each `var` you kept.

---

## Module 4 — The Standard Library You'll Actually Use

### 4.1 Strings, Numbers, and Dates
- `String` immutability, `StringBuilder`, `String.format` vs. concatenation
- `BigDecimal` vs. `double` — and why money is never a `double`
- `java.time`: `Instant`, `LocalDate`, `LocalDateTime`, `ZonedDateTime`, `Duration`, `Period`
- **DIY Exercise:** Write a billing utility that parses ISO-8601 timestamps, computes invoice age in business days, and totals line items in `BigDecimal` with banker's rounding.

### 4.2 I/O and NIO.2
- `java.io` streams vs. `java.nio.file` (`Path`, `Files`)
- Reading/writing text and binary; charsets
- Walking a directory tree with `Files.walk`
- **DIY Exercise:** Build a `du`-style utility that prints the on-disk size of every directory in a tree, sorted largest-first.

### 4.3 Optional and Null Safety
- `Optional` as a return type, not a field or parameter
- `map`, `flatMap`, `orElseGet` vs. `orElse`, `ifPresent`
- The anti-patterns: `Optional.get` without `isPresent`, `Optional<List<T>>`
- **DIY Exercise:** Refactor a chain of nested null checks (`user.getAddress().getCity().getZip()`) into a single `Optional` chain that returns a default zip on any null link.

### 4.4 JSON and Serialization
- Why `java.io.Serializable` is dangerous (security, versioning, invariant bypass) — and what to use instead
- Jackson basics: `ObjectMapper`, `@JsonProperty`, `@JsonCreator`, `@JsonIgnore`
- Polymorphic deserialization (`@JsonTypeInfo`, `@JsonSubTypes`) and why it's a security footgun
- Records and Jackson; immutability-friendly deserialization
- Streaming JSON with `JsonParser` for large payloads
- **DIY Exercise:** Define a sealed `Event` hierarchy (`UserCreated`, `UserDeleted`, `OrderPlaced`) as records. Configure Jackson to round-trip a `List<Event>` through JSON with a discriminator field. Add a parameterized test asserting every subtype round-trips losslessly.

### 4.5 Logging
- The facade pattern in practice: SLF4J as the API, Logback / Log4j 2 as the impl, why bindings matter
- Parameterized logging (`log.info("user {} did {}", userId, action)`) vs. string concatenation — performance and safety
- Log levels and what each one means in a production system
- MDC (Mapped Diagnostic Context) for request-scoped fields like `trackingId`, `propertyId`
- Structured logging — why ops teams care
- **DIY Exercise:** Wire SLF4J + Logback into a small app. Add an MDC entry per request in a fake "controller", emit two log lines, and confirm the MDC value appears on both. Then misuse it (forget to clear MDC on thread reuse) and observe the bug.

### 4.6 HTTP with `java.net.http.HttpClient`
- Synchronous and asynchronous API; `HttpClient`, `HttpRequest`, `HttpResponse`
- `BodyHandlers` for strings, byte arrays, files, streaming
- `CompletableFuture<HttpResponse<T>>` for async pipelines (preview for 6.3)
- Timeouts, redirects, connection reuse
- When to reach for OkHttp / Apache HttpClient instead
- **DIY Exercise:** Build a `WeatherClient` that fetches from a public JSON API, parses the response with Jackson, and exposes both blocking and `CompletableFuture` methods. Add a 2-second total timeout that fails the future cleanly.

---

## Module 5 — Functional Java

### 5.1 Lambdas and Functional Interfaces
- Lambda syntax, target typing, capturing variables (effectively final)
- The core functional interfaces: `Function`, `Predicate`, `Consumer`, `Supplier`, `BiFunction`
- Method references: `Class::method`, `instance::method`, `Class::new`
- **DIY Exercise:** Implement a `Pipeline<T>` class that chains `Function<T, T>` steps and runs them in order. Compose three text-transform steps (trim, lowercase, collapse whitespace) using only method references.

### 5.2 The Streams API
- Sources, intermediate ops (`map`, `filter`, `flatMap`), terminal ops (`collect`, `reduce`, `forEach`)
- `Collectors`: `toList`, `toMap`, `groupingBy`, `partitioningBy`, downstream collectors
- Lazy evaluation and short-circuiting
- When **not** to use streams (debuggability, hot loops, side effects)
- **DIY Exercise:** Given a `List<Order>`, produce a `Map<Customer, BigDecimal>` of total spend per customer using `groupingBy` and a downstream `reducing` collector — no explicit loops.

### 5.3 Parallel Streams (and Their Traps)
- `parallelStream()` mechanics; the common ForkJoinPool
- When parallelism helps and when it makes things slower
- Stateful operations and ordering hazards
- **DIY Exercise:** Compare sequential vs. parallel stream throughput on a CPU-bound task (e.g., prime counting in `[1, 10_000_000]`). Document where the crossover is on your machine.

---

## Module 6 — Concurrency

### 6.1 Threads, Runnables, and the Memory Model
- `Thread`, `Runnable`, `Callable`, `Future`
- The Java Memory Model in plain language: visibility, ordering, happens-before
- `volatile`, `synchronized`, intrinsic locks
- Why `i++` isn't atomic
- **DIY Exercise:** Write a producer/consumer with a single shared counter. First demonstrate the race condition with no synchronization, then fix it with `synchronized`, then with `AtomicInteger`. Measure throughput differences.

### 6.2 The `java.util.concurrent` Toolbox
- `ExecutorService`, `ThreadPoolExecutor`, `ScheduledExecutorService`
- `BlockingQueue`, `ConcurrentHashMap`, `CopyOnWriteArrayList`
- `Atomic*` classes
- Locks: `ReentrantLock`, `ReadWriteLock`, `StampedLock`
- `CountDownLatch`, `CyclicBarrier`, `Semaphore`, `Phaser`
- **DIY Exercise:** Build a bounded thread-safe `WorkQueue` with a fixed worker pool. Submitting work blocks if the queue is full. Shutting down drains in-flight work cleanly.

### 6.3 CompletableFuture and Async Composition
- `supplyAsync`, `thenApply`, `thenCompose`, `thenCombine`
- `allOf`, `anyOf`
- Exception handling: `exceptionally`, `handle`, `whenComplete`
- Choosing executors; the default ForkJoinPool trap
- **DIY Exercise:** Implement a `fanOutThenAggregate` that calls three "remote" services (mock with `Thread.sleep`) in parallel and aggregates the results — with a 500ms total timeout and graceful fallbacks per service.

### 6.4 Concurrency Pitfalls and Patterns
- Deadlock, livelock, starvation; lock ordering
- Thread confinement, immutability, and stack confinement
- Double-checked locking — when it's correct in modern Java and when it's still wrong
- **DIY Exercise:** Reproduce a deadlock between two locks acquired in opposite orders by two threads. Then fix it with consistent lock ordering. Then fix the same problem with `tryLock` and a backoff.

### 6.5 Testing Concurrent Code
- Reproducibility challenges; deterministic vs. probabilistic tests
- `CountDownLatch` in tests; `Awaitility` for polling assertions
- Avoiding `Thread.sleep` as a synchronization tool
- **DIY Exercise:** Write a test for the `WorkQueue` you built in 6.2 that proves: (a) submitted work runs, (b) submission blocks when full, (c) `shutdown()` drains in-flight work.

---

## Module 7 — Design Patterns in Idiomatic Java

> Patterns are taught in the order they tend to compose, not alphabetically. Each is paired with a real Java-stdlib example so you recognize it in the wild.

### 7.1 Creational Patterns
- **Singleton** — enum-based, lazy holder idiom; why most "singletons" should just be DI-managed
- **Factory Method** and **Static Factory Methods** — `List.of`, `Optional.of`
- **Builder** — `StringBuilder`, `Stream.Builder`; fluent vs. step builder
- **Prototype** — and why `clone()` is a footgun
- **DIY Exercise:** Convert a 7-argument `HttpRequest` constructor into a fluent `Builder` with required-vs-optional parameters enforced at compile time using a step builder.

### 7.2 Structural Patterns
- **Adapter** — `Arrays.asList`, `InputStreamReader`
- **Decorator** — `BufferedInputStream`, `Collections.unmodifiableList`
- **Composite** — DOM-like trees
- **Facade** — high-level wrappers around messy subsystems
- **Proxy** — `java.lang.reflect.Proxy`, dynamic proxies
- **DIY Exercise:** Build a `Notifier` interface with a base `EmailNotifier`, then layer `RetryingNotifier` and `RateLimitedNotifier` decorators. Verify each works alone and composed.

### 7.3 Behavioral Patterns
- **Strategy** — passed via lambdas, e.g., `Comparator`
- **Observer** — `PropertyChangeListener`; why not to roll your own
- **Template Method** — `AbstractList`, `HttpServlet`
- **Command** — `Runnable`, `Callable`
- **State** — finite state machines; tip-state lifecycle as a real example
- **Iterator** — `Iterable`, `Iterator`, custom iteration
- **Chain of Responsibility** — servlet filter chains
- **DIY Exercise:** Model an order's lifecycle (`NEW` → `PAID` → `SHIPPED` → `DELIVERED`, with `CANCELLED` exits) as a State pattern. Invalid transitions throw. Add an Observer that logs every transition.

### 7.4 Anti-Patterns and "Pattern Smell"
- The God Object, the Anemic Domain Model, the Singleton-as-global-state
- Over-engineering: when a pattern is overkill
- **DIY Exercise:** Take a deliberately over-engineered 6-class "Strategy + Factory + Singleton" implementation of "add two numbers" and reduce it to the smallest sensible code. Justify what you removed.

---

## Module 8 — Annotations, Reflection, and the Module System

> This module is the doorway to understanding "magic" libraries — Spring, JPA, Jackson, JUnit, Mockito. They all run on the machinery here. After this module, framework behavior should stop being mysterious.

### 8.1 Annotations
- Built-in annotations (`@Override`, `@Deprecated`, `@SuppressWarnings`, `@FunctionalInterface`)
- Defining your own: `@Retention`, `@Target`, `@Repeatable`, `@Inherited`
- Source / class / runtime retention — what each is good for
- Meta-annotations and composition (e.g., how Spring builds `@RestController` on top of `@Controller` + `@ResponseBody`)
- **DIY Exercise:** Define a `@Validated` annotation on parameters and a `@NotBlank`/`@Range(min, max)` pair. The annotations alone do nothing — that's expected. You'll wire them up in 8.2.

### 8.2 Reflection
- `Class<?>`, `Method`, `Field`, `Constructor` — the runtime model
- Reading annotations at runtime; `getDeclaredAnnotations`, `isAnnotationPresent`
- Invoking methods and reading/writing fields reflectively; `setAccessible` and what it costs
- Dynamic proxies (`java.lang.reflect.Proxy`) — how AOP-style libraries actually work
- Performance and safety costs; when reflection is the right tool and when it's a smell
- **DIY Exercise:** Write a tiny validation runner that takes any object, scans its fields for `@NotBlank` / `@Range` (from 8.1), and returns a list of violations. Then write a method-call interceptor using `Proxy` that logs every method invocation on an interface implementation.

### 8.3 The Java Platform Module System (JPMS)
- `module-info.java`: `requires`, `exports`, `opens`
- The strong encapsulation rules in Java 9+ and why reflective access into `java.base` requires `--add-opens`
- Why frameworks (Spring, Jackson) sometimes need module config
- The classpath vs. the module path — how mixed projects work in practice
- When you should bother with JPMS and when the classpath is fine
- **DIY Exercise:** Take a small two-package project, give it a `module-info.java` that exports only one package, and confirm that consumers can't reach the internal package. Then break it deliberately by reflecting into the internal package — observe the error and fix it with `opens`.

### 8.4 Class Loading
- The class loader hierarchy (bootstrap, platform, application)
- Loading classes at runtime; `Class.forName`, `ClassLoader.loadClass`
- Isolated class loaders (the basis of plugin systems and app servers)
- The `ServiceLoader` mechanism — `META-INF/services` and how JDBC drivers get found
- **DIY Exercise:** Write a tiny plugin host. Define a `Greeter` interface in your main module. Drop two JARs into a `plugins/` directory, each providing a `Greeter` implementation registered via `ServiceLoader`. Load them at runtime and invoke each.

---

## Module 9 — JVM and Performance Awareness

### 9.1 How the JVM Runs Your Code
- Class loading, bytecode, JIT compilation (C1, C2)
- Garbage collection in plain language: generations, G1 vs. ZGC defaults in Java 17
- Heap vs. stack; escape analysis
- **DIY Exercise:** Write a small program that allocates 10M short-lived objects in a loop. Run it with `-verbose:gc` and read the GC log. Then add `-Xmx128m` and observe what changes.

### 9.2 Profiling and Diagnostics
- `jps`, `jstack`, `jmap`, `jcmd`
- Thread dumps and how to read them
- JFR (Java Flight Recorder) basics
- **DIY Exercise:** Take a deliberately deadlocked program, capture a thread dump with `jstack`, and identify the locking cycle from the output alone.

### 9.3 Common Performance Pitfalls
- Unnecessary boxing, string concatenation in hot loops
- Over-synchronization
- Memory leaks via static collections, listeners not unregistered, `ThreadLocal` misuse
- **DIY Exercise:** Given a piece of suspiciously slow code, find the hot path with a profiler (or `System.nanoTime`) and produce a 10x speedup. Document the change and why it worked.

---

## Extended Practice — Multi-Concept Challenges

These are larger than the per-section DIY exercises and intentionally cross module boundaries. Each should take an evening to a weekend.

1. **Thread-safe in-memory key-value store with TTL**
   Touches: concurrency, generics, collections, design patterns (Strategy for eviction policies), testing.

2. **Mini event bus**
   Subscribe/publish with typed events, sync and async dispatch modes, error isolation per subscriber.
   Touches: Observer, generics, `CompletableFuture`, exception handling, testing concurrent code.

3. **Streaming log parser**
   Parse a multi-GB log file without loading it all into memory. Filter, group, and aggregate by request ID.
   Touches: NIO.2, streams, collectors, performance awareness.

4. **Rate limiter library**
   Implement token bucket and sliding window strategies behind a common interface. Thread-safe, testable, configurable.
   Touches: Strategy, concurrency primitives, JUnit parameterized tests, time abstraction for testability.

5. **Plugin loader**
   Discover and load implementations of an interface from a directory of JARs at runtime.
   Touches: classloaders, reflection, `ServiceLoader`, exception handling.

6. **Retry + circuit breaker decorator**
   Wrap any `Supplier<T>` / `Callable<T>` with retry-with-backoff and a circuit breaker. Composable and observable.
   Touches: Decorator, State pattern (circuit states), `CompletableFuture`, testing time-dependent behavior.

7. **Concurrent web crawler (bounded)**
   Crawl up to N pages from a seed URL with a configurable concurrency limit, polite delays, and dedup.
   Touches: thread pools, `ConcurrentHashMap`, `Semaphore`, graceful shutdown, testing with mocks (extra credit: use `java.net.http.HttpClient` for the actual fetching).

8. **Tiny dependency-injection container**
   A `@Inject`-style annotation, a registry of singletons, and constructor injection resolved by reflection. Detect cycles and report them clearly.
   Touches: annotations, reflection, generics, exception handling.

9. **Annotation-driven REST router (no framework)**
   Define `@Get("/path")` and `@Post("/path")` annotations on methods. At startup, scan a class for annotated methods and build a routing table; dispatch incoming requests by matching path + verb. Run it on `com.sun.net.httpserver.HttpServer` or `java.net.http`.
   Touches: annotations, reflection, dynamic proxies (optional), HTTP, JSON.

10. **Schema-aware JSON serializer**
    Build a minimal Jackson-alike: traverse an object's fields via reflection, honor a custom `@JsonName` and `@JsonIgnore`, support records and primitives, emit JSON to a `Writer`. Then add a deserializer that uses a record's canonical constructor.
    Touches: reflection, records, generics, I/O, error handling.

11. **Structured logging adapter with MDC propagation**
    Wrap SLF4J with a `StructuredLogger` API that emits JSON lines (timestamp, level, message, MDC, custom fields). Then write a helper that propagates MDC across `CompletableFuture` boundaries — by default it doesn't.
    Touches: logging, decorators, `CompletableFuture`, threading.

12. **Service loader–based feature-flag client**
    Define a `FeatureFlagProvider` SPI. Ship two implementations (in-memory + file-backed) discoverable via `ServiceLoader`. The host app picks one at startup based on a system property and falls back gracefully if none are present.
    Touches: `ServiceLoader`, class loading, design patterns (Strategy, Decorator), JPMS (optional stretch).

13. **Plugin sandbox with isolated class loaders**
    Extend the plugin loader (#5) so each plugin runs in its own `URLClassLoader` and can be unloaded at runtime by dropping references. Prove unloading actually frees memory using `jmap`.
    Touches: class loaders, JPMS, reflection, JVM diagnostics.

---

## Suggested Pacing

| Phase | Modules | Realistic time |
|---|---|---|
| OOP foundations | 1 | 2 weeks |
| Testing | 2 | 1 week |
| Idiomatic Java + standard library | 3–4 | 2–3 weeks |
| Functional | 5 | 1 week |
| Concurrency | 6 | 2 weeks |
| Patterns | 7 | 1 week |
| Annotations, reflection, JPMS | 8 | 1–2 weeks |
| JVM awareness | 9 | 1 week |
| Extended practice | pick 4–6 | 3–4 weeks |

Total: ~3 months at evenings-and-weekends pace. The point isn't speed — it's that by the end, you can sit down at a blank file and write idiomatic Java without reaching for a model.
