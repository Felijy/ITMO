import static java.lang.Math.*;

public class Lab1 {

	private static final int ARR1_ST = 7;
	private static final int ARR1_END = 15;
	private static final int ARR2_ST = -7;
	private static final int ARR2_END = 7;
	private static final int ARR2_COUNT = 20;
	private static final int ARR3_COLUMS = 20;
	private static final int ARR3_ROWS = 5;
	

	private static short[] makeFirstArray(int st, int end) {
		short k;
		if (end % 2 == 0) {
			k = (short)(end - 1);
		}
		else {
			k = (short)end;
		}
		int lenght = ((int)k - st) / 2 + 1; //+1 чтобы 7 тоже попала
		short[] c = new short[lenght];
		for (int i = 0; i < lenght; i++) {
			c[i] = k;
			k -= 2;
		}

		return c;
	}


	private static double[] makeSecondArray(int st, int end, int count) {
		double[] x = new double[count];
		for (int i = 0; i < count; i++) {
			x[i] = random()*14 - 7; 
			//random() возвращает [0;1)
			//* 14 -> от 0 до 14. -7 -> от -7 до +7
		}

		return x;
	}


	private static double[][] makeThirdArray(short[] c, double[] x, int col, int row) {
		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				switch (c[i]) {
				case 15 -> res[i][j] = calculateFirstStatement(x[j]);
				case 7, 13 -> res[i][j] = calculateSecondStatement(x[j]);
				default -> res[i][j] = calculateThirdStatement(x[j]);
				}
			}
		}
		return res;
	}


	private static double calculateFirstStatement(double x) {
		double result;
		result = 0.25 + (x + 1) / x;
		result = pow(result / atan(x / 14), 2);
		result = pow(2 * result, 2);
		return result;
	}


	private static double calculateSecondStatement(double x) {
		double result;
		double powNumber;
		powNumber = 1 - cbrt(x);
		powNumber = pow(0.25 * (3 + x), 3) * powNumber;
		powNumber = pow(powNumber, 3) + 1;
		powNumber = powNumber * atan(sin(x));
		result = pow(E, pow(0.75 / (x - 4), 2));
		result = pow(result, powNumber);
		return result;
	}


	private static double calculateThirdStatement(double x) {
		double result;
		double powNumber;
		powNumber = sin(cbrt(pow(E, x)));
		result = pow(E, powNumber);
		return result;
	}


	public static void main(String[] args) {
		short[] c = makeFirstArray(ARR1_ST, ARR1_END);
		double[] x = makeSecondArray(ARR2_ST, ARR2_END, ARR2_COUNT);
		double[][] res =  makeThirdArray(c, x, ARR3_COLUMS, ARR3_ROWS);

		print_res(res);
	}	


	private static void print_res(double[][] c) {
		for (double[] i : c) {
			for (double j : i) {
				if (j > pow(10, 5)) {
					System.out.printf("%15.5e ", j);
				}
				else {
					System.out.printf("%15.5f ", j);	
				}
			}
			System.out.println();
		}
	}
}