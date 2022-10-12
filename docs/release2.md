# Dokumentasjon for release 2

## Endringer fra release 1

Denne releasen hadde fokus på modularisering, arkitektur, kodekvalitet og utvidet dokumentasjon. Endringer som har blitt gjort:

- Prosjektet baserer seg nå på en 3-lags-arkitektur og der ulike moduler: **core**, **fxui** og **localpersistence** har blitt laget for å representere lagene
- Bruk av verktøyene Jacoco, Checkstyle og Spotbugs for å sjekke kodekvalitet
- Javadoc er skrevet for alle metoder
- Persistens til fil med JSON, og ikke til tekstfil

## Verktøy for sjekking av testdekningsgrad og kodekvalitet

Sammen med denne releasen ble det implementert ulike verktøy for sjekk av både testdekningsgrad og kodekvalitet. 

### Jacoco

Jacoco ble brukt for å sjekke testdekningsgrad. Dette verktøyet er implementert for alle moduler og sjekker i hvilken grad testene dekker koden som har blitt skrevet. Jacoco er implementert som "plugin" for maven i pom.xml-fil, med versjon 0.8.7.

### Checkstyle og Spotbugs

Disse to verktøyene har blitt implementert for å sjekke kodekvaliteten. Checkstyle skal sjekke at Java-koden som har blitt skrevet følger vanlig kodestandard, og Spotbugs ser etter feil(bugs) i koden. Verktøyene er lagt inn som "plugins" for maven i pom.xml, der checkstyle bruker versjon 9.0 og spotbugs bruker versjon 4.7.2.

## Løsninger til brukerhistorie 3, 4 og 5

### StorageController-klassen og Storage.fxml

For å løse brukerhistorie 3, 4 og 5 måtte flere knapper legges til i ui-et. For brukerhistorie 3 var det behov for knapper for å øke og minke beholdning av vare, både med 1 og med spesifisert antall. For å øke og eller minke med spesifisert antall var det også behov for et tekstfelt. Brukerhistorie 4 krevde at vi la til knapp og tekstfelt, både for ny pris og for å legge til rabatt. Brukerhistorie 5 ble løst ved å legge til en knapp for å fjerne en vare. Deretter måtte disse knappene kobles opp mot grunnklassene gjennom StorageController.

### Clothing-klassen

For å løse brukerhistorie 4 måtte vi endre og legge til en del metoder i Clothing-klassen. Metodene gjorde det mulig å endre pris, legge til rabatt, og i tillegg fjerne rabatt og få tilbake originalpris.