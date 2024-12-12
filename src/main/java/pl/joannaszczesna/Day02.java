package pl.joannaszczesna;
/*
--- Day 2: Red-Nosed Reports ---
The unusual data (your puzzle input) consists of many reports, one levels per line. Each levels is a list of levels called levels that are separated by spaces. For example:

7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
This example data contains six reports each containing five levels.

The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a levels only counts as safe if both of the following are true:

The levels are either all increasing or all decreasing.
Any two adjacent levels differ by at least one and at most three.
In the example above, the reports can be found safe or unsafe by checking those rules:

7 6 4 2 1: Safe because the levels are all decreasing by 1 or 2.
1 2 7 8 9: Unsafe because 2 7 is an increase of 5.
9 7 6 2 1: Unsafe because 6 2 is a decrease of 4.
1 3 2 4 5: Unsafe because 1 3 is increasing but 3 2 is decreasing.
8 6 4 4 1: Unsafe because 4 4 is neither an increase or a decrease.
1 3 6 7 9: Safe because the levels are all increasing by 1, 2, or 3.
So, in this example, 2 reports are safe.

Analyze the unusual data from the engineers. How many reports are safe?

--- Part Two ---
The engineers are surprised by the low number of safe reports until they realize they forgot
 to tell you about the Problem Dampener.

The Problem Dampener is a reactor-mounted module that lets the reactor safety systems tolerate a single bad level
 in what would otherwise be a safe levels. It's like the bad level never happened!

Now, the same rules apply as before, except if removing a single level from an unsafe levels would make it safe,
 the levels instead counts as safe.

More of the above example's reports are now safe:

7 6 4 2 1: Safe without removing any level.
1 2 7 8 9: Unsafe regardless of which level is removed.
9 7 6 2 1: Unsafe regardless of which level is removed.
1 3 2 4 5: Safe by removing the second level, 3.
8 6 4 4 1: Safe by removing the third level, 4.
1 3 6 7 9: Safe without removing any level.
Thanks to the Problem Dampener, 4 reports are actually safe!

Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports.
 How many reports are now safe?
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day02 {

  static long countSafeReports(List<String> inputLines) {
    List<Report> reports = parseInputToReports(inputLines);
    return reports.stream().filter(Day02::isReportSafe).count();
  }

  static long countSafeReportsWithOneToleration(List<String> inputLines) {
    List<Report> reports = parseInputToReports(inputLines);
    return reports.stream().filter(Day02::isReportSafeWithOneToleration).count();
  }

  static boolean isReportSafeWithOneToleration(Report report) {
    List<Integer> levels = new ArrayList<>(report.levels);

      return IntStream.range(0, levels.size())
              .mapToObj(i -> makeReportWithoutLevel(i, levels))
              .anyMatch(Day02::isReportSafe);
  }

  private static Report makeReportWithoutLevel(int i, List<Integer> levels) {
    return new Report(IntStream.range(0, levels.size())
            .mapToObj(j -> new Pair(j, levels.get(j)))
            .filter(p -> p.index != i)
            .map(p -> p.value)
            .toList()
    );
  }

  private static Report makeReportWithoutLevelMix(int index, List<Integer> levels) {
    List<Integer> newReport = new ArrayList<>(levels);
    newReport.remove(index);
    return new Report(newReport);
  }

  private static List<Report> parseInputToReports(List<String> inputLines) {
    return inputLines.stream()
      .map(line -> Arrays.stream(
          line.split("\\s+"))
        .map(Integer::parseInt)
        .toList())
      .map(Report::new)
      .toList();
  }

  private static boolean isReportSafe(Report report) {
    return isReportMonotonic(new ArrayList<>(report.levels()));
  }

  private static boolean isReportMonotonic(List<Integer> report) {
    List<Integer> reversed = new ArrayList<>(report.reversed());
    return checkMonotonic(report) || checkMonotonic(reversed);
  }

  private static boolean checkMonotonic(List<Integer> report) {
    return IntStream.range(1, report.size())
      .allMatch(i ->
        (report.get(i) - report.get(i - 1)) <= 3
          &&
          (report.get(i) - report.get(i - 1)) >= 1);
  }

  record Report(List<Integer> levels) {
  }

  record Pair(int index, Integer value) {
  }
}
