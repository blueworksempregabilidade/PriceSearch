package br.com.profdigital.priceresearch;

// 2

import android.content.Context;
import android.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {

    private static final String TAG = "UserDao";

    public static int insertUser(User mUser, Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "Insert into USERS (fullname, username, password, email) values (?, ?, ?, ?)";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setString(1, mUser.getFullName());
            mPreparedStatement.setString(2, mUser.getUserName());
            mPreparedStatement.setString(3, mUser.getPassword());
            mPreparedStatement.setString(4, mUser.getEmail());
            vResponse = mPreparedStatement.executeUpdate(); // executou com sucesso será 1

        }catch (Exception mException){
           Log.e(TAG, mException.getMessage());
           mException.printStackTrace();
        }

        return vResponse;

    }

    public static String authenticateUser(User mUser, Context mContext){
        String mResponse = "";
        String mSql;
        try{

            //https://alvinalexander.com/blog/post/jdbc/jdbc-preparedstatement-select-like/

            mSql = "SELECT id, fullName, email, password FROM users WHERE password like ? and email like ?";
            //mSql = "SELECT id, fullName, email, password FROM users WHERE password = ? and email = ?";

            //https://pt.stackoverflow.com/questions/369624/statement-ou-preparedstatement-por-qual-motivo-evitar-usar-o-statement

            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
//            mPreparedStatement.setString(1, "%" + mUser.getPassword() + "%");
//            mPreparedStatement.setString(2, "%" + mUser.getEmail() + "%");
            mPreparedStatement.setString(1, mUser.getPassword());
            mPreparedStatement.setString(2, mUser.getEmail());
            ResultSet mResultSet = mPreparedStatement.executeQuery();
            while(mResultSet.next()){
                mResponse = mResultSet.getString(2); // veja o objeto 'mSql'
            }

        } catch(Exception mException){
            mResponse = "Exception";
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return mResponse;
    }


    public static int deleteProduct(User mUsers, Context mContext){

        int vResponse = 0;
        String mSql;

        try{
            mSql = "Delete from users where id=?";
            PreparedStatement mPreparedStatement = MSSQLConnectionHelper.getConnection(mContext).prepareStatement(mSql);
            mPreparedStatement.setInt(1, mUsers.getId());
            vResponse = mPreparedStatement.executeUpdate();

        }catch(Exception mException){
            Log.e(TAG, mException.getMessage());
            mException.printStackTrace();
        }

        return vResponse;

    }


}
