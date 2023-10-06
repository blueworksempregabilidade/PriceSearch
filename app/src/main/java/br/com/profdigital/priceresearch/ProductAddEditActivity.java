package br.com.profdigital.priceresearch;

// 4

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddEditActivity extends AppCompatActivity {

    private static final String TAG = "ProductAddEditActivity";

    String mMode;

    TextView mTextViewCancel;

    Button mButtonSaveProduct;
    EditText mEditTextName, mEditTextPrice;

    RatingBar mRatingBarPricePerception;

    String mMessage;

   Slider mSliderConsumptionCycle;
   int vSliderValueAmountConsumption;

   ChipGroup mChipGroupOption;
   Chip mChip0, mChip1, mChip2, mChip3, mChip4;
   int vChipValueConsumptionCycle;




    private void performCancel(){
        Intent mIntentReply = new Intent();
        setResult(RESULT_CANCELED, mIntentReply);
        finish();
    }


    public class ClickMyCancel implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            performCancel();
        }
    }

    private boolean isRequired() {
        if( TextUtils.isEmpty(mEditTextName.getText()) ||
            TextUtils.isEmpty(mEditTextPrice.getText()) ||
                vChipValueConsumptionCycle == 0 )
        {
            return true;
        } else {
            return false;
        }

    }

    private void saveProduct(){
        if(isRequired()){
            mMessage = "Mandatory information";
            Toast.makeText(getApplicationContext(), mMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent mIntentResponse = new Intent();

        mMode = "ADD";
        int vId = getIntent().getIntExtra("EXTRA_ID", -1);
        if( vId != -1 ) {
            mMode = "EDIT";
            mIntentResponse.putExtra("EXTRA_ID" , vId);
        }

        String mStringName = mEditTextName.getText().toString().trim();
        mIntentResponse.putExtra("EXTRA_NAME", mStringName);

        float vPrice = Float.valueOf(mEditTextPrice.getText().toString());  //erro de conversao 0,00   o separador decimal
        mIntentResponse.putExtra("EXTRA_PRICE", vPrice);

        float vPerception = mRatingBarPricePerception.getRating();
        mIntentResponse.putExtra("EXTRA_PERCEPTION_PRICE", vPerception);

        mIntentResponse.putExtra( "EXTRA_AMOUNT_CONSUMPTION", vSliderValueAmountConsumption);

        mIntentResponse.putExtra("EXTRA_CONSUMPTION_CYCLE",vChipValueConsumptionCycle);

        mIntentResponse.putExtra("EXTRA_MODE", mMode);

        setResult(RESULT_OK, mIntentResponse);
        finish();
    }


    public class ClickMyButtonSave implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            saveProduct();
        }
    }

    public class SliderMyTouch implements Slider.OnChangeListener{

        @Override
        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
            vSliderValueAmountConsumption = (int)value;
            mChip0.setText( vSliderValueAmountConsumption + "X");
        }

    }

    public class ChipGroupMySelectionChip implements ChipGroup.OnCheckedStateChangeListener{
        @Override
        public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
            switch (group.getCheckedChipId()){
                case R.id.chip_consumption_cycle_1: vChipValueConsumptionCycle=1; break;
                case R.id.chip_consumption_cycle_2: vChipValueConsumptionCycle=2; break;
                case R.id.chip_consumption_cycle_3: vChipValueConsumptionCycle=3; break;
                case R.id.chip_consumption_cycle_4: vChipValueConsumptionCycle=4; break;
                default: vChipValueConsumptionCycle = 0;
            }
            //Toast.makeText(getApplicationContext(), "Chip " + vChipValueConsumptionCycle, Toast.LENGTH_SHORT).show();
        }
    }

    private void setChipNumber(int vChipValueConsumptionCycle){
        switch (vChipValueConsumptionCycle){
            case 1: mChip1.setChecked(true); break;
            case 2: mChip2.setChecked(true); break;
            case 3: mChip3.setChecked(true); break;
            case 4: mChip4.setChecked(true); break;
        }
    }


    // https://pt.stackoverflow.com/questions/279149/tratamento-casas-decimais-edittext
    public class FormatInputNumber implements InputFilter{
        int vDigitsBeforeZero;
        int vDigitsAfterZero;
        Pattern mPattern;
        private static final int DIGITS_BEFORE_ZERO_DEFAULT = 100;
        private static final int DIGITS_AFTER_ZERO_DEFAULT = 100;

        public FormatInputNumber(Integer digitsBeforeZero, Integer digitsAfterZero) {
            vDigitsBeforeZero = (digitsBeforeZero != null ? digitsBeforeZero : DIGITS_BEFORE_ZERO_DEFAULT);
            vDigitsAfterZero = (digitsAfterZero != null ? digitsAfterZero : DIGITS_AFTER_ZERO_DEFAULT);
            mPattern = Pattern.compile("-?[0-9]{0," + (vDigitsBeforeZero) + "}+((\\.[0-9]{0," + (vDigitsAfterZero) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence charSequenceSource, int vStart, int vEnd, Spanned spannedDest, int vDestStart, int vDestEnd) {
            String replacement = charSequenceSource.subSequence(vStart, vEnd).toString();
            String newVal = spannedDest.subSequence(0, vDestStart).toString() + replacement + spannedDest.subSequence(vDestEnd, spannedDest.length()).toString();
            Matcher matcher = mPattern.matcher(newVal);
            if (matcher.matches()){
                return  null;
            }
            if(TextUtils.isEmpty(charSequenceSource)){
                return spannedDest.subSequence(vDestStart, vDestEnd);
            } else {
                return "";
            }
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        mTextViewCancel = findViewById(R.id.textView_cancel_product_add_edit);
        mTextViewCancel.setOnClickListener(new ClickMyCancel());

        mButtonSaveProduct = findViewById(R.id.button_save_product);
        mButtonSaveProduct.setOnClickListener(new ClickMyButtonSave());

        mEditTextName = findViewById(R.id.editText_product_name);
        mEditTextPrice = findViewById(R.id.editText_product_price);
        mRatingBarPricePerception = findViewById(R.id.rating_product_price_perception);

// https://www.youtube.com/watch?v=IibybM4oM1w
        //https://www.geeksforgeeks.org/how-to-customise-mdc-sliders-in-android/
       mSliderConsumptionCycle = findViewById(R.id.slider_consumption_cycle);
       mSliderConsumptionCycle.addOnChangeListener(new SliderMyTouch());

       mChipGroupOption = findViewById(R.id.chip_group_option);
       mChipGroupOption.setOnCheckedStateChangeListener(new ChipGroupMySelectionChip());

       mChip0 = findViewById(R.id.chip_consumption_cycle_0);
       mChip1 = findViewById(R.id.chip_consumption_cycle_1);
       mChip2 = findViewById(R.id.chip_consumption_cycle_2);
       mChip3 = findViewById(R.id.chip_consumption_cycle_3);
       mChip4 = findViewById(R.id.chip_consumption_cycle_4);

        Intent mIntentShow = getIntent();

        if(mIntentShow.hasExtra("EXTRA_ID")){
            setTitle("Product Edit");

            mEditTextName.setText(mIntentShow.getStringExtra("EXTRA_NAME"));

            double value = mIntentShow.getDoubleExtra("EXTRA_PRICE", 0); //hack - not running with float
            //String mStringPrice = String.format("%.2f", value);
            String mStringPrice = String.format(java.util.Locale.ROOT,"%.2f", value); //hack - show point separate
            mEditTextPrice.setText(mStringPrice);
            mEditTextPrice.setFilters(new InputFilter[]{new FormatInputNumber(10,2)}); //https://pt.stackoverflow.com/questions/279149/tratamento-casas-decimais-edittext

            mRatingBarPricePerception.setRating(mIntentShow.getFloatExtra("EXTRA_PERCEPTION_PRICE", 0.0f));

            vSliderValueAmountConsumption = mIntentShow.getIntExtra("EXTRA_AMOUNT_CONSUMPTION",1);
            mSliderConsumptionCycle.setValue(vSliderValueAmountConsumption);

            vChipValueConsumptionCycle = mIntentShow.getIntExtra("EXTRA_CONSUMPTION_CYCLE",0);
            setChipNumber(vChipValueConsumptionCycle);

        } else {
            setTitle("New Product");
            vSliderValueAmountConsumption = 1;
            vChipValueConsumptionCycle = 0;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch ( item.getItemId() ) {
            case R.id.action_menu_save:
                saveProduct();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}