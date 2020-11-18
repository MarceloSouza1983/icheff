$(document).ready(function() {

    document.getElementById("divSuccess").classList.add("hide");

	$("#userForm").submit(function(event) {

    event.preventDefault();
    
    var dataAtual = dateTimeNow();

    let formData = {
        bairro :  $("#usu_bairro").val(),
        celular :  $("#usu_celular").val(),
        cep :  $("#usu_cep").val(),
        cidade :  $("#usu_cidade").val(),
        complemento :  $("#usu_complemento").val(),
        cpf :  $("#usu_cpf").val(),
        dataCadastro : dataAtual,
        dataNascimento : $("#usu_data_nascimento").val(),
        estado :  $("#usu_estado").val(),
        login : $("#usu_login").val(),
        logradouro :  $("#usu_logradouro").val(),
        nome : $("#usu_nome").val(),
        numero : $("#usu_numero").val(),
        rg : $("#usu_rg").val(),
        senha : $("#usu_senha").val(),
        telefone : $("#usu_telefone").val()
    }

    let data1 = document.getElementById("usu_nome").value;
    let data2 = document.getElementById("usu_cpf").value;
    let data3 = document.getElementById("usu_login").value;
    let data4 = document.getElementById("usu_rg").value;
    let data5 = document.getElementById("usu_senha").value;
    let data6 = document.getElementById("usu_data_nascimento").value;

    if (data1.length == 0 || data2.length == 0 || data3.length == 0 || data4.length == 0 || data5.length < 5 || data6.length < 10) {
        alert("Por favor preencha todos os campos e tente novamente.")
    } else {
        
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "api/usuarios/save",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (response) {
            
        }
        
    });

    reset();
    mostrarDiv();

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

function dateTimeNow() {

    let currentDate = new Date();
    let currentDateBrazil = new Date(currentDate.valueOf() - currentDate.getTimezoneOffset() * 60000);

    return currentDateBrazil.toISOString();

}

// Mostra a div de cadastramento com sucesso
function mostrarDiv() {
	
	var element = document.getElementById("divSuccess");
	element.classList.remove("hide");

	setTimeout(function () {
		document.getElementById("divSuccess").classList.add("hide");
	}, 2000);

}

/*
    
/*

function CadastrarUsuario() {
    var parametros = {
        cpf : $('#usu_cpf').val(),
        login : $('usu_login').val(),
        nome : $('#usu_nome').val(),
        rg : $('#usu_rg').val(),
        senha : $('#usu_senha').val()
    };

    var location = window.location.url;

    alert(parametros + "\n" + location);

    var variavel = JSON.parse(parametros);

    alert("\n" + variavel);

    /*http://localhost:8080/usuarios/save?CPF=30800413806&Login=map_souza%40yahoo.com&Nome=Roberto&RG=338576769&Senha=123456789

   

    $.ajax({
        url : "/usuarios/save",
        datatype : "json",
        contentType : "application/json; charset=utf-8",
        type : "POST",
        data : JSON.stringify(parametros),
        success : function(data) {
            console.log(data);
        },
        error : function(error) {
        }
    });
}


/*$(document).ready(function () {
    $("btnCadastrar").submit(function (e) {
        e.preventDefault();

        var params = {
            usu_cpf: $("#usu_cpf").val(), usu_login: $("#usu_login").val(), usu_nome: $("#usu_nome").val(),
            usu_rg: $("#usu_rg").val(), usu_senha: $("#usu_senha").val()
        };

        var data = jQuery.param(params);   // arbitrary variable name

        $.ajax({
            type: "POST",
            url: "usuarios/save/",
            data: data,
        }).success(function (response) {

        })
        console.log(data);
    })
}) */

    /*
    $(document).ready(function () {
        $("form[ajax=true]").submit(function (e) {
            e.preventDefault();
    
            var dataAtual = DateTimeNow();
            console.log(dataAtual);
    
            // PREPARE FORM DATA
            var formData = {
                usu_cpf: $("#usu_cpf").val(),
                usu_login: $("#usu_login").val(),
                usu_nome: $("#usu_nome").val(),
                usu_rg: $("#usu_rg").val(),
                usu_senha: $("#usu_senha").val()
                
            }
    
            // usu_data_nascimento: $("#usu_data_nascimento").val(),
    
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "usuarios/save",
                data: JSON.stringify(formData),
                dataType: "json",
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
                    $("#response").css({ "display": "block" });
                    alert("Dados cadastrados")
    
                },
                error: function (response) {
                    /*let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong>' + response.message + '</strong>' + ' ,Error: ' + message.error +
                        '</div>'
                    $("#response").append(errorAlert);
                    $("#response").css({ "display": "block" });
                    alert("Dados não foram inseridos") 
    
                }
            });
    
            resetData();
    
        })
    
    }) */