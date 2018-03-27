package tgo1014.projectx.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import tgo1014.projectx.Models.QRData;
import tgo1014.projectx.R;
import tgo1014.projectx.Realm.RealmController;

/**
 * Created by ahmad on 09/06/2017.
 */

public class QRDataAdapter extends RealmRecyclerViewAdapter<QRData> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public QRDataAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qrdata, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();

        // get the article
        final QRData qrData = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;


        if (qrData.getName().isEmpty()){
            holder.tvName.setVisibility(View.GONE);
        }
        if (qrData.getAddress().isEmpty()){
            holder.tvAddress.setVisibility(View.GONE);
        }
        if (qrData.getEmail().isEmpty()){
            holder.tvEmail.setVisibility(View.GONE);
        }
        if (qrData.getPhoneNumber().isEmpty()){
            holder.tvPhoneNumber.setVisibility(View.GONE);
        }
        if (qrData.getFbProfileLink().isEmpty()){
            holder.tvFBURL.setVisibility(View.GONE);
        }


        // set the title and the snippet
        holder.tvName.setText(qrData.getName());
        holder.tvAddress.setText(qrData.getAddress());
        holder.tvPhoneNumber.setText(qrData.getPhoneNumber());
        holder.tvEmail.setText(qrData.getEmail());
        holder.tvFBURL.setText(qrData.getFbProfileLink());

        // load the background image

        //remove single match from realm
//        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                RealmResults<QRData> results = realm.where(QRData.class).findAll();
//
//                // Get the book title to show it in toast message
//                QRData b = results.get(position);
//                String title = b.getTitle();
//
//                // All changes to data must happen in a transaction
//                realm.beginTransaction();
//
//                // remove single match
//                results.remove(position);
//                realm.commitTransaction();
//
//                if (results.size() == 0) {
//                    Prefs.with(context).setPreLoad(false);
//                }
//
//                notifyDataSetChanged();
//
//                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        //update single match from realm
//        holder.card.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View content = inflater.inflate(R.layout.edit_item, null);
//                final EditText editTitle = (EditText) content.findViewById(R.id.title);
//                final EditText editAuthor = (EditText) content.findViewById(R.id.author);
//                final EditText editThumbnail = (EditText) content.findViewById(R.id.thumbnail);
//
//                editTitle.setText(qrData.getTitle());
//                editAuthor.setText(qrData.getAuthor());
//                editThumbnail.setText(qrData.getImageUrl());
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setView(content)
//                        .setTitle("Edit Book")
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                RealmResults<QRData> results = realm.where(QRData.class).findAll();
//
//                                realm.beginTransaction();
//                                results.get(position).setAuthor(editAuthor.getText().toString());
//                                results.get(position).setTitle(editTitle.getText().toString());
//                                results.get(position).setImageUrl(editThumbnail.getText().toString());
//
//                                realm.commitTransaction();
//
//                                notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView tvName;
        public TextView tvAddress;
        public TextView tvEmail;
        public TextView tvPhoneNumber;
        public TextView tvFBURL;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvFBURL = (TextView) itemView.findViewById(R.id.tvFbURL);

        }
    }
}
