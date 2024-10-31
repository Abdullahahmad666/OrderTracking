package com.example.navigation_smd_7a;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private boolean isScheduled;
    private boolean isDelivered;

    // Constructor with the 'isScheduled' flag
    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects, boolean isScheduled,boolean isDelivered) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.isScheduled = isScheduled;
        this.isDelivered = isDelivered;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        // Find views
        TextView tvTitle = v.findViewById(R.id.tvProductTitle);
        ImageView ivDelete = v.findViewById(R.id.ivDelete);
        Button btnAction = v.findViewById(R.id.btnConfirm); // Assuming btnConfirm is the button ID

        // Get the current product
        Product p = getItem(position);
        tvTitle.setText(p.getTitle() + " : " + p.getPrice());

        // Set button text based on the fragment context
        if (isScheduled) {
            // If in ScheduleFragment, hide delete icon, set button to "Deliver"
            ivDelete.setVisibility(View.GONE);
            btnAction.setText("Deliver");
            btnAction.setBackgroundColor(0xFF77DD77);
        } else {
            // In NewOrderFragment, show delete icon, set button to "Confirm"
            ivDelete.setVisibility(View.VISIBLE);
            btnAction.setText("Confirm");
        }
        if (isDelivered) {
            // If in ScheduleFragment, hide delete icon, set button to "Deliver"
            ivDelete.setVisibility(View.GONE);
            btnAction.setText("Delivered");
            btnAction.setEnabled(false);
            btnAction.setBackgroundColor(0xFFFF5722);
        }

        // Set button click listener based on button action
        // ProductAdapter.java
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDB db = new ProductDB(context);
                db.open();

                if (isScheduled) {
                    // Deliver action (Optional: You may add functionality for delivering if needed)
                    db.updateStatus(p.getId(), "delivered"); // Example status change for delivered products
                } else {
                    // Confirm action: Move to ScheduleFragment
                    db.updateStatus(p.getId(), "scheduled"); // Update status to "scheduled"
                }

                db.close();

                // Remove the product from the current fragment's list
                remove(p);
                notifyDataSetChanged();
            }
        });


        // Delete icon click listener
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDB db = new ProductDB(context);
                db.open();
                db.remove(p.getId());
                db.close();
                remove(p);
                notifyDataSetChanged();
            }
        });

        return v;
    }
}
