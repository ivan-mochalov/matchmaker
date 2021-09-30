# The matchmaker
The implementation of a simple matchmaker

The matchmaker accepts users and is able to create groups based on a special rate calculated from the latency and the skill.
The algorithm consist of the following parts:
* if the pool contains user with expired delay
    * if enough users to create full group -> select by rate
    * if not -> all users moved to the group
* if not -> select all current users, calculate rate for each user and create group with the lowest rate

### Prerequisites

* installed java

```
java -version
java version "11.0.10" 2021-01-19 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.10+8-LTS-162)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.10+8-LTS-162, mixed mode)
```

### Run

```
> ./gradlew bootRun
```

### Check out

* swagger API:

```
http://localhost:8080/swagger-ui.html
```

* see also: resources/rest-api.http
