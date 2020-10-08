fetch('http://localhost:9094/tdLists/read')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(alldata) {
        console.log(alldata);
        
        for (let key of alldata) {
          document.querySelector("form.addtasks").addEventListener("submit", function(stop){
          stop.preventDefault();
          let taskForm = document.querySelector("form.addtasks").elements;
          console.log(taskForm);
          let taskBody = taskForm["taskBody"].value;
          let taskPriority = taskForm["taskPriority"].value;
          addTask(key[id], taskBody, taskPriority);
        })
        }

        let data = Object.keys(alldata[0]);

        createCard(alldata);
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });

function createCard(alldata) {
    for (let key of alldata) {
        console.log(key);
           
        let find = document.getElementById("cardContainer");
        let card = document.createElement("div");
        card.className = "card";
        card.style = "width: 28rem;";
        find.appendChild(card);

        console.log(key['listTitle']);
        let header = document.createElement("div");
        header.className ="headerCard";
        let h1 = document.createElement("h1");
        h1.style = "color: rgb(4, 4, 53); font-size: 350%; font-family: 'TasksFont';";
        h1.className = "card-title";
        let textTitle = document.createTextNode(key['listTitle']);
            
        h1.appendChild(textTitle);
        card.appendChild(header);
        header.appendChild(h1);

        console.log(key['listSubtitle']);
        let p = document.createElement("p");
        p.className = "card-subtitle";
        p.style = "color: rgb(4, 4, 53); font-size: 160%; font-family:'TasksFont';";
        let textSubtitle = document.createTextNode(key['listSubtitle'] + " ");

        p.appendChild(textSubtitle);
        header.appendChild(p);

        //Edit, delete and create
        let edit = document.createElement("a");
        edit.href = "viewLists.html?id=" + key['id'];
        let editIcon = document.createElement("i");
        editIcon.className = "far fa-edit";
        editIcon.style = "color: rgb(4, 4, 53); position:relative; left: 3mm;";
        let trash = document.createElement("a");
        trash.href = "deleteList.html?id=" + key['id'];;
        let trashIcon = document.createElement("i");
        trashIcon.className = "far fa-trash-alt";
        trashIcon.style = "color: rgb(4, 4, 53); position:relative; left: 4mm;";
        let add = document.createElement("a");
        add.href = "newList.html";
        let addIcon = document.createElement("i");
        addIcon.className = "fas fa-plus";
        addIcon.style = "color: rgb(4, 4, 53); position:relative; left: 5mm;";

        p.appendChild(edit);
        edit.appendChild(editIcon);
        p.appendChild(trash);
        trash.appendChild(trashIcon);
        p.appendChild(add);
        add.appendChild(addIcon);

        console.log(key['tasks']);
        let body = document.createElement("div");
        let ul = document.createElement("ul");
            
        ul.className = "list-group";
        body.className ="card-body";
            
        card.appendChild(body);
        body.appendChild(ul);
            
        for(values of key['tasks']) {
            console.log(values['taskBody']); 

            let li = document.createElement("li");
            let check = document.createElement("div");
            check.className = "form-group form-check";
            li.className = "list-group-item";
            li.style = "border-style: hidden;";

            let input = document.createElement("input");
            input.type = "checkbox";
            input.className = "form-check-input";
                
            let label = document.createElement("label");
            let textBody = document.createTextNode(values['taskBody'] + "");
            label.className = "form-check-label";

            //Info +trash2
            let info = document.createElement("a");
            info.href = "viewTasks.html?id=" + values['id'];
            let infoIcon = document.createElement("i");
            infoIcon.className = "fas fa-info";
            infoIcon.style = "color: rgb(4, 4, 53); position:relative; left: 8mm;";

            let trash2 = document.createElement("a");
            trash2.href = "deleteTask.html?id=" + values['id'];
            let trash2Icon = document.createElement("i");
            trash2Icon.className = "far fa-trash-alt";
            trash2Icon.style = "color: rgb(4, 4, 53); position:relative; left: 10mm;";

            label.appendChild(textBody);
            ul.appendChild(li);
            li.appendChild(check);
            check.appendChild(input);
            check.appendChild(label);
            check.appendChild(info);
            info.appendChild(infoIcon);
            check.appendChild(trash2);
            trash2.appendChild(trash2Icon);
          }
        
        let a = document.createElement("a");
        a.className = "btn btn-outline-primary";
        a.style = "transform: translate(80%, 30%);";
        a.href = "newTask.html?id=" + values['id'];
        let addTask = document.createTextNode("  Add a new task")
        body.appendChild(a); 

        let icon = document.createElement("i");
        icon.className = "far fa-plus-square";
            
        a.appendChild(icon); 
        a.appendChild(addTask);
        }
    }

  