# ClothingStorage - REST-api

Dette dokumentet forklarer hvordan server-siden av REST-api'et er satt opp.

## Get Storage

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage <http://localhost:8080/clothingStorage>

Response:
```json
{
    "clothes" : [ {
        "type" : "Pants",
        "brand" : "Nike",
        "size" : "S",
        "price" : 10.0,
        "discount" : 0.0
    }, {
        "quantity" : 5
    }, {
        "type" : "Shorts",
        "brand" : "LouisVuitton",
        "size" : "M",
        "price" : 20.0,
        "discount" : 0.0
    }, {
        "quantity" : 8
    }, {
        "type" : "Socks",
        "brand" : "Adidas",
        "size" : "L",
        "price" : 30.0,
        "discount" : 0.0
    }, {
        "quantity" : 4
    }, {
        "isSorted" : false
    } ]
}
```

## Get names of Clothings in Storage

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/names <http://localhost:8080/clothingStorage/names>

Response:
```json
{
    [ 
        "PantsNikeS",
        "ShortsLouisVuittonM",
        "SocksAdidasL"
    ]
}
```

## Get sorted names of Clothings in Storage

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/sortedNames <http://localhost:8080/clothingStorage/sortedNames>

Response:
```json
{
    [ 
        "PantsAdidasL",
        "JacketLacosteS",
        "UnderwearNikeS"
    ]
}
```

## Get sorted Clothings

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/sorted/{id} <http://localhost:8080/clothingStorage/sorted/0>

Parameter: id
  - 0 for lavest pris
  - 1 for høyest pris
  - 2 for klær på rabatt

Response:
```json
{
    [
        "Pants; Nike; 10.0kr",
        "Shorts; LouisVuitton; 20.0kr",
        "Socks; Adidas; 30.0kr"
    ]
}
```
## Get sorted Clothings by type

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/sortedType/{type} <http://localhost:8080/clothingStorage/sortedType/Pants>

Parameter: type

Response:
```json
{
    [
        "Pants; Nike; 86.0kr",
        "Pants; Adidas; 99.0kr",
        "Pants; Levi's; 25.0kr"
    ]
}
```

## Get sorted Clothings by brand

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/sortedBrand/{brand} <http://localhost:8080/clothingStorage/sortedBrand/Nike>

Parameter: brand

Response:
```json
{
    [
        "Pants; Nike; 44.0kr",
        "Shorts; Nike; 98.0kr",
        "Jacket; Nike; 35.0kr"
    ]
}
```

## Get the storage display

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/storageDisplay <http://localhost:8080/clothingStorage/storageDisplay>

Response:
```json
{
    [
        "Jacket; Nike; M; 2",
        "Sweater; LouisVuitton; S; 9",
        "Shorts; Supreme; L; 4"
    ]
}
```

## Get the price display

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/priceDisplay <http://localhost:8080/clothingStorage/priceDisplay>

Response:
```json
{
    [
        "Pants; Adidas; 40.0kr",
        "Underwear; Lacoste; 86.0kr",
        "Jacket; Nike; 26.0kr"
    ]
}
```

## Get Clothing

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/clothes/{name} <http://localhost:8080/clothingStorage/clothes/PantsNikeM>

Parameter: name

Response:
```json
{
    "type" : "Pants",
    "brand" : "Nike",
    "size" : "M",
    "price" : 44.0,
    "discount" : 0.5
}
```

## Add Clothing

Type: <span style="color:#FFA500">PUT</span>

URI: host:port/clothingStorage/clothes/{name} <http://localhost:8080/clothingStorage/clothes/JacketLacosteS>

Parameter: name

Request: 
```json
{
    "type" : "Jacket",
    "brand" : "Lacoste",
    "size" : "S",
    "price" : 149.0,
    "discount" : 0.0
}
```

Response:
```json
{
    true
}
```

## Remove Clothing

Type: <span style="color:#FF0000">DELETE</span>

URI: host:port/clothingStorage/clothes/{name} <http://localhost:8080/clothingStorage/clothes/SocksNikeL>

Parameter: name 

Response:
```json
{
    true
}
```

## Get quantity of Clothing

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/quantity/{name} <http://localhost:8080/clothingStorage/quantity/PantsNikeS>

Response:
```json
{
    5
}
```

## Put quantity of Clothing

Type: <span style="color:#FFA500">PUT</span>

URI: host:port/clothingStorage/quantity/{name} <http://localhost:8080/clothingStorage/quantity/SweaterAdidasM>

Parameter: name

Request:
```json
{
    quantity=5
}
```

Response:
```json
{
    true
}
```

## Get list of quantities for each size of type

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/stats/chartData/{type} <http://localhost:8080/clothingStorage/stats/chartData/Pants>

Parameter: type

Response:
```json
{
    [
        5,
        0,
        8
    ]
}
```

## Get total quantity

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/stats/totalQuantity <http://localhost:8080/clothingStorage/stats/totalQuantity>

Response:
```json
{
    17
}
```

## Get total value

Type: <span style="color:#2e86c1">GET</span>

URI: host:port/clothingStorage/stats/totalValue <http://localhost:8080/clothingStorage/stats/totalValue>

Response:
```json
{
    330.0
}
```
