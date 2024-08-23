import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        String Output = "Results: ";
        int Total = 0;
        int ModifierTotal = 0;
        //setup default Variables
        System.out.println(args[0]);

        String[] InputArguments = args[0].split("\\+");
        //Get the first argument entered (the only one) and splits it along the '+'
        for (int n = 0; n < InputArguments.length; n++)
            //for each argument inputted
            if (InputArguments[n].contains("d")) {
                //if the argument demands we roll dice
                int DiceCount = Integer.valueOf(InputArguments[n].split("d")[0]);
                int DiceSides = Integer.valueOf(InputArguments[n].split("d")[1]);
                //gather information in seperate variables
                Output = Output + String.valueOf(DiceSides) + "-sided dice; ";
                //append the number of sides of the dice to the output string
                for (int k = 0; k < DiceCount; k++) {
                    int RollResult = ThreadLocalRandom.current().nextInt(1, DiceSides + 1);
                    Output = Output + String.valueOf(RollResult) + ", ";
                    //roll the dice randomly and add that value to the output line
                    Total = Total + RollResult;
                    //add the roll to the total
                }
                Output = Output.substring(0, Output.length()-2);
                //remove the extra ", "
                Output = Output + ". ";
                //add ". "
            } else {
                //if the argument is a modifier
                int Modifier = Integer.valueOf(InputArguments[n]);
                ModifierTotal = ModifierTotal + Modifier;
                //save the modifier total to add at the end (if there is multiple modifiers they will be combined)
                Total = Total + Modifier;
            }
        Output = Output + "Modifier: " + ModifierTotal + ". ";
        Output = Output + "Total: " + Total + ".";
        System.out.println(Output);
    }
}