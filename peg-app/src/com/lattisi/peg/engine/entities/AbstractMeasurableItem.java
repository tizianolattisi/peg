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

    @Override // XXX
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMeasurableItem)) return false;

        AbstractMeasurableItem that = (AbstractMeasurableItem) o;

        if( that.getMeasure()!=null && this.getMeasure()!=null ){
            System.out.println("/n/n****************\nEQUALS WITH MEASURABLE!/n****************\n" +
                    "\n");
            return this.getMeasure().equals(this.getMeasure());
        }

        return false;
    }

}
