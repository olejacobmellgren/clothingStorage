# Local Persistence

Modul som håndterer lagring av storage-objekter til og fra JSON formaterte filer.

Modulen inneholder følgende klasser:

- ClothingDeserializer
- ClothingSerializer
- StorageDeserializer
- StorageSerializer
- ClothingStorageModule
- ClothingStoragePersistence

## ClothingDeserializer

Spesifiserer hvordan Clothing-objektet skal leses fra JSON-format.

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Clothing: deserialiserer et Clothing-objekt fra JSON- format

- deserialize(JsonNode) -> Clothing: deserialiserer en JsonNode til et Clothing-objekt

## ClothingSerializer

Spesifiserer hvordan Clothing-objektet skal skrives til JSON format.

### Metoder

- serialize(Clothing, JsonGenerator): Serialiserer et Clothing-objekt til JSON format

## StorageDeserializer

Spesifiserer hvordan Storage-objektet skal leses fra JSON-format.

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Storage: deserialiserer et Storage-objekt fra JSON-format

- deserialize(JsonNode) -> Storage: deserialiserer en JsonNode til et Storage-objekt

## StorageSerializer

Spesifiserer hvordan Storage-objektet skal skrives til JSON-format.

### Metoder

- serialize(Storage, JsonGenerator): serialiserer et Storage-objekt til JSON-format.

## ClothingStorageModule

Lager en Jackson module som konfigurerer JSON serialisering og deserialisering av storage forekomster.

### Metoder

- ClothingStorageModule(): Initialiserer clothingStorageModule med passende serializers og deserializers.

## ClothingStoragePersistence

Wrapper-klasse for JSON-serialisering, for å unngå direkte kompilerings problemer med Jackson for andre moduler.

### Metoder

- ClothingStoragePersistence(): Initialiserer ClothingStoragePersistence 

- createJacksonModule() -> SimpleModule: lager ClothingStorage sin Jackson module

- createObjectMapper() -> ObjectMapper: lager en objekt mapper fra ClothingStorageModule

- readClothingStorage(Reader) -> Storage: leser Storage-objektet fra fil

- writeClothingStorage(Storage, Writer): Skriver Storage-objektet til fil 

- setSaveFile(String): Setter saveFilePath-variabelen til gitt path

- getSaveFilePath() -> Path: henter saveFilePath-variabelen, altså pathen som er satt som lagringsplass

- loadClothingStorage() -> Storage: henter Storage-objektet fra fil 

- saveClothingStorage(): Lagrer Storage-objektet til fil
