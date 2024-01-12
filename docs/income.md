# Income API Spec

## Create Income

Endpoint : POST /api/incomes

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "balance" : "100000",
  "item_name" : "Towel Cake",
  "price" : "13000",
  "amount" : "2"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id"  : "random string",
    "balance" : "100000",
    "item_name" : "Towel Cake",
    "price" : "13000",
    "amount" : "2"
  }
}
```
Response Body (Failed) :

```json
{
  "errors" : "Invalid data format"
}
```
## Update Income

Endpoint : PUT /api/incomes/{idIncome}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "balance" : "100000",
  "item_name" : "Towel Cake",
  "price" : "13000",
  "amount" : "2"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id"  : "random string",
    "balance" : "100000",
    "item_name" : "Towel Cake",
    "price" : "13000",
    "amount" : "2"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Invalid data format"
}
```

## Get Income

Endpoint : GET /api/incomes/{idIncome}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id"  : "random string",
    "balance" : "100000",
    "item_name" : "Towel Cake",
    "price" : "13000",
    "amount" : "2"
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors" : "Income data is not found"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Income data is not found"
}
```

## Remove Income

Endpoint : DELETE /api/incomes/{idIncome}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Income data is not found"
}
```