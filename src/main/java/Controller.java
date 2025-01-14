import java.util.ArrayList;
/**
 * This class controls our game flow.
 * @author Jonathan Ma
 * @version 1.0
 */
public class Controller {
    private GameS22 game; //controller has a game and a text view for gui
    private TextView textView;

    public Controller(){
        this.game = setUpGameModel();
        this.textView = new TextView();
        game.getGameBoard().plantMine();
        textView.updateView(this.game);
    }

    public void playGame() {
        //New Objective Modification
        int mineTotal = 1;
        System.out.println("-- A landmine was planted! --");
        while(!game.isGameEnded()) {
            int round = game.getRound(); //making sure we only plant mines on DIFFERENT ROUNDS!
            textView.getNextPlayersAction(this.game); //get action
            if (!Rules.checkValidAction(game, textView.getRow1(), textView.getColumn1(),
                    textView.getRow2(), textView.getColumn2(), textView.getAction())) { //check validity
                System.out.println("Invalid Move!");
            } else {
                carryOutAction(textView.getRow1(), textView.getColumn1(), textView.getRow2(), textView.getColumn2(), textView.getAction());
                //New Objective Modification
                //checks if a piece is on a landmine, and destroys it if so.
                if (game.getBoardSquares()[textView.getRow2()][textView.getColumn2()].isLandMine()
                        && !game.getBoardSquares()[textView.getRow2()][textView.getColumn2()].isEmpty()) {
                    game.getOpponentTeam().removePieceFromTeam(
                    game.getBoardSquares()[textView.getRow2()][textView.getColumn2()].getPiece()
                    );
                    game.getBoardSquares()[textView.getRow2()][textView.getColumn2()].removePiece();
                    game.getBoardSquares()[textView.getRow2()][textView.getColumn2()].setRevealed();
                    System.out.println("Your Piece was destroyed by a Land Mine!");
                }
            }

            //New Objective Modification
            //after round 4, land mines will quickly populate the board. Max is 36 for gameplay’s sake.
            if (game.getRound() >= 5 && round != game.getRound() && mineTotal < 36) {
                for (int i = 0; i < game.getRound() - 3; i++) {
                    game.getGameBoard().plantMine();
                    mineTotal++;
                } //displays message to user telling how many land mines are planted and how many populate the board
                System.out.println("--- " + (game.getRound() - 3) + " landmines were planted! ("
                        + mineTotal + " mines have been planted in total) ---");
            }
            else{
                if (mineTotal == 1) {
                    System.out.println("--- There is " + mineTotal + " landmine on the board ---");
                } else {
                    System.out.println("--- There are " + mineTotal + " landmines on the board ---");
                }
            }

            textView.updateView(this.game);
        }
        game.getGameBoard().revealMines();
        textView.updateView(this.game);
        if (game.isAWinner()) {
            textView.printEndOfGameMessage(this.game); //prints winner
        } else {
            System.out.println("It's a tie! The game is over, and there is no winner"); //no winner ? it's a tie
        }
    }

    public void carryOutAction(int row1, int column1, int row2, int column2, char action){
        switch(action) {
            case 'M':
                ActionMove actionMove = new ActionMove(this.game, row1, column1, row2, column2);
                actionMove.performAction();
                break;
            case 'A':
                ActionAttack actionAttack = new ActionAttack(this.game, row1, column1, row2, column2);
                actionAttack.performAction();
                break;
            case 'R':
                ActionRecruit actionRecruit = new ActionRecruit(this.game, row1, column1, row2, column2);
                actionRecruit.performAction();
                break;
            case 'S':
                ActionSpawn actionSpawn = new ActionSpawn(this.game, row1, column1, row2, column2);
                actionSpawn.performAction();
                break;
                //New Action Modification
            case 'H':
                ActionHastyStrike actionHastyStrike = new ActionHastyStrike(this.game, row1, column1, row2, column2);
                actionHastyStrike.performAction();
        }
    }
    public GameS22 setUpGameModel(){
        // Create 4 pieces for team A

        // Load the pieces in an ArrayList
        ArrayList<Piece> piecesTeamA = new ArrayList<>();
        piecesTeamA.add(new PieceMinion('M',"Blu",
                0,0,false,true));
        piecesTeamA.add(new PieceBuzz('B',"Blu",2,1,
                true,false,true));
        piecesTeamA.add(new PieceBlueHen('H',"Blu",2,
                9,false,true));
        piecesTeamA.add(new PieceEvilMinion('E',"Blu",1,
                0,0,false, true));
        piecesTeamA.add(new PieceGoblin('G',"Blu", false, false, 0,
                0, true));
        // Create a team object
        Team teamA = new Team("Blu",piecesTeamA);

        // Create 4 pieces for team B
        // Load the pieces in an ArrayList
        ArrayList<Piece> piecesTeamB = new ArrayList<>();
        piecesTeamB.add(new PieceMinion('M',"Red",
                0,0,false,true));
        piecesTeamB.add(new PieceBlueHen('H',"Red",3,
                9,false,true));
        piecesTeamB.add(new PieceBuzz('B',"Red",2,1,
                true,false,true));
        piecesTeamB.add(new PieceEvilMinion('E',"Red",1,
                1,4,false, true));
        piecesTeamB.add(new PieceGoblin('G',"Red", false, false, 0,
                0, true));
        // Create a team object
        Team teamB = new Team("Red",piecesTeamB);

        // Create an instance of the game
        return new GameS22(8, 8,teamA, teamB);
    }

    public static void main(String[] args){
        Controller controller = new Controller();
        controller.playGame();
    }
}
