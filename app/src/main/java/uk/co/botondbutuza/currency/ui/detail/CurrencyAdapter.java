package uk.co.botondbutuza.currency.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.botondbutuza.currency.R;

/**
 * Created by brotond on 19/10/2017.
 */

class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.Holder> {
    private List<String> items;
    private List<String> checked;

    @Inject CurrencyAdapter() {
        items = new ArrayList<>();
        checked = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency_checkbox, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public void onViewRecycled(Holder holder) {
        holder.unbind();
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    void setItems(List<String> items) {
        this.items.clear();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    String getItem(int position) {
        return items.get(position);
    }


    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.check)   CheckBox checkBox;
        @BindView(R.id.text)    TextView text;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(String val) {
            text.setText(val);
            checkBox.setChecked(checked.contains(val));
        }

        void unbind() {
            checkBox.setChecked(false);
        }

        boolean toggleCheck() {
            checkBox.toggle();

            if (checkBox.isChecked()) checked.add(text.getText().toString());
            else checked.remove(text.getText().toString());

            return checkBox.isChecked();
        }
    }
}
