var Tpp = Tpp || {}

Tpp.GraficoPontosPorMes = (function(){

	GraficoPontosPorMes = function(){
		this.ctx = $('#graficoPontosPorMes')[0].getContext('2d');
	}

	GraficoPontosPorMes.prototype.iniciar = function(){
		$.ajax({
			url: 'pontuacoes/total-pontos-mes',
			method: 'GET',
			success: onDadosRecebidos.bind(this),
			error: function(error){
				console.log(error);
			}
		});
	}

	function onDadosRecebidos(pontoMes){	
		var meses = [];
		var pontos = [];
		
		pontoMes.forEach(function(ponto){
			meses.unshift(ponto.mes);
			pontos.unshift(ponto.total);
		});
	
		var graficoPontosPorMes = new Chart(this.ctx, {
			type: 'line',
			data: {
				labels: meses,
				datasets: [{
					label: 'Pontos por Mês',
		            backgroundColor: 'rgb(255, 99, 132)',
		            borderColor: 'rgb(255, 99, 132)',
		            data: pontos					
				}]
			},
		});	
	}

	return GraficoPontosPorMes;

})();

$(function(){
	var graficoPontosPorMes = new Tpp.GraficoPontosPorMes();
	graficoPontosPorMes.iniciar();
})