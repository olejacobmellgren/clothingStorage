# Om ClothingStorage

Appen ClothingStorage har som hensikt å hjelpe klesbutikkeiere/-ansatte å holde kontroll på lagrene sine. Brukeren kan få en oversikt over klærne på lageret, legge til eller fjerne klær, endre priser, legge inn rabatter på klær og merker, samt mulighet for filtrering basert på klestype, merke, pris og rabatter.

## Illustrerende skjermbilder
Disse bildene viser hvordan vi tenker appen skal se ut når den er mer eller mindre ferdig.

<img src="other-documentation/images/profilepage.png" width="200"> <img src="other-documentation/images/marketpage.png" width="200">

# Arkitektur og modularisering

Appen baserer seg på en full 3-lags arkitektur bestående av domenelaget, brukergrensesnittlaget og persistenslaget. I domenelaget er kjernelogikken der man håndterer data og spesifiserer hva som kan gjøres med den. I brukergrensesnittlaget er ui-et som brukeren interagerer med. Persistenslaget håndterer persistens til fil der man definerer et filformat.

Med utgangspunkt i en slik arkitektur deles appen i et sett med moduler der hver modul har ansvar for ett spesifikt arkitekturlag. Modulene for denne appen er:

- core - består av java-klasser for håndtering av data i form av "clothing-items" og "storage" med "clothing-items"
- fxui - består av det visuelle brukergrensesnitt og controller for appen ved bruk av JavaFX og FXML
- localpersistence - består av lagring av dataen på en bestemt filformat ved bruk av JSON

Et diagram for denne arkitekturen og avhengigheten mellom de ulike modulene er vist i et diagram under.

<img src="other-documentation/architecture/architecture.png" width="500">

# Brukerhistorier

Under beskrives en rekke brukerhistorier som skal dekke funksjonalitet og krav til appen som ble beskrevet og vist over.

## Brukerhistorie 1: Oversikt over klær
Som ansatt i klesbutikk ønsker jeg å ha oversikt over klærne butikken har på lager, så jeg vet når jeg må bestille nye klær.

Bruker har behov for en oversikt over klærne som er på lager med mulighet for å legge til flere klær når det kommer nye leveranser. I tillegg må bruker ha oversikt over antall av hver klestype for å vite når hun må bestille mer.

Det er også ønskelig for ansatte å ha en separat liste med oversikt over prisene til ulike klesplagg. Disse vil variere avhengig av klesmerke.

### Viktig å se
- Oversikt(liste) over klærne på lager
- Oversikt(liste) over priser på klær avhengig av merker

### Viktig å kunne gjøre
- Legge til nye klær

## Brukerhistorie 2: Lese og skrive til filer
Som eier av klesbutikk ønsker jeg å kunne få filer med informasjon om klærne på lageret slik at jeg kan bruke filene når jeg må bestille flere klær eller til økonomiske rapporter.

Bruker har behov for å skrive til og lese fra filer for å kunne bruke disse videre til bestilling og rapporter.

### Viktig å se
- Knapp for å lese fra fil
- Knapp for å kunne skrive til fil

### Viktig å gjøre
- Lese fra fil
- Skrive til fil

## Brukerhistorie 3: Endre beholdning av vare
Som ansatt/eier av klesbutikk ønsker jeg å kunne øke antall av en type klær når butikken får inn nye varer. Jeg ønsker også å kunne minke antall varer i beholdningen når de blir solgt.

Bruker har behov for å øke og minke beholdning av varer.

### Viktig å se
- Oversikt(liste) over klærne på lager slik at man kan trykke på et klesplagg
- Tekstfelt for å skrive hvor mye beholdningen skal endres med
- Knapp for å øke beholdningen med gitt antall
- Knapp for å minke beholdningen med gitt antall

### Viktig å gjøre
- Øke antallet for det valgte klesplagget i listen
- Minke antallet for det valgte klesplagget i listen

## Brukerhistorie 4: Endre pris for vare
Som ansatt/eier av klesbutikk ønsker jeg å kunne endre prisen på en vare, enten dersom varen er på salg og det skal legges inn rabatt, eller om varen endrer pris fast. Det er også ønskelig å kunne fjerne rabatt og få tilbake original pris.

Bruker har behov for å endre pris for en vare gjennom rabatt eller helt ny pris, og fjerne rabatt.

### Viktig å se
- Oversikt(liste) over klærne på lager slik at man kan trykke på et klesplagg
- Tekstfelt for å skrive ny pris til vare og knapp for å bekrefte
- Tekstfelt for å skrive rabatt til vare og knapp for å bekrefte
- Knapp for å fjerne rabatt

### Viktig å gjøre
- Endre prisen for det valgte klesplagget i listen til gitt pris
- Endre prisen for det valgte klesplagget i listen med hensyn på gitt rabatt
- Fjerne rabatt og få tilbake originalpris

## Brukerhistorie 5: Fjerne vare
Som ansatt/eier av klesbutikk ønsker jeg å kunne å kunne fjerne en vare fra listen over beholdninger, enten dersom en ny vare ble lagt til feil, eller dersom de går ut av beholdning og butikken ikke får inn flere varer av denne typen.

Bruker har behov for fjerne vare fra liste.

### Viktig å se
- Oversikt(liste) over klærne på lager slik at man kan trykke på et klesplagg
- Knapp for å fjerne valgt klesplagg

### Viktig å gjøre
- Fjerne klesplagget fra listen
