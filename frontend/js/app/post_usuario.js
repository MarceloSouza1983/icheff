const BASE_URL = window.location.protocol + '//' + window.location.hostname + (window.location.port != '' ? ':' + window.location.port : '');

$(document).ready(function () {

    document.getElementById("divSuccess").classList.add("hide");

    $("#userForm").submit(function (event) {

        event.preventDefault();

        const tipo = 0;

        let formData = {
            bairro: $("#usu_bairro").val(),
            celular: $("#usu_celular").val(),
            cep: $("#usu_cep").val(),
            cidade: $("#usu_cidade").val(),
            complemento: $("#usu_complemento").val(),
            cpf: $("#usu_cpf").val(),
            dataNascimento: $("#usu_data_nascimento").val(),
            estado: $("#usu_estado").val(),
            login: $("#usu_login").val(),
            logradouro: $("#usu_logradouro").val(),
            nome: $("#usu_nome").val(),
            numero: $("#usu_numero").val(),
            rg: $("#usu_rg").val(),
            senha: $("#usu_senha").val(),
            telefone: $("#usu_telefone").val(),
            tipo: tipo
        }

        let data1 = document.getElementById("usu_nome").value;
        let data2 = document.getElementById("usu_cpf").value;
        let data3 = document.getElementById("usu_login").value;
        let data4 = document.getElementById("usu_rg").value;
        let data5 = document.getElementById("usu_senha").value;
        let data6 = document.getElementById("usu_data_nascimento").value;

        if (data1.length == 0 || data2.length == 0 || data3.length == 0 || data4.length == 0 || data5.length < 5 || data6.length < 10) {
            alert("Por favor preencha todos os campos e tente novamente.");
        } else {

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: BASE_URL + '/api/usuarios/save',
                data: JSON.stringify(formData),
                success: function (response) {
                    reset();
                    mostrarDiv();
                    setTimeout(() => {
                        window.location.href = BASE_URL + '/login.html';
                    }, 2500);
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("Usuário ou senha incorreta");
                    console.log(JSON.parse(jqXHR.responseText));
                }
            });


        }

    });

});

function reset() {
    $("#usu_bairro").val("");
    $("#usu_celular").val("");
    $("#usu_cep").val("");
    $("#usu_cidade").val("");
    $("#usu_complemento").val("");
    $("#usu_cpf").val("");
    $("#usu_data_nascimento").val("");
    $("#email").val("");
    $("#usu_estado").val("");
    $("#usu_login").val("");
    $("#usu_logradouro").val("");
    $("#usu_nome").val("");
    $("#usu_numero").val("");
    $("#usu_rg").val("");
    $("#senha").val("");
    $("#usu_senha").val("");
    $("#usu_telefone").val("");
}

// Mostra a div de cadastramento com sucesso
function mostrarDiv() {

    var element = document.getElementById("divSuccess");
    element.classList.remove("hide");

    setTimeout(function () {
        document.getElementById("divSuccess").classList.add("hide");
    }, 2000);

}