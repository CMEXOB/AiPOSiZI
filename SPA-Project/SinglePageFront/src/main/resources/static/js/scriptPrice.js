const url = 'http://localhost:8081';

async function changePrices(index){
    const changed = document.getElementById(index);
    changed.innerHTML = "";
    const res = await fetch(url + '/price/'+changed.getAttribute("id"), {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const json = await res.json();

    var form = document.createElement('form');
    form.id ="form" + changed.getAttribute("id");
    changed.appendChild(form);
    form.addEventListener("submit", updatePrice);

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
        if(json['factory']['id'] === jsonFactory["id"]){
            option.setAttribute('selected', true);
        }
        select.appendChild(option);
    });
    form.appendChild(select);

    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите продукт';
    form.appendChild(innerH5);

    select = document.createElement('select');
    select.id = 'productId';
    select.name = 'productId';

    const resProduct = await fetch(url + '/product', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsonsProduct = await resProduct.json();

    jsonsProduct.forEach((jsonProduct) => {
        var option = document.createElement('option');
        option.name = "productId";
        option.innerText = jsonProduct["name"];
        option.value = jsonProduct["id"];
        if(json['product']['id'] === jsonProduct["id"]){
            option.setAttribute('selected', true);
        }
        select.appendChild(option);
    });
    form.appendChild(select);

    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите продукт';
    form.appendChild(innerH5);



    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите дату';
    form.appendChild(innerH5);
    var input = document.createElement('input');
    input.id = 'date';
    input.type = 'date';
    input.name = 'date';
    input.value = json['date'];
    input.className = "form-control";
    form.appendChild(input);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'purchasePrice';
    input.value = json['purchasePrice'];
    input.placeholder="Введите закупочную цену";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'sellingPrice';
    input.value = json['sellingPrice'];
    input.placeholder="Введите отпускную цену";
    input.className = "form-control";
    form.appendChild(input);

    var button = document.createElement('button');
    button.type = 'submit';
    button.textContent = 'Обновить';
    button.className = "btn btn-success";
    form.appendChild(button);
}

async function getPrices() {

    const res = await fetch(url + '/price', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsons = await res.json();
    const prices = document.getElementById("content");
    prices.innerHTML = "";
    var h1 = document.createElement('h1');
    h1.innerText = 'Цены';
    prices.appendChild(h1);
    ////////

    var form = document.createElement('form');
    form.id ="add";
    prices.appendChild(form);
    form.addEventListener("submit", addPrice);

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

    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите продукт';
    form.appendChild(innerH5);

    select = document.createElement('select');
    select.id = 'productId';
    select.name = 'productId';

    const resProduct = await fetch(url + '/product', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsonsProduct = await resProduct.json();

    jsonsProduct.forEach((jsonProduct) => {
        var option = document.createElement('option');
        option.name = "productId";
        option.innerText = jsonProduct["name"];
        option.value = jsonProduct["id"];
        select.appendChild(option);
    });
    form.appendChild(select);



    var innerH5 = document.createElement('h5');
    innerH5.innerText = 'Выберите дату';
    form.appendChild(innerH5);
    // <input id="date" name="date" type="date"><br>
    var input = document.createElement('input');
    input.id = 'date';
    input.type = 'date';
    input.name = 'date';
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'purchasePrice';
    input.placeholder="Введите закупочную цену";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'sellingPrice';
    input.placeholder="Введите отпускную цену";
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
        prices.appendChild(iDiv);

        var innerH5 = document.createElement('h5');
        innerH5.innerText = 'ID: ' + json["id"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Название фабрики: ' + json["factory"]["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Название продукта: ' + json["product"]["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Дата: ' + json["date"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Закупочная: ' + json["purchasePrice"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Отпускная: ' + json["sellingPrice"];
        iDiv.appendChild(innerH5);


        var button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Изменить';
        button.onclick = function() { changePrices(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

        button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Удалить';
        button.onclick = function() { deletePrice(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

    });
}
async function addPrice(e) {
    if (e.preventDefault) e.preventDefault();
    const price = document.getElementById(e.target.id);
    var formData = new FormData(price);
    var object = {};
    var factoryId;
    var productId;
    formData.forEach(function(value, key) {
        if(key === "factoryId"){
            factoryId = value;
        }
        else if(key === "productId"){
            productId = value;
        }
        else{
            object[key] = value;
        }
    });
    var json = JSON.stringify(object);

    const res = await fetch(url + '/price/add?factoryId='+factoryId + '&productId='+productId, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getPrices();
}

async function updatePrice(e) {
    if (e.preventDefault) e.preventDefault();
    const price = document.getElementById(e.target.id);
    var formData = new FormData(price);
    var object = {};
    var factoryId;
    var productId;
    formData.forEach(function(value, key) {
        if(key === "factoryId"){
            factoryId = value;
        }
        else if(key === "productId"){
            productId = value;
        }
        else{
            object[key] = value;
        }
    });
    var json = JSON.stringify(object);

    const id = e.target.id.split("form");
    const res = await fetch(url + '/price/upd/' + id[1] + '?factoryId='+factoryId + '&productId='+productId, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getPrices();
}

async function deletePrice(index) {
    const res = await fetch(url + '/price/del/' + index, {
        method: 'DELETE',
    });

    const resp = await res.json();
    console.log(resp);
    getPrices();
}