async function changeFactories(index){
    const changed = document.getElementById(index);
    changed.innerHTML = "";
    const res = await fetch(url + '/factory/'+changed.getAttribute("id"), {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const json = await res.json();

    var form = document.createElement('form');
    form.id ="form" + changed.getAttribute("id");
    changed.appendChild(form);
    form.addEventListener("submit", updateFactory);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'name';
    input.value = json['name'];
    input.placeholder="Введите название фабрики";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'region';
    input.value = json['region'];
    input.placeholder="Введите регион, где находится фабрика";
    input.className = "form-control";
    form.appendChild(input);

    var button = document.createElement('button');
    button.type = 'submit';
    button.textContent = 'Обновить';
    button.className = "btn btn-success";
    form.appendChild(button);
}

async function getFactories() {

    const res = await fetch(url + '/factory', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsons = await res.json();
    const factories = document.getElementById("content");
    factories.innerHTML = "";
    var h1 = document.createElement('h1');
    h1.innerText = 'Фабрики';
    factories.appendChild(h1);
    ////////

    var form = document.createElement('form');
    form.id ="add";
    factories.appendChild(form);
    form.addEventListener("submit", addFactory);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'name';
    input.placeholder="Введите название фабрики";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'region';
    input.placeholder="Введите регион, где находится фабрики";
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
        factories.appendChild(iDiv);

        var innerH5 = document.createElement('h5');
        innerH5.innerText = 'ID: ' + json["id"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Название: ' + json["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Регион: ' + json["region"];
        iDiv.appendChild(innerH5);

        var button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Изменить';
        button.onclick = function() { changeFactories(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

        button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Удалить';
        button.onclick = function() { deleteFactory(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

    });
}
async function addFactory(e) {
    if (e.preventDefault) e.preventDefault();
    const factory = document.getElementById(e.target.id);
    var formData = new FormData(factory);
    var object = {};
    formData.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    const res = await fetch(url + '/factory/add' , {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getFactories();
}

async function updateFactory(e) {
    if (e.preventDefault) e.preventDefault();
    const factory = document.getElementById(e.target.id);
    var formData = new FormData(factory);
    var object = {};
    formData.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    const id = e.target.id.split("form");
    const res = await fetch(url + '/factory/upd/' + id[1], {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getFactories();
}

async function deleteFactory(index) {
    const res = await fetch(url + '/factory/del/' + index, {
        method: 'DELETE',
    });

    const resp = await res.json();
    console.log(resp);
    if(resp === "INTERNAL_SERVER_ERROR"){
        alert("Данная фабрика является внешним ключом! Фабрику можно будет удалить только после того, как будут удалены все связанные с ним цены")
    }
    getFactories();
}