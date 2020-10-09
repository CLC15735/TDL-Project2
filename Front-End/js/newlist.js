document.querySelector("form.nlists").addEventListener("submit", function(stop){
    stop.preventDefault();
    let listForm = document.querySelector("form.nlists").elements;
    console.log(listForm);
     let listTitle = listForm["listTitle"].value;
     let listSubtitle = listForm["listSubtitle"].value;
     addList(listTitle, listSubtitle);
})

function addList(listTitle, listSubtitle) {
    fetch("http://localhost:9094/tdLists/create", {
        method: 'post',
        headers: {
        "Content-type": "application/json"
        },
        body:json = JSON.stringify({
            "listTitle": listTitle,
            "listSubtitle": listSubtitle,
        })
    })
    .then(json)
    .then(function (data) {
        console.log('Request succeeded with JSON response', data);
    })
    .catch(function (error) {
        console.log('Request failed', error);
    });
}