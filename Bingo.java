import java.util.Scanner;

class Card {
	static final int SIZE = 5;

	static class MarkResult {
		private final boolean found;
		private final boolean bingo;

		MarkResult(boolean found, boolean bingo) {
			this.found = found;
			this.bingo = bingo;
		}

		boolean wasFound() {
			return found;
		}

		boolean hasBingo() {
			return bingo;
		}
	}

	static class Cell {
		private final int number;
		private boolean marked;

		Cell(int number) {
			this.number = number;
			this.marked = false;
		}

		int getNumber() {
			return number;
		}

		boolean isMarked() {
			return marked;
		}

		void setMarked(boolean marked) {
			this.marked = marked;
		}
	}

	private final Cell[][] grid;

	Card(int[][] numbers) {
		if (numbers.length != SIZE) {
			throw new IllegalArgumentException("Card must have exactly 5 rows.");
		}

		this.grid = new Cell[SIZE][SIZE];

		for (int row = 0; row < SIZE; row++) {
			if (numbers[row].length != SIZE) {
				throw new IllegalArgumentException("Each row must have exactly 5 columns.");
			}

			for (int col = 0; col < SIZE; col++) {
				grid[row][col] = new Cell(numbers[row][col]);
			}
		}
	}

	boolean markNumber(int number) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (grid[row][col].getNumber() == number) {
					grid[row][col].setMarked(true);
					return true;
				}
			}
		}

		return false;
	}

	MarkResult markAndCheckBingo(int number) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (grid[row][col].getNumber() == number) {
					grid[row][col].setMarked(true);
					return new MarkResult(true, hasBingoAt(row, col));
				}
			}
		}

		return new MarkResult(false, false);
	}

	int getNumberAt(int row, int col) {
		validateIndex(row, col);
		return grid[row][col].getNumber();
	}

	boolean isMarkedAt(int row, int col) {
		validateIndex(row, col);
		return grid[row][col].isMarked();
	}

	private void validateIndex(int row, int col) {
		if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
			throw new IndexOutOfBoundsException("Row and column must be in range 0-4.");
		}
	}

	private boolean hasBingoAt(int row, int col) {
		if (isRowComplete(row) || isColumnComplete(col)) {
			return true;
		}

		if (row == col && isMainDiagonalComplete()) {
			return true;
		}

		if (row + col == SIZE - 1 && isAntiDiagonalComplete()) {
			return true;
		}

		return false;
	}

	private boolean isRowComplete(int row) {
		for (int col = 0; col < SIZE; col++) {
			if (!grid[row][col].isMarked()) {
				return false;
			}
		}
		return true;
	}

	private boolean isColumnComplete(int col) {
		for (int row = 0; row < SIZE; row++) {
			if (!grid[row][col].isMarked()) {
				return false;
			}
		}
		return true;
	}

	private boolean isMainDiagonalComplete() {
		for (int i = 0; i < SIZE; i++) {
			if (!grid[i][i].isMarked()) {
				return false;
			}
		}
		return true;
	}

	private boolean isAntiDiagonalComplete() {
		for (int i = 0; i < SIZE; i++) {
			if (!grid[i][SIZE - 1 - i].isMarked()) {
				return false;
			}
		}
		return true;
	}
}


public class Bingo {
    public static void main(String[] args) {
        int[][] numbers = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
        };

        Card card = new Card(numbers);

        // Example usage
        card.markNumber(7);
        card.markNumber(14);

        for (int row = 0; row < Card.SIZE; row++) {
            for (int col = 0; col < Card.SIZE; col++) {
                System.out.print(card.getNumberAt(row, col));
                if (card.isMarkedAt(row, col)) {
                    System.out.print("* ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        Scanner scanner = new Scanner(System.in);
        boolean hasBingo = false;

        while(!hasBingo) {
            // Game loop logic
            System.out.print("Enter a number to mark (or 'exit' to quit): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                int numberToMark = Integer.parseInt(input);
				Card.MarkResult markResult = card.markAndCheckBingo(numberToMark);
				if (markResult.wasFound()) {
                    System.out.println("Number " + numberToMark + " marked.");

                    // Check for win condition
					if (markResult.hasBingo()) {
						hasBingo = true;
						System.out.println("BINGO! You have a winning line.");
					}

                } else {
                    System.out.println("Number " + numberToMark + " not found on the card.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit'.");
            }

        }

		scanner.close();
    }
}