# SpringDemoTodo Project Overview

This file is a long-term reference for understanding the project quickly, even if you come back to it later after forgetting the details.

## 1. What This Project Is

This project is a small Spring Boot REST API for managing todos.

Right now, it is an early-stage demo application with:

- one main API endpoint
- a controller, service, repository, and model layer
- in-memory sample data instead of a real database
- minimal automated testing

The current endpoint is:

- `GET /api/v1/todos`

It returns a list of todo items as JSON.

## 2. Tech Stack

From `build.gradle`, the project uses:

- Java 21
- Spring Boot 4.0.5
- Spring dependency management plugin
- Spring Web MVC
- Lombok
- Spring Boot DevTools
- dotenv-java
- JUnit / Spring Boot test support

## 3. Project Structure

Main files:

- `src/main/java/com/example/demo/TodoAppApplication.java`
- `src/main/java/com/example/demo/controllers/TodoController.java`
- `src/main/java/com/example/demo/services/TodoService.java`
- `src/main/java/com/example/demo/repositories/TodoRepoistiory.java`
- `src/main/java/com/example/demo/schema/Todo.java`
- `src/main/resources/application.yaml`
- `src/test/java/com/example/demo/DemoApplicationTests.java`

Each file has a specific responsibility:

### `TodoAppApplication.java`

This is the application entry point.

Responsibilities:

- starts the Spring Boot application
- loads environment variables from `.env`
- sets them as system properties before Spring starts

This matters because the app port can be changed through `.env`.

### `TodoController.java`

This is the web layer.

Responsibilities:

- receives HTTP requests
- maps URLs to Java methods
- returns data as JSON

Current behavior:

- handles `GET /api/v1/todos`
- calls the service layer

### `TodoService.java`

This is the business logic layer.

Responsibilities:

- contains application logic
- acts as a middle layer between controller and repository

Current behavior:

- simply forwards the request to the repository

There is no real business logic yet, but this layer is useful for future growth.

### `TodoRepoistiory.java`

This is the data access layer.

Responsibilities:

- provides todo data
- simulates storage

Current behavior:

- stores todos in a Java `List`
- returns all todos through `findAll()`

Important note:

- this is not connected to a real database yet

### `Todo.java`

This is the data model.

Responsibilities:

- defines what a todo looks like

Current fields:

- `id`
- `content`

### `application.yaml`

This is the application configuration file.

Current settings:

- application name is `TodoApp`
- server port is `${PORT:8089}`

This means:

- if `PORT` is provided, that value is used
- otherwise, the app falls back to port `8089`

### `DemoApplicationTests.java`

This is the test file.

Current behavior:

- checks that the Spring application context loads successfully

It does not currently test the actual `/api/v1/todos` endpoint.

## 4. How The Request Flow Works

When the app is running and a client calls:

`GET /api/v1/todos`

the flow is:

1. Spring Boot starts through `TodoAppApplication`.
2. Spring scans the project and creates beans for controller, service, and repository.
3. The request reaches `TodoController`.
4. `TodoController.getAllTodos()` is called.
5. The controller calls `TodoService.getAllTodos()`.
6. The service calls `TodoRepoistiory.findAll()`.
7. The repository returns the in-memory list of todos.
8. Spring converts the Java objects to JSON automatically.
9. The JSON response is sent back to the client.

In simple words:

- controller handles the request
- service handles logic
- repository returns data
- model defines the response structure

## 5. Current API Behavior

The app currently exposes one endpoint:

### `GET /api/v1/todos`

Sample response:

```json
[
  { "id": "1", "content": "Buy groceries" },
  { "id": "2", "content": "Buy groceries" },
  { "id": "3", "content": "Buy groceries" }
]
```

Note:

- all three sample todos currently have the same content
- data is hardcoded in memory
- restarting the app resets the data

## 6. Spring Annotations Used In This Project

This project relies on several important Spring and Lombok annotations.

### `@SpringBootApplication`

Used on the main app class.

Meaning:

- marks the main Spring Boot application
- enables auto-configuration
- enables component scanning

### `@RestController`

Used on `TodoController`.

Meaning:

- this class handles HTTP requests
- return values are written directly as JSON

### `@RequestMapping("/api/v1/todos")`

Used on `TodoController`.

Meaning:

- sets the base URL path for all endpoints in the controller

### `@GetMapping`

Used on `getAllTodos()`.

Meaning:

- maps HTTP `GET` requests to that method

### `@Service`

Used on `TodoService`.

Meaning:

- marks the class as a service bean
- typically used for business logic

### `@Repository`

Used on `TodoRepoistiory`.

Meaning:

- marks the class as a repository bean
- typically used for data access

### `@SpringBootTest`

Used in the test class.

Meaning:

- loads the Spring application context during testing

### Lombok Annotations

Used in the model and service classes.

#### `@Getter`

- generates getter methods

#### `@Setter`

- generates setter methods

#### `@AllArgsConstructor`

- generates a constructor with all fields

## 7. Dependency Injection In This Project

Dependency injection means Spring creates objects for you and passes them into the classes that need them.

In this project:

- `TodoController` depends on `TodoService`
- `TodoService` depends on `TodoRepoistiory`

Dependency chain:

`TodoController -> TodoService -> TodoRepoistiory`

How it works:

- Spring sees `@RestController`, `@Service`, and `@Repository`
- it creates objects for those classes
- it injects dependencies through constructors

Current examples:

- `TodoController` uses an explicit constructor
- `TodoService` uses Lombok `@AllArgsConstructor` to generate the constructor

Why dependency injection is helpful:

- reduces tight coupling
- makes code easier to test
- keeps responsibilities separated
- makes future changes easier

Without Spring, you would manually create objects with `new`.
With Spring, the framework wires them for you.

## 8. Configuration And Port Behavior

This was one of the most important practical points we discussed.

The port is configured in:

- `src/main/resources/application.yaml`

The value is:

- `server.port: ${PORT:8089}`

Meaning:

- use `PORT` if available
- otherwise use `8089`

The app also loads `.env` manually in `TodoAppApplication`.

So if `.env` contains:

```env
PORT=8090
```

then the app will run on `8090`, not `8089`.

That is why this kind of mismatch can happen:

- browser works on one port
- terminal `curl` fails on another port

### Practical Example From This Project

If `.env` contains:

```env
PORT=8090
```

then:

- `http://localhost:8090/api/v1/todos` works
- `http://localhost:8089/api/v1/todos` fails

So always distinguish between:

- default configured port
- actual runtime port

## 9. Why The Earlier `curl` Command Failed

The command:

```bash
curl http://localhost:8089/api/v1/todos
```

failed because the application was actually running on `8090`.

Reason:

- `.env` was overriding the default Spring config

The correct command for that setup was:

```bash
curl http://localhost:8090/api/v1/todos
```

This is a good lesson for future debugging:

- do not rely only on the default config value
- always check if an environment variable overrides it

## 10. How To Run The Project

From the project root:

```bash
./gradlew bootRun
```

This starts the Spring Boot application.

## 11. How To Test The API

Once the app is running, call the endpoint with the correct port.

If the default port is active:

```bash
curl http://localhost:8089/api/v1/todos
```

If `.env` sets `PORT=8090`:

```bash
curl http://localhost:8090/api/v1/todos
```

You can also open the endpoint in a browser.

## 12. How To Run Tests

Run:

```bash
./gradlew test
```

At the moment, the tests only confirm that the Spring application context loads.

## 13. How To Verify The Active Port

Useful commands:

Check `.env`:

```bash
cat .env
```

Check listening ports:

```bash
lsof -nP -iTCP -sTCP:LISTEN | rg '8090|8089|java'
```

Practical rule:

- if `.env` has `PORT`, the app uses that value
- if not, the app uses `8089`

## 14. Current Limitations

This project is still very simple. Current limitations include:

- no database
- no create, update, or delete endpoints
- no validation
- no error handling strategy
- no DTO layer
- no persistence across restarts
- only one basic test
- repository name has a typo: `TodoRepoistiory`

These are normal for a starter demo project.

## 15. Good Next Steps

If you continue building this project, good next steps would be:

- add `POST /api/v1/todos`
- add `GET /api/v1/todos/{id}`
- add update and delete endpoints
- connect to a database
- add proper unit and integration tests
- add validation
- rename `TodoRepoistiory` to `TodoRepository`
- improve startup logging to print the active port clearly

## 16. One-Screen Summary

If you forget everything, remember this:

- this is a Spring Boot todo REST API
- it currently has one read endpoint
- the architecture is controller -> service -> repository
- data is stored in memory, not in a database
- Spring creates and injects the objects automatically
- the port defaults to `8089`
- `.env` can override the port, for example to `8090`
- if browser works but `curl` fails, check the actual running port first

