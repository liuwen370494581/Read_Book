package GreenDao3;

import com.example.liuwen.two.Bean.Book;
import com.example.liuwen.two.Bean.Catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/15 10:10
 * desc   :
 */
public class BookDaoHolder {

    public static void insert(Book model) {
        DaoManager.getInstance().getDaoSession().getBookDao().insert(model);
    }

    public static void deleteByModel(Book model) {
        DaoManager.getInstance().getDaoSession().getBookDao().delete(model);
    }

    public static void deleteAllData() {
        DaoManager.getInstance().getDaoSession().getBookDao().deleteAll();
    }

    public static void update(Book model) {
        DaoManager.getInstance().getDaoSession().getBookDao().update(model);
    }

    public static List<Book> query() {
        List<Book> list;
        list = DaoManager.getInstance().getDaoSession().getBookDao().queryBuilder().list();
        Collections.sort(list, (book1, book2) -> book2.getAddBookTime().compareTo(book1.getAddBookTime()));
        return list;
    }

    public static long getCount() {
        return DaoManager.getInstance().getDaoSession().getBookDao().count();
    }
}
