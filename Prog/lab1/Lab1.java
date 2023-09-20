import static java.lang.Math.*;

public class Lab1 {

	private static int Arr1_st = 7;
	private static int Arr1_end = 15;
	private static int Arr2_st = -7;
	private static int Arr2_end = 7;
	private static int Arr2_count = 20;
	private static int Arr3_colum = 20;
	private static int Arr3_row = 5;
	

	private static short[] Array_1(int st, int end) {
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


	private static double[] Array_2(int st, int end, int count) {
		double[] x = new double[count];
		for (int i = 0; i < count; i++) {
			x[i] = random()*14 - 7; 
			//random() возвращает [0;1)
			//* 14 -> от 0 до 14. -7 -> от -7 до +7
		}

		return x;
	}


	private static double[][] Array_3(short[] c, double[] x, int col, int row) {
		double[][] res = new double[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				switch (c[i]) {
				case 15 -> res[i][j] = CalculateFirst(x[j]);
				case 7, 13 -> res[i][j] = CalculateSecond(x[j]);
				default -> res[i][j] = CalculateThird(x[j]);
				}
			}
		}
		return res;
	}


	private static double CalculateFirst(double x) {
		double result;
		result = 0.25 + (x + 1) / x;
		result = pow(result / atan(x / 14), 2);
		result = pow(2 * result, 2);
		return result;
	}


	private static double CalculateSecond(double x) {
		double result;
		double n_pow;
		n_pow = 1 - cbrt(x);
		n_pow = pow(0.25 * (3 + x), 3) * n_pow;
		n_pow = pow(n_pow, 3) + 1;
		n_pow = n_pow * atan(sin(x));
		result = pow(E, pow(0.75 / (x - 4), 2));
		result = pow(result, n_pow);
		return result;
	}


	private static double CalculateThird(double x) {
		double result;
		double n_pow;
		n_pow = sin(cbrt(pow(E, x)));
		result = pow(E, n_pow);
		return result;
	}


	public static void main(String[] args) {
		short[] c = Array_1(Arr1_st, Arr1_end);
		double[] x = Array_2(Arr2_st, Arr2_end, Arr2_count);
		double[][] res =  Array_3(c, x, Arr3_colum, Arr3_row);

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