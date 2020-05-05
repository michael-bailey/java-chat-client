# Protocol
here we will define the protocol used between the client and the server.

## getting info from the server
this set of protocols will define how data is retrieved from the server

any initial request to the server will make it send ?Request:

### !Status:
this will retrieve the status from the server including
* server name
* server ip
* server UUID
possibly
* owner name
* owner email


## !Joining
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

