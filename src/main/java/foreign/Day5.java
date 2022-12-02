package foreign;

import static jdk.jshell.execution.Util.*;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import util.Util;


record Coordinate(int x, int y) {
      }

class CoordinateSystem {

   // coordinate system that stores the amount of overlaps per coordinate
   public final List<List<Integer>> coordinateOverlaps = Lists.newArrayList();

   public void increaseCoordinateOverlap(final Coordinate coordinate) {
      // make sure the coordinate system has enough X lists
      while (coordinateOverlaps.size() <= coordinate.x()) {
         coordinateOverlaps.add(Lists.newArrayList());
      }

      // make sure the coordinate system has all Y values initialized
      while (coordinateOverlaps.get(coordinate.x()).size() <= coordinate.y()) {
         coordinateOverlaps.get(coordinate.x()).add(0);
      }

      int currentValue = coordinateOverlaps.get(coordinate.x()).get(coordinate.y());
      coordinateOverlaps.get(coordinate.x()).set(coordinate.y(), currentValue + 1);
   }

   public int getCoordinateOverlaps(final Integer minimumOverlap) {
      final List<Integer> collect = coordinateOverlaps.stream()
            .flatMap(Collection::stream)
            .filter(overlap -> overlap >= minimumOverlap)
            .collect(Collectors.toList());
      return collect.size(); // 5442
   }
}

public class Day5 {

   enum CalculationMode {
      HORIZONTAL_VERTICAL,
      HORIZONTAL_VERTICAL_DIAGONAL
   }

   public static void main( String[] args ) {
//      final List<String> input = Util.getAoCTestInput("Day05");
      final List<String> input = Util.getAoCInput("Day05");
      Day5 bla = new Day5();
      final List<ImmutablePair<Coordinate, Coordinate>> immutablePairs = bla.parseFromToCoordinates(input);
      final int x = bla.calculateCoordinateOverlaps(immutablePairs, CalculationMode.HORIZONTAL_VERTICAL);
      assert x == 5442;
      System.out.println(x);
   }

   /**
    * Expected line format: "FromX,FromY -> ToX,ToY"
    */
   public List<ImmutablePair<Coordinate, Coordinate>> parseFromToCoordinates(final List<String> lines) {
      return lines.stream()
            .map(line -> ImmutablePair.of(
                  new Coordinate(
                        Integer.parseInt(line.split(" -> ")[0].split(",")[0]),
                        Integer.parseInt(line.split(" -> ")[0].split(",")[1])),
                  new Coordinate(
                        Integer.parseInt(line.split(" -> ")[1].split(",")[0]),
                        Integer.parseInt(line.split(" -> ")[1].split(",")[1]))))
            .toList();
   }

   public int calculateCoordinateOverlaps(final List<ImmutablePair<Coordinate, Coordinate>> fromToCoordinates, final CalculationMode calculationMode) {
      final CoordinateSystem coordinateSystem = new CoordinateSystem();

      fromToCoordinates.forEach(fromToCoordinate -> {
         var from = fromToCoordinate.getLeft();
         var to = fromToCoordinate.getRight();

         if (from.x() < to.x()) {
            // from left to right
            // horizontal
            if (from.y() == to.y()) {
               for (int fromX = from.x(); fromX <= to.x(); fromX++) {
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y()));
               }
            }

            if (calculationMode != CalculationMode.HORIZONTAL_VERTICAL_DIAGONAL || !isDiagonal45Degrees(from, to)) {
               return;
            }

            // diagonal
            int round = 0;
            for (int fromX = from.x(); fromX <= to.x(); fromX++) {
               if (from.y() < to.y()) {
                  // from up to down
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y() + round));
               } else {
                  // from down to up
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y() - round));
               }
               round++;
            }
         } else if (from.x() > to.x()) {
            // from right to left
            // horizontal
            if (from.y() == to.y()) {
               for (int fromX = from.x(); fromX >= to.x(); fromX--) {
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y()));
               }
            }

            if (calculationMode != CalculationMode.HORIZONTAL_VERTICAL_DIAGONAL || !isDiagonal45Degrees(from, to)) {
               return;
            }

            // diagonal
            int round = 0;
            for (int fromX = from.x(); fromX >= to.x(); fromX--) {
               if (from.y() < to.y()) {
                  // from up to down
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y() + round));
               } else {
                  // from down to up
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(fromX, from.y() - round));
               }
               round++;
            }
         } else {
            // vertical
            if (from.y() < to.y()) {
               for (int fromY = from.y(); fromY <= to.y(); fromY++) {
                  // from up to down
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(from.x(), fromY));
               }
            } else if (from.y() > to.y()) {
               for (int fromY = from.y(); fromY >= to.y(); fromY--) {
                  // from down to up
                  coordinateSystem.increaseCoordinateOverlap(new Coordinate(from.x(), fromY));
               }
            }
         }
      });

      return coordinateSystem.getCoordinateOverlaps(2);
   }

   /**
    * Check if both points are on a diagonal line of exactly 45 degrees.
    * <p>
    * v
    */
   private boolean isDiagonal45Degrees(final Coordinate from, final Coordinate to) {
      return Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y());
   }
}