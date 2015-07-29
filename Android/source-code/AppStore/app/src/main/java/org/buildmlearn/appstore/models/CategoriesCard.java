package org.buildmlearn.appstore.models;

import org.buildmlearn.appstore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Template model for Categories
 */
public class CategoriesCard {
    public static final String[] CategoryName={"Science","Mathematics","Literature","SocialStudies","History","Geography","English","Physics","Chemistry","Biology"};
    public static final int[] CategoryImage={R.drawable.card_science,R.drawable.card_mathematics,R.drawable.card_literature, R.drawable.card_socialstudies,
            R.drawable.card_history,R.drawable.card_geography, R.drawable.card_english,R.drawable.card_physics, R.drawable.card_chemistry, R.drawable.card_biology};

    public final int Background;
    public final String Name;
    private CategoriesCard(String mName,int mBackground)
    {
        Name=mName;
        Background=mBackground;
    }
    public static List<CategoriesCard> getCategoriesList()
    {
        List<CategoriesCard> list=new ArrayList<>();
        for(int i=0;i<CategoryName.length;i++)
        {
            CategoriesCard ob=new CategoriesCard(CategoryName[i],CategoryImage[i]);
            list.add(ob);
        }
        return list;
    }
}
