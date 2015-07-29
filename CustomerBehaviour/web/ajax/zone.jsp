
<%@page import="java.text.DecimalFormat"%>
<%@page import="controllers.MainController"%>
<%

    String zona = request.getParameter("zona");
    
    
    
    if(zona !=null){
        MainController mc = MainController.getInstance();
        int raspuns = -1;
        
        //if (zona!=null){
            raspuns = mc.getTotalTimeByZone(Integer.parseInt(zona));
        //}
        
            System.out.println(raspuns);
      
        //if (raspuns!=-1)
            //out.print((double)raspuns/3600);
        
            
            //double d = (double)raspuns/3600;

//DecimalFormat newFormat = new DecimalFormat("#.##");
//double twoDecimal =  Double.valueOf(newFormat.format(d));

out.print(raspuns);
    }

   
   
    
%>