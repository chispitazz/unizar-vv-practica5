package com.alexaitken.gildedrose;

public class Item {

    private String name;
    private int sellIn;

    private int quality;

    public Item(final String name, final int sellIn, final int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getSellIn() {
        return this.sellIn;
    }

    public final void setSellIn(final int sellIn) {
        this.sellIn = sellIn;
    }

    public final int getQuality() {
        return this.quality;
    }

    public final void setQuality(final int quality) {
        this.quality = quality;
    }
}