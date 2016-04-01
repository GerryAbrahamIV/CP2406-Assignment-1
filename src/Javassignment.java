import java.io.IOException;


public class Javassignment

{
    public static void main(String[] args) throws IOException {
                //Start of file reading
        String file_name = "card.txt";
        try {
            ReadFile file = new ReadFile(file_name);
            String[] cardLines = file.OpenFile();

            int i, x;

            for ( i=0; i < cardLines.length; i++ )
            {   String currentCardStat[] = cardLines[i].split(",");
                // Name = 0,Hardness = 1,Gravity = 2,Cleavage = 3,Crustal Abundance = 4,EcoValue = 5
                System.out.println("Name: " + currentCardStat[0] + ", Hardness: " + currentCardStat[1] + ", Gravity: " + currentCardStat[2]); //testing


            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        //end of file reading




    }
}