package com.alexaitken.gildedrose;

class Inventory {

    private static final int INT = 50;
    private static final int SELLIN11 = 11;
    private static final int SELLIN6 = 6;
    public static final String BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert";
    public static final String BRIE = "Aged Brie";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String VEST = "+5 Dexterity Vest";
    private final Item[] items;

    public Inventory(final Item[] items) {
        this.items = items.clone();
    }


    public final void updateQuality() {
        for (final Item item : this.items) {
            if (!BRIE.equals(item.getName())
                    && !BACKSTAGE.equals(item.getName())) {
                if (0 < item.getQuality()) {
                    if (!SULFURAS.equals(item.getName())) {
                        item.setQuality(item.getQuality() - 1);
                    }
                }
            } else {
                if (INT > item.getQuality()) {
                    item.setQuality(item.getQuality() + 1);

                    if (BACKSTAGE.equals(item.getName())) {
                        if (SELLIN11 > item.getSellIn()) {
                            if (INT > item.getQuality()) {
                                item.setQuality(item.getQuality() + 1);
                            }
                        }

                        if (SELLIN6 > item.getSellIn()) {
                            if (INT > item.getQuality()) {
                                item.setQuality(item.getQuality() + 1);
                            }
                        }
                    }
                }
            }

            if (!SULFURAS.equals(item.getName())) {
                item.setSellIn(item.getSellIn() - 1);
            }

            if (0 > item.getSellIn()) {
                if (BRIE.equals(item.getName())) {
                    if (INT > item.getQuality()) {
                        item.setQuality(item.getQuality() + 1);
                    }
                } else {
                    if (BACKSTAGE.equals(item.getName())) {
                        item.setQuality(0);
                    } else {
                        if (0 < item.getQuality()) {
                            if (!SULFURAS.equals(item.getName())) {
                                item.setQuality(item.getQuality() - 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
