# The matchmaker
The implementation of a simple matchmaker

The matchmaker accepts users and is able to create groups based on the latency, the skill or the delay.
The algorithm consist of the following parts:
* if the pool contains user with expired delay
    * if enough users to create full group -> select by the lowest difference with current user latency, skill and delay
    * if not -> all users moved to the group
* if not -> select all current users, calculated median latency and skill then select by the values

During the group selection:
* sorting the entire group by the difference between median latency and users latencies and select first groupSize times 3 (or less) values
* sorting the selected values by skill the same way and select first groupSize times 2 (or less) values
* sorting by delay

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
