package adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ke.co.lt.com.skilllite.R;
import model.ExpertSearchModel;


public class ExpertSearchAdapter extends RecyclerView.Adapter<ExpertSearchAdapter.ViewHolder> {
    Context context;
    private ArrayList<ExpertSearchModel> ridehistoryModelArrayList;

    public ExpertSearchAdapter(Context context, ArrayList<ExpertSearchModel> ridehistoryModelArrayList) {
        this.context = context;
        this.ridehistoryModelArrayList = ridehistoryModelArrayList;
    }

    @NonNull
    @Override
    public ExpertSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertSearchAdapter.ViewHolder holder, int position) {
        holder.name.setText(ridehistoryModelArrayList.get(position).getName());
        holder.gender.setText(ridehistoryModelArrayList.get(position).getGender());
        holder.country.setText(ridehistoryModelArrayList.get(position).getCountry());
        holder.county.setText(ridehistoryModelArrayList.get(position).getCounty());
        holder.estate.setText(ridehistoryModelArrayList.get(position).getEstate());
        holder.expert.setText(ridehistoryModelArrayList.get(position).getExpert());
        holder.neigh.setText(ridehistoryModelArrayList.get(position).getNeigh());
        holder.phone.setText(ridehistoryModelArrayList.get(position).getPhone());
        Picasso.get().load(ridehistoryModelArrayList.get(position).getImage()).placeholder(R.drawable.user).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return ridehistoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, gender, country, county, estate, expert, phone, neigh;
        CircleImageView image;

        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
            country = itemView.findViewById(R.id.country);
            county = itemView.findViewById(R.id.county);
            estate = itemView.findViewById(R.id.estate);
            expert = itemView.findViewById(R.id.expert);
            phone = itemView.findViewById(R.id.phone);
            neigh = itemView.findViewById(R.id.neigh);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    PopupMenu popupMenu = new PopupMenu(context, expert);
                    popupMenu.getMenuInflater().inflate(R.menu.pop_option_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals("Call now")) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + phone));
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                }
                                v.getContext().startActivity(callIntent);
                            }else if(item.getTitle().equals("Sms now")){
                                Uri sms_Uri = Uri.parse("smsto:"+phone);
                                Intent intent = new Intent(Intent.ACTION_SENDTO, sms_Uri);
                                intent.putExtra("sms_body", "Hi How is you?");
                                v.getContext().startActivity(intent);
                            }else if(item.getTitle()=="Whatsup now"){
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, "");
                                intent.setType("text/plain");
                                v.getContext().startActivity(intent);
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

        }

    }


}