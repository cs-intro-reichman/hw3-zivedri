/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent","listen"));  // true
		System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie","Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true
		// Tests the preProcess function.
		System.out.println(preProcess("What? No way!!!"));
		
		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
		
		// Performs a stress test of randomAnagram 
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}  

	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		str1 = preProcess(str1);
		str2 = preProcess(str2);
		if (str1.length() != str2.length()) {
			return false;
		}
		for (int i = 0;i < str1.length();i++){
			if (str2.indexOf(str2.charAt(i)) < 0) {
				return false;
			}
			if (str1.indexOf(str2.charAt(i)) < 0) {
				return false;
			}
		}
		return true;
	}
	   
	// Returns a preprocessed version of the given string: all the letter characters are converted
	// to lower-case, and all the other characters are deleted, except for spaces, which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		str = str.toLowerCase();
		String s = "";
		for (int i = 0;i < str.length(); i++){
			if (str.charAt(i) >= 97 && str.indexOf(i) <= 122){
				s += str.charAt(i);
			}
		}
		return s;
	} 
	   
	// Returns a random anagram of the given string. The random anagram consists of the same
	// characters as the given string, re-arranged in a random order. 
	public static String randomAnagram(String str) {
		str = preProcess(str);
		String randomStr = "";
		while (str.length() > 0){
			int i = (int) (Math.random() * str.length());
			char c = str.charAt(i); // keeping the char we removed
			randomStr += c; // adding the random character to a new string.
			if (i == 0) { // if the character is the first in the string.
				str = str.substring(i+1);
			}
			else if (i == str.length() - 1) { // if the character is the last in the string.
				str = str.substring(0, i);
			}
			else {
				String temp = str.substring(0,i); // removing the character that selected.
				str = temp + str.substring(i+1 , str.length());
			}
		}
		return randomStr;
	}
}
