import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Javassignment {
    public static void main(String[] args) {

        List<String> cardList = new ArrayList<>();

        //Start of file reading
        String file_name = "card.txt";
        try {
            ReadFile file = new ReadFile(file_name);
            String[] cardLines = file.OpenFile();

            int i;

            for (i = 0; i < cardLines.length; i++) //loop the lines
            {
                String currentCardStat[] = cardLines[i].split(",");     //
                // Name = 0,Hardness = 1,Gravity = 2,Cleavage = 3,Crustal Abundance = 4,EcoValue = 5
                System.out.println("Name: " + currentCardStat[0] + ", Hardness: " + currentCardStat[1] + ", Gravity: " + currentCardStat[2]+ ", Cleavage: " + currentCardStat[3]); //testing

                cardList.add(cardLines[i]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //end of file reading

        int cardCode;

        CardsAttribute cardDeck = new CardsAttribute();

        String cardCodeString = JOptionPane.showInputDialog("Card code:");


        cardCode = Integer.parseInt(cardCodeString);

        boolean repeatValue;
        String cardDetails;
        repeatValue = true;
        cardDetails = "";

        while (repeatValue) {
            repeatValue = false;
            String cardAttString = JOptionPane.showInputDialog("Card Attribute, Name = 0,Hardness = 1,Gravity = 2,Cleavage = 3,Crustal Abundance = 4,EcoValue = 5: ");
            int cardAtt = Integer.parseInt(cardAttString);
            switch (cardAtt) {
                case 0:
                    cardDetails = cardDeck.getName(cardList.get(cardCode)); break;
                case 1:
                    cardDetails = cardDeck.getHardness(cardList.get(cardCode)); break;
                case 2:
                    cardDetails = cardDeck.getGravity(cardList.get(cardCode)); break;
                case 3:
                    cardDetails = cardDeck.getCleavage(cardList.get(cardCode)); break;
                case 4:
                    cardDetails = cardDeck.getAbundance(cardList.get(cardCode)); break;
                case 5:
                    cardDetails = cardDeck.getEcoValue(cardList.get(cardCode)); break;
                default:
                    repeatValue = true;
                    JOptionPane.showMessageDialog(null, "Invalid input!");
                    break;

            }
        }


        JOptionPane.showMessageDialog(null, cardDetails);



    }
}