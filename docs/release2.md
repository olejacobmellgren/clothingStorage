# Dokumentasjon for release 1

## Arbeidsvaner og arbeidsflyt

Gruppen har så langt fungert svært bra sammen og vi har tatt i bruk flere arbeidsmetoder fra den smidige utviklingsprosessen "Scrum". Gitlab har på mange måter hjulpet oss til å opprettholde gode arbeidsvaner og et godt system i denne prosessen.

Prosjektet som en helhet kan bli sett på som en "product-backlog", der ulike deler av prosjektet er i en rekkefølge som har blitt prioritert av fagansvarlig. Hver innlevering utgjør en "sprint", som har en "sprint-backlog" som er deler av "product-backlog". Gruppen har hatt møte i starten av hver innlevering og laget en "sprint-backlog" med utgangspunkt i et "sprint goal", altså målet for denne "sprinten". "Sprint goal" har blitt laget i form av en milepæl ("milestone") i Gitlab. Kodingsoppgaver ("issues") i Gitlab har utgjort "sprint-backlog" i Scrum-prosessen og har blitt knyttet til tilsvarende "sprint goal". I starten har også brukerhistorier blitt laget som beskriver funksjoner som ønskes at skal bli implementert i den "sprinten". Kodingsoppgaver har blitt knyttet til disse brukerhistoriene. 

I sprint-planleggingen har vi fordelt kodingsoppgavene på gruppemedlemmene, og gitt hver kodingsoppgave én ansvarlig i Gitlab. Dersom noen kodingsoppgaver besto av et samarbeid mellom to eller flere personer valgte vi å ikke gi denne kodingsoppgaven én enkelt ansvarlig. Dette valget kommer av at Gitlab ikke har muligheten til å gjøre flere personer ansvarlig for en kodingsoppgave. Derfor har vi for det meste prøvd å lage små kodingsoppgaver som kan bli utført av én person. Dersom vi i ettertid har sett at det kreves flere personer til en kodingsoppgave, har vi tatt i bruk parprogrammering ved bruk av LiveShare for å få til dette. Samarbeidet har blitt kommentert i tilhørende kodingsoppgave i Gitlab, eller blitt spesifisert i "commiten" til arbeidet i den branchen det har blitt gjennomført.

Møtene mellom gruppe har gått veldig bra. Vi har hatt et 4-timers møte hver mandag, som har blitt utvidet til 6-timer dersom vi har sett at dette er nødvendig. I tillegg til dette, har vi klart å opprettholde en god dialog gjennom ukene ved å ha en gruppechat for gruppemedlemmene. Ettersom vi ikke hadde mange dager vi hadde mulighet for å møtes samlet fysisk, har gruppechaten blitt brukt for å holde en daglig dialog rundt arbeidet. Dette har da representert "daily scrum meetings" og vi har oppdatert hverandre om hva vi har gjort, hva som skal blit gjort og om det har oppstått noen problemer. Det hadde vært ønskelig å ha flere fysiske møter, men ettersom timeplanene til gruppemedlemmene krasjet i stor grad, var ikke dette mulig. Det lange møte på mandag har blitt brukt til sprint-planlegging dersom det er på starten av en sprint og annet arbeid som er velegnet å gjøre samlet. 

## Verktøy for sjekking av testdekningsgrad og kodekvalitet

Sammen med denne releasen ble det implementert ulike verktøy for sjekk av både testdekningsgrad og kodekvalitet. 

### Jacoco

Jacoco ble brukt for å sjekke testdekningsgrad. Dette verktøyet er implementert for alle moduler og sjekker i hvilken grad testene dekker koden som har blitt skrevet. Innstillinger... (?)

### Checkstyle og Spotbugs

Disse to verktøyene har blitt implementert for å sjekke kodekvaliteten. Checkstyle skal sjekke at Java-koden som har blitt skrevet følger vanlig kodestandard, og Spotbugs ser etter feil(bugs) i koden. Innstillinger... (?)