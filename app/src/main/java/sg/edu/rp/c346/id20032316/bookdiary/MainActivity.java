package sg.edu.rp.c346.id20032316.bookdiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn5Stars, btnInsert;
    ListView lv;
    ArrayList<Book> al;
    CustomAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn5Stars = findViewById(R.id.btn5Stars);
        btnInsert = findViewById(R.id.btnInsert);
        lv = findViewById(R.id.lvBook);

        al = new ArrayList<Book>();
        ca = new CustomAdapter(MainActivity.this, R.layout.row, al);
        lv.setAdapter(ca);

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllBooksByStar(5));
                ca.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = al.get(position);
                Intent intent = new Intent(MainActivity.this, ModifyBook.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.insert, null);

                final EditText etTitle = viewDialog.findViewById(R.id.etTitle);
                final EditText etAuthor = viewDialog.findViewById(R.id.etAuthors);
                final EditText etReview = viewDialog.findViewById(R.id.etReview);
                final RadioGroup rgLength = viewDialog.findViewById(R.id.rgLength);
                final RatingBar ratingBar = viewDialog.findViewById(R.id.ratingBar);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert");
                myBuilder.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etTitle.getText().toString();
                        String author = etAuthor.getText().toString();
                        String review = etReview.getText().toString();
                        int rbSelected = rgLength.getCheckedRadioButtonId();
                        String length = "";
                        if (rbSelected == 1) {
                            length = "Long";
                        } else if (rbSelected == 2) {
                            length = "Short";
                        }
                        float stars = ratingBar.getRating();

                        DBHelper dbh = new DBHelper(MainActivity.this);
                        long inserted_id = dbh.insertBook(title, author, review, length, stars);

                        if (inserted_id != -1){
                            Toast.makeText(MainActivity.this, "Insert successful",
                                    Toast.LENGTH_SHORT).show();
                        }

                        al.clear();
                        al.addAll(dbh.getAllBook());
                        ca.notifyDataSetChanged();
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(MainActivity.this);
        al.clear();
        al.addAll(dbh.getAllBook());
        ca.notifyDataSetChanged();
    }

}