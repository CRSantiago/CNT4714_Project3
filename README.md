# Client-Server-MySQL-Application

### Two-Tier Client-Server Application Development With MySQL and JDBC

There are 3 user: root, client and operationslog.
Root has all privileges.
Client has only select command privileges.
operationslog logs keeps a running total of the number of queries and the number of updates that have occurred via the user application.

Root command
![RootCommand7B](https://user-images.githubusercontent.com/42612374/229374635-e87657c2-c5ad-4190-a7e7-735bfad5f8d0.PNG)

Client command

![ClientCommand4](https://user-images.githubusercontent.com/42612374/229374580-31f27b29-8c53-46cb-bfa7-398a7a3144d2.PNG)

Client Error

![ClientCommand3B](https://user-images.githubusercontent.com/42612374/229374617-d379cf14-99d2-4c7e-ae1f-905b771403a1.PNG)

Operations Log after several commands

![operations log count](https://user-images.githubusercontent.com/42612374/229374668-a592aecf-26e0-4d50-9ee7-7c2a25ab266e.PNG)
