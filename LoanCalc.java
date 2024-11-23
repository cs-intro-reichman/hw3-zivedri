// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

	static double epsilon = 0.001;  // Approximation accuracy
	static int iterationCounter;    // Number of iterations 

	// Gets the loan data and computes the periodical payment.
    // Expects to get three command-line arguments: loan amount (double),
    // interest rate (double, as a percentage), and number of payments (int).
	public static void main(String[] args) {
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {
		double currentRate ; // the current rate.
		rate = rate/100; // modify rate to %.
		for (int i = 0;i < n;i++) { //number of payments.
			currentRate = (loan-payment) * rate; // the sum added to the loan from the rate.
			loan = loan - payment + currentRate; // the current loan sum.
		}
		return loan; // the ending balance.
	}
	
	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;
		double rate2 = epsilon;
		double g = loan/n;
		while (endBalance(loan,rate,n,g) > 0){
			g = (loan/n) + (loan*rate2);
			rate2 += epsilon;
			iterationCounter++;
		}
		return g;
    }
    
    // Uses bisection search to compute an approximation of the periodical payment 
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
    public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;
		double rate2 = rate/100;
		// Sets L and H to initial values such that f(L) > 0, f(H) < 0,
       // implying that the function evaluates to zero somewhere between L and H.
		// So, let’s assume that L and H were set to such initial values.
		double paymentL = loan/n +loan*rate2;
		double paymentH = loan/n;
		double g = (paymentL + paymentH) / 2;
		while ((paymentL-paymentH) > epsilon) {
			// Sets L and H for the next iteration
			if (endBalance(loan,rate,n,paymentH)*endBalance(loan,rate,n,g) > 0){
				// the solution must be between g and L
				paymentH = g;
			}
			else {
				// the solution must be between H and g
				paymentL = g;
			}
			g = (paymentL + paymentH) / 2;
			// Computes the mid-value (g) for the next iteration
			iterationCounter++;
		}
		return g;
    }
}