var Ambientes = {
		init:function(){

			var me = Ambientes;
			me.buildComboUnidades();
			me.renderAmbientes({});

		},
		
		mostrarHorariosDiponibles:function(idAmbiente, descripcionInicialUnidad){
			console.log('ambiente seleccionado='+idAmbiente);
			$('.horario').html('<div class ="loading-ambiente"><img src = "resources/img/loading.gif" /></div>');
			$.ajax({
				method : "GET",
				url : "json/ambientes/horario.json",
				data : {idAmbiente:idAmbiente},
				dataType : 'json'
			}).done(function(response) {
				console.log(response);
				$('.horario').empty();
				$('.horario').append('<h2> '+descripcionInicialUnidad+' / Hora: '+response.horaActual+' </h2>');
				$('.horario').append('<h3>Disponibilidad</h3>');
				if(response.listaDisponibilidad){
					response.listaDisponibilidad.forEach(function(item){
						$('.horario').append('<h4>'+item+'.</h4>');
					});
				}
				
				$('.horario').show();
			});
			
			
		},
		agregarEventos: function(){
			
			var me = Ambientes;
			
			//Evento al seleccionar un ambiente	
        	$('.item-ambiente').click(function(){
    			var selected = $(this);
    			$('.item-ambiente').removeClass('item-ambiente-selected');
    			selected.addClass('item-ambiente-selected');
    			me.mostrarHorariosDiponibles(selected.data('idambiente'), selected.data('descinfranombre'));
    		});

        	//Busqueda de ambientes
    		$('#txtBusqueda').keyup(function(){
    			$('.horario').hide();
    			var b = $(this);
    			var arr = [];

    			if(b.val().length > 1 ){
    				$('.item-ambiente').each(function(){
    				var item = $(this);
    					if(item.html().toLowerCase().indexOf(b.val().toLowerCase()) >= 0){
//    							arr.push('#'+item.data('infraestructura'));
    							item.show();
    					}else{
    						item.hide();
    					}
    				});
    				//$('.header-item-ambiente').hide();
//    				arr.forEach(function(headerId){
//    					$(headerId).show();
//    				});
    			}else{
    				$('.item-ambiente').show();
    				//$('.header-item-ambiente').show();
    			}
    		});
			
		},
		
		cadenaIsInArray:function(value,arr){
			
			if(arr && value){
				var isPresent = false;
				arr.forEach(function(item){
					if(value == item){
						isPresent = true;
						return;
					}
				});
				
				return isPresent;
			}else{
				return false;
			}
			
			
		},
		
		getListFlonKey:function(key,arr){
			var list = [];
			if(key && arr){
				arr.forEach(function(item){
					if(item.unidad.idUnidad == key){
						list.push(item);
					}
				});
				return list;
			}else{
				return [];
			}
		},
		
		buildComboUnidades:function(){
			var me = Ambientes;
			$.ajax({
				method : "GET",
				url : "json/ambientes/unidades.json",
				data : {},
				dataType : 'json'
			}).done(function(response) {
				if(response.data){
					response.data.forEach(function(unidad){
						$('#tipoUnidad').append('<option value = "'+unidad.idUnidad+'">'+unidad.nombre+'</option>');
					});
				}
				
				$('#tipoUnidad').change(function(){
					$('.horario').hide();
					var params = {};
					if($('#tipoUnidad').val() != '0'){
						params.idUnidad = $('#tipoUnidad').val();
					}
					
					$('.comtent-items-ambiente').html('<div class ="loading-ambiente"><img src = "resources/img/loading.gif" /></div>');
					
					me.renderAmbientes(params);
				});
				
			});
			
		},
		
		renderAmbientes:function(params){
			
			var me = Ambientes;
			
			params = params || {};
			
			$.ajax({
				method : "GET",
				url : "json/ambientes/data.json",
				data : params,
				dataType : 'json'
			}).done(function(response) {
				
				var arr = [];
				
				
				
				if(response.data){
					var data = response.data;
//					data.forEach(function(ambiente){
//						if(!me.cadenaIsInArray(ambiente.unidad.idUnidad,arr)){
//							arr.push(ambiente.unidad.idUnidad);
//						}
//					});
//					
//					var mapData = {};
//					
//					arr.forEach(function(key){
//						mapData[key] = me.getListFlonKey(key,data);
//					});
					
					$('.comtent-items-ambiente').empty();
//					var arrTemp = null;
//					var valorInicialAmbiente=0;
//					var descripcionInicialUnidad='';
//					Object.keys(mapData).forEach(function(key) {
//						
//						arrTemp = mapData[key];
//						if(arrTemp){
//
//							arrTemp.forEach(function(item){
//								valorInicialAmbiente++;
//								if(valorInicialAmbiente==1){
//									valorInicialAmbiente=item.idAmbiente;
//									descripcionInicialUnidad=arrTemp[0].unidad.nombre;
//									
//									$('.comtent-items-ambiente').append('<div class="item-ambiente item-ambiente-selected" data-idambiente="'+item.idAmbiente+'" data-descInfraNombre="'+mapData[key][0].unidad.nombre+'" data-infraestructura="'+'padre-'+key+'">'+
//											''+item.nombre+
//											(item.piso && item.piso.nombre?'<br><small>'+item.piso.nombre+'</small>':'')+
//										 '</div>');
//								}else{
//									$('.comtent-items-ambiente').append('<div class="item-ambiente" data-idambiente="'+item.idAmbiente+'" data-descInfraNombre="'+mapData[key][0].unidad.nombre+'" data-infraestructura="'+'padre-'+key+'">'+
//											''+item.nombre+
//											(item.piso && item.piso.nombre?'<br><small>'+item.piso.nombre+'</small>':'')+
//										 '</div>');
//								}
//						
//							});
//						}
//					});
					
					
					data.forEach(function(item){
						$('.comtent-items-ambiente').append('<div class="item-ambiente" data-idambiente="'+item.idAmbiente+'" data-descInfraNombre="" data-infraestructura="">'+
								''+item.nombre+
								(item.piso && item.piso.nombre?'<br><small>'+item.piso.nombre+'</small>':'')+
							 '</div>');
					});
					
					
					
					me.agregarEventos();
					me.mostrarHorariosDiponibles(valorInicialAmbiente, descripcionInicialUnidad);
				}else{
					$('.comtent-items-ambiente').html('No hay Ambientes disponibles en este momento.');
				}
				
			});
			
		}
}; 





