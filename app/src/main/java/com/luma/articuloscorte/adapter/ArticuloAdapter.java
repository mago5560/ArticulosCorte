package com.luma.articuloscorte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luma.articuloscorte.R;
import com.luma.articuloscorte.model.ArticuloModel;
import com.luma.articuloscorte.model.EmpleadosModel;

import java.util.ArrayList;
import java.util.List;

public class ArticuloAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    public List<ArticuloModel> info;
    public List<ArticuloModel> aux;
    private final Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    private static OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onClick(ItemAdapterViewHolder holder, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ArticuloAdapter(List<ArticuloModel> info, Context context, OnItemClickListener mItemClickListener) {
        this.info = info;
        this.aux = new ArrayList<>(info);
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    public void setInfo(List<ArticuloModel> info) {
        this.info = info;
        this.aux = new ArrayList<>(info);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View tarjeta = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_footer, parent, false);
            return new FooterViewHolder(tarjeta);
        }
        View tarjeta = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maestro, parent, false);
        return new ItemAdapterViewHolder(tarjeta);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemAdapterViewHolder) {
            final ArticuloModel cls = info.get(position);
            ItemAdapterViewHolder holderItem = (ItemAdapterViewHolder) holder;
            holderItem.lblCVCodigo.setText(String.valueOf(cls.getCODIGOARTICULO()));
            holderItem.lblCVDescripcion.setText(cls.getARTICULODESCRIPCION());

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.footerText.setText("No se encontraron mas registros...");
        }
    }

    //Si se utiliza esta fucion tomar en cuenta que se agrega size +1 por el footer.
    @Override
    public int getItemCount() {
        if (info.size() == 0) {
            return 0;
        } else
            return info.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == info.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView lblCVCodigo, lblCVDescripcion;
        private CardView cv;


        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            lblCVCodigo = itemView.findViewById(R.id.lblCVCodigo);
            lblCVDescripcion =  itemView.findViewById(R.id.lblCVDescripcion);

            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                if (v == cv) {
                    mItemClickListener.onClick(this, getLayoutPosition());
                }
            }
        }

    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView footerText;

        public FooterViewHolder(View view) {
            super(view);
            footerText = (TextView) view.findViewById(R.id.footer_text);
        }
    }


    // <editor-fold defaultstate="collapsed" desc="(FILTER GRD)">
    @Override
    public Filter getFilter() {
        return getBuscar;
    }

    private Filter getBuscar = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ArticuloModel> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(aux);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ArticuloModel item : aux) {
                    if (item.toString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            info.clear();
            info.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    // </editor-fold>
}
