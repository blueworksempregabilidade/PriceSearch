package br.com.profdigital.priceresearch;

// 1

public class Product {

    private static final String TAG = "Product";

    private int mUnit; // não usado no banco de dados apenas controle na lista

    public int getUnit() {
        return mUnit;
    }

    public void setUnit(int unit) {
        mUnit = unit;
    }

    private int mId;
    private String mName;
    private double mPrice;

    public int getId() {
        return mId;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    private int mStatus;

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public Product(String name, double price, float rating, int status, int image, int amountConsumption, int consumptionCycle, int unit) {
        mName = name;
        mPrice = price;
        mRating = rating;
        mStatus = status;
        mImage = image;
        mAmountConsumption = amountConsumption;
        mConsumptionCycle = consumptionCycle;
        mUnit = unit;
    }

    public Product(int id, String name, double price, float rating, int status, int image, int amountConsumption, int consumptionCycle, int unit) {
        mId = id;
        mName = name;
        mPrice = price;
        mStatus = status;
        mRating = rating;
        mImage = image;
        mAmountConsumption = amountConsumption;
        mConsumptionCycle = consumptionCycle;
        mUnit = unit;
    }

    private float mRating;

    private int mImage;

    public int getImage() {
        return mImage;
    }

    public int getAmountConsumption() {
        return mAmountConsumption;
    }

    public void setAmountConsumption(int amountConsumption) {
        mAmountConsumption = amountConsumption;
    }

    public int getConsumptionCycle() {
        return mConsumptionCycle;
    }

    public void setConsumptionCycle(int consumptionCycle) {
        mConsumptionCycle = consumptionCycle;
    }

    public void setImage(int image) {
        mImage = image;
    }


    private int mAmountConsumption;
    private int mConsumptionCycle;

}
