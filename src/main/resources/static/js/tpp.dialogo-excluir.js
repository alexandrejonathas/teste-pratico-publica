var Tpp = Tpp || {}

Tpp.DialogoExcluir = (function(){

	DialogoExcluir = function(){
		this.btnExclusao = $(".js-btn-exclusao");
	}

	DialogoExcluir.prototype.iniciar = function(){
		this.btnExclusao.on("click", onExcluirClicado.bind(this));
	}

	function onExcluirClicado(e){
		e.preventDefault();
		var botaoClicado = $(e.currentTarget);
		
		var url = botaoClicado.data("url");
		var objeto =  botaoClicado.data("objeto");
		
		swal({
			title : "Tem certeza?",
			text : `Você não poderá recuperar o ${objeto} após a exclusão.`,
		    showCancelButton: true,
		    confirmButtonColor: "#DD6B55",
		    closeOnConfirm: false,
		    closeOnCancel: false, 
			buttons: ["Cancelar", "Sim, exclua!"],
			dangerMode: true,			
		}).then((result) => {
			if (result) {
				onExcluirConfirmado(url);
			} 
		});		
		
	}
	
	function onExcluirConfirmado(url){
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluidoSucesso.bind(this),
			error: function(error) {
				var error = JSON.parse(error.responseText);
				onExcluidoError(error);
			},
		});
	}
	
	function onExcluidoSucesso(){
		window.location.reload();
	}

	function onExcluidoError(error){
		swal({
			title : error.title,
			text : error.detail,
		    showCancelButton: false,
		    confirmButtonColor: "#DD6B55",
		    closeOnConfirm: false, 
		});	
	}

	return DialogoExcluir;

})();

$(function(){
	var dialogoExcluir = new Tpp.DialogoExcluir();
	dialogoExcluir.iniciar();
})
