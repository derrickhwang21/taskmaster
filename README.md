# Task Master
Building an Android Task Master App. Which allows tracking tasks on a project.

## Feature Task

### Feature Tasks


## Fire Base Functionality

Firebase as a backend for our TaskMaster App

Home          |  User Welcome Screen |  Home After Add
:-------------------------:|:-------------------------: | :--------------------------------:
![sc-home](assets/firebase-add-project.png)  |  ![sc-add-project](assets/firebase-addproject1.png) | ![sc-home-project](assets/firebase-addproject2.png)

#### User Login 

User login with email and password

Welcome Page          |  SignIn with email or google |  User Welcome Page
:-------------------------:|:-------------------------: | :--------------------------------:
![sc-home](assets/firebase-signin1.png)  |  ![sc-add-project](assets/firebase-signin2.png) | ![sc-home-project](assets/firebase-signin3.png)

#### User Projects in Firebase Database

User created projects in Firestore cloud database

Add Project          |  Firebase Project
:-------------------------:|:-------------------------:
![sc-add-task](assets/firebase-addproject1.png)  |  ![sc-add-task-after](assets/firebase-snip-project.PNG)

Edit Project          |  Edit Options
:-------------------------:|:-------------------------:
![sc-add-task](assets/firebase-projectedit.png)  |  ![sc-add-task-after](assets/firebase-projectedit2.png)

#### User Tasks in Firebase Database

User created tasks to those projects

Task Activity          | Add Task Activity |  Task Activity
:-------------------------:|:-------------------------: | :--------------------------------:
![sc-home](assets/firebase-add-task.png)  |  ![sc-add-project](assets/firebase-addtask1.png) | ![sc-home-project](assets/firebase-addtask2.png)

#### User ability to assign task

Ability to allow users to "Assign" a task to themself

The user assgined to a task should be able to "Accept" that task, and later "Finish" that task. 

Update Task          |  Assign Task |  Task After Update
:-------------------------:|:-------------------------: | :--------------------------------:
![sc-home](assets/firebase-updatetask.png)  |  ![sc-add-project](assets/firebase-updatetask2.png) | ![sc-home-project](assets/firebase-updatetask3.png)

Firebase Task          |  Firebase Task After
:-------------------------:|:-------------------------:
![sc-add-task](assets/firebase-snip-task.PNG)  |  ![sc-add-task-after](assets/firebase-snip-task2.PNG)

## Database Structure

* Project

|  Field |  Type    |
|-------------|------------|
| projectId  | Long  |
| taskId | long |
| title | String |
| description  | String |


* Task

|  Field      |  Type      |
|-------------|------------|
| taskId  | Long  |
| title | String  |
| description | String |
| finishBy   | finishBy |
| taskState | State |
| projectReference | String |

## Cloud Function

#### View all Tasks assigned to them on a screen

[Show Projects](https://us-central1-taskmaster-9484b.cloudfunctions.net/showCollections)

[Show Tasks](https://us-central1-taskmaster-9484b.cloudfunctions.net/getAllTasks)

[Path to Cloud Functions](/cloud-functions)


## Credit/Sources 
[RecyclerView](https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465)

[Material Icons](http://google.github.io/material-design-icons/)

[Floating Action Bar](https://guides.codepath.com/android/floating-action-buttons)

[Enums](https://javarevisited.blogspot.com/2011/08/enum-in-java-example-tutorial.html) 
 * special shout out to Tara Johnson, Suzanne Richman, and Michelle Ferreirae for their conceptual teaching
 
 [Radio Group - Check Listener](https://stackoverflow.com/questions/18536195/android-oncheckedchanged-for-radiogroup)
 
 [Type Converter](https://stackoverflow.com/questions/47435686/room-orm-enum-type-converter-error)
 
 [Type Converter - 2](https://stackoverflow.com/questions/44498616/android-architecture-components-using-enums)
 
 [Document Reference - Sub Collection](https://stackoverflow.com/questions/47514419/how-to-add-subcollection-to-a-document-in-firebase-cloud-firestore)
 
 [Document Reference](https://codelabs.developers.google.com/codelabs/firestore-android/#6)
 
 [Cloud Function - Reading from Firestore](https://stackoverflow.com/questions/48375904/read-data-from-cloud-firestore-with-firebase-cloud-function/49516133#49516133)
 
 [Cloud function](https://firebase.google.com/docs/functions/functions-and-firebase#cloud_functions_for_firebase)
 
 ## ChangeLog
 
 1/28/2019: Initial project setup(layout, room, migrate to androidX, activity adding and deleting tasks)
 
 1/29/2019: Added in button group and enum type properties to entity model. 
 
 1/30/2019: Finished enum properties, added in state converter class, refactored adapter and activity, updated readme.
 
 1/31/2019: Added in Sign in for Firebase
 
 2/01/2019: Added in Signout for Firebase and finished auth
 
 2/02/2019: Added in Firestore and completed add, delete functionality to Firestore database. 
 
 2/03/2019: Updated readme and added in Firebase update functionality , added cloud functions, added sample of how data is stored in firestore


