# Protocol
here we will define the protocol used between the client and the server.

## Regex

this is the regex for pattern matching the protocol.

`(\?|\!)([a-zA-z0-9]*)\:|([a-zA-z]*):([a-zA-Z0-9\-\+\[\]{}\_\=\/]+|(\"(.*?)\")+)`

## getting info from the server
this set of protocols will define how data is retrieved from the server

any initial request to the server will make it send ?Request:

### !encrypt:
this keyword request the server to start a key exchange the new keys will be used to encrypt further messages.

*

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