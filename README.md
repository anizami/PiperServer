PiperServer
===========

## User Documentation
Daily Feast is an android application that allows users to find free food events at Macalester College by compiling information about free food events from the Daily Piper, Macalester’s daily newsletter, and displaying them in a user interface. It also allows users to advertise their own events by posting it to the Daily Feast server, making the information available to all other users.

## Launching the app:
Launch the app by tapping the Daily Feast icon on your Android’s app launch screen. You will be sent to the Daily Feast home screen after a brief loading period.

## Viewing events:
From the Daily Feast home screen, tap the button reading “See today’s menu”. Your app will connect to the Daily Feast server, and will download the event information to your phone. If your wifi is disabled, or you have a bad internet connection, you will be notified. The “Today’s Events” screen will display the event titles of all events of the day that feature free food. By tapping an event title, you will be taken to a new screen for that event which will display the event’s title, time, location and a brief description of the food. Since the Daily Piper does not publish events on the weekends, you will only be able to view events created by fellow users on weekend days (Saturday and Sunday).

## Creating Events:
To add your own event to the Daily Feast database, press the “Create New Event” button on the home screen. The button will take you to a new screen where you will be prompted to enter the necessary event information. Enter an event title, time, location and description and then press the “Create Event” button to make your event. You will then be taken to the “Today’s Events” screen that displays all the events of the day. Your newly created event will be at the bottom of the list. Since user created events are added to a database on the Daily Feast server, all other users will be able to see your new event when they launch their Daily Feast apps!

## Developer Documentation

# Daily Feast
Provides free food event information to users. Currently only supports Macalester College. 

## Architecture
The DailyFeast and PiperServer repositories contain the android client and the Java server written using the Spark framework respectively. 

![DailyFeast Flow Chart](https://github.com/fabrgu/github.io/blob/master/DailyFeast%20Flow%20Chart.png?raw=true)

The Spark server is deployed on Heroku at thedailyfeast.herokuapp.com. It is connected to a Postgres database and has two major components - the class containing the routes and an updater. The updater runs on a Heroku scheduler every morning at 10am and updates the database with the latest events on the Macalester campus that have free food. The updater scrapes the Daily Piper’s public website and compiles a list of ‘PiperEvents’ that contain strings describing public events on Macalester College’s campus that feature free food for students and then adds them to the database. The main server is very simple and only has two routes - one get route that returns all events with free food from the database and a post route that allows users to submit their own events. It also uses Hibernate to communicate with the Postgres database. 


## Client development

The application is targeted for Android version 19, but the lowest version that it will work with is 11. This Android application was built using IntelliJ IDEA 13, but other android application environments can be used. The android client is written in java and can be found at:  https://github.com/fabrgu/DailyFeast. The android client requires the commons-io-2.4.jar (found at: http://commons.apache.org/proper/commons-io/download_io.cgi), gson-2.2.4.jar (found at: https://code.google.com/p/google-gson/downloads/list), java-json.jar (found at: http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm), and jsoup-1.7.3.jar (found at: http://jsoup.org/download).  To facilitate testing, we used the calendar library to manually switch between weekdays and weekends to see if the weekend AlertDialog, which notifies users that the Daily Piper does not publish on weekends and the database would consequently be initially empty, properly worked. In addition, the client-side worked in conjunction with the server to facilitate testing by clearing the database to test for situations where no events occurred. 


## Server Development

The server engine is written in Java using the Spark framework and uses Maven to handle all its dependencies. To run the server locally, check out a copy of the repository from https://github.com/fabrgu/PiperServer and make sure you have Maven installed on your machine. Maven takes care of all the external libraries required for the server so nothing has to be downloaded and added externally. 

First create a Postgres database and update the hibernate.cfg.xml file with the database’s username, password and URL. Then run the PiperUpdate class that will automatically create the table for PiperEvents, scrape the Daily Piper website for events with free food and add them to the database. The Hibernate config file has set the hbm2ddl property to update so simply running the PiperUpdate class creates the required table. As there is no data that needs to be permanently stored in the DB and all the information is retrieved from an external data source, this update property does not cause any problems. Then run the PiperServer class and the server is running locally! 

You can also deploy the server to Heroku and use a Postgres DB provided as an add-on by Heroku instead. Change the DB configuration parameters accordingly in the Hibernate.cfg.xml file and add the PiperUpdate as a task to the Heroku scheduler add-on. 
