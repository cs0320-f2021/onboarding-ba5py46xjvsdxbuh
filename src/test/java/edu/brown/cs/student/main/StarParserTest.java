package edu.brown.cs.student.main;

import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StarParserTest {
  StarParser parser = new StarParser();

  @Test
  public void testClosestSinglePositionedStar() {
    this.parser.parseFileData("three-star.csv");
    assertEquals(this.parser.naiveNeighbors(1, 0, 0,0)[0], "1");
  }
  @Test
  public void testClosestTwoPositionedStars() {
    this.parser.parseFileData("three-star.csv");
    String[] stars = this.parser.naiveNeighbors(2, 0, 0,0);
    assertEquals(stars[0], "1");
    assertEquals(stars[1], "2");
  }
  @Test
  public void testKGreaterThanStars() {
    this.parser.parseFileData("three-star.csv");
    String[] stars = this.parser.naiveNeighbors(10, 0, 0,0);
    assertEquals(stars[0], "1");
    assertEquals(stars[1], "2");
    assertEquals(stars[2], "3");
    assertEquals(stars.length, 3);
  }
  @Test
  public void testDataSizeSmall() {
    this.parser.parseFileData("three-star.csv");
    assertEquals(parser.getData().size(), 3);
  }
  @Test
  public void testDataSizeLarge() {
    this.parser.parseFileData("stardata.csv");
    assertEquals(parser.getData().size(), 119617);
  }
  @Test
  public void testSimpleStarNeighbor() {
    this.parser.parseFileData("stardata.csv");
    assertEquals(parser.naiveNeighbors(1, 0,0,0)[0], "0");
  }
  @Test
  public void testNoNeighbor() {
    this.parser.parseFileData("stardata.csv");
    assertEquals(parser.naiveNeighbors(0, 0,0,0).length, 0);
  }
  @Test
  public void testBlankNameReturn() {
    this.parser.parseFileData("ten-star.csv");
    assertEquals(parser.naiveNeighbors(1, 282, 0, 5)[0], "1");
  }

}
