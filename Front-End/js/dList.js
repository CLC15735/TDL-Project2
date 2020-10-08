const findId = new URLSearchParams(window.location.search);

for (let found of findId) {
    let id = found[1];
    console.log(id);
    getOneList(id);
}

function getOneList(id) {
    fetch('http://localhost:9094/tdLists/read/'+id)
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

        document.getElementById("listId").value = data.id;
        document.getElementById("listTitle").value = data.listTitle;
        document.getElementById("listSubtitle").value = data.listSubtitle;
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
}

document.querySelector("form.dlists").addEventListener("submit", function(stop){
    stop.preventDefault();
    let listForm = document.querySelector("form.dlists").elements;
    console.log(listForm);
     let id = listForm["listId"].value; 
     
     deleteList(id);    
})

function deleteList(id) {
    fetch('http://localhost:9094/tdLists/delete/' + id, {
    method: 'DELETE',
    })
    .then(res => res.text()) // or res.json()
    .then(res => console.log(res))
    }