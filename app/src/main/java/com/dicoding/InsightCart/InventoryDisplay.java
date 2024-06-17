package com.dicoding.InsightCart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class InventoryDisplay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuList;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_menu);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        menuList = new ArrayList<>();

        menuList.add(new MenuItem("Kopikap", "Rp 20.000"));
        menuList.add(new MenuItem("Kopikap", "Rp 20.000"));

        menuAdapter = new MenuAdapter(this, menuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(menuAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuList.add(new MenuItem("Kopikap", "Rp 20.000"));
                menuAdapter.notifyItemInserted(menuList.size() - 1);
            }
        });

        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {

                menuList.remove(position);
                menuAdapter.notifyItemRemoved(position);
            }
        });
    }
}
