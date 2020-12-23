package com.revolut.apis.transfer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nombre1 = 6;
		int nombre2 = 0;
		Calculatrice calculatrice = new Calculatrice();

		System.out.println(
				"Résultat addition de " + nombre1 + " + " + nombre2 + " = " + calculatrice.addition(nombre1, nombre2));

		System.out.println(
				"Résultat de " + nombre1 + " - " + nombre2 + " = " + calculatrice.soustraction(nombre1, nombre2));

		try {
			System.out.println(
					"Résultat de " + nombre1 + " / " + nombre2 + " = " + calculatrice.division(nombre1, nombre2));
		} catch (ArithmeticException e) {
			// TODO Auto-generated catch block
			System.out.println("Division par 0 impossible");
		}
	}

}
