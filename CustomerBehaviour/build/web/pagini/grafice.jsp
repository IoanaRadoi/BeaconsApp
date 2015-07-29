<%-- 
    Document   : grafice
    Created on : Jun 30, 2015, 7:36:39 PM
    Author     : Oana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>   
    <head>
    <title>Auto-adjust</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
      body {
        text-align: center;
      }
      
      #g1,#g2, #g3, #g4 {
        width:400px; height:320px;
        display: inline-block;
        margin: 1em;
      }
      
    
      
      p {
        display: block;
        width: 450px;
        margin: 2em auto;
        text-align: left;
      }
      
      #display{
          display: inline-block;
          
      }
    </style>
    
    <script src="resources/js/raphael.2.1.0.min.js"></script>
    <script src="resources/js/justgage.1.0.1.min.js"></script>
    <script language="JavaScript" type="text/javascript" src="scripts/main.js"></script>
    <script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
        <script type="text/javascript" src="scripts/main.js"></script>
    
    <script language="JavaScript" type="text/javascript">
      var g1, g2, g3, g4;
      
      window.onload = function(){
        var g1 = new JustGage({
          id: "g1", 
          value: getHours(1),
          min: 0,
          max: 3600,
          title: "Zona 0",
          label: "Secunde"
        });
        
        
        var g2 = new JustGage({
          id: "g2", 
          value: getHours(2),
          min: 0,
          max: 3600,
          title: "Zona 1",
          label: "Secunde"
        });
        
        var g3 = new JustGage({
          id: "g3", 
          value: getHours(3),
          min: 0,
          max: 3600,
          title: "Zona 2",
          label: "Secunde"
        });        

        setInterval(function() {
          g1.refresh(getHours(1));
          g2.refresh(getHours(2));
          g3.refresh(getHours(3));
        }, 2500);
      };
    </script>

	</head>
  <body>   
      <div id="display">
    <div id="g1"></div>
    <div id="g2"></div>
    <div id="g3"></div>
   </div>   

  </body>
</html>
