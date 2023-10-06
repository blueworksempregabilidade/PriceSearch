package br.com.profdigital.priceresearch;

// 5

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "ProductActivity";

    public String TEXT_SEARCH_RESULT = "my text search";
    public ProductAdapter mProductAdapter;

    TextView mTextViewFullName, mTextViewTotalValue;

    ExtendedFloatingActionButton mFloatingActionButtonAction;
    FloatingActionButton mFloatingActionButtonLogout;
    FloatingActionButton mFloatingActionButtonAddProduct;

    FloatingActionButton mFloatingActionButtonFinalizeList;

    TextView mTextViewAddProduct, mTextViewLogout, mTextViewFinalize;

    Boolean isAllFabsVisible; //para verificar se os sub FABs são visíveis ou não

    SharedPreferences mSharedPreferences;

    //https://www.youtube.com/watch?v=6E5ODrmUtmo
    //    bom   https://www.youtube.com/watch?v=Ke9PaRdMcgc
    // esclareceu melhor https://www.youtube.com/watch?v=qO3FFuBrT2E
//https://developer.android.com/training/basics/intents/result?hl=pt-br
    ActivityResultLauncher<Intent>  mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                Bundle mBundleResponse = result.getData().getExtras();
                int vId = mBundleResponse.getInt("EXTRA_ID", -1);
                String mName = mBundleResponse.getString("EXTRA_NAME");
                float vPrice = mBundleResponse.getFloat("EXTRA_PRICE", -1.0f);
                float vPerception = mBundleResponse.getFloat("EXTRA_PERCEPTION_PRICE", -1.0f);
                int vAmountConsumption = mBundleResponse.getInt("EXTRA_AMOUNT_CONSUMPTION", -1);
                int vConsumptionCycle = mBundleResponse.getInt("EXTRA_CONSUMPTION_CYCLE", -1);
                String mMode = mBundleResponse.getString("EXTRA_MODE");

                String mMessage = "CRUD message";

                if(mMode.equals("EDIT")){
                    Product mProduct = new Product(vId, mName, vPrice, vPerception, 1, 0, vAmountConsumption, vConsumptionCycle, 0);
                    int vResult = ProductDao.updateProduct(mProduct, getApplicationContext());
                    if(vResult <= 0){
                        mMessage = "An error occurred while updating the product";
                    } else {
                        setupRecyclerView();
                        mMessage = "Successfully updated product";
                    }
                } else {
                    Product mProduct = new Product(mName, vPrice, vPerception, 1, 0, vAmountConsumption, vConsumptionCycle, 0);
                    int vResult = ProductDao.insertProduct(mProduct, getApplicationContext());
                    if(vResult <= 0){
                        mMessage = "An error occurred while inserting the product";
                    } else {
                        setupRecyclerView();
                        mMessage = "Product inserted successfully";
                    }
                }
                mTextViewTotalValue.setText("R$ 0.00");
                Toast.makeText(ProductActivity.this, mMessage, Toast.LENGTH_SHORT).show();
            } else if(result.getResultCode() == RESULT_CANCELED){
               // Toast.makeText(ProductActivity.this, "Action to add or edit was canceled", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.screen_products_list), "Action to add or edit was canceled", Snackbar.LENGTH_SHORT).show();
                //snackbar muitas possibilidades    https://www.geeksforgeeks.org/how-to-add-a-snackbar-in-android/
            } else {
                //Toast.makeText(ProductActivity.this, "An unexpected error occurred", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.screen_products_list), "An unexpected error occurred", Snackbar.LENGTH_SHORT).show();
            }
        }
    });

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder mViewHolder = (RecyclerView.ViewHolder) view.getTag();
            int vPosition = mViewHolder.getAdapterPosition();
            Intent mIntent = new Intent(getApplicationContext(), ProductAddEditActivity.class);
            //Intent mIntent = new Intent(ProductActivity.this, ProductAddEditActivity.class);
            mIntent.putExtra("EXTRA_ID", mProductAdapter.getProductAt(vPosition).getId());
            mIntent.putExtra("EXTRA_NAME", mProductAdapter.getProductAt(vPosition).getName());
            mIntent.putExtra("EXTRA_PRICE", mProductAdapter.getProductAt(vPosition).getPrice());
            mIntent.putExtra("EXTRA_PERCEPTION_PRICE", mProductAdapter.getProductAt(vPosition).getRating());
            mIntent.putExtra("EXTRA_AMOUNT_CONSUMPTION", mProductAdapter.getProductAt(vPosition).getAmountConsumption());
            mIntent.putExtra("EXTRA_CONSUMPTION_CYCLE", mProductAdapter.getProductAt(vPosition).getConsumptionCycle());
            mActivityResultLauncher.launch(mIntent);
        }
    };

//    private void showPopupMenu(View mItemView){  //assista a esse video https://www.youtube.com/watch?v=fl5BB3I3MvQ   e   https://www.youtube.com/watch?v=WsUeeeKte3I  e https://www.youtube.com/watch?v=tIRGRVzYuhc
//        // Criação do PopupMenu
//        PopupMenu popup = new PopupMenu(mItemView.getContext(), mItemView);
//        MenuInflater inflater = popup.getMenuInflater();
////        // Inflar o menu com o arquivo xml
//        inflater.inflate(R.menu.reaction_menu, popup.getMenu());
//
//       // Definir o ClickListener
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.reaction_edit:
//                        // Implemente a ação para a reação de "like"
//                        Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.reaction_shop:
//                        // Implemente a ação para a reação de "love"
//                        Toast.makeText(getApplicationContext(), "Shop", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.reaction_delete:
//                        // Implemente a ação para a reação de "love"
//                        Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.reaction_reset:
//                        // Implemente a ação para a reação de "love"
//                        Toast.makeText(getApplicationContext(), "Reset", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.reaction_close:
//                        // Implemente a ação para a reação de "love"
//                        Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
//                        return true;
//                    // adicione mais casos para mais reações
//                    default:
//                        return false;
//                }
//            }
//        });
//
//
//        Toast.makeText(this, "LONG PRESS", Toast.LENGTH_SHORT).show();
//        // Mostrar o PopupMenu
////        popup.inflate(R.menu.reaction_menu);
////        popup.setGravity(Gravity.RIGHT);
//   //erro     popup.show();
//    }


//    private View.OnLongClickListener onLongItemClickListener = new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View view) {
//            showPopupMenu(view);
//            return false;
//        }
//    };

    private void setupRecyclerView(){
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView_products);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
//        mProductAdapter = new ProductAdapter(getApplicationContext(), ProductDao.listAllProducts(getApplicationContext()) ); //aqui é o segredo
        mProductAdapter = new ProductAdapter(getApplicationContext(), ProductDao.listAllProducts(getApplicationContext()) , mTextViewTotalValue ); //a - nv2 na classe ProductAdapter
        // https://stackoverflow.com/questions/25187400/updating-textview-on-activity-once-data-in-adapter-class-is-chaqui é o segredo  nged resposta do mmlooloo em 07-08-2014
        mRecyclerView.setAdapter(mProductAdapter);

        //tem mais código aqui veja TaskActivity.java do app Gerenciamento de tarefas
        mProductAdapter.setProductList(ProductDao.listAllProducts(getApplicationContext()));

        mProductAdapter.setOnItemClickListener(onItemClickListener);

      //  mProductAdapter.setOnLongClickListener(onLongItemClickListener);

        // 17-03 https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28  botoes para editar e deletar

    }

    private void verifyNotLogged(){
        if(mSharedPreferences.getString("logged", "false").equals("false")){
            showLogin();
        } else {
            //https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/
            mTextViewFullName.setText(mSharedPreferences.getString("fullName", ""));
            setupRecyclerView();
        }
    }

    private void showLogin(){
        Intent mIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(mIntent);
        finish();
    }

    private void exitAppLogoff(){
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("logged" , "");
        mEditor.putString("email" , "");
        mEditor.putString("fullName", "");
        mEditor.apply();
        showLogin();
    }

    public class ClickMyButtonLogout implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            exitAppLogoff();
        }
    }

    private void changeStateFabsAndTexts(){
        if(!isAllFabsVisible){
            mFloatingActionButtonAddProduct.show();
            mFloatingActionButtonLogout.show();
            mFloatingActionButtonFinalizeList.show();
            mTextViewAddProduct.setVisibility(View.VISIBLE);
            mTextViewLogout.setVisibility(View.VISIBLE);
            mTextViewFinalize.setVisibility(View.VISIBLE);
            mFloatingActionButtonAction.extend();
            isAllFabsVisible=true;
        } else {
            mFloatingActionButtonAddProduct.hide();
            mFloatingActionButtonLogout.hide();
            mFloatingActionButtonFinalizeList.hide();
            mTextViewAddProduct.setVisibility(View.GONE);
            mTextViewLogout.setVisibility(View.GONE);
            mTextViewFinalize.setVisibility(View.GONE);
            mFloatingActionButtonAction.shrink();
            isAllFabsVisible=false;
        }
    }

    public class ClickMyButtonAction implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            changeStateFabsAndTexts();
        }
    }

    private void showAddProduct(){
        //https://developer.android.com/training/basics/intents/result?hl=pt-br
        changeStateFabsAndTexts();
        Intent mIntent = new Intent(ProductActivity.this, ProductAddEditActivity.class);
        mActivityResultLauncher.launch(mIntent);
    }

    public class ClickMyButtonAdd implements View.OnClickListener{ //androidmanifest
        @Override
        public void onClick(View view) {
            showAddProduct();
        }
    }


    private void finalizeProductList(){
        Toast.makeText(getApplicationContext(), "Vai finalizar", Toast.LENGTH_SHORT).show();
        //logica para obter os itens com quantidade e gerar lista
    }

    public class ClickMyButtonFinalizeList implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finalizeProductList();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mTextViewFullName = findViewById(R.id.textView_fullName);

        Intent mIntent = getIntent();
        if(mIntent.hasExtra("EXTRA_FULL_NAME")){
            mTextViewFullName.setText(mIntent.getStringExtra("EXTRA_FULL_NAME"));
        }

        mFloatingActionButtonAction = findViewById(R.id.fab_action);
        mFloatingActionButtonAction.setOnClickListener(new ClickMyButtonAction());

        mFloatingActionButtonLogout = findViewById(R.id.fab_exit_app);
        mFloatingActionButtonLogout.setOnClickListener(new ClickMyButtonLogout());

        mFloatingActionButtonAddProduct = findViewById(R.id.fab_add_product);
        mFloatingActionButtonAddProduct.setOnClickListener(new ClickMyButtonAdd());

        mFloatingActionButtonFinalizeList = findViewById(R.id.fab_finalize_product_list);
        mFloatingActionButtonFinalizeList.setOnClickListener(new ClickMyButtonFinalizeList());

        mTextViewAddProduct = findViewById(R.id.textView_add_product);
        mTextViewLogout = findViewById(R.id.textView_logout_product);
        mTextViewFinalize = findViewById(R.id.textView_finalize_product_list);

        mTextViewTotalValue = findViewById(R.id.textView_total_value);

        mFloatingActionButtonLogout.setVisibility(View.GONE);
        mFloatingActionButtonAddProduct.setVisibility(View.GONE);
        mFloatingActionButtonFinalizeList.setVisibility(View.GONE);

        mTextViewLogout.setVisibility(View.GONE);
        mTextViewAddProduct.setVisibility(View.GONE);
        mTextViewFinalize.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mFloatingActionButtonAction.shrink(); //estado reduzido inicialmente

        //https://stackoverflow.com/questions/30699302/android-design-support-library-expandable-floating-action-buttonfab-menu

        // https://medium.com/nerd-for-tech/how-to-add-extended-floating-action-button-in-android-android-studio-java-481cc9b3cdcb
        // https://www.youtube.com/watch?v=AIMZBwORt1k&t=548s

        mSharedPreferences = getSharedPreferences("MyAppName" , MODE_PRIVATE);
        verifyNotLogged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        // veja a região de import
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_main_menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TEXT_SEARCH_RESULT = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProductAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){ //tres pontinhos

        isAllFabsVisible=false;
        changeStateFabsAndTexts();

        switch (item.getItemId()) {
            case R.id.action_add:
                showAddProduct();
                return true;
            case R.id.action_delete_all:
                //mTaskViewModel.deleteAll();
                Toast.makeText(this, "All products have been deleted.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
