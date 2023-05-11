async function changeProducts(index){
    const changed = document.getElementById(index);
    changed.innerHTML = "";
    const res = await fetch(url + '/product/'+changed.getAttribute("id"), {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const json = await res.json();

    var form = document.createElement('form');
    form.id ="form" + changed.getAttribute("id");
    changed.appendChild(form);
    form.addEventListener("submit", updateProduct);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'name';
    input.value = json['name'];
    input.placeholder="Введите название продукта";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'sort';
    input.value = json['sort'];
    input.placeholder="Введите сорт продукта";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'productGroup';
    input.value = json['productGroup'];
    input.placeholder="Введите группу продукта";
    input.className = "form-control";
    form.appendChild(input);

    var button = document.createElement('button');
    button.type = 'submit';
    button.textContent = 'Обновить';
    button.className = "btn btn-success";
    form.appendChild(button);
}

async function getProducts() {

    const res = await fetch(url + '/product', {
        method: 'GET',
        headers: { Accept: 'application/json' },
    });
    const jsons = await res.json();
    const products = document.getElementById("content");
    products.innerHTML = "";
    var h1 = document.createElement('h1');
    h1.innerText = 'Продукция';
    products.appendChild(h1);
    ////////

    var form = document.createElement('form');
    form.id ="add";
    products.appendChild(form);
    form.addEventListener("submit", addProduct);

    var input = document.createElement('input');
    input.type = 'text';
    input.name = 'name';
    input.placeholder="Введите название продукта";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'sort';
    input.placeholder="Введите сорт продукта";
    input.className = "form-control";
    form.appendChild(input);

    input = document.createElement('input');
    input.type = 'text';
    input.name = 'productGroup';
    input.placeholder="Введите группу продукта";
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
        products.appendChild(iDiv);

        var innerH5 = document.createElement('h5');
        innerH5.innerText = 'ID: ' + json["id"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Название: ' + json["name"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Сорт: ' + json["sort"];
        iDiv.appendChild(innerH5);

        innerH5 = document.createElement('h5');
        innerH5.innerText = 'Группа: ' + json["productGroup"];
        iDiv.appendChild(innerH5);

        var button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Изменить';
        button.onclick = function() { changeProducts(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

        button = document.createElement('a');

        button.className = "btn btn-warning"
        button.innerText = 'Удалить';
        button.onclick = function() { deleteProduct(iDiv.getAttribute("id")); };
        iDiv.appendChild(button);

    });
}
async function addProduct(e) {
    if (e.preventDefault) e.preventDefault();
    const product = document.getElementById(e.target.id);
    var formData = new FormData(product);
    var object = {};
    formData.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    const res = await fetch(url + '/product/add' , {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getProducts();
}

async function updateProduct(e) {
    if (e.preventDefault) e.preventDefault();
    const product = document.getElementById(e.target.id);
    var formData = new FormData(product);
    var object = {};
    formData.forEach((value, key) => object[key] = value);
    var json = JSON.stringify(object);

    const id = e.target.id.split("form");
    const res = await fetch(url + '/product/upd/' + id[1], {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: json
    });
    const resp = await res.json();
    console.log(resp);
    getProducts();
}

async function deleteProduct(index) {
    const res = await fetch(url + '/product/del/' + index, {
        method: 'DELETE',
    });

    const resp = await res.json();
    console.log(resp);
    if(resp === "INTERNAL_SERVER_ERROR"){
        alert("Данный продукт является внешним ключом! Продукт можно будет удалить только после того, как будут удалены все связанные с ним цены")
    }
    getProducts();
}