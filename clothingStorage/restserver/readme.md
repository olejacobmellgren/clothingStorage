# Restserver

Dette er en modul som håndterer Service laget, altså REST-API, med formål å sende data til og fra service laget.
Modulen inneholder følgende klasser:

- ClothingStorageApplication
- ClothingStorageController
- ClothingStorageService

## Avhengigheter

- **core** - Kjernelogikk
- **localpersistence** - Fillagring
- **spring-boot-starter-web** - Trengs for å kjøre springboot-applikasjonen
- **spring-boot-starter-test** - Trengs i integrationtestene
- **spring-boot-starter-jetty** - Trengs for å kjøre springboot-applikasjonen
- **junit-jupiter-api** - JUnit API
- **junit-jupiter-engine** - Brukes for å kjøre Junit-tester

## Plugins

- **maven-compiler-plugin** - For å kompilere kildekoden
- **maven-surefire-plugin** - For kjøring av Junit-tester
- **jacoco-maven-plugin** - For å sjekke testdekningsgrad med **Jacoco**
- **spotbugs-maven-plugin** - For å sjekke etter bugs i koden med **SpotBugs**
- **maven-checkstyle-plugin** - For å sjekke kodekvalitet med **Checkstyle**
- **spring-boot-maven-plugin** - For å kjøre Springboot

## ClothingStorageApplication

Application-klasse for Springboot serveren. Den starter serveren.

### Metoder

- objectMapperModule() -> ObjectMapper: lager objectmapper fra ClothingStoragePersistence

- main(String...): Main metode for å starte appen. Den gir tilgang til run metoden i SpringApplication med
  ClothingStorageApplication.class og strengene gitt som argumenter

- dummy() -> void: Kun for at checkstyle skal passere. Uten denne kræsjer Springboot

## ClothingStorageController

Kontroller (Controller) klassen for håndtering av get og post etterspørsler. Den tar i bruk ClothingStorageService. 

### Metoder

- getStorage() -> Storage: henter Storage

- getNames() -> List<String>: henter navnene på alle Clothing-objektene i Storage

- getSortedNames() -> ArrayList<String>: henter ut alle navnene på Clothing-objektene i Storage i sortert rekkefølge

- getSortedClothes(String) -> List<String>: henter ut pricedisplay av Clothing-objektene i Storage sortert

- getSortedClothesType(String) -> List<String>: henter ut Clothing-objektene i Storage sortert etter type

- getSortedClothesBrand(String) -> List<String>: henter ut Clothing-objektene i Storage sortert etter merke (brand)

- getStorageDisplay() -> List<String>: henter ut liste til storage displayet

- getPriceDisplay() -> List<String>: henter ut Storage til pris displayet 

- autoSaveStorage(): automatisk lagring av Storage

- getClothing(String) -> Clothing: henter ut Clothing-objektet som matcher med navnet som er gitt inn som string

- putClothing(String) -> boolean: erstatter eller legger til et Clothing-objekt 

- removeClothing(String) -> boolean: fjerner Clothing-objektet med matchende navn 

- getQuantity(String) -> int: henter antallet av et Clothig-objekt

- putQuantity(String) -> boolean: erstatter eller legger til mengde (quantity)

- removeQuantity(String) -> boolean: fjerner mengde (quantity)

- getQuantitieForTypeAndSize(String) -> List<Integer>: henter mengde for hver størrelse for en type Clothing

- getTotalQuantity() -> int: henter total mengde av Clothing i Storage

- getTotalValue() -> double: henter total verdi av Storage

## ClothingStorageService

Gir serveren tilgang til modulene og funksjonene fra core og localpersistence.

### Metoder

- ClothingStorageService(): Lager et nytt Storage-objekt enten med et gitt Storage eller et tomt et hvis ingen Storage er gitt

- autoSaveTodoModel() -> void: lagrer alle endringer i Storage til disk

- getStorage() -> Storage: lar de andre klassene få tilgangen til dette Storage-objektet
- setStorage() -> void: lar de andre klassene endre på dette Storage-objektet
