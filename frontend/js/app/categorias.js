const BASE_URL = window.location.protocol + '//' + window.location.hostname + (window.location.port != '' ? ':' + window.location.port : '');

$(document).ready(function () {
    var categoria = window.location.hash.substr(1)
    categoria = categoria ? categoria : 'variados'

    $('#' + categoria + '-tab').addClass('active')
    $('#' + categoria).addClass('show active in')

    $.ajax({
        type: "GET",
        url: BASE_URL + "/api/receitas",
        crossDomain: true,
        contentType: "application/json",
        success: function (response) {
            
            response.forEach((receita, indice) => {
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
                var price = parseFloat(modal.find(".price").text()) / amount;
                var receita_id = $button.attr('receita_id');
            
                adicionarReceita(amount, desc, price, receita_id);

                mostrarCarrinho();
         
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
        "<button class=\"btn btn-cards\" data-toggle=\"modal\" data-backdrop=\"static\" data-keyboard=\"false\" onclick=\"montarVideo(this)\" data-target=\"#modal-" + idModal + "\">" +
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

function criaModal(idModal, nome, porcoes, listaIngredientes, modoPreparo, linkVideo, preco, receita_id) {

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
        "<noscript><iframe width=\"560\" height=\"315\" src=\"" + linkVideo.replace('watch?v=', 'embed/') + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe></noscript>" +
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
        "<button id=\"comprar\" type=\"button\" receita_id=\"" + receita_id + "\" class=\"btn btn-secondary\" data-dismiss=\"modal\">" +
        "<span>R$ </span><span class=\"price\">" + preco.toFixed(2) + "</span> Adicionar ao carrinho <i class=\"fas fa-shopping-cart\"></i>" +
        "</button>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

    document.body.appendChild(modal);
}

function atualizaPreco(idModal, preco){
    const quantidade = parseInt($("#modal-" + idModal).find('input').val());
    $("#modal-" + idModal + " .price").innerHTML = "R$ " + (preco * quantidade);
}

function criaCardModal(receita) {
    criaCard(getIdCategoria(receita.categoria), receita.id, receita.nome, receita.imagem)
    criaModal(receita.id, receita.nome, receita.porcoes, receita.ingredientes, receita.descricao, receita.linkVideo, receita.preco, receita.id)
}

function montarVideo(_this) {
    let modalTarget = $(_this).attr('data-target'),
        $modalTarget = $(modalTarget),
        $noScript = $modalTarget.find('noscript').eq(0),
        html = $noScript.html();

    $noScript.after($(html));

    $noScript.remove();

    document.getElementById("carrinho").style.display = 'none';
}

function pararVideo() {
    var videos = document.querySelectorAll('iframe, video');
	Array.prototype.forEach.call(videos, function (video) {
		if (video.tagName.toLowerCase() === 'video') {
			video.pause();
		} else {
			var src = video.src;
			video.src = src;
		}
	});
}

function mostrarCarrinho() {
    $(".carrinho-oculto").slideToggle(300);
    /*let carrinho = document.getElementById("carrinho");

    if (carrinho.style.display === "block") {
        carrinho.style.display = "none";
    } else {
        carrinho.style.display = "block";

        let htmlH = document.querySelector('html').offsetHeight,
            marginTopCarrinho = document.querySelector('header').offsetHeight + 2;

        carrinho.style.height = (htmlH - marginTopCarrinho) + 'px';

    }*/
}

var list = [];

//somando total
function getTotal(list) {
    var total = 0;
    for (var key in list) {
        total += parseFloat(list[key].valor) * list[key].quantidade;
    }
    document.getElementById("totalValue").innerHTML = formatValue(total);
}

//criando a tabela
function setList(list) {
    var table = '<thead><tr><td>Descrição</td><td>Quantidade</td><td>Valor</td><td>Ação</td></tr></thead><tbody>';
    for (var key in list) {
        table += '<tr class="text-center">' +
                    '<td class="text-left">' + list[key].descricao + '</td>' +
                    '<td>' + list[key].quantidade + '</td>' +
                    '<td>' + list[key].valor.toString().replace('.',',') + '</td><td>' +
                    '<button class="btn btn-danger" onclick="deleteData(' + key + ');"><i class="fas fa-eraser"></i> Delete</button>' +
                    '</td>' +
                '</tr>';
    }

    table += '</tbody>';

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

function adicionarReceita(amount, desc, price, receita_id) {
    
    if (price.length === 2) {
        price += ".00";
    }

    var value = price;

    let alterados = list.filter(function(obj){
        if(obj.receita_id == receita_id){
            obj.quantidade = parseInt(obj.quantidade) + parseInt(amount);
            return true;
        }
    }).length;

    if(alterados==0){
        list.push({
            "descricao": desc,
            "quantidade": amount,
            "valor": value,
            "receita_id": receita_id
        });
    }

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
function deleteList(force) {
    if(list.length === 0){
        return;
    } else if(force || confirm("Deseja deletar o pedido?")) {
        list = [];
        setList(list);
    }
}

//finalizando o Pedido
function finalizarPedido() {
    
    let lista = JSON.parse(localStorage.getItem('list'));
    
    if(lista.length===0){
        alert('A lista está vazia!');
        return;
    }
    
    let data = [];

    let valorTotal = 0;

    for(let i=0;i<lista.length;i++){
        data.push({
            receita_id: lista[i].receita_id,
            quantidade: lista[i].quantidade
        });
        valorTotal += lista[i].valor * lista[i].quantidade;
    }

    let jwt = localStorage.getItem('icheff-jwt');

    if(!jwt || jwt == ''){
        alert("Você não está logado no sistema!");
        window.location.href = BASE_URL + '/login.html';
        return;
    }

    $.ajax({
        type: 'POST',
        url: BASE_URL + '/api/vendas',
        contentType: "application/json",
        data: JSON.stringify(data),
        headers: { 'Authorization': 'Bearer ' + jwt },
        beforeSend: function(){
            $('div#carrinho-tabela').hide();
            $('div#container-valor-total').hide();
        },
        success: function (data, textStatus, jqXHR) {
            $('div#pagamento').show();
            let venda_id = jqXHR.getResponseHeader('venda_id');
            $('div#pagamento span#venda_id').html(venda_id);
            $('div#pagamento span#valor_compra').html(valorTotal);
            //deleteList(true); // comentado hoje
            $('#totalValue').html(formatValue(valorTotal));
            $('div#pagamento input').eq(0).focus();
        },
        error: function(jqXHR, textStatus, errorThrown){

            if(jqXHR.status == 403){
                window.location.href = BASE_URL + '/login.html';
                return;
            }

            $('div#carrinho-tabela').show();
            $('div#container-valor-total').show();
            alert("Erro ao cadastrar a venda");
            console.log(JSON.parse(jqXHR.responseText.status));
        }
    });

}

function pagar(){

    let data = {
        venda_id: $('div#pagamento span#venda_id').html(),
        cartao: {
            numero: $('div#pagamento input#cartao').val(),
            validade: $('div#pagamento input#validade').val(),
            cvv: $('div#pagamento input#cvv').val()
        }
    };

    if(data.cartao.numero.length===0){
        $('div#pagamento input#cartao').focus();
        return;
    }

    if(data.cartao.validade.length!==5){
        $('div#pagamento input#validade').focus();
        return;
    }
    
    if(data.cartao.cvv.length!==3){
        $('div#pagamento input#cvv').focus();
        return;
    }

    let jwt = localStorage.getItem('icheff-jwt');

    let $alertPagamento = $('#alert-pagamento');

    $.ajax({
        type: 'PUT',
        url: BASE_URL + '/api/pagamento',
        contentType: 'application/json',
        data: JSON.stringify(data),
        headers: { 'Authorization': 'Bearer ' + jwt },
        beforeSend: function(){
            $alertPagamento.html('').hide();
            $('div#container-valor-total').hide();
        },
        success: function (data, textStatus, jqXHR) {
            desistirDaCompra();
            mostrarCarrinho();
            enviarEmail();
            
        },
        error: function(jqXHR, textStatus, errorThrown){
            if(jqXHR.status == 401){
                $alertPagamento.html("Pagamento não aprovado!").show();
            } else {
                $alertPagamento.html("Erro ao solicitar o pagamento ao servidor!").show();
            }
        }
    });
    
}

function desistirDaCompra(){
    $('div#pagamento').hide();
    $('div#carrinho-tabela').show();
    $('div#container-valor-total').show();
    deleteList(true); // Adicionado hoje
    $('#totalValue').html(formatValue(0));
}

//salvando em storage
function saveListStorage(list) {
    var jsonStr = JSON.stringify(list);
    localStorage.setItem("list", jsonStr);
}

function enviarEmail(){

    let emailUser = localStorage.getItem('icheff-email-user');

    if(!emailUser || emailUser == ''){
        alert("Email do usuário não encontrado");
        return;
    } else {
        $.ajax({
            type: "POST",
            url: BASE_URL + "/api/email/send",
            data: emailUser,
            contentType: "application/json",
            success: function(response){
                alert("Obrigado pela compra! Verifique os detalhes do pedido no seu e-mail.");
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert("Compra finalizada");
            }
        });
    }

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