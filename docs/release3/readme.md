# Dokumentasjon for release 3

## Endringer fra release 2

Denne releasen hadde fokus på REST-API, utvidet funksjonalitet og kodekvalitet. Endringer som har blitt gjort er:

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

For å løse brukerhistorie 6 er det lagt til filtrering på merke, pris, type klær og om varen er på salg i Storage-klassen til **core** modulen. Det er lagt til en nedtrekksmeny for filter der man kan velge mellom å sortere på laveste pris, høyeste pris, klestype, merke og salg. Dersom man velger type eller merke vil man få en ekstra nedtrekksmeny der man kan velge spesifikt hvilket type/merke man vil filtrere på.

## Statistikk

Sammen med denne releasen ble det implementert en egen side for statistikk for å løse brukerhistorie 7, der brukeren kan få oversikt over total antall klær og total verdi. I tillegg er det et søylediagram som viser antall av ulike typer klær hvis man har valgt all Clothes, og antall av hver størrelse dersom man har valgt en spesiell type klær. Logikken for dette ligger i **core** modulen mens knappene er implementert i **ui** modulen. 