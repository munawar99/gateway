package com.jpmorgan.asset.gateway;

public class Factory {
	 
   public static Gateway getGateway(String type){
	   
      if (type == null){
         return null;
      }
      
      if (type.equalsIgnoreCase("messageGateway")){
         return new MessageGateway();
      }
      
      return null;
   }
   
}