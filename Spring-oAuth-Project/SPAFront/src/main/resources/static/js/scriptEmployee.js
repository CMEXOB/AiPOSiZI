async function changeEmployees(index){
    const changed = document.getElementById(index);
    const res = await fetch(url + '/employee/'+changed.getAttribute("id"), {
        method: 'GET',
        headers: {
            Accept: 'application/json' ,
            'Authorization': jwt
        },
    });
    const json = await res.json();
    if(json === 'UNAUTHORIZED'){
        alert("UNAUTHORIZED!")
    }
    else {
        changed.innerHTML = "";
        var form = document.createElement('form');
        form.id = "form" + changed.getAttribute("id");
        changed.appendChild(form);
        form.addEventListener("submit", updateEmployee);

        var innerH5 = document.createElement('h5');
        innerH5.innerText = 'Выберите фабрику';
        form.appendChild(innerH5);

        var select = document.createElement('select');
        select.id = 'factoryId';
        select.name = 'factoryId';

        const resFactory = await fetch(url + '/factory', {
            method: 'GET',
            headers: {Accept: 'application/json'},
        });
        const jsonsFactory = await resFactory.json();

        jsonsFactory.forEach((jsonFactory) => {
            console.log(jsonFactory)
            var option = document.createElement('option');
            option.name = "factoryId";
            option.innerText = jsonFactory["name"];
            option.value = jsonFactory["id"];
            if (json['factory']['id'] === jsonFactory["id"]) {
                option.setAttribute('selected', true);
            }
            select.appendChild(option);
        });

        form.appendChild(select);

        var input = document.createElement('input');
        input.type = 'text';
        input.name = 'name';
        input.value = json['name'];
        input.placeholder = "Введите название продукта";
        input.className = "form-control";
        form.appendChild(input);

        input = document.createElement('input');
        input.type = 'text';
        input.name = 'surname';
        input.value = json['surname'];
        input.placeholder = "Введите сорт продукта";
        input.className = "form-control";
        form.appendChild(input);

        var button = document.createElement('button');
        button.type = 'submit';
        button.textContent = 'Обновить';
        button.className = "btn btn-success";
        form.appendChild(button);
    }
}

async function getEmployees() {

    const res = await fetch(url + '/employee', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsons = await res.json();
    const employees = document.getElementById("content");
    employees.innerHTML = "";
    var h1 = document.createElement('h1');
    h1.innerText = 'Работники';
    employees.appendChild(h1);
    ////////
    var form = document.createElement('form');
    form.id ="add";
    employees.appendChild(form);
    form.addEventListener("submit", addEmployee);

    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите фабрику';
    form.appendChild(innerH5);

    var select = document.createElement('select');
    select.id = 'factoryId';
    select.name = 'factoryId';

    const resFactory = await fetch(url + '/factory', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsonsFactory = await resFactory.json();

    jsonsFactory.forEach((jsonFactory) => {
        var option = document.createElement('option');
        option.name = "factoryId";
        option.innerText = jsonFactory["name"];
        option.value = jsonFactory["id"];
        select.appendChild(option);
    });
    form.appendChild(select);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'name';
    input.placeholder="Введите имя работника";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'surname';
    input.placeholder="Введите фамилия работника";
    input.className = "form-control";
    form.appendChild(input);

    var button = document.createElement('button');
    button.type = 'submit';
    button.textContent = 'Добавить';
    button.className = "btn btn-success";
    form.appendChild(button);
    ////////
    jsons.forEach((json) => {
        var iDiv = document.createElement('div');
        iDiv.id = json["id"];
        iDiv.className = 'alert alert-info mt-2';
        employees.appendChild(iDiv);

        var innerH5 = document.createElement('h5');
        innerH5.innerText = 'ID: ' + json["id"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Название фабрики: ' + json["factory"]["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Имя: ' + json["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Фамилия: ' + json["surname"];
        iDiv.appendChild(innerH5);

        var button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Изменить';
        button.onclick = function() { changeEmployees(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

        button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Удалить';
        button.onclick = function() { deleteEmployee(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

    });
}
async function addEmployee(e) {
    if (e.preventDefault) e.preventDefault();
    const employee = document.getElementById(e.target.id);
    var formData = new FormData(employee);
    var object = {};
    var factoryId;
    formData.forEach(function(value, key) {
        if(key === "factoryId"){
            factoryId = value;
        }
        else{
            object[key] = value;
        }
    });
    var json = JSON.stringify(object);

    const res = await fetch(url + '/employee/add?factoryId='+factoryId , {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': jwt
        },
        body: json
    });
    const resp = await res.json();
    if(resp === 'UNAUTHORIZED'){
        alert("UNAUTHORIZED!")
    }
    getEmployees();
}

async function updateEmployee(e) {
    if (e.preventDefault) e.preventDefault();
    const employee = document.getElementById(e.target.id);
    var formData = new FormData(employee);
    var object = {};
    var factoryId;

    formData.forEach(function(value, key) {
        if(key === "factoryId"){
            factoryId = value;
        }
        else{
            object[key] = value;
        }
    });
    var json = JSON.stringify(object);

    const id = e.target.id.split("form");
    const res = await fetch(url + '/employee/upd/' + id[1] + '?factoryId='+factoryId, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': jwt
        },
        body: json
    });
    const resp = await res.json();
    if(resp === 'UNAUTHORIZED'){
        alert("UNAUTHORIZED!")
    }
    getEmployees();
}

async function deleteEmployee(index) {
    const res = await fetch(url + '/employee/del/' + index, {
        method: 'DELETE',
        headers: {
            'Authorization': jwt
        }
    });
    const resp = await res.json();
    if(resp === 'UNAUTHORIZED'){
        alert("UNAUTHORIZED!")
    }
    getEmployees();
}