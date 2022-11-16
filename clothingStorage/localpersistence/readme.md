# Local Persistence

Modul som håndterer lagring av storage-objekter til og fra JSON formaterte filer.

Modulen inneholder de følgende klassene:

- ClothingDeserializer
- ClothingSerializer
- StorageDeserializer
- StorageSerializer
- ClothingStorageModule
- ClothingStoragePersistence

## ClothingDeserializer

Spesifiserer hvordan clothing-objektet skal leses fra JSON format.

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Clothing: deserialiserer et clothing-objekt fra json format.

- deserialize(JsonNode) -> Clothing: deserialiserer en JsonNode til et clothing-objekt.

## ClothingSerializer

Spesifiserer hvordan clothing-objektet skal skrives til JSON format.

### Metoder

- serialize(Clothing, JsonGenerator): serialiserer et clothing-objekt til JSON format.

## StorageDeserializer

Spesifiserer hvordan storage-objektet skal leses fra JSON format.

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Storage: deserialiserer et storage-objekt fra JSON format.

- deserialize(JsonNode) -> Storage: deserialiserer en JsonNode til et storage-objekt.

## StorageSerializer

Spesifiserer hvordan storage-objektet skal skrives til JSON format.

### Metoder

- serialize(Storage, JsonGenerator): serialiserer et storage-objekt til JSON format.

## ClothingStorageModule

Lager en Jakson module som konfigurerer JSON serialisering og deserialisering av storage forekomster.

### Metoder

- ClothingStorageModule(): Initialiserer clothingStorageModule med passende serializers og deserializers.

## ClothingStoragePersistence

Wrapper-klasse for JSON-serialisering, for å unngå direkte kompilerings problemer med Jackson for andre moduler.

### Metoder

- ClothingStoragePersistence(): Initialiserer ClothingStoragePersistence. 

- createJacksonModule() -> SimpleModule: Lager ClothingStorage sin Jackson module.

- createObjektMapper() -> ObjektMapper: Lager en objekt maper fra ClothingStorageModule.

- readClothingStorage(Reader) -> Storage: Leser storage-objektet fra fil.

- writeClothingStorage(Storage, Writer): Skriver storage-objektet til fil. 

- setSaveFile(String): Setter saveFilePath-variabelen til gitt path. 

- getSaveFilePath() -> Path: Henter saveFilePath-variabelen. Aka henter pathen som er satt som lagringsplass

- loadClothingStorage() -> Storage: Henter storage-objektet fra fil. 

- saveClothingStorage(): Lagrer storage-objektet til fil.
