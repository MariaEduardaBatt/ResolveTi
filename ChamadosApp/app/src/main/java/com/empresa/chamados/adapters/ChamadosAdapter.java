package com.empresa.chamados.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.empresa.chamados.R;
import com.empresa.chamados.activities.AtendimentoChamadoActivity;
import com.empresa.chamados.models.Chamado;
import java.io.File;
import java.util.List;

public class ChamadosAdapter extends RecyclerView.Adapter<ChamadosAdapter.ViewHolder> {

    private List<Chamado> lista;

    public ChamadosAdapter(List<Chamado> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chamado, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chamado c = lista.get(position);
        holder.txtTitulo.setText(c.getTitulo());
        holder.txtData.setText(c.getData());
        holder.txtLocal.setText(c.getLocal());
        holder.txtStatus.setText(c.getStatus());

        int corStatus;
        switch (c.getStatus()) {
            case "Em andamento":
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.laranja_claro);
                break;
            case "Concluído":
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.status_done);
                break;
            default:
                corStatus = ContextCompat.getColor(holder.itemView.getContext(), R.color.laranja_escuro);
                break;
        }
        holder.txtStatus.setBackgroundColor(corStatus);

        if (c.getImagem() != null && !c.getImagem().isEmpty()) {
            holder.imgImagem.setVisibility(ImageView.VISIBLE);
            Glide.with(holder.itemView.getContext())
                    .load(Uri.fromFile(new File(c.getImagem())))
                    .centerCrop()
                    .into(holder.imgImagem);
        } else {
            holder.imgImagem.setVisibility(ImageView.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AtendimentoChamadoActivity.class);
            intent.putExtra("CHAMADO_ID", c.getId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtData, txtLocal, txtStatus;
        ImageView imgImagem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.item_titulo);
            txtData = itemView.findViewById(R.id.item_data);
            txtLocal = itemView.findViewById(R.id.item_local);
            txtStatus = itemView.findViewById(R.id.item_status);
            imgImagem = itemView.findViewById(R.id.item_imagem);
        }
    }
}
