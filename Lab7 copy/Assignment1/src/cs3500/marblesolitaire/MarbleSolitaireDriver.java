package cs3500.marblesolitaire;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireView;

import java.util.Scanner;

/**
 * represents driver class for English Marble Solitaire game
 */
public class MarbleSolitaireDriver {
  /**
   * main method to run the game
   * @param args
   */
  public static void main(String[] args) {
    MarbleSolitaireModel model = new EnglishSolitaireModel();
    MarbleSolitaireView view = new MarbleSolitaireTextView(model);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to Marble Solitaire!");
    while (!model.isGameOver()) {
      System.out.println(view.toString());
      System.out.println("Score: " + model.getScore());
      System.out.println("Enter your move (fromRow fromCol toRow toCol): ");
      int fromRow = scanner.nextInt();
      int fromCol = scanner.nextInt();
      int toRow = scanner.nextInt();
      int toCol = scanner.nextInt();

      try {
        model.move(fromRow, fromCol, toRow, toCol);
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid move: " + e.getMessage());
      }
    }

    System.out.println("Game over!");
    System.out.println("Final board:");
    System.out.println(view.toString());
    System.out.println("Final score: " + model.getScore());
  }
}
