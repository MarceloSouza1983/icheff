
function criaCard(idCategoria, idModal, titulo, texto, imagem, descricaoImagem) {
    var card = document.createElement("div");
    card.className = "col-12 col-md-6 col-lg-4";
    card.innerHTML =
        "<div class=\"card\">" +
        "<img src=\"" + imagem + "\" class=\"card-img-top\" alt=\"" + descricaoImagem + "\">" +
        "<div class=\"card-body\">" +
        "<h5 class=\"card-title\">" + titulo + "</h5>" +
        "<p class=\"card-text\">" + texto + "</p>" +
        "<button class=\"btn btn-cards\" data-toggle=\"modal\" data-target=\"#modal-" + idModal + "\">" +
        "Detalhes" +
        "</button>" +
        "</div>" +
        "</div>";
    document.getElementById(idCategoria).appendChild(card);
}

function criaLista(lista) {
    var ingredientes = "";

    lista.forEach(element => {
        ingredientes += '<li>' + element + '</li>'
    });

    return ingredientes;
}

function criaModal(idModal, titulo, listaIngredientes, preco) {

    var modal = document.createElement("div");
    modal.innerHTML = "<div class=\"modal fade\" id=\"modal-" + idModal + "\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"modalLabel\" aria-hidden=\"true\">" +
        "<div class=\"modal-dialog\">" +
        "<div class=\"modal-content\">" +
        "<div class=\"modal-header\">" +
        "<h3 class=\"modal-title\" id=\"modalLabel\">" + titulo + "</h3>" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
        "<span aria-hidden=\"true\">&times;</span>" +
        "</button>" +
        "</div>" +
        "<div class=\"modal-body\">" +
        "<div class=\"container\">" +
        "<div class=\"row modal-receitas-body\">" +
        "<h4>Lista de ingredientes <i class=\"fas fa-clipboard-list\"></i></h4>" +
        "<ul>" +
        criaLista(listaIngredientes) +
        "</ul>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "<div class=\"modal-footer\">" +
        "<div>" +
        "<button type=\"button\" class=\"btn btn-secondary btn-circle\">" +
        "-" +
        "</button>" +
        "<span class=\"contador-carrinho\"> 1 </span>" +
        "<button type=\"button\" class=\"btn btn-secondary btn-circle\">" +
        "+" +
        "</button>" +
        "</div>" +
        "<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">" +
        "<span class=\"price\"> R$ " + preco + "</span> Adicionar ao carrinho <i class=\"fas fa-shopping-cart\"></i>" +
        "</button>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

    document.body.appendChild(modal);
}

function criaCardModal(idCategoria, idModal, titulo, texto, imagem, descricaoImagem, listaIngredientes, preco) {
    criaCard(idCategoria, idModal, titulo, texto, imagem, descricaoImagem)
    criaModal(idModal, titulo, listaIngredientes, preco)
}

const lista = [
    '1 cebola pequena',
    '3 dentes de alho triturados ou picados;',
    '500g de carne moída (patinho);',
    '1 col (chá) orégano seco;',
    '1 col (sobremesa) sal;',
    'pimenta-do-reino a gosto;',
    '1 sachê de molho pronto;',
    'Massa para lasanha;',
    '350g de presunto;',
    '350g de queijo Muçarela;',
    'Queijo parmesão a gosto.',
];

const lista2 = [];

criaCardModal("cards-variados", 1, "Costela Barbecue", "Receita de Costela Barbecue", "img/barbecue.jpg", "Costela Barbecue", lista, 50)
criaCardModal("cards-variados", 2, "Pizza Marguerita", "Receita de Pizza Marguerita", "img/pizza.jpg", "Pizza Marguerita", lista2, 50)
criaCardModal("cards-variados", 3, "Hamburguer", "Receita de Hamburguer", "img/hamburguer.jpg", "Hamburguer", lista2, 50)
criaCardModal("cards-peixes", 4, "Costela Barbecue", "Receita de Costela Barbecue", "img/barbecue.jpg", "Costela Barbecue", lista2, 38)
criaCardModal("cards-fitness", 5, "Hamburguer", "Receita de Hamburguer", "img/hamburguer.jpg", "Hamburguer", lista2, 40)
criaCardModal("cards-vegetarianos", 6, "Lasanha Bolonhesa", "Receita de Lasanha Bolonhesa", "img/lasanha.jpg", "Lasanha Bolonhesa", lista2, 45)
criaCardModal("cards-variados", 7, "Lasanha Bolonhesa", "Receita de Lasanha Bolonhesa", "img/lasanha.jpg", "Lasanha Bolonhesa", lista2, 45)