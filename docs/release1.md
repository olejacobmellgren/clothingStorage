# Dokumentasjon for innlevering 1

## Valg av app

Vi bestemte oss for å lage en app med et enkelt konsept for å sikre god kvalitet, samtidig som mulighetene er åpne for mer avanserte utvidelser. Det første møtet gikk til "brainstorming" av ideer for appen, og vi ble først enige om en bytteapp for planter og bøker. Da vi kom fram til at dette krevde flere brukere for å opprettholde, forsøkte vi å komme på nye ideer som unngår dette problemet. Vi kom deretter fram til en app som viser en oversikt over klær for en klesbutikk, med flere funksjoner (som beskrevet i readme.md-filen i kodingsprosjektet). Vi valgte å gå for denne appen fordi vi hadde gode tanker om hvordan den skulle bygges opp med klasser, metoder og muligheter for utvidelser.

## Fremgangsmåte for release 1

Etter å ha laget brukerhistorier lagde vi tilsvarende issues i GitLab. Vi fordelte deretter disse issuene likt på alle, og startet å jobbe. Vi hadde ukentlige møter hvor vi diskuterte issues som hadde blitt jobbet på i løpet av uken, og kom fram til eventuelle forbedringer/løsninger. I tillegg kontaktet vi hverandre over meldinger dersom noe ikke kunne vente til neste gruppemøte. Til slutt endte vi opp med det ferdige produktet for release 1.

## Løsninger til brukerhistorie

### - Clothing-klassen

Et klesplagg i en butikk har normalt et navn, merke, størrelse, pris og eventuelt en lapp om den er på salgs. Nettopp slik kom vi fram til hva attributtene til Clothing-klassen skulle være. Disse attributtene gjør det lettere for brukeren til å skille mellom forskjellige klesplagg, som er i tråd med brukerhistorie 1.

### - Storage-klassen

Et lager består som regel av flere hyller med varer. For å gjøre det oversiktlig for brukeren, valgte vi å vise de forskjellige klesplaggene som er på lager i form av en liste. For hvert klesplagg i listen vises de viktigste, tilhørende attributtene ved siden av. Slik kan brukeren få en god oversikt over lagerstatusen, som da gjør det lettere vite når hun trenger å bestille nye klesplagg slik som er beskrevet i brukerhistorie 1. Denne klassen inneholder også en metode for å legge til nye klesplagg til lageret.

### - Filhåndtering-klassen

Utifra brukerhistorie 2 måtte vi finne en måte å skrive og lese data fra en fil. Filhåndterings-klassen ble laget for å løse nettopp denne oppgaven, og skriver all relevant informasjon om hvert klesplagg i lageret på en fin måte. I selve appen kan dette lett gjøres ved å skrive inn filnavnet man ønsker og velge om man vil skrive eller lese fra denne filen. Slik kan brukeren bruke disse filene til rapporter og bestillinger.

