[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2251/gr2251.git)

# Clothing Storage - Group gr2251 project

Clothing Storage er en applikasjon for lagring av klær som er skrevet i Java. Prosjektet er laget av gruppe 2251 i emnet IT1901 ved NTNU. Det kan åpnes i gitpod ved å trykke på "Gitpod-Ready-to-Code" knappen over. Selve kodingsprosjektet ligger i mappen **clothingStorage**. Inne i denne ligger også en [readme-fil](clothingStorage/readme.md#ClothingStorage) som beskriver hva appen handler om og hva den er ment å gjøre. Her ligger blant annet også beskrivelse av **arkitektur** og alle **brukerhistorier**.

## Bygging

Prosjektet er konfigurert til å bygge med maven. Ved å kjøre `mvn install` i **clothingStorage** vil man forberede prosjektet for å kunne kjøre. Vær oppmerksom på at dette vil kompilere prosjektet og kjøre testene. Ui-testene kan ta litt tid, og kommer til å ta kontroll over musen. Ved å kjøre `mvn install -DskipTests` kan man forberede prosjektet uten å kjøre testene.

## Kjøring av applikasjonen

Etter man har utført en av kommandoene under **[Bygging](readme.md#bygging)** kan man kjøre applikasjonen enten lokalt eller med REST-api. 

- ### Kjøring med lokal lagring

Man kan kjøre applikasjonen med lokal lagring ved å navigere til **clothingStorage/ui** og kjøre `mvn javafx:run`, eller kjøre `mvn javafx:run -f ui/pom.xml` direkte i **clothingStorage**.

- ### Kjøring med REST-api

Man kan kjøre applikasjonen med REST-api ved å navigere til **clothingStorage/restserver** og kjøre `mvn spring-boot:run`eller kjøre `mvn spring-boot:run -f restserver/pom.xml`direkte i **clothingStorage**. Deretter må man åpne en ny terminal og kjøre `mvn javafx:run -Premoteapp -f ui/pom.xml` i **clothingStorage**.

## Testing

Testene for prosjektet kjøres ved `mvn test` i **clothingStorage**. Da vil først testene for alle modulene kjøres etter hverandre. Testene for **ui** vil åpne appen, ta kontroll over musen din, og deretter utføre en rekke tester som tester alle funksjonene i appen.

## Dokumentasjon 

I mappen **[docs](docs)** ligger de ulike release-dokumentasjonene for hver innlevering (**[release1](docs/release1/readme.md)**, **[release2](docs/release2/readme.md)** og **[release3](docs/release3/readme.md)**). I hver av releasene finnes **user-stories.md** som inneholder brukerhistoriene som kom i denne releasen. I **release2** finnes en beskrivelse av arbeidsvaner (**[workflow](docs/release2/workflow.md)**). I mappen **[release3](docs/release3/diagrams/)** ligger alle diagrammer for prosjektet i **[diagrams](docs/release3/diagrams)** og forespørslene som støttes av REST-tjenesten (**[restapi.md]**(docs/release3/restapi.md)).

Inne i hver modul finnes en readme-fil med en mer detaljert beskrivelse av avhengigheter, plugins og klasser med tilhørende metoder.
