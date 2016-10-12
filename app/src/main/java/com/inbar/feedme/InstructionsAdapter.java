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

public class InstructionsAdapter extends RecyclerView.Adapter<InstructionsAdapter.MyViewHolder> {
    private Context context;
    private List<String> instructionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView instruction;
        public ImageView edit, delete;

        public MyViewHolder(View view) {
            super(view);
            instruction = (TextView) view.findViewById(R.id.tv_instruction);
            edit = (ImageView) view.findViewById(R.id.iv_edit_instruction);
            delete = (ImageView) view.findViewById(R.id.iv_delete_instruction);
        }
    }

    public InstructionsAdapter(Context context, List<String> instructionsList) {
        this.context = context;
        this.instructionsList = instructionsList;
    }

    @Override
    public InstructionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructions_list_item, parent, false);

        return new InstructionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InstructionsAdapter.MyViewHolder holder, final int position) {
        final String element = instructionsList.get(position);

        holder.instruction.setText(element);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newInstruction = showEditDialog(holder.edit, element);
                if (!newInstruction.equals(element)) {
                    element.replace(element, newInstruction);
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
                                instructionsList.remove(position);
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

    private String showEditDialog(View view, final String instruction) {
        final String newInstruction= instruction;

        final Dialog instructionEdit = new Dialog(context);
        instructionEdit.setTitle("Edit instruction");
        instructionEdit.setContentView(R.layout.instruction_edit_dialog);

        final EditText instructionName = (EditText) instructionEdit.findViewById(R.id.et_instruction);
        instructionName.setText(instruction);

        ImageView approve = (ImageView) instructionEdit.findViewById(R.id.edit_instruction_approve);
        approve.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   newInstruction.replace(instruction, instructionName.getText().toString().trim());
                   notifyDataSetChanged();
                   instructionEdit.dismiss();
               }
           }
        );

        instructionEdit.show();
        return newInstruction;
    }

    @Override
    public int getItemCount() {
        if (instructionsList != null)
            return instructionsList.size();
        return 0;
    }
}
