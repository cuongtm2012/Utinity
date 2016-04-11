package util;

public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		helpConfiguration help = new helpConfiguration();
		long accNo = 0;

		accNo = Long.parseLong("9999999999");

		// System.out.println(accNo);
		if (help.isValidDate("10/02/00")) {
			System.out.println("True");
		}

	}

}
