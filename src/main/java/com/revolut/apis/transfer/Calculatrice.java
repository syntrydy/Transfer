package com.revolut.apis.transfer;

public class Calculatrice {

	private String name = "Calculatrice";

	public int addition(int nombre1, int nombre2) {
		return nombre1 + nombre2;
	}

	public int soustraction(int nombre1, int nombre2) {
		return nombre1 - nombre2;
	}

	public int multiplication(int nombre1, int nombre2) {
		return nombre1 * nombre2;
	}

	public double division(int nombre1, int nombre2) throws ArithmeticException {
		return nombre1 / nombre2;
	}

	public String getName() {
		return name;
	}

}
