package GreenDao3;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.liuwen.two.Bean.Book;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOK".
*/
public class BookDao extends AbstractDao<Book, Long> {

    public static final String TABLENAME = "BOOK";

    /**
     * Properties of entity Book.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property BookName = new Property(1, String.class, "bookName", false, "BOOK_NAME");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Url = new Property(3, String.class, "url", false, "URL");
        public final static Property ImageUrl = new Property(4, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property ChapterSize = new Property(5, String.class, "chapterSize", false, "CHAPTER_SIZE");
        public final static Property LastUpdateTime = new Property(6, String.class, "lastUpdateTime", false, "LAST_UPDATE_TIME");
        public final static Property LastChapterName = new Property(7, String.class, "lastChapterName", false, "LAST_CHAPTER_NAME");
        public final static Property SiteName = new Property(8, String.class, "siteName", false, "SITE_NAME");
        public final static Property AddBookTime = new Property(9, String.class, "addBookTime", false, "ADD_BOOK_TIME");
    }


    public BookDao(DaoConfig config) {
        super(config);
    }
    
    public BookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BOOK_NAME\" TEXT," + // 1: bookName
                "\"AUTHOR\" TEXT," + // 2: author
                "\"URL\" TEXT," + // 3: url
                "\"IMAGE_URL\" TEXT," + // 4: imageUrl
                "\"CHAPTER_SIZE\" TEXT," + // 5: chapterSize
                "\"LAST_UPDATE_TIME\" TEXT," + // 6: lastUpdateTime
                "\"LAST_CHAPTER_NAME\" TEXT," + // 7: lastChapterName
                "\"SITE_NAME\" TEXT," + // 8: siteName
                "\"ADD_BOOK_TIME\" TEXT);"); // 9: addBookTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(2, bookName);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(5, imageUrl);
        }
 
        String chapterSize = entity.getChapterSize();
        if (chapterSize != null) {
            stmt.bindString(6, chapterSize);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(7, lastUpdateTime);
        }
 
        String lastChapterName = entity.getLastChapterName();
        if (lastChapterName != null) {
            stmt.bindString(8, lastChapterName);
        }
 
        String siteName = entity.getSiteName();
        if (siteName != null) {
            stmt.bindString(9, siteName);
        }
 
        String addBookTime = entity.getAddBookTime();
        if (addBookTime != null) {
            stmt.bindString(10, addBookTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Book entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String bookName = entity.getBookName();
        if (bookName != null) {
            stmt.bindString(2, bookName);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(3, author);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(4, url);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(5, imageUrl);
        }
 
        String chapterSize = entity.getChapterSize();
        if (chapterSize != null) {
            stmt.bindString(6, chapterSize);
        }
 
        String lastUpdateTime = entity.getLastUpdateTime();
        if (lastUpdateTime != null) {
            stmt.bindString(7, lastUpdateTime);
        }
 
        String lastChapterName = entity.getLastChapterName();
        if (lastChapterName != null) {
            stmt.bindString(8, lastChapterName);
        }
 
        String siteName = entity.getSiteName();
        if (siteName != null) {
            stmt.bindString(9, siteName);
        }
 
        String addBookTime = entity.getAddBookTime();
        if (addBookTime != null) {
            stmt.bindString(10, addBookTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Book readEntity(Cursor cursor, int offset) {
        Book entity = new Book( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // bookName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // author
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // url
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // imageUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // chapterSize
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // lastUpdateTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // lastChapterName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // siteName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // addBookTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Book entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBookName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAuthor(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setImageUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChapterSize(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setLastUpdateTime(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLastChapterName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSiteName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setAddBookTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Book entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Book entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Book entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
