package com.ssj.hulijie.pro.db.dbmanager;

import java.util.List;

/**
 * Created by vic on 2016/12/2.
 */

public interface DBManagerBase<T> {

    /**
     * 插入数据
     * @param t
     * @return
     */
    long insertData(T t);

    /**
     * 查找所有数据
     * @return
     */
    List<T> queryData();

    /**
     * 删除指定数据
     * @param t
     * @return
     */
    int detete(T t);


    /**
     * 删除最旧的一个数据
     * @return
     */
    int deleteLast();

    /**
     * 更新数据
     * @param t
     * @return
     */

    int update(T t);


    /**
     * 获取数据总数
     * @return
     */
    int count();

    /**
     * 删除全部数据
     * @return
     */
    int deleteAll();


}
