# Dokumentasjon for release 3

## Endringer fra release 2

Denne releasen hadde fokus på REST-API, utvidet funksjonalitet og kodekvalitet. Endringer som har blitt gjort:

- Prosjektet er utvidet med følgende nye mouler: **client**, **integrationtests**, **restserver** og **ui**
- Lagd REST-API vha. Springboot
- Lagd egen side for statistikk
- Lagt til filtrering
- UI endret til å være kompatibel med REST-APIet
- Javadoc er skrevet for alle metoder

## REST-API

Springboot er benyttet til å sette opp et REST-API. Serveren ligger i **restserver** modulen. Denne modulen håndterer HTTP requests fra den nye **client** modulen, og den er avhengig av **core** modulen. På den måten blir **ui** og **core** uavhengige av hverandre ettersom dataen håndteres av **client** modulen og restserveren.

Ettersom Spring brukes i stor grad av andre Java utviklere, er omfattende dokumentert og har et stort fellesskap (community), valgte gruppen å benytte Spring Boot til å sette opp REST-APIet. 

## Løsninger til brukerhistorie 6 og 7

Brukerhistoriene som ble lagt til i release 3 finnes i **[user-stories](/docs/release3/user-stories.md)**.

## Filtrering

For å løse brukerhistorie 6 er det lagt til filtrering på merke, pris, type klær og om varen er på salg i Storage-klassen til **core** modulen. 



## Statistikk-side

Sammen med denne releasen ble det implementert en egen side for statistikk, der brukeren kan få oversikt over for eksempel total antall klær eller fordelingen av hver type klesplagg. Logikken for dette ligger i **core** modulen. 

### StorageController-klassen og Storage.fxml

For å løse brukerhistorie 3, 4 og 5 måtte flere knapper legges til i ui-et. For brukerhistorie 3 var det behov for knapper for å øke og minke beholdning av vare, både med 1 og med spesifisert antall. For å øke og eller minke med spesifisert antall var det også behov for et tekstfelt. Brukerhistorie 4 krevde at vi la til knapp og tekstfelt, både for ny pris og for å legge til rabatt. Brukerhistorie 5 ble løst ved å legge til en knapp for å fjerne en vare. Deretter måtte disse knappene kobles opp mot grunnklassene gjennom StorageController.

### Clothing-klassen

For å løse brukerhistorie 4 måtte vi endre og legge til en del metoder i Clothing-klassen. Metodene gjorde det mulig å endre pris, legge til rabatt, og i tillegg fjerne rabatt og få tilbake originalpris.