package com.example.lenovo.criminalfaceidentificationsystem;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class item_ideas {

    int background;
    String link;



    public  item_ideas()
    {

    }

    public item_ideas(int background, String link) {
        this.background = background;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
