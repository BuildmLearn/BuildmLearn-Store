package org.buildmlearn.appstore.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Srujan Jha on 5/29/2015.
 */
public class Apps implements Parcelable {
    public String Name,Description,Category,Author,AuthorEmail,AppIcon,Type;
    public Bitmap BAppIcon;
    public String[] Screenshots;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Name);
        dest.writeString(Description);
        dest.writeString(Category);
        dest.writeString(Type);
        dest.writeString(Author);
        dest.writeString(AuthorEmail);
        dest.writeString(AppIcon);
        dest.writeInt(Screenshots.length);
        for(int i=0;i<Screenshots.length;i++)dest.writeString(Screenshots[i]);

    }
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Apps> CREATOR = new Parcelable.Creator<Apps>() {
        public Apps createFromParcel(Parcel in) {
            return new Apps(in);
        }

        public Apps[] newArray(int size) {
            return new Apps[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Apps(Parcel in) {
        Name=in.readString();
        Description=in.readString();
        Category=in.readString();
        Type=in.readString();
        Author=in.readString();
        AuthorEmail=in.readString();
        AppIcon = in.readString();
        int l=in.readInt();
        Screenshots=new String[l];
        for(int i=0;i<l;i++)Screenshots[i]=in.readString();
    }
    public Apps()
    {
        Name="";Description="";Category="";Type="";Author="";AuthorEmail="";AppIcon="";
        Screenshots=null;
    }
}
