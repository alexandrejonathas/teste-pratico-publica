var Tpp = Tpp || {}

Tpp.MaskDate = (function(){
	
	MaskDate = function(){
		this.inputDate = $('.js-mask-date');
	}
	
	MaskDate.prototype.iniciar = function() {
		this.inputDate.mask('00/00/0000');
	}
	
	return MaskDate;

})();

$(function(){

	var maskDate = new Tpp.MaskDate();
	maskDate.iniciar();

});