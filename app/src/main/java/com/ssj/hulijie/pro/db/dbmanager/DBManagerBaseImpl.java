package com.ssj.hulijie.pro.db.dbmanager;

import android.content.Context;

import com.ssj.hulijie.pro.db.helper.MyDatabaseHelper;
import com.ssj.hulijie.pro.db.helper.Orm;
import com.ssj.hulijie.utils.AppLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static com.ssj.hulijie.pro.db.helper.TemplateConfig.mappings;


/**
 * @param <T>
 * @author qufa
 */
public class DBManagerBaseImpl<T> implements DBManagerBase<T> {

    private MyDatabaseHelper helper;
    private Orm orm;

    public DBManagerBaseImpl(Context context, Class clsTemplate) {
        this.helper = new MyDatabaseHelper(context);
        this.orm = mappings.get(clsTemplate.getSimpleName() + ".orm.xml");
    }


    @Override
    public int deleteAll() {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method delete = daoCls.getMethod("deleteAll");
                Object result = delete.invoke(dao);
                return (int) result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }
        return 0;
    }

    @Override
    public int deleteLast() {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method delete = daoCls.getMethod("deleteLast");
                Object result = delete.invoke(dao);
                return (int) result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }

        return 0;
    }

    @Override
    public int count() {
        if (orm != null) {
            Class daoCls = null;
            try {
                daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method count = daoCls.getMethod("count", new Class[]{});
                Object invoke = count.invoke(dao, new Object[]{});
                return (int) invoke;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            AppLog.Log("没找到映射文件");
        }

        return 0;
    }

    @Override
    public long insertData(T t) {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method insert = daoCls.getMethod("insert", Object.class);
                Object result = insert.invoke(dao, t);
                AppLog.Log(result + "编号数据生成");
                return (long) result;
            } catch (Exception e) {
                AppLog.Log("插入数据失败");
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }
        return 0;
    }

    @Override
    public List<T> queryData() {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method insert = daoCls.getMethod("getAll", new Class[]{});
                Object result = insert.invoke(dao, new Object[]{});
                AppLog.Log((List) result + "");
                return (List) result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }
        return null;
    }


    @Override
    public int detete(T t) {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method delete = daoCls.getMethod("delete", Object.class);
                Object result = delete.invoke(dao, t);
                return (int) result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }

        return 0;
    }

    @Override
    public int update(T t) {
        if (orm != null) {
            try {
                Class daoCls = Class.forName(orm.getDaoName());
                Constructor constructor = daoCls.getDeclaredConstructor(new Class[]{MyDatabaseHelper.class});
                Object dao = constructor.newInstance(helper);
                Method update = daoCls.getMethod("update", Object.class);
                Object result = update.invoke(dao, t);
                return (int) result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLog.Log("没找到映射文件");
        }

        return 0;
    }
}
