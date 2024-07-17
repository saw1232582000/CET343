package UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.assignment.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DB_Context.DBContext;
import DB_Context.PropertyModel;


public class Property_Form_Fragment extends Fragment {
    Spinner property_type_spinner;
    Spinner furniture_type_spinner;
    Spinner bedrooms_spinner;
    EditText date_time_picker;
    EditText ref_no;
    EditText price;
    EditText remark;
    EditText reporter;
    Button save_btn;
    Button delete_btn;
    public String current_mode;
    public String current_username;
    DBContext dbContext;
    View ref_no_layout;
    List<PropertyModel> property_list;
    String reference_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null){
            current_mode =bundle.getString("mode");//check mode 'add_mode or 'edit_mode'
            current_username= bundle.getString("username");

        }

        View form_view=inflater.inflate(R.layout.property_form_fragment, container, false);
        String[] bedroom_items = {"Select bedroom type", "Studio","One","Two"};
        String[] furniture_type_items = {"Select furniture type", "Furnished","Unfurnished","Part Furnished"};
        String[] property_type_items = {"Select property type", "Flat","House","Bungalow"};
        ArrayAdapter<String> bedroom_adapter=new ArrayAdapter<>(Property_Form_Fragment.this.getActivity(), android.R.layout.simple_spinner_dropdown_item,bedroom_items);
        ArrayAdapter<String> property_type_adapter=new ArrayAdapter<>(Property_Form_Fragment.this.getActivity(), android.R.layout.simple_spinner_dropdown_item,property_type_items);
        ArrayAdapter<String> furniture_type_adapter=new ArrayAdapter<>(Property_Form_Fragment.this.getActivity(), android.R.layout.simple_spinner_dropdown_item,furniture_type_items);
        property_type_spinner=form_view.findViewById(R.id.property_type);
        furniture_type_spinner=form_view.findViewById(R.id.furniture_type);
        bedrooms_spinner=form_view.findViewById(R.id.bedroom);
        property_type_spinner.setAdapter(property_type_adapter);
        furniture_type_spinner.setAdapter(furniture_type_adapter);
        bedrooms_spinner.setAdapter(bedroom_adapter);

        dbContext=new DBContext(Property_Form_Fragment.this.getActivity());
        ref_no_layout=form_view.findViewById(R.id.reference_no_layout);
        ref_no=form_view.findViewById(R.id.reference_no);
        price=form_view.findViewById(R.id.price);
        remark=form_view.findViewById(R.id.remark);
        reporter=form_view.findViewById(R.id.reporter_name);
        date_time_picker=form_view.findViewById(R.id.date);
        save_btn=form_view.findViewById(R.id.save_btn);
        delete_btn=form_view.findViewById(R.id.delete_btn);

        if(current_mode=="add_mode"){
            ref_no_layout.setVisibility(View.INVISIBLE);
            save_btn.setText("Add");
            save_btn.setWidth(500);
            delete_btn.setVisibility(View.GONE);

        }
        if(current_mode=="detail_mode"){

           reference_no=bundle.getString("ref_no");
            ref_no.setText("Reference_No:"+reference_no);
            ref_no.setEnabled(false);
            property_list=dbContext.readProperty_by_ref_no(reference_no);
            PropertyModel p=property_list.get(0);
            String prop_type=p.getType().toString();
            String bed_type=p.getRooms();
            String fur_type=p.getFurniture();
            property_type_spinner.setSelection(property_type_adapter.getPosition(prop_type));
            bedrooms_spinner.setSelection(bedroom_adapter.getPosition(bed_type));
            date_time_picker.setText(property_list.get(0).getDate());
            price.setText(property_list.get(0).getPrice());
            furniture_type_spinner.setSelection(furniture_type_adapter.getPosition(fur_type));
            remark.setText(property_list.get(0).getRemark());
            reporter.setText(property_list.get(0).getName());
        }

        reporter.setText(current_username);
        final Calendar calendar=Calendar.getInstance();

//        date_time_picker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                        // Update the Calendar with the selected date
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, monthOfYear);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                        String selectedDateString = dateFormat.format(calendar.getTime());
//                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
//                        String currentTimeString = timeFormat.format(calendar.getTime());
//                        date_time_picker.setText(selectedDateString+" "+currentTimeString);
//                    }
//
//                };
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Property_Form_Fragment.this.getActivity(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//
//                // Show the DatePickerDialog
//                datePickerDialog.show();
//            }
//        });
        date_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SingleDateAndTimePickerDialog.Builder builder = new SingleDateAndTimePickerDialog.Builder(Property_Form_Fragment.this.getActivity())
//                        .setTitle("Select Date and Time")
//                        .setListener(new SingleDateAndTimePickerDialog.Listener() {
//                            @Override
//                            public void onDateSelected(Date date) {
//                                // Handle the selected date and time
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
//                                String formattedDateTime = sdf.format(date);
//                                date_time_picker.setText(formattedDateTime);
//                            }
//                        });
//
//                SingleDateAndTimePickerDialog dialog = builder.build();
//                dialog.display();
//                SingleDateAndTimePickerDialog.Builder builder=new SingleDateAndTimePickerDialog.Builder(Property_Form_Fragment.this.getActivity())
//                        .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
//                            @Override
//                            public void onDisplayed(SingleDateAndTimePicker picker) {
//                                // Retrieve the SingleDateAndTimePicker
//                            }
//
//                            @Override
//                            public void onClosed(SingleDateAndTimePicker picker) {
//                                picker.getDate();
//                            }
//                        })
//                        .title("Simple")
//                        .listener(new SingleDateAndTimePickerDialog.Listener() {
//                            @Override
//                            public void onDateSelected(Date date) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
//                                String formattedDateTime = sdf.format(date);
//                                date_time_picker.setText(formattedDateTime);
//                            }
//                        }).display();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(current_mode=="add_mode"){
                String prop_type=property_type_spinner.getSelectedItem().toString();
                String bedroom=bedrooms_spinner.getSelectedItem().toString();
                String add_date=date_time_picker.getText().toString();
                String pr=price.getText().toString();
                String fur_type=furniture_type_spinner.getSelectedItem().toString();
                String rem=remark.getText().toString();
                String rp_name=reporter.getText().toString();

                if(prop_type.isEmpty() || bedroom.isEmpty() || add_date.isEmpty() || pr.isEmpty() || fur_type.isEmpty() || rem.isEmpty())
                {
                    Toast.makeText(Property_Form_Fragment.this.getActivity(), "Enter all data", Toast.LENGTH_SHORT).show();
                }
                else {
                        dbContext.addProperty(prop_type,bedroom,add_date,pr,fur_type,rem,rp_name);
                      //  Toast.makeText(Property_Form_Fragment.this.getActivity(), "New property added successfully", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
            if(current_mode=="detail_mode"){
                String prop_type=property_type_spinner.getSelectedItem().toString();
                String bedroom=bedrooms_spinner.getSelectedItem().toString();
                String add_date=date_time_picker.getText().toString();
                String pr=price.getText().toString();
                String fur_type=furniture_type_spinner.getSelectedItem().toString();
                String rem=remark.getText().toString();
                String rp_name=reporter.getText().toString();
                dbContext.updateProperty(reference_no,Integer.parseInt(reference_no) ,prop_type,bedroom,add_date,pr,fur_type,rem,rp_name);
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dbContext.deleteProperty(reference_no);
            FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            }
        });

        return form_view;
    }
}