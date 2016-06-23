##### dag 1
- API gevonden
- SQL gebruiken voor data opslag
- Proposal geschreven + ui uittekenen

##### dag 2
- UI MySchedule en todays workout samen nemen, ga een recycler view daarvoor gebruiken.
- Today's workout weghalen uit Home page.
- Denk equipment optie eruit halen.
- Select muscle in workout builder moet een button worden die een lijst omhoog brengt
  met keuzes uit spieren, of al een lijst zijn met spieren meteen die geklikt kunnen
  worden. Stopt spelfouten en zoeken naar dingen die er toch niet zijn. Helpt ook de
  onervaren user.
- moet iets doen met wanneer je naar edit gaat, wanneer je oefening afvinkt en 
  wanneer je een workout in je planning stopt.
- Design document gemaakt.

  ctrl-shift-n laat je zoeken naar alle folders in AS.
  https://developer.android.com/training/basics/data-storage/databases.html

##### dag 3
  - niet database doorzoeken na input user maar gewoon bij opstarten hele database
    in SQL zetten en dan kan alles local gedaan worden.
  - SQL is dus local
  - per user opslaan wat voor oefeningen ze maken en dan suggesties geven op basis 
    van wat andere users hebben gedaan zou een optie kunnen zijn. Wel lastig met 
    database en dergelijke. Onthouden voor als er nog tijd is.

##### dag 4
  - progress laat eerst lijst van workouts zien, selecteer workout laat recyclerview
    zien met graph etc.
  - onderin elke page moet een homebutton komen, wat moet er dan in home komen? hoe
    gaat dat werken met de backbutton van telefoon.
  - exercise en workout models gemaakt.
  - activities zijn af, alleen nog kijken wat ik doe voor edit van exercises.

##### dag 5
  - show and tell

##### dag 6
  - httprequest en asyctask gemaakt, alleen voor eerste inladen data. mss is het nodig 
    om later nog meer data te requesten. niet alle data is in engels, kan wel op gefilterd
    worden.
  - model aangepast op de data die ik uiteindelijk krijg.
  - items worden per page teruggeven, kan requesten op page nr, kijken hoe dat handig gaat,
    maar eerst andere dingen.

##### dag 7
  - als je ontouch gebruikt kan je daar onclick en onlongclick inzetten en dat zorgt er
    dan voor dat die twee nooit strijd met elkaar hebben.
  - kijken naar context menu om te gebruiken eventueel om exercises te editten. even 
    loeren hoe dat er uitziet en of het logisch is voor de user.
  - beginnen met basic list en adapter, afgemaakt. Kijken naar expandable listview voor 
    het weergeven van de resultaten na zoeken.

##### dag 8
  - SQLContractClass gemaakt.
  - SQLDatabase gemaakt.
  

##### dag 9
  - 3 tabellen gemaakt voor database.
  - newworkout moet anders, results moet ook anders

##### dag 10
  - show and tell
  - todolist gemaakt wat ik nog moet doen
  - kan een spinner in een recyclerview?

TODOS

// - Modelclass maken voor workout + dag
// - Zorg dat exercise modelclass alle data kan vasthouden die nodig is voor beide tabellen.
// - Link leggen tussen de 3 tabellen, zie schrift voor plan.
// - User moet dag kunnen kiezen na workout maken, nieuwe activity of anders?
// - Nieuwe activity om sets/reps/weight te kiezen na selecteren elke exercise voor workout.
// - 2 dropdown menus in results om te filteren om catgorie of muscle.
/ - onclick maken om uitleg weer te geven in results, onlongclick om te adden.
// - if statement in adapter voor exercise list om filter te handelen. niet filteren uit database
    maar wat er gedisplayed wordt door de adapter.
// - click functionaliteiten voor schedule bepalen en maken.
// - data filteren op engels en alles ophalen.
// - manier om naar home te navigeren aanpassen.
// - databases closen na gebruik

# - progress colom aan tabel toevoegen om progressie bij te houden in array van ints.
# - bovenstaande array ophalen en plotten in progress met bijbehorende info.

##### dag 11
 - aan filter gewerkt.
 - activity om sets reps te kiezen gemaakt.
 - onitemclick op exerciselist


##### dag 12
- filter afgemaakt, data wordt gewist bij start, niet heel netjes.
- Workouts worden nu goed toegevoegd aan list meteen na invoeren.
- Tabellen gelinked, data voor laten zien oefeningen per workout is gemaakt.

##### dag 13
- 4e table gemaakt om workouts op verschillende dagen te kunnen zetten
- 

##### dag 14
- 

##### dag 15
- show and tell
- list in list laat een workout zien, dagen staan nu goed.

##### dag 16
- listview exercises aangepast, filter op uitleg, proberen spieren naar naam te zetten

##### dag 17
- switch om spieren naar naam te zetten
- opruimen in code
- geen filter maken per spier
- 1 button in settings
- alles behalve paar classes opgeruimt
- lists die exercises laten zien veranderd

Nieuwe todo
- spinner category moet namen laten zien
- spieren in list niet meer null
- <p> wegfilteren
- edittext mag alleen chars en ints accepten
- checkboxen laten werken in workoutlist, ergens moet knop
- homeknop kan in standaard menu zonder fragments? dan doen
- 

##### dag 18
- exercise list wordt nu elke keer geleegd
- workouts in dag zijn clickable
- list in list expand goed, laat alles zien.
- check voor lege of niet bestaande table
- na kiezen workout uit planner komen alle exercises goed in list, alleen exercise sets wordt niet gevonden
- muscles schrappen

TODO
// categories naar strings veranderen, makkelijker om text te laten zien
// deleten van exercise, workout
// exercise instellingen veranderen
// onbackpress aanpassen in home en newworkout, homebutton eruit
//layout fixxen
// check voor input in settings
// opnieuw opruimen
// rotatie blokken
// popup voor delete


##### dag 19
- categories naar strings
- startactivityforresult en update database voor settings
- exercisesets column wordt gevonden
- exercises kunnen worden verweiderd
- workouts kunnen worden verweiderd
- newworkoutbackpress start altijd home

##### dag 20
- textstyle aangepast voor hele app
- results afgemaakt, backpress erin en layout
- newworkout idem + alert dialog
- homeactivity idem + http en async, backpress in home gaat uit app
- exercise settings idem + alleen nummers toetsenbord
- workoutcontent idem + alert en list layout

//niet vergeten space handling te verweideren of weer te implementen
//headers fixen
/sql classes fixen
//feedback aan user na kiezen exercise
//niet oneindg grote edittexts

##### dag 21
- edittexts pakken niet oneindig veel chars
- verslag geschreven