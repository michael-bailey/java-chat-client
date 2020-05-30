# java-chat-client

![Status_Image](https://github.com/michael-bailey/java-chat-client/workflows/Java%20CI/badge.svg)

This is a chat client written in java using the javafx ui library.

This uses sockets to transfer data between clients.

it will send messages directly to the other user using end to end encryption using pubic- private key cryptography.

messages are stored locally on a file which is encrypted.

later on the messages will be sent via servers due to limitations with other operating systems.

that will only store details about where messages 
are to be sent, and permissions for the user on the server

---

## Dependencies

* java 13 - the jdk we are using.

* JavaFx - used for the User interface. (installed via cradle)

* gradle - the build system we are using. (used with a wrapper)

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

[Network Protocol](https://michael-bailey.github.io/java-chat-client/Protocol)

**this Documentation is work in progress (feel free to critisise constructively)**

#Owners
[michael-bailey](https://github.com/michael-bailey/)
[mitch161](https://github.com/mitch161/)
