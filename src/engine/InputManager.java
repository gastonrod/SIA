package engine;

import ar.com.itba.sia.*;
import java.util.*;

public class InputManager<E> {
    private static Scanner reader = new Scanner(System.in);  // Reading from System.in

    public int manageInput(Node<E> currentNode, Problem<E> problem) {
        while(true) {
            System.out.print("> ");
            String s = reader.next();
            try {
                int n = Integer.parseInt(s);
                if (n > 0)
                    return n;
                else {
                    System.out.println("Only greater than 0 integers are accepted.");
                    continue;
                }
            } catch (Exception e) {
                Commands command;
                try {
                    command = Commands.valueOf(s.toUpperCase());
                } catch(Exception ex){
                    command = Commands.UNKNOWN;
                }
                switch (command) {
                    case NEXT:
                        return 1;
                    case P_B:
                        System.out.println(currentNode.state.toString());
                        break;
                    case P_P:
                        if ( currentNode.parent != null)
                            System.out.println(currentNode.parent.state.toString());
                        else
                            System.out.println("This node has no parent! Try something else.");
                        break;
                    case R:
                        List<Rule<E>> rules = problem.getRules(currentNode.state);
                        for (Rule rule : rules) {
                            System.out.println(rule.toString());
                        }
                        break;
                    case H:
                        printHelp();
                        break;
                    case D:
                        System.out.println(currentNode.depth);
                        break;
                    case A_C:
                        System.out.println(currentNode.accumulatedCost);
                        break;
                    case E_C:
                        System.out.println(currentNode.estimatedCost);
                        break;
                    case OFF:
                        return -1;
                    case UNKNOWN: default:
                        System.out.println("The command was not recognized. Type h for help.");
                        break;
                }
            }
        }
    }

    public void closeScanner(){
        reader.close();
    }

    private void printHelp(){
        System.out.println( "p_b prints the current node's board\n" +
                "p_p prints the current node's parent board\n" +
                "r prints the appliable rules for the current node\n" +
                "d prints the current node's depth\n" +
                "a_c prints the current node's accumulated cost\n" +
                "e_c prints the current node's estimated cost\n" +
                "h prints all the available commands\n" +
                "next advances forward one step\n" +
                "[number] advances forward n steps\n" +
                "off turns the debugging mode off");
    }

    private enum Commands{
        NEXT,
        P_B,
        P_P,
        R,
        H,
        D,
        A_C,
        E_C,
        OFF,
        UNKNOWN
    }

}
