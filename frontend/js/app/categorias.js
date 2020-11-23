$( document ).ready(function() {
    var categoria = window.location.hash.substr(1)
    categoria = categoria ? categoria : 'variados'
    
    $('#'+categoria+'-tab').addClass('active')
    $('#'+categoria).addClass('show active in')

    $.ajax({
        type : "GET",
        url : "http://localhost:8080/api/receitas",
        crossDomain: true,
        contentType: "application/json",
        success: function(response){
          console.log(response)
          response.forEach( (receita, indice) => {
              console.log(receita)
            criaCardModal(receita)
          })
        }
    });
});

function getIdCategoria(categoria) {
    switch(categoria){
        case "Variados":
            return "cards-variados";
        case "Peixes e frutos do mar":
            return "cards-peixes";
        case "Fitness":
            return "cards-fitness";
        case "Vegetarianos":
            return "cards-vegetarianos";
        default:
            ""
    }
}

function criaCard(idCategoria, idModal, nome, imagem) {
    var card = document.createElement("div");
    card.className = "col-12 col-md-6 col-lg-4";
    card.innerHTML =
        "<div class=\"card\">" +
        "<img src=\"" + imagem + "\" class=\"card-img-top\" alt=\"" + nome + "\">" +
        "<div class=\"card-body\">" +
        "<h5 class=\"card-title\">" + nome + "</h5>" +
        "<button class=\"btn btn-cards\" data-toggle=\"modal\" data-backdrop=\"static\" data-keyboard=\"false\" data-target=\"#modal-" + idModal + "\">" +
        "Detalhes" +
        "</button>" +
        "</div>" +
        "</div>";
    document.getElementById(idCategoria).appendChild(card);
}

function criaLista(lista) {
    var ingredientes = "";

    lista.forEach(ingrediente => {
        ingredientes += '<li>' + ingrediente.quantidade + " " + getUnidade(ingrediente) + ingrediente.nome.toLowerCase() + '</li>'
    });

    return ingredientes;
}

function getUnidade(ingrediente) {
        if (ingrediente.unidadeSingular == "Unidade"){
            return " ";
        } 

        if (ingrediente.quantidade != 1){
            return ingrediente.unidadePlural.toLowerCase() + " de ";
        } 

        return ingrediente.unidadeSingular.toLowerCase() + " de ";
}

function criaModal(idModal, nome, listaIngredientes, modoPreparo, linkVideo, preco) {

    var modal = document.createElement("div");
    modal.innerHTML = "<div class=\"modal fade\" id=\"modal-" + idModal + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalLabel\" aria-hidden=\"true\">" +
        "<div class=\"modal-dialog\">" +
        "<div class=\"modal-content\">" +
        "<div class=\"modal-header\">" +
        "<h3 class=\"modal-title\" id=\"modalLabel\">" + nome + "</h3>" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" onclick=\"pararVideo()\" aria-label=\"Close\">" +
        "<span aria-hidden=\"true\">&times;</span>" +
        "</button>" +
        "</div>" +
        "<div class=\"modal-body\">" +
        "<div class=\"container\">" +
        "<div class=\"row modal-receitas-body\">" +
        "<div class=\"container\"><h4>Lista de ingredientes <i class=\"fas fa-clipboard-list\"></i></h4><ul>" +
        criaLista(listaIngredientes) +
        "</ul></div>" +
        "<div class=\"container\">" +
        "<h4>Modo de preparo <i class=\"fas fa-utensils\"></i></h4>" +
        "<p>" + modoPreparo + "</p></div>" +
        "<iframe width=\"560\" height=\"315\" src=\"" +  linkVideo + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "<div class=\"modal-footer\">" +
        "<div class=\"number-wrapper\">" +
        "<input type=\"number\" class=\"quantidade\" value=\"1\" min=\"1\" max=\"10\" id=\"qtd1\">" +
        "</input>" +
        "</div>" +
        "<button type=\"button\" class=\"btn btn-secondary\" onclick=\"adicionarReceita()\" data-dismiss=\"modal\">" +
        "<span class=\"price\"> R$ " + preco + "</span> Adicionar ao carrinho <i class=\"fas fa-shopping-cart\"></i>" +
        "</button>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

    document.body.appendChild(modal);
}

function criaCardModal(receita) {
    criaCard(getIdCategoria(receita.categoria), receita.id, receita.nome, receita.imagem)
    criaModal(receita.id, receita.nome, receita.listaIngredientes, receita.descricao, receita.linkVideo, receita.preco)
}

//Método apenas para teste, excluir depois de cadastrar as receitas no banco de dados
function criaCardModal2(categoria, idModal, nome, descricao, imagem, listaIngredientes, linkVideo, preco ) {
    criaCard(categoria, idModal, nome, imagem)
    criaModal(idModal, nome, listaIngredientes, descricao, linkVideo, preco)
}

function pararVideo(){
    $('iframe')[0].contentWindow.postMessage('{"event":"command","func":"stopVideo","args":""}', '*');
 }

const lista = [
    { quantidade: 1, nome:'cebola pequena', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 3, nome:'dentes de alho triturados ou picados;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 400, nome:'carne moída (patinho);', unidadeSingular: 'Grama', unidadePlural: "Gramas"},
    { quantidade: 1, nome:'orégano seco;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 1, nome:'sal;', unidadeSingular: 'Colher de sopa', unidadePlural: "Colheres de sopa"},
    { quantidade: 1, nome:'pimenta-do-reino a gosto;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 2, nome:'sachê de molho pronto;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 1, nome:'Massa para lasanha;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 350, nome:'presunto;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 350, nome:'queijo Muçarela;', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
    { quantidade: 1, nome:'Queijo parmesão a gosto.', unidadeSingular: 'Unidade', unidadePlural: "Unidades"},
];

const lista2 = [];

criaCardModal2("cards-variados", 10, "Lasanha Bolonhesa", "Receita de Lasanha Bolonhesa", "img/lasanha.jpg", lista, "https://www.youtube.com/embed/-9Wp7NfeTBY?enablejsapi=1", 50)
criaCardModal2("cards-variados", 20, "Costela Barbecue", "Receita de Costela Barbecue", "img/barbecue.jpg", lista2, "", 38)
criaCardModal2("cards-variados", 30, "Hamburguer", "Receita de Hamburguer", "img/hamburguer.jpg", lista2, "", 50)
criaCardModal2("cards-peixes", 40, "Costela Barbecue", "Receita de Costela Barbecue", "img/barbecue.jpg", lista2, "", 38)
criaCardModal2("cards-fitness", 50, "Hamburguer", "Receita de Hamburguer", "img/hamburguer.jpg", lista2, "", 40)
criaCardModal2("cards-vegetarianos", 60, "Lasanha Bolonhesa", "Receita de Lasanha Bolonhesa", "img/lasanha.jpg", lista2, "", 45)
criaCardModal2("cards-variados", 70, "Pizza Marguerita", "Receita de Pizza Marguerita", "img/pizza.jpg", lista2, "", 50)

function mostrarCarrinho() {
    if (document.getElementById("carrinho").style.display === "block") {
        document.getElementById("carrinho").style.display = "none";
    } else {
        document.getElementById("carrinho").style.display = "block";
    }
}

var list = [];

//somando total
function getTotal(list) {
    var total = 0;
    for (var key in list) {
        total += list[key].valor * list[key].quantidade;
    }
    document.getElementById("totalValue").innerHTML = formatValue(total);
}

//criando a tabela
function setList(list) {
    var table = '<thead><tr><td>Descrição</td><td>Quantidade</td><td>Valor</td><td>Ação</td></tr></thead><tbody>';
    for (var key in list) {
        table += '<tr><td>' + list[key].descricao + '</td><td>' + list[key].quantidade + '</td><td>' + list[key].valor + '</td><td> <button class="btn btn-danger" onclick="deleteData(' + key + ');"><i class="fas fa-eraser"></i> Delete</button></td></tr>';
        
    }
    table += '</tbody>';
    console.log(list);

    document.getElementById('listTable').innerHTML = table;
    getTotal(list);
    saveListStorage(list);
}

//formatando o preço
function formatValue(value) {
    var str = parseFloat(value).toFixed(2) + "";
    str = str.replace(".", ",");
    str = "R$ " + str;
    return str;
}

function adicionarReceita() {
    
    pararVideo();

    var amount = document.getElementById("qtd1").value;
    var desc = document.getElementById("modalLabel").textContent;
    var valueDesformatada = document.getElementById("precoReceita").textContent;
    var str = "";
    str = valueDesformatada.substring(3, 10).trim();

    if (str.length === 2) {
        str += ".00";
    }

    var value = str;

    list.push({ "descricao": desc, "quantidade": amount, "valor": value });
    setList(list);
}

//deletando os dados
function deleteData(id) {
    if (confirm("Deseja deletar este item?")) {
        if (id === list.length - 1) {
            list.pop();
        } else if (id === 0) {
            list.shift();
        } else {
            var arrAuxIni = list.slice(0, id);
            var arrAuxEnd = list.slice(id + 1);
            list = arrAuxIni.concat(arrAuxEnd);
        }
        setList(list);
    }
}

//deletando Pedido
function deleteList() {
    if (confirm("Deseja deletar o pedido?")) {
        list = [];
        setList(list);
    }
}

//salvando em storage
function saveListStorage(list) {
    var jsonStr = JSON.stringify(list);
    localStorage.setItem("list", jsonStr);
}

//verifica se já tem algo salvo
function initListStorage() {
    var testList = localStorage.getItem("list");
    if (testList) {
        list = JSON.parse(testList);
    }
    setList(list);
}

initListStorage();