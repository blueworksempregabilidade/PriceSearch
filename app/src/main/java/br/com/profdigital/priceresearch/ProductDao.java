package br.com.profdigital.priceresearch;

// 2

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private static final String TAG = "ProductDao";

    public static int insertProduct(Product mProduct, Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "INSERT products (name, price, rating, status, image, amountConsumption, consumptionCycle) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);

            mPreparedStatement.setString(1, mProduct.getName());
            mPreparedStatement.setDouble(2, mProduct.getPrice());
            mPreparedStatement.setFloat(3, mProduct.getRating());
            mPreparedStatement.setInt(4, mProduct.getStatus());
            mPreparedStatement.setInt(5, mProduct.getImage());
            mPreparedStatement.setInt(6, mProduct.getAmountConsumption());
            mPreparedStatement.setInt(7, mProduct.getConsumptionCycle());
            vResponse = mPreparedStatement.executeUpdate(); // executou com sucesso será 1

        }catch (Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;

    }

    public static int updateProduct(Product mProduct, Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "UPDATE products SET name=?, price=?, rating=?, status=?, image=?, amountConsumption=?, consumptionCycle=? WHERE id=?";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mProduct.getName());
            mPreparedStatement.setDouble(2, mProduct.getPrice());
            mPreparedStatement.setFloat(3, mProduct.getRating());
            mPreparedStatement.setInt(4, mProduct.getStatus());
            mPreparedStatement.setInt(5, mProduct.getImage());
            mPreparedStatement.setInt(6, mProduct.getAmountConsumption());
            mPreparedStatement.setInt(7, mProduct.getConsumptionCycle());
            mPreparedStatement.setInt(8, mProduct.getId());
            vResponse = mPreparedStatement.executeUpdate(); // executou com sucesso será 1

        }catch (Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;

    }

    public static int deleteProduct(Product mProduct, Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "Delete from products where id=?";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setInt(1, mProduct.getId());
            vResponse = mPreparedStatement.executeUpdate();

        }catch(Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;

    }

    public static int deleteAllProducts(Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "Delete from products";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            vResponse = mPreparedStatement.executeUpdate();

        }catch(Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;

    }

    public static List<Product> listAllProducts(Context mContext){

        // Listagem de produtos
        List<Product> mProductList = null;
        String mSql;

        try{
            mSql = "Select id, name, price, rating, status, image, amountConsumption, consumptionCycle from products order by name ASC";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            ResultSet mResultSet = mPreparedStatement.executeQuery();
            mProductList = new ArrayList<Product>();
            while(mResultSet.next()){
                mProductList.add(new Product(
                        mResultSet.getInt(1),
                        mResultSet.getString(2),
                        mResultSet.getDouble(3), // price
                        mResultSet.getFloat(4), //rating
                        mResultSet.getInt(5),
                        mResultSet.getInt(6),
                        mResultSet.getInt(7),
                        mResultSet.getInt(8),
                        0
                ));
            }

        }catch(Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return mProductList;

    }

    public static List<Product> searchProductsByName(String mStringName, Context mContext){

        // Listagem de produtos
        List<Product> mProductList = null;
        String mSql;

        try{
            mSql = "Select id, name, price, rating, status, image,  amountConsumption, consumptionCycle from products where name like '%" + mStringName + "%' order by name ASC";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            ResultSet mResultSet = mPreparedStatement.executeQuery();
            mProductList = new ArrayList<Product>();
            while(mResultSet.next()){
                mProductList.add(new Product(
                        mResultSet.getInt(1),
                        mResultSet.getString(2),
                        mResultSet.getDouble(3),
                        mResultSet.getFloat(4),
                        mResultSet.getInt(5),
                        mResultSet.getInt(6),
                        mResultSet.getInt(7),
                        mResultSet.getInt(8),
                        0
                ));
            }

        }catch(Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return mProductList;

    }

}
