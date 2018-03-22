var Ambientes = {
		init:function(){

			var me = Ambientes;
			me.buildComboUnidades();
			me.renderAmbientes({});

		},
		
		agregarEventos: function(){
			
			//Evento al seleccionar un ambiente	
        	$('.item-ambiente').click(function(){
        		$('.horario').show();
    			var selected = $(this);
    			$('.item-ambiente').removeClass('item-ambiente-selected');
    			selected.addClass('item-ambiente-selected');
    			console.log('seleccionar')
    		});

        	//Busqueda de ambientes
    		$('#txtBusqueda').keyup(function(){
    			var b = $(this);
    			var arr = [];
    			if(b.val().length > 2 ){
    				$('.item-ambiente').each(function(){
    				var item = $(this);
    					if(item.html().toLowerCase().indexOf(b.val().toLowerCase()) >= 0){
    							arr.push('#'+item.data('infraestructura'));
    							item.show();
    					}else{
    						item.hide();
    					}
    				});
    				$('.header-item-ambiente').hide();
    				arr.forEach(function(headerId){
    					$(headerId).show();
    				});
    			}else{
    				$('.item-ambiente').show();
    				$('.header-item-ambiente').show();
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
					data.forEach(function(ambiente){
						if(!me.cadenaIsInArray(ambiente.unidad.idUnidad,arr)){
							arr.push(ambiente.unidad.idUnidad);
						}
					});
					
					var mapData = {};
					
					arr.forEach(function(key){
						mapData[key] = me.getListFlonKey(key,data);
					});
					
					$('.comtent-items-ambiente').empty();
					var arrTemp = null;
					Object.keys(mapData).forEach(function(key) {

						arrTemp = mapData[key];
						if(arrTemp){
							$('.comtent-items-ambiente').append('<div class="header-item-ambiente" id="'+mapData[key][0].unidad.nombre+'-'+key+'">'+mapData[key][0].unidad.nombre+'-'+key+'</div>');
							arrTemp.forEach(function(item){
								$('.comtent-items-ambiente').append('<div class="item-ambiente" data-infraestructura="'+mapData[key][0].unidad.nombre+'-'+key+'">'+
																		''+item.nombre+
																		(item.piso && item.piso.nombre?'<br><small>Primer piso</small>':'')+
																	 '</div>');
							});
						}
						

					});
					
					me.agregarEventos();
					
				}
				
			});
			
		}
}; 





