package com.masaibar.ormarxjavasample;

import android.content.Context;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class Item {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public String text;

    public static Item create(String text) {
        Item item = new Item();
        item.text = text;
        return item;
    }

    public static void add(final Context context, final Item item) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma(context).insertIntoItem(item);
            }
        });
    }

    public static void delete(final Context context, final long id) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma(context).deleteFromItem().idEq(id);
            }
        });
    }

    public static void clearAll(final Context context) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma(context).deleteFromItem().execute();
            }
        });
    }

    private static OrmaDatabase getOrma(Context context) {
        return OrmaUtil.getInstance(context);
    }
}
