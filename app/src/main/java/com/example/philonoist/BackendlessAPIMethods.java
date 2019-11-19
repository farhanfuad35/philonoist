package com.example.philonoist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class BackendlessAPIMethods {
    public static void logOut(final Context context) {
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            @Override
            public void handleResponse(Void response) {
                Toast.makeText(context, "You are successfully logged out!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Sorry couldn't logout right now. Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

//
//    public static void Login(final Context context, final String email, final String password, Boolean stayLoggedIn, final Activity activity)
//    {
//        Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
//            @Override
//            public void handleResponse(BackendlessUser response) {
//                CONSTANTS.setCurrentUserEmail(email);
//
//                FileMethods.writes(context, email);
//                System.out.println("logged in "+email);
//
//                Intent intent = new Intent(context, com.example.philonoist.TuitionList.class);
//                context.startActivity(intent);
//                activity.finish();
//
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
//                btLogin.setText("Login");
//            }
//        }, stayLoggedIn);
//    }
}
