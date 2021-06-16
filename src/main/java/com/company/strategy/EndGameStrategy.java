
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.strategy;

import com.company.models.moves.*;
import com.company.models.moves.movestypes.*;

import java.util.ArrayList;
import java.util.List;

public class EndGameStrategy {


    private List<MoveType> prioritizedMoveTypes;

    public EndGameStrategy(){
        this.setPrioritizedMoveTypes(new ArrayList<>());
        this.prioritizedMoveTypes.add(new WasteToFoundation());
        this.prioritizedMoveTypes.add(new TableauToFoundation());
        this.prioritizedMoveTypes.add(new StockMove());
    }

    public List<MoveType> getPrioritizedMoveTypes() {
        return prioritizedMoveTypes;
    }

    public void setPrioritizedMoveTypes(List<MoveType> prioritizedMoveTypes) {
        this.prioritizedMoveTypes = prioritizedMoveTypes;
    }
}
