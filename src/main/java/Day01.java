import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import util.Util;


// --- Day 1: Sonar Sweep ---
public class Day01 {


   public static void main( String[] args ) {
      System.out.println("Hello World");
      firstPart();

//      secondPart(util.Util.getAoCTestInputAsInts(Day01.class.getName()));
      secondPart(Util.getAoCInputAsInts(Day01.class.getName()));
   }

   private static void firstPart() {
      final int x = countIncrements(getInput());
      System.out.println(x -1);// The first increment doesn't count in this case
   }

   private static List<Integer> getInput() {
      return Util.getAoCInputAsInts(Day01.class.getName());
   }

   private static int countIncrements( List<Integer> input ) {
      final AtomicInteger _incrementCounter = new AtomicInteger(0);
      final IncrementCounterConsumer consumer = new IncrementCounterConsumer(_incrementCounter);
      input.stream().forEach(consumer);
      return _incrementCounter.get();
   }

   private static void secondPart( List<Integer> ints ) {
      final List<Integer> sumList = new ArrayList<>();
      for ( int i = 0; i +2 < ints.size(); i++ ) {
         sumList.add(ints.get(i) + ints.get(i + 1) + ints.get(i + 2));
      }
      final int x = countIncrements(sumList);
      System.out.println(x -1);// The first increment doesn't count in this case
   }

   private static class IncrementCounterConsumer implements Consumer<Integer> {

      private final AtomicInteger _incrementCounter;
      private final AtomicInteger _lastInteger = new AtomicInteger(0);

      private IncrementCounterConsumer( AtomicInteger incrementCounter ) {_incrementCounter = incrementCounter;}

      @Override
      public void accept( Integer integer ) {
            if ( _lastInteger.get() < integer ) {
               _incrementCounter.incrementAndGet();
            }
            _lastInteger.set(integer);
      }
   }

}
