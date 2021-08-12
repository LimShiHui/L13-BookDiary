package sg.edu.rp.c346.id20032316.bookdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;

public class ModifyBook extends AppCompatActivity {

    EditText etID, etTitle, etAuthor, etReview;
    RadioGroup rgLength;
    RatingBar ratingStars;
    Button btnUpdate, btnDelete, btnCancel;
    Book books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_book);

        etID = findViewById(R.id.etID);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthors);
        etReview = findViewById(R.id.etReview);
        rgLength = findViewById(R.id.rgLength);
        ratingStars = findViewById(R.id.ratingBar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        books = (Book) intent.getSerializableExtra("book");

        etID.setText(books.get_id() + "");
        etID.setEnabled(false);
        etTitle.setText(books.getTitle());
        etAuthor.setText(books.getAuthor());
        etReview.setText(books.getReview());

        if (books.getLength().equalsIgnoreCase("long")) {
            rgLength.check(R.id.rbLong);
        } else if (books.getLength().equalsIgnoreCase("short")) {
            rgLength.check(R.id.rbShort);
        }

        ratingStars.setRating(books.getStars());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyBook.this);
                books.setTitle(etTitle.getText().toString());
                books.setAuthor(etAuthor.getText().toString());
                books.setReview(etReview.getText().toString());
                if (rgLength.getCheckedRadioButtonId() == R.id.rbLong) {
                    books.setLength("long");
                } else if (rgLength.getCheckedRadioButtonId() == R.id.rbShort) {
                    books.setLength("short");
                }
                float numStars = ratingStars.getRating();
                books.setStars(numStars);

                dbh.updateBook(books);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyBook.this);
                dbh.deleteBook(books.get_id());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}