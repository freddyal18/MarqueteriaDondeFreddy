package com.example.marqueteriadondefreddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapterSer extends RecyclerView.Adapter<ImageAdapterSer.ImageViewHolder> {

    private Context mContext;
    private List<uploadInfo> mUploads;

    public ImageAdapterSer(Context context, List<uploadInfo> uploads){
        mContext = context;
        mUploads = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item_ser, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        uploadInfo uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getImageName());
        holder.descrip.setText(uploadCurrent.getDescripcion());
        Picasso.get().load(uploadCurrent.getImageURL()).fit().centerCrop().into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView descrip;
        public ImageView imageView1;

        public ImageViewHolder (@NonNull View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.txv_nombre_servicio);
            descrip = itemView.findViewById(R.id.txv_desc_ser);
            imageView1 = itemView.findViewById(R.id.img_ver_ser);
        }
    }
}
