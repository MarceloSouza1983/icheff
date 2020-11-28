$(document).ready(function (e) {

	document.getElementById("divSuccess").classList.add("hide");
	$("form[ajax=true]").submit(function (e) {

		e.preventDefault();

		// Preparar dados
		var formData = {
			email: $("#email").val()
		}

		if (document.getElementById("email").value == "") {
			alert("Por favor preencha o campo email e tente novamente.")
		} else {

			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/api/newsletter",
				data: JSON.stringify(formData),
				success: function() {
					resetData();
					mostrarDiv();
				},
				error: (jqXHR, textStatus, errorThrown) => {
                    alert('Erro ao realizar a solicitação ao servidor:' + 'Código do Erro: ' + jqXHR.status + ' - ' + jqXHR.statusText);
                },
			});

		}

	})

})

function resetData() {
	$("#email").val("");
}

// Mostra a div de cadastramento com sucesso
function mostrarDiv() {
	
	var element = document.getElementById("divSuccess");
	element.classList.remove("hide");

	setTimeout(function () {
		document.getElementById("divSuccess").classList.add("hide");
	}, 2000);

}