# Song Trainer

## Zadanie

Vypracované ako projekt na predmet **Grafická uživatelská rozhraní v Javě** vyučovaný na **VUT FIT**.

Úlohou bolo vytvoriť webovú aplikáciu, ktorej užívatelia sú ľudia hrajúci na hudobné nástroje. Aplikácia má slúžiť na trénovanie pesničiek, ktoré užívatelia už vedia zahrať. Keď sa užívateľ naučí novú pesničku, nahrá backing track do systému a vyplní informácie o pesničke ako názov, na ktorom nástroji ju vie hrať.

Keď nastane čas, že užívateľ chce trénovať na svojom hudobnom nástroje, využíva aplikáciu aby za sebou púšťala nahrané backing tracks a on bude zároveň s nimi hrať. 

## Technológie

Pri práci na projekte boli použité nasledujúce technológie:
- Spring (boot, security, MVC, JPA)
- Maven
- JSP
- MySQL

## Spustenie

Pred spustenním aplikácie je nutné pripraviť databázu.
V projekte využívame MySQL a SQL skript `songtrainer_db_setup_empty.sql` v zložke `sql-scripts/`. 
Tento skript vytvorí databázu songtrainer a pripraví aj dvoch užívateĺov:

- bežný užívateľ s username _john_ a heslom _password_
- lektor s username _mary_ a heslom _password_

Ďalej je potrebné v súbore application.properties, ktorý sa nachádza v zložke `src/main/resources` upraviť
položky `spring.datasource.username` a `spring.datasource.password` na  ́údaje používané k pripojeniu do MySQL.

Pomocou Maven je možné aplikáciu ľahko spustiť príkazom `mvn spring-boot:run`. Stránka je potom dostupná
na adrese `http://localhost:8080/`.
