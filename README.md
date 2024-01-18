# A Comprehensive Guide on Schedu Now😊

>  Struggling to keep track of your busy schedule? Say goodbye to missed appointments, forgotten tasks, and oversleeping alarms. This innovative Android Calendar App is designed to streamline your life.

## Inspiration🌟

The inspiration behind this project, Schedu Now, stemmed from a deep understanding of the common issue of indecisiveness and procrastination that many people face in their daily lives. We recognized that in a world brimming with endless choices and distractions, making decisions and sticking to them can be incredibly challenging. This realization was the catalyst for creating Schedu Now – an app designed to empower individuals to take control of their time and tasks effectively.

## What it does🚀
Schedu Now, as a creative Android calendar app, offers a comprehensive suite of features designed to enhance time management and productivity. 

One of the standout features of our app, Schedu Now, is the innovative integration of the calendar and tasks on the same screen. This unique design choice significantly enhances the user experience by offering a streamlined and cohesive view of their schedule and to-dos. Presenting both elements together by utilizing various ViewModels eliminates the need to toggle between different sections of the app to view separate elements of one’s schedule, thus reducing the cognitive load and making time management more manageable and less overwhelming. This seamless integration is achieved through the incorporation of Live Data, which keeps track of and observes data changes. 
In addition, the app allows for setting up customizable alarms and notifications for both tasks and calendar events. This feature is designed to cater to different alert preferences and ensures timely reminders. 
The app is designed with an intuitive interface, making it easy for users of all tech levels to navigate and utilize its features effectively. 
For users with multiple devices, the app offers synchronization across them, ensuring that your calendar and tasks are updated and accessible wherever you go.

## How we built it👍

This project was developed in Android Studio using Java as the programming language, and utilized GitHub for version control. The process involved:

- Developing the Calendar
- Implementing the SQLite Database and DAO accessed through Android Room
- Incorporate a RecyclerViewModel as the task list
- Connecting a separate screen with a Floating Action Button to insert tasks and appointments
- Enhancing User Interface through RoomViewModel to organize the calendar and task list on the same screen
- Utilizing Live Data to update data to display on screen right after inserting a new task/appointment
- Adding alarm settings

## Challenges we ran into🍕

We encountered several challenges during the development of Schedu Now:

1. securely extracting new tasks or appointments (represented as strings) from the sub class into the main class and inserting them into the database
To solve this challenge, I developed a specific algorithm within the onActivityResult method of my MainActivity Class. When transitioning to NewWordActivity to insert a new task or appointment, it requires a request code. Once the data is input and ready to return to the main class (MainActivity), it carries a result code with it — either RESULT_OK if data is input, or RESULT_CANCELLED if no data is input and returned. The method first checks if the requestCode matches NEW_WORD_ACTIVITY_REQUEST_CODE, confirming that the returning result is from the activity where a new task or appointment is added. It also checks if the resultCode is RESULT_OK, indicating that the operation was successful. If both conditions are met, the method extracts the string data (the new task or appointment) using data.getStringExtra(NewWordActivity.EXTRA_REPLY). This string represents the user's input from the other activity. The data is then inputted into the database, accompanied by the date and an autogenerated ID.

## What we learned🎉
Throughout the development phase of Schedu Now, I engaged in an extensive exploration of the Android Room, developing app applications through Android Studio. This venture considerably enhanced my comprehension of database structures, the algorithms required for secure data transfer between classes, and SQLite commands. My expertise in Java was further refined, a critical aspect for the seamless integration of diverse app components. Moreover, this project served as a practical platform to enhance my proficiency in algorithmic troubleshooting and debugging utilizing Log.
