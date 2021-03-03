package com.company.strategy;

import com.company.model.move.*;

import java.util.ArrayList;
import java.util.List;

public class Strategy {


    private List<MoveType> prioritizedMoveTypes;

    public Strategy(){
        prioritizedMoveTypes = new ArrayList<>();
        prioritizedMoveTypes.add(new TableauToTableau());
        prioritizedMoveTypes.add(new TableauToFoundation());
        prioritizedMoveTypes.add(new FoundationToTableau());
        prioritizedMoveTypes.add(new StockMove());

    }

    public List<MoveType> getPrioritizedMoveTypes() {
        return prioritizedMoveTypes;
    }

    public void setPrioritizedMoveTypes(List<MoveType> prioritizedMoveTypes) {
        this.prioritizedMoveTypes = prioritizedMoveTypes;
    }
}
