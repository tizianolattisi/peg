package com.lattisi.peg.engine.entities;

import java.util.Collection;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 21:20
 */
public interface Container {

    public Collection<? extends Item> getChildren();
    public Boolean contains(Item item);
    public default Integer getDensity(){
        Integer density=0;
        for( Item child: getChildren() ){
            if( child instanceof Measurable && ((Measurable) child).getMeasure() != null ){
                density++;
            }
        }
        return density;
    };

}
