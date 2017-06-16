package com.ssj.hulijie.pro.db.dao;


import com.ssj.hulijie.pro.db.helper.MyDatabaseHelper;
import com.ssj.hulijie.pro.db.model.ItemSearchHistory;

public class SearchHistoryDao extends BaseDao<ItemSearchHistory> {

    public SearchHistoryDao(MyDatabaseHelper helper) {
        super(ItemSearchHistory.class, helper);
    }


}
