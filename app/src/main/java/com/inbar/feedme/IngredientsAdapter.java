package com.inbar.feedme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Inbar on 10/11/2016.
 */

public class IngredientsAdapter extends android.support.v7.widget.RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    private Context context;
    private List<Ingredient> ingredientsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount, units, ingredient;
        public ImageView edit, delete;

        public MyViewHolder(View view) {
            super(view);
            amount = (TextView) view.findViewById(R.id.tv_amount);
            units = (TextView) view.findViewById(R.id.tv_units);
            ingredient = (TextView) view.findViewById(R.id.tv_ingredient);
            edit = (ImageView) view.findViewById(R.id.iv_edit_ingredient);
            delete = (ImageView) view.findViewById(R.id.iv_delete_ingredient);
        }
    }

    public IngredientsAdapter(Context context, List<Ingredient> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);

        return new IngredientsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IngredientsAdapter.MyViewHolder holder, final int position) {
        final Ingredient element = ingredientsList.get(position);

        holder.amount.setText(Double.toString(element.getAmount()));
        holder.units.setText(element.getUnits().name().toLowerCase());
        holder.ingredient.setText(element.getIngredient());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ingredient newIngredient = showEditDialog(holder.edit, element);
                Ingredient newIngredient = showEditDialog(element);
                if (!newIngredient.equals(element)) {
                    element.setAmount(newIngredient.getAmount());
                    element.setUnits(newIngredient.getUnits());
                    element.setIngredient(newIngredient.getIngredient());
                    notifyDataSetChanged();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ingredientsList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    /*private Ingredient showEditDialog(View view, final Ingredient ingredient) {
        final Ingredient newIngredient = ingredient;

        final Dialog ingredientEdit = new Dialog(context);
        ingredientEdit.setTitle("Edit Ingredient");
        ingredientEdit.setContentView(R.layout.ingredient_edit_dialog);

        final EditText amount = (EditText) ingredientEdit.findViewById(R.id.et_amount);
        amount.setText(Double.toString(ingredient.getAmount()));

        final EditText ingredientName = (EditText) ingredientEdit.findViewById(R.id.et_ingredient);
        ingredientName.setText(ingredient.getIngredient());

        // Units spinner
        final Spinner spinner = (Spinner) ingredientEdit.findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter .createFromResource(context,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(ingredient.getUnits().ordinal());

        ImageButton approve = (ImageButton) ingredientEdit.findViewById(R.id.edit_ingredient_approve);
        approve.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           newIngredient.setAmount(Double.parseDouble(amount.getText().toString().trim()));
                                           newIngredient.setIngredient(ingredientName.getText().toString().trim());
                                           newIngredient.setUnits(Ingredient.Units.values()[spinner.getSelectedItemPosition()]);
                                           ingredientEdit.dismiss();
                                       }
                                   }
        );*/
    private Ingredient showEditDialog(final Ingredient ingredient) {
        final Ingredient newIngredient = ingredient;

        final Dialog ingredientEdit = new Dialog(context);
        ingredientEdit.setTitle("Edit Ingredient");
        ingredientEdit.setContentView(R.layout.ingredient_edit_dialog);

        final EditText amount = (EditText) ingredientEdit.findViewById(R.id.et_amount);
        final EditText ingredientName = (EditText) ingredientEdit.findViewById(R.id.et_ingredient);

        // Units spinner
        final Spinner spinner = (Spinner) ingredientEdit.findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter .createFromResource(context,
                R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // If updating an existing ingredient get current values
        if (ingredient != null) {
            amount.setText(Double.toString(ingredient.getAmount()));
            ingredientName.setText(ingredient.getIngredient());
            spinner.setSelection(ingredient.getUnits().ordinal());
        }

        ImageButton approve = (ImageButton) ingredientEdit.findViewById(R.id.edit_ingredient_approve);
        approve.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   boolean validInput = true;
                   if (amount.getText().toString().isEmpty() ||
                           (Integer.parseInt(amount.getText().toString().trim()) <= 0)) {
                       validInput = false;
                       amount.setError("Amount must be a valid positive number");
                   }
                   else
                       amount.setError("");

                   if (ingredientName.getText().toString().isEmpty()) {
                       validInput = false;
                       ingredientName.setError("Please specify an ingredient");
                   }
                   else
                       ingredientName.setError("");

                   if (validInput) {
                       newIngredient.setAmount(Double.parseDouble(amount.getText().toString().trim()));
                       newIngredient.setIngredient(ingredientName.getText().toString().trim());
                       newIngredient.setUnits(Ingredient.Units.values()[spinner.getSelectedItemPosition()]);
                       ingredientEdit.dismiss();
                   }
               }
           }
        );

        ingredientEdit.show();
        return newIngredient;
    }

    @Override
    public int getItemCount() {
        if (ingredientsList != null)
            return ingredientsList.size();
        return 0;
    }

    public List<Ingredient> getIngredients() {
        return ingredientsList;
    }

    public void addNewIngredient() {
        Ingredient newIngredient = showEditDialog(null);
        if (!newIngredient.getIngredient().isEmpty() &&
                newIngredient.getAmount() != 0) {
            ingredientsList.add(newIngredient);
            notifyDataSetChanged();
        }
    }
}
