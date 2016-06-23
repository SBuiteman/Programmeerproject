Stijn Buiteman 
Programmeerproject
HelpMeExercise

Description
In this app the user is presented with a week overview containing workouts. The user can create new workouts by giving
a name and a day. Thereafter the workout can be filled with exercises retrieved from an online database. Each time an 
exercise is chosen the user may specify sets, reps and weight for that exercise. Once completed the workout is added to 
the week overview. Clicking on a workout shows all the exercises belonging to the workout with the chosen settings and
an explanation of the exercise. Clicking an exercise allows the user to edit the settings of the exercise. The user can
delete workouts and exercises. All data is stored in an SQLite database.

Technical Design
- Exercise Model and Workout Model classes
These classes are able to hold any and all of the variables that need to be passed to classes or the database. They are
used to create object lists instead of keeping all the data separate and can be called via setters and getters.
- SQLContract class
This class is used to specify tables and their colums. In this class four tables are created: 
AllExercises, made to hold all the exercises that will be retrieved from the online database at wger.de. This table has
six columns:
	- exerciseid, a different id for each exercise included in the retrieved JSON.
	- exercisename, holding the name of each exercise.
	- category, holding the category of each exercise.
	- explanation,holding the explanation on how to perform each exercise.
workoutcontent, made to hold the settings for each exercise chosen for each exercise, and the exerciseid and a workoutid.
These id's are used to retrieve aditional information form other tables so that data does not have to be stored multiple 
times and to enable each exercise to be chosen for multiple workouts. This is now possible because the workout in which
an exercise belongs is not specified in the AllExercises table, where the related workout would have to be stored in the
row specific to an exercise, thus only allowing for one option. This table contains:
	- workouttag, in which workout does this exercise belong.
	- exercisetag, specifies which exercise is concerned.
	- exercisesets
	- exercisereps
	- exerciseweight
workouts, made to hold created workouts plus an id which is used in combination with the workoutcontent table as explained
above. This table contains:
	- workoutname
	- id
weektable, made to hold created workouts plus the day assigned to the workout so that multiple workouts can be in one day.
This table contains:
	- weekday
	- assignedworkouts

-SQLDatabaseController
This class is called any time the data in the database needs to be altered. At the very first call the tables are 
created if they did not yet exist on the device. The database controller contains methods to both insert and delete
data from specific tables sort of like the getter and setter functions in a model class. A check is implemented to
see if a table exists and has content to prevent crashes.

-Home Activity
When the app is started a list with weekdays is created by callen the adapter in ScheduleAdapter. In this list another
list is created showing all the workouts created per day (i.e. listitem). This happens in the ScheduleAdapter which calls
the InScheduleAdapter. The contents of the first list are made with a hardcoded array containing days which is passed
from the HomeActivity. Along with this array a list of objects holding all workouts and their assigned days in the 
weektable is also passed to the ScheduleAdapter. In the ScheduleAdapter the list is filtered per day. Then a new adapter
is called from the ScheduleAdapter, which passes a list of workouts pertaining to a specific day (i.e. the day of the
current list item that is being created). The next adapter, InScheduleAdapter, creates a list containing the passed
workouts form the ScheduleAdapter. This list is thus part of the listitems created by the ScheduleAdapter.
Meanwhile, AsyncTaskManager class is called in order to get the data from the API wger.de. AsyncTaskManager calls a
HttpRequestHelper class to do a get request. The returned JSON is then processed in AsyncTaskManager after which an 
objectlist of all retrieved exercises is given to the Home Activity. Home Activity then calls the SQLDatabaseController
class which adds the exercises to the AllExercises table. This is done everytime the app starts up because the API can
always contain new data or updates. Apart from the list a button is shown to the user to start creating a new workout
in the NewWorkoutActivity.

-NewWorkout Activity
In this activity the user is presented with a listview containing all the created workouts. These workouts are retrieved
via a call to the SQLDatabaseController and then passed to the NewWorkoutAdapter. To create a new workout, the present
edittext must contain at least one character. If this is not the case the user is presented with a request for input.
A day can be selected with a NumberPicker, changes are detected with an OnValueChange listener. When input is given and 
the create button is pressed the input is taken and stored in the workouts table via the SQLDatabaseController. The 
current calue of the NumberPicker is also retrieved and stored in the weekdtable along with the given name via a different
call to the SQLDatabaseController. The ResultsActivity is then started. The ResultActivity is also started if a workout 
in the list is clicked. In both cases an intent is send containing the name of the workout that was made of clicked. When
an item is longclicked it is removed, before this happens the user is presented with an alert dialog to ask if the user
is sure. On confirm the SQLDatabase is called to remove the workout from the weektable, the workouts table and the
information in the workoutcontent table related to the removed workout.

-Results Activity
On start this activity calls the SQLDatabaseController to retrieve all exercises from AllExercises table and puts that
data in an object list. The ExerciseListAdapter is called and passed this list. In the list all exercises, their
category and explanation are presented. Via a spinner the results can be filtered per category. When a category is
selected the adapter is called and passed the selected category. In the adapter this category is used to only
display exercises matching the selected category. When an item is clicked and intent is send to the SettingsActivity
containing the name of the selected exercise, the name of the selected workout that was send with the intent from
the NewWorkoutActivity and a tag informing SettingsActivity which activity has called.

-Settings Activity 
In this activity the user must give input for sets, reps and weight. An attempt to finish the activity before this
results in a message asking for complete input. The edittexts only except numbers and thus the keyboard presented
to the user it set to numbers by default. When all input is given the SQLDatabaseCotroller is called to retrieve the
id's from both the exercise and workout that were recieved via an intent from the AllExercises table and the workouts
table respectively. Then, a new call to the SQLDatabaseController inserts the input and the id's in a new row in the
workoutcontent table, these are sent together in one object. However, this only happens if SettingsActivity was started 
from ResultsActivity. If SettingsActivity was called from the WorkoutContentActivity the created object is returned to
that activity. After this onFinish() is always called to return the user to the activity he came from.

-Workout Content Activity
This activity is accessed by clicking on a workout in the week planner from the HomeActivity. The user is presented with
a list showing all exercises that belong to that exercise, this list is made by te WorkoutContentAdapter. This list is 
gotten via the SQLDatabaseController by passing the name of the selected workout. This name was passed via intent from 
the HomeActivity. First, the workoutid is found in the workouts table. Second, the settings for the exercises and their 
id's are found in the workoutcontent table using the workoutid. Last, specific infomation about each exercise is found 
in the AllExercises table using the exerciseid. Clicking on an item start the SettingsActivity for result. When this 
results is passed back the SQLDatabaseController is called to update the workoutcontent table after which the 
WorkoutContentAdapter is called again to update the view with the current information. LongClicking an item removes it, 
but only after the user is promted with an alert dialog if he really want to remove the item.

The biggest challenge making this app was working with the SQL database. As I had never worked with this before I had to
find out how to properly create tables to begin with. The queries to insert and delete data were similar as what I have
used before and did not pose a problem, however, using a cursor and which way to use a cursor was more difficult. I had a
lot of issues putting the data I retrieved from the tables back in object models. Especially if it concerned getting 
integers from the table. Somehow and int was not always an int which made the proces counter intuitive. This was made
even more difficult because it is not possible to look in the tables any other way than by retrieving data with the 
cursors. After creating my first table the next problem was that I wanted an exercise to be able to be in multiple
workouts. Using only one table I would have had to create a new column for each workout thereby also creating a great
many columns with empty spaces or default values since many exercises might not or almost never be selected. Thus I
made two more tables and implemented a many-to-many relation. I had to figure out which data to store where and how to
keep track of that as well. 
With regards to my initial design I decided to have the week schema be shown in the HomeActivity instead of making
a separate activity. Because I also dropped the progress activity due to lack of time I could reduce the amount of
buttons in the HomeActivity to one. At first I was planning to show the exercises from a workout in the week schema
together with the workouts they were matched with. The plan was to use a recycler view to make a really long list of
lists. However, I decided to make a separate activity to view the contents of a workout. But then I realised that users
might want to have multiple exercises for one day. This forced me to create another table to keep track of those days
and also made me think about how to make that display. I decided to make a list in the list so that space taken per day
would be dynamically related to the amount of workouts for each day. Creating this second list posed a number of problems
because I could not manage to control it from the HomeActivity. Eventually I put the adapter and clicklistener in the
adapter of the first list and made a filter to go through all workouts and pass only the workouts with the day
corresponding to the item I was trying to fill with the list. Lastly I managed a way to grow the list corresponding to
its content, at first it always remained only one line which was also not scrollable. 
I was planning to show the instructions for an exercise via some kin of onclick method. But because I already used two
kinds of clicks to edit and remove an exercise I decided to place the instructions in a textview below the rest of the
information of each exercise.
Because my API did not provide many correctly working filter functions I decided to get the entire database and then
do the filtering myself. This meant that I only had to do one httprequest each time at startup. Almost immediately after
starting my app I decided to drop equipment as part of the informtion for the user since it is kind of implicit in
relation to each exercise. Soon after that I decided to limit the options of user input as much as possible to prevent
errors and abuse. Thus I implemented selection menu's like a spinner and a numberpicker to get input from the user.
I decided to add a separate activity to edit exercises because I felt that the screen would become to crowded if I did
not. In the end I also dropped specific muscles as part of the information because the data provided from the API 
regarding muscles was really inconsistent. Moreover, I felt that specifying that an exercise was for arms or chest was
enough for the user to make his choices. 
Because my app has a lot of going back and forth using the back button became quite annoying. Sometimes I had to cycle
back through 20 screens to exit. Thus I made some activities always automatically return the user to a specific other
activity and  in other activities I made the back button always return the user to a specific activity. This also
allowed me to remove the home button which was taking up space and did not look to good.
I also entirely scrapped the progress activity because there simply was not enough time for me to implement it.

I feel that I made the right decision reforming the week schema. As it is now a user can see his planning instantly when
the app is started, adding the exercises would make the screen very cluttered. I also think it was the right decision to
show the exercise information below each exercise instead of adding another click function. I could have made a button in
each list item but the resulting row of buttons would have looking terrible. Given more time I would have tried to use a
fragment which appears on item click showing all the options and extra info. So clicking an item would allow for the
exercise to be edited, removed and would show extra information. This would also have made the settings activity reduntent
and would have made my app more accessible.






















