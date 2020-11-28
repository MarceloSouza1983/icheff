
$(document).ready(function () {
    var categoria = window.location.hash.substr(1)
    categoria = categoria ? categoria : 'variados'

    $('#' + categoria + '-tab').addClass('active')
    $('#' + categoria).addClass('show active in')

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/receitas",
        crossDomain: true,
        contentType: "application/json",
        success: function (response) {
            console.log(response)
            response.forEach((receita, indice) => {
                console.log(receita)
                criaCardModal(receita)
            })

            $(".modal .btn-circle").on("click", function() {

                var $button = $(this);
                var oldValue = $button.parent().find("span").text();
                var newVal = 0;

                if ($button.text() == "+" && oldValue < 10) {
                    newVal = parseFloat(oldValue) + 1;
                } else if ($button.text() == "-" && oldValue > 1) {
                    newVal = parseFloat(oldValue) - 1;
                }
              
                if(newVal != 0) {
                    $button.parent().find("span").text(newVal);

                    var $modalFotter = $button.parent().parent();
                    const oldPrice = $modalFotter.find(".price").text();
    
                    const newPrice = newVal * ( parseFloat(oldPrice) / oldValue)

                    $modalFotter.find(".price").text(newPrice.toFixed(2))
                }
         
            });

            $(".modal #comprar").on("click", function() {

                var $button = $(this);
                var modal = $button.parent().parent();

                pararVideo();

                var amount = modal.find(".contador-carrinho").text();
                var desc = modal.find("#modalLabel").text();
                var price = modal.find(".price").text();
            
                adicionarReceita(amount, desc, price);
         
            });
        }
    });
    
});

function getIdCategoria(categoria) {
    switch (categoria) {
        case "Variados":
            return "cards-variados";
        case "Peixes e frutos do mar":
            return "cards-peixes";
        case "Fitness":
            return "cards-fitness";
        case "Veganos e Vegetarianos":
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
    const siglas = ["g", "kg", "mL"]

    if (siglas.includes(ingrediente.unidade)) {
        return ingrediente.unidade.toLowerCase() + " de ";
    }

    if (ingrediente.quantidade != 1) {
        return ingrediente.unidadePlural.toLowerCase() + " de ";
    }

    return ingrediente.unidadeSingular.toLowerCase() + " de ";
}

function criaModal(idModal, nome, porcoes, listaIngredientes, modoPreparo, linkVideo, preco) {

    var modal = document.createElement("div");
    modal.innerHTML = "<div class=\"modal fade\" id=\"modal-" + idModal + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalLabel\" aria-hidden=\"true\">" +
        "<div class=\"modal-dialog\">" +
        "<div class=\"modal-content\">" +
        "<div class=\"modal-header\">" +
        "<div>" +
        "<h3 class=\"modal-title\" id=\"modalLabel\">" + nome + "</h3>" + 
        "<p> <b> Rende: </b> "+ porcoes + " porções</p>" +
        "</div>" +
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
        "<iframe width=\"560\" height=\"315\" src=\"" + linkVideo.replace('watch?v=', 'embed/') + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "<div class=\"modal-footer\">" +
        "<div>" +
        "<button type=\"button\" class=\"btn btn-secondary btn-circle\">" +
        "-" +
        "</button>" +
        "<span class=\"contador-carrinho\">1</span>" +
        "<button type=\"button\" class=\"btn btn-secondary btn-circle\">" +
        "+" +
        "</button>" +
        "</div>" +
        "<button id=\"comprar\" type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">" +
        "<span>R$ </span><span class=\"price\">" + preco + "</span> Adicionar ao carrinho <i class=\"fas fa-shopping-cart\"></i>" +
        "</button>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

    document.body.appendChild(modal);
}

function atualizaPreco(idModal, preco){

    const quantidade = parseInt($("#modal-" + idModal).find('input').val());
    console.log(quantidade)
    console.log($("#modal-" + idModal + " .price").innerHTML)
    $("#modal-" + idModal + " .price").innerHTML = "R$ " + (preco * quantidade);
}

function criaCardModal(receita) {
    criaCard(getIdCategoria(receita.categoria), receita.id, receita.nome, receita.imagem)
    criaModal(receita.id, receita.nome, receita.porcoes, receita.ingredientes, receita.descricao, receita.linkVideo, receita.preco)
}

function pararVideo() {
    $('iframe')[0].contentWindow.postMessage('{"event":"command","func":"stopVideo","args":""}', '*');
}

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
        total += parseFloat(list[key].valor);
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

function adicionarReceita(amount, desc, price) {
    
    if (price.length === 2) {
        price += ".00";
    }

    var value = price;

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