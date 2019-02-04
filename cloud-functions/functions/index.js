const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");
});

exports.showCollections = functions.https.onRequest((req, res) => {
    
    let collections = [];
    let db = admin.firestore();
    db.collection("project").get().then(snapshot => {

        snapshot.forEach(doc => {
            let newelement = {
                "desc": doc.data().desc,
                "title": doc.data().title
            }
            collections = collections.concat(newelement);
        });
        res.send(collections)
        return "";
    }).catch(reason => {
        res.send(reason)
    })
});

exports.getAllTasks = functions.https.onRequest((req, res) =>{
    getTodos().
        then((projects) => {
            console.log("all projects " + projects) 
            return res.json(projects);
        })
        .catch((err) => {
            console.log('Error getting documents', err);
            return res.status(500).json({ message: "Error getting the all projects" + err });
        });
});

function getTodos(){
    var projectRef = db.collection('project');

    return projectRef.get()
        .then((snapshot) => {
            let projects = [];
            return Promise.all(
                snapshot.docs.map(doc => {  
                        let project = {};                
                        project.id = doc.id;
                        project.project = doc.data(); 
                        var projectTasksPromise = getTaskItem(project.id);
                        return projectTasksPromise.then((tasks) => {                    
                                project.tasks = tasks;
                                projects.push(project);         
                                return projects;                  
                            }) 
                })
            )
            .then(projects => {
                return projects.length > 0 ? projects[projects.length - 1] : [];
            })

        })
}


function getTaskItem(id){
    var taskRef = db.collection('project').doc(id).collection('tasks');
    let tasks = [];
    return taskRef.get()
        .then(snapshot => {
            snapshot.forEach(task => {
                let task_item = {};
                tasks.id = task.id;
                tasks.task_item = task.data();           
                tasks.push(task_item);
            })
            return tasks;
        })
}