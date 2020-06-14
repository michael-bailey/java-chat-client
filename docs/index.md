# Java Chat Client

![Build](https://github.com/michael-bailey/java-chat-client/workflows/Build/badge.svg)

---

This is a chat client written in java using a custom protocol based on key value pairs, paired with javafx as the UI library.

It connects to a server to announce it's presence and get the status and info of other users that are connected

data is stored locally in a file which is encrypted using the java crypto api.

---

## Dependencies

* Java 13

* JavaFx - used for the User interface. (installed via gradle)

  ---


## Execution

to run this program use the grade wrapper

### linux

```sh
# to build the project.
./gradlew build

# to test the build
./gradlew test

# to run via gradle
./gradlew run

# to build a full exectuable jar version
./gradlew shadowjar
```

### windows

```bat
# to build the project.
gradlew.bat build

# to test the build
gradlew.bat test

# to run via gradle
gradlew.bat run

# to build a full exectuable jar version
gradlew.bat shadowjar
```

---

## Documentation Pages

* [Java Docs](https://michael-bailey.github.io/java-chat-client/javadoc/index.html)

* [Protocol](https://michael-bailey.github.io/java-chat-client/Protocol)
* [User Interface](https://michael-bailey.github.io/java-chat-server/interface)
* [Architecture](https://michael-bailey.github.io/java-chat-server/architecture)

**this Documentation is work in progress (feel free to critisise constructively)**

#Owners
[michael-bailey](https://github.com/michael-bailey/javadoc/index.html)
[mitch161](https://github.com/mitch161/)

