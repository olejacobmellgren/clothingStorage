# Client

Modul som håndterer klient-siden av APIet.
Modulen inneholder kun én klasse:

- StorageClient

## StorageClient

StorageClient kommuniserer med server-siden av APIet ved hjelp av å opprette forskjellige HTTP-Requests avhengig av om man vil hente eller publisere noe. Den håndterer også responsen som serveren sender, og gjør om dataen til noe som kan brukes av RemoteAccess i ui-modulen.

### Metoder

- StorageClient(): Lager en StorageClient som initialiserer endepunktet og ObjectMapperen som brukes for å deserialisere data

- getStorage() -> Storage: Henter Storage-objekt fra restserveren vha. en GET-request

- getClothing(String) -> Clothing: Henter Clothing-objekt fra restserveren vha. en GET-request

- putClothing(Clothing) -> boolean: Putter Clothing-objekt på restserveren vha. en PUT-request

- removeClothing(String) -> boolean: Fjerner Clothing-objekt fra restserveren vha. en DELETE-request

- getQuantity(String) -> int: Henter mengden (quantity) av et Clothing-objekt fra restserveren vha. en GET-request

- putQuantity(String, int) -> boolean: Putter mengde (quantity) av Clothing-objektet på restserveren vha. en PUT-request 

- getNames() -> List<'String'>: Henter navnene på alle Clothing-objektene i restserveren vha. en GET-request

- getSortedNames() -> List<'String'>: Henter navnene på alle Clothing-objektene i restserveren i sortert rekkefølge vha. en GET-request

- getSorted(int) -> List<'String'>: Henter navnene på alle sorterte Clothing-objekter på restserveren hvis det er sortert på en spesifikk måte vha. en GET-request

- getSortedType(String) -> List<'String'>: Henter Clothing-objektene i restserveren sortert etter type vha. en GET-request

- getSortedBrand(String) -> List<'String'>: Henter Clothing-objektene i restserveren sortert etter brand vha. en GET-request

- getStorageDisplay() -> List<'String'>: Henter displayet for Storage-objektet på restserveren til storage-page vha. en GET-request

- getPriceDisplay() -> List<'String'>: Henter displayet for Storage-objektet på restserveren til price-page vha. en GET-request

- getQuantitiesForTypeAndSizes(String) -> List<'Integer'>: Henter mengden (quantity) til hver størrelse basert på type fra restserveren vha. en GET-request

- getTotalValue() -> double: Henter totalverdien fra restserveren vha. en GET-request

- getTotalQuantity() -> int: Henter total mengde (quantity) fra restserver vha. en GET-request

- uriParam(String) -> String: Konverterer string til UTF_8 format.