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

document.querySelector("form.dtasks").addEventListener("submit", function(stop){
    stop.preventDefault();
    let taskForm = document.querySelector("form.dtasks").elements;
    console.log(taskForm);
     let id = taskForm["taskId"].value;
     
     deleteTask(id);    
})

function deleteTask(id) {
    fetch('http://localhost:9094/tasks/delete/' + id, {
      method: 'DELETE',
    })
    .then(res => res.text()) // or res.json()
    .then(res => console.log(res))
  }