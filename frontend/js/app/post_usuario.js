$(document).ready(function () {
    $("#add_new_customer").submit(function (evt) {
        evt.preventDefault();

        // PREPARE FORM DATA
        let formData = {
            usu_nome: $("#usu_nome").value,
            usu_data_nascimento: $("#usu_data_nascimento").value,
            usu_data_cadastro: $("#usu_data_cadastro").value,
            usu_cpf: $("#usu_cpf").value,
            usu_rg: $("#usu_rg").value,
            usu_tipo: $("#usu_tipo").value,
            usu_login: $("#usu_login").value,
            usu_senha: $("#usu_senha").value
        }

        $.ajax({
            url: '/usuarios/create',
            type: 'POST',
            contentType: "application/json",
            data: JSON.stringify(formData),
            dataType: 'json',
            async: false,
            cache: false,
            success: function (response) {
                /*let usuario = response.customers[0];
                let usuarioString = "{ nome: " + usuario.usu_nome +
                    ", CPF: " + usuario.usu_cpf +
                    ", RG: " + usuario.usu_rg +
                    ", Data nascimento: " + usuario.usu_data_nascimento +
                    ", Tipo: " + usuario.usu_tipo + " }"
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>' + response.message + '</strong> Customer\'s Info = ' + usuarioString;
                '</div>'
                $("#response").append(successAlert);
                $("#response").css({ "display": "block" });*/
                alert("Dados cadastrados")

            },
            error: function (response) {
                /*let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>' + response.message + '</strong>' + ' ,Error: ' + message.error +
                    '</div>'
                $("#response").append(errorAlert);
                $("#response").css({ "display": "block" });*/
                alert("Dados não foram inseridos")

            }
        });

    });

    (function () {
        let pathname = window.location.pathname;
        if (pathname === "/") {
            $(".nav .nav-item a:first").addClass("active");
        } else if (pathname == "../frontend/cadastro.html") {
            $(".nav .nav-item a:last").addClass("active");
        }
    })
})