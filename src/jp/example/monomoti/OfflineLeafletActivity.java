package jp.example.monomoti;

import jp.example.monomoti.MyBuiltInDatabase;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.util.Base64;

public class OfflineLeafletActivity extends Activity {
	private MyBuiltInDatabase mdb;
	private SQLiteDatabase db;

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WebView myWebView=(WebView)findViewById(R.id.my_webview);
        myWebView.setWebViewClient(new WebViewClient());
        
        myWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater)
            {
                    quotaUpdater.updateQuota(1024 * 1024 * 2);
            }
        });
        String dbPath = new StringBuffer("/data/data/").append(getPackageName()).append("/databases").toString();
        
        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        
        settings.setLoadsImagesAutomatically(true);
        settings.setPluginsEnabled(true);
        settings.setSupportZoom(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(dbPath);
        
        myWebView.addJavascriptInterface(new JsObject(), JsObject.JS_OBJECT_NAME);
        
	    try {  
	        mdb = new MyBuiltInDatabase(this,"control-room",1);
	        db = mdb.getReadableDatabase();
	    } catch(SQLException sqle){  
	        throw sqle;
	    }  	
        
        myWebView.loadUrl("file:///android_asset/map.html");
        
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
        db.close();
        mdb.close();
	}
	
    private class JsObject {
    	private static final String JS_OBJECT_NAME = "Android";
    	
    	public String getTileUrl(int z,int x, int y){
	        String[] columns = new String[]{"tile_data"};
	        String selection = "zoom_level = ? AND tile_column = ? AND tile_row =?";
	        String[] selectionArgs = {String.valueOf(z),String.valueOf(x),String.valueOf(y)};
	        String rtn = null;
	        Cursor c = db.query("tiles", columns, selection, selectionArgs, null, null, null);
	        if (c.moveToFirst()){
	        	do{
	        		rtn = Base64.encodeToString(c.getBlob(c.getColumnIndex("tile_data")), Base64.DEFAULT);
	        	}while(c.moveToNext());
	        }
	        c.close();
    		return rtn;
    	}
    	
    	
    }

}