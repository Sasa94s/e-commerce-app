# API Documentation
> Postman Collection is available as [JSON](./src/main/resources/ECommerce-App.postman_collection.json) for view/download.

## Create User

Creates User account if the username does not exist, and password complexity is achieved.

Endpoint:
`POST` `/api/user/create`
### Sample Request Body
```json
{
  "username": "Sasa94s",
  "password": "passw0rD!",
  "confirmPassword": "passw0rD!"
}
```
### Sample Response Body
```json
{
    "id": 1,
    "username": "Sasa94s",
    "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
}
```

## Login User

Authenticates User according to a valid registered username and password. 

Endpoint:
`POST` `/api/user/login`
### Sample Request Body
```json
{
  "username": "Sasa94s",
  "password": "passw0rD!"
}
```
### Sample Response Header
```
"Authorization": Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTYXNhOTRzIiwiZXhwIjoxNjMyNDM4MDk5fQ.HK4mAxhF-1DBTGITnTCPd9LpwyY7W9wYdeuNNWUWh40k3a4xR8q97SzmNHSxjYT76oJazFmQZiGdCcavY0SlUw
```


## Get User by ID

Retrieves User information by ID

Endpoint:
`GET` `/api/user/id/{id}`
### Sample Response Body
```json
{
  "id": 1,
  "username": "Sasa94s",
  "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
}
```

## Get User by Username

Retrieves User information by ID

Endpoint:
`GET` `/api/user/username/{username}`
### Sample Response Body
```json
{
  "id": 1,
  "username": "Sasa94s",
  "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
}
```

## Get Items

Retrieves shopping items available in stock.

Endpoint:
`GET` `/api/item`
### Sample Response Body
```json
[
  {
    "id": 1,
    "name": "Round Widget",
    "price": 2.99,
    "description": "A widget that is round"
  },
  {
    "id": 2,
    "name": "Square Widget",
    "price": 1.99,
    "description": "A widget that is square"
  }
]
```

## Get Item by ID

Retrieves shopping item available in stock by ID.

Endpoint:
`GET` `/api/item/{id}`
### Sample Response Body
```json
{
  "id": 1,
  "name": "Round Widget",
  "price": 2.99,
  "description": "A widget that is round"
}
```


## Get Item by ID

Retrieves shopping item available in stock by ID.

Endpoint:
`GET` `/api/item/name/{name}`
### Sample Response Body
```json
[
  {
    "id": 1,
    "name": "Round Widget",
    "price": 2.99,
    "description": "A widget that is round"
  }
]
```

## Add to Cart

Adds shopping item available in stock to User's shopping cart.

Endpoint:
`POST` `/api/cart/addToCart`
### Sample Request Body
```json
{
  "username": "Sasa94s",
  "itemId": 1,
  "quantity": 2
}
```
### Sample Response Body
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "name": "Round Widget",
      "price": 2.99,
      "description": "A widget that is round"
    },
    {
      "id": 1,
      "name": "Round Widget",
      "price": 2.99,
      "description": "A widget that is round"
    }
  ],
  "user": {
    "id": 1,
    "username": "Sasa94s",
    "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
  },
  "total": 5.98
}
```


## Remove from Cart

Removes shopping item available in stock from User's shopping cart.

Endpoint:
`POST` `/api/cart/removeFromCart`
### Sample Request Body
```json
{
  "username": "Sasa94s",
  "itemId": 1,
  "quantity": 1
}
```
### Sample Response Body
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "name": "Round Widget",
      "price": 2.99,
      "description": "A widget that is round"
    }
  ],
  "user": {
    "id": 1,
    "username": "Sasa94s",
    "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
  },
  "total": 2.99
}
```


## Submit Order

Submit order based on shopping items added to User's shopping cart.

Endpoint:
`POST` `/api/order/submit/{username}`

### Sample Response Body
```json
{
  "id": 1,
  "items": [
    {
      "id": 1,
      "name": "Round Widget",
      "price": 2.99,
      "description": "A widget that is round"
    }
  ],
  "user": {
    "id": 1,
    "username": "Sasa94s",
    "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
  },
  "total": 2.99
}
```



## Get Orders History

Get Orders history submitted by User.

Endpoint:
`GET` `/api/order/history/{username}`

### Sample Response Body
```json
[
  {
    "id": 1,
    "items": [
      {
        "id": 1,
        "name": "Round Widget",
        "price": 2.99,
        "description": "A widget that is round"
      }
    ],
    "user": {
      "id": 1,
      "username": "Sasa94s",
      "password": "$2a$10$Y2.R/jdeiG61tLnq5zmPEOFyaiyv.z5TlLOVvbfNIOXvu2bX7xM/y"
    },
    "total": 2.99
  }
]
```


