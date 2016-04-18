import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Javassignment {

    public static void main(String[] args) {

        CardsAttribute[] cardDeck = new CardsAttribute[60];         //create a deck, 54 is the number of cards

        List<String> cardList = new ArrayList<>();                  //contains the lines of the card.txt
        ArrayList<Integer> deckCode = new ArrayList<>();     //the deck, contains card code from 0-53 so far

        //Start of file reading
        String file_name = "card.txt";
        try {
            ReadFile file = new ReadFile(file_name);
            String[] cardLines = file.OpenFile();
            String[] trumpNameList = new String[]{"The Geminologist", "The Geophysicst", "The Mineralogist", "The Petrologist", "The Miner", "The Geologist"};
            String[] trumpDescList = new String[]{"Hardness", "Specific Gravity, or throw Magnetite together to instantly wins round.", "Cleavage", "Crustal Abundance", "Economic Value", "a category of your choice"};
            int trumpCountUp = 0;
            for (int i = 0; i < cardLines.length; i++) //loop the lines, so far, i should be 0-53, which is 54 cards
            {
                String currentCardStat[] = cardLines[i].split(",");     //
                // Name = 0,    Hardness = 1,   Gravity = 2,    Cleavage = 3,   Crustal Abundance = 4,  EcoValue = 5
                System.out.println("Name: " + currentCardStat[0] + ", Hardness: " + currentCardStat[1] + ", Gravity: " + currentCardStat[2]+ ", Cleavage: " + currentCardStat[3]
                    + ", Crustal Abundance: " + currentCardStat[4] + ", Economic Value: " + currentCardStat[5]); //testing

                cardList.add(cardLines[i]);
                deckCode.add(i);

                cardDeck[i] = new CardsAttribute();                     //add cards as object
                cardDeck[i].setCardDetails(cardLines[i]);               //set the card's line attributes inside the card
            }

            for (int i = 54; i<60;i++){
                cardDeck[i].setTrumpName(trumpNameList[trumpCountUp]);
                cardDeck[i].setTrumpDesc(", Changes trump category into " + trumpDescList[trumpCountUp] + ". Re-adds eliminated player too.");
                trumpCountUp++;
                cardDeck[i].setTrumpValue(trumpCountUp);
            }
            //54 hardness, 55 gravity 45 magnetite, 56 cleavage, 57 crust.abun, 58 ecovalue, 59 choose
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //end of file reading

        long seed = System.nanoTime();
        Collections.shuffle(deckCode, new Random(seed));        //shuffles deck


        boolean playerLoop = true;
        int playerNumber = 0;

        while (playerLoop){
            playerNumber = Integer.parseInt(JOptionPane.showInputDialog("Insert number of players from 3 - 5:"));
            if (playerNumber <= 5 && playerNumber >= 3)
                playerLoop = false;
            else JOptionPane.showMessageDialog(null, "Wrong input");
        }

        Players[] playerArray = new Players[playerNumber];          //creating an array of players

        for (int i = 0; i<playerNumber; i++) {
            playerArray[i] = new Players();                         //use the Player object inside the array as player
            int properInt = i + 1;                                  //Player [0] --> player 1, playerArray[1] --> Player 2 etc
            playerArray[i].setName("Player " + properInt);


            for (int o = 0; o < 8; o++) {
            playerArray[i].addCard(deckCode.get(0));
            deckCode.remove(0);
            }
        }


        //testing purpose, printing every cards in player's hand

        for (int i = 0; i < playerArray.length; i++) {                                                          //loop for the number of players
            System.out.println("\n\n" + playerArray[i].getName());                                              //print player name
            System.out.println(playerArray[i].getAllCard());                                                    //print all handcard's code
            for (int o = 0; o < playerArray[i].getAllCard().size(); o++){                                       //loop for the number of cards in hand
                System.out.print("\n" + playerArray[i].getCard(o));                                             //print the card's code one by one
                System.out.print("  " + cardDeck[playerArray[i].getCard(o)].printCardDetail() + "  ");          //print the card detail line
                for (int p = 1; p <= 5;p++) {                                                                   //loop from 1 to 5, from hardness to economic value
                    System.out.print("  " + cardDeck[playerArray[i].getCard(o)].getCardAttributeValue(p));      //print the value of the card, one by one
                }
            }

        }  //end of test

        //      finally start game here?

        boolean gameContinue = true;    //is the game still continuing?
        int currentWinner = 0;          //who was the last hand's winner? by default is Player 1 at first, which is playerArray[0]
        String currentHandCardString = "";
        JOptionPane.showMessageDialog(null, "Game start! Player 1 goes first");


        ArrayList currentButtonChoice = new ArrayList();

        while (gameContinue){           //loop while game is still continuing

            for (int i = 0; i < playerNumber;i++){
                playerArray[i].setEliminated(false);        //making sure everyone is alive at the start of the round
            }

/* just a reference for class methods:
    Methods for players in game:                        (i is the player's number minus 1. Player 1 is 0, Player 2 is 1 etc)
        playerArray[i].getName()                        return player's name in string
        playerArray[i].addCard(int cardCode)            will add a card into the player's hand. The input is the card code. Get the card code from deck class
        playerArray[i].getAllCard()                     will return an arraylist of the CODE of the cards in player's hand
        playerArray[i].getCard(int cardPlace)           will return a card's CODE based on the position of the card. Eg: [1 ,2 ,3], input 0 and you get 1
        playerArray[i].chooseCard(int cardPlace)        is the same as getCard, but it will also REMOVE the card from the player's hand
    Methods for card deck in game:                      (i is the card's code. The first card is 0, and minus the supertrump, last is 53)
        cardDeck[i].printCardDetail()                   will return a line of the card's details. eg: 0 --> "Quartz,7,2.65,poor/none,high,moderate"
        cardDeck[i].getCardAttributes(int Type)         will return the STRING value of a certain attribute based on the line, NOT the numeric value or the comparable value
        cardDeck[i].getCardAttributeValue(int Type)     will return the DOUBLE VALUE of a certain att based on the line.

        dictionary for type:  Hardness = 1,   Gravity = 2,    Cleavage = 3,   Crustal Abundance = 4,  Economic Value = 5
 */
            int tempCardChoice = 0; //temporary storage on where the card in the hand is, NOT the card's code
            int cardChoice = 0;     //get what the card's code is
            int cardChoiceValue;    //the value of the chosen card
            String[] trumpList = {"Hardness", "Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};
            int biggestCard;        //the biggest card's code
            int trumpType;          //the current card's trump type
            double biggestCardValue = 0;   //the biggest card's value
            int eliminatedPlayerCount = 0;  //counting how many players have been eliminated on a round. Will reset after every round
            boolean turnContinues = true;

            currentButtonChoice.clear();
            currentHandCardString = "";
            //54 hardness, 55 gravity magnetite, 56 cleavage, 57 crust.abun, 58 ecovalue, 59 choose
            for (int i=0; i < playerArray[currentWinner].getCardSize();i++) {                                       //setting what will be shown to player
                if (playerArray[currentWinner].getCard(i) < 54) {               //if it's not a supertrump card
                    System.out.println("Cardcheck, Not Supertrump");
                    currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(0) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(1)
                            + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(2) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(3)
                            + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(4) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(5));     //to print card details
                    currentButtonChoice.add(cardDeck[playerArray[currentWinner].getCard(i)].getName());
                }
                else{                   //if it's a supertrump card
                    System.out.println("Cardcheck, Supertrump");
                    currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentWinner].getCard(i)].getTrumpName() + cardDeck[playerArray[currentWinner].getCard(i)].getTrumpDesc());
                    currentButtonChoice.add(cardDeck[playerArray[currentWinner].getCard(i)].getTrumpName());
                }

            }
            tempCardChoice = JOptionPane.showOptionDialog(null, playerArray[currentWinner].getName() + "\n\n" +
                    "(Name,Hardness,Gravity,Cleavage,Crustal Abundance,Economical Value)\n" + currentHandCardString, "Input a card!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, currentButtonChoice.toArray(), null);
            // ^this one is to print the option pane and get input based on the position of the card^
            cardChoice = playerArray[currentWinner].chooseCard(tempCardChoice);

            biggestCard = cardChoice;
            if (cardChoice < 54) {
                System.out.println("Not Supertrump");
                trumpType = JOptionPane.showOptionDialog(null, cardDeck[cardChoice].printCardDetail() +
                        "\nPick a Trump type",  "Pick a Trump type", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, trumpList, trumpList[0]);
                trumpType += 1;
                biggestCardValue = cardDeck[biggestCard].getCardAttributeValue(trumpType);      //if it's not supertrump
            }
            else {
                System.out.println("Supertrump");
                biggestCardValue = 0;                                                           //if it's a supertrump
                if (cardChoice ==59) {
                    trumpType = JOptionPane.showOptionDialog(null, "You picked Geologist! Pick a trump type! ", "Input a trump type!",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, trumpList, null);
                    trumpType += 1;
                    JOptionPane.showMessageDialog(null, "You picked " + cardDeck[cardChoice].getTrumpName() + "! Now " +
                            "the trump attribute is " + trumpList[trumpType]);

                }
                else if (cardChoice == 55){                                             //if you choose Geophycisist and you have Magnetite
                    String[] yesNo = {"Yes","No"};
                    if (playerArray[currentWinner].getAllCard().contains(45)){
                        int magneInstaWin = JOptionPane.showOptionDialog(null, "You have Magnetite! Do you want to throw" +
                                " it in together to instantly win the round?", "Magnetite detected!",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,yesNo,null);
                        if (magneInstaWin == 1){JOptionPane.showMessageDialog(null,"Alright, moving on.");}
                        else {
                            turnContinues = false;
                            playerArray[currentWinner].chooseCard(playerArray[currentWinner].getAllCard().indexOf(45));
                            JOptionPane.showMessageDialog(null, "Alright, you win the round!");
                        }

                    }
                    trumpType = cardDeck[cardChoice].getTrumpValue();
                }
                else {
                    trumpType = cardDeck[cardChoice].getTrumpValue();
                    JOptionPane.showMessageDialog(null, "You picked " + cardDeck[cardChoice].getTrumpName() + "! Now " +
                            "the trump attribute is " + trumpList[trumpType-1]);
                }

            }
            //54 hardness, 55 gravity 45 magnetite, 56 cleavage, 57 crust.abun, 58 ecovalue, 59 choose
            /*testing purposes
            JOptionPane.showMessageDialog(null,cardChoice);
            JOptionPane.showMessageDialog(null, playerArray[currentWinner].getAllCard());
            JOptionPane.showMessageDialog(null, biggestCard + ", " + biggestCardValue);
            end of test*/


            int currentPlayer = currentWinner;
            while (turnContinues){          // core gameplay. Loop while more than 1 is alive
                currentPlayer += 1;
                boolean checkingPlayer = true;
                while (checkingPlayer){                                             //making sure players arent already eliminated or out of list
                    if (currentPlayer == (playerNumber)){
                        currentPlayer = 0;}
                    else if (playerArray[currentPlayer].getEliminated())
                        currentPlayer += 1;
                    else
                        checkingPlayer = false;
                }
                System.out.println("Successful new turn for " + playerArray[currentPlayer].getName());

                currentButtonChoice.clear();
                currentHandCardString = "";                                             //reading the player's hand card
                for (int i=0; i < playerArray[currentPlayer].getCardSize();i++) {
                    if (playerArray[currentPlayer].getCard(i) < 54) {               //if it's not a supertrump card
                        System.out.println("Cardcheck, Not Supertrump");
                        currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(0) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(1)
                                + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(2) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(3)
                                + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(4) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(5)
                        );     //to print card details
                        currentButtonChoice.add(cardDeck[playerArray[currentPlayer].getCard(i)].getName());
                    } else {                   //if it's a supertrump card
                        System.out.println("Cardcheck, Supertrump");
                        currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpName() + cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpDesc());
                        currentButtonChoice.add(cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpName());
                    }
                }
                String skipturn = "Skip Turn";
                currentButtonChoice.add(skipturn);       //add a skip button

                JOptionPane.showMessageDialog(null, playerArray[currentPlayer].getName() + "'s turn.");

                boolean repeatCardInput = true;
                while (repeatCardInput) {
                    tempCardChoice = JOptionPane.showOptionDialog(null, playerArray[currentPlayer].getName() + "\n\n" +
                                    "Current Trump Card: " + trumpList[trumpType-1] + "\nCurrent Card: " + cardDeck[biggestCard].getName() +
                            "\nCurrent Card's Value: " + cardDeck[biggestCard].getCardAttributes(trumpType) + "\n\n(Name,Hardness,Gravity,Cleavage,Crustal Abundance,Economical Value)\n" + currentHandCardString, "Input a card!",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, currentButtonChoice.toArray(), null);
                    // ^this one is to print the option pane and get input based on the position of the card^

                    if (tempCardChoice == playerArray[currentPlayer].getCardSize()) {        //if the player chooses skip
                        System.out.println("Running skip");
                        playerArray[currentPlayer].setEliminated(true);                     //they are eliminated
                        playerArray[currentPlayer].addCard(deckCode.get(0));                //and must draw a card
                        System.out.println(playerArray[currentPlayer].getName() + " Draw " + cardDeck[deckCode.get(0)].getName());
                        deckCode.remove(0);
                        repeatCardInput = false;                                            //and they skip turn
                        eliminatedPlayerCount += 1;                                         // +1 to eliminated player's counting
                        System.out.println("Successful skip");

                        if (deckCode.size() == 0){                                          //if deck is out of card
                            turnContinues = false;                                          //end the turn
                            gameContinue = false;                                           //end the game
                            JOptionPane.showMessageDialog(null, "Whoops, deck is out of card. \n\nGame over!\nNobody wins!");
                            System.out.println("Successful out-of-card situation");

                        }
                    }
                    else {
                        System.out.println("Running input");
                        cardChoice = playerArray[currentPlayer].getCard(tempCardChoice); //get the card code based on the player's hand
                        if (cardChoice < 54) {                                                                  //if it's not supertrump
                            if (biggestCardValue < cardDeck[cardChoice].getCardAttributeValue(trumpType)) {     //if the value is valid
                                System.out.println("Picked non-supertrump");
                                cardChoice = playerArray[currentPlayer].chooseCard(tempCardChoice);             //get the card code based on the player's hand
                                biggestCard = cardChoice;                                                       //change current biggest card to that card
                                biggestCardValue = cardDeck[biggestCard].getCardAttributeValue(trumpType);      //and the value too
                                repeatCardInput = false;                                                    //then exit loop

                                if (playerArray[currentPlayer].getCardSize() == 0) {
                                    gameContinue = false;
                                    JOptionPane.showMessageDialog(null, "You got no cards left, you win the game! The winner is " + playerArray[currentPlayer].getName());
                                }

                                System.out.println("Successful input");
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid Input, value must be bigger than current card!");  //if invalid, loop and ask again
                                // System.out.println("Successful retry prompt");}
                            }
                        }
                        else{
                            System.out.println("Picked Supertrump");                                //if they pick supertrump
                            cardChoice = playerArray[currentPlayer].chooseCard(tempCardChoice);
                            if (cardChoice ==59) {                                                  //specifically Geologist
                                trumpType = JOptionPane.showOptionDialog(null, "You picked Geologist! Pick a trump type! ", "Input a trump type!",
                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, trumpList, null);
                                trumpType += 1;
                            }
                            else if (cardChoice == 55){                                             //if you choose Geophycisist and you have Magnetite
                                String[] yesNo = {"Yes","No"};
                                if (playerArray[currentPlayer].getAllCard().contains(45)){
                                    int magneInstaWin = JOptionPane.showOptionDialog(null, "You have Magnetite! Do you want to throw" +
                                            " it in together to instantly win the round?", "Magnetite detected!",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,yesNo,null);
                                    if (magneInstaWin == 1){JOptionPane.showMessageDialog(null,"Alright, moving on.");}
                                    else {
                                        turnContinues = false;
                                        playerArray[currentPlayer].chooseCard(playerArray[currentWinner].getAllCard().indexOf(45));
                                        JOptionPane.showMessageDialog(null, "Alright, you win the round!");
                                        currentWinner = currentPlayer;
                                    }

                                }
                                trumpType = cardDeck[cardChoice].getTrumpValue();
                            }
                            else {
                                trumpType = cardDeck[cardChoice].getTrumpValue();
                            }

                            JOptionPane.showMessageDialog(null, "You picked " + cardDeck[cardChoice].getTrumpName() + "! Now " +
                                    "the trump attribute is " + trumpList[trumpType-1]);
                            biggestCard = cardChoice;
                            biggestCardValue = 0;
                            repeatCardInput = false;
                            for (int i = 0; i < playerNumber; i++){         //get everyone to be alive again after picking supertrump card
                                playerArray[i].setEliminated(false);
                            }
                            eliminatedPlayerCount = 0;
                        }

                    }

                    }//end of card input loop

                if (eliminatedPlayerCount == playerNumber-1){       //if everyone's alive but 1
                    turnContinues = false;                          //end the round
                    checkingPlayer = true;
                    while (checkingPlayer){                                             //doing this again to get the next player's code
                        if (currentPlayer == (playerNumber)){
                            currentPlayer = 0;}
                        else if (playerArray[currentPlayer].getEliminated())
                            currentPlayer += 1;
                        else
                            checkingPlayer = false;
                    }
                    currentWinner = currentPlayer;                  //and the winner of this round is the next player!
                    JOptionPane.showMessageDialog(null, "Everyone else skipped, the winner of this round is " +playerArray[currentPlayer].getName());
                }

                }//end of round loop



        }//end of game loop


    }   //end of public static void
}       //end of class