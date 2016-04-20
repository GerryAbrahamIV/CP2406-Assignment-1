import javax.swing.*;
import java.io.IOException;
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
        //      finally start game here?

        boolean gameContinue = true;    //is the game still continuing?
        int currentWinner = 0;          //who was the last hand's winner? by default is Player 1 at first, which is playerArray[0]
        String currentHandCardString;
        JOptionPane.showMessageDialog(null, "Game start! Player 1 goes first");


        ArrayList currentButtonChoice = new ArrayList();

        while (gameContinue){           //loop while game is still continuing

            for (int i = 0; i < playerNumber;i++){
                playerArray[i].setEliminated(false);        //making sure everyone is alive at the start of the round
            }
            int tempCardChoice; //temporary storage on where the card in the hand is, NOT the card's code
            int cardChoice;     //get what the card's code is
            String[] trumpList = {"Hardness", "Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};
            int biggestCard;        //the biggest card's code
            int trumpType;          //the current card's trump type
            double biggestCardValue;   //the biggest card's value
            int eliminatedPlayerCount = 0;  //counting how many players have been eliminated on a round. Will reset after every round
            boolean turnContinues = true;

            currentButtonChoice.clear();
            currentHandCardString = "";
            //54 hardness, 55 gravity magnetite, 56 cleavage, 57 crust.abun, 58 ecovalue, 59 choose
            for (int i=0; i < playerArray[currentWinner].getCardSize();i++) {                                       //setting what will be shown to player
                if (playerArray[currentWinner].getCard(i) < 54) {               //if it's not a supertrump card
                    currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(0) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(1)
                            + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(2) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(3)
                            + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(4) + ", " + cardDeck[playerArray[currentWinner].getCard(i)].getCardAttributes(5));     //to print card details
                    currentButtonChoice.add(cardDeck[playerArray[currentWinner].getCard(i)].getName());
                }
                else{                   //if it's a supertrump card
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
                trumpType = JOptionPane.showOptionDialog(null, cardDeck[cardChoice].printCardDetail() +
                        "\nPick a Trump type",  "Pick a Trump type", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, trumpList, trumpList[0]);
                trumpType += 1;
                biggestCardValue = cardDeck[biggestCard].getCardAttributeValue(trumpType);      //if it's not supertrump
            }
            else {
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
            if (playerArray[currentWinner].getCardSize() == 0) {
                turnContinues = false;
                gameContinue = false;
                JOptionPane.showMessageDialog(null, "You got no cards left, you win the game! The winner is " + playerArray[currentWinner].getName());
            }

            //54 hardness, 55 gravity 45 magnetite, 56 cleavage, 57 crust.abun, 58 ecovalue, 59 choose
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

                currentButtonChoice.clear();
                currentHandCardString = "";                                             //reading the player's hand card
                for (int i=0; i < playerArray[currentPlayer].getCardSize();i++) {
                    if (playerArray[currentPlayer].getCard(i) < 54) {               //if it's not a supertrump card
                        currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(0) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(1)
                                + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(2) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(3)
                                + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(4) + ", " + cardDeck[playerArray[currentPlayer].getCard(i)].getCardAttributes(5)
                        );     //to print card details
                        currentButtonChoice.add(cardDeck[playerArray[currentPlayer].getCard(i)].getName());
                    } else {                   //if it's a supertrump card
                        currentHandCardString += ("\nCard " + (i + 1) + ": " + cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpName() + cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpDesc());
                        currentButtonChoice.add(cardDeck[playerArray[currentPlayer].getCard(i)].getTrumpName());
                    }
                }
                String skipturn = "Skip Turn";
                currentButtonChoice.add(skipturn);       //add a skip button

                JOptionPane.showMessageDialog(null, playerArray[currentPlayer].getName() + "'s turn.");
                int tempBiggestCard = biggestCard;
                boolean repeatCardInput = true;
                while (repeatCardInput) {
                    tempCardChoice = JOptionPane.showOptionDialog(null, playerArray[currentPlayer].getName() + "\n\n" +
                                    "Current Trump Card: " + trumpList[trumpType-1] + "\nCurrent Card: " + cardDeck[biggestCard].getName() +
                            "\nCurrent Card's Value: " + cardDeck[biggestCard].getCardAttributes(trumpType) + "\n\n(Name,Hardness,Gravity,Cleavage,Crustal Abundance,Economical Value)\n" + currentHandCardString, "Input a card!",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, currentButtonChoice.toArray(), null);
                    // ^this one is to print the option pane and get input based on the position of the card^

                    if (tempCardChoice == playerArray[currentPlayer].getCardSize()) {        //if the player chooses skip
                        playerArray[currentPlayer].setEliminated(true);                     //they are eliminated
                        JOptionPane.showMessageDialog(null, "Skipped turn, you draw a card: " + cardDeck[deckCode.get(0)].getName());
                        playerArray[currentPlayer].addCard(deckCode.get(0));                //and must draw a card
                        deckCode.remove(0);
                        repeatCardInput = false;                                            //and they skip turn
                        eliminatedPlayerCount += 1;                                         // +1 to eliminated player's counting

                        if (deckCode.size() == 0){                                          //if deck is out of card
                            turnContinues = false;                                          //end the turn
                            gameContinue = false;                                           //end the game
                            JOptionPane.showMessageDialog(null, "Whoops, deck is out of card. \n\nGame over!\nNobody wins!");
                        }
                    }
                    else {
                        cardChoice = playerArray[currentPlayer].getCard(tempCardChoice); //get the card code based on the player's hand
                        if (cardChoice < 54) {                                                                  //if it's not supertrump
                            if (biggestCardValue < cardDeck[cardChoice].getCardAttributeValue(trumpType)) {     //if the value is valid
                                cardChoice = playerArray[currentPlayer].chooseCard(tempCardChoice);             //get the card code based on the player's hand
                                biggestCard = cardChoice;                                                       //change current biggest card to that card
                                biggestCardValue = cardDeck[biggestCard].getCardAttributeValue(trumpType);      //and the value too
                                repeatCardInput = false;                                                    //then exit loop
                            }
                            else {
                                JOptionPane.showMessageDialog(null, "Invalid Input, value must be bigger than current card!");  //if invalid, loop and ask again
                            }
                        }
                        else{                             //if they pick supertrump
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
                                    if (magneInstaWin == 1){JOptionPane.showMessageDialog(null,"Alright, moving on.");} //if player choose not to throw magnetite
                                    else {                                                                              //if player choose to throw magnetite
                                        turnContinues = false;
                                        playerArray[currentPlayer].chooseCard(playerArray[currentWinner].getAllCard().indexOf(45));
                                        JOptionPane.showMessageDialog(null, "Alright, you win the round!");             //instawin
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
                            biggestCardValue = cardDeck[tempBiggestCard].getCardAttributeValue(trumpType);

                            repeatCardInput = false;
                            for (int i = 0; i < playerNumber; i++){         //get everyone to be alive again after picking supertrump card
                                playerArray[i].setEliminated(false);
                            }
                            eliminatedPlayerCount = 0;                      //reset 'alive' counter
                        }//end of supertrump pick
                        if (playerArray[currentPlayer].getCardSize() == 0) {
                            turnContinues = false;
                            gameContinue = false;
                            JOptionPane.showMessageDialog(null, "You got no cards left, you win the game! The winner is " + playerArray[currentPlayer].getName());
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