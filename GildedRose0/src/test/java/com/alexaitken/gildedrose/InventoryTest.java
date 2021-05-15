package com.alexaitken.gildedrose;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class InventoryTest {

    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";

    public InventoryTest() {
    }

    private static Inventory createInventory(final Item... items) {
        return new Inventory(items);
    }

    //P3
    @Test
    public void should_never_change_quality_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertThat(sulfuras.getQuality(), is(80));
    }
    //P3
    @Test
    public void should_never_change_sellIn_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertThat(sulfuras.getSellIn(), is(0));
    }

    //P9
    @Test
    public final void should_lower_the_sellIn_by_one_for_normal_items() {
        final Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        final Inventory inventory = InventoryTest.createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getSellIn(), is(9));
    }
    //P9
    @Test
    public final void should_lower_the_quality_by_one_for_normal_items() {
        final Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        final Inventory inventory = InventoryTest.createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(19));
    }
    //9b
    @Test
    public final void should_not_lower_the_quality_below_zero() {
        final Item normalItem = new Item("+5 Dexterity Vest", 10, 0);
        final Inventory inventory = InventoryTest.createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(0));
    }

    //P10
    @Test
    public final void should_lower_the_quality_twice_as_fast_once_the_sell_in_date_has_passed() {
        final Item normalItem = new Item("+5 Dexterity Vest", -1, 25);
        final Inventory inventory = InventoryTest.createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(23));
    }

    //P1
    @Test
    public final void should_increase_the_quality_of_aged_brie_as_it_gets_older() {
        final Item agedBrie = new Item("Aged Brie", 10, 25);
        final Inventory inventory = InventoryTest.createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getQuality(), is(26));
    }

    //P1b
    @Test
    public final void should_not_increase_the_quality_of_aged_brie_over_50() {
        final Item agedBrie = new Item("Aged Brie", 10, 50);
        final Inventory inventory = InventoryTest.createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getQuality(), is(50));
    }

    //P2
    @Test
    public final void should_increase_the_quality_of_aged_brie_below_50() {
        final Item agedBrie = new Item("Aged Brie", -8, 25);
        final Inventory inventory = InventoryTest.createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getQuality(), is(27));
    }

    //P2
    @Test
    public void should_decrease_the_sell_in_of_aged_brie_below_50() {
        Item agedBrie = new Item("Aged Brie", -8, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getSellIn(), is(-9));
    }

    //P2b
    @Test
    public final void should_not_increase_the_quality_of_aged_brie_at_50() {
        final Item agedBrie = new Item("Aged Brie", -8, 50);
        final Inventory inventory = InventoryTest.createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getQuality(), is(50));
    }

    //P2c
    @Test
    public final void should_not_increase_the_quality_of_aged_brie_over_50_2() {
        final Item agedBrie = new Item("Aged Brie", -8, 49);
        final Inventory inventory = InventoryTest.createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getQuality(), is(50));
    }

    //P2d
    @Test
    public void should_decrease_the_sell_in_of_aged_brie_bellow_50() {
        Item agedBrie = new Item("Aged Brie", 0, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertThat(agedBrie.getSellIn(), is(-1));
    }


    //No valida
    @Test
    public final void should_lower_backstage_passes_to_zero_quality_once_concert_has_happened() {
        final Item backStagePass = new Item(BACKSTAGE_PASSES, -1, 20);
        final Inventory inventory = InventoryTest.createInventory(backStagePass);
        inventory.updateQuality();
        assertThat(backStagePass.getQuality(), is(0));
    }

    //P7, P8
    @Test
    public void should_lower_backstage_passes_to_zero_quality_once_concert_has_happened_limit_cases(){
        Item backStagePass0DaysAway = new Item(BACKSTAGE_PASSES, 0, 25);
        Item backStagePassNegativeDaysAway = new Item(BACKSTAGE_PASSES, -8, 0);
        Inventory inventory = createInventory( backStagePass0DaysAway,
                backStagePassNegativeDaysAway);
        inventory.updateQuality();
        assertThat(backStagePass0DaysAway.getQuality(), is(0));
        assertThat(backStagePassNegativeDaysAway.getQuality(), is(0));
        assertThat(backStagePassNegativeDaysAway.getSellIn(), is(-9));
    }

    //P4
    @Test
    public final void should_increase_backstage_passes_quality_by_1_when_the_concert_is_more_than_10_days_away() {
        final Item backStagePass = new Item(BACKSTAGE_PASSES, 11, 20);
        final Inventory inventory = InventoryTest.createInventory(backStagePass);
        inventory.updateQuality();
        assertThat(backStagePass.getQuality(), is(21));
    }

    //P5
    @Test
    public final void should_increase_backstage_passes_quality_by_2_when_the_concert_is_10_days_or_less_away() {
        final Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 27);
        final Inventory inventory = InventoryTest.createInventory(backStagePass);
        inventory.updateQuality();
        assertThat(backStagePass.getQuality(), is(29));
    }
    //P6
    @Test
    public final void should_increase_backstage_passes_quality_by_3_when_the_concert_is_5_days_or_less_away() {
        final Item backStagePass = new Item(BACKSTAGE_PASSES, 5, 44);
        final Inventory inventory = InventoryTest.createInventory(backStagePass);
        inventory.updateQuality();
        assertThat(backStagePass.getQuality(), is(47));
    }

    //P4b, P5c, P6d
    @Test
    public final void should_not_increase_backstage_passes_above_a_quality_of_50() {
        final Item backStagePassMoreThan10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50);
        final Item backStagePass10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        final Item backStagePass5DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        final Inventory inventory = InventoryTest.createInventory(backStagePassMoreThan10DaysAway, backStagePass10DaysAway, backStagePass5DaysAway);
        inventory.updateQuality();
        assertThat(backStagePassMoreThan10DaysAway.getQuality(), is(50));
        assertThat(backStagePass10DaysAway.getQuality(), is(50));
        assertThat(backStagePass5DaysAway.getQuality(), is(50));
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
        assertThat(backStagePass10DaysAway50Quality.getQuality(), is(50));
        assertThat(backStagePass6DaysAway.getQuality(), is(27));
        assertThat(backStagePass6DaysAway49Quality.getQuality(), is(50));
        assertThat(backStagePass5DaysAway50Quality.getQuality(), is(50));
        assertThat(backStagePass5DaysAway49Quality.getQuality(), is(50));
        assertThat(backStagePass1DayAway48Quality.getQuality(), is(50));
        assertThat(backStagePass1DayAway49Quality.getQuality(), is(50));
        assertThat(backStagePass1DayAway50Quality.getQuality(), is(50));
    }

    //P6e
    @Test
    public void should_increase_backstage_passes_quality_by_3_when_the_concert_is_5_days_or_less_away_limit_case() {
        Item backStagePass1DayAway = new Item(BACKSTAGE_PASSES, 1, 25);
        Inventory inventory = createInventory(backStagePass1DayAway);
        inventory.updateQuality();
        assertThat(backStagePass1DayAway.getQuality(), is(28));
    }

    //P10d
    @Test
    public void sell_in_negative_quality_lower_by_two() {
        Item normalItem = new Item("+5 Dexterity Vest", 0, 25);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getSellIn(), is(-1));
    }

    //P10c
    @Test
    public void should_lower_sell_in_by_one_quality_never_changes() {
        Item normalItem = new Item("+5 Dexterity Vest", -8, 0);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(0));
    }


    //P10b
    @Test
    public void should_lower_sell_in_by_one_quality_lower_by_one() {
        Item normalItem = new Item("+5 Dexterity Vest", -8, 1);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(0));
    }


    @Test
    public final void coverage_100_percent() {
        final Item normalItem = new Item("Sulfuras, Hand of Ragnaros", -8, 1);
        final Inventory inventory = InventoryTest.createInventory(normalItem);
        inventory.updateQuality();
        assertThat(normalItem.getQuality(), is(1));
    }
}
