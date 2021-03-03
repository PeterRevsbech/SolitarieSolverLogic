package com.company.model.move;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

public abstract class MoveType {

    //If it returns null, move was not possible
    public abstract SpecificMove getMove(ISolitaireState state);



}
