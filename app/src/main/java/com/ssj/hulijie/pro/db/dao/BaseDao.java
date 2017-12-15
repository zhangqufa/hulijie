package com.ssj.hulijie.pro.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ssj.hulijie.pro.db.helper.Item;
import com.ssj.hulijie.pro.db.helper.Key;
import com.ssj.hulijie.pro.db.helper.MyDatabaseHelper;
import com.ssj.hulijie.pro.db.helper.Orm;
import com.ssj.hulijie.pro.db.helper.TemplateConfig;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author qufa
 */
public class BaseDao<T> {
    private Class clsTemplate;
    private MyDatabaseHelper helper;
    private Orm orm;

    public BaseDao(Class clsTemplate, MyDatabaseHelper helper) {
        super();
        this.clsTemplate = clsTemplate;
        this.helper = helper;
        orm = TemplateConfig.mappings.get(clsTemplate.getSimpleName() + ".orm.xml");
    }

    public int count() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select count(*) from " + orm.getTableName();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete(orm.getTableName(), null, null);
        return delete;
    }


    public int update(T t) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //获得主键
        Key key = orm.getKey();
        System.out.println("~~~~~~key.isIdentity: " + key.isIdentity());
        //这里是判断 inIdenity 如果为false 说明不是自增长， 需要赋值，如果为true ，则不需要赋值
        if (!key.isIdentity()) {
            //获得方法对象
            Method method = values.getClass().getDeclaredMethod("put", String.class, Class.forName(key.getType()));
            //获得对应的主键值
            Field field = t.getClass().getDeclaredField(key.getProperty());
            field.setAccessible(true);
            Object value = field.get(t);
            //调用方法
            method.invoke(values, key.getColumn(), value);
        }
        List<Item> items = orm.getItems();
        for (Item item : items) {
            //获得方法对象
            Method method = values.getClass().getDeclaredMethod("put", String.class, Class.forName(item.getType()));
            //获得对应的属性值
            Field field = t.getClass().getDeclaredField(item.getProperty());
            field.setAccessible(true);
            Object value = field.get(t);
            //调用方法
            method.invoke(values, item.getColumn(), value);
        }
        //获得对应的主键值
        Field field = t.getClass().getDeclaredField(key.getProperty());
        field.setAccessible(true);
        Object value = field.get(t);
        String[] args = {String.valueOf(value)};
        int update = db.update(orm.getTableName(), values, key.getColumn() + "=?", args);
        db.close();
        return update;
    }


    public long insert(T t) throws NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //获得主键
        Key key = orm.getKey();
        //如果主键不自动生成 则放入到ContentValues里去
        if (key != null && !key.isIdentity()) {
            //获得方法对象
            Method method = values.getClass().getDeclaredMethod("put", String.class, Class.forName(key.getType()));
            //获得对应的主键值
            Field field = t.getClass().getDeclaredField(key.getProperty());
            field.setAccessible(true);
            Object value = field.get(t);
            //调用方法
            method.invoke(values, key.getColumn(), value);
        }
        List<Item> items = orm.getItems();
        for (Item item : items) {
            //获得方法对象
            Method method = values.getClass().getDeclaredMethod("put", String.class, Class.forName(item.getType()));
            //获得对应的属性值
            Field field = t.getClass().getDeclaredField(item.getProperty());
            field.setAccessible(true);
            Object value = field.get(t);
            //调用方法
            method.invoke(values, item.getColumn(), value);
        }
        long id = db.insert(orm.getTableName(), null, values);
        db.close();
        return id;
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try {
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query(orm.getTableName(), null, null, null, null, null, "addtime DESC");
            Key key = orm.getKey();
            List<Item> items = orm.getItems();
            Class cursorCls = cursor.getClass();
            while (cursor.moveToNext()) {
                T t = (T) clsTemplate.newInstance();
                //先获得key的值  先获取 cursor的get方法，之后给t对象赋值
                int index = cursor.getColumnIndex(key.getColumn());
                Method method = cursorCls.getMethod(TemplateConfig.methodMappings.get(key.getType()), int.class);
                Field field = clsTemplate.getDeclaredField(key.getProperty());
                field.setAccessible(true);
                Object value = method.invoke(cursor, index);
                field.set(t, value);
                //获得其他属性的
                for (Item item : items) {
                    index = cursor.getColumnIndex(item.getColumn());
                    method = cursorCls.getMethod(TemplateConfig.methodMappings.get(item.getType()), int.class);
                    field = clsTemplate.getDeclaredField(item.getProperty());
                    field.setAccessible(true);
                    value = method.invoke(cursor, index);
                    field.set(t, value);
                }
                list.add(t);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int delete(T t) throws NoSuchFieldException, IllegalAccessException {
        SQLiteDatabase db = helper.getWritableDatabase();
        Key key = orm.getKey();
        System.out.println("~~~~~~key.isIdentity: " + key.isIdentity());
        //获得对应的主键值
        Field field = t.getClass().getDeclaredField(key.getProperty());
        field.setAccessible(true);
        Object value = field.get(t);
        String[] args = {String.valueOf(value)};
        int affect_index = db.delete(orm.getTableName(), key.getColumn() + "=?", args);
        db.close();
        return affect_index;
    }


    public int deleteLast() throws NoSuchFieldException, IllegalAccessException {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select min(addtime) from " + orm.getTableName();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        long count = cursor.getLong(0);
        int delete = db.delete(orm.getTableName(), "addtime = ?", new String[]{String.valueOf(count)});
        cursor.close();
        db.close();
        return delete;
    }

}
