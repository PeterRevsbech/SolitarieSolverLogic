
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.moves;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;

public abstract class MoveType {

    /**
     * @param state
     * @return SpecificMove of the desired MoveType. If null, means move was not possible
     */
    //If it returns null, move was not possible
    public abstract SpecificMove getMove(ISolitaireState state);

    public abstract String toString();

}
