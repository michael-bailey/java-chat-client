# Protocol
For our program we are defining a simple textbased protocol that conveys a command and key value parameters.

for example a connect command would look like this

`!connect: name:michael-bailey`

---

## Regex

To define the protocol syntax we have creates a regex that seperates a whole string into parts

`(\?|\!)([a-zA-z0-9]*)\:|([a-zA-z]*):([a-zA-Z0-9\-\+\[\]{}\_\=\/]+|(\"(.*?)\")+)`

---

## Control words

this class defines the control words

each control word is defiend as a static string so it is easy to import into and use

*extracted from PROTOCOL_MESSAGES.java*

```java
package client.enums;

import java.util.regex.Pattern;

public class PROTOCOL_MESSAGES {
    public final static String REQUEST = "?request:";
    public final static String INFO = "?info!";

    // results
    public final static String SUCCESS = "!success:";
    public final static String ERROR = "!error:";

    // connect commands
    public final static String CONNECT = "!connect:";
    public final static String DISCONNECT = "!disconnect:";

    // server commands
    public final static String UPDATE_CLIENTS = "!clientUpdate:";
    public final static String CLIENT = "!client:";
    public final static String TEST = "!test:";

    // ptp commands
    public final static String MESSAGE = "!message:";
}
```



## getting info from the server
this set of protocols will define how data is retrieved from the server

any initial request to the server will make it send ?Request:

### !encrypt:
this keyword request the server to start a key exchange the new keys will be used to encrypt further messages.

### !info:
this will retrieve server info.
* server name
* server MOTD

possibly
* owner name
* owner email


## !Join:
this will tell the server to use this socket as a client
will require sending
* username
* user UUID
* user ip address

posibly
* public key

server will return:
* !Success:
* !Error:

## !Leave:
this will tell the server the client is leaving.
no data is required because the server will base the request on the current client object.

## interacting with clients