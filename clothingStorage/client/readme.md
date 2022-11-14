# Client

Modul som håndterer klinet siden av APIet. Formålet med modulen er ...
Modulen inneholder de følgende klasse:

- StorageClient

## StorageClient

....

### Metoder

- StorageClient(): Lager en StorageClient fra en builder.

- getStorage() -> Storage: Henter Storage med Clothing objekter i.

- getClothing(String) -> Clothing: Henter Clothing fra objekt restserveren.

- putClothing(Clothing) -> boolean: Putter Clothing objekt på restserveren.

- removeClothing(String) -> boolean: Fjerner Clothing objekt fra restserveren.

- getQuantity(String) -> int: Henter mengden (quantity) av et Clothing objekt fra restserveren. 

- putQuantity(String, int) -> boolean: Putter mengde (quantity) av Clothing objektet på restserveren.  

- getNames() -> List<String>: Henter navnene på alle Clothing objektene i Storage.

- getSortedNames() -> List<String>: Henter navnene på alle Clothing objektene i Storage i sortert rekkefølge.

- getSorted(int) -> List<String>: Henter Clothing objektene i Storage når det er sortert på en spesifik måte.

- getSortedType(String) -> List<String>: Henter Clothing objektene i Storage sortert etter type.

- getSortedBrand(String) -> List<String>: Henter Clothing objektene i Storage sortert etter brand.

- getStorageDisplay() -> List<String>: Henter dispalyet for Storage på storage-page.

- getPriceDisplay() -> List<String>: Henter dispalyet for Storage på price-page.

- getQuantitiesForTypeAndSizes(String) -> List<Integer>: Henter mengden (quantity) for en type Clothing.

- getTotalValue() -> double: Henter total verdien av Storage.

- getTotalQuantity() -> int: Henter mengde (quantity) fra restserver.

- uriParam(String) -> String: Konverterer string til UTF_8 format.
