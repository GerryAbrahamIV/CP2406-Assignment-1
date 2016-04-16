
public class CardsAttribute {

    private String cardLine;

    public void setCardDetails(String cardLine){
        this.cardLine = cardLine;
    }

    public String printCardDetail() {
        return this.cardLine;
    }

    public String getCardAttributes(int Type){                  //// Name = 0, Hardness = 1, Gravity = 2, Cleavage = 3, Crustal Abundance = 4, EcoValue = 5:
        String currentCardStat[] = this.cardLine.split(",");
        return currentCardStat[Type];
    }

    public Double getCardAttributeValue(int Type) {
        String currentCardStat[] = this.cardLine.split(",");
        double value;
        if ((Type == 1) || (Type == 2)) {                       //hardness or gravity, just convert raw string into double
            value = Double.parseDouble(currentCardStat[Type]);
        }
        else if (Type == 3) {                                   //Cleavage. ranking: none, poor/none, 1 poor, 2 poor, 1 good 1 poor,
            if (currentCardStat[3].equals("none"))              // 2 good, 3 good, 1 perfect, 1 perfect 1 good, 1 perfect 2 good, 2 perfect 1 good,
                value = 0;                                      // 3 perfect, 4 perfect, 6 perfect
            else if (currentCardStat[3].equals("poor/none"))
                value = 1;
            else if (currentCardStat[3].equals("1 poor"))
                value = 2;
            else if (currentCardStat[3].equals("2 poor"))
                value = 3;
            else if (currentCardStat[3].equals("1 good/1 poor"))
                value = 4;
            else if (currentCardStat[3].equals("2 good"))
                value = 5;
            else if (currentCardStat[3].equals("3 good"))
                value = 6;
            else if (currentCardStat[3].equals("1 perfect"))
                value = 7;
            else if (currentCardStat[3].equals("1 perfect/1 good"))
                value = 8;
            else if (currentCardStat[3].equals("1 perfect/2 good"))
                value = 9;
            else if (currentCardStat[3].equals("2 perfect/1 good"))
                value = 10;
            else if (currentCardStat[3].equals("3 perfect"))
                value = 11;
            else if (currentCardStat[3].equals("4 perfect"))
                value = 12;
            else value = 13;
        }
        else if (Type == 4) {
            if (currentCardStat[4].equals("ultratrace"))
                value = 0;
            else if (currentCardStat[4].equals("trace")) {
                value = 1;
            }
            else if (currentCardStat[4].equals("low")) {
                value = 2;
            }
            else if (currentCardStat[4].equals("moderate")) {
                value = 3;
            }
            else if (currentCardStat[4].equals("high")) {
                value = 4;
            }
            else value = 5;
        }
        else {                                                //economic value
            if (currentCardStat[5].equals("trivial"))
                value = 0;
            else if (currentCardStat[5].equals("low")) {
                value = 1;
            }
            else if (currentCardStat[5].equals("moderate")) {
                value = 2;
            }
            else if (currentCardStat[5].equals("high")) {
                value = 3;
            }
            else if (currentCardStat[5].equals("very high")) {
                value = 4;
            }
            else value = 5;
        }

    return value;
    }

    public String getName(){
        String currentCardStat[] = this.cardLine.split(",");
        return currentCardStat[0];
    }






        }




    /*
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
    */



