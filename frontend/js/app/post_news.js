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
				url: "newsletter",
				data: JSON.stringify(formData),
				dataType: 'json',
				success: function() {

				}
			});

			resetData();
			mostrarDiv();
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
	}, 1500);

}