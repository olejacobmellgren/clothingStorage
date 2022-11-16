# Local Persistence

Modul som håndterer lagring av storage-objekter til og fra json formaterte filer.

Modulen inneholder de følgende klassene:

- ClothingDeserializer
- ClothingSerializer
- StorageDeserializer
- StorageSerializer
- ClothingStorageModule
- ClothingStoragePersistence

## ClothingDeserializer

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Clothing: 

- deserialize(JsonNode) -> Clothing:

## ClothingSerializer

### Metoder

- serialize(Clothing, JsonGenerator):

## StorageDeserializer

### Metoder

- deserialize(JsonParser, DeserializationContext) -> Storage: 

- deserialize(JsonNode) -> Storage: 

## StorageSerializer

### Metoder

- serialize(Storage, JsonGenerator):

## ClothingStorageModule

### Metoder

- ClothingStorageModule(): 

## ClothingStoragePersistence

### Metoder

- ClothingStoragePersistence():

- createJacksonModule() -> SimpleModule:

- createObjektMapper() -> ObjektMapper:

- readClothingStorage(Reader) -> Storage: 

- writeClothingStorage(Storage, Writer):

- setSaveFile(String): 

- getSaveFilePath() -> Path: 

- loadClothingStorage() -> Storage: 

- saveClothingStorage(): 
