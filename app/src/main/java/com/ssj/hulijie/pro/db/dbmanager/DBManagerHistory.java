package com.ssj.hulijie.pro.db.dbmanager;

import android.content.Context;

import com.ssj.hulijie.pro.db.model.ItemSearchHistory;


/**
 * Created by vic on 2016/12/2.
 */

public class DBManagerHistory extends DBManagerBaseImpl<ItemSearchHistory> {

    public DBManagerHistory(Context context) {
        super(context,ItemSearchHistory.class);
    }
}
