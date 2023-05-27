/**
 * File: Problem Set Unit 5
 * Author: Ajai Jeyakaran
 * Date Created: May 15, 2023
 * Date Modified: May 25, 2023
 */
import java.util.Scanner;
import java.util.ArrayList;

class Main {
    public static String ask() {
        Scanner calc = new Scanner(System.in);
        System.out.print("In: ");
        String input1 = calc.nextLine();
        return input1;
    }

    public static void main(String[] args) {
        boolean flag = true; // by default true so while loop runs
        while (flag) {
            String input1 = ask(); // the user input
            while (input1.trim().equals("") || (int) input1.charAt(0) == 32) { // checks if the input is full of spaces, or empty, if so asks input again
                System.out.println("Invalid Input");
                input1 = ask();
            }
            String[] input = input1.split(" "); // puts the input into an array
            int intcounter = 0;
            int opercounter = 0; 
            int deci = 0;
            for (int i = 0; i < input.length; i++) { // goes through the array
                String[] subinput = input[i].split(""); // sub splits it so it goes through each individual character
                int subdeci = 0; // number of decimals in each expression so doesn't exceed count of 1
                if (input.length % 2 == 0) { // if the length of array is even (starts from 1) invalid, since it'll be an operator
                    opercounter = 1; // makes invalid
                    i = input.length; // ends loop
                } else if (i % 2 == 0) {  // if it's the even indexes (numbers only)
                    for (int j = 0; j < subinput.length; j++) { // sub splits into individual character 
						if (subinput[j].equals("-") && subinput.length > 1 && j==0) { // if it starts with a "-" goes to next index, as it is valid
							j++;
						}
						char chara = subinput[j].charAt(0); 
                        if ((subinput[j].equals(""))) { // if an index is empty stops checking and sets invalid
                            i = input.length + 1;
                        }
                        if (subinput[j].equals(".") && subdeci == 0 && subinput.length!=1) { // checks if there is a decimal, if so only one occurance can happen else invalid, and length of the index has to be greater than 1 to prevent only "." being inputted
                            subdeci = 1;
                            deci++;
                            j++;
                        } else if (!((int) chara >= 48 && (int) chara <= 57)) { // if an integer uses ASCII values to check if it's a valid number
                            intcounter = 1;
                        }
                    }
                } else { // checks if odd indexes are any of the valid operators
                    if (!(input[i].equals("+") || input[i].equals("-") || input[i].equals("*") || input[i].equals("/") || input[i].equals("^"))) {
                        opercounter = 1;
                    }
                }
            }
            boolean flag1 = false;
            if (intcounter == 0 && opercounter == 0) { // if expression if valid
                flag = false;
                flag1 = true;
            } else { // if expressino is invalid
                System.out.println("Invalid Input");
            }
            while (flag1) { 
                ArrayList<String> strarr = new ArrayList<String>();
                for (int i = 0; i < input.length; i++) { // copies from array to arraylist
                    strarr.add(input[i]);
                }
                for (int j = 0; j < strarr.size(); j++) { // checks for "^" first and does the operation 
                    if (strarr.get(j).equals("^")) {
                        strarr.set(j,Math.pow(Double.parseDouble(strarr.get(j - 1)), Double.parseDouble(strarr.get(j + 1)))+ "");
                        strarr.remove(j - 1); // removes previous index relative to "^"
                        strarr.remove(j); // removes following index relative to "^"
                        j = 0; // resets loop so it can check for "^" again after updated arraylist
                    }
                }
                for (int j = 0; j < strarr.size(); j++) { // checks for "*" and "/" first and does the operation 
                    if (strarr.get(j).equals("*") || strarr.get(j).equals("/")) {
                        if (strarr.get(j).equals("*")) {
                            strarr.set(j,Double.parseDouble(strarr.get(j - 1)) * Double.parseDouble(strarr.get(j + 1)) + "");
                        } else {
                            strarr.set(j,Double.parseDouble(strarr.get(j - 1)) / Double.parseDouble(strarr.get(j + 1)) + "");
							if (Double.parseDouble(strarr.get(j - 1)) % Double.parseDouble(strarr.get(j + 1)) != 0) { // if there is no remainder after dividing the two numbers result will continue to be an integer if no decimal was placed in expression
                                deci++;
                            }
                        }
                        strarr.remove(j - 1);
                        strarr.remove(j);
                        j = 0;
                    }
                }
                for (int j = 0; j < strarr.size(); j++) { // checks for "+" and "-" first and does the operation 
                    if (strarr.get(j).equals("+") || strarr.get(j).equals("-")) {
                        if (strarr.get(j).equals("+")) {
                            strarr.set(j,Double.parseDouble(strarr.get(j - 1)) + Double.parseDouble(strarr.get(j + 1)) + "");
                        } else {
                            strarr.set(j,Double.parseDouble(strarr.get(j - 1)) - Double.parseDouble(strarr.get(j + 1)) + "");
                        }
                        strarr.remove(j - 1);
                        strarr.remove(j);
                        j = 0;
                    }
                }
                if (!(deci > 0)) { // if there wasn't a decimal in expression and was a whole number after divison, outputs result as integer 
                    System.out.println((int) Double.parseDouble(strarr.get(0)));
                } else { // if decimal at anypoint in result or expression outputs as double
                    System.out.println(Double.parseDouble(strarr.get(0)));
                }
                flag1 = false; 
            }
        }
    }
}
