package example.healthassistant;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserData extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button createUser;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_data);
        email = (EditText)findViewById(R.id.signUpEmail);
        password = (EditText) findViewById(R.id.createPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        createUser = (Button) findViewById(R.id.createAccount);
        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString()!=""){
                    if(!password.getText().toString().equals("")&&!confirmPassword.getText().toString().equals("")){
                        if(password.getText().toString().equals(confirmPassword.getText().toString())){
                        //    String[] projection = {DbContract.DbEntry.COLUMN_EMAIL, DbContract.DbEntry.COLUMN_PASSWORD};
                        //   Cursor data = mDb.query(DbContract.DbEntry.TABLE_NAME,projection, DbContract.DbEntry.COLUMN_EMAIL+ " = "+ email.getText().toString(),
                          //          null,null,null,null,null);
                         //   if(data.getCount()==0){
                            addData(email.getText().toString(),password.getText().toString());
                            Intent i = new Intent(NewUserData.this, PHR.class);
                            startActivity(i);

                        //    else
                        //        Toast.makeText(NewUserData.this, "Use other email address. This one already registered",Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(NewUserData.this, "Password and confirm password don't match",Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(NewUserData.this, "Enter valid Passwords",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(NewUserData.this, "Enter valid Email Address",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void addData(String email, String password) {
        DbHelper db = new DbHelper(this);
        mDb = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.DbEntry.COLUMN_EMAIL, email);
        cv.put(DbContract.DbEntry.COLUMN_PASSWORD, password);
        try {
            long result = mDb.insert(DbContract.DbEntry.TABLE_NAME, null, cv);

            if (result != -1)
                Toast.makeText(this, "Inserted successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException ex){
            String s = ex.getMessage();
        }
    }


}
