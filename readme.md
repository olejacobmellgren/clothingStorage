[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2251/gr2251.git)

# Clothing Storage - Group gr2251 project

Clothing Storage er en applikasjon for lagring av klær som er skrevet i Java. Prosjektet er laget av gruppe 2251 i emnet IT1901 ved NTNU. Det kan åpnes i gitpod ved å trykke på "Gitpod-Ready-to-Code" knappen over. Selve kodingsprosjektet ligger i mappen "clothingStorage". Inne i denne ligger også en readme-fil som beskriver hva appen handler om og hva den ment å gjøre.

## Bygging og kjøring av applikasjonen

Prosjektet er konfigurert til å bygge med maven. Ved å kjøre `mvn install` i **clothingStorage** vil man forberede prosjektet for å kunne kjøre. Vær oppmerksom på at dette vil kompilere prosjektet og kjøre testene. Ui-testene kan ta litt tid, og kommer til å ta kontroll over musen. Deretter kan man kjøre applikasjonen ved å navigere til **clothingStorage/fxui** og kjøre `mvn javafx:run` eller navigere til **clothingStorage** og kjøre `mvn javafx:run -f fxui/pom.xml`. Testene for prosjektet kjøres ved `mvn test` i **clothingStorage**.

## Dokumentasjon 

I mappen **[docs](docs)** ligger de ulike release-dokumentasjonene for hver innlevering (**[release1](docs/release1.md)**, **[release2](docs/release2.md)**, ...). I mappen **[other-documentation](docs)** ligger annen dokumentasjon som et diagram for arkitektur(**[architecture](other-documentation/architecture/architecture.png)**), illustrasjoner av design konseptet (**[design-concept](design-concept)**) og beskrivelse av arbeidsvaner (**[workflow](other-documentation/workflow.md)**).
