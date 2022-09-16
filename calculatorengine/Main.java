package com.calculatorengine;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    static double execute(char opCode, double leftVal, double rightVal) {

        double result;
        switch (opCode) {
            case 'a':
                result = leftVal + rightVal;
                break;
            case 's':
                result = leftVal - rightVal;
                break;
            case 'm':
                result = leftVal * rightVal;
                break;
            case 'd':
                result = leftVal / rightVal;
                break;
                    /*
                    If Statement of Case 'd' Above (Explained)
                        if(value2 != 0)
                            result = value1 / value2;
                            break;
                    */
            default:
                System.out.println("Invalid Code" + opCode);
                result = 0.0d;
                break;
        }
        return result;
    }

    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        double result = execute(opCode, leftVal, rightVal);
        System.out.println(result);
    }

    //This Mode allows the user to start the application and then enter an operation and the values. That is, user
    // can specify multiply rather than being limited to using the opCode m.
    static char opCodeFromString(String operationName) {
        char opCode = operationName.charAt(0);
        return opCode;
    }

    //This does the Translation work for the Numeric words. Also, we want this Method to use the word that represents
    // a number, translate that into the double that our application already understands, and return. In short,
    // valueFromWord method gives us back the numeric value that corresponds to the word that the user typed in.
    // This method can handle numeric values themselves.
    static double valueFromWord(String word) {
        //This Array lists the Numeric Names that we understand.
        String[] numberWords = {"zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"};

        double value = -1d;
        //This For Loop tries to match up the word that the user types in. When we find the matching word, we take
        // the index of that word, assign it to a local variable value, and then return the value.
        for(int index = 0; index < numberWords.length; index++) {
            //This if statement checks to see if the word the user typed in matches any of the values.
            if(word.equals(numberWords[index])) {
                value = index;
                break;
            }
        }
        //If the value is -1, it tells us that the string we received id not one of the words we understand. So,
        // we'll need to parse that number out of the String.
        if(value == -1d)
            value = Double.parseDouble(word);

        return value;
    }

    //This Method handle details of getting the input from the user and breaking the input into its individual parts.
    static void executeInteractively() {
        System.out.println("Enter an operation and two Numbers: ");
        Scanner sc = new Scanner(System.in);
        //Here, we've assigned the instance of the scanner to a local variable named userInput of type String. What
        // this will do is that if the  user types in something like multiply-space-three-space-five, that value
        // will be assigned to our userInput string variable.
        String userInput = sc.nextLine();
        //The Split Method accepts an expression to identify what we want to use to split the String into parts. We
        // simply want to split it based on spaces. Therefore, we'll pass in the string literal space. Then, we
        // assign the result of split to a local variable named parts of type string array.
        String[] parts = userInput.split( " ");
        performOperation(parts);
    }

    //This Method converts the Individual parts broken above from strings into the appropriate data types, performs
    // the operation, and prints the result out to the user.
    private static void performOperation(String[] parts) {
        char opCode = opCodeFromString(parts[0]);
        if(opCode == 'w')
            handleWhen(parts);
        else {
            double leftVal = valueFromWord(parts[1]);
            double rightVal = valueFromWord(parts[2]);
            double result = execute(opCode, leftVal, rightVal);
            displayResult(opCode, leftVal, rightVal, result);
        }
    }

    private static void handleWhen(String[] parts) {
        //Here, we parse the value  that the user typed in. It will take the string value that the user typed in for
        // date(As long as the date is formatted correctly). That parse method will translate that string into an
        // instance of LocalDate and assign it to our startDate variable.
        LocalDate startDate = LocalDate.parse(parts[1]);
        //Gets the No. of Days that the user wants to add.
        long daysToAdd = (long) valueFromWord(parts[2]);
        //Does the Date Arithmetic. We'll use our startDate variable and call it plusDays method, passing in
        // daysToAdd plusDays method, passing in daysToAdd.
        LocalDate newDate = startDate.plusDays(daysToAdd);
        //Display our result.
        //N/B :- Key to this is the user typing in the Date in this exact format. Eg,.. when 2022-09-15 seven
        String output = String.format("%s Plus %d days is %s", startDate, daysToAdd, newDate);
        System.out.println(output);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
        char symbol = symbolFromOpCode(opCode);
//        StringBuilder sb = new StringBuilder(20);
//        sb.append(leftVal);
//        sb.append(" ");
//        sb.append(symbol);
//        sb.append(" ");
//        sb.append(rightVal);
//        sb.append(" = ");
//        sb.append(result);
        //Remember that StringBuilder is not itself a String. To get the String, we'll want to call its toString
        // Method. So we'll assign the result to a local variable named output of type String, and once we have our
        // output, we can simply print it out.
//      String output = sb.toString();

        // .4 limits the No. of decimal places to four
        String output = String.format("%.4f %.4c %.4f = %.4f", leftVal, symbol, rightVal, result);
        System.out.println(output);
    }

    //symbolFromOpCode accepts a char opCode as a parameter and its return type is also char. This method loops
    // through the opCodes array to find the opCode that was passed in. When it finds the opCode that was passed in
    // it assigns it to a local variable symbol, breaks out the loop, and then simply returns that symbol.
    private static char symbolFromOpCode(char opCode) {
        char[] opCodes = {'a', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';
        for(int index = 0; index < opCodes.length;  index++) {
            if(opCode == opCodes[index]) {
                symbol = symbols[index];
                break;
            }
        }
        return symbol;
    }

    public static void main(String[] args) {

        //Parallel Arrays - Means that the element in each array is meant to be used with a corresponding element
        //                  in each of the other arrays.
        double[] leftVals = {200.0d, 150.0d, 100.0d, 50.0d};
        double[] rightVals = {50.0d, 100.0d, 150.0d, 200.0d};
        char[] opCodes = {'a','s','m','d'};
        double[] results = new double[opCodes.length];

        //Switch case Statement
        //This Mode loops through a predefined set of Arrays performing Calculations
        if(args.length == 0) {
            for (int i = 0; i < opCodes.length; i++) {
                results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);
            }

            for (double currentResult : results)
                System.out.println(currentResult);
        }
        else if(args.length == 1 && args[0].equals("interactive"))
            executeInteractively();

            //This Mode allows you to put in an opCode into numeric values, and the application will perform the
            // operation indicated by those command line values.
        else if(args.length == 3)
            handleCommandLine(args);
        else
            System.out.println("Please provide an opCode and 2 Numeric values:");

        /*
        If-else Statement Block
        if(opCode == 'a')
            result = value1 + value2;
        else if(opCode == 's')
            result = value1 - value2;
        else if(opCode == 'm')
            result = value1 * value2;
        else if(opCode == 'd') {
            if(value2 != 0)
                result = value1 / value2;
        }

        else {
            System.out.println("Invalid Code" + opCode);
            result = 0.0d;
        }
        */
    }
}
