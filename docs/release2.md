# Dokumentasjon for release 2

## Endringer fra release 1

Denne releasen hadde fokus på modularisering, arkitektur, kodekvalitet og utvidet dokumentasjon. Endringer som har blitt gjort:

- Prosjektet baserer seg nå på en 3-lags-arkitektur og der ulike moduler: **core**, **fxui** og **localpersistence** har blitt laget for å representere lagene
- Bruk av verktøyene Jacoco, Checkstyle og Spotbugs for å sjekke kodekvalitet
- Javadoc er skrevet for alle metoder
- Persistens til fil med JSON, og ikke til tekstfil

## Arbeidsvaner og arbeidsflyt

Gruppen har så langt fungert svært bra sammen og vi har tatt i bruk flere arbeidsmetoder fra den smidige utviklingsprosessen "Scrum". Gitlab har på mange måter hjulpet oss til å opprettholde gode arbeidsvaner og et godt system i denne prosessen.

Prosjektet som en helhet kan bli sett på som en "product-backlog", der ulike deler av prosjektet er i en rekkefølge som har blitt prioritert av fagansvarlig. Hver innlevering utgjør en "sprint", som har en "sprint-backlog" som er deler av "product-backlog". Gruppen har hatt møte i starten av hver innlevering og laget en "sprint-backlog" med utgangspunkt i et "sprint goal", altså målet for denne "sprinten". "Sprint goal" har blitt laget i form av en milepæl ("milestone") i Gitlab. Kodingsoppgaver ("issues") i Gitlab har utgjort "sprint-backlog" i Scrum-prosessen og har blitt knyttet til tilsvarende "sprint goal". I starten har også brukerhistorier blitt laget som beskriver funksjoner som ønskes at skal bli implementert i den "sprinten". Kodingsoppgaver har blitt knyttet til disse brukerhistoriene. 

I sprint-planleggingen har vi fordelt kodingsoppgavene på gruppemedlemmene, og gitt hver kodingsoppgave én ansvarlig i Gitlab. Dersom noen kodingsoppgaver besto av et samarbeid mellom to eller flere personer valgte vi å ikke gi denne kodingsoppgaven én enkelt ansvarlig. Dette valget kommer av at Gitlab ikke har muligheten til å gjøre flere personer ansvarlig for en kodingsoppgave. Derfor har vi for det meste prøvd å lage små kodingsoppgaver som kan bli utført av én person. Dersom vi i ettertid har sett at det kreves flere personer til en kodingsoppgave, har vi tatt i bruk parprogrammering ved bruk av LiveShare for å få til dette. Samarbeidet har blitt kommentert i tilhørende kodingsoppgave i Gitlab, eller blitt spesifisert i "commiten" til arbeidet i den branchen det har blitt gjennomført.

Møtene mellom gruppe har gått veldig bra. Vi har hatt et 4-timers møte hver mandag, som har blitt utvidet til 6-timer dersom vi har sett at dette er nødvendig. I tillegg til dette, har vi klart å opprettholde en god dialog gjennom ukene ved å ha en gruppechat for gruppemedlemmene. Ettersom vi ikke hadde mange dager vi hadde mulighet for å møtes samlet fysisk, har gruppechaten blitt brukt for å holde en daglig dialog rundt arbeidet. Dette har da representert "daily scrum meetings" og vi har oppdatert hverandre om hva vi har gjort, hva som skal bli gjort og om det har oppstått noen problemer. Det hadde vært ønskelig å ha flere fysiske møter, men ettersom timeplanene til gruppemedlemmene krasjet i stor grad, var ikke dette mulig. Det lange møte på mandag har blitt brukt til sprint-planlegging dersom det er på starten av en sprint og annet arbeid som er velegnet å gjøre samlet. 

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