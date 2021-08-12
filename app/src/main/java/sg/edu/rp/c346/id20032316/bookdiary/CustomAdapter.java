package sg.edu.rp.c346.id20032316.bookdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    int id;
    ArrayList<Book> bookList;

    public CustomAdapter(@NonNull Context context, int resource, ArrayList<Book> object) {
        super(context, resource, object);
        this.context = context;
        this.id = resource;
        this.bookList = object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvAuthor = rowView.findViewById(R.id.tvAuthor);
        TextView tvReview = rowView.findViewById(R.id.tvReview);
        RadioGroup rgLength = rowView.findViewById(R.id.rgLength); // no use
        ImageView ivLength = rowView.findViewById(R.id.imageView);
        RatingBar ratingBar = rowView.findViewById(R.id.ratingBar);

        Book currentBook = bookList.get(position);

        tvTitle.setText(currentBook.getTitle());
        tvAuthor.setText(currentBook.getAuthor());
        tvReview.setText(currentBook.getReview());
        if (currentBook.getLength().equalsIgnoreCase("long")) {
            ivLength.setImageResource(R.drawable.long_image);
        } else if (currentBook.getLength().equalsIgnoreCase("short")) {
            ivLength.setImageResource(R.drawable.short_image);
        }

        ratingBar.setEnabled(false);
        float stars = currentBook.getStars();
        ratingBar.setRating(stars);

        return rowView;
    }
}
