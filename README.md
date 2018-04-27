# [Semesteroppgave 2: “Fire på rad”](https://retting.ii.uib.no/inf101.v18.sem2/blob/master/SEM-2.md)


* **README**
* [Oppgavetekst](SEM-2.md)

Dette prosjektet inneholder [Semesteroppgave 2](SEM-2.md). Du kan også [lese oppgaven online](https://retting.ii.uib.no/inf101.v18.oppgaver/inf101.v18.sem2/blob/master/SEM-2.md) (kan evt. ha små oppdateringer i oppgaveteksten som ikke er med i din private kopi).

**Innleveringsfrist:**
* Hele oppgaven skal være ferdig til **fredag 27. april kl. 2359** ([AoE](https://www.timeanddate.com/worldclock/fixedtime.html?msg=4&iso=20180427T2359&p1=3399))
* [Ekstra tips til innlevering](https://retting.ii.uib.no/inf101/inf101.v18/wikis/innlevering)

(Kryss av under her, i README.md, så kan vi følge med på om du anser deg som ferdig med ting eller ikke.)

**Utsettelse:** Hvis du trenger forlenget frist er det mulig å be om det (spør gruppeleder – evt. foreleser/assistenter hvis det er en spesiell situasjon). Hvis du ber om utsettelse bør du være i gang (ha gjort litt ting, og pushet) før fristen
   * En dag eller to går greit uten begrunnelse.
   * Eksamen er relativt tidlig i år, så vi vil helst unngå lange utsettelser.
   * Om det er spesielle grunner til at du vil trenge lengre tid, så er det bare å ta kontakt, så kan vi avtale noe. Ta også kontakt om du [trenger annen tilrettelegging](http://www.uib.no/student/49241/trenger-du-tilrettelegging-av-ditt-studiel%C3%B8p). 
   
# Fyll inn egne svar/beskrivelse/kommentarer til prosjektet under
* Levert av:   *Tom Rune Sæverås* (*yap006*)
* [ ] hele semesteroppgaven er ferdig og klar til retting!
* Code review:
   * [ ] jeg har fått tilbakemelding underveis fra @brukernavn, ...
   * [ ] jeg har gitt tilbakemelding underveis til @brukernavn, ...
* Sjekkliste:
   * [x] Kjørbart Fire på Rad-spill
   * [think so] Forklart designvalg, hvordan koden er organisert, abstraksjon, og andre ting 
   * [x] Tester
   * [x] Dokumentasjon (JavaDoc, kommentarer, diagrammer, README, etc.)
   * [x] Fornuftige navn på klasser, interfaces, metoder og variabler
   * [x] Fornuftige abstraksjoner og innkapsling (bruk av klasser, interface, metoder, etc.)

## Oversikt
*(oversikt over koden din og det du har gjort)*
I have a main method that makes the start screen and takes user input to create a new game. 
Based on user input a game vs AI is started, or a game vs another human. If it's the AI, this uses the MiniMax algo to calculate best move. 
### Bruk
* For å starte programmet kjør: 
Main.
Type in name. Choose difficulty. If you choose versus, type in a new username(cannot be the same as the login name)

## Designvalg
*(hvordan du har valgt å løse oppgaven)*
I'm taking another course, 112, where we have made a similar GUI to visualise a game. The GUI I used is based a lot on this GUI project. 
For the MiniMax algo for the AI I have put together this by googling my ass of. It's a collection of many different google results put together for my use.

### Bruk av abstraksjon
*(hvordan du har valgt objekter/klasser for å representere ting i spillet)*
I made an Interface for how I wanted the Board class and the game modes to look. I did this at the start, but added features as I found out that I needed them.

### Erfaring – hvilke valg viste seg å være gode / dårlige?
*(designerfaringer – er det noe du ville gjort annerledes?)*
The choice of not making tests before after was a bad decision. I should have done this while adding functionality and I would have saved myself time and anger.
I would want to make a more pleasing and smooth design, but I am no designer, and I did not have the time to learn to be one.  

## Testing
*(hvordan du har testet ting)*
Most of the testing hase been done while I was making the program. The test classes themselves I mostly made after I was happy with the functionality.
In retrospective I should have made some of the tests while programming, would have reduced number of rages.
I should also have made more tests for functionality, but time is my enemy.

## Funksjonalitet, bugs
*(hva virker / virker ikke)*
As far as I can see the functionality of most stuff works fine.

## Evt. erfaring fra code review
Did not go through other peoples code.
## Annet
*(er det noe du ville gjort annerledes?)*
If I had more time I would like to add the feature of highscores. And I would also like to make the visuals look better than it does now.
I would like to make a choice of column based on the mouse cursor, instead of having to press a button.
I have not pushed to Git this whole project, which is not good at all. I'm writing this after I tried to push the last day, and got errors that I had to fix. If i had pushed frequently I would have noticed the errors sooner and would not have to stress the final day to fix them.
