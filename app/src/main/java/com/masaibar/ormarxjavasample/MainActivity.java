package com.masaibar.ormarxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ItemAdapter mAdapter;
    private List<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    private void setupListView() {
        ListView listView = (ListView) findViewById(R.id.list_view);
        mItems = getItems();
        mAdapter = new ItemAdapter(this, android.R.layout.simple_list_item_1, mItems);
        listView.setAdapter(mAdapter);

        getItemsByRx();
    }

    private void setupButtons() {
        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = Item.create(UUID.randomUUID().toString());
                Item.add(getApplicationContext(), item);
            }
        });

        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item.clearAll(getApplicationContext());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<Item> getItems() {
        return getOrma()
                .selectFromItem()
                .toList();
    }

    private void getItemsByRx() {
        Observable<Item_Selector> observable = getOrma().relationOfItem().createQueryObservable();
        observable
                .flatMap(new Function<Item_Selector, ObservableSource<Item>>() {
                             @Override
                             public ObservableSource<Item> apply(@NonNull Item_Selector items) throws Exception {
                                 return items.executeAsObservable();
                             }
                         }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Item, Item>() {
                    @Override
                    public Item apply(@NonNull Item item) throws Exception {
                        return item;
                    }
                })
                .subscribe(new Consumer<Item>() {
                    @Override
                    public void accept(Item item) throws Exception {
                        Log.d("!!!!!!!!!", item.text);
                        mAdapter.add(item);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private OrmaDatabase getOrma() {
        return OrmaUtil.getInstance(getApplicationContext());
    }
}
