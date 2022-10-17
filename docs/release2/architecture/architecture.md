# Arkitektur

Appen baserer seg på en full 3-lags arkitektur bestående av domenelaget, brukergrensesnittlaget og persistenslaget. I domenelaget er kjernelogikken der man håndterer data og spesifiserer hva som kan gjøres med den. I brukergrensesnittlaget er ui-et som brukeren interagerer med. Persistenslaget håndterer persistens til fil der man definerer et filformat.

Med utgangspunkt i en slik arkitektur deles appen i et sett med moduler der hver modul har ansvar for ett spesifikt arkitekturlag. Modulene for denne appen er:

- core - består av java-klasser for håndtering av data i form av "clothing-items" og "storage" med "clothing-items"
- fxui - består av det visuelle brukergrensesnitt og controller for appen ved bruk av JavaFX og FXML
- localpersistence - består av lagring av dataen på en bestemt filformat ved bruk av JSON

Et diagram for denne arkitekturen og avhengigheten mellom de ulike modulene er vist i et diagram under.

<img src="other-documentation/../../other-documentation/architecture/architecture.png" width="500">