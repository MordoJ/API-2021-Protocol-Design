## 1. Protocol objectives
The protocol allows the client to request the answer of a mathematical operation, giving two numbers and the operation wanted. .

## 2. Overall behavior
To ensure the communication between the client and the server, we use the TCP/IP protocol. The client accesses the server with the address localhost, and the port number 256. The server speaks first, giving a list of available commands to te client.

When the client doesn't want to use the server's services anymore, he closes the connection by sending a "DONE" command.

## 3. Messages
After each command, the server and the client must write "XOXO". 

When starting a communication, the server sends a "BJR" message, followed by the set of available intructions : "ADD" (addition operation) and "MULT" (multiplication operation).

FLOW !!

## 4. Specific elements 
(IF USEFUL)

## 5. Exemples
