# Core

Modul som inneholder grunnklassene til prosjektet.

Modulen inneholder de følgende klassene:

- Clothing
- Storage
- StorageStatistics

## Clothing

Objekt-klassen for appen. Dette er appens mest grunnleggende objekt.

### Metoder

- Clothing(String, String, char, double): Initialiserer et clothing-objekt

- setType(): Setter type-variabelen til clothing-objektet

- isValidType(String): Sjekker om en type er gyldig

- setBrand(String): Setter brand-variabelen til clothing-objektet

- isValidBrand(String) -> boolean: Sjekker om en brand er gyldig

- setSize(char): Setter size-variabelen til clothing-objektet

- setPrize(double, boolean): Setter price-variabelen til clothing-objektet

- setPriceAfterDiscount(double): Setter prisen til et clothing-objekt etter at et discout er lagt til

- removeDiscount(): Fjerner discounten fra clothing-objektet og setter pricen tilbake til original prisen

- isValidDiscount(double) -> boolean: Sjekker om gitt discount er en gyldig discount

- setDiscount(double): Setter discount-variabelen til clothing-objektet

- setName(): Setter navnet til clothing-objektet

- getType() -> String: Henter type-variabelen til clothing-objektet

- getBrand() -> String: Henter brand-variabelen til clothing-objektet

- getSize() -> char: Henter size-variabelen til clothing-objektet

- getPrize() -> double: Henter price-variabelen til clothing-objektet

- isOnDiscount() -> boolean: Sjekker om discount er ulik null

- getDiscount() -> double: Henter discount-variabelen til clothing-objektet

- getName() -> String: Henter name-variabelen til clothing-objektet

- toString() -> String: Returnerer clothing-objektet som en string

- equalButDifferentPrice(Clothing) -> boolean: Sjekker om to clothing-objekter har like attributer utenom price-variabelen

- equalButDifferentSize(Clothing) -> boolean: Sjekker hvis to clothing-objekter har like attributer utenom size-variabelen

## Storage

Klassen for oppbevaring av Clothing-objekt. Dette er også en av appens mest grunnleggende klasser.

### Methods

- Storage(): Initialiserer et storage-objekt

- addNewClothing(Clothing, int): Legger til et git antall like clothing-objekt til storage

- removeClothing(Clothing): Fjerner et git antall like clothing-objekt fra storage

- increaseQuantity(Clothing, int): Øker mengden av et clothing-objekt i storage med en gitt mengde

- decreseQuantity(Clothing, int): Reduserer mengden av et clothing-objekt i storage med en gitt mengde

- updateQuantity(Clothing, int): Oppdaterer mangden (quantity) av et clothing-objekt direkte

- storageDisplay() -> List<String>: Itererer seg gjennom alle clothing-objektene i storage og formaterer de til en string tilpasset displayet i storage-page siden til appen

- priceDisplay() -> List<String>: Itererer seg gjennom alle clothing-objektene i storage og formaterer de til en string tilpasset displayet i price-page siden til appen 

- isValidQuantity(int) -> boolean: Sjekker om gitt mengde er gyldig 

- isClothingInStorage(Clothing) -> boolean: Sjekker om clothing-objektet er i storage

- setIsSortedPricePage(boolean): Setter om price-page er sortert eller ikke

- getIsSortedClothes() -> boolean: Sjekker om price-page er sortert eller ikke

- getQuatity(Clothing) -> int: Henter mengden av et clothing-objekt i storage 

- getAllClothings() -> LinkedHasMap<Clothing, Integer>: Henter alle clothing-objektene i storage

- getSotedClothings() -> ArrayList<Clothing>: Henter liste med clothing-objekt sortert i price-page

- addSortedClothing(Clothing): Legger til et clothing-objekt til en sortert clothing liste

- sortOnLowestPrice(): Sorterer storage-list basert på lavest pris

- sortOnHighestPrice(): Sorterer storage-list basert på høyest pris

- filterOnBrand(String): Filtrerer storage-list basert på brand

- filterOnType(String): Filtrerer storage-list basert på type

- filterOnDiscount(): Filtrerer storage-list basert på discount 

- getClothing(int) -> Clothing: Henter clothing-objekt fra hashmap ved index

- getClothing(String) -> Clothing: Henter clothing-objekt fra hashmap ved navn

- getClothingFromSortedClothes(int) -> Clothing: Henter clothing-objekt fra sortert liste

- toString() -> String: Returnerer storage-objektet som en tilpasset string


## StorageStatistics

Klassen for funksjonaliteten bak statistikk siden til appen.

### Methods

- getTotalQuantity(Storage) -> int: Henter total mengde av clothing i storage 

- getTotalValue(Storage) -> double: Henter totalverdien til alle Clothing-objektene i storage

- getQuantityForType(Storage, String) -> int: Henter mengden av en type clothing

- getQuantityForTypeAndSize(Storage, String, char) -> int: Henter mengden av en type clothing med en bestemt size