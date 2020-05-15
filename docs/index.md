# java-chat-client

![Java CI](https://github.com/michael-bailey/java-chat-client/workflows/Java%20CI/badge.svg)

this is a chat client written in java using hte javafx ui library.
this uses a simple sockets to transfer data between clients

it will send messages directly to the other user using end to end encryption using pubic- private key cryptography.

messages are stored locally on a file which is encrypted.

later on the messages will be sent via servers due to limitations with other operating systems.

that will only store details about where messages 
are to be sent, and permissions for the user on the server

## Dependencies

* java 13 - the jdk we are using
* JavaFx - used for the User interface
* gradle - the build system we are using

# other infomation pages.
* ![client to server protocol](./docs/Protocol.md)

## Running

to run this program use the grade wrapper

#### linux

`./gradlew run`

#### windows

`gradlew.bat run`

## images

#### the login window
![UI Login](.github/images/Screenshot 2020-03-27 at 12.34.33.png)



#### the main window with contant
![UI Main](.github/images/Screenshot%202020-03-29%20at%2015.56.16.png)

*this may not reflect the actual interface at all times as it may be changed in the future*

#Owners
[michael-bailey](https://github.com/michael-bailey/)
[mitch161](https://github.com/mitch161/)