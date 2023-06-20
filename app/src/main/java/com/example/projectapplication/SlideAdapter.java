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

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

        private List<SlideImage> slideImage;
        private ViewPager2 viewPager2;

        SlideAdapter(List<SlideImage> slideImage, ViewPager2 viewPager2) {
            this.slideImage = slideImage;
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
            holder.setImage(slideImage.get(position));
        }

        @Override
        public int getItemCount() {
            return slideImage.size();
        }

        class SlideViewHolder extends RecyclerView.ViewHolder{
            private RoundedImageView imageView;

            public SlideViewHolder(@NonNull View itemView){
                super(itemView);
                imageView = itemView.findViewById(R.id.imageSlide);
            }

            void setImage(SlideImage slideImage){
                imageView.setImageResource(slideImage.getImage());
            }
        }
}
