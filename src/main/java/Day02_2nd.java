import java.util.List;


public class Day02_2nd {

   public static void main( String[] args ) {
//      secondPart(Util.getAoCTestInput("Day02"));
      secondPart(Util.getAoCInput("Day02"));
   }

   private static void secondPart( List<String> fileAsStrings ) {
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
      int aim;

      @Override
      public String toString() {
         return "Position{" + "depth=" + depth + ", horizontal=" + horizontal + ", aim=" + aim + '}';
      }
   }

   enum Direction {
      forward{ // increases the horizontal position by X units.
         @Override
         public void operate(final Position position, int number ) {
           position.horizontal += number;
           position.depth += position.aim * number;
         }
      },
      down{ // increases the depth by X units.
         @Override
         public void operate( Position position, int number ) {
            position.aim += number;
         }
      },
      up{ // decreases the depth by X units
         @Override
         public void operate( Position position, int number ) {
            position.aim -= number;
         }
      };

      public void operate( Position position, int number ){}
   }

}
