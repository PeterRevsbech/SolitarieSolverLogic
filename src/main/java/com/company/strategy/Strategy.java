package com.company.strategy;

import com.company.model.move.*;
import com.company.model.move.movestypes.*;

import java.util.ArrayList;
import java.util.List;

public class Strategy {


    private List<MoveType> prioritizedMoveTypes;

    public Strategy() {
        prioritizedMoveTypes = new ArrayList<>();
        //Very primitive first strategy

        prioritizedMoveTypes.add(new WasteToFoundation());
        prioritizedMoveTypes.add(new TableauToFoundation());

        prioritizedMoveTypes.add(new TableauToTableau());
        prioritizedMoveTypes.add(new StockMove());


        prioritizedMoveTypes.add(new WasteToTableau());

        prioritizedMoveTypes.add(new FoundationToTableau());

    }

    public static Strategy endGameStrategy(){
        Strategy strategy=  new Strategy();
        strategy.setPrioritizedMoveTypes(new ArrayList<>());
        strategy.prioritizedMoveTypes.add(new WasteToFoundation());
        strategy.prioritizedMoveTypes.add(new TableauToFoundation());
        strategy.prioritizedMoveTypes.add(new StockMove());

        return strategy;
    }

    public List<MoveType> getPrioritizedMoveTypes() {
        return prioritizedMoveTypes;
    }

    public void setPrioritizedMoveTypes(List<MoveType> prioritizedMoveTypes) {
        this.prioritizedMoveTypes = prioritizedMoveTypes;
    }
}
