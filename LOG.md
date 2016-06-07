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