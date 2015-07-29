package org.buildmlearn.appstore.models;

import android.graphics.Bitmap;

/**
 * Model class of the App, currently used for installed apps, rendered from the BuildmLearn file of the apps.
 */
public class AppInfo {
    public String Name,Description,Category,Author,AuthorEmail;
    public int Type;
    public Bitmap AppIcon;
    public Bitmap[] Screenshots;
}
