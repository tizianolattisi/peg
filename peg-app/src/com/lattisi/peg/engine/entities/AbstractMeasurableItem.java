package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 16/01/14
 * Time: 19:05
 */
public abstract class AbstractMeasurableItem extends AbstractItem implements Measurable {

    private String measure =null;

    @Override
    public String getMeasure() {
        return measure;
    }

    @Override
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public boolean isSimilarTo(Measurable item) {
        if( item.getMeasure()!=null && this.getMeasure()!=null ){
            return this.getMeasure().equals(this.getMeasure());
        }
        return false;
    }
}
