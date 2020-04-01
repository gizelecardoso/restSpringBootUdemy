package br.com.erudio.repository;

import org.springframework.stereotype.Component;

@Component
public class MathConverter {

	
	public static Double convertToDouble(String strNumber) {
		if(strNumber == null) {
			return 0D;
		}
		
		String number = strNumber.replaceAll(",", ".");
		
		if(isNumeric(number)) {
			return Double.parseDouble(strNumber);
		}
		
		return 0D;
	}


	public static boolean isNumeric(String strNumber) {
		
		if(strNumber == null) {
			return false;
		}
		
		String number = strNumber.replaceAll(",", ".");
		
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}

}
