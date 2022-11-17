# Integration Tests

Modul for integrasjonstester for prosjektet med følgende klasser og testklasser:

- Dummy
- DummyTest
- StorageIntegrationTest

Vi velger å nevne testklassene også fordi **StorageIntegrationTest** er poenget med modulen. Integrasjonstestene tester om de to selvstendige modulene **client** og **restserver** klarer å kommunisere med hverandre for å sende data over REST-APIet. Dette er viktig for å sikre at applikasjonen som en helhet klarer å bruke REST-APIet, og at det ikke skjer feil med data i overføringen mellom klient og server.

Modulen har også en tom klasse som kreves av Springboot for å kompilere klassen. Med denne følger også en test-klasse for å ha god testdekningsgrad for modulen.

## Avhengigheter

- **core** - Kjernelogikk
- **localpersistence** - Fillagring
- **client** - Klient-siden
- **restserver** - Server-siden
- **spring-boot-starter-web** - Trengs for å kjøre springboot-applikasjonen
- **spring-boot-starter-test** - Trengs i integrasjonstestene
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

## StorageIntegrationTest

Integrasjonstester for klient og server.

### Tester

- startClient(): -> Lager en StorageClient og setter opp data før hver test

- testGetStorage() -> tester å hente ut Storage med Clothing objekter i

- testGetClothing() -> tester å hente Clothing-objekt restserveren

- testPutClothing() -> tester å putte til Clothing-objekt på restserveren

- testRemoveClothing() -> tester å fjerne Clothing-objekt fra restserveren

- testGetQuantity() -> tester å hente mengden (quantity) av et Clothing-objekt fra restserveren

- testPutQuantity() -> tester å putte mengde (quantity) av Clothing-objektet på restserveren  

- testGetNames() -> teste å hente navnene på alle Clothing-objektene i Storage

- testGetSortedNames() -> tester å hente navnene på alle Clothing-objektene i Storage i sortert rekkefølge

- testGetSorted() -> tester å hente Clothing-objektene i Storage når det er sortert på en spesifik måte

- testGetSortedType() -> tester å hente Clothing-objektene i Storage sortert etter type

- testGetSortedBrand() -> tester å hente Clothing-objektene i Storage sortert etter brand

- testGetStorageDisplay() -> tester å hente displayet for Storage på storage-page

- testGetPriceDisplay() -> tester å hente displayet for Storage på price-page

- testGetQuantitiesForTypeAndSizes() -> tester å hente mengden (quantity) til hver størrelse for en type

- testGetTotalValue() -> tester å hente total verdien av Storage

- testGetTotalQuantity() -> tester å hente total mengde (quantity) fra restserver

- deleteFile() -> sletter filen med data etter hver test