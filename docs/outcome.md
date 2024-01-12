# Outcome API Spec

## Create Outcome

Endpoint : POST /api/outcomes

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "balance" : "100000",
  "outcome_name" : "Biaya produksi",
  "outcome_total" : "20000"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "balance" : "100000",
    "outcome_name" : "Biaya produksi",
    "outcome_total" : "20000"
  }
}
```
Response Body (Failed) :

```json
{
  "errors" : "Invalid data format"
}
```
## Update Outcome

Endpoint : PUT /api/outcomes/{idOutcome}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body :

```json
{
  "balance" : "100000",
  "outcome_name" : "Biaya produksi",
  "outcome_total" : "20000"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "balance" : "100000",
    "outcome_name" : "Biaya produksi",
    "outcome_total" : "20000"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Invalid data format"
}
```

## Get Outcome

Endpoint : GET /api/outcomes/{idOutcome}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "balance" : "100000",
    "outcome_name" : "Biaya produksi",
    "outcome_total" : "20000"
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors" : "Outcome data is not found"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Outcome data is not found"
}
```

## Remove Outcome

Endpoint : DELETE /api/outcomes/{idOutcome}

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
  "errors" : "Outcome data is not found"
}
```