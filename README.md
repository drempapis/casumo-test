## Casumo

## Structure
Java 8 / Spring 4 / maven 3 have been used to create a web application exposing a HTTP-REST-API.

The code is layered following the standards with top-down only dependencies between the layers.
-- controller
-- service
-- repository
-- domain

-Movies
The inventory of movies is being retrieved from a json file and stored into memory.

There is a service (MoviesService) controlling the availability of the movies flagging them. When a movie is requested
by a user, the movie is flagged as non-avalaible. The status is changed when the movie is returned.

- Session
For each new customer a unique uuid identifier is assigned. For a /rent request with a new customerId a new session is created
where the requested movies and the bonus are stored . The user can request several movies by one operation and return some, or 
all of them. The session state provides the information for applying the charging logic. 

- Data Structures
The heavier ConcurrentHashMap has been used instead of a synchronized HashMap for thread-safety applied to session and
moviesInventory data structures.


### HTTP-REST-API

- Description : RENT MOVIES - POST - REQUEST 
- Path        : [/rent]
- Method      : [POST]
- Produces    : [application/json]
- Consumes    : [application/json]
- Headers     : [Content-Type:application/json]
- Body        : 

```
{  
   "customerId":UUID,
   "moviesList":[  
      {  
         "movieId":UUID,
         "timeStamp":Long,
         "days":int
      }
   ]
}
```
*Example
```
{  
   "customerId":"9b0321b4-ac25-4191-b50e-f1c0957baa1f",
   "moviesList":[  
      {  
         "movieId":"f8bab770-6109-11e5-9d70-feff819cdc9f",
         "timeStamp":1443022560000,
         "days":1
      },
      {  
         "movieId":"f8bab9d2-6109-11e5-9d70-feff819cdc9f",
         "timeStamp":1443022560000,
         "days":5
      }
   ]
}
```
- Response Status Code : 200 - OK
- Response     :
 ```
{
   "price":int,  "The charged price for the requested movies"
   "bonus":int   "The obtained bonus stored in user´s session"
}
```
- Description : RETURN MOVIES - POST - REQUEST
- Path        : [/return]
- Method      : [POST]
- Produces    : [application/json]
- Consumes    : [application/json]
- Headers     : [Content-Type:application/json]
- Body        : 

```
{  
   "customerId":UUID,
   "moviesList":[  
      {  
         "movieId":UUID,
         "timeStamp":Long,
         "days":int
      }
   ]
}
```
*Example
```
{  
   "customerId":"9b0321b4-ac25-4191-b50e-f1c0957baa1f",
   "moviesList":[  
      {  
         "movieId":"f8bab770-6109-11e5-9d70-feff819cdc9f",
         "timeStamp":1443022560000,
         "days":1
      },
      {  
         "movieId":"f8bab9d2-6109-11e5-9d70-feff819cdc9f",
         "timeStamp":1443022560000,
         "days":5
      }
   ]
}
```
- Response Status Code : 200 - OK
- Response     :
 ```
{
   "price":int  "The subcharged price for the requested movies"
}
```

### Execute the application
To execute and run the applcation type in a console 

mvn clean install jetty:run

This command will execute the unit tests (Test), the integration tests (IT) startup
the web application using an embedded jetty server. You can test the app, using any
http client, logs will be printed in the console, while accessing the controller layer.