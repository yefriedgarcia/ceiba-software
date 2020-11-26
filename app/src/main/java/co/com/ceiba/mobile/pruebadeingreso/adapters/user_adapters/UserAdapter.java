package co.com.ceiba.mobile.pruebadeingreso.adapters.user_adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.models.users_model.UserData;
import co.com.ceiba.mobile.pruebadeingreso.view.activities.PostActivity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {

    private ArrayList<UserData> userData;
    private Context context;
    private ArrayList<UserData> userDataFil;

    public UserAdapter(Context context, ArrayList<UserData> userData){
        this.context = context;
        this.userData = userData;
        userDataFil = new ArrayList<>(this.userData);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(userData.get(position).getName());
        holder.phone.setText(userData.get(position).getPhone());
        holder.email.setText(userData.get(position).getEmail());

        holder.btn_view_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(context, PostActivity.class);
                    i.putExtra("id", userData.get(position).getId());
                    i.putExtra("name", userData.get(position).getName());
                    i.putExtra("email", userData.get(position).getEmail());
                    i.putExtra("phone", userData.get(position).getPhone());
                    context.startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(userData != null)
            return userData.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<UserData> listFilter = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                listFilter.addAll(userDataFil);
            }else{
                for (int i = 0; i< userDataFil.size(); i++) {
                    if(userDataFil.get(i).getName().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        listFilter.add(userDataFil.get(i));
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = listFilter;

            if(listFilter.size() == 0){
                try{
                    Toast.makeText(context, context.getResources().getString(R.string.list_is_empty), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userData.clear();
            try {
                userData.addAll((Collection<? extends UserData>) filterResults.values);
                notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name, phone, email;
        private Button btn_view_post;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            btn_view_post = itemView.findViewById(R.id.btn_view_post);
        }
    }
}
