var $navigation = $('#siga-sidenav'),
        $mainStack = $('#siga-main'),
        $sideStack = $('#siga-sidebar'),
        $mainApp = $('#main-app'),
        $pageTitle = $('#page-title'),
        $scrollableContainer = $('.scrollable-container'),
        last_template = '',
        currentPlan = '';

/*=============== Main helpers ===============*/

if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function(elt /*, from*/) {
                var len = this.length >>> 0;

                var from = Number(arguments[1]) || 0;
                from = (from < 0)
                        ? Math.ceil(from)
                        : Math.floor(from);

                if (from < 0) from += len;

                for (; from < len; from++) {
                        if (from in this && this[from] === elt)
                                return from;
                }

                return -1;
        };
}

// Production steps of ECMA-262, Edition 5, 15.4.4.18
if ( !Array.prototype.forEach ) {
        Array.prototype.forEach = function forEach( callback, thisArg ) {
                var T, k;

                if ( this === null ) {
                        throw new TypeError( "this is null or not defined" );
                }

                var O = Object(this);

                var len = O.length >>> 0;

                if ( {}.toString.call(callback) !== "[object Function]" ) {
                        throw new TypeError( callback + " is not a function" );
                }

                if ( thisArg ) {
                        T = thisArg;
                }

                k = 0;

                while( k < len ) {
                        var kValue;

                        if ( Object.prototype.hasOwnProperty.call(O, k) ) {
                                kValue = O[ k ];
                                callback.call( T, kValue, k, O );
                        }

                        k++;
                }
        };
}

/*=============== Sammy.js ===============*/

var app = $.sammy('#main-app', function() {

        // incluir plugin Sammy.Template
        this.use(Sammy.Template, 'tpl');

        var ua = navigator.userAgent.toLowerCase(),
                isBlackberry = /blackberry/i.test(ua),
                isAndroid = /android/i.test(ua),
                isGingerbread = /android 2\.3/i.test(ua),
                isHoneycombOrNewer = /android [3-9]/i.test(ua),
                isFroyoOrOlder = isAndroid && !isGingerbread && !isHoneycombOrNewer;

        if ( isBlackberry || isGingerbread || isFroyoOrOlder ) {
                this.disable_push_state = true;

                $(' html' ).addClass( 'simple-layout' );
        }

        /*=============== Helpers ===============*/
        this.helpers({
                changeNavigation: function( route ) {
                        if ( route === undefined ) {
                                $navigation.find( 'li' ).removeClass('active');
                                return;
                        }

                        $navigation
                                .find( 'li' ).removeClass( 'active' )
                                .find( 'a[href="#/' + route + '"]' )
                                .parent()
                                .addClass('active');
                },
                scrollToElement: function( element ) {
                        if ( element === undefined ) {
                                element = $( 'html, body' );
                        }

                        $scrollableContainer.animate({
                                scrollTop: element.offset().top
                        }, 200);
                },
                createInfoMessage: function( content ) {
                        return '<p class="main-alert alert alert-info"><a class="close" data-dismiss="alert" href="#">&times;</a>' + content + '</p>';
                },
                parseGeneralInfo: function( data ) {
                        var $elements = $();                        
                        $elements = $elements.add('<p class="main-alert alert alert-info"><a class="close" data-dismiss="alert" href="#">&times;</a>' + data + '</p>');
                        $mainApp.find( '.main-alert' ).remove();
                        $mainApp.prepend( $elements );
                },        
                parseGeneralErrors: function( data ) {
                        var $elements = $();
                        for ( var i = 0; i < data.length; i++ ) {
                                $elements = $elements.add('<p class="main-alert alert alert-error"><a class="close" data-dismiss="alert" href="#">&times;</a>' + data[i] + '</p>');
                        }
                        $mainApp.find( '.main-alert' ).remove();
                        $mainApp.prepend( $elements );
                        this.scrollToElement();
                },
                parseGeneralWarnings: function( data ) {
                        var elements = $();
                        for ( var i = 0; i < data.length; i++ ) {
                                elements = elements.add('<p class="main-alert alert"><a class="close" data-dismiss="alert" href="#">&times;</a>' + data[i] + '</p>');
                        }
                        $mainApp.find('.main-alert').remove();
                        elements.prependTo($mainApp);
                },
                renderTemplate: function( options ) {
                        var settings = $.extend( {
                                        template: '',
                                        url: null,
                                        data: null
                                }, options );

                        if ( typeof settings.template !== 'string' || settings.template === '' ) {
                                return false;
                        }

                        var context = this,
                                ajaxOptions = {
                                        type: 'post',
                                        data: settings.data
                                };

                        // agregar la ruta correcta a la plantilla
                        var templateUrl = 'templates/' + settings.template + '.tpl';

                        if ( settings.url === null || settings.url === '' ) {
                                return this
                                        .load( templateUrl, { type : 'get' } )
                                        .interpolate( settings.data, templateUrl );
                        }

                        delete context.contentData;

                        return this
                                .load( settings.url, ajaxOptions )
                                .then( function( response ) {
                                        context.contentData = response;
                                })
                                .load( templateUrl, { type : 'get' } )
                                .interpolate( context.contentData, templateUrl )
                                .then( function() {
                                        delete context.contentData;
                                });
                },
                renderModal: function( template, url, data ) {
                        this.renderTemplate({
                                template: template,
                                url: url,
                                data: data
                        })
                                .appendTo( $mainApp )
                                .then( function() {
                                        $( '#siga-modal').modal().on( 'hidden', function() {
                                                $(this).remove();
                                        });
                                });
                },
                callBaseTemplate: function( options ) {
                        var settings = $.extend( {
                                        template: '',
                                        url: null,
                                        data: null,
                                        menu: options.template,
                                        title: null
                                }, options );

                        if ( typeof settings.template !== 'string' || settings.template === '' ) {
                                return false;
                        }

                        // indicar cual fue la ultima plantilla cargada
                        last_template = settings.template;

                        // agregar la ruta correcta a la plantilla
                        var templateUrl = 'templates/' + settings.template + '.tpl';

                        var context = this;

                        var ajaxOptions = {
                                type: 'post',
                                data: settings.data
                        };

                        //delete context.contentData;

                        // seleccionar item del menu
                        this.changeNavigation( settings.menu );

                        // si no hay una URL definida entonces no se estÃ¡n 
                        // enviando datos para cargar junto con la plantilla
                        if ( settings.url === null || settings.url === '' ) {
                                $pageTitle.text( settings.title );
                                $mainApp.removeClass().addClass( settings.template );

                                return this.load( templateUrl, { type: 'get' } )
                                        .interpolate( null, templateUrl )
                                        .swap()
                                        .then( function() {
                                                $( document ).trigger( 'template-loaded', { template: settings.template } );
                                        });
                        }

                        // renderizar y lanzar eventos
                        return this.load( settings.url, ajaxOptions )
                                .then( function( response ) {
                                        context.contentData = response;
                                        $pageTitle.text( response.title ? response.title : settings.title );
                                        $mainApp.removeClass().addClass( settings.template );
                                })
                                .load( templateUrl, { type: 'get' } )
                                .interpolate( context.contentData, templateUrl )
                                .swap()
                                .then( function() {
                                        context.$element().removeClass( 'hidden' );
                                        $( document ).trigger( 'template-loaded', { template: settings.template } );
                                });
                },
                getPlanMenu: function( nextRoute ) {
                        if ( typeof nextRoute !== 'string' ) {
                                nextRoute = document.location.hash;
                        }

                        var options = {
                                template: 'planes',
                                url: 'json/asignatura/estudioVigenteList.json'
                        };

                        return this
                                .renderTemplate(options)
                                .prependTo( app.$element() )
                                .then( function() {
                                        $( '.plan-filter a' ).on( 'click', function( e ) {
                                                currentPlan = $( this ).data( 'plan' );
                                                app.runRoute( 'get', nextRoute );
                                                e.preventDefault();
                                        });
                                });
                },
                verifyPlanActivo: function(idCurrentPlan ) {
                        var jqxhr1 = $.post( 'json/asignatura/estudioVigenteList.json', function( data ) {
                            var count = 0;
                            data.estudios.forEach( function( item ) {
                                   if ( item.id === idCurrentPlan ) {
                                       count = 1;
                                       return;
                                   } 
                            });
                            if(count === 0){
                                if ( data.estudios && data.estudios.length > 0 ) {
                                    currentPlan = data.estudios[0].id;
                                }
                            }
                        });
                        return jqxhr1;
                },
                getEstudioAllMenu: function( nextRoute ) {
                        if ( typeof nextRoute !== 'string' ) {
                                nextRoute = document.location.hash;
                        }

                        var options = {
                                template: 'planes',
                                url: 'json/asignatura/estudioAllList.json'
                        };

                        return this
                                .renderTemplate(options)
                                .prependTo( app.$element() )
                                .then( function() {
                                        $( '.plan-filter a' ).on( 'click', function( e ) {
                                                var pa = $(this).data('pa');
                                                currentPlan = $( this ).data( 'plan' );
                                                if(pa === null){
                                                    if(nextRoute === '#/perfil/academico/actual' ||
                                                        nextRoute === '#/perfil/academico/solicitudes'){
                                                        nextRoute = '#/perfil/academico';
                                                        app.setLocation(nextRoute);
                                                    }else{
                                                        app.runRoute( 'get', nextRoute );
                                                        e.preventDefault();    
                                                    }
                                                }else{
                                                    app.runRoute( 'get', nextRoute );
                                                    e.preventDefault();
                                                }
                                        });
                                });
                },
                countUnreadMessages: function( element ) {
                        this.load( 'json/mensaje/nuevos.json', { type: 'post', cache: false }, function( content ) {
                                var count = content.cantidadMensajes;
                                element.text( count === 0 || count === '' ? '' : count );
                                
                                var $notificationMessage = $('.notification-message');
                                $notificationMessage.text( count === 0 || count === '' ? '' : count);    
                        });
                }
        });

        this.notFound = function() {
                this.swap('');
                $pageTitle.text( 'Error 404' );
                $( '<p class="alert alert-error">No se encontr&oacute; la p&aacute;gina o no tienes acceso.</p>' )
                        .appendTo( this.$element() );
                $mainStack.removeClass( 'slided' );
        };

        this.before( { only: { verb: 'get' } }, function() {
                $.xhrPool.abortAll();
                $mainStack.removeClass( 'slided' );
                $sideStack.removeClass( 'slided' );
        });

        this.before( { except: { path: /\#\/cursos\/([^\/]+)\/([^\/]+)\/([^\/]+)/ } }, function() {
                ga( 'send', 'pageview', window.location.pathname + window.location.search + window.location.hash );
        });

        this.before( /\#\/cursos\/([^\/]+)\/([^\/]+)\/([^\/]+)/, function() {
                ga( 'send', 'pageview', window.location.pathname + window.location.search + '#/cursos' );
        });

        this.before( /\#\/cursos\/([^\/]+)\/([^\/]+)\/([^\/]+)\/([^\/]+)/, function() {
                ga( 'send', 'pageview', window.location.pathname + window.location.search + '#/cursos' );
        });

        this.before( { only: { path: ['#/avisos', '#/cursos', '#horario'] } }, function( context ) {
                this.load( 'json/aviso/destacado.json', { type: 'post', cache: false }, function( response ) {
                        $(document).one( 'template-loaded', $.proxy( function() {
                            if(response.enlace.length > 0 && response.contenido.length > 0){
                                var content = '<i class="icon-warning-sign icon-large"></i> ' + response.contenido;
                                if(response.enlace.length > 0){
                                    content += ' <a href="' + response.enlace + '" target="_blank">Ver m&aacute;s <i class="icon-share-alt"></i></a>';
                                }
                                $mainApp.prepend( context.createInfoMessage( content ) );
                                context.scrollToElement();
                            }
                        }, this ) );
                });
        });
        
        this.before({ only: { path : '#/perfil/receptor-correspondencia/editar', verb: 'post'}},function(context){
            $form = $(context.target);
            form_validation.init({
                form: '#'+$form.attr('id')
                ,rules: {
                    direccion: "required"
                    ,telefonofijo : {
                        required: true
                        ,digits: true
                        ,minlength: 9
                        ,maxlength: 9
                    }
                    ,telefonocelular : {
                        required: true
                        ,digits: true
                        ,minlength: 9
                        ,maxlength: 9
                    }
                    ,email: {
                      required: true,
                      email: true
                    }
                }
                ,messages: {
                    direccion: "Ingresa la direcci&oacute;n"
                    ,telefonofijo : {
                        required: "Ingresa el tel&eacute;fono fijo"
                        ,digits: "Solo d&iacute;gitos son v&aacute;lidos"
                        ,minlength: "Ingresa un tel&eacute;fono fijo v&aacute;lido"
                        ,maxlength: "Ingresa un tel&eacute;fono fijo v&aacute;lido"
                    }
                    ,telefonocelular : {
                        required: "Ingresa el celular"
                        ,digits: "Solo d&iacute;gitos son v&aacute;lidos"
                        ,minlength: "Ingresa un celular v&aacute;lido"
                        ,maxlength: "Ingresa un celular v&aacute;lido"
                    }
                    ,email: {
                      required: "Ingresa el email",
                      email: "Ingresa un email v&aacute;lido"
                    }
                  }
             });
             
            if($form.validate().form()){
                return true;
            }else{
                return false;
            }
        });


        /*=============== Routes ===============*/
       
        this.get( '#/', function(context) {                    
//            this.redirect( '#/avisos' );
        	this.redirect( '#/ambientes' );
        });
        
       
        this.get( '#/resumen', function() {
                this.callBaseTemplate( {
                        template: 'resumen',
                        url: 'resumen.json'
                });
        });
        this.get('#/delegados/confirmar/:idalumno/:idestudio/:idnivel/:idcandidato',function(context){
                var idalumno=this.params.idalumno,
                idestudio=this.params.idestudio,
                idnivel=this.params.idnivel,
                idcandidato=this.params.idcandidato;
                 this.callBaseTemplate({
                     template: 'delegados_guardarvoto',
                     url: 'json/delegado/confirmar.json',
                     title: 'Delegados',
                     data: {
                                    idCandidato: idalumno,
                                   idEdicionestudio:idestudio,
                                   idNivel: idnivel,
                                   idalumnoCandidato:idcandidato
                            }
                            
                 } ).then( function() {
                     context.getPlanMenu();
                   });
        });
         this.get('#/delegados',function(context){
                
                 
                 this.callBaseTemplate({
                     template: 'delegados',
                     url: 'json/delegado/eleccion.json',
                     data: {
                                    idEdicionestudio: currentPlan
                            }
                            
                 } ).then( function() {;
                 context.getPlanMenu();                                
                        $('a[rel=tooltip]').tooltip();
                 $( '.open-modal' ).on( 'click', function() {
                     var id = $( this ).data( 'id' );
                     var ee = $( this ).data( 'ee' );
                     var nivel = $( this ).data( 'nivel' );
                     var candidato = $( this ).data( 'idcandidato' );
                  context.renderModal(
                                 'confirmarvotacion',
                                   'json/delegado/confirmar.json',
                                   {
                                   idCandidato: id,
                                   idEdicionestudio:ee,
                                   idNivel: nivel,
                                   idalumnoCandidato:candidato
                                    }
                                  );
             }
            );
                } ); 
                } );
                
         this.post( '#/delegados/votacion', function( context ) {
              $.post( 'json/delegado/saveInfo.json', $( '#votacion' ).serialize(), function( data ) {
                         $('#siga-modal').modal('hide');
                         
                              context.redirect( '#/delegados');                               
                             
                        
                }, 'json');
        });
         
        this.get( '#/avisos', function() {
                this.callBaseTemplate({
                        template: 'avisos',
                        url: 'json/aviso/avisosList.json'
                }).then( function() {                        
                        var $announcements = $( '#announcements' );

                        // antes de mostrar contenido del aviso
                        $announcements.on( 'show', function( e ) {
                                var $element = $(e.target);

                                // marcar como activo
                                $element.prev().addClass( 'active' );

                                // marcar como ya renderizado
                                $element.data( 'rendered', true );
                        });

                        // deseleccionar activo al cerrar
                        $announcements.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass( 'active' );
                        });

                        $( '.category-filter a' ).on( 'click', function( e ) {
                                var $accordionGroup = $( '.accordion-group' ),
                                        mustBeGeneral = $( this ).data( 'general' );

                                // cambiar enlace activo
                                $( this ).parent( 'li' ).addClass( 'active' ).siblings().removeClass( 'active' );

                                // mostrar u ocultar de acuerdo a si es general o no
                                if ( mustBeGeneral ) {
                                        $accordionGroup.show();
                                }
                                else {
                                        $accordionGroup.hide();
                                        $( '.accordion-group[data-general="false"]').show();
                                }
                           
                                e.preventDefault();
                        });

                         // el primero debe estar abierto
                            $( '.stick' ).collapse( 'show' );
                        /* Cargando Notificaciones - Pimera vez*/
                        notificationMessage();
                        notificationPension();
                        notificationAsignatura();
                        
                        //Luego de haber seleccionado la especialidad se muestra un mensaje de confirmación al alumno.
                        $.post( 'json/aviso/mensajeEleccionEspecialidad.json' )
                            .done( function( data ) {
                                if ( data.mensaje !== undefined && data.mensaje.length > 0 ) {
                                    $('#siga-modal').find('#especialidadMsj').html(data.mensaje);
                                    $('#siga-modal').modal('show');
                                }
                            });
                });
        });

        this.get( '#/cursos' , function( context ) {    
            context.verifyPlanActivo(currentPlan)
                .done(function() {                      
                    context.callBaseTemplate({
                            template: 'asignaturas',
                            menu: 'cursos',
                            url: 'json/asignatura/asignaturaDictadaList.json',                                
                            data: {
                                    idEdicionestudio: currentPlan
                            }                                
                    }).then( function() {
                            var $expandAllButton = $('.expand-all');
                            var $collapseAllButton = $('.collapse-all');
                            var $subjectsAccordion = $('#subjects');

                            $('a[rel=tooltip], .btn[rel=tooltip], i[rel=tooltip]').tooltip();

                            context.getPlanMenu();

                            $expandAllButton.on( 'click', function() {
                                    $subjectsAccordion.find( '.accordion-body' ).collapse( 'show' );
                            });

                            $collapseAllButton.on( 'click', function() {
                                    $subjectsAccordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                            });

                            // accion para ir al detalle de la asignatura
                            $( '.view-subject-detail' ).one( 'click', function() {
                                    var subjectInitials = $( this ).data( 'initials' );
                                    var id = $( this ).data( 'id' );
                                    var idseccion = $( this ).data( 'idseccion' );

                                    // no abrir tab
                                    $subjectsAccordion.off( 'show click' );

                                    // ir a la otra pagina
                                    context.redirect( '#/cursos/' + subjectInitials + "/" + id + "/" + idseccion);
                            });

                            // antes de mostrar contenido del curso
                            $subjectsAccordion.on( 'show', '.accordion-body', function( e ) {
                                    var $element = $( e.target ),
                                            id = $element.data( 'id' ),
                                            idseccion = $element.data( 'idseccion' ),
                                            ee = $element.data( 'ee' );

                                    // si aun no se ha mostrado
                                    if ( id !== undefined && ! $element.data( 'rendered' ) ) {
                                            var $inner = $element.find( '.accordion-inner' );

                                            // obtener datos y renderizar
                                            context.renderTemplate({
                                                    template: 'asignaturas-evaluaciones',
                                                    url: 'json/asignatura/asignatura-evaluaciones.json',
                                                    data: {
                                                            idAsignaturaDictada: id,
                                                            idSeccion: idseccion,
                                                            idEdicionestudio: ee
                                                    }
                                            })
                                            .appendTo( $inner )
                                            .then( function() {
                                                    $inner.on( 'click', '.open-modal', function() {
                                                            var id = $(this).data( 'id' );
                                                            context.renderModal(
                                                                    'evaluaciones',
                                                                    'json/asignatura/evaluaciones-temas.json',
                                                                    {
                                                                            idEvaluacion: id
                                                                    }
                                                            );
                                                    });
                                                    $('i[rel=tooltip]').tooltip({placement: 'right'});
                                            });
                                    }

                                    // marcar como activo
                                    $element.prev().addClass( 'active' );

                                    // marcar como ya renderizado
                                    $element.data( 'rendered', true );
                            });

                            // deseleccionar activo al cerrar
                            $subjectsAccordion.on('hide', '.accordion-body', function(e) {
                                    $(e.target).prev().removeClass('active');
                            });
                    });
                });
        });

        this.get( '#/cursos/:initials/:id/:idseccion', function( context ) {
               if ( ! this.params.initials.match( /^[a-z0-9]+$/i ) ) {
                        this.redirect( '#/' );
                        return false;
                }

                var id = this.params.id,
                        idseccion = this.params.idseccion;

                this.callBaseTemplate( {
                        template: 'asignaturas-ver',
                        menu: 'cursos',
                        url: 'json/asignatura/asignaturaDetalle.json',
                        data: {
                                idAsignaturaDictada: id,
                                idSeccion: idseccion
                        }
                }).then( function() {
                        // cargar lista de asignaturas
                        context.renderTemplate({
                                template: 'asignaturas-lista',
                                url: 'json/asignatura/asignaturaDictadaList.json',
                                data: {
                                        idEdicionestudio: currentPlan
                                }
                        }).prependTo( app.$element() ).then( function() {
                                $( 'a[rel=tooltip]' ).tooltip();
                        });

                        // mostrar datos
                        $( '#subject-nav-tabs a' ).on( 'show', function( e ) {
                                var $tab = $( e.target ),
                                        tabName = $tab.data( 'id' ),
                                        $tabPane = $( $tab.data( 'target' ) );

                                if ( ! $tabPane.data( 'rendered' ) ) {
                                        context.renderTemplate({
                                                template: 'asignaturas-tab-' + tabName,
                                                url: 'json/asignatura/asignatura-' + tabName + '.json',
                                                data: {
                                                        idAsignaturaDictada: id,
                                                        idSeccion: idseccion,
                                                        idEdicionestudio: currentPlan
                                                }
                                        }).replace( $tabPane ).then( function() {
                                                var $accordion = $tabPane.find( '.accordion' ),
                                                        $expandAllButton = $tabPane.find( '.expand-all' ),
                                                        $collapseAllButton = $tabPane.find( '.collapse-all' );

                                                $accordion.on( 'show', function( e ) {
                                                        $( e.target ).prev().addClass( 'active' );
                                                });

                                                $accordion.on( 'hide', function( e ) {
                                                        $( e.target ).prev().removeClass('active');
                                                });

                                                $expandAllButton.on( 'click', function() {
                                                        $tabPane.find( '.accordion-body' ).collapse( 'show' );
                                                });

                                                $collapseAllButton.on( 'click', function() {
                                                        $tabPane.find( '.accordion-body.in' ).collapse( 'toggle' );
                                                });

                                                $expandAllButton.click();

                                                if ( $tabPane.attr( 'id' ) === 'subject-tab-evaluaciones' ) {
                                                        $tabPane.on( 'click', '.open-modal', function() {
                                                                var id = $(this).data( 'id' );
                                                                context.renderModal(
                                                                        'evaluaciones',
                                                                        'json/asignatura/evaluaciones-temas.json',
                                                                        {
                                                                                idEvaluacion: id
                                                                        }
                                                                );
                                                        });
                                                        
                                                        $('i[rel=tooltip]').tooltip({placement: 'right'});
                                                }

                                                $tabPane.data( 'rendered', true);
                                        });
                                }
                        });

                        $( 'a[data-target="#subject-tab-evaluaciones"]' ).click();

                }).then( function() {
                        $( document ).trigger( 'callback-executed' );
                });
        });

        this.get( '#/cursos/:initials/:id/:idseccion/:link', function() {

                if ( last_template !== 'asignaturas-ver' ) {
                        app.runRoute( 'get', '#/cursos/'
                                + this.params.initials + '/' + this.params.id + '/'
                                + this.params.idseccion );
                }

                $( document ).one( 'callback-executed', $.proxy( function() {
                        //app.log( 'callback-executed llamado en initials:link' );
                        $( 'a[data-target="#subject-tab-' + this.params.link + '"]' ).tab( 'show' );
                }, this ) );

        });

        this.get( '#/horario', function() {
                var schedulePromise = $.post( 'json/horario/horarioList.json' );
                this.callBaseTemplate( {
                        template: 'horario',
                        url: 'json/horario/horario.json'
                }).then(function(){
                        schedulePromise.done( function( response ) {
                            if(response.ishorarioevento){
                                //nuevo horario
                                if ( $( '#pregrado').length > 0 ) {
                                    
                                        response.horarioList.forEach( function( asignatura ) {                                                        
                                              var   html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '('+ asignatura.nombreSeccion +')'+'</strong>(@Descripcion)</a>';

                                                if(asignatura.retiroCurso){
                                                    html = '<div class="subject-block">' +
                                                        '<strong class="initials">' + asignatura.sigla +'('+asignatura.nombreSeccion+')'+'</strong>(@Descripcion)</div>';
                                                }
                                                
                                                        $( '#ge-' + asignatura.orden + '-' + asignatura.dia )
                                                                .append( html.replace( '@Descripcion', asignatura.aula ) );
                                                
                                                

                                               
                                        });
                                         response.asignaturaList.forEach( function( asignatura ) {
                                              var html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';
                                                
                                                        asignatura.practicaProgramadaList.forEach( function( block ) {
                                                        $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia )
                                                                .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                                });
                                                
                                         
                                         });
                                }
                                
                            }else {
                                if ( $( '#pregrado').length > 0 ) {
                                        response.asignaturaList.forEach( function( asignatura ) {                                                        
                                                var html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';

                                                if(asignatura.retiroCurso){
                                                    html = '<div class="subject-block">' +
                                                        '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</div>';
                                                }
                                                asignatura.horarioList.forEach( function( block ) {
                                                        $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia )
                                                                .append( html.replace( '@Descripcion', block.aula.nombre ) );
                                                });

                                                asignatura.practicaProgramadaList.forEach( function( block ) {
                                                        $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia )
                                                                .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                                });
                                        });
                                }
                            }
                        });
                });
        });
          
        
         this.get( '#/horario/prev/:id', function() {
             var valor = this.params.id;
              var sufix="prev";
              console.log(sufix);
             
                var schedulePromise = $.post( 'json/horario/horarioList2.json?id='+valor+'&sufix=prev');

                this.callBaseTemplate( {
                        template: 'horario_2',
                        url: 'json/horario/horario2.json',
                        data:{
                            id:valor,
                            sufix:sufix
                        }
                        
                }).then(function(){
                   
                        schedulePromise.done( function( response ) {
                            console.log("path prev "+response.sufix);
                           
                            if(response.ishorarioevento){
                                //nuevo horario
                                if ( $( '#pregrado').length > 0 ) {
                                    
                                        response.horarioList.forEach( function( asignatura ) {                                                        
                                              var   html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '('+ asignatura.nombreSeccion +')'+'</strong>(@Descripcion)</a>';

                                                if(asignatura.retiroCurso){
                                                    html = '<div class="subject-block">' +
                                                        '<strong class="initials">' + asignatura.sigla +'('+asignatura.nombreSeccion+')'+'</strong>(@Descripcion)</div>';
                                                }
                                                
                                                        $( '#ge-' + asignatura.orden + '-' + asignatura.dia )
                                                                .append( html.replace( '@Descripcion', asignatura.aula ) );
                                                
                                                

                                               
                                        });
                                         response.asignaturaList.forEach( function( asignatura ) {
                                              var html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';
                                                
                                                        asignatura.practicaProgramadaList.forEach( function( block ) {
                                                        $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia )
                                                                .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                                });
                                                
                                         
                                         });
                                }
                                
                            }
                        });
                });
        });
        
         this.get( '#/horario/next/:id', function() {
             var valor = this.params.id;
             var sufix="next";
             console.log(valor);
             console.log(sufix);
           
                var schedulePromise = $.post( 'json/horario/horarioList2.json?id='+valor+'&sufix=next');
                 
                this.callBaseTemplate( {
                        template: 'horario_1',
                        url: 'json/horario/horario2.json',
                        data:{
                            id:valor,
                            sufix:sufix
                        }
                        
                }).then(function(){
                 
                        schedulePromise.done( function( response ) {
                            console.log("path next"+response.sufix);
                            var sufix=response.sufix;
                            if(response.ishorarioevento){
                                
                                //nuevo horario
                                if ( $( '#pregrado').length > 0 ) {
                                    
                                        response.horarioList.forEach( function( asignatura ) {                                                        
                                              var   html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '('+ asignatura.nombreSeccion +')'+'</strong>(@Descripcion)</a>';

                                                if(asignatura.retiroCurso){
                                                    html = '<div class="subject-block">' +
                                                        '<strong class="initials">' + asignatura.sigla +'('+asignatura.nombreSeccion+')'+'</strong>(@Descripcion)</div>';
                                                }
                                                
                                                        $( '#ge-' + asignatura.orden + '-' + asignatura.dia )
                                                                .append( html.replace( '@Descripcion', asignatura.aula ) );
                                                
                                                

                                               
                                        });
                                         response.asignaturaList.forEach( function( asignatura ) {
                                              var html = '<a class="subject-block" href="#/cursos/' +
                                                        asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                        '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';
                                                
                                                        asignatura.practicaProgramadaList.forEach( function( block ) {
                                                        $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia )
                                                                .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                                });
                                                
                                         
                                         });
                                }
                                
                            }
                        });
                });
        });
        

        this.get( '#/perfil/academico', function( context ) {

                this.callBaseTemplate( {
                        template: 'perfil-academico',
                        menu: 'perfil/academico',
                        url: 'json/perfilacademico/perfilAcademico.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {
                        context.getEstudioAllMenu();                                
                        $('a[rel=tooltip]').tooltip();
                });

        });

        this.get( '#/perfil/academico/actual', function( context ) {

                this.callBaseTemplate( {
                        template: 'perfil-academico-actual',
                        menu: 'perfil/academico',
                        url: 'json/perfilacademico/periodoActual.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        context.getEstudioAllMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                        $('.btn[rel=tooltip]').tooltip();

                        // accion para ir al detalle de la asignatura
                        $( '.view-subject-detail' ).one( 'click', function() {
                                var subjectInitials = $( this ).data( 'initials' );
                                        var id = $( this ).data( 'id' );
                                        var idseccion = $( this ).data( 'idseccion' );

                                // no abrir tab
                                $accordion.off( 'show click' );

                                // ir a la otra pagina
                                context.redirect( '#/cursos/' + subjectInitials + "/" + id + "/" + idseccion);
                        });
                });
        });

        this.get( '#/perfil/academico/indicadores', function( context ) {

                var chartPromise = $.post('json/perfilacademico/graficoRendimiento.json', {idEdicionestudio: currentPlan});

                this.callBaseTemplate( {
                        template: 'perfil-academico-indicadores',
                        menu: 'perfil/academico',
                        url: 'json/perfilacademico/indicadores.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {
                        context.getEstudioAllMenu();

                        yepnope({
                                test: Modernizr.canvas,
                                nope: [
                                        'resources/js/vendor/explorercanvas.min.js'
                                ],
                                load: [
                                        'resources/js/vendor/chart.min.js'
                                ],
                                complete: function() {
                                        var axis = {
                                                x: [],
                                                y: []
                                        };

                                        // iterar sobre los datos para convertirlos en eje
                                        chartPromise.done( function( response ) {
                                                $.each( response.datos, function( index, value ) {
                                                        axis.x.push( value.periodo );
                                                        axis.y.push( value.indice );
                                                });
                                        });

                                        // datos para el grafico
                                        var data = {
                                                labels : axis.x,
                                                datasets : [
                                                        {
                                                                fillColor : "rgba(65,128,171,.5)",
                                                                strokeColor : "#d3d3d3",
                                                                pointColor : "#A2CF28",
                                                                pointStrokeColor : "#fff",
                                                                data : axis.y
                                                        }
                                                ]
                                        };

                                        // opciones del grafico
                                        var options = {
                                                scaleOverlay: true,
                                                scaleFontSize: 10,
                                                scaleShowLabels: true,
                                                scaleFontColor: '#999',
                                                animationSteps: 25
                                        };

                                        var setupCanvas = function( canvas ) {
                                                var ctx, newWidth;

                                                canvas = $(canvas);
                                                newWidth = canvas.parent().width();
                                                canvas.prop({
                                                        width: newWidth,
                                                        height: 360
                                                });
                                                canvasEl = canvas.get(0);
                                                if(typeof canvasEl != 'undefined' ) {


                                                if ( typeof G_vmlCanvasManager != 'undefined' ) {
                                                        G_vmlCanvasManager.initElement( canvasEl );
                                                        options.animationSteps = 3;
                                                        canvas.parent().css('position', 'relative');
                                                }
                                                ctx = canvasEl.getContext( '2d' );
                                                return new Chart( ctx ).Line( data, options );
                                                }else{
                                                    return null;
                                                }
                                        };

                                        (function(canvas) {
                                                setupCanvas(canvas);
                                                return $(window).resize(function() {
                                                        return setupCanvas(canvas);
                                                });
                                        })( '#the-chart' );
                                }
                        });    
                });
        });

        this.get( '#/perfil/academico/historial', function( context ) {

                this.callBaseTemplate( {
                        template: 'perfil-academico-historial',
                        menu: 'perfil/academico',
                        url: 'json/perfilacademico/historial.json',
                        data:{
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        context.getEstudioAllMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                });

        });
        
        this.get( '#/perfil/academico/historial-idiomas', function( context ) {

                this.callBaseTemplate( {
                        template: 'perfil-academico-historial-idiomas',
                        menu: 'perfil/academico',
                        url: 'json/perfilacademico/historial-idiomas.json',
                        data:{
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        context.getEstudioAllMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                });

        });

        this.get( '#/perfil/academico/solicitudes', function( context ) {
                this.callBaseTemplate( {
                        template: 'perfil-academico-solicitudes',
                        menu: 'perfil/academico',
                        url: 'json/tramiteacademico/finalizadosList.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        context.getEstudioAllMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                });

        });

        this.get( '#/perfil/personal', function() {
                this.callBaseTemplate( {
                        template: 'perfil-personal',
                        menu: 'perfil/personal',
                        url: 'json/personal/info.json'
                        ,data:{
                                idEdicionestudio: currentPlan
                        }
                });
        });

        this.get( '#/perfil/personal/editar', function( context ) {
                var departamentoPromise = $.post( 'json/personal/departamentoList.json' );

                this.callBaseTemplate( {
                        template: 'perfil-personal-editar',
                        menu: 'perfil/personal',
                        url: 'json/personal/infoEdit.json'
                }).then( function() {
                        var emptyOption = '<option value="0">Seleccione...</option>';

                        var $departamento = $( '#departamento' ),
                                $provincia = $( '#provincia' ),
                                $distrito = $( '#distrito' ),
                                $departamentoPension = $( '#pension_departamento' ),
                                $provinciaPension = $( '#pension_provincia' ),
                                $distritoPension = $( '#pension_distrito' );

                        var departamentoOrigin = $departamento.data( 'origin' ),
                                provinciaOrigin = $provincia.data( 'origin' ),
                                distritoOrigin = $distrito.data( 'origin' ),
                                departamentoPensionOrigin = $departamentoPension.data( 'origin' ),
                                provinciaPensionOrigin = $provinciaPension.data( 'origin' ),
                                distritoPensionOrigin = $distritoPension.data( 'origin' );

                        departamentoPromise.done( function( response ) {
                                var items = $.map( response.departamentoList, function( value ) {
                                        return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                });

                                // listar departamentos
                                $departamento.html( items.join('') );
                                $departamentoPension.html( items.join('') );

                                // seleccionar el departamento
                                $departamento.val( departamentoOrigin );

                                // seleccionar el departamento de la pension
                                $departamentoPension.val( departamentoPensionOrigin );
                        });

                        // si hay un departamento seleccionado
                        departamentoOrigin = typeof departamentoOrigin !== 'undefined' && departamentoOrigin !== null
                                ? departamentoOrigin
                                // sino toma el primer departamento mostrado
                                : $departamento.val();

                        // cargar lista de provincias
                        $.post( 'json/personal/provinciaList.json?idDepartamento=' + departamentoOrigin )
                                .done( function( response ) {
                                        var items = $.map( response.provinciaList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        // listar provincias
                                        $provincia.html( items.join('') ).val( provinciaOrigin );
                                });

                        // si hay una provincia seleccionada
                        if ( typeof provinciaOrigin !== 'undefined' && provinciaOrigin !== null ) {
                                // cargar lista de distritos
                                $.post( 'json/personal/distritoList.json?idProvincia=' + provinciaOrigin )
                                        .done( function( response ) {
                                                var items = $.map( response.distritoList, function( value ) {
                                                        return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                                });

                                                // listar distritos
                                                $distrito.html( items.join('') ).val( distritoOrigin );
                                        });
                        }

                        // si hay un departamento_pension seleccionado
                        departamentoPensionOrigin = typeof departamentoPensionOrigin !== 'undefined' && departamentoPensionOrigin !== null
                                ? departamentoPensionOrigin
                                // sino toma el primer departamento mostrado
                                : $departamentoPension.val();

                        // cargar lista de provincias
                        $.post( 'json/personal/provinciaList.json?idDepartamento=' + departamentoPensionOrigin )
                                .done( function( response ) {
                                        var items = $.map( response.provinciaList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        // listar provincias
                                        $provinciaPension.html( items.join('') ).val( provinciaPensionOrigin );
                                });

                        // si hay una provincia_pension seleccionada
                        if ( typeof provinciaPensionOrigin !== 'undefined' && provinciaPensionOrigin !== null ) {
                                // cargar lista de distritos
                                $.post( 'json/personal/distritoList.json?idProvincia=' + provinciaPensionOrigin )
                                        .done( function( response ) {
                                                var items = $.map( response.distritoList, function( value ) {
                                                        return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                                });

                                                // listar distritos
                                                $distritoPension.html( items.join('') ).val( distritoPensionOrigin );
                                        });
                        }

                        // al cambiar departamento
                        $departamento.on( 'change', function() {
                                var provinciaPromise = $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamento.val() );

                                // eliminar inmediatamente
                                $provincia.html( emptyOption );
                                $distrito.html( emptyOption );

                                provinciaPromise.done( function( response ) {
                                        var items = $.map( response.provinciaList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar departamentos
                                        $provincia.html( items.join('') );
                                });
                        });

                        // al cambiar provincia
                        $provincia.on( 'change', function() {
                                var distritoPromise = $.post( 'json/personal/distritoList.json?idProvincia=' + $provincia.val() );

                                // eliminar inmediatamente
                                $distrito.html( emptyOption );

                                distritoPromise.done( function( response ) {
                                        var items = $.map( response.distritoList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar distritos
                                        $distrito.html( items.join('') );
                                });
                        });

                        // al cambiar departamento_pension
                        $departamentoPension.on( 'change', function() {
                                var provinciaPensionPromise = $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamentoPension.val() );

                                // eliminar inmediatamente
                                $provinciaPension.html( emptyOption );
                                $distritoPension.html( emptyOption );

                                provinciaPensionPromise.done( function( response ) {
                                        var items = $.map( response.provinciaList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar departamentos
                                        $provinciaPension.html( items.join('') );
                                });
                        });

                        // al cambiar provincia_pension
                        $provinciaPension.on( 'change', function() {
                                var distritoPensionPromise = $.post( 'json/personal/distritoList.json?idProvincia=' + $provinciaPension.val() );

                                // eliminar inmediatamente
                                $distritoPension.html( emptyOption );

                                distritoPensionPromise.done( function( response ) {
                                        var items = $.map( response.distritoList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar distritos
                                        $distritoPension.html( items.join('') );
                                });
                        });
                });
        });
      
        this.post( '#/perfil/personal/editar', function( context ) {
                $perfilPersonalEditarSubmit=$("#perfil-personal-editar-submit");
                $perfilPersonalEditarSubmit2=$("#perfil-personal-editar-submit2");
                $perfilPersonalEditarSubmit.button('loading');
                $perfilPersonalEditarSubmit2.button('loading');
                $.post( 'json/personal/saveInfoEdit.json', $( '#edit-profile' ).serialize(), function( data ) {
                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $perfilPersonalEditarSubmit.button('reset');
                                $perfilPersonalEditarSubmit2.button('reset');
                        }
                        else {
                                context.redirect( '#/perfil/personal' );
                                $( document ).one( 'template-loaded', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'Perfil actualizado' ) );
                                        context.scrollToElement( $mainStack );
                                });
                        }
                        context.scrollToElement( $mainStack );
                }, 'json');
        
        });
        
        this.get( '#/perfil/receptor-correspondencia', function(context) {
                this.callBaseTemplate( {
                        template: 'perfil-receptor-correspondencia',
                        menu: 'perfil/personal',
                        url: 'json/personal/receptorCorrespondencia.json',
                        data: {idEdicionestudio: currentPlan}
                }).then( function() {
                        // mostrar datos
                        $( '#subject-nav-tabs a' ).on( 'show', function( e ) {
                                var $tab = $(e.target),
                                        idTipo = $tab.data( 'id' ),
                                        $tabPane = $( $tab.data( 'target' ) );

                                if (!$tabPane.data( 'rendered' )){
                                     context.renderTemplate({
                                            template: 'perfil-receptor-miembro',
                                            url: 'json/personal/verInfoMiembro.json',
                                            data: {idTipo: idTipo, idEdicionestudio: currentPlan}
                                    }).replace( $tabPane ).then( function() {            
                                        $tabPane.data( 'rendered', true);
                                    });
                                }
                        });
                        $('#subject-nav-tabs a:first').click();
                });
        });
        
        this.get( '#/perfil/receptor-correspondencia/editar/:miembro', function(context) {
                var tipoMiembro = this.params.miembro;
                this.callBaseTemplate( {
                        template: 'perfil-receptor-correspondencia',
                        menu: 'perfil/personal',
                        url: 'json/personal/receptorCorrespondencia.json',
                        data: {idEdicionestudio: currentPlan}
                }).then( function() {
                        // mostrar datos
                        $( '#subject-nav-tabs a' ).on( 'show', function( e ) {
                                var $tab = $(e.target),
                                        idTipo = $tab.data( 'id' ),
                                        $tabPane = $( $tab.data( 'target' ) );

                                if (!$tabPane.data( 'rendered' )){
                                     context.renderTemplate({
                                            template: 'perfil-receptor-miembro-editar',
                                            url: 'json/personal/verInfoMiembro.json',
                                            data: {idTipo: idTipo, idEdicionestudio: currentPlan}
                                    }).replace( $tabPane ).then( function() {            
                                        $tabPane.data( 'rendered', true);
                                    });
                                }
                        });
                        $('#nav-tab-' + tipoMiembro).click();
                });
        });
        
        this.post( '#/perfil/receptor-correspondencia/editar', function(context) {
                $.post( 'json/personal/updateInfoMiembro.json', $('#edit-miembro').serialize(), function( data ) {
                        $submitAsesor=$("#edit-miembro");
                        $submitAsesor.button('loading');
                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitAsesor.button('reset');
                        }
                        else {
                                context.redirect( '#/perfil/receptor-correspondencia');
                                $( document ).one( 'template-loaded', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'La informaci&oacute;n del miembro de la familia a sido actualizada correctamente.' ) );
                                });
                        }
                }, 'json');
        });

        this.get( '#/asesor', function() {
                this.redirect( '#/asesor/perfil' );
        });

        this.get( '#/asesor/perfil', function(context) {
            context.verifyPlanActivo(currentPlan)
                .done(function() {
                context.callBaseTemplate( {
                        template: 'asesor-perfil',
                        menu: 'asesor/perfil',
                        url: 'json/asesor/perfil.json',
                        data: {
                                    idEdicionestudio: currentPlan
                            }
                });
            });
        });

        this.get( '#/asesor/horario', function(context) {
                var schedulePromise = $.post( 'json/asesor/horario.json' );
                context.verifyPlanActivo(currentPlan)
                .done(function() {
                context.callBaseTemplate( {
                        template: 'asesor-horario',
                        menu: 'asesor/perfil',
                        url: 'json/asesor/perfil.json',
                        data: {
                                    idEdicionestudio: currentPlan
                            }
                }).then( function() {
                        schedulePromise.done( function( response ) {
                                response.asignaturaList.forEach( function( asignatura ) {
                                        var html = '<a class="subject-block" href="#/asignaturas/' +
                                                asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';

                                        asignatura.horarioList.forEach( function( block ) {
                                                $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.aula.nombre ) );
                                        });

                                        asignatura.practicaProgramadaList.forEach( function( block ) {
                                                $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                        });
                                });

                                response.asesoriaList.forEach( function( block ) {
                                        var ubicacion = '';

                                        if ( block.oficina !== null ){
                                                ubicacion = '(' + block.oficina + ' - ' + block.edificio + ')';
                                        }

                                        var html = '<a class="subject-block" href="#/asesor/horario">' +
                                                '<strong class="initials">' + block.titulo + '</strong> ' + ubicacion + '</a>';

                                        $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                .append( html );
                                });
                        });
                });
        });
        });

        this.get( '#/asesor/entrevistas', function(context) {
            context.verifyPlanActivo(currentPlan)
                .done(function() {
                context.callBaseTemplate( {
                        template: 'asesor-entrevistas',
                        menu: 'asesor/perfil',
                        url: 'json/asesor/entrevistas.json',
                        data: {
                                    idEdicionestudio: currentPlan
                            }
                });
            });
        });
            
//        this.get( '#/asesor/sugeridos', function(context) {
//            context.verifyPlanActivo(currentPlan)
//                .done(function() {
//                    context.callBaseTemplate( {
//                        template: 'asesor-sugerido',
//                        menu: 'asesor/perfil',
//                        url: 'json/asesor/sugeridosupdated.json',
//                        data: {
//                                    idEdicionestudio: currentPlan
//                            }
//                    }).then( function() {				
//                        //
//                        //cuando cambia asesor 1
//                        //
//                        var sugerido1 = $('#sugerido_1');
//                        sugerido1.on( 'change', function() {                                    
//                            //app.log( 'cambio ' +  sugerido1.val()); 
//                            $.post( 'json/asesor/getFoto.json?idAsesorSugerido=' + sugerido1.val())
//                                    .done( function( response ) {	
//                                var items ='<img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/'+ response.fotoAsesor +'" width = "100px"/>';
//                                $('.img_Asesor_1').html( items );
//                            });
//
//                        });
//                        //
//                        //cuando cambia asesor 1
//                        //
//                        var sugerido2 = $('#sugerido_2');
//                        sugerido2.on( 'change', function() {                                    
//                            //app.log( 'cambio ' +  sugerido2.val()); 
//                            $.post( 'json/asesor/getFoto.json?idAsesorSugerido=' + sugerido2.val())
//                                    .done( function( response ) {	
//                                var items ='<img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/'+ response.fotoAsesor +'" width = "100px"/>';
//                                $('.img_Asesor_2').html( items );
//                            });
//
//                        });
//                });
//            });
//        });

        this.post( '#/asesor/sugeridos', function(context) {
                $.post( 'json/asesor/saveAsesorSugerido.json', $( '#new-sugeridos' ).serialize(), function( data ) {
                        $submitAsesor=$("#submit-asesores");
                        $submitAsesor.button('loading');
                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitAsesor.button('reset');
                        }
                        else { 
                                context.redirect( '#/asesor/sugeridos');
                                $( document ).one( 'template-loaded', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'Gracias por seleccionar a tu asesor. En breve se actualizar&aacute; la informaci&oacute;n del asesor asignado.' ) );
                                });
                        }
                }, 'json');
        });

        this.get( '#/pensiones', function() {
                this.redirect('#/pensiones/cronogramas');
        });

        this.get( '#/pensiones/cronogramas', function(context) {
            context.verifyPlanActivo(currentPlan)
                .done(function() {
                    context.callBaseTemplate( {
                            template: 'pensiones',
                            menu: 'pensiones/cronogramas',
                            url: 'json/pensiones/cronogramas.json',
                            data: {
                                    idEdicionestudio: currentPlan
                            }
                    }).then( function() {
                        
                        //agregando modal
                        $( '.open-modal' ).on( 'click', function() {
                     var cpgp = $( this ).data( 'id' );
                     var fechav = $( this ).data( 'fechav' );
                     var cuota = $( this ).data( 'cuota' );
                    
                  context.renderModal(
                                 'reporteboleta',
                                   'json/pensiones/pagos.json',
                                   {
                                   cpgp: cpgp,
                                   idEdicionestudio: currentPlan,
                                   fechav:fechav,
                                   cuota:cuota
                                    }
                                  );
             }
            );
                        
                            var $expandAllButton = $( '.expand-all' ),
                                    $collapseAllButton = $( '.collapse-all' ),
                                    $accordion = $( '.accordion' );

                            context.getPlanMenu();

                            $accordion.on('show', function( e ) {
                                    $( e.target ).prev().addClass( 'active' );
                            });

                            $accordion.on( 'hide', function( e ) {
                                    $( e.target ).prev().removeClass( 'active' );
                            });

                            $expandAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body' ).collapse( 'show' );
                            });

                            $collapseAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                            });

                            // el primero debe estar abierto
                            $( '.collapse' ).first().collapse( 'show' );
                    });
               });
        });
        
        this.post( '#/pensiones/cronogramas', function( context ) {
                $.post('json/pensiones/verifySavePagoEspecial.json', $( '#new-pagoEsp' ).serialize(), function(data) {
                        $submitPago=$("#submit-pago-esp");
                        $submitPago.button('loading');
                        context.parseGeneralInfo( 'La solicitud ha sido enviada satisfactoriamente.' );
                        $submitPago.button('reset');
                }, 'json');
        });

        this.get( '#/pensiones/pagosEspeciales', function( context ) {
                this.callBaseTemplate({
                        template: 'pagos-especiales',
                        menu: 'pensiones/pagosEspeciales',
                        url: 'json/pensiones/pagosEspeciales.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        context.getPlanMenu();
                        var $radios = document.getElementsByTagName('input');
                        var $value;
                        //
                        //cuando cambia tipoPago
                        //
                        $('input[name="tipo"]').on( 'change', function() {                                    
                            for (var i = 0; i < $radios.length; i++) {
                                if ($radios[i].type === 'radio' && $radios[i].checked) {
                                    $value = $radios[i].value;       
                                }
                            }
                            //app.log( 'llamado a radio ' +  $value); 
                            $.post( 'json/pensiones/fechaPagosEspeciales.json?idEdicionestudio=' + currentPlan + '&idTipo=' + $value)
                                    .done( function( response ) {
                                var items = $.map( response.fechaPagoList, function( data ) {
                                    return '<tr><td>Cuota # ' + data.cuota + '</td><td>' + data.fecha + '</td></tr>';                                                        
                                });
                                // listar fechas
                                $('#fechaCuotaTable tbody').html( items );
                            });

                        });
                });
        }); 
                
        this.get( '#/mensajes', function() {
                this.redirect( '#/mensajes/inbox' );
        });

        this.get( '#/mensajes/inbox', function( context ) {
                this.callBaseTemplate( {
                        template: 'mensajes-recibidos',
                        menu: 'mensajes/inbox',
                        url: 'json/mensaje/inboxList.json',
                                                        data: {
                                all: false
                        }
                }).then( function() {

                        var $expandAllButton = $('.expand-all'),
                                $collapseAllButton = $('.collapse-all'),
                                $accordion = $('.accordion'),
                                $messageCountBadge = $('.message-inbox-count');

                        context.countUnreadMessages( $messageCountBadge );

                        $accordion.on( 'show', function( e ) {
                                var $element = $( e.target );

                                // marcar como activo
                                $element.prev().addClass( 'active' );

                                // si no ha sido leido
                                if ( $element.data( 'read' ) === false ) {

                                        var ajaxData = {
                                                id: $element.data( 'id' ),
                                                fechaleido: $element.data( 'fechaleido' )
                                        };

                                        $.post( 'json/mensaje/marcarleido.json', ajaxData, function() {
                                                $element.data( 'read', true);
                                                $element.prev().find( '.message-status' ).removeClass( 'icon-envelope-alt' ).addClass( 'icon-envelope' );
                                                context.countUnreadMessages( $messageCountBadge );
                                        });

                                }
                        });

                        $accordion.on('hide', function(e) {
                                $(e.target).prev().removeClass('active');
                        });

                        $expandAllButton.on('click', function() {
                                $accordion.find('.accordion-body').collapse('show');
                        });

                        $collapseAllButton.on('click', function() {
                                $accordion.find('.accordion-body.in').collapse('toggle');
                        });

                });
        });

                        this.get( '#/mensajes/inbox/all', function( context ) {
                                        this.callBaseTemplate( {
                        template: 'mensajes-recibidos',
                        menu: 'mensajes/inbox',
                        url: 'json/mensaje/inboxList.json',
                                                        data: {
                                all: true
                        }
                }).then( function() {

                        var $expandAllButton = $('.expand-all'),
                                $collapseAllButton = $('.collapse-all'),
                                $accordion = $('.accordion'),
                                $messageCountBadge = $('.message-inbox-count');

                        context.countUnreadMessages( $messageCountBadge );

                        $accordion.on( 'show', function( e ) {
                                var $element = $( e.target );

                                // marcar como activo
                                $element.prev().addClass( 'active' );

                                // si no ha sido leido
                                if ( $element.data( 'read' ) === false ) {

                                        var ajaxData = {
                                                id: $element.data( 'id' ),
                                                fechaleido: $element.data( 'fechaleido' )
                                        };

                                        $.post( 'json/mensaje/marcarleido.json', ajaxData, function() {
                                                $element.data( 'read', true);
                                                $element.prev().find( '.message-status' ).removeClass( 'icon-envelope-alt' ).addClass( 'icon-envelope' );
                                                context.countUnreadMessages( $messageCountBadge );
                                        });

                                }
                        });

                        $accordion.on('hide', function(e) {
                                $(e.target).prev().removeClass('active');
                        });

                        $expandAllButton.on('click', function() {
                                $accordion.find('.accordion-body').collapse('show');
                        });

                        $collapseAllButton.on('click', function() {
                                $accordion.find('.accordion-body.in').collapse('toggle');
                        });

                });
                        });

        this.get( '#/mensajes/outbox', function( context ) {

                this.callBaseTemplate( {
                        template: 'mensajes-enviados',
                        menu: 'mensajes/inbox',
                        url: 'json/mensaje/outboxList.json'
                }).then( function() {

                        var $expandAllButton = $('.expand-all'),
                                $collapseAllButton = $('.collapse-all'),
                                $accordion = $('.accordion'),
                                $messageCountBadge = $('.message-inbox-count');

                        context.countUnreadMessages( $messageCountBadge );

                        $accordion.on( 'show', function(e) {
                                $(e.target).prev().addClass( 'active' );
                        });

                        $accordion.on('hide', function(e) {
                                $(e.target).prev().removeClass('active');
                        });

                        $expandAllButton.on('click', function() {
                                $accordion.find('.accordion-body').collapse('show');
                        });

                        $collapseAllButton.on('click', function() {
                                $accordion.find('.accordion-body.in').collapse('toggle');
                        });

                });
        });

        this.get( '#/mensajes/nuevo', function( context ) {

                // si proviene de asesor
                var backToAdviser = last_template.match(/asesor-\w+/i);

                this.callBaseTemplate( {
                        template: 'mensajes-nuevo',
                        menu: 'mensajes/inbox',
                        url: 'json/mensaje/mensajeNuevo.json'
                }).then( function() {

                        var $messageCountBadge = $( '.message-inbox-count' );

                        if ( backToAdviser ) {
                                $( '.btn-back' ).attr( 'href', '#/asesor' ).text( 'Volver al perfil de mi asesor' );
                        }

                        context.countUnreadMessages( $messageCountBadge );
                });
        });

        this.post( '#/mensajes/nuevo', function( context ) {
                $.post( 'json/mensaje/verifySaveMensaje.json', $( '#new-message' ).serialize(), function( data ) {
                        $submitMensaje=$("#submit-mensaje");
                        $submitMensaje.button('loading');
                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitMensaje.button('reset');
                        }
                        else {
                                context.redirect( '#/mensajes/outbox' );
                                $( document ).one( 'template-loaded', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'Mensaje enviado' ) );
                                });
                        }
                }, 'json');
        });

        this.get( '#/tramites', function() {
                this.redirect('#/tramites/pendientes');
        });

        this.get( '#/tramites/pendientes', function( context ) {
            context.verifyPlanActivo(currentPlan)
                .done(function() {
                    context.callBaseTemplate({
                            template: 'tramites',
                            menu: 'tramites/pendientes',
                            url: 'json/tramiteacademico/pendientesList.json',
                            data: {idEdicionestudio: currentPlan}
                    }).then( function() {
                            var $expandAllButton = $( '.expand-all' ),
                                    $collapseAllButton = $( '.collapse-all' ),
                                    $accordion = $( '.accordion' );

                            $('.nav-pills li').first().addClass('active');

                            context.getPlanMenu();

                            $accordion.on( 'show', function( e ) {
                                    $( e.target ).prev().addClass( 'active' );
                            });

                            $accordion.on( 'hide', function( e ) {
                                    $( e.target ).prev().removeClass( 'active' );
                            });

                            $expandAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body' ).collapse( 'show' );
                            });

                            $collapseAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                            });

                    });
               });
        });

        this.get( '#/tramites/finalizados', function( context ) {
                this.callBaseTemplate({
                        template: 'tramites',
                        menu: 'tramites/pendientes',
                        url: 'json/tramiteacademico/finalizadosList.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        $('.nav-pills li').last().addClass('active');

                        context.getPlanMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass( 'active' );
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                });
        });

        this.get( '#/tramites/nuevo', function() {
                this.callBaseTemplate( {
                        template: 'tramites-nuevo',
                        menu: 'tramites/pendientes',
                        url: 'json/tramiteacademico/nuevo.json'
                }).then( function() {

                        $('#select-process .nav a').on('click', function(e) {
                                $('#solicitud-id').attr('value', $(this).data('id'));
                                $('#solicitud-nombre').attr('value', $(this).text());
                                $('#select-process').slideUp('fast');
                                $('#new-process').slideDown('fast');
                                e.preventDefault();
                        });

                        $('#change-process').on('click', function(e) {
                                $('#select-process').slideDown('fast');
                                $('#new-process').slideUp('fast');
                                e.preventDefault();
                        });

                        $('button[rel="tooltip"]').tooltip();
                });
        });

        this.post( '#/tramites/nuevo', function( context ) {
                $.post('json/tramiteacademico/saveTramite.json', $( '#new-process' ).serialize(), function( data ) {
                        $submitTramite=$("#submit-tramite");
                        $submitTramite.button('loading');
                        if ( $.isArray( data.errores ) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitTramite.button('reset');
                        }
                        else {
                                context.redirect( '#/tramites/pendientes' );
                                $ (document ).one( 'template-loaded ', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'La solicitud ha sido enviada para su revisi&oacute;n.' ) );
                                });
                        }
                }, 'json');
        });
        
        /*Nueva opcion Ambiente*/
        
        this.get( '#/ambientes', function( context ) {
                    context.callBaseTemplate({
                            template: 'ambientes',
                            menu: 'ambientes',
                            url: 'json/ambientes/ambientesLibresList.json',
                            data: {}
                    }).then( Ambientes.init );
             
        });
        
        
        /*Fin opcion Ambiente*/

        this.get( '#/docs', function() {
                this.redirect('#/docs/pendientes');
        });

        this.get( '#/docs/pendientes', function( context ) {
            context.verifyPlanActivo(currentPlan)
                .done(function() {
                    context.callBaseTemplate({
                            template: 'docs-pendientes',
                            menu: 'docs/pendientes',
                            url: 'json/documentooficial/pendientesList.json',
                            data: {
                                    idEdicionestudio: currentPlan
                            }
                    }).then( function() {

                            var $expandAllButton = $( '.expand-all' ),
                                    $collapseAllButton = $( '.collapse-all' ),
                                    $accordion = $( '.accordion' ),
                                    $uploadVoucher = $( '.upload-voucher' );

                            context.getPlanMenu();

                            $accordion.on( 'show', function( e ) {
                                    $( e.target ).prev().addClass( 'active' );
                            });

                            $accordion.on( 'hide', function( e ) {
                                    $( e.target ).prev().removeClass( 'active' );
                            });

                            $expandAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body' ).collapse( 'show' );
                            });

                            $collapseAllButton.on( 'click', function() {
                                    $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                            });

                            $uploadVoucher.on( 'click', function() {
                                    var id = $( this ).data( 'id' );

                                    // ir a la otra pagina
                                    context.redirect( '#/docs/voucher/' + id );
                            });
                    });
               });
        });

        this.get( '#/docs/finalizados', function( context ) {
                this.callBaseTemplate({
                        template: 'docs-finalizados',
                        menu: 'docs/pendientes',
                        url: 'json/documentooficial/finalizadosList.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                }).then( function() {

                        var $expandAllButton = $('.expand-all'),
                                $collapseAllButton = $('.collapse-all'),
                                $accordion = $('.accordion');

                        context.getPlanMenu();

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass( 'active' );
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });

                });
        });

        this.get( '#/docs/nuevo', function() {
                this.callBaseTemplate({
                        template: 'docs-nuevo',
                        menu: 'docs/pendientes',
                        url: 'json/documentooficial/nuevoDocumento.json',
                        data: {
                                idEdicionestudio: currentPlan
                        }
                });
        });

        // primero crea el documento, luego sube el voucher
        this.post( '#/docs/nuevo', function(context) {
                $.post( 'json/documentooficial/saveNuevoDocumento.json', $( '#new-doc' ).serialize(), function( data ) {
                        $submitDoc=$("#submit-doc");
                        $submitDoc.button('loading');
                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitDoc.button('reset');
                        }
                        else {
                                // el servidor devuelve el id del nuevo documento
                                // y ahora solicita subir el voucher
                                var createdDoc = data.docId;

                                context.redirect( '#/docs/voucher/' + createdDoc );
                                $( document ).one( 'template-loaded', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'La solicitud ha sido enviada para su revisi&oacute;n.' ) );
                                });
                        }
                }, 'json');
        });

        this.get( '#/docs/voucher/:id', function( context ) {
                var id = this.params.id;

                this.changeNavigation( 'docs' );
                $mainApp.removeClass().addClass( 'docs' );
                $pageTitle.text( 'Agregar voucher' );

                this.partial( 'templates/voucher.tpl' ).then( function() {
                        $( document ).trigger( 'template-loaded' );
                });

                yepnope({
                        load: [
                                'resources/js/vendor/jquery.ui.widget-1.10.1.min.js',
                                'resources/js/vendor/jquery.iframe-transport.1.6.1.min.js',
                                'resources/js/vendor/jquery.fileupload-5.31.min.js'
                        ],
                        complete: function() {
                                $( '#the-voucher' ).fileupload({
                                        limitConcurrentUploads: 1,
                                        url: 'json/documentooficial/uploadVoucher.json',
                                        dataType: 'json',
                                        formData: {id: id},
                                        add: function( e, data ) {
                                                $( this ).closest( '.btn' ).addClass( 'disabled' );
                                                $( this ).css( 'right', '-999em' );
                                                data.submit();
                                        },
                                        progressall: function ( e, data ) {
                                                var progress = parseInt( data.loaded / data.total * 100, 10 );
                                                $( '.progress .bar' ).css( 'width', progress + '%' );
                                        },
                                        done: function ( e, data ) {
                                                if ( data.result && data.result.resultado === 'ok' ) {
                                                        context.redirect( '#/docs' );
                                                        $( document ).one( 'template-loaded', function() {
                                                                $mainApp.prepend( context.createInfoMessage( 'Se ha guardado correctamente el voucher.' ) );
                                                        });
                                                }
                                                else {
                                                        $( this ).closest( '.btn' ).removeClass( 'disabled' );
                                                        $( this ).css( 'right', 0 );
                                                        $( '.progress .bar' ).css( 'width', 0 );
                                                        context.parseGeneralErrors( data.result.errores );
                                                }
                                        }
                                });
                        }
                });
        });

        this.get( '#/buscar', function() {
                this.redirect( '#/buscar/profesores' );
        });

        this.get( '#/buscar/profesores', function( context ) {
                var n = 
                                this.params.nombre !== undefined &&
                                this.params.nombre.length >= 3 &&
                                this.params.nombre.match(/^[a-zÃ¡Ã©Ã­Ã³ÃºÃ± ,.'-]+$/i)
                                        ? $.trim( this.params.nombre )
                                        : '',
                        p = 
                                this.params.paterno !== undefined &&
                                this.params.paterno.length >= 3 &&
                                this.params.paterno.match(/^[a-zÃ¡Ã©Ã­Ã³ÃºÃ± ,.'-]+$/i)
                                        ? $.trim( this.params.paterno )
                                        : '',
                        m = 
                                this.params.materno !== undefined &&
                                this.params.materno.length >= 3 &&
                                this.params.materno.match(/^[a-zÃ¡Ã©Ã­Ã³ÃºÃ± ,.'-]+$/i)
                                        ? $.trim( this.params.materno )
                                        : '';

                this.callBaseTemplate({
                        template: 'buscar-profesores',
                        menu: 'buscar/profesores',
                        title: 'Profesores'
                }).then( function() {
                        if ( n.length < 3 && p.length < 3 && m.length < 3 ) {
                                return false;
                        }

                        $( '#nombre' ).val( n );
                        $( '#apellidoP' ).val( p );
                        $( '#apellidoM' ).val( m );

                        $.post( 'json/buscar/buscarProfesor.json', $( '#search-people' ).serialize(), function( data ) {
                                if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                        context.parseGeneralErrors( data.errores );
                                }
                                else {
                                        context.renderTemplate({
                                                template: 'buscar-profesores-results',
                                                data: data
                                        }).replace( '.results' );
                                        context.scrollToElement( $( '.results') );
                                }
                        }, 'json');
                });
        });

        this.get( '#/buscar/asignaturas', function( context ) {
                this.callBaseTemplate({
                        template: 'buscar-asignaturas',
                        menu: 'buscar/profesores',
                        title: 'Buscar asignaturas'
                }).then( function() {

                        var emptyOption = '<option value="0">Todos</option>';

                        var $campus=$('#campus'),$facultad = $('#facultad'),$pa = $('#pa');

                        var campusOrigin = null,facultadOrigin = null;

                        // si hay un campus seleccionado
                        campusOrigin = typeof campusOrigin !== 'undefined' && campusOrigin !== null
                        ? campusOrigin : $campus.val();
                        $facultad.html( emptyOption );
                        $.post( 'json/buscar/centroAcademicoList.json?idCampus=' + campusOrigin).done( function( response ) {
                                var items = $.map( response.centroAcademicoList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                });
                                items.unshift( emptyOption );
                                // listar facultades
                                $facultad.html( items.join('') );

                                // si hay una facultad seleccionada
                                facultadOrigin = typeof facultadOrigin !== 'undefined' && facultadOrigin !== null
                                        ? facultadOrigin
                                        // sino toma la primera facultad mostrada
                                        : $facultad.val();

                        });

                        $pa.html( emptyOption );
                        // si hay una facultad seleccionada
                        if ( typeof facultadOrigin !== 'undefined' && facultadOrigin !== null ) {
                                // cargar lista de periodos academicos
                                $.post( 'json/buscar/peridoAcademicoList.json?idCentroAcademico=' + facultadOrigin )
                                        .done( function( response ) {
                                                var items = $.map( response.peridoAcademicoList, function( value ) {
                                                        return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                                });
                                                items.unshift( emptyOption );
                                                // listar periodos acedemicos
                                                $pa.html( items.join('') );
                                                });
                        }

                        // al cambiar de campus
                        $campus.on( 'change', function() {
                                var facultadPromise = $.post( 'json/buscar/centroAcademicoList.json?idCampus=' + $campus.val());

                                // eliminar inmediatamente
                                $facultad.html( emptyOption );
                                $pa.html( emptyOption );

                                facultadPromise.done( function( response ) {
                                        var items = $.map( response.centroAcademicoList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar facultades
                                        $facultad.html( items.join('') );
                                });
                        });

                        // al cambiar facultad
                        $facultad.on( 'change', function() {
                                var paPromise = $.post( 'json/buscar/peridoAcademicoList.json?idCentroAcademico=' + $facultad.val() );

                                // eliminar inmediatamente
                                $pa.html( emptyOption );

                                paPromise.done( function( response ) {
                                        var items = $.map( response.peridoAcademicoList, function( value ) {
                                                return '<option value="' + value.id + '">' + value.nombre + '</option>';
                                        });

                                        items.unshift( emptyOption );

                                        // listar distritos
                                        $pa.html( items.join('') );
                                });
                        });
                        var $form = $( '#search-subjects' );

                        $form.submit( function(e) {
                                $.post( 'json/buscar/buscarAsignatura.json', $form.serialize(), function( data ) {
                                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                                context.parseGeneralErrors( data.errores );
                                        } else {
                                                context.renderTemplate({
                                                        template: 'buscar-asignaturas-results',
                                                        data: data
                                                }).replace( '.results' );
                                                context.scrollToElement( $( '.results') );
                                        }
                                }, 'json');

                                return false;
                        });
                });
        });

        this.get('#/asignaturas/:initials/:id/:idseccion', function(context) {
                if ( ! this.params.initials.match( /^[a-z0-9]+$/i ) ) {
                        this.redirect( '#/' );
                        return false;
                }

                var id = this.params.id,
                        idseccion = this.params.idseccion;

                this.callBaseTemplate({
                    template: 'detalle-asignatura',
                    menu: 'buscar/profesores',
                    url: 'json/buscar/asignaturaDetalle.json',
                    data: {
                        idAsignaturaDictada: id,
                        idSeccion: idseccion
                    }
                }).then( function() {                            
                    $( '#subject-nav-tabs a' ).on( 'show', function( e ) {
                        var $tab = $( e.target ),
                                tabName = $tab.data( 'id' ),
                                $tabPane = $( $tab.data( 'target' ) );
                        if ( ! $tabPane.data( 'rendered' ) ) {
                            if(tabName === 'horario'){
                                context.renderTemplate({
                                        template: 'asignaturas-tab-' + tabName,                                                        
                                        data: null
                                }).replace( $tabPane ).then( function() {
                                    $.post( 'json/buscar/asignatura-horario.json', { idAsignaturaDictada: id, idSeccion: idseccion }, function( response ) {
                                        var html = '<a class="subject-block" href="#/asignaturas/' +
                                                response.asignatura.sigla + '/' + response.asignatura.id + '/' + response.asignatura.asignaturaSeccion.idSeccion + '">' +
                                                '<strong class="initials">' + response.asignatura.sigla + '</strong> (' + response.asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';

                                        response.asignatura.horarioList.forEach( function( block ) {
                                                $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.aula.nombre ) );
                                        });

                                        response.asignatura.practicaProgramadaList.forEach( function( block ) {
                                                $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                        });
                                    });
                                });
                            }else if(tabName === 'material'){
                                context.renderTemplate({
                                        template: 'asignaturas-tab-' + tabName,
                                        url: 'json/buscar/asignatura-' + tabName + '.json',
                                        data: {
                                                idAsignaturaDictada: id,
                                                idSeccion: idseccion
                                        }
                                }).replace( $tabPane ).then( function() {
                                    var $accordion = $tabPane.find( '.accordion' ),
                                            $expandAllButton = $tabPane.find( '.expand-all' ),
                                            $collapseAllButton = $tabPane.find( '.collapse-all' );

                                    $accordion.on( 'show', function( e ) {
                                            $( e.target ).prev().addClass( 'active' );
                                    });

                                    $accordion.on( 'hide', function( e ) {
                                            $( e.target ).prev().removeClass('active');
                                    });

                                    $expandAllButton.on( 'click', function() {
                                            $tabPane.find( '.accordion-body' ).collapse( 'show' );
                                    });

                                    $collapseAllButton.on( 'click', function() {
                                            $tabPane.find( '.accordion-body.in' ).collapse( 'toggle' );
                                    });

                                    $expandAllButton.click();
                                });
                             }
                             $tabPane.data( 'rendered', true);
                        }
                    });
                    $('a[data-target="#subject-tab-horario"]' ).click();	
                });
        });

        this.get( '#/profesores/:alias', function() {
                this.redirect( '#/profesores/' + this.params.alias + '/perfil' );
        });

        this.get( '#/profesores/:alias/perfil', function() {
                var idProfesor = this.params.alias;

                this.callBaseTemplate({
                        template: 'detalle-profesor-perfil',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/perfil.json',
                        data: {
                                idProfesor: idProfesor
                        }
                }).then( function() {
                        $.post( 'json/profesor/horario.json', { idProfesor: idProfesor }, function( response ) {
                                response.asignaturaList.forEach( function( asignatura ) {
                                        var html = '<a class="subject-block" href="#/asignaturas/' +
                                                asignatura.sigla + '/' + asignatura.id + '/' + asignatura.asignaturaSeccion.idSeccion + '">' +
                                                '<strong class="initials">' + asignatura.sigla + '</strong> (' + asignatura.asignaturaSeccion.nombreSeccion + ') (@Descripcion)</a>';

                                        asignatura.horarioList.forEach( function( block ) {
                                                $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.aula.nombre ) );
                                        });

                                        asignatura.practicaProgramadaList.forEach( function( block ) {
                                                $( '#pr-' + block.bloqueHorario.orden + '-' + block.dia.id )
                                                        .append( html.replace( '@Descripcion', block.grupoPractica.nombre ) );
                                        });
                                });

                                response.asesoriaList.forEach( function( block ) {
                                        var ubicacion = '';
                                        if(block.oficina !== null){
                                                ubicacion = "(" + block.oficina + " - " + block.edificio + ")";
                                        }
                                        var html = '<a class="subject-block" href="#/profesores/' + idProfesor + '/perfil">' +
                                                                '<strong class="initials">' + block.titulo + '</strong> ' + ubicacion + '</a>';

                                        $( '#ge-' + block.bloqueHorario.orden + '-' + block.dia.id ).append( html );
                                });
                        });
                });
        });

        this.get( '#/profesores/:alias/investigacion', function() {
                var idProfesor = this.params.alias;

                this.callBaseTemplate({
                        template: 'detalle-profesor-investigacion',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/investigacion.json',
                        data: {
                                idProfesor: idProfesor
                        }
                }).then( function() {
                        var $expandAllButton = $( '.expand-all' ),
                                $collapseAllButton = $( '.collapse-all' ),
                                $accordion = $( '.accordion' );

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });

                        $expandAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body' ).collapse( 'show' );
                        });

                        $collapseAllButton.on( 'click', function() {
                                $accordion.find( '.accordion-body.in' ).collapse( 'toggle' );
                        });
                });
        });

        this.get( '#/profesores/:alias/cv', function() {
                var idProfesor = this.params.alias;
                this.callBaseTemplate({
                        template: 'detalle-profesor-cv',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/cvProfesor.json',
                        data: {
                                idProfesor: idProfesor
                        }
                }).then( function() {
                        var $accordion = $( '.accordion' );

                        $accordion.on( 'show', function( e ) {
                                $( e.target ).prev().addClass( 'active' );
                        });

                        $accordion.on( 'hide', function( e ) {
                                $( e.target ).prev().removeClass('active');
                        });
                });
        });

                        this.get( '#/investigaciones/:id', function() {
                var backToAdviser = last_template.match(/detalle-profesor-\w+/i);

                this.callBaseTemplate({
                        template: 'investigacion',
                        menu: 'buscar/profesores',
                        url: 'data/page-investigacion.json'
                }).then( function() {

                        if ( backToAdviser ) {
                                $( '.btn-back' ).removeClass( 'hidden' );
                        }
                });
        });

        this.get( '#/investigacionesCurso/:id', function() {
                var backToAdviser = last_template.match(/detalle-profesor-\w+/i);
                                        var id = this.params.id;
                this.callBaseTemplate({
                        template: 'investigacion',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/investigacionCurso.json',
                        data: {
                                id: id
                        }
                }).then( function() {

                        if ( backToAdviser ) {
                                $( '.btn-back' ).removeClass( 'hidden' );
                        }
                });
        });

                        this.get( '#/investigacionArt/:id', function() {
                var backToAdviser = last_template.match(/detalle-profesor-\w+/i);
                                        var id = this.params.id;
                this.callBaseTemplate({
                        template: 'investigacion-articulo',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/investigacionArt.json',
                        data: {
                                id: id
                        }
                }).then( function() {

                        if ( backToAdviser ) {
                                $( '.btn-back' ).removeClass( 'hidden' );
                        }
                });
        });

                        this.get( '#/investigacionTesis/:id', function() {
                var backToAdviser = last_template.match(/detalle-profesor-\w+/i);
                                        var id = this.params.id;
                this.callBaseTemplate({
                        template: 'investigacion-tesis',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/investigacionTesis.json',
                        data: {
                                id: id
                        }
                }).then( function() {

                        if ( backToAdviser ) {
                                $( '.btn-back' ).removeClass( 'hidden' );
                        }
                });
        });

                        this.get( '#/investigacionLibro/:id', function() {
                var backToAdviser = last_template.match(/detalle-profesor-\w+/i);
                                        var id = this.params.id;
                this.callBaseTemplate({
                        template: 'investigacion-libro',
                        menu: 'buscar/profesores',
                        url: 'json/profesor/investigacionLibro.json',
                        data: {
                                id: id
                        }
                }).then( function() {

                        if ( backToAdviser ) {
                                $( '.btn-back' ).removeClass( 'hidden' );
                        }
                });
        });

        this.get( '#/intranet', function() {
                this.redirect( '#/intranet/mapa' );
        });

        this.get( '#/universidad', function() {
                this.callBaseTemplate({
                        template: 'universidad',
                        menu: 'universidad',
                        url: 'json/universidad/documentoList.json'
                })			
        });

        this.get( '#/intranet/mapa', function() {
                this.callBaseTemplate({
                        template: 'intranet-mapa',
                        menu: 'intranet',
                        title: 'Mapa del sitio'
                });
        });

        this.get( '#/intranet/manuales', function() {
                this.callBaseTemplate({
                        template: 'intranet-manuales',
                        menu: 'intranet',
                        title: 'Manuales'
                });
        });

        this.get( '#/opciones', function( context ) {
                this.callBaseTemplate({
                        template: 'opciones',
                        title: 'Opciones'
                }).then( function() {
                        var $form = $( '#settings' );

                        $form.submit( function(e) {
                                $.post( 'updatePassword.json', $form.serialize(), function( data ) {
                                        if ( $.isArray(data.errores) && data.errores.length > 0 ) {
                                                context.parseGeneralErrors( data.errores );
                                        }
                                        else {
                                                context.redirect( '#/resumen' );
                                                $( document ).one( 'template-loaded', function() {
                                                        $mainApp.prepend( context.createInfoMessage( 'Tu contrase&ntilde;a se ha cambiado correctamnte.' ) );
                                                });
                                        }
                                }, 'json');

                                return false;
                        });
                });
        });

        this.get( '#/consultas', function() {
                this.redirect('#/consultas/pendientes');
        });

        this.get( '#/consultas/pendientes', function() {

                this.callBaseTemplate({
                        template: 'consultas',
                        menu: 'consultas',
                        url: 'json/consulta/pendientesList.json'
                }).then( $.proxy( function() {

                        var $expandAllButton = $('.expand-all');
                        var $collapseAllButton = $('.collapse-all');
                        var $accordion = $('.accordion');

                        $('.nav-pills li').first().addClass('active');				

                        $accordion.on('show', function(e) {
                                var $element = $(e.target);

                                // marcar como activo
                                $element.prev().addClass('active');
                        });

                        $accordion.on('hide', function(e) {
                                var $element = $(e.target);
                                $element.prev().removeClass('active');
                        });

                        $expandAllButton.on('click', function() {
                                $accordion.find('.accordion-body').collapse('show');
                        });

                        $collapseAllButton.on('click', function() {
                                $accordion.find('.accordion-body.in').collapse('toggle');
                        });

                }, this ) );
        });

        this.get( '#/consultas/finalizados', function() {

                this.callBaseTemplate({
                        template: 'consultas',
                        menu: 'consultas',
                        url: 'json/consulta/finalizadosList.json'
                }).then( $.proxy( function() {

                        var $expandAllButton = $('.expand-all');
                        var $collapseAllButton = $('.collapse-all');
                        var $accordion = $('.accordion');

                        $('.nav-pills li').last().addClass('active');

                        $accordion.on('show', function(e) {
                                var $element = $(e.target);

                                // marcar como activo
                                $element.prev().addClass('active');
                        });

                        $accordion.on('hide', function(e) {
                                $(e.target).prev().removeClass('active');
                        });

                        $expandAllButton.on('click', function() {
                                $accordion.find('.accordion-body').collapse('show');
                        });

                        $collapseAllButton.on('click', function() {
                                $accordion.find('.accordion-body.in').collapse('toggle');
                        });

                }, this ) );
        });

        this.get( '#/consultas/nuevo', function() {

                this.callBaseTemplate( {
                        template: 'consultas-nuevo',
                        menu: 'consultas',
                        url: 'json/consulta/nuevo.json'
                }).then( function() {
                        $('#select-process .nav a').on('click', function(e) {
                                $('#solicitud-id').attr('value', $(this).data('id'));
                                $('#solicitud-nombre').attr('value', $(this).text());
                                $('#select-process').slideUp('fast');
                                $('#new-suggestion').slideDown('fast');
                                e.preventDefault();
                        });
                        $('#change-process').on('click', function(e) {
                                $('#select-process').slideDown('fast');
                                $('#new-process').slideUp('fast');
                                e.preventDefault();
                        });

                        $('button[rel="tooltip"]').tooltip();
                });
        });

        this.post( '#/consultas/nuevo', function( context ) {
                $.post( 'json/consulta/saveConsulta.json', $( '#new-suggestion' ).serialize(), function( data ) {
                        $submitConsulta=$("#submit-consulta");
                        $submitConsulta.button('loading');
                        if ( $.isArray( data.errores ) && data.errores.length > 0 ) {
                                context.parseGeneralErrors( data.errores );
                                $submitConsulta.button('reset');
                        }
                        else {
                                context.redirect( '#/consultas/pendientes' );
                                $ (document ).one( 'template-loaded ', function() {
                                        $mainApp.prepend( context.createInfoMessage( 'El mensaje ha sido enviado para su revisi&oacute;n.' ) );
                                });
                        }
                }, 'json');
        });                

        this.get( '#/cerrar-sesion', function() {
                window.location.href = 'j_spring_security_logout';
        });

});        

$(function() {

        var loadingIndicator = $( '#loading-indicator' ),
                errorIndicator = $( '#error-indicator' ),
                sessionIndicator = $( '#session-indicator' );

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
                $.xhrPool.length = 0;
        };

        $( document ).ajaxStart( function() {
                loadingIndicator.fadeIn( 'fast' );
        });

        $( document ).ajaxSuccess( function() {
                errorIndicator.fadeOut( 'fast' );
        });

        $( document ).ajaxComplete( function( event, jqXHR, ajaxOptions) {
                var index = $.xhrPool.indexOf( jqXHR );

                if (index > -1) {
                        $.xhrPool.splice(index, 1);
                }

                var ct = jqXHR.getResponseHeader( 'content-type' ) || '';

                if ( ct.indexOf( 'html' ) > -1 && jqXHR.status === 200 ) {
                        window.location.href = 'login.html?continue=' + window.location.hash.split( '#')[1];
                        sessionIndicator.fadeIn( 'fast' ).delay( 10000 ).fadeOut( 'fast' );
                }

                if ( $.xhrPool.length === 0 ) {
                        loadingIndicator.fadeOut( 'fast' );
                }
        });

        $( document ).ajaxError( function( event, jqXHR, ajaxSettings, thrownError ) {
                loadingIndicator.fadeOut( 'fast' );
                if ( thrownError !== 'abort' ) {
                        errorIndicator.fadeIn( 'fast' ).delay( 4000 ).fadeOut( 'fast' );
                }
                //app.clearTemplateCache();

                if ( jqXHR.status >= 400 ) {
                        app.notFound();
                }
        });

        $.post( 'json/asignatura/estudioVigenteList.json', function( data ) {
                if ( data.estudios && data.estudios.length > 0 ) {
                        currentPlan = data.estudios[0].id;
                        // inicia la aplicacion
                        app.run( '#/' );

                        $.timer(240000, function() {
                           notificationMessage();                           
                        });				
                }
                else {
                        alert( 'No se pudo cargar el plan de estudios.' );
                }
        });

        $( '#toggle-sidebar' ).on( 'click', function() {
                $( '#siga-sidebar, #siga-main' ).toggleClass( 'slided' );
        });

        $( 'a[rel=tooltip]' ).tooltip();

});

/* Utilidades - SIGA Alumno */
function notificationMessage(){
    var $notificationMessage = $('.notification-message');
    $.post('json/mensaje/nuevos.json', {}, function( response ) {
        var count = response.cantidadMensajes;
        $notificationMessage.text( count === 0 || count === '' ? '' : count);
    });
}

function notificationPension(){
    var $notificationPension = $('.notification-pension');
    
    $.post('json/pensiones/estadoMoroso.json', {idEdicionestudio: currentPlan}, function( response ) {
        if(response.moroso){
            var text_notif = '<i class="icon-warning-sign"></i>';            
            $notificationPension.html(text_notif);
            //$('i[rel=tooltip]').tooltip(); rel="tooltip" title="Bloqueo de notas por deuda"
        }else{
            $notificationPension.html("");
        }
    });
}

function notificationAsignatura(){
    var $notificationAsignatura = $('.notification-asignatura');
    
    $.post('json/asignatura/statusNotificacion.json', {idEdicionestudio: currentPlan}, function( response ) {
        if(response.showNotify){
            var text_notif = '<i class="icon-flag"></i>';            
            $notificationAsignatura.html(text_notif);
        }else{
            $notificationAsignatura.html("");
        }
    });
}