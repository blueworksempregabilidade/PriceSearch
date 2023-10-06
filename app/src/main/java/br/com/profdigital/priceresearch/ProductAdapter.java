package br.com.profdigital.priceresearch;

// 3

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {

    // nv remove private final LayoutInflater mLayoutInflater;
    private View.OnClickListener mOnItemClickListener;
    private View.OnLongClickListener mOnLongClickListener;

    private Context mContext;
    private List<Product> mProductList;

    private TextView mTextViewTotalPrice;//nv2

//    public ProductAdapter(Context context, List<Product> productList) { //nv
    public ProductAdapter(Context context, List<Product> productList, TextView textViewTotalPrice) { //nv2
        mContext = context;
        mProductList = productList;
        mTextViewTotalPrice = textViewTotalPrice;//nv2
    }

    private List<Product> mProductListFull; // será usada para recompor totalmente a lista na busca

//    public ProductAdapter(Context mContext){ //nv remove
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }

    public String setPriceColor(double vRating){
        if(vRating < 3){
            return "#BF0404"; //299E4A green     737373 gray
        } else {
            return "#000000";
        }
    }

 //x1  public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
   public class ProductViewHolder extends RecyclerView.ViewHolder {
        // widgets do layout card_item_list.xml
        private final TextView mTextViewItemProductDescription;
        private final TextView mTextViewItemProductPrice;
        private final RatingBar mRatingBarItemProduct;
        private final Button mButtonProductQuantity;
        private final Button mButtonProductAdd;
        private final Button mButtonProductRemove;

       // private final ImageView mImageViewProduct;

     double vTotalPrice = 0.0;



     int vQuantity = 0;
//     public class ClickMyButtonQuantity implements View.OnClickListener{
//         @Override
//         public void onClick(View view) {
//             vQuantity++;
//             mButtonProductQuantity.setText(""+vQuantity);
//         }
//     }

     public class ClickMyButtonRemove implements View.OnClickListener{
         @Override
         public void onClick(View view) {
             if(vQuantity >0){
                 vQuantity = mProductList.get(getAdapterPosition()).getUnit()-1;
                 //vQuantity--;
                 if(vQuantity >= 10){
                     mButtonProductQuantity.setTextSize( 12f);
                 } else{
                     mButtonProductQuantity.setTextSize( 18f);
                 }
                 if(vQuantity<0){ // corrigir bug do ultimo item da lista quando zerado passava a -1
                     vQuantity=0;
                 }
                 mButtonProductQuantity.setText(""+vQuantity);
                 mProductList.get(getAdapterPosition()).setUnit(vQuantity);

                 showTotalPrice();


             }
         }
     }

     public class ClickMyButtonAdd implements View.OnClickListener{
         @Override
         public void onClick(View view) {

             //vQuantity++;
             vQuantity = mProductList.get(getAdapterPosition()).getUnit()+1;
             if(vQuantity >= 10){
                 mButtonProductQuantity.setTextSize(12f);
             } else{
                 mButtonProductQuantity.setTextSize(18f);
             }
             mButtonProductQuantity.setText(""+vQuantity);

             mProductList.get(getAdapterPosition()).setUnit(vQuantity);

//             vTotalPrice = mProductList.get(getAdapterPosition()).getUnit()*mProductList.get(getAdapterPosition()).getPrice();
//              Toast.makeText(mContext.getApplicationContext(), ""+vTotalPrice, Toast.LENGTH_SHORT).show();
             showTotalPrice();

         }
     }

     private void showTotalPrice(){
         vTotalPrice = 0;
         for (int i = 0; i <= mProductList.size()-1; i++){
             vTotalPrice = vTotalPrice + mProductList.get(i).getPrice()* mProductList.get(i).getUnit();
         }
         NumberFormat mFormatValue = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
         String mStringValue = mFormatValue.format(vTotalPrice);
         mTextViewTotalPrice.setText(mStringValue);
//         mTextViewTotalPrice.setText(""+vTotalPrice);
//         Toast.makeText(mContext.getApplicationContext(), ""+vTotalPrice, Toast.LENGTH_SHORT).show();
     }



     private ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewItemProductDescription = itemView.findViewById(R.id.textView_item_product_name_rev2);
            mTextViewItemProductPrice = itemView.findViewById(R.id.textView_item_product_price_rev2);
            mRatingBarItemProduct = itemView.findViewById(R.id.ratingBar_item_product_price_perception_rev2);

            mButtonProductQuantity = itemView.findViewById(R.id.button_product_quantity_rev2);
        // mButtonProductQuantity.setOnClickListener(new ClickMyButtonQuantity());

         mButtonProductRemove = itemView.findViewById(R.id.button_product_remove_rev2);
         mButtonProductRemove.setOnClickListener(new ClickMyButtonRemove());

         mButtonProductAdd = itemView.findViewById(R.id.button_product_add_rev2);
        mButtonProductAdd.setOnClickListener(new ClickMyButtonAdd());

            //mImageViewProduct = itemView.findViewById(R.id.image_item_product_list);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);

           // itemView.setOnLongClickListener(mOnLongClickListener);
     //X1       itemView.setOnCreateContextMenuListener(this);


        }

// X1       @Override //https://stackoverflow.com/questions/26466877/how-to-create-context-menu-for-recyclerview
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            contextMenu.setHeaderTitle("Select The Action " + view.getId());
//            contextMenu.add(0, view.getId(), 0, "Call");//groupId, itemId, order, title
//            contextMenu.add(0, view.getId(), 0, "SMS");
//
//        }

    }

    @NonNull
    @Override  // esse método carrega um item da lista
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup mViewGroupParent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext); //nv
        //View mItemView = mLayoutInflater.inflate(R.layout.card_item_product_list, null);
        //View mItemView = mLayoutInflater.inflate(R.layout.card_item_product_list, mViewGroupParent, false);
        View mItemView = mLayoutInflater.inflate(R.layout.card_item_product_list_2, mViewGroupParent, false);
        return new ProductViewHolder(mItemView);
    }

    @Override        // vinculo dos objetos da tela com o objeto do java
    public void onBindViewHolder(@NonNull ProductViewHolder mProductViewHolder, int vPosition) {
        Product mProductCurrent = mProductList.get(vPosition);
        mProductViewHolder.mTextViewItemProductDescription.setText(mProductCurrent.getName());

        // https://pt.stackoverflow.com/questions/30701/formata%C3%A7%C3%A3o-de-um-double-em-java
        // https://pt.stackoverflow.com/questions/219211/qual-a-forma-correta-de-usar-os-tipos-float-double-e-decimal
        // https://github.com/maniero/SOpt/blob/master/Conceptual.md  MARCOS DIVULGUE PARA OS ALUNOS

//        mProductViewHolder.mTextViewItemProductPrice.setText(String.valueOf(mProductCurrent.getPrice()));
        String mStringPrice = String.format("%.2f", mProductCurrent.getPrice());
        mProductViewHolder.mTextViewItemProductPrice.setText(mStringPrice);
        mProductViewHolder.mTextViewItemProductPrice.setTextColor(Color.parseColor(setPriceColor(mProductCurrent.getRating())));
        mProductViewHolder.mRatingBarItemProduct.setRating(mProductCurrent.getRating());

        mProductViewHolder.mButtonProductQuantity.setText(""+mProductCurrent.getUnit());

        // veja aqui ProductViewHolder - resposta de muitos erros
 //deveria ser aqui       mProductViewHolder.mButtonProductQuantity.setText(""+vQuantity);

        //mProductViewHolder.mImageViewProduct.setImageDrawable(mContext.getResources().getDrawable(mProductCurrent.getImage()));

        //Glide.with().load()    imagem - pesquise

    }

    @Override
    public int getItemCount() {
        if(mProductList != null){
            return  mProductList.size();
        } else {
            return 0;
        }
    }

    public void setProductList(List<Product> mProducts){
        mProductList = mProducts;
        mProductListFull = new ArrayList<>(mProducts);
        notifyDataSetChanged();
    }

    public Product getProductAt(int vPosition){
        return mProductList.get(vPosition);
    }

    public void setOnItemClickListener(View.OnClickListener mItemClickListener){
        mOnItemClickListener = mItemClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener mItemLongClickListener){
        mOnLongClickListener = mItemLongClickListener;
    }

    private Filter applyProductFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Product> mFilteredList = new ArrayList<>();
            if(charSequence == null  ||  charSequence.length() == 0 ) {
                mFilteredList.addAll(mProductListFull);
            } else {
                String mFilterPattern = charSequence.toString().toLowerCase().trim();
                for(Product mProduct : mProductListFull ) {
                    if(mProduct.getName().toLowerCase().contains(mFilterPattern)){
                        mFilteredList.add(mProduct);
                    }
                }
            }
            FilterResults mFilterResults = new FilterResults();
            mFilterResults.values = mFilteredList;
            return mFilterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mProductList.clear();
            mProductList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter(){
        return applyProductFilter;
    }

}
