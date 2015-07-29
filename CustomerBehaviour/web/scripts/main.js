/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getHours(zona){ 
    
    var jqXHR =  $.ajax({
        type: 'post',
        url: 'ajax/zone.jsp',
        async: false,
        data: {zona: zona},        

    });   
    return parseFloat(jqXHR.responseText);
    
             
   
    
}



