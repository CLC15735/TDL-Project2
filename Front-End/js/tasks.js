const findId = new URLSearchParams(window.location.search);

for (let found of findId) {
    let id = found[1];
    console.log(id);
    getOneTask(id);
}

function getOneTask(id) {
    fetch('http://localhost:9094/tasks/read/'+id)
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(data) {
        console.log(data);

       document.getElementById("taskId").value = data.id;
       document.getElementById("taskBody").value = data.taskBody;
       document.getElementById("taskPriority").value = data.taskPriority;
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
}

document.querySelector("form.tasks").addEventListener("submit", function(stop){
    stop.preventDefault();
    let taskForm = document.querySelector("form.tasks").elements;
    console.log(taskForm);
     let id = taskForm["taskId"].value;
     let taskBody = taskForm["taskBody"].value;
     let taskPriority = taskForm["taskPriority"].value;
     updateTask(id, taskBody, taskPriority);
    //  deleteTask(id);
})


function updateTask(id, taskBody, taskPriority) {
    //Convert integer into string
    let updateId = parseInt(id);

    fetch("http://localhost:9094/tasks/update/"+updateId, {
     method: 'put',
     headers: {
       "Content-type": "application/json"
    },
     body:json = JSON.stringify({
      "taskBody": taskBody,
      "taskPriority": taskPriority,
      "id": updateId
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

// function deleteTask(id) {
//   fetch('http://localhost:9094/tasks/delete/' + id, {
//     method: 'DELETE',
//   })
//   .then(res => res.text()) // or res.json()
//   .then(res => console.log(res))
// }




