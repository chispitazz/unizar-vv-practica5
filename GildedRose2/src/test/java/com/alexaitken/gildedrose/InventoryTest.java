package com.alexaitken.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alexaitken.gildedrose.Inventory;
import com.alexaitken.gildedrose.Item;


public class InventoryTest {

    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";

    private Inventory createInventory(Item... items) {
        return new Inventory(items);
    }

    //P3
    @Test
    public void should_never_change_quality_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertEquals(80, sulfuras.getQuality());
    }
    //P3
    @Test
    public void should_never_change_sellIn_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertEquals(0, sulfuras.getSellIn());
    }

    //P9
    @Test
    public void should_lower_the_sellIn_by_one_for_normal_items() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(9, normalItem.getSellIn());
    }
    //P9
    @Test
    public void should_lower_the_quality_by_one_for_normal_items() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(19, normalItem.getQuality());
    }
    //9b
    @Test
    public void should_not_lower_the_quality_below_zero() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 0);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }

    //P10
    @Test
    public void should_lower_the_quality_twice_as_fast_once_the_sell_in_date_has_passed() {
        Item normalItem = new Item("+5 Dexterity Vest", -1, 25);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(23, normalItem.getQuality());
    }

    //P1
    @Test
    public void should_increase_the_quality_of_aged_brie_as_it_gets_older() {
        Item agedBrie = new Item("Aged Brie", 10, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(26, agedBrie.getQuality());
    }

    //P1b
    @Test
    public void should_not_increase_the_quality_of_aged_brie_over_50() {
        Item agedBrie = new Item("Aged Brie", 10, 50);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(50, agedBrie.getQuality());
    }

    //P2
    @Test
    public void should_increase_the_quality_of_aged_brie_below_50() {
        Item agedBrie = new Item("Aged Brie", -8, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(27, agedBrie.getQuality());
    }

    //P2
    @Test
    public void should_decrease_the_sell_in_of_aged_brie_below_50() {
        Item agedBrie = new Item("Aged Brie", -8, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(-9, agedBrie.getSellIn());
    }

    //P2b
    @Test
    public void should_not_increase_the_quality_of_aged_brie_at_50() {
        Item agedBrie = new Item("Aged Brie", -8, 50);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(50, agedBrie.getQuality());
    }

    //P2c
    @Test
    public void should_not_increase_the_quality_of_aged_brie_over_50_2() {
        Item agedBrie = new Item("Aged Brie", -8, 49);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(50, agedBrie.getQuality());
    }

    //P2d
    @Test
    public void should_decrease_the_sell_in_of_aged_brie_bellow_50() {
        Item agedBrie = new Item("Aged Brie", 0, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(-1, agedBrie.getSellIn());
    }


    //No valida
    @Test
    public void should_lower_backstage_passes_to_zero_quality_once_concert_has_happened() {
        Item backStagePass = new Item(BACKSTAGE_PASSES, -1, 20);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(0, backStagePass.getQuality());
    }

    //P7, P8
    @Test
    public void should_lower_backstage_passes_to_zero_quality_once_concert_has_happened_limit_cases(){
        Item backStagePass0DaysAway = new Item(BACKSTAGE_PASSES, 0, 25);
        Item backStagePassNegativeDaysAway = new Item(BACKSTAGE_PASSES, -8, 0);
        Inventory inventory = createInventory( backStagePass0DaysAway,
                backStagePassNegativeDaysAway);
        inventory.updateQuality();
        assertEquals(0, backStagePass0DaysAway.getQuality());
        assertEquals(0, backStagePassNegativeDaysAway.getQuality());
        assertEquals(-9, backStagePassNegativeDaysAway.getSellIn());
    }

    //P4
    @Test
    public void should_increase_backstage_passes_quality_by_1_when_the_concert_is_more_than_10_days_away() {
        Item backStagePass = new Item(BACKSTAGE_PASSES, 11, 20);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(21, backStagePass.getQuality());
    }

    //P5
    @Test
    public void should_increase_backstage_passes_quality_by_2_when_the_concert_is_10_days_or_less_away() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 27);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(29, backStagePass.getQuality());
    }
    //P6
    @Test
    public void should_increase_backstage_passes_quality_by_3_when_the_concert_is_5_days_or_less_away() {
        Item backStagePass = new Item(BACKSTAGE_PASSES, 5, 44);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(47, backStagePass.getQuality());
    }

    //P4b, P5c, P6d
    @Test
    public void should_not_increase_backstage_passes_above_a_quality_of_50() {
        Item backStagePassMoreThan10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50);
        Item backStagePass10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        Item backStagePass5DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        Inventory inventory = createInventory(backStagePassMoreThan10DaysAway, backStagePass10DaysAway, backStagePass5DaysAway);
        inventory.updateQuality();
        assertEquals(50, backStagePassMoreThan10DaysAway.getQuality());
        assertEquals(50, backStagePass10DaysAway.getQuality());
        assertEquals(50, backStagePass5DaysAway.getQuality());
    }

    //P5b, P5d, P5f, P6b, P6c, P6f, P6g, P6h
    @Test
    public void should_not_increase_backstage_passes_above_a_quality_of_50_other_limit_cases() {
        Item backStagePass10DaysAway50Quality = new Item(BACKSTAGE_PASSES, 10, 50);
        Item backStagePass6DaysAway = new Item(BACKSTAGE_PASSES, 6, 25);
        Item backStagePass6DaysAway49Quality = new Item(BACKSTAGE_PASSES, 6, 49);
        Item backStagePass5DaysAway50Quality = new Item(BACKSTAGE_PASSES, 5, 50);
        Item backStagePass5DaysAway49Quality = new Item(BACKSTAGE_PASSES, 5, 49);
        Item backStagePass1DayAway50Quality = new Item(BACKSTAGE_PASSES, 1, 50);
        Item backStagePass1DayAway49Quality = new Item(BACKSTAGE_PASSES, 1, 49);
        Item backStagePass1DayAway48Quality = new Item(BACKSTAGE_PASSES, 1, 48);
        Inventory inventory = createInventory( backStagePass10DaysAway50Quality,
                backStagePass6DaysAway,
                backStagePass6DaysAway49Quality,
                backStagePass5DaysAway50Quality,
                backStagePass5DaysAway49Quality,
                backStagePass1DayAway50Quality,
                backStagePass1DayAway49Quality,
                backStagePass1DayAway48Quality);
        inventory.updateQuality();
        assertEquals(50, backStagePass10DaysAway50Quality.getQuality());
        assertEquals(27, backStagePass6DaysAway.getQuality());
        assertEquals(50, backStagePass6DaysAway49Quality.getQuality());
        assertEquals(50, backStagePass5DaysAway50Quality.getQuality());
        assertEquals(50, backStagePass5DaysAway49Quality.getQuality());
        assertEquals(50, backStagePass1DayAway48Quality.getQuality());
        assertEquals(50, backStagePass1DayAway49Quality.getQuality());
        assertEquals(50, backStagePass1DayAway50Quality.getQuality());
    }

    //P6e
    @Test
    public void should_increase_backstage_passes_quality_by_3_when_the_concert_is_5_days_or_less_away_limit_case() {
        Item backStagePass1DayAway = new Item(BACKSTAGE_PASSES, 1, 25);
        Inventory inventory = createInventory(backStagePass1DayAway);
        inventory.updateQuality();
        assertEquals(28, backStagePass1DayAway.getQuality());
    }

    //P10d
    @Test
    public void sell_in_negative_quality_lower_by_two() {
        Item normalItem = new Item("+5 Dexterity Vest", 0, 25);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(-1, normalItem.getSellIn());
    }

    //P10c
    @Test
    public void should_lower_sell_in_by_one_quality_never_changes() {
        Item normalItem = new Item("+5 Dexterity Vest", -8, 0);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }


    //P10b
    @Test
    public void should_lower_sell_in_by_one_quality_lower_by_one() {
        Item normalItem = new Item("+5 Dexterity Vest", -8, 1);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }


    @Test
    public void coverage_100_percent() {
        Item normalItem = new Item("Sulfuras, Hand of Ragnaros", -8, 1);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(1, normalItem.getQuality());
    }
}
