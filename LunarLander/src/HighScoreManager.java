import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class HighScoreManager {


    private ArrayList<PlayerScore> ar = new ArrayList<PlayerScore>();
    public void loadScores()
    {
        try
        {
            BufferedReader inFile = new BufferedReader(new FileReader("scores.txt"));
            String s = inFile.readLine();
            while(s !=null)
            {
                StringTokenizer st = new StringTokenizer(s);
                PlayerScore player = new PlayerScore(st.nextToken(), Integer.parseInt(st.nextToken()));
                ar.add(player);
                s =inFile.readLine();
            }
        }
        catch(FileNotFoundException e)
        {

        }
        catch(IOException e)
        {

        }
    }
    public void sortScores()
    {
        Collections.sort(ar);
    }
    public String getScores()
    {
        StringBuilder sb = new StringBuilder();
        for(PlayerScore player: ar)
            sb.append(player + "\n");
        return sb.toString();
    }



}
