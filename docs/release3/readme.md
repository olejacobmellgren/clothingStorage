# Dokumentasjon for release 3

Denne releasen hadde fokus på REST-API, utvidet funksjonalitet og kodekvalitet. 

## Endringer fra release 2

Endringer som har blitt gjort er:

- Prosjektet er utvidet med følgende nye mouler: **client**, **integrationtests** og **restserver**
- Lagde REST-API vha. Springboot
- Lagde egen side for statistikk
- La til filtrering på pris-siden
- UI endret til å være kompatibel både med og uten REST-APIet

## REST-API

Springboot er benyttet til å sette opp et REST-API. Serveren ligger i **restserver**-modulen. Denne modulen håndterer HTTP requests fra den nye **client**-modulen, og den er avhengig av **core**-modulen. På den måten blir **ui** og **core** uavhengige av hverandre ettersom dataen håndteres av **client**- og **restserver**-modulen.

Ettersom Spring brukes i stor grad av andre Java utviklere, er omfattende dokumentert og har et stort fellesskap (community), valgte gruppen å benytte Spring Boot til å sette opp REST-APIet. 

## Løsninger til brukerhistorie 6 og 7

Brukerhistoriene som ble lagt til i release 3 finnes i **[user-stories](/docs/release3/user-stories.md)**.

## Filtrering

For å løse brukerhistorie 6 er det lagt til filtrering på merke, pris, type klær og om varen er på salg i Storage-klassen på pris-siden. Dette ble gjort mulig gjennom endringer i **core**-modulen. Det er lagt til en nedtrekksmeny for filter der man kan velge mellom å sortere på laveste pris, høyeste pris, klestype, merke og salg. Dersom man velger type eller merke vil man få en ekstra nedtrekksmeny der man kan velge spesifikt hvilket type/merke man vil filtrere på. Når pris-siden er sortert er det fremdeles mulig å endre på pris eller legge til rabatt.

## Statistikk

Sammen med denne releasen ble det implementert en egen side for statistikk for å løse brukerhistorie 7, der brukeren kan få oversikt over total antall klær og total verdi. I tillegg er det et søylediagram som viser antall av ulike typer klær hvis man har valgt all Clothes, og antall av hver størrelse dersom man har valgt en spesiell type klær. Logikken for dette ligger i **core** modulen mens knappene er implementert i **ui** modulen. 

## Nye arbeidsvaner

Fra release 2 endret vi på hvordan master-branchen brukes. Denne branchen ble beskyttet slik at man ikke kan pushe direkte til denne. Dermed må medlemmer på gruppen opprette merge-requests for å klare å gjøre endringer på master-branchen.

I forbindelse med merge-requester lagde vi en merge-request-template slik at man enkelt kunne få opp en mal på merge-requesten. Malen inneholder ulike punkter som måtte fylles ut som en beskrivelse av merge-requesten. Denne templaten finnes i **[merge-request-templates](/.gitlab/merge_request_templates/merge_request_template.md)**. I tillegg lagde vi også issue-templates av samme grunn. Her kunne man enten ha en **[bug-template](/.gitlab/issue_templates/bug_template.md)** for kodingsoppgaver ("issues") som omhandlet bugs , eller en **[default-template](/.gitlab/issue_templates/default_template.md)** som ble brukt for alle andre typer kodingsoppgaver. På denne måten fikk alle kodingsoppgaver en mer utfyllende beskrivelse. Vi skrev også mer utfyllende commit-meldinger. Overskriften knyttet commiten til en kodingsoppgave (gjennom kodingsoppgavenummer) og beskrev commiten. Commit-bodyen inneholdt hva som ble gjort og hvorfor.

Arbeidet med en kodingsoppgave ble gjort en egen branch. Dette gjør det oversiktlig for andre på gruppen å se hva ulike branches brukes til, og hvilke kodingsoppgaver som er underveis. Navnet på branchen beskrev hva arbeidet på denne besto av. Når arbeidet med kodingsoppgaven var ferdig og alt fungerte som det skulle ble det opprettet en merge-request.

Kodevurdering ble tatt mye i bruk under denne releasen i merge-requests. Alle merge-requests har blitt nøye sjekket av andre på gruppen, og tilbakemeldinger ble gitt. På denne måten kunne vi få innsikt i hvordan andre på gruppen strukturerer koden sin, og holde oss oppdatert på hva som er blitt gjort.

I denne releasen ble det også gjort parprogrammering i enda større grad. Dette var hensiktsmessig for denne releasen fordi det var naturlig å dele arbeidet med REST-api i en klient- og server-del. Da arbeidet vi to og to, og sørget for å holde hverandre oppdaterte slik at vi klient- og server-del ville fungere sammen.