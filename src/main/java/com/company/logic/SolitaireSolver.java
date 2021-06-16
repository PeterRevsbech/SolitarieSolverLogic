
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.logic;

import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.moves.movestypes.StockMove;
import com.company.models.moves.movestypes.TableauToFoundation;
import com.company.models.moves.movestypes.WasteToFoundation;
import com.company.models.states.ISolitaireState;
import com.company.strategy.TreeSearcher;

import java.util.ArrayList;
import java.util.List;

public class SolitaireSolver {

    private final int fixedDepth;
    private final long timeLimitMillis;
    private final List<MoveType> endGameStrategy = new ArrayList<>();

    public SolitaireSolver(int fixedDepth, long timeLimitMillis) {
        this.endGameStrategy.add(new WasteToFoundation());
        this.endGameStrategy.add(new TableauToFoundation());
        this.endGameStrategy.add(new StockMove());
        this.fixedDepth = fixedDepth;
        this.timeLimitMillis = timeLimitMillis;
    }

    // determine best move
    public SpecificMove bestPossibleMove(Solitaire game) {
        ISolitaireState state = game.getLastState();

        SpecificMove move = null;

        if (game.isAllCardsFaceUp(state)) {
            //find move
            for (MoveType moveType : endGameStrategy) {
                move = moveType.getMove(state);
                if (move != null) {
                    return move;
                }
            }
        }

        long t0 = System.currentTimeMillis();
        int depth = 2; //Start with a depth of 2 and increase as long as there is time

        while (fixedDepth != -1 || System.currentTimeMillis() - t0 < timeLimitMillis) { //While the time limit has not yet been exceeded - start a new round
            if (fixedDepth != -1) { //If fixed depth is set - use it
                depth = fixedDepth;
            }
            TreeSearcher treeSearcher = new TreeSearcher(state, depth);
            treeSearcher.buildTree(treeSearcher.getRoot(), depth);
            move = treeSearcher.evaluateTree(treeSearcher.getRoot(), depth);
            depth++;
            if (fixedDepth != -1) { //If fixed depth is set - do no more iterations
                break;
            }
        }


        //Return move
        return move;
    }
}
