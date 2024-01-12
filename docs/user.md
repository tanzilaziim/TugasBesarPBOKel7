# User API Spec

## Register User
Endpoint: POST /api/user

Request Body:

```json
{
  "username" : "tanzil",
  "password" : "admin123",
  "name"     : "Tanzil Aziim"
}
```
Response Body (Success):

```json
{
  "data"  : "OK"
}
```

Response Body (Failed):

```json
{
  "errors": "Username or password must fill, ???"
}
```

## Login User

Endpoint: POST /api/auth/login

Request Body:

```json
{
  "username" : "tanzil",
  "password" : "admin123"
}
```
Response Body (Success):

```json
{
  "data"  : {
    "token"     : "TOKEN",
    "expiredAt" : 2342342342342 // miliseconds
  }
}
```

Response Body (Failed, 401):

```json
{
  "errors": "Username or password is not correct"
}
```

## Get User

Endpoint: GET /api/users/current

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
  "data" : {
    "username"  : "tanzil",
    "name"      : "Tanzil Aziim"
  }
}
```

Response Body (Failed, 401):

```json
{
  "errors": "Unauthorized"
}
```

## Update User

Endpoint: PATCH /api/users/current

Request Header:

- X-API-TOKEN : Token (Mandatory)

Request Body:

```json
{
  "name"     : "Aziim Tanzil", // put if only want to update name
  "password" : "new password" // put if only want to update password
}
```

Response Body (Success):

```json
{
  "data" : {
    "username"  : "tanzil",
    "name"      : "Tanzil Aziim"
  }
}
```

Response Body (Failed, 401):

```json
{
  "errors": "Unauthorized"
}
```

## Logout User

Endpoint: DELETE /api/auth/logout

Request Header:

- X-API-TOKEN : Token (Mandatory)

Response Body (Success):

```json
{
  "data" : "OK"
}
```
