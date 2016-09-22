package com.inbar.feedme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

/*import rx.subjects.PublishSubject;
import rx.Observable;*/

/**
 * Created by Barr Inbar on 9/15/2016.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Recipe> recipeList;
    //private final PublishSubject<Recipe> onClickSubject = PublishSubject.create();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public RecipesAdapter(Context mContext, List<Recipe> recipeList) {
        this.mContext = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Recipe element = recipeList.get(position);

        holder.title.setText(element.getName());
        Resources res = mContext.getResources();
        String prepTime = res.getQuantityString(R.plurals.numberOfMinutes, element.getPrepTime(), element.getPrepTime());
        holder.time.setText(prepTime);

        // loading picture using Glide library
        Glide.with(mContext).load(element.getThumbnail())
                .centerCrop()
                .placeholder(R.drawable.feedme)
                .error(R.drawable.rec_default)
                .crossFade()
                .into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("FEEDME", element.getName() + " holder clicked in Recycler view");
                Toast.makeText(mContext, "You chose to cook " + element.getName(), Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                Intent gotoRecipe = new Intent(mContext, RecipeActivity.class);
                gotoRecipe.putExtra("recipe", gson.toJson(element));
                mContext.startActivity(gotoRecipe);
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(element);
                Log.i("FEEDME", element.getTitle() + " holder clicked in Recycler view");
                Toast.makeText(mContext, "You chose to cook " + element.getTitle(), Toast.LENGTH_SHORT).show();
                Intent gotoRecipe = new Intent(mContext, RecipeActivity.class);
                gotoRecipe.putExtra("recipe_id", element.getId());
                mContext.startActivity(gotoRecipe);
            }
        });*/

        /*public rx.Observable<Recipe> getPositionClicks(){
            return onClickSubject.asObservable();
        }
*/
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.recipe_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourites", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_share:
                    Toast.makeText(mContext, "Share", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
