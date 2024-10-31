package com.example.navigation_smd_7a;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class ScheduleFragment extends Fragment {

    private Context context;
    private ListView lvShOrderList;
    private ProductAdapter adapter;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductDB productDB;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ListView and ProductDB
        lvShOrderList = view.findViewById(R.id.lvSchOrdersList);
        productDB = new ProductDB(context);

        // Set up the adapter with the initial empty product list
        adapter = new ProductAdapter(context, R.layout.product_item_design, products);
        lvShOrderList.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshProductList(); // Refresh the product list every time the fragment becomes visible
    }

    // Method to load products from the database and refresh the ListView
    private void refreshProductList() {
        productDB.open();
        products.clear();
        products.addAll(productDB.fetchProducts()); // Fetch updated product list
        productDB.close();

        adapter.notifyDataSetChanged(); // Notify adapter of data changes to update the ListView
    }
}