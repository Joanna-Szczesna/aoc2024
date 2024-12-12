package pl.joannaszczesna;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day02Test {
  @Test
  void part1_sample() throws URISyntaxException, IOException {
    assertEquals(2, Day02.countSafeReports(readInput("day02_sample")));
  }

  @Test
  void part1_input() throws URISyntaxException, IOException {
    assertEquals(686, Day02.countSafeReports(readInput("day02_input")));
  }

  @Test
  void part2_sample() throws URISyntaxException, IOException {
    assertEquals(4, Day02.countSafeReportsWithOneToleration(readInput("day02_sample")));
  }
  @Test
  void part2_input() throws URISyntaxException, IOException {
    assertEquals(717, Day02.countSafeReportsWithOneToleration(readInput("day02_input")));
  }

  @Test
  void part2_myExample() {
    Day02.Report broken = new Day02.Report(List.of(25,23,21,18,17,20));
    assertTrue(Day02.isReportSafeWithOneToleration(broken));
  }


  private List<String> readInput(String fileName) throws URISyntaxException, IOException {
    return Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
  }
}
