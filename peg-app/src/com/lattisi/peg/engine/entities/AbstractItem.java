package com.lattisi.peg.engine.entities;

/**
 * User: tiziano
 * Date: 05/10/13
 * Time: 13:13
 */
public abstract class AbstractItem implements Item {

    private String name;
    private ItemType type;

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
    public String toString() {
        return "<" + this.getClass().getSimpleName() + " " + name + " at " + super.hashCode() + ">";
    }
}