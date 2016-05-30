Programmeerproject
Stijn Buiteman
stijnbuiteman@gmail.com

Summary: This application should allow users to make their own personal workout schedules, devide these schedules
in a week planner and keep track of their progression over time (i.e. more weight/reps). Building, editting
or using a workout schedule during training all take place in another activity. Examples of the exercises in form
of pictures and text is always available, same as the possibility to edit reps or weight per exercise. Depending on
time visual representation of the progression and tools to help decide on settings will be added. 

Problem: A lot of people have trouble to find out which exercises they should do and/or how to perform those
exercises even though they do have specific training goals. This app can find exercises based on searches on specific
musclegroups or available equipment. This means the a user can decide he wants bigger chest-muscles, he can look for
exercises that train his chest.

Features: The features of this app include: 
- Users will be able to retreive lists of specific exercises without the need for intimit knowledge
  of the skeleto-muscular system.
- Users will receive instructions for each exercise on how to perform it.
- More knowledgeable users can find replacements or additions for their own workouts.
- The severity of the exercises can be hand tailored to the users desires.
- Users can keep track of their progress to see how they are doing.
- App might include features to help users deside on the settings for their exercises.

Data: This app will use the API from wger.de which is a database containing exercises sorted per muscle group.
This data is sent in JSON packages from where the required information can be retreived. The same database also
contains pictures and written explanations regarding each exercise that is available. Furthermore, a place to 
store data will be required. Most likely a SQL database will be imployed for this purpose. Lastly, some form
of database may be used later on to retreive information to help users deside on settings etc.

Parts of the app: The app will have a start screen allowing choice between new workout, stored workouts, schedule
and progression. Each choice will take the user to a new screen in which the same activity will load the
approporate views for the users selection. Workouts will be stored in objectarraylists, each object being one
exercise. Objectlists will be loaded into the views as lists which can then be edited by clicking on items or 
buttons. Button functions will be written in a separate class. To get the objects httprequests will be done in 
a separate activity, which will be handled by a parallel activity. Each adapter will also be in a separate class.

Technical problems: I already found a good API as mentioned above which yiels JSON packages. Problems may arise 
storing objectarrays. I expect that finding the proper way to use a SQL database will render that problem obsolete.
Furthermore it might prove difficult to find a source to provide users with aditional data to help them decide on
the proper weight and repetitions for their exercises. But I have not yet looked into that because it is not part
of my minimum viable product. The MVP only contains the arraylists working with the views as described above.

Similar applications: There are many applications which offer trainingschedules complimented with videos and
advanced statistics. For example Academia Mania. This app also offers assistence with chosing the proper 
foods and supplements in combination with training schedules.However, like all the other apps of its kind, it is
not free to use. Academia Mania uses about the same layout as described above, so listviews of exercises or foods
loaded in different screens as the user makes his choice. 

