/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var form_validation = {
    $form : null
    ,rules : null
    ,messages : null
    ,init: function(){
        args = arguments[0] || {};
        
        if(args.form !== null && args.rules !== null && args.messages !== null){
            this.$form = $(args.form);
            this.rules = args.rules;
            this.messages = args.messages;
        }else{
            return;
        }
        
        this.validar();
    }
    ,validar : function(){
        this.$form.validate({
            rules: form_validation.rules
            ,messages: form_validation.messages
            ,errorClass:'validation_error'
            ,errorPlacement: function(error, element) {
                if($(element).nextAll('.beforeError').length>0){
                    $(element).nextAll('.beforeError').after(error);
                }else{
                    $(element).after(error);
                }
            }
            ,debug: false
//            ,submitHandler: function(form){
//                $(form).submit();
//            }
        });
    }
};