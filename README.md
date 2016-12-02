# boris-bikes

This "api" exposes a very simple yada resource that uses the [BikePoint API](https://api.tfl.gov.uk/#BikePoint) to
return either a html or json representation of the 5 nearest bikepoints to a specified location.


## Usage

    $ PORT=8080 java -jar boris-bikes.jar

## Endpoint

The "api" exposes a single endpoint `/bikepoints`. Calling it with no parameters will return the 5 nearest bikepoints
to [Leyton](https://www.google.co.uk/maps/place/Leyton,+London/@51.5619513,-0.0306485,14z/data=!4m5!3m4!1s0x48761d997519ec8b:0xe04da9391dd9878b!8m2!3d51.561948!4d-0.013139) sorted
by distance.

The `/bikepoints` endpoint is "protected" by Basic authentication using a hardcoded username (mark) and password (changeme).

```sh
curl -H "Accept: application/json" http://mark:changeme@localhost:3080/bikepoints
```

The "api" supports content negotiation, you can ask for html instead of json.

```sh
curl -H "Accept: text/html" http://mark:changeme@localhost:3080/bikepoints
```

You can also supply optional parameters `lat`, `lon`, `radius` and `number` to the `/bikepoints` endpoint.

```
lat: The latitude position at which to find nearest bikepoints
lon: The longitude position at which to find nearest bikepoints
radius: The radius out from lat and lon
number: The number of bikepoints to return
```

```sh
curl -H "Accept: application/json" http://mark:changeme@localhost:3080/bikepoints\?lat\=51.5212073\&lon\=-0.0740046\&radius\=10000\&number\=20
```

Or request html again.

```sh
curl -H "Accept: text/html" http://mark:changeme@localhost:3080/bikepoints\?lat\=51.5212073\&lon\=-0.0740046
```
