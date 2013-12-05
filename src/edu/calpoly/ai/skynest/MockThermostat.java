package edu.calpoly.ai.skynest;

public class MockThermostat {

   // The outside temperature equillibrium
   private static final double outsideTemp = 50;
   
   // A constant governing how quickly the temperature approches the target temp
   private static final double C = 0.0042;
   
   /** Gets the forecasted temperature */
   public static double getTemp(int time, int depTime,
    int arrTime, double targetTemp) {
      
      if (time < depTime || time > arrTime) {
         return targetTemp;
      }
      
      double tempDiff = targetTemp - outsideTemp;
      int depDiff = depTime - time;
      int arrDiff = time - arrTime;
      
      double depCo = Math.exp(C * depDiff) - 1;
      double arrCo = Math.exp(C * arrDiff) - 1;
      
      double temp = targetTemp + Math.max(depCo, arrCo) * tempDiff;
      
      return temp;
   }
}
