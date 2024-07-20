package UI;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;



import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.assignment.R;
import com.google.android.material.textfield.TextInputLayout;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DB_Context.DBContext;
import DB_Context.ItemModel;
import DB_Context.PropertyModel;


public class Property_Form_Fragment extends Fragment {
    EditText item_name;
    Spinner item_category;
    EditText price;

    EditText description;

    Button save_btn;
    Button image_upload_btn;
    ImageView item_image;
    Button delete_btn;
    public String current_mode;
    public String current_username;

    private String user_id;

    private String image_base64_string;
    DBContext dbContext;
    View ref_no_layout;
    List<ItemModel> item_list;
    String passed_item_id;
    int SELECT_PICTURE = 200;
    private ActivityResultLauncher<Intent> selectPictureLauncher;

    DialogFragment dialogFragment = new DialogFragment();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register the ActivityResultLauncher
        selectPictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri selectedImageUri = data.getData();
                                // Set the image to the ImageView
                                item_image.setImageURI(selectedImageUri);
                                image_base64_string = convertImageToBase64(selectedImageUri);
                            }
                        }
                    }
                }
        );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null){
            current_mode =bundle.getString("mode");//check mode 'add_mode or 'edit_mode'
            current_username= bundle.getString("username");

        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession",Property_Form_Fragment.this.getActivity().MODE_PRIVATE);
        user_id = sharedPreferences.getString("user_id", null);

        View form_view=inflater.inflate(R.layout.property_form_fragment, container, false);
        String[] item_categories = {"Select category", "cat 1"," cat 2","cat 3"};


        ArrayAdapter<String> item_category_adapter=new ArrayAdapter<>(Property_Form_Fragment.this.getActivity(), android.R.layout.simple_spinner_dropdown_item,item_categories);



        /*------------------------------Bind with ui-----------------------------------------*/
        item_name=form_view.findViewById(R.id.item_name);
        item_category=form_view.findViewById(R.id.item_category);
        price=form_view.findViewById(R.id.price);
        description=form_view.findViewById(R.id.item_description);
        image_upload_btn=form_view.findViewById(R.id.image_upload_btn);
        item_image=form_view.findViewById(R.id.imageView_item_image);

        item_category.setAdapter(item_category_adapter);

        dbContext=new DBContext(Property_Form_Fragment.this.getActivity());





        save_btn=form_view.findViewById(R.id.save_btn);
        delete_btn=form_view.findViewById(R.id.delete_btn);

        if(current_mode=="add_mode"){

            save_btn.setText("Add");
            save_btn.setWidth(500);
            delete_btn.setVisibility(View.GONE);

        }
        if(current_mode=="detail_mode"){


            passed_item_id=bundle.getString("item_id");
            item_list=dbContext.read_item_by_item_id(passed_item_id);
            ItemModel i=item_list.get(0);
            String selected_item_name=i.getItem_name();
            String selected_item_category=i.getCategory();
            String selected_item_price=i.getPrice();
            String selected_image_data=i.getImage_data();
            String selected_item_description=i.getDescription();
            image_base64_string=selected_image_data;
            item_image.setImageBitmap(convertBase64ToBitmap(selected_image_data));

            item_name.setText(selected_item_name);
            item_category.setSelection(item_category_adapter.getPosition(selected_item_category));
            price.setText(selected_item_price);
            description.setText(selected_item_description);

        }




//

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(current_mode=="add_mode"){
                String it_name=item_name.getText().toString();
                String category=item_category.getSelectedItem().toString();
                String pr=price.getText().toString();
                String desc=description.getText().toString();
                String message2=image_base64_string;
                String message="item name:"+it_name+"\n"+
                        "item name:"+category+"\n"+
                        "item name:"+pr+"\n"+
                        "item name:"+desc+"\n";
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(message2);
                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
                if( category.isEmpty() ||  pr.isEmpty() ||  it_name.isEmpty())
                {
                    Toast.makeText(Property_Form_Fragment.this.getActivity(), "Enter all data", Toast.LENGTH_SHORT).show();
                }
                else {
                       if(dbContext.addItem(user_id,image_base64_string,it_name,pr,category,desc)) {
                           Toast.makeText(Property_Form_Fragment.this.getActivity(), "New item added successfully", Toast.LENGTH_SHORT).show();
                       }
                       else{
                           Toast.makeText(Property_Form_Fragment.this.getActivity(), "Item Creation Fail!", Toast.LENGTH_SHORT).show();
                       }

//                    FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
//                    fragmentManager.popBackStack();
                }
            }
            if(current_mode=="detail_mode"){

                String current_item_name=item_name.getText().toString();
                String current_price=price.getText().toString();
                String current_description=description.getText().toString();
                String current_category=item_category.getSelectedItem().toString();
                String current_image_data=image_base64_string;
                if(dbContext.updateItem(passed_item_id,current_image_data,current_item_name,current_price,current_category,current_description)) {
                    Toast.makeText(Property_Form_Fragment.this.getActivity(), "Item updated successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Property_Form_Fragment.this.getActivity(), "Updating item fail!", Toast.LENGTH_SHORT).show();
                }

//                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
            }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dbContext.deleteProperty(passed_item_id);
            FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            }
        });

        image_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an intent to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Launch the intent using the ActivityResultLauncher
                selectPictureLauncher.launch(Intent.createChooser(intent, "Select Picture"));
            }
        });



        return form_view;
    }
    private String convertImageToBase64(Uri imageUri) {
        try {
            InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private Bitmap convertBase64ToBitmap(String base64String) {
        try {
            byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}