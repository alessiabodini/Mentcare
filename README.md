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

1. **gestione delle cure individuali**: i medici possono creare una cartella clinica per i pazienti, modificarne le informazioni, vedere la storia clinica del paziente ecc. 
2. **monitoraggio dei pazienti**: il sistema monitora i registri dei pazienti coinvolti nel trattamento e i genera degli avvisi se vengono rilevati eventuali problemi;
3. **report amministrativo**: il sistema genera mensilmente dei report che mostrano il numero di pazienti trattati in ogni clinica, il numero di quelli che sono entrati/usciti dal sistema, il numero di quelli isolati, i farmici prescritti e il loro costo, ecc.

La parte di sistema che si può vedere qui sviluppata riguarda la *gestione delle cure individuali* e si concentra sul lavoro svolto dalle seguenti figure: *dottori* (dell'ospedale o medici di base), *infermieri* e *receptionist*. Si considera in particolare che ogni infermiere e dottore dell'ospedale possano accedere a tutte le cartelle cliniche presenti, mentre i medici di base solo ai propri pazienti. In futuro sarebbe necessario provvedere a un sistema di autorizzazione in base al sistema ospedaliero di cui gli utenti fanno parte.

## Scenari
Gli scenari sviluppati sono i seguenti:

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
All'interno di _AppController_ si può anche trovare il link a pagine usate per il controllo nel database corrente degli utenti ("/staff-list" e "/patients-list"), dei farmaci ("/medications-list") e dei trattamenti ("/treatments-list") presenti. Il database in questione viene caricato attraverso un apposita funzione al primo caricamento della homepage iniziale.

## Design
Di seguito si riportano i diagrammi sviluppati per la progettazione. 

- **Use case diagram**:
  
![UseCaseDiagram](/uml/UseCaseDiagram.jpg)

- **Class diagrams**:

![ClassDiagram](/uml/ClassDiagram.jpg)

![ClassDiagramMVC](/uml/ClassDiagramMVC.jpg)

- **Sequence diagram**, rappresentante l'autenticazione nel sistema:

![Authentication](/uml/Authentication.jpg)

- **Activity diagram**, rappresentante la modifica della diagnosi di un paziente:

![UpdateConditions](/uml/UpdateConditions.jpg)


## Qualità
