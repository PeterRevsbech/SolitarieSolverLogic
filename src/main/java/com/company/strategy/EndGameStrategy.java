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
