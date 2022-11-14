# UI

Modul for å håndtere frontend av applikasjonen. Denne modulen inneholder følgende klasser:

- NewClothingPageController
- PricePageController
- StatisticsPageController
- StoragPageController
- StorageApp


## NewClothingPageController

Håndterer siden for å legge til et nytt Clothing-objekt.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen.

- getErrorMessage() -> String: Returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen.

- getConfirmMessage() -> String: Returnerer confirm-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen.

- showErrorMessage(String errorMessage) -> void: Vises en "pop-up" i ui-et med error-melding.

- showConfirmMessage(String confirmedMessage) -> void: Vises en "pop-up" i ui-et med confirmed-melding.

- handleReset() -> void: Resetter alle felter i ui-et for å lage et nytt Clothing-objekt.

- handleCancel() -> void: Bytter side tilbake til Storage-siden.

- handleOk() -> void: Legger til et Clothing-Objekt til Storage og bytter tilbake til Storage-siden der det vises i listen i ui-et.


## PricePageController

Håndterer siden med oversikt over priser der man kan sortere og filtrere Clothing-objekter.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen.

- getErrorMessage() -> String: Returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen.

- showErrorMessage(String errorMessage) -> void: Vises en "pop-up" i ui-et med error-melding.

- updatePriceList() -> void: Oppdaterer visningen av priser i listen i ui-et.

- handleStoragePageButton() -> void: Bytter til Storage-siden.

- handleStatisticsPageButton() -> void: Bytter til statistikk-siden.

- handleFilterChoice() -> void: Sjekker hvilket filter man har valgt og viser ekstra choicebox dersom man har valgt "Brand" eller "Type". Ellers fjernes disse.

- handleConfirmFilter() -> void: Bekrefter valget av filter og viser filtrert/sortert liste i ui-et.

- handleResetFilter() -> void: Resetter filteret og oppdaterer prisene i listen i ui-et til å være usortert.

- handleConfirmNewPrice() -> void: Oppdaterer prisen i ui-et til et valgt Clothing-objekt fra listen.

- handleConfirmDiscount() -> void: Oppdaterer prisen i ui-et til et valgt Clothing-objekt fra listen basert på rabatt.

- handleRemoveDiscount() -> void: Fjerner rabatten til et valgt Clothing-objekt fra listen i ui-et, og oppdaterer pris. Viser feilmelding dersom det ikke er på rabatt.


## StatisticsPageController

Håndterer siden med statistikk for Storage. Man kan bytte mellom ulike diagrammer og se total verdi og antall klær på lager.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen.

- setDiagramForAllClothes() -> void: Endrer diagrammet til å vise antall for alle typer klær.

- setTotalQuantityLabel() -> void: Viser tekst med totalt antall av klær.

- handleStoragePageButton() -> void: Bytter til Storage-siden.

- handlePricePageButton() -> void: Bytter til pris-siden.

- handleTypeForDiagram() -> void: Endrer diagrammet til å vise fordelinger av størrelser for en type Clothing, basert på valget i choicebox.


## StoragePageController

Håndterer siden der man kan øke og minke antall av ulike Clothing-objekter, eller fjerne de helt.

### Metoder

- setStorage(Storage storage) -> void: setter storage til gitt storage, hjelpefunksjon for controller-test klassen.

- getErrorMessage() -> String: Returnerer error-melding for controller som vises i ui-et, hjelpefunksjon for controller-test klassen.

- showErrorMessage(String errorMessage) -> void: Vises en "pop-up" i ui-et med error-melding.

- updateStorageList() -> void: Oppdaterer visningen av Clothing-objekter i listen i ui-et med nytt antall eller at noe har blitt fjernet.

- handlePricePageButton() -> void: Bytter til pris-siden.

- handleStatisticsPageButton() -> void: Bytter til statistikk-siden.

- handleNewClothingItem() -> void: Bytter til siden for å legge til nytt Clothing-objekt.

- handleRemoveClothingItem() -> void: Fjerner et Clothing-objekt og oppdaterer listen i ui-et.

- handleIncreaseByOne() -> void: Øker et valgt Clothing-objekt i ui-et med 1.

- handleDecreaseByOne() -> void: Minker et valgt Clothing-objekt i ui-et med 1.

- handleAddQuantity() -> void: Øker et valgt Clothing-objekt i ui-et med et antall som er spesifisert i et tesktfelt.

- handleRemoveQuantity() -> void: Minker et valgt Clothing-objekt i ui-et med et antall som er spesifisert i et tesktfelt.


## StorageApp

Klasse som starter appen. "Extends Application".

### Metoder

- start(Stage stage) -> void: Starter applikasjonen. Parameteret er "main-stage"

- main(String[] args) -> void: Kjører applikasjonen.