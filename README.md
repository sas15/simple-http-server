# Simple HTTP Server

An example of a simple, *no-dependency* Java HTTP server written from scratch. It can only respond to `GET` requests with files read from the disk.

The server utilizes a thread pool, each connection is processed on a separate thread.

## How to run it?

~~~~bash
mvn exec:java -Dexec.mainClass="server.cli.Main" [-Dport=9090] [-DrootDirectory=.]
~~~~

By default, the server will listen on port `9090` and will serve files from the current directory (`.`).

## Requesting a file

The easiest way is to use curl:

~~~~bash
curl localhost:9090/pom.xml -v
~~~~
