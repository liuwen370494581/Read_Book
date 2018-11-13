package GreenDao3;

import com.example.liuwen.two.Bean.Catalog;

import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/13 15:58
 * desc   :
 */
public class CatalogDaoHolder {

    public static void insert(Catalog model) {
        DaoManager.getInstance().getDaoSession().getCatalogDao().insert(model);
    }

    public static void deleteByModel(Catalog model) {
        DaoManager.getInstance().getDaoSession().getCatalogDao().delete(model);
    }

    public static void deleteAllData() {
        DaoManager.getInstance().getDaoSession().getCatalogDao().deleteAll();
    }

    public static void update(Catalog model) {
        DaoManager.getInstance().getDaoSession().getCatalogDao().update(model);
    }

    public static List<Catalog> query() {
        List<Catalog> list = new ArrayList<>();
        list = DaoManager.getInstance().getDaoSession().getCatalogDao().queryBuilder().list();
        return list;
    }

    public static long getCount() {
        return DaoManager.getInstance().getDaoSession().getCatalogDao().count();
    }

}
