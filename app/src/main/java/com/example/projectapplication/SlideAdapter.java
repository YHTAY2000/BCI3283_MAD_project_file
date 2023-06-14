package com.example.projectapplication;

import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder>{

    private List<SlideImage> slideImages;
    private ViewPager2 viewPager2;

    SlideAdapter(List<SlideImage> slideImages, ViewPager2 viewPager2) {
        this.slideImages = slideImages;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.slide_image,
                    parent,
                    false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImage(slideImages.get(position));
        if (position == slideImages.size() -2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideImages.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideImage slideImage){
            //if you want to display image from internet you can put code here using glide or piccaso
            imageView.setImageResource(slideImage.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideImages.addAll(slideImages);
            notifyDataSetChanged();
        }
    };
}
