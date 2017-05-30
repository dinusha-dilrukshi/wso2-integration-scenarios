# DestinationService Sample

This is the MSF4J Desination Service sample that gives you random routeNumber for your destination.

## How to build the sample

From this directory, run

```
mvn clean install
```

## How to run the sample locally

From the target directory, run
```
java -jar travel-destination-*.jar
```

## How to test the sample

We will use the cURL command line tool for testing. You can use your preferred HTTP or REST client too.

```
curl -v http://localhost:8080/destination/route
```

You should get a response similar to the following:

```
{"routeNo":2}
```
