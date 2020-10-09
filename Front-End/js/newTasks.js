const findId = new URLSearchParams(window.location.search);


document.querySelector("form.ntasks").addEventListener("submit", function(stop){
    stop.preventDefault();
    let taskForm = document.querySelector("form.ntasks").elements;
    console.log(taskForm);
     let taskBody = taskForm["taskBody"].value;
     let taskPriority = taskForm["taskPriority"].value;
     for (let found of findId) {
        let id = found[1];
        console.log(id);
        addTask(id, taskBody,taskPriority);   
     }  
})

function addTask(id, taskBody, taskPriority) {
    //Convert integer into string
    let listId = parseInt(id);

    fetch("http://localhost:9094/tasks/create", {
     method: 'post',
     headers: {
       "Content-type": "application/json"
    },
     body:json = JSON.stringify({
      "taskBody": taskBody,
      "taskPriority": taskPriority,
      "tdLists": {
        "id" : listId
      }
     })
   })
   .then(res => res.json())
      .then(function (data) {
             console.log('Request succeeded with JSON response', data);
               })
  .catch(function (error) {
         console.log('Request failed', error);
   });
}
