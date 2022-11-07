# Restserver

Dette er en modul som håndterer Service laget, aka. rest API, med formål å sende data til og fra service laget.
Modulen inneholder de følgende klassene:

- ClothingStorageApplication
- ClothingStorageController
- ClothingStorageService

## ClothingStorageApplication

Application klasse for spring boot serveren. Den starter serveren.

### Methods

- main(String...): Main method for starting the application. It accesses the method run in SpringApplication with
  GetFitApplication.class and the given strings as arguments.

- dummy() -> void: Has no function other than to pass checkstyle. Alternative breaks springboot.

## ClothingStorageController

TODO

Controller class for handling the get and post requests. It consists of a GetFitService.

### Methods

TODO

The payload, response and endpoints shown in **[Schema](/get-fit/schema.md/)**. The methods are divided into post, get
and exception. The tags for the categories are simplified, and they are not equal to the tags in the code.

@Get

- getLogEntry(String) -> String: Gets the entry given by the function argument (id). It is returned as a string
  according to Schema.md.

- getFilters() -> String: Gets the possible ways to filter logEntry as a string according to Schema.md.

- getListOfLogEntries(String, String, String, String, String) -> String: It gets the entries which fit into the function
  arguments (filters and sorting criteria). The parameters are sorting type (default date), reverse (default false),
  category (not required), subcategory (not required) and date (for filtering, not required). It returns a string with
  the different entries according to Schema.md.

- getStatisticsData(String, String) -> String: Get statistics based on the date and the category. The parameters are the
  date to filter by and the category to filter by. The category is not required.

- getChartData(String) -> String: Get the entry count for the statistics chart. The parameter is date, and it returns the
  count for each category filtered by the given date.

@Post

- addLogEntry(String) -> String: It adds the entry to the entry manager in GetFitService.

- editLogEntry(String, String): The method gets an entry id and a logEntry as a String (according to Schema.md payload)
  and replaces the already existing entry with this entry, but keeps the same id.

- removeLogEntry(String): The logEntry with the same id as the input, is removed from the entryManager in GetFitService.

@ExceptionHandler

- handleIllegalArgumentException(IllegalAccessException) -> String: Returns the exception message as a String.
- handleIOException(IOException) -> String: Returns the exception message as a String.
- handleIllegalArgumentException(NoSuchElementException) -> String: Returns the exception message as a String.

## ClothingStorageService

Gir serveren tilgang til modulene og funksjonene fra core og localpersistence.

### Methods

- ClothingStorageService(): Lager et nytt Storage enten med et gitt Storage eller et tomt et hvis ingen Storage er gitt.

- autoSaveTodoModel() -> void: Lagerer alle endringer i Storage til disk.

- getStorage() -> Storage: Lar de andre klassene få tilgangen til dette Storage
- setStorage() -> void: Lar de andre klassene endre på dette storaget.
