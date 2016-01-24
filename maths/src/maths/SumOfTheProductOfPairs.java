package maths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Place numbers 1 to N in a circle. Adjacent pairs are multiplied and a total
 * sum of each pair is found. e.g. numbers 1 2 3 4 5 6 7 8
 * 1x2+2x3+3x4+4x5+5x6+6x7+7x8+8x1=
 * 
 * @author Ivan Stuart
 * 
 */
public class SumOfTheProductOfPairs {

	public static void main(String args[]) {

		if (args.length != 2) {
			System.out
					.print("SumOfTheProductOfPairs [maxNumber] [numberToFind]");
			return;
		}

		int maxNumber = Integer.parseInt(args[0]);

		int numberToFind = Integer.parseInt(args[1]);

		// Lowest {1,7,3,5,4,6,2,8} = 123
		// Next {1,7,4,5,3,6,2,8} = 124

		// Middle {1,2,3,4,5,6,7,8} = 176

		// High {8,7,5,3,1,2,4,6} = 191;

		// int[] listOfNumber = {8,7,5,3,1,2,4,6};

		int foundValue = 0;
		for (numberToFind = 123; numberToFind < 192; numberToFind++) {
			while (foundValue != numberToFind) {

				int[] listOfNumber = createRandomList(maxNumber);

				foundValue = calculateSumOfProductOfPairs(listOfNumber);

				if (foundValue == numberToFind) {
					System.out
							.println(toString(listOfNumber) + "= " + foundValue);
				}
			}
		}
	}

	private static int[] createRandomList(int maxNumber) {
		Random r = new Random();
		List<Integer> listOfNumbers = new ArrayList<>(maxNumber + 1);

		for (int i = 0; i < maxNumber; i++) {
			listOfNumbers.add(i + 1);
		}

		int list[] = new int[maxNumber];
		for (int i = 0; i < maxNumber; i++) {
			int randomIndex = r.nextInt(listOfNumbers.size());
			list[i] = (int) listOfNumbers.remove(randomIndex);
		}
		return list;
	}

	public static int calculateSumOfProductOfPairs(int numbers[]) {
		int total = 0;
		for (int i = 0; i < numbers.length - 1; i++) {
			total += numbers[i] * numbers[i + 1];
		}
		total += numbers[numbers.length - 1] * numbers[0];
		return total;
	}

	public static String toString(int numbers[]) {
		String text = "";
		for (int i = 0; i < numbers.length - 1; i++) {
			text += "(" + numbers[i] + " x " + numbers[i + 1] + ") + ";
		}
		text += "(" + numbers[numbers.length - 1] + " x " + numbers[0] + ")";
		return text;
	}
}
