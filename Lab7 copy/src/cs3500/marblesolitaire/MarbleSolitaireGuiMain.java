package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.SwingGuiController;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;
import cs3500.marblesolitaire.view.SwingGuiView;
import cs3500.marblesolitaire.view.TriangleSolitaireTextView;
import cs3500.marblesolitaire.view.EuropeanSolitaireTextView;
import cs3500.marblesolitaire.view.MarbleSolitaireGuiView;

import java.io.IOException;
import java.util.Scanner;

/**
 * Main class for the Marble Solitaire game.
 */
public class MarbleSolitaireGuiMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the game type (english, european, or triangular):");
        String gameType = scanner.nextLine().trim().toLowerCase();

        MarbleSolitaireModel model = null;
        MarbleSolitaireGuiView view = null;

        int size = 3;
        int emptyRow = -1;
        int emptyCol = -1;

        System.out.println("Enter the size of the board (default 3):");
        if (scanner.hasNextInt()) {
            size = scanner.nextInt();
        }
        scanner.nextLine(); // consume the remaining newline

        System.out.println("Enter the initial empty position row (default -1 for center):");
        if (scanner.hasNextInt()) {
            emptyRow = scanner.nextInt();
        }
        scanner.nextLine(); // consume the remaining newline

        System.out.println("Enter the initial empty position column (default -1 for center):");
        if (scanner.hasNextInt()) {
            emptyCol = scanner.nextInt();
        }
        scanner.nextLine(); // consume the remaining newline

        switch (gameType) {
            case "english":
                model = new EnglishSolitaireModel(size, emptyRow, emptyCol);
                view = new SwingGuiView(model);
                break;
            case "european":
                model = new EuropeanSolitaireModel(size, emptyRow, emptyCol);
                view = new EuropeanSolitaireTextView(model);
                break;
            case "triangular":
                model = new TriangleSolitaireModel(size, emptyRow, emptyCol);
                view = new TriangleSolitaireTextView(model);
                break;
            default:
                System.out.println("Invalid game type");
                return;
        }

        if (view instanceof SwingGuiView) {
            new SwingGuiController(model, (SwingGuiView) view);
        } else {
            try {
                playTextGame(model, view);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void playTextGame(MarbleSolitaireModel model, MarbleSolitaireGuiView view) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (!model.isGameOver()) {
            view.renderBoard();
            view.renderMessage("\nEnter move (fromRow fromCol toRow toCol): ");

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine().trim();
                String[] inputs = input.split("\\s+");

                if (inputs.length == 4) {
                    try {
                        int fromRow = Integer.parseInt(inputs[0]) - 1;
                        int fromCol = Integer.parseInt(inputs[1]) - 1;
                        int toRow = Integer.parseInt(inputs[2]) - 1;
                        int toCol = Integer.parseInt(inputs[3]) - 1;
                        model.move(fromRow, fromCol, toRow, toCol);
                    } catch (IllegalArgumentException e) {
                        view.renderMessage("Invalid move. Try again.\n");
                    }
                } else {
                    view.renderMessage("Invalid input. Enter exactly four numbers.\n");
                }
            }
        }

        view.renderBoard();
        view.renderMessage("\nGame Over!\n");
        scanner.close();
    }
}