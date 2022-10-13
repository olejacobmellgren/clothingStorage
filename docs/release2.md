# Dokumentasjon for release 2

## Endringer fra release 1

Denne releasen hadde fokus på modularisering, arkitektur, kodekvalitet og utvidet dokumentasjon. Endringer som har blitt gjort:

- Prosjektet baserer seg nå på en 3-lags-arkitektur og der ulike moduler: **core**, **fxui** og **localpersistence** har blitt laget for å representere lagene
- Bruk av verktøyene Jacoco, Checkstyle og Spotbugs for å sjekke kodekvalitet
- Javadoc er skrevet for alle metoder
- Persistens til fil med JSON, og ikke til tekstfil

## Persistens gjennom implisitt lagring

For den lokale persistensen til appen vår har vi valgt implisitt lagring. Grunnen til dette er at det generelt er forventet at slike apper beholder og lagrer dataen som blir lagt inn, også etter man lukker den. Dersom bruker er nødt til å laste opp lagerbeholdningen av klær hver gang for å gjøre endringer vil dette kunne ta unødvendig med tid og gi dårligere flyt i bruken av appen. En annen fordel med å lagre implisitt er at bruker slipper å lagre når appen skal lukkes. Dermed slipper man å risikere at man for eksempel glemmer å lagre og alle endringer blir forkastet.

På en annen side kunne det vært fordelsmessig å bruke en dokumentmetafor dersom man ønsker at appen skal kunne lagre flere ulike lagere for klær. Men ettersom det er tenkt at appen kun skal brukes av èn butikk for deres beholdning av klær, vil implisitt lagring være mer hensiktsmessig.

## Verktøy for sjekking av testdekningsgrad og kodekvalitet

Sammen med denne releasen ble det implementert ulike verktøy for sjekk av både testdekningsgrad og kodekvalitet. 

### Jacoco

Jacoco ble brukt for å sjekke testdekningsgrad. Dette verktøyet er implementert for alle moduler og sjekker i hvilken grad testene dekker koden som har blitt skrevet. Jacoco er implementert som "plugin" for maven i pom.xml-fil, med versjon 0.8.7. Man kan sjekke testdekningsgrad etter man har kjørt `mvn install` og navigerer til den modulen man vil sjekke for og inn i **target/site/jacoco/index.html** denne kan man åpne i en "live server" og man får oversikten over hvor mange prosent av koden som er dekket av testene for ulike klasser.

### Checkstyle

Checkstyle har blitt implementert for å sjekke kodekvaliteten. Dette verktøyet skal sjekke at Java-koden som har blitt skrevet følger vanlig kodestandard. Checkstyle er lagt inn som "plugin" for maven i pom.xml, der checkstyle bruker versjon 9.0. Både Checkstyle og Spotbugs sier ifra om feil når man kjører `mvn install` eller `mvn verify`. Vi valgte å bruke google_checks.xml som mal for vår egen checkstyle og ga denne noen hensiktsmessige modifikasjoner. Checkstylen som brukes i vårt prosjekt er altså **[checks.xml](../clothingStorage/config/checkstyle/checks.xml)**. Endringene som ble gjort er:

- Error i steden for warning
  - Forklaring på valg: Ved å endre til at checkstyle violations gir error vil man ikke kunne kjøre `mvn install` eller `mvn verify` uten at alle checkstyles er fikset.
    I tillegg vil man få et totalt antall checkstyles i terminalen, i motsetning til hvis man bruker warnings
- Innhopp på 4 mellomrom
  - Forklaring på valg: google_checks.xml sin standard er at et innhopp på 2 mellomrom skal brukes. Siden hele gruppen bruker et innhopp på 4, endret vi dette i filen.
- Pakkenavn følger et annet mønster
  - Forklaring på valg: Pakkenavnene våre slik de er nå fører til checkstyle violations. Det er ikke tillat med stor bokstav i et pakkenavn med google_checks.xml. I steden for å endre på pakkenavnene våre endret vi på mønsteret som er tillat slik at checkstyle violations ikke dukker opp for dette.

### Spotbugs

Slik som checkstyle er også Spotbugs implementert for å sjekke kodekvaliteten. Spotbugs ser etter feil(bugs) i koden og er også lagt inn som "plugin" for maven i ytterste pom-fil. Spotbugs bruker versjon 4.7.2. I filen **[exclude.xml](../clothingStorage/config/spotbugs/exclude.xml)** ligger Spotbugs som har blitt valgt å bli ignorert. Disse har blitt ignorert av ulike årsaker:

- Bug: "EI_EXPOSE_REP2"
  - Hva klages på: Lagrer Storage i Controller og endrer på dens interne tilstand
  - Forklaring på ignorering: Vi har behov for å lagre tilstanden til Storage som kan bli oppdatert gjennom interaksjon med ui-et. Controller må derfor lagre dette.
- Bug: "SA_LOCAL_SELF_COMPARISON"
  - Hva klages på: Sjekker om JsonNode instanceof ObjectNode
  - Forklaring på ignorering: Vi ønsker å sjekke om noden er ObjectNode slik at vi vet at vi kan bruke de tilhørende metodene
- Bug: "DE_MIGHT_IGNORE"
  - Hva klages på: Exception kan utløses i initialize() for controller som blir ignorert når man prøver å laste opp innhold fra json-fil
  - Forklaring på ignorering: Vi ønsker at Exception skal bli ignorert dersom det ikke er noe innhold å laste fra json-fil. Dette hindrer at man får feilmelding ved åpning av appen første gang
- Bug: "REC_CATCH_EXCEPTION"
  - Hva klages på: Kommer av samme valg som ble tatt over, her klages det på at det ikke blir gjort noe når man catcher exception
  - Forklaring på ignorering: Se forklaring på **Bug: "DE_MIGHT_IGNORE"**


## Løsninger til brukerhistorie 3, 4 og 5

### StorageController-klassen og Storage.fxml

For å løse brukerhistorie 3, 4 og 5 måtte flere knapper legges til i ui-et. For brukerhistorie 3 var det behov for knapper for å øke og minke beholdning av vare, både med 1 og med spesifisert antall. For å øke og eller minke med spesifisert antall var det også behov for et tekstfelt. Brukerhistorie 4 krevde at vi la til knapp og tekstfelt, både for ny pris og for å legge til rabatt. Brukerhistorie 5 ble løst ved å legge til en knapp for å fjerne en vare. Deretter måtte disse knappene kobles opp mot grunnklassene gjennom StorageController.

### Clothing-klassen

For å løse brukerhistorie 4 måtte vi endre og legge til en del metoder i Clothing-klassen. Metodene gjorde det mulig å endre pris, legge til rabatt, og i tillegg fjerne rabatt og få tilbake originalpris.

## Planlagte endringer som ikke ble gjennomført i release2

- Dele opp kontroller og fxml-fil i flere kontrollere og med tilhørende fxml-filer. En mulighet for splitting vi har tenkt på:
  - Storage-siden
  - Pris-siden
  - Siden for å legge til nye clothing-objekter
- Å kunne filtrere clothing-objekter på pris-siden etter merke, pris, osv
- Når man legger til to clothing-objekter av samme type og merke, men forskjellig størrelse, er det ønskelig å disse skal vises med samme pris på pris-siden. For mer detaljer se **Lager-side vs pris-side** i [readme-md](clothingStorage/../../clothingStorage/readme.md)