# Client

Modul som håndterer klient-siden av APIet.
Modulen inneholder kun én klasse:

- StorageClient

## StorageClient

StorageClient kommuniserer med server-siden av APIet ved hjelp av å opprette forskjellige HTTP-Requests avhengig av om man vil hente eller publisere noe. Den håndterer også responsen som serveren sender, og gjør om dataen til noe som kan brukes av RemoteAccess i ui-modulen.

### Metoder

- StorageClient(): Lager en StorageClient som initialiserer endepunktet og ObjectMapperen som brukes for å deserialisere data.

- getStorage() -> Storage: Henter Storage med Clothing objekter i.

- getClothing(String) -> Clothing: Henter Clothing fra objekt restserveren.

- putClothing(Clothing) -> boolean: Putter Clothing objekt på restserveren.

- removeClothing(String) -> boolean: Fjerner Clothing objekt fra restserveren.

- getQuantity(String) -> int: Henter mengden (quantity) av et Clothing objekt fra restserveren. 

- putQuantity(String, int) -> boolean: Putter mengde (quantity) av Clothing objektet på restserveren.  

- getNames() -> List<'String'>: Henter navnene på alle Clothing objektene i Storage.

- getSortedNames() -> List<'String'>: Henter navnene på alle Clothing objektene i Storage i sortert rekkefølge.

- getSorted(int) -> List<'String'>: Henter Clothing objektene i Storage når det er sortert på en spesifik måte.

- getSortedType(String) -> List<'String'>: Henter Clothing objektene i Storage sortert etter type.

- getSortedBrand(String) -> List<'String'>: Henter Clothing objektene i Storage sortert etter brand.

- getStorageDisplay() -> List<'String'>: Henter displayet for Storage på storage-page.

- getPriceDisplay() -> List<'String'>: Henter displayet for Storage på price-page.

- getQuantitiesForTypeAndSizes(String) -> List<'Integer'>: Henter mengden (quantity) til hver størrelse for en type   Clothing .

- getTotalValue() -> double: Henter total verdien av Storage.

- getTotalQuantity() -> int: Henter total mengde (quantity) fra restserver.

- uriParam(String) -> String: Konverterer string til UTF_8 format.