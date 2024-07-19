package UI;




import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment.R;

import java.util.ArrayList;
import java.util.List;

import DB_Context.DBContext;
import DB_Context.ItemModel;
import DB_Context.PropertyModel;

public class Property_Fragment extends Fragment implements PropertyClickListener {

    RecyclerView recyclerView;
    PropertyAdapter adapter;
    Button add_btn;
    List<ItemModel> item_list;
    DBContext dbContext;
    String username="";
    String password="";
    EditText search_text;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View property_view=inflater.inflate(R.layout.fragment_property, container, false);
        Bundle bundle=getArguments();

        if(bundle!=null)
        {
             username=bundle.getString("username");
             password=bundle.getString("password");
        }

        recyclerView=property_view.findViewById(R.id.property_recyclerView);
        add_btn=property_view.findViewById(R.id.add_new_property);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        search_text=property_view.findViewById(R.id.property_search);
        dbContext=new DBContext(Property_Fragment.this.getActivity());
        item_list = new ArrayList<>();
        item_list=dbContext.search_items_by_name("");
        adapter=new PropertyAdapter(getContext(), item_list,this);
        recyclerView.setAdapter(adapter);

       // FragmentManager fragmentManager=getSupportFragmentManager();

//        for(int i=0;i<2;i++)
//        {
//            PropertyModel p=new PropertyModel(1,"ptype","proom","12-2-2023","122","furn","no remark","name"+i);
//            property_list.add(p);
//        }
        adapter.notifyDataSetChanged();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed.
                // You can get the text using the `s` parameter.
                String newText = s.toString();
                item_list= dbContext.search_items_by_name(newText);
                adapter=new PropertyAdapter(getContext(),item_list,Property_Fragment.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed.
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add_mode");
                bundle.putString("username",username);
                Property_Form_Fragment p_form_fragment = new Property_Form_Fragment();
                p_form_fragment.setArguments(bundle);
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,p_form_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });



        return property_view;
    }
    @Override
    public void onItemClick(int position){
        ItemModel current_property=item_list.get(position);
        String item_id=current_property.getId();
        Bundle args=new Bundle();
        args.putString("ref_no",item_id);
        args.putString("mode", "detail_mode");
        args.putString("username",username);
        Property_Form_Fragment property_form_fragment=new Property_Form_Fragment();
        property_form_fragment.setArguments(args);
        FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,property_form_fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}