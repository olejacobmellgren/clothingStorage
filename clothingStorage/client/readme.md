# Client

Modul som håndterer klient-siden av APIet.
Modulen inneholder kun én klasse:

- StorageClient

## Avhengigheter

- **core** - kjernelogikk
- **localpersistence** - fillagring
- **mockito-core** - Mockito
- **wiremock-jre8** - Wiremock til testing
- **junit-jupiter-api** - JUnit API
- **junit-jupiter-engine** - Brukes for å kjøre lage Junit-testene

## Plugins

- **maven-compiler-plugin** - For å kompilere kildekoden
- **maven-surefire-plugin** - For kjøring av Junit-tester
- **jacoco-maven-plugin** - For å sjekke testdekningsgrad med **Jacoco**
- **spotbugs-maven-plugin** - For å sjekke etter bugs i koden med **SpotBugs**
- **maven-checkstyle-plugin** - For å sjekke kodekvalitet med **Checkstyle**

## StorageClient

StorageClient kommuniserer med server-siden av APIet ved hjelp av å opprette forskjellige HTTP-Requests avhengig av om man vil hente eller publisere noe. Den håndterer også responsen som serveren sender, og gjør om dataen til noe som kan brukes av RemoteAccess i ui-modulen.

### Metoder

- StorageClient(): lager en StorageClient som initialiserer endepunktet og ObjectMapperen som brukes for å deserialisere data

- getStorage() -> Storage: henter Storage-objekt fra restserveren vha. en GET-request

- getClothing(String) -> clothing: Henter Clothing-objekt fra restserveren vha. en GET-request

- putClothing(Clothing) -> boolean: putter Clothing-objekt på restserveren vha. en PUT-request

- removeClothing(String) -> boolean: fjerner Clothing-objekt fra restserveren vha. en DELETE-request

- getQuantity(String) -> int: henter mengden (quantity) av et Clothing-objekt fra restserveren vha. en GET-request

- putQuantity(String, int) -> boolean: putter mengde (quantity) av Clothing-objektet på restserveren vha. en PUT-request 

- getNames() -> List<'String'>: henter navnene på alle Clothing-objektene i restserveren vha. en GET-request

- getSortedNames() -> List<'String'>: henter navnene på alle Clothing-objektene i restserveren i sortert rekkefølge vha. en GET-request

- getSorted(int) -> List<'String'>: henter navnene på alle sorterte Clothing-objekter på restserveren hvis det er sortert på en spesifikk måte vha. en GET-request

- getSortedType(String) -> List<'String'>: henter Clothing-objektene i restserveren sortert etter type vha. en GET-request

- getSortedBrand(String) -> List<'String'>: henter Clothing-objektene i restserveren sortert etter brand vha. en GET-request

- getStorageDisplay() -> List<'String'>: henter displayet for Storage-objektet på restserveren til storage-page vha. en GET-request

- getPriceDisplay() -> List<'String'>: henter displayet for Storage-objektet på restserveren til price-page vha. en GET-request

- getQuantitiesForTypeAndSizes(String) -> List<'Integer'>: henter mengden (quantity) til hver størrelse basert på type fra restserveren vha. en GET-request

- getTotalValue() -> double: henter totalverdien fra restserveren vha. en GET-request

- getTotalQuantity() -> int: henter total mengde (quantity) fra restserver vha. en GET-request

- uriParam(String) -> String: konverterer string til UTF_8 format.