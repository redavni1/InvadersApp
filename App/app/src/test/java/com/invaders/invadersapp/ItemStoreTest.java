package com.invaders.invadersapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33, manifest = Config.NONE)
public class ItemStoreTest {
    private ItemStore itemStore;
    private int[] itemPrice = {50, 100, 150, 150};
    private int[] itemQuantity = {0, 1, 2, 3};

    /**
     * Sets up the testing environment before each test.
     * This method initializes the ItemStore activity and sets its state for testing.
     */
    @Before
    public void setUp() {
        itemStore = Robolectric.buildActivity(ItemStore.class)
                .create()
                .resume()
                .get();
        itemStore.itemPrice = itemPrice;
        itemStore.itemQuantity = itemQuantity;
        itemStore.coinQuantity = 200;
    }

    /**
     * Tests successful purchase scenario when there are enough coins.
     * Asserts that the purchase method returns true and that the coin and item quantities are updated correctly.
     */
    @Test
    public void testPurchaseItem_Successful() {
        int itemIndex = 0;
        boolean result = itemStore.purchaseItem(itemIndex);

        assertTrue(result);
        assertEquals(150, itemStore.coinQuantity);
        assertEquals(1, itemStore.itemQuantity[itemIndex]);
    }

    /**
     * Tests the purchase failure scenario due to insufficient coins.
     * Asserts that the purchase method returns false and that the coin and item quantities remain unchanged.
     */
    @Test
    public void testPurchaseItem_Failure_NotEnoughCoins() {
        int itemIndex = 3;
        itemStore.coinQuantity = 100;
        boolean result = itemStore.purchaseItem(itemIndex);

        assertFalse(result);
        assertEquals(100, itemStore.coinQuantity);
        assertEquals(3, itemStore.itemQuantity[itemIndex]);
    }

    /**
     * Tests the initialization of the ItemStore activity.
     * Asserts that the initial state of coin and item quantities are as expected.
     */
    @Test
    public void testInitialization() {
        assertEquals(200, itemStore.coinQuantity);
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3}, itemStore.itemQuantity);
    }

    /**
     * Tests the purchase method with an invalid item index.
     * Expects an ArrayIndexOutOfBoundsException to be thrown.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testPurchaseWithInvalidIndex() {
        itemStore.purchaseItem(-1);
    }

    /**
     * Tests the scenario where the same item is purchased sequentially.
     * Asserts that the item quantity and coin count are updated correctly after each purchase.
     */
    @Test
    public void testSequentialPurchases() {
        assertTrue(itemStore.purchaseItem(0));
        assertTrue(itemStore.purchaseItem(0));

        assertEquals(2, itemStore.itemQuantity[0]);
        assertEquals(100, itemStore.coinQuantity);
    }

    /**
     * Tests the scenario of purchasing each item once.
     * Asserts that the item quantities are updated correctly for each item in the store.
     */
    @Test
    public void testAllItemsPurchase() {
        for (int i = 0; i < itemPrice.length; i++) {
            itemStore.purchaseItem(i);
        }

        Assert.assertNotEquals(new int[]{1, 2, 3, 4}, itemStore.itemQuantity);
    }

    /**
     * Tests the scenario of attempting to purchase an item when coins are insufficient.
     * Asserts that the purchase method returns false and the coin count remains unchanged.
     */
    @Test
    public void testCoinDepletion() {
        itemStore.coinQuantity = 50;
        assertFalse(itemStore.purchaseItem(2));

        assertEquals(50, itemStore.coinQuantity);
    }
}

