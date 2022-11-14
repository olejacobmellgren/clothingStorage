# Restserver

Dette er en modul som håndterer Service laget, aka. rest API, med formål å sende data til og fra service laget.
Modulen inneholder de følgende klassene:

- ClothingStorageApplication
- ClothingStorageController
- ClothingStorageService

## ClothingStorageApplication

Application klasse for spring boot serveren. Den starter serveren.

### Metoder

- objectMapperModule() -> ObjectMapper: Lager objecktmapper fra ClothingStoragePersistence.

- main(String...): Main metode for å starte appen. Den gir tilgang til run metoden i SpringApplication med
  ClothingStorageApplication.class og strengene gitt som argumenter.

- dummy() -> void: Kun for at checkstyle ikke skal passere. Uten denne kræsjer Springboot.

## ClothingStorageController

Kontroller (Controller) klassen for håndtering av get og post etterspørsler. Den tar i bruk ClothingStorageService. 

### Methods

- getStorage() -> Storage: Henter Storage. 

- getNames() -> List<String>: Henter navnene på alle Clothing objektene i Storage.

- getSortedNames() -> ArrayList<String>: Henter ut alle navnene på Clothing objektene i Storage i sortert rekkefølge.

- getSortedClothes(String) -> List<String>: Henter ut pricedisplay av Clothing objektene i Storage sortert.

- getSortedClothesType(String) -> List<String>: Henter ut Clothing objektene i Storage sortert etter type.

- getSortedClothesBrand(String) -> List<String>: Henter ut Clothing objektene i Storage sortert etter merke (brand).

- getStorageDisplay() -> List<String>: Henter ut liste til storage displayet.

- getPriceDisplay() -> List<String>: Henter ut Storage til pris displayet. 

- autoSaveStorage(): automatisk lagring av Storage. 

- getClothing(String) -> Clothing: Henter ut Clothing objektet som matcher med navnet som er gitt inn som string.

- putClothing(String) -> boolean: Erstatter eller legger til et Clothing. 

- removeClothing(String) -> boolean: Fjerner Clothing objektet med matchende navn. 

- getQuantity(String) -> int: Henter antallet av et Colothig objekt.

- putQuantity(String) -> boolean: Erstatter eller legger til mengde(quantity). 

- removeQuantity(String) -> boolean: Fjerner mengde(quantity).

- getQuantitieForTypeAndSize(String) -> List<Integer>: Henter mengde for hver størrelse for en type Clothing. 

- getTotalQuantity() -> int: Henter total mengde av Clothing i Storage.

- getTotalValue() -> double: Henter total verdi av Storage.

## ClothingStorageService

Gir serveren tilgang til modulene og funksjonene fra core og localpersistence.

### Methods

- ClothingStorageService(): Lager et nytt Storage enten med et gitt Storage eller et tomt et hvis ingen Storage er gitt.

- autoSaveTodoModel() -> void: Lagerer alle endringer i Storage til disk.

- getStorage() -> Storage: Lar de andre klassene få tilgangen til dette Storage
- setStorage() -> void: Lar de andre klassene endre på dette storaget.
