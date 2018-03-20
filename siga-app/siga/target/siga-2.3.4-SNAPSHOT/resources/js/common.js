/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function formatosInput(){
    $('body').on('keypress', '.textoNumero', function(event){
        var k = event.which;
        if(k>=48&&k<=57)
            ;
        else
            event.preventDefault();
    });
}

$(function() {
    formatosInput();
});
