# UI

Modul for å håndtere frontend av applikasjonen. Denne modulen inneholder følgende klasser:

- NewClothingPageController
- PricePageController
- StatisticsPageController
- StoragPageController
- StorageApp

## Avhengigheter

- **core** - Kjernelogikk
- **localpersistence** - Fillagring
- **client** - Klient-siden
- **javafx-controls** - JavaFX-bibliotek
- **javafx-fxml** - Brukes i fxml-filer
- **testfx-core** - Trengs for å teste applikasjonen vha. TestFX
- **testfx-junit5** - Trengs for å teste applikasjonen vha. TestFX
- **junit-jupiter-api** - JUnit API
- **junit-jupiter-engine** - Brukes for å kjøre Junit-testene

## Plugins

- **maven-compiler-plugin** - For å kompilere kildekoden
- **maven-surefire-plugin** - For kjøring av Junit-tester
- **jacoco-maven-plugin** - For å sjekke testdekningsgrad med **Jacoco**
- **spotbugs-maven-plugin** - For å sjekke etter bugs i koden med **SpotBugs**
- **maven-checkstyle-plugin** - For å sjekke kodekvalitet med **Checkstyle**
- **javafx-maven-plugin** - For å kunne kjøre JavaFx-applikasjonen
- **jpackage-maven-plugin** - For å lage en executable fil utifra target/clothingStorage mappen


## NewClothingPageController

Håndterer siden for å legge til et nytt Clothing-objekt.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen

- getErrorMessage() -> String: returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen

- getConfirmMessage() -> String: returnerer confirm-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen

- showErrorMessage(String errorMessage) -> void: en "pop-up" vises i ui-et med error-melding

- showConfirmMessage(String confirmedMessage) -> void: en "pop-up" vises i ui-et med confirmed-melding

- handleReset() -> void: resetter alle felter i ui-et for å lage et nytt Clothing-objekt

- handleCancel() -> void: bytter side tilbake til Storage-siden

- handleOk() -> void: legger til et Clothing-objekt til Storage og bytter tilbake til Storage-siden der det vises i listen i ui-et


## PricePageController

Håndterer siden med oversikt over priser der man kan sortere og filtrere Clothing-objekter.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen

- getErrorMessage() -> String: returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen

- showErrorMessage(String errorMessage) -> void: en "pop-up" vises i ui-et med error-melding

- updatePriceList() -> void: oppdaterer visningen av priser i listen i ui-et

- handleStoragePageButton() -> void: bytter til Storage-siden

- handleStatisticsPageButton() -> void: bytter til statistikk-siden

- handleFilterChoice() -> void: sjekker hvilket filter man har valgt og viser ekstra choicebox dersom man har valgt "Brand" eller "Type". Ellers fjernes disse

- handleConfirmFilter() -> void: bekrefter valget av filter og viser filtrert/sortert liste i ui-et

- handleResetFilter() -> void: resetter filteret og oppdaterer prisene i listen i ui-et til å være usortert

- handleConfirmNewPrice() -> void: oppdaterer prisen i ui-et til et valgt Clothing-objekt fra listen

- handleConfirmDiscount() -> void: oppdaterer prisen i ui-et til et valgt Clothing-objekt fra listen basert på rabatt

- handleRemoveDiscount() -> void: fjerner rabatten til et valgt Clothing-objekt fra listen i ui-et, og oppdaterer pris. Viser feilmelding dersom det ikke er på rabatt


## StatisticsPageController

Håndterer siden med statistikk for Storage. Man kan bytte mellom ulike diagrammer og se total verdi og antall klær på lager.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen

- setDiagramForAllClothes() -> void: endrer diagrammet til å vise antall for alle typer klær

- setTotalQuantityLabel() -> void: viser tekst med totalt antall av klær

- handleStoragePageButton() -> void: bytter til Storage-siden

- handlePricePageButton() -> void: bytter til pris-siden

- handleTypeForDiagram() -> void: endrer diagrammet til å vise fordelinger av størrelser for en type Clothing, basert på valget i choicebox


## StoragePageController

Håndterer siden der man kan øke og minke antall av ulike Clothing-objekter, eller fjerne de helt.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen

- getErrorMessage() -> String: returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen

- showErrorMessage(String errorMessage) -> void: en "pop-up" vises i ui-et med error-melding.

- updateStorageList() -> void: oppdaterer visningen av Clothing-objekter i listen i ui-et med nytt antall eller at noe har blitt fjernet

- handlePricePageButton() -> void: bytter til pris-siden

- handleStatisticsPageButton() -> void: bytter til statistikk-siden

- handleNewClothingItem() -> void: bytter til siden for å legge til nytt Clothing-objekt

- handleRemoveClothingItem() -> void: fjerner et Clothing-objekt og oppdaterer listen i ui-et

- handleIncreaseByOne() -> void: øker et valgt Clothing-objekt i ui-et med 1

- handleDecreaseByOne() -> void: minker et valgt Clothing-objekt i ui-et med 1

- handleAddQuantity() -> void: øker et valgt Clothing-objekt i ui-et med et antall som er spesifisert i et tesktfelt

- handleRemoveQuantity() -> void: minker et valgt Clothing-objekt i ui-et med et antall som er spesifisert i et tesktfelt


## StorageApp

Klasse som starter appen. "Extends Application".

### Metoder

- start(Stage stage) -> void: Starter applikasjonen. Parameteret er "main-stage"

- main(String[] args) -> void: kjører applikasjonen