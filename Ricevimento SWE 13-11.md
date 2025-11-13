raccogliere le cose prodotte in un solo file. IMPORTANTE.

## Statement
generalmente va bene.
"digitalizzazione dei servizi" ??
ci ha gamato totalmente il fatto di averlo fatto con l'ai.
come si misura l'efficienza? -> era scritto nello statement. 
Scriviamolo per bene. Ci ha sgamato totalmente l'ai. 

Si scrivono le cose che diventano concrete che poi implementeremo. 

## Use case diagram
invoke -> meglio non usarlo
non parla mai di prima e di dopo. Non specifica mai la precedenza delle operazioni. Evitare la procedura. (bibliotecario)

Cittadino. Meglio farlo come il bibliotecario. Non specificare le procedure

## Class diagram
### Business
"va bene, questo può andare".
Durante la scrittura del codice, ci sarà un auth manager "undetermined", all'inizio non si può sapere cosa si fa

ciò che compare deve riflettere use case e template. 

### orm
connection manager -> singleton.

## domain model
loan -> fare riferimento alla card e al book. come associazioni di classi. 
User -> non tutto questo subclassing. Library user compone al suo interno un anagrafica, senza il subclassing.
Card-> invertire possesso col library user.
Book -> mapper, il book è assegnato ad un certo compartimento. MAPPER !!!. Per disaccoppiare. 


## DB
"riflette un po' mi aspetto le classi"
rivedere la parte dello user. 
