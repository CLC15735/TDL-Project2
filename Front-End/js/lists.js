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

document.querySelector("form.lists").addEventListener("submit", function(stop){
    stop.preventDefault();
    let listForm = document.querySelector("form.lists").elements;
    console.log(listForm);
     let id = listForm["listId"].value;
     let listTitle = listForm["listTitle"].value;
     let listSubtitle = listForm["listSubtitle"].value;
     updateList(id, listTitle, listSubtitle);
})


function updateList(id, listTitle, listSubtitle) {
    let updateId = parseInt(id);

    fetch("http://localhost:9094/tdLists/update/"+updateId, {
     method: 'put',
     headers: {
       "Content-type": "application/json"
    },
     body:json = JSON.stringify({
      "listTitle": listTitle,
      "listSubtitle": listSubtitle,
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

// function deleteList(id) {
//   fetch('http://localhost:9094/tdLists/delete/' + id, {
//     method: 'DELETE',
//   })
//   .then(res => res.text()) // or res.json()
//   .then(res => console.log(res))
// }