/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    
    $("value='editar'").click(function(){
       var fields = $("[name]");
    
        var parametros = "";
    
        for(var i = 0;i<fields.length();i++){
            parametros += $(fields[i]).attr("name")+":"+$(fields[i]).val()+";"; 
        }
    
        $.post("GenericEditServlet",parametros,
        function(data){
            $("#mensagem").html(data);
            $("#modal-mensagem").modal("show");
        }); 
        });
    
    
    
});
