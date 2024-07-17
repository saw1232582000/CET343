package UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.R;

import java.util.List;

import DB_Context.PropertyModel;

public class PropertyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<PropertyModel> dataList;
    private PropertyClickListener propertyClickListener;

    public PropertyAdapter(Context context, List<PropertyModel> dataList,PropertyClickListener propertyClickListener) {
        this.context = context;
        this.dataList = dataList;
        this.propertyClickListener=propertyClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, parent, false);
        return new MyViewHolder(view,propertyClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.property_title.setText("PN-"+dataList.get(position).getRef_no());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



}
class MyViewHolder extends RecyclerView.ViewHolder{

    TextView property_title;
    CardView property_Card;
    public MyViewHolder(@NonNull View itemView,PropertyClickListener propertyClickListener) {
        super(itemView);
//        recImage = itemView.findViewById(R.id.recImage);
        property_Card = itemView.findViewById(R.id.property_Card);
//        recDesc = itemView.findViewById(R.id.recDesc);
//        recLang = itemView.findViewById(R.id.recLang);
        property_title = itemView.findViewById(R.id.property_Title);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(propertyClickListener!= null){
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        propertyClickListener.onItemClick(pos);
                    }
                }
            }
        });
    }
}
