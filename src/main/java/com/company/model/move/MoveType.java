package com.company.model.move;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

public abstract class MoveType {

    /**
     *
     * @param state
     * @return SpecificMove of the desired MoveType. If null, means move was not possible
     */
    //If it returns null, move was not possible
    public abstract SpecificMove getMove(ISolitaireState state);



}
