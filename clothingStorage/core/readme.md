# Core

Modul som inneholder grunnklassene til prosjektet.

Modulen inneholder de følgende klassene:

- Clothing
- Storage
- StorageStatistics

## Avhengigheter

- **junit-jupiter-api** - JUnit API
- **junit-jupiter-engine** - Brukes for å kjøre lage Junit-testene

## Plugins

- **maven-compiler-plugin** - For å kompilere kildekoden
- **maven-surefire-plugin** - For kjøring av Junit-tester
- **jacoco-maven-plugin** - For å sjekke testdekningsgrad med **Jacoco**
- **spotbugs-maven-plugin** - For å sjekke etter bugs i koden med **SpotBugs**
- **maven-checkstyle-plugin** - For å sjekke kodekvalitet med **Checkstyle**

## Clothing

Objekt-klassen for appen. Dette er appens mest grunnleggende objekt.

### Metoder

- Clothing(String, String, char, double): initialiserer et clothing-objekt

- setType(): setter type-variabelen til clothing-objektet

- isValidType(String): sjekker om en type er gyldig

- setBrand(String): setter brand-variabelen til clothing-objektet

- isValidBrand(String) -> boolean: sjekker om en brand er gyldig

- setSize(char): setter size-variabelen til clothing-objektet

- setPrize(double, boolean): setter price-variabelen til clothing-objektet

- setPriceAfterDiscount(double): setter prisen til et clothing-objekt etter at et discout er lagt til

- removeDiscount(): fjerner discounten fra clothing-objektet og setter pricen tilbake til original prisen

- isValidDiscount(double) -> boolean: sjekker om gitt discount er en gyldig discount

- setDiscount(double): setter discount-variabelen til clothing-objektet

- setName(): setter navnet til clothing-objektet

- getType() -> String: henter type-variabelen til clothing-objektet

- getBrand() -> String: henter brand-variabelen til clothing-objektet

- getSize() -> char: henter size-variabelen til clothing-objektet

- getPrize() -> double: henter price-variabelen til clothing-objektet

- isOnDiscount() -> boolean: sjekker om discount er ulik null

- getDiscount() -> double: henter discount-variabelen til clothing-objektet

- getName() -> String: henter name-variabelen til clothing-objektet

- toString() -> String: returnerer clothing-objektet som en string

- equalButDifferentPrice(Clothing) -> boolean: sjekker om to clothing-objekter har like attributer utenom price-variabelen

- equalButDifferentSize(Clothing) -> boolean: sjekker hvis to clothing-objekter har like attributer utenom size-variabelen

## Storage

Klassen for oppbevaring av Clothing-objekt. Dette er også en av appens mest grunnleggende klasser.

### Metoder

- Storage(): initialiserer et storage-objekt

- addNewClothing(Clothing, int): legger til et git antall like clothing-objekt til storage

- removeClothing(Clothing): fjerner et git antall like clothing-objekt fra storage

- increaseQuantity(Clothing, int): øker mengden av et clothing-objekt i storage med en gitt mengde

- decreseQuantity(Clothing, int): reduserer mengden av et clothing-objekt i storage med en gitt mengde

- updateQuantity(Clothing, int): oppdaterer mangden (quantity) av et clothing-objekt direkte

- storageDisplay() -> List<String>: itererer seg gjennom alle clothing-objektene i storage og formaterer de til en string tilpasset displayet i storage-page siden til appen

- priceDisplay() -> List<String>: itererer seg gjennom alle clothing-objektene i storage og formaterer de til en string tilpasset displayet i price-page siden til appen 

- isValidQuantity(int) -> boolean: sjekker om gitt mengde er gyldig 

- isClothingInStorage(Clothing) -> boolean: sjekker om clothing-objektet er i storage

- setIsSortedPricePage(boolean): setter om price-page er sortert eller ikke

- getIsSortedClothes() -> boolean: sjekker om price-page er sortert eller ikke

- getQuatity(Clothing) -> int: henter mengden av et clothing-objekt i storage 

- getAllClothings() -> LinkedHasMap<Clothing, Integer>: henter alle clothing-objektene i storage

- getSotedClothings() -> ArrayList<Clothing>: henter liste med clothing-objekt sortert i price-page

- addSortedClothing(Clothing): legger til et clothing-objekt til en sortert clothing liste

- sortOnLowestPrice(): sorterer storage-list basert på lavest pris

- sortOnHighestPrice(): sorterer storage-list basert på høyest pris

- filterOnBrand(String): filtrerer storage-list basert på brand

- filterOnType(String): filtrerer storage-list basert på type

- filterOnDiscount(): filtrerer storage-list basert på discount 

- getClothing(int) -> Clothing: henter clothing-objekt fra hashmap ved index

- getClothing(String) -> Clothing: henter clothing-objekt fra hashmap ved navn

- getClothingFromSortedClothes(int) -> Clothing: henter clothing-objekt fra sortert liste

- toString() -> String: returnerer storage-objektet som en tilpasset string


## StorageStatistics

Klassen for funksjonaliteten bak statistikk siden til appen.

### Metoder

- getTotalQuantity(Storage) -> int: henter total mengde av clothing i storage 

- getTotalValue(Storage) -> double: henter totalverdien til alle Clothing-objektene i storage

- getQuantityForType(Storage, String) -> int: henter mengden av en type clothing

- getQuantityForTypeAndSize(Storage, String, char) -> int: henter mengden av en type clothing med en bestemt size