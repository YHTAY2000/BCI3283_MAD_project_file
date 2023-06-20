package com.example.projectapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity{

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout home, profile, settings, about, logout;
    ViewPager2 viewPager2;
    CardView eventCard, guestCard;
    //implementing auto slide facility
    private Handler slideHandler = new Handler();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        settings = findViewById(R.id.setting);
        about = findViewById(R.id.about);
        logout = findViewById(R.id.logout);
        viewPager2 = findViewById(R.id.viewPager);
        eventCard = findViewById(R.id.eventCard);
        guestCard = findViewById(R.id.guestCard);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer(drawerLayout);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomePage.this, Setting.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomePage.this, Profile.class);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(HomePage.this, About.class);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        eventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Event list
                Intent intent = new Intent(HomePage.this, Event_Home.class);
                startActivity(intent);
            }
        });

        guestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guest list
                Intent intent = new Intent(HomePage.this, Guest_List.class);
                startActivity(intent);
            }
        });

        ImageSlider();

    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity, Class secondActivity){
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 2000);
    }

    public void ImageSlider(){
        List<SlideImage> sliderimages = new ArrayList<>();
        sliderimages.add(new SlideImage(R.drawable.banner1));
        sliderimages.add(new SlideImage(R.drawable.banner2));
        sliderimages.add(new SlideImage(R.drawable.banner3));
        sliderimages.add(new SlideImage(R.drawable.banner4));
        sliderimages.add(new SlideImage(R.drawable.banner5));

        viewPager2.setAdapter(new SlideAdapter(sliderimages,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositionTransform = new CompositePageTransformer();
        compositionTransform.addTransformer(new MarginPageTransformer(30));
        compositionTransform.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.5f);
            }
        });

        viewPager2.setPageTransformer(compositionTransform);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 3000);
            }
        });

    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

}
