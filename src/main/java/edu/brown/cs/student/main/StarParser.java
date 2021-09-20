package edu.brown.cs.student.main;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.zip.DataFormatException;

public class StarParser {
  private ArrayList<String[]> data;

  /* This method is responsible for taking the data from the given file and loading it into the
  data ArrayList. The data is stored in the format of a String array inside an ArrayList so that
  each element of the ArrayList corresponds to all the data of a certain star.*/
  public void parseFileData(String filename) {
    File starsFile = new File("data/stars/" + filename);
    this.data = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(starsFile))) {
      String input;
      int lineCount = 0;
      String[] firstList = {"StarID", "ProperName", "X", "Y", "Z"};
      while ((input = br.readLine()) != null) {
        lineCount += 1;
        String[] line = input.split(",");
        if (lineCount == 1 && !line[0].equals(firstList[0]) && !line[1].equals(firstList[1])
            && !line[2].equals(firstList[2]) && !line[3].equals(firstList[3])
            && !line[4].equals(firstList[4])) {
          throw new DataFormatException("ERROR: The data has not been provided in a valid format");
        } else if (lineCount != 1) {
          this.data.add(line);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ERROR: Invalid input for REPL");
    }
    System.out.println("Read " + data.size() + " stars from data/stars/" + filename);
  }
  /* This method is responsible for handling user input for searching for the nearest star with
  the arguments k, x, y, z. It uses a helper method to actually do the searching, then processes
  the data here to be returned in the correct format.*/
  public String[] naiveNeighbors(int k, double x, double y, double z) {
    ArrayList<String[]> closestEntries = closestStarSearch(k, x, y, z, null);
    String[] closestStars = new String[closestEntries.size()];
    for (int i = 0; i < closestEntries.size(); i++) {
      closestStars[i] = closestEntries.get(i)[0];
    }
    return closestStars;
  }
  /* This method is responsible for handling user input for searching for the nearest star with
  the arguments k, starName. It uses a helper method to actually do the searching, then processes
  the data here to be returned in the correct format.*/
  public String[] naiveNeighbors(int k, String starName) {
    double[] starPosition = findStarName(starName);
    double x = starPosition[0];
    double y = starPosition[1];
    double z = starPosition[2];
    ArrayList<String[]> closestEntries = closestStarSearch(k, x, y, z, starName);
    String[] closestStars = new String[closestEntries.size()];
    for (int i = 0; i < closestEntries.size(); i++) {
      closestStars[i] = closestEntries.get(i)[0];
    }
    return closestStars;
  }
  /* This method is responsible for finding a star based on a given name. To do this, it searches
  through the data in an attempt to find the given star. If the star is found. The X, Y, Z position
  of that star is returned. Otherwise, an illegal argument exception is thrown, indicating the
  given star could not be found in the list.*/
  public double[] findStarName(String starName) {
    double[] starPosition = new double[3];
    for (String[] line : this.data) {
      if (line[1].equals(starName)) {
        starPosition[0] = Double.parseDouble(line[2]);
        starPosition[1] = Double.parseDouble(line[3]);
        starPosition[2] = Double.parseDouble(line[4]);
        return starPosition;
      }
    }
    throw new IllegalArgumentException("ERROR: Requested star could not be found");
  }
  /* This method is responsible for performing the naive search algorithm. Comments below will
  explain in more detail, but the point of this algorithm is to search for the k closest stars to
  a given position by comparing that to the other stars loaded in data. A simple distance formula
  is used to compare the two, and the data is stored in an ArrayList of Strings containing the
  name and the distance of the given point to that star.*/
  public ArrayList<String[]> closestStarSearch(int k, double x, double y, double z,
                                               String starName) {
    ArrayList<String[]> closestEntries = new ArrayList<>();
    //This prevents out of bounds issues with a k that is too large
    if (k > this.data.size()) {
      k = this.data.size();
      System.out.println("Given k value was greater than data set. K will be set to the size of "
          + "the data");
    }
    int max = Integer.MAX_VALUE;
    //Arraylist is initaliazed with Integer Max values as a safe comparison
    String[] blank = {"-1", String.valueOf(max)};
    for (int i = 0; i < k; i++) {
      closestEntries.add(blank);
    }
    for (String[] line : this.data) {
      //Prevents adding the named star to the list of closest stars
      if (line[1].equals(starName)) {
        continue;
      }
      double lineX = Double.parseDouble(line[2]);
      double lineY = Double.parseDouble(line[3]);
      double lineZ = Double.parseDouble(line[4]);
      //distance calculations
      double distance = Math.sqrt(Math.pow((lineX - x), 2.0) + Math.pow((lineY - y), 2.0)
          + Math.pow((lineZ - z), 2.0));
      for (int j = 0; j < k; j++) {
        //puts the Star name and distance in the list if the distance is smaller than existing entry
        if (distance < Double.parseDouble(closestEntries.get(j)[1])) {
          String[] entry = {line[0], String.valueOf(distance)};
          closestEntries.set(j, entry);
          break;
        //handles edge case where 2 stars are tied for furthest distance that belongs in list
        } else if (j == k - 1 && distance < Double.parseDouble(closestEntries.get(j)[1])) {
          double random = Math.random();
          if (random > 1f / 2f) {
            String[] entry = {line[1], String.valueOf(distance)};
            closestEntries.set(j, entry);
          }
        }
      }
    }
    return closestEntries;
  }
  //A simple getter for the data. Used only in testing.
  public ArrayList<String[]> getData() {
    return this.data;
  }
}
