package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:13
 */
public class Item implements IItem {

    private String name;
    private ItemType type;
    private String metric=null;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public String getTypeName() {
        return type.name();
    }

    @Override
    public String getMetric() {
        return metric;
    }

    @Override
    public void setMetric(String metric) {
        this.metric = metric;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().concat(name) + " (" + super.hashCode() +")" + "metric: " + metric;
    }
}
