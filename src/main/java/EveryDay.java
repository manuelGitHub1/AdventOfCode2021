import java.util.List;

import util.Util;


public class EveryDay {

   boolean testInput = false;

   protected List<String> input(){
      final String name = this.getClass().getName();
      if ( testInput ) {
         return Util.getAoCTestInput(name);
      }
      return Util.getAoCInput(name);
   }

}
