# Mentcare

## Requisiti
**Mentcare** è un sistema informativo medico che registra informazioni su pazienti con problemi di salute mentale e le cure cui sono sottoposti. Il sistema ha due obbiettivi:

- generare informazioni riguardanti i pazienti che consentono ai responsabili di valutare le condizioni cliniche rispetto agli obbiettivi locali e organizzativi;
- fornire al personale medico informazioni tempestive per definire le cure dei pazienti.

Il sistema dispone di: 

- un database centrale;
- l'accesso da host remoti, da cui è possibile visionare una copia della cartella clinica dei pazienti.

Gli utenti di tale sistema sono:

- <ins>staff medico</ins>: dottori, infermieri e assistenti sanitari (che seguono i pazienti a casa);
- <ins>staff non medico</ins>: receptionist (che prendono gli appuntamenti), tecnici che gestiscono le cartelle cliniche e staff amministrativo che redige i rapporti.

Il sistema necessita dell'implementazione delle seguenti features:

1. **gestione delle cure individuali**: i medici e gli infermieri possono creare una cartella clinica per i pazienti e modificarne le informazioni, i medici possono inoltre prescrivere e modificare farmaci e trattamenti. Inoltre i receptionists si occupano della registrazione dei pazienti nel sistema e della prenotazione dei consulti;
2. **monitoraggio dei pazienti**: il sistema monitora i registri dei pazienti coinvolti nel trattamento e i genera degli avvisi se vengono rilevati eventuali problemi;
3. **report amministrativo**: il sistema genera mensilmente dei report che mostrano il numero di pazienti trattati in ogni clinica, il numero di quelli che sono entrati/usciti dal sistema, il numero di quelli isolati, i farmici prescritti e il loro costo, ecc.

La parte di sistema che si può vedere qui sviluppata riguarda la *gestione delle cure individuali* e si concentra sul lavoro svolto dalle seguenti figure: *dottori* (dell'ospedale o medici di base), *infermieri* e *receptionist*. Si considera in particolare che ogni infermiere e dottore dell'ospedale possano accedere a tutte le cartelle cliniche presenti, mentre i medici di base solo ai propri pazienti. In futuro sarebbe necessario provvedere a un sistema di autorizzazione in base al sistema ospedaliero di cui gli utenti fanno parte.

Di seguito si presentano gli scenari sviluppati, implementanti in base ai requisiti esposti dagli stakeholder per il sistema.

## Scenari
Gli scenari sviluppati (in base ai requisiti richiesti) sono i seguenti:

1. <ins>Visualizzazione della cartella clinica di un paziente</ins> (da parte dello staff medico).  
   Lo staff medico, una volta entrato nella propria homepage, ha la possibilità di scegliere un paziente da un menù a tendina. Cliccando poi invio o su "View medical record" viene reindirizzato alla cartella clinica del paziente. Tale cartella clinica presenta le informazioni personali del paziente, il proprio medico di base, allergie, sintomi e patologie diagnosticate, farmaci e trattamenti prescritti e ancora validi al momento corrente.


2. <ins>Modifica della cartella clinica di un paziente</ins> (sempre da parte dello staff medico).  
   Ciò include:
    - la modifica delle allergie e delle patologie (con relativi sintomi) presentate dal paziente. In questo caso, la figura incaricata, a partire dalla cartella clinica del paziente, può cliccare sul pulsante "Edit medical record". Da qui si apre una nuoca pagina, da cui si può:
        - modificare le allergie scrivendo all'interno dell apposito campo;
        - patologie del paziente selezionando/deselezionando rispettivamente le nuove condizioni riscontrate nel paziente e quelle invece da cui risulta guarito;
    - la modifica/cancellazione di farmaci e trattamenti precedentemente prescritti. Per fare ciò basta cliccare sul link "Change" presente vicino al farmaco/trattamento d'interesse. Questa azione riconduce a una nuova pagina, da cui è possibile:
        - modificare la dose e l'unità di un farmaco e similmente la descrizione e la frequenza di un trattamento (per una descrizione dettagliata vedere rispettivamente i casi di prescrizione di farmaci e di trattamenti);
        - cancellare tramite il pulsante "Delete" il farmaco o il trattamento in questione. Tale azione riporta poi in automatico alla cartella clinica del paziente.
    

3. <ins>Prescrizione di un nuovo farmaco</ins>  
   I dottori, una volta all'interno della cartella clinica del proprio paziente, possono prescrivere un nuovo farmaco cliccando sul pulsante "Prescribe new medication". A questo punto si aprirà una nuova pagina da cui sarà possibile per l'utente selezionare da un menù a tendina il nome del farmaco scelto, indicare la dose e selezionare l'unità di misura corrispondente. Una volta schiacciato su "Save" il sistema controlla che la dose per il farmaco inserita sia corretta, ovvero esiste nel database di farmaci ufficiale, e il farmaco scelto in tale dose non risulti già prescritto al paziente. Se i dati non sono corretti viene inviato un messaggio di errore che consiglia di compilare il form diversamente, in caso contrario invece il sistema procede con una verifica delle allergie. Avviene cioè un controllo tra i componenti del farmaco e le eventuali allergie del paziente: se il paziente risulta allergico a tale farmaco viene emesso un nuovo messaggio di errore. Tale controllo può essere altrimenti evitato selezionando una checkbox presente nel form che evita tale controllo e permette al medico di prescrivere il farmaco nonostante possibili effetti collaterali.  
   Una futura modifica/cancellazione del farmaco può essere effettuata, come indicato sopra, direttamente dalla cartella clinica del paziente.  
   _Il database di farmaci al momento è solo una bozza; dovrebbe essere completato rispetto a una lista ufficiale di medicinali autorizzati dal sistema sanitario nazionale._
  
   

4. <ins>Prescrizione di un nuovo trattamento</ins>  
   Similmente allo scenario precedente, un medico può anche prescrivere un nuovo trattamento sempre a partire dalla cartella clinica di un paziente. Cliccando sul pulsante "Prescribe new treatment" si aprirà un pagina in cui è possibile inserire i due campi che identificano un trattamento: descrizione (da scrivere nell'apposito campo) e frequenza (selezionabile tra giornaliera, settimanale, mensile e annuale). _Le frequenze disponibili al momento sono solo un esempio e possono essere tranquillamente modificate in un futuro._ Una volta completato il form, il medico può inviare il tutto tramite il pulsante "Save" e il sistema a questo punto inserirà il trattamento solo nel caso in cui non ci siano campi vuoti e non sia già stato prescritto, con identiche caratteristiche, al paziente. In caso contrario verrà emesso un messaggio di errore che consiglierà di ricompilare il form con nuovi parametri. 

6. <ins>Visualizzazione delle informazioni personali di un paziente</ins>  
   Un receptionist, una volta autenticatosi nel sistema, ha la possibilità di selezionare un paziente (dall'intero sistema ospedaliero) e visualizzare le sue informazioni personali, oltre che i consulti a cui ha già preso appuntamento. 
   

7. <ins>Modifica delle informazioni personali di un paziente</ins>  
   A partire dalla schermata presentata sopra, il receptionist può cliccare sul tasto "Edit" per modificare le informazioni riguardanti il paziente, a eccezione del suo ID. In particolare, risulta possibile modificare nome, cognome, data di nascita, numero di telefono, indirizzo e infine anche il medico di base (scelto tra quelli disponibili). Una volta compilato il form e cliccato su "Save", il sistema controlla che i dati inseriti non siano vuoti e che la data sia specificata nel formato corretto prima di salvarli nel database, altrimenti manda un messaggio di errore. Viene inoltre controllato se un altro paziente con gli stessi identici dati non sia già presente nel sistema, così da evitare doppioni. 

   
5. <ins>Registrazione di un nuovo paziente</ins>  
   Una volta entrato nella sua homepage, il receptionist può anche registrare un nuovo paziente cliccando su "Register a new patient". In questo caso la pagina che si apre è simile a quella di modifica, ma in questo caso tutti i campi sono inizialmente vuoti. Una volta completato il form e cliccato su "Save", il sistema esegue gli stessi controlli di prima e se i dati risultano corretti procede al salvataggio del paziente nel sistema. 

8. <ins>Prenotazione di un consulto</ins>  
   A partire dalla scheda raffigurante i dati personali di un paziente, un receptionist può prendere per lui un nuovo appuntamento per un consulto con un medico ospedaliero. Cliccando così sul pulsante "Book a consultation", si apre una nuova pagina che permette di definire il dottore con cui prendere appuntamento, la data e l'ora, la clinica in cui presentarsi e le ragioni della visita. Una volta inviati i dati il sistema controlla che i campi siano stati correttamente specificati e che non ci sia un consulto uguale (senza contare la ragione del consulto) già definito per il paziente, altrimenti manda un messaggio di errore.  
   _Il sistema in questo momento non controlla né le disponibilità del medico né quelle del paziente per data e ora definite, ne se questa risulta in un certo range di apertura della clinica. Oltretutto, non verifica che il dottore richiesto sia reperibile proprio alla clinica specificata. Tale sistema di prenotazione risulta al momento quindi solo una bozza e maggiori controlli dovrebbero essere specificati in un futuro._
   

9. <ins>Cancellazione di un consulto</ins>  
   Una volta all'interno della pagina personale di un paziente, il receptionist può cancellare un consulto prenotato in precedenza, cliccando sul link "Delete" vicino alla descrizione del consulto. 

Tutti gli scenari presentati sottintendono una precedente **autenticazione** del sistema. Tale autenticazione è possibile collegandosi all'indirizzo principale ("/") e cliccando sul pulsante "Authenticate". Il sistema al momento garantisce l'accesso alle mansioni solo in base al proprio codice identificativo. Il codice viene affidato in modo automatico dal sistema quando un qualsiasi utente viene registrato nel database e decreta la tipologia di utente in base alle lettere d'inizio del codice: "DOC" per i medici, "NUR" per gli infermieri, "REC" per i receptionist e "PAT" per i pazienti. In seguito all'autenticazione ogni dottori, infermieri e receptionist vengono reindirizzati alla propria homepage specifica.

### Extra
Il progetto, per facilitare il test del sistema, permette anche dalla homepage principale di entrare come un generico dottore ospedaliero, infermiere o receptionist e provare così il sistema.   
All'interno di _AppController_ si può anche trovare il link a pagine usate per il controllo nel database corrente degli utenti ("/staff-list" e "/patients-list"), dei farmaci ("/medications-list"), dei trattamenti ("/treatments-list") e infine dei consulti ("/consultations-list") presenti. Il database in questione viene caricato attraverso un apposita funzione al primo caricamento della homepage iniziale.

## Design
Di seguito si riportano i diagrammi sviluppati per la progettazione. 

- Use case diagram:
  
![UseCaseDiagram](/uml/UseCaseDiagram.jpg)

- Class diagrams:

![ClassDiagram](/uml/ClassDiagram.jpg)

![ClassDiagramMVC](/uml/ClassDiagramMVC.jpg)

- Sequence diagram, rappresentante l'autenticazione nel sistema:

![Authentication](/uml/Authentication.jpg)

- Activity diagram, rappresentante la modifica della diagnosi di un paziente:

![UpdateConditions](/uml/UpdateConditions.jpg)


## Qualità
Il **test delle unità** garantisce una copertura del codice del 100% con IntellijCoverage e del 99% con JaCoCo focalizzandosi sul package _entity_.

Per quanto riguarda _AppController_ (e anche _LoadDatabase_) si sono invece sviluppati 26 **test di accettazione**, tutti basati sui dati presenti inizialmente nel database. Si vanno a testare in particolare i seguenti scenari:

1. Autenticazione di un dottore (ospedaliero) (con ID "DOC10000"). Tale ID verrà anche utilizzato per tutti i test riguardanti le mansioni di un medico. 

2. Autenticazione <ins>non</ins> riuscita con un ID non valido ("NUR1000", non esistente nel database).

3. Visualizzazione della cartella clinica di un paziente ("Alessandro Cremonini", con ID "PAT10008") da parte di un medico ospedaliero. Tale paziente verrà utilizzato per i seguenti test riguardanti le mansioni di dottori. 

4. Modifica della cartella clinica di un paziente, con aggiunta di allergie ("Dust") e modifica della diagnosi. In particolare viene deseleziona la casella con "Mood disorder" e viene selezionata la casella "Anxiety disorder", a cui vengono associati nuovi sintomi ("Panic attacks").

5. Eliminazione di un farmaco prescritto ("Prozac (20.0 mg)"). Come anche in altri casi, prima di una tale operazione, perché il test abbia sempre successo, ci si assicura che tale farmaco già sia stato prescritto.

6. Modifica di un farmaco ("Prozac") in un nuovo dosaggio ("28 units").

7. Modifica <ins>non</ins> riuscita di un farmaco in un dosaggio non presente nel database. 

8. Prescrizione di un nuovo farmaco ("Xanax (0.5 mg)"), verificando che il farmaco non sia già stato prescritto prima con tale dosaggio.

9. Prescrizione <ins>non</ins> riuscita di un nuovo farmaco a cui il paziente risulta allergico ("Xanax (1.0 mg)") (quando non viene spuntata l'opzione di <ins>non</ins> controllare eventuali allergie). 

10. Prescrizione <ins>non</ins> riuscita di un farmaco non esistente nel database ("Xanax (5.0 mg)").

11. Eliminazione di un trattamento precedentemente prescritto ("Meetings with doctor (Monthly)").

12. Modifica di un trattamento precedentemente prescritto ("Meetings with doctor (Monthly)").

13. Prescrizione di un nuovo trattamento ("Meetings with doctor (Monthly)"), verificando che non sia già stato prescritto (o in caso già cancellato).

14. Prescrizione <ins>non</ins> di un trattamento già presente nella cartella clinica ("Meetings with doctor (Monthly)"). 

15. Autenticazione di un infermiere (con ID "NUR10004"). Tale ID verrà anche utilizzato per i seguenti test riguardanti le mansioni di un infermiere. 

16. Visualizzazione della cartella clinica di un paziente ("Ilaria Bonetti", con ID "PAT10009") da parte di un infermiere. Tale paziente verrà utilizzato anche per il prossimo test. 

17. Modifica della cartella clinica di un paziente, con aggiunta di allergie ("Bees") e modifica della diagnosi. In particolare viene deseleziona la casella con "Eating disorder" e viene selezionata la casella "Mood disorder", a cui vengono associati nuovi sintomi ("Sadness").

18. Autenticazione di un receptionist (con ID "REC10006"). Tale ID verrà anche utilizzato per i seguenti test riguardanti le mansioni di un receptionist. 

19. Visualizzazione delle informazioni personali di un paziente ("Ilaria Bonetti", con ID "PAT10009") da parte di un infermiere. Tale paziente verrà utilizzato anche nei prossimi test. 

20. Modifica delle informazioni personali di un paziente. In particolare si modifica il numero di telefono in "3526681536" e si sceglie un nuovo medico di base ("Stefano Scorsese").

21. Modifica <ins>non</ins> riuscita delle informazioni personali di un paziente, quando la data viene modificata in un formato sbagliato ("1-02-1998").

22. Registrazione <ins>non</ins> riuscita di un paziente ("Maria Rovere")  le cui informazioni personali sono già completamente riferite a un paziente esistente. 

23. Eliminazione di un paziente ("PAT10010"), controllando che quest'ultimo sia già presente nel database. Per assicurarsi di ciò, chiama prima un metodo che verifica la registrazione di un nuovo paziente ("Maria Rovere", con ID "PAT10010"). 

24. Eliminazione di un consulto ("2021-03-21 10:30 with Dr. Sole at Xperia"), prenotato precedentemente. Per assicurarsi della presenza di tale prenotazione, chiama a sua volta un altro metodo che testa la prenotazione di un nuovo consulto (proprio "2021-03-21 10:30 with Dr. Sole at Xperia"). 

25. Prenotazione <ins>non</ins> riuscita di un nuovo consulto ("2021-03-21 10:30 with Dr. Sole at Xperia"), poiché già esistente tra gli appuntamenti del paziente.

26. Prenotazione <ins>non</ins> riuscita di un nuovo consulto a causa della specifica della data in un formato errato ("21-03-2021"). 