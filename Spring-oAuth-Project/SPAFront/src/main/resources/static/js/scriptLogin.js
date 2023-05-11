var jwt;
var url2 = "http://localhost:8080";

async function googleLogin() {
    const res = await fetch(url2 + '/oauth2/authorization/google', {
        method: 'GET',
    });

    const resp = await res.json();
    console.log(resp);
}

async function logout() {
    name = null;
    jwt = null;
    var l = document.getElementById("login");
    var panel = document.getElementById("panel");
    panel.innerText = '';
    l.innerHTML = '';

    await fetch(url2 + '/logout', {
        method: 'GET',
    });

    var button = document.createElement('a');
    button.className = "btn btn-warning"
    button.innerText = 'Войти через Google';
    button.href = "/oauth2/authorization/google"
    l.appendChild(button);

    var button = document.createElement('a');
    button.className = "btn btn-warning"
    button.innerText = 'Войти через GitHub';
    button.href = "/oauth2/authorization/github"
    l.appendChild(button);
}

function prepare(){
    if(jwt === null){
        var l = document.getElementById("login");
        var panel = document.getElementById("panel");
        console.log(l);
        console.log(panel);
        panel.innerText = '';
        l.innerHTML = '';

        var button = document.createElement('a');
        button.className = "btn btn-warning"
        button.innerText = 'Войти через Google';
        button.href = "/oauth2/authorization/google"
        l.appendChild(button);

        var button = document.createElement('a');
        button.className = "btn btn-warning"
        button.innerText = 'Войти через GitHub';
        button.href = "/oauth2/authorization/github"
        l.appendChild(button);
    }
    else {
        var panel = document.getElementById("panel");
        panel.innerText = 'Приветствую ' + name;
        var l = document.getElementById("login");
        l.innerHTML = '';

        var button = document.createElement('a');
        button.className = "btn btn-warning"
        button.innerText = 'Выйти';
        button.onclick = function() { logout(); };
        l.appendChild(button);
    }
}
prepare()