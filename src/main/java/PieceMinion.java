public class PieceMinion extends Piece implements Recruiter{
    /**
     * Piece based on a minion from Despicable Me
     * @author Jonathan Ma
     * @version 1.0
     */

    //fields unique to minions: number of recruits, and number of times this piece has called spawn()
    protected int numRecruits;
    protected int numTimesSpawned;
    //static, maximum number of times this piece can spawn
    public static final int MAX_NUM_SPAWNED = 3;

    //constructors
    public PieceMinion(char symbol, String teamColor,
                       int numRecruits, int numTimesSpawned,
                       boolean hidden, boolean original) {
        super(symbol, teamColor, hidden, original);
        this.numRecruits = numRecruits;
        this.numTimesSpawned = numTimesSpawned;
    }

    public PieceMinion(){
        this('M',"- -",
                0,0,
                false,true);
    }
    //getters
    public int getNumRecruits() {return numRecruits;}
    public int getNumTimesSpawned() {return numTimesSpawned;}
    //setters
    public void setNumRecruits(int numRecruits) {
        this.numRecruits = numRecruits;
    }

    @Override
    public boolean validRecruitPath(int row1, int row2, int column1, int column2) {
        return true;
    }

    //see Piece.java for formal definition: an instance of PieceMinion will print "Bello!" to console when speak() is called
    @Override
    public void speak(){
        System.out.println("Bello!");
    }

    //see Piece.java for formal definition
    @Override
    public boolean validMovePath(int fromSquareRow, int fromSquareCol,
                                 int toSquareRow, int toSquareCol) {
        // You will implement this method in a later step
        // each Piece will have a different valid path
        return true;
    }

    /**
     * see Piece.java for formal definition, PieceMinion will spawn a new PieceMinion with the same teamColor as
     * its original PieceMinion, the lowercased symbol of its original PieceMinion numRecruits=1, numTimesSpawned=0,
     * hidden=false, original=false
     * @return New PieceMinion
     */
    @Override
    public PieceMinion spawn(){
        if(canSpawn()) {
            this.numTimesSpawned++;
            return new PieceMinion(Character.toLowerCase(this.symbol),
                    this.teamColor, 1,
                    0,
                    false,
                    false);
        }
        else{
            return null;
        }
    }

    /**
     * A PieceMinion can spawn if it is the original and if it has not spawned MAX_NUM_SPAWNED of times
     * @return boolean representing if this PieceMinion can spawn more PieceMinions
     */
    public boolean canSpawn(){
        return original && numTimesSpawned < MAX_NUM_SPAWNED;
    }

}