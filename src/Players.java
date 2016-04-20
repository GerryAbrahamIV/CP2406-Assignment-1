import java.util.ArrayList;
import java.util.List;

public class Players {
        private String name;
        private ArrayList<Integer> handCard = new ArrayList<>();
        private boolean eliminated = false;
        ArrayList nameList = new ArrayList();

    public Players(){

        this.eliminated = false;
    }

    public void setName(String name){       //set player name
        this.name = name;
    }

    public String getName(){                //get player name
        return this.name;
    }


    public void addCard(int cardCode){      //add card code to the player's hand card
        this.handCard.add(cardCode);
    }

    public ArrayList getAllCard() {         //return an arraylist of the player's hand card

        return (this.handCard);
    }

    public Integer getCard(int cardPlace){  //return a specific card by inputting the specific card's placement
        return (this.handCard.get(cardPlace));
    }

    public Integer chooseCard(int cardPlace){           //return a specific card's code from hand and removing them from hand
        int chosenCard = this.handCard.get(cardPlace);
        this.handCard.remove(cardPlace);
        return chosenCard;
    }

    public Integer getCardSize(){                   //return the number of cards in the hand
        return this.handCard.size();
    }

    public void setEliminated(boolean truefalse){   //set if the player can still play in the round.
        this.eliminated = truefalse;
    }

    public boolean getEliminated(){                 //checks if the player already cannot go anymore
        return this.eliminated;
    }


}
