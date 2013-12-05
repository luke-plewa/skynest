package edu.calpoly.ai.skynest;

public class MockThermostat {

   // The outside temperature equillibrium
   private static final double outsideTemp = 50;
   
   // A constant governing how quickly the temperature approches the target temp
   private static final double C = 0.05;
   
   /** Gets the forecasted temperature */
   public static double getTemp(int time, int arrivalTime, double targetTemp, boolean arrivalFlag) {
   
      // If the time is after the arrival time, the nest is on
      if(arrivalFlag)
      {
    	  if (time > arrivalTime) {
    		  return targetTemp;
          }
      }
      else 
      {
    	  if (time < arrivalTime) {
    		  return targetTemp;
          }
      }
      
      // Produces values close to 0 if we're just before the arrival time
      // and values approaching -1 for times much earlier than the arrival time
      double tempCoefficient = Math.exp(C * (time - arrivalTime)) - 1;
      
      // Calculates a temperature between outside and target temperatures, based
      // on the time to go before the scheduled arrival time
      double temp = targetTemp + tempCoefficient * (targetTemp - outsideTemp);
      return temp;
   }
}
