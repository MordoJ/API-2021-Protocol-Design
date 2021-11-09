# Specification 
___

## 1. Protocol objectives
The protocol allows the client to request the answer of a mathematical operation, giving two numbers and the operation wanted. .

## 2. Overall behavior
To ensure the communication between the client and the server, we use the TCP/IP protocol. The client accesses the server with the address localhost, and the port number 256. The server speaks first, giving a list of available commands to te client.

When the client doesn't want to use the server's services anymore, he closes the connection by sending a "DONE" command.

[ADD] (addition operation)<br />
Client sends "ADD 14 2 XOXO" -> server responses "RESULT 16 XOXO"


[MULT] (multiplication operation)<br />
Client sends "MULT 14 2 XOXO" -> server responses "RESULT 28 XOXO"

## 3. Messages
After each command, the server and the client must write "XOXO". 

When starting a communication, the server sends a "BJR" message, followed by the set of available intructions :<br />
[ADD] (addition operation)<br />
[MULT] (multiplication operation)<br />




## 4. Specific elements 
(IF USEFUL)
Supported operations
Error handling
Extensibility

## 5. Exemples

Examples: examples of some typical dialogs.
After writing the specification, submit a Pull Request

