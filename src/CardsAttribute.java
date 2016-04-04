
public class CardsAttribute {

// Name = 0,Hardness = 1,Gravity = 2,Cleavage = 3,Crustal Abundance = 4,EcoValue = 5:
    public String printCardDetails(String line, int attCode){

        String currentCardStat[] = line.split(",");
        return currentCardStat[attCode];
}

    public String getName(String line){
        String currentCardStat[] = line.split(",");
        return currentCardStat[0];
    }
    public String getHardness(String line){

        String currentCardStat[] = line.split(",");
        return currentCardStat[1];
    }
    public String getGravity(String line){

        String currentCardStat[] = line.split(",");
        return currentCardStat[2];
    }
    public String getCleavage(String line){

        String currentCardStat[] = line.split(",");
        return currentCardStat[3];
    }
    public String getAbundance(String line){

        String currentCardStat[] = line.split(",");
        return currentCardStat[4];
    }
    public String getEcoValue(String line){

        String currentCardStat[] = line.split(",");
        return currentCardStat[5];
    }




}