function getRomanos(num){
	if(num == 1){
		return "I";
	}
	if(num == 2){
		return "II";
	}
	if(num == 3){
		return "III";
	}
	if(num == 4){
		return "IV";
	}
	if(num == 5){
		return "V";
	}
	if(num == 6){
		return "VI";
	}
	if(num == 7){
		return "VII";
	}
	if(num == 8){
		return "VIII";
	}
	if(num == 9){
		return "IX";
	}
	if(num == 10){
		return "X";
	}
	if(num == 11){
		return "XI";
	}
	if(num == 12){
		return "XII";
	}
}

//Corta la sigla de la malla
function cortaSiglaDeLinkMalla(sigla){
	tam = sigla.length;
	cadenaNueva="";
	if(tam > 5){
		arrayCadena = sigla.split("");
		for(var j=0 ; j<arrayCadena.length ; j++){
			if(j==4){
				cadenaNueva = cadenaNueva + "<br>";
			}
			cadenaNueva = cadenaNueva + arrayCadena[j];
		}
		return cadenaNueva;
	}else{
		return sigla;
	}
}

//Obtiene el nombre de la clase de acuerdo al estado del curso
function defineNombreClase(codigoEstado){
	if( codigoEstado == 1 || codigoEstado == 8){
		return "mallaAsignaturaNoDictaraNoRequisitos";
	}else if( codigoEstado == 2 ){
		return "mallaAsignaturaNoSeccionesDisponibles";
	}else if( codigoEstado == 3){
		return "mallaAsignaturaPuedeLlevar";
	}else if( codigoEstado == 4 ){
		return "mallaAsignaturaDesaprobada";
	}else if( codigoEstado == 5 ){
		return "mallaAsignaturaAprobadaConvalidada";
	}else if( codigoEstado == 6 ){
		return "mallaAsignaturaExonerada";
	}else if( codigoEstado == 7 ){
		return "mallaAsignaturaAsignado";
	}else if( codigoEstado == 9 ){
		return "mallaAsignaturaNoDictaraDesaprobada";
	}
}

$(function() {
		
    var loadingIndicator = $('#loading-indicator'),
            errorIndicator = $('#error-indicator'),
            sessionIndicator = $('#session-indicator');

    $.ajaxSetup({
        type: "POST",
        beforeSend: function(jqXHR) {
            $.xhrPool.push(jqXHR);
        }
    });

    $.xhrPool = [];

    $.xhrPool.abortAll = function() {
        $(this).each(function(idx, jqXHR) {
            jqXHR.abort();
        });
        $.xhrPool.length = 0
    };

    $(document).ajaxStart(function() {
        loadingIndicator.fadeIn('fast');
    });

    $(document).ajaxSuccess(function() {
        errorIndicator.fadeOut('fast');
    });

    $(document).ajaxComplete(function(event, jqXHR, ajaxOptions) {
        var index = $.xhrPool.indexOf(jqXHR);

        if (index > -1) {
            $.xhrPool.splice(index, 1);
        }

        var ct = jqXHR.getResponseHeader('content-type') || '';

        if (ct.indexOf('html') > -1 && jqXHR.status === 200) {
            window.location.href = '../login.html?continue=' + window.location.hash.split('#')[1];
            sessionIndicator.fadeIn('fast').delay(10000).fadeOut('fast');
        }

        if ($.xhrPool.length === 0) {
            loadingIndicator.fadeOut('fast');
        }
    });

    $(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
        loadingIndicator.fadeOut('fast');
        if (thrownError !== 'abort') {
            errorIndicator.fadeIn('fast').delay(4000).fadeOut('fast');
        }
        if (jqXHR.status >= 400) {
            app.notFound();
        }
    });	
});