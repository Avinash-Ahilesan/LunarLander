public class PlayerScore implements Comparable<PlayerScore>{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score <0)
            this.score=0;
        else
            this.score=score;
    }
    public PlayerScore(String name, int score)
    {
        setName(name);
        setScore(score);
    }
    public String toString()
    {
        return name + " " + score;
    }


    private String name;
    private int score;

    @Override
    public int compareTo(PlayerScore o) {
        return o.score-this.score;
    }
}
