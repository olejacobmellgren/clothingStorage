# Config

Denne mappen inneholder definert format for checkstyles og en liste med spotbugs som skal ignoreres. Det er skrevet mer detaljerte beskrivelser av verktøyene under.

## Checkstyle

Checkstyle har blitt implementert for å sjekke kodekvaliteten. Dette verktøyet skal sjekke at Java-koden som har blitt skrevet følger vanlig kodestandard. Checkstyle er lagt inn som "plugin" for maven i pom.xml, der checkstyle bruker versjon 9.0. Både Checkstyle og Spotbugs sier ifra om feil når man kjører `mvn install` eller `mvn verify`. Vi valgte å bruke google_checks.xml som mal for vår egen checkstyle og ga denne noen hensiktsmessige modifikasjoner. Checkstylen som brukes i vårt prosjekt er altså **[checks.xml](/clothingStorage/config/checkstyle/checks.xml)**. Endringene som ble gjort er:

- Error i steden for warning
  - Forklaring på valg: Ved å endre til at checkstyle violations gir error vil man ikke kunne kjøre `mvn install` eller `mvn verify` uten at alle checkstyles er fikset.
    I tillegg vil man få et totalt antall checkstyles i terminalen, i motsetning til hvis man bruker warnings
- Innhopp på 4 mellomrom
  - Forklaring på valg: google_checks.xml sin standard er at et innhopp på 2 mellomrom skal brukes. Siden hele gruppen bruker et innhopp på 4, endret vi dette i filen.
- Pakkenavn følger et annet mønster
  - Forklaring på valg: Pakkenavnene våre slik de er nå fører til checkstyle violations. Det er ikke tillat med stor bokstav i et pakkenavn med google_checks.xml. I steden for å endre på pakkenavnene våre endret vi på mønsteret som er tillat slik at checkstyle violations ikke dukker opp for dette.

## Spotbugs

Slik som checkstyle er også Spotbugs implementert for å sjekke kodekvaliteten. Spotbugs ser etter feil(bugs) i koden og er også lagt inn som "plugin" for maven i ytterste pom-fil. Spotbugs bruker versjon 4.7.2. I filen **[exclude.xml](/clothingStorage/config/spotbugs/exclude.xml)** ligger Spotbugs som har blitt valgt å bli ignorert. Disse har blitt ignorert av ulike årsaker:

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