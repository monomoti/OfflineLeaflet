package jp.example.monomoti;

import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyBuiltInDatabase extends SQLiteAssetHelper {
	
	public MyBuiltInDatabase(Context context,String dbName,int dbVersion) {
		super(context, dbName, null, dbVersion);
	}
}
