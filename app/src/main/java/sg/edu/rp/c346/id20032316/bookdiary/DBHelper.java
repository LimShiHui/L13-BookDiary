package sg.edu.rp.c346.id20032316.bookdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TABLE_BOOK = "book";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_BOOK_AUTHOR = "book_author";
    private static final String COLUMN_BOOK_REVIEW = "book_review";
    private static final String COLUMN_BOOK_LENGTH = "book_length";
    private static final String COLUMN_BOOK_STAR = "book_star";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createIslandSql = "CREATE TABLE " + TABLE_BOOK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BOOK_TITLE + " TEXT,"
                + COLUMN_BOOK_AUTHOR + " TEXT,"
                + COLUMN_BOOK_REVIEW + " TEXT,"
                + COLUMN_BOOK_LENGTH + " TEXT,"
                + COLUMN_BOOK_STAR + " REAL)";
        db.execSQL(createIslandSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);

    }

    public  long insertBook(String title, String author, String review, String length, float star) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_REVIEW, review);
        values.put(COLUMN_BOOK_LENGTH, length);
        values.put(COLUMN_BOOK_STAR, star);

        long result = db.insert(TABLE_BOOK, null, values);
        db.close();
        return result;
    }

    public ArrayList<Book> getAllBook() {
        ArrayList<Book> books = new ArrayList<Book>();


        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_BOOK_TITLE + ","
                + COLUMN_BOOK_AUTHOR + ","
                + COLUMN_BOOK_REVIEW + ","
                + COLUMN_BOOK_LENGTH
                + ","
                + COLUMN_BOOK_STAR
                + " FROM " + TABLE_BOOK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String bookTitle = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                String bookReview = cursor.getString(3);
                String bookLength = cursor.getString(4);
                float bookStar = cursor.getFloat(5);
                 Book note = new Book(id, bookTitle, bookAuthor, bookReview, bookLength, bookStar);
                books.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }

    public int updateBook(Book data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, data.getTitle());
        values.put(COLUMN_BOOK_AUTHOR, data.getAuthor());
        values.put(COLUMN_BOOK_REVIEW, data.getReview());
        values.put(COLUMN_BOOK_LENGTH, data.getLength());
        values.put(COLUMN_BOOK_STAR, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_BOOK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteBook(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_BOOK, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Book> getAllBooksByStar(int starFilter) {
        ArrayList<Book> books = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_BOOK_TITLE, COLUMN_BOOK_AUTHOR, COLUMN_BOOK_REVIEW, COLUMN_BOOK_LENGTH, COLUMN_BOOK_STAR};
        String condition = COLUMN_BOOK_STAR + " >= ?";
        String[] args = {String.valueOf(starFilter)};

        Cursor cursor = db.query(TABLE_BOOK, columns, condition, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String bookTitle = cursor.getString(1);
                String bookAuthor = cursor.getString(2);
                String bookReview = cursor.getString(3);
                String bookLength = cursor.getString(4);
                float bookStar = cursor.getFloat(5);
                Book note = new Book(id, bookTitle, bookAuthor, bookReview, bookLength, bookStar);
                books.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return books;
    }

}
