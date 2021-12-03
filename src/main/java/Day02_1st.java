import java.util.List;


public class Day02_1st {

   public static void main( String[] args ) {
//      first(Util.getAoCTestInput(Day02.class.getName()));
      first(Util.getAoCInput(Day02_1st.class.getName()));
   }

   private static void first( List<String> fileAsStrings ) {
      final Position position = new Position();
     for (String string : fileAsStrings) {
        final String[] split = string.split("\\W");
        if ( split.length != 2  ) {
           throw new IllegalStateException("Illegal format for line: " + string);
        }
        Direction.valueOf(split[0]).operate(position, Integer.parseInt(split[1]));
     }
      System.out.println(position + " = " + position.horizontal * position.depth);
   }

   private static class Position {
      int depth;
      int horizontal;

      @Override
      public String toString() {
         return "Position{" + "depth=" + depth + ", horizontal=" + horizontal + '}';
      }
   }

   enum Direction {
      forward{ // increases the horizontal position by X units.
         @Override
         public void operate( Position position, int number ) {
           position.horizontal += number;
         }
      },
      down{ // increases the depth by X units.
         @Override
         public void operate( Position position, int number ) {
            position.depth += number;
         }
      },
      up{ // decreases the depth by X units
         @Override
         public void operate( Position position, int number ) {
            position.depth -= number;
         }
      };

      public void operate( Position position, int number ){}
   }

}
