package com.luma.articuloscorte.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luma.articuloscorte.R;
import com.luma.articuloscorte.model.EmpleadosModel;
import com.luma.articuloscorte.model.EntregaArticuloModel;

import java.util.ArrayList;
import java.util.List;

public class EntregaArticuloAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<EntregaArticuloModel> info;
    public List<EntregaArticuloModel> aux;
    private final Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    private static OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onClick(ItemAdapterViewHolder holder, int position);
        public void onTransferirClick(ItemAdapterViewHolder holder, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public EntregaArticuloAdapter(List<EntregaArticuloModel> info, Context context, OnItemClickListener mItemClickListener) {
        this.info = info;
        this.aux = new ArrayList<>(info);
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    public void setInfo(List<EntregaArticuloModel> info) {
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
        View tarjeta = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrega_articulo, parent, false);
        return new ItemAdapterViewHolder(tarjeta);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemAdapterViewHolder) {
            final EntregaArticuloModel cls = info.get(position);
            ItemAdapterViewHolder holderItem = (ItemAdapterViewHolder) holder;
            if(cls.getTRANSFERIDO() > 0){
                holderItem.lblCVTransferido.setText("SI");
                holderItem.btnCVTransferir.setVisibility(View.GONE);
            }else{
                holderItem.lblCVTransferido.setText("NO");
                holderItem.btnCVTransferir.setVisibility(View.VISIBLE);
            }

            holderItem.lblCVCodigoEmpleado.setText(String.valueOf(cls.getEMPLEADO()));
            holderItem.lblCVNombreEmpleado.setText(cls.getNOMBREEMPLEADO());
            holderItem.lblCVCodigoArticulo.setText(String.valueOf(cls.getCODIGOARTICULO()));
            holderItem.lblCVDescripcionArticulo.setText(cls.getARTICULODESCRIPCION());
            holderItem.lblCVCantidad.setText(String.valueOf(cls.getCANTIDAD()));

            holderItem.lblCVFecha.setText(cls.getFECHAENTREGA());

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
        private TextView lblCVTransferido, lblCVCodigoEmpleado,lblCVNombreEmpleado
                ,lblCVCodigoArticulo,lblCVDescripcionArticulo,lblCVCantidad,lblCVFecha;
        private CardView cv;
        private Button btnCVTransferir;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            btnCVTransferir = itemView.findViewById(R.id.btnCVTransferir);

            lblCVTransferido = itemView.findViewById(R.id.lblCVTransferido);
            lblCVCodigoEmpleado =  itemView.findViewById(R.id.lblCVCodigoEmpleado);

            lblCVNombreEmpleado =  itemView.findViewById(R.id.lblCVNombreEmpleado);
            lblCVCodigoArticulo =  itemView.findViewById(R.id.lblCVCodigoArticulo);
            lblCVDescripcionArticulo =  itemView.findViewById(R.id.lblCVDescripcionArticulo);
            lblCVCantidad =  itemView.findViewById(R.id.lblCVCantidad);
            lblCVFecha =  itemView.findViewById(R.id.lblCVFecha);

            cv.setOnClickListener(this);
            btnCVTransferir.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                if (v == cv) {
                    mItemClickListener.onClick(this, getLayoutPosition());
                }else if(v == btnCVTransferir){
                    mItemClickListener.onTransferirClick(this,getLayoutPosition());
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
}
