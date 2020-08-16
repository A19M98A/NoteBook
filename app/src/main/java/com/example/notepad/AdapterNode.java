package com.example.notepad;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class AdapterNode extends ArrayAdapter<Notes> {
    public AdapterNode(ArrayList<Notes> array)
    {
        super(G.context, R.layout.activity_notes, array);
    }

    public static class ViewHolder
    {
        public TextView text;
        public TextView title;
        public Button edit;
        View root;

        public ViewHolder(View view) {
            text = (TextView) view.findViewById(R.id.text);
            title = (TextView) view.findViewById(R.id.title);
            root = (View) view.findViewById(R.id.root);
            edit = (Button) view.findViewById(R.id.edit);
        }

        public void fill(final ArrayAdapter<Notes> adapter, final Notes item, final int position)
        {
            title.setText(item.title);
            int x = G.rnd.nextInt(256);
            int y = G.rnd.nextInt(256);
            int z = G.rnd.nextInt(256);
            int color = Color.argb(255, x, y, z);
            title.setTextColor(color);
            String str = item.text.split("\n")[0];
            text.setText(str.substring(0, str.length() < 20 ? str.length() : 20));

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(G.currentActivity, Edit.class);
                    intent.putExtra("POSITION", position);
                    G.currentActivity.startActivity(intent);
                }
            });

            root.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(G.currentActivity);
                    dialog.setContentView(R.layout.activity_show_note);

                    Button ok = (Button) dialog.findViewById(R.id.ok);
                    Button delete = (Button) dialog.findViewById(R.id.delete);
                    TextView Title, Text;

                    Title = (TextView) dialog.findViewById(R.id.title);
                    Text = (TextView) dialog.findViewById(R.id.text);

                    Title.setText(item.title);
                    Text.setText(item.text);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            G.database.deleteNote(item.id);
                            G.nodes.remove(item);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
        }
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder holder;
        Notes item = getItem(position);
        if (convertView == null)
        {
            convertView = G.inflater.inflate(R.layout.activity_notes, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(this, item, position);
        return convertView;
    }
}
