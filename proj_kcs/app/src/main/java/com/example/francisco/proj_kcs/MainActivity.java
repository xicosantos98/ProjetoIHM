package com.example.francisco.proj_kcs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitomi.cmlibrary.CircleMenu;
import com.nex3z.notificationbadge.NotificationBadge;

import java.security.acl.Group;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper databaseHelper;

    private ListView list;
    private GridLayout gridLayout;
    private RelativeLayout relativeLayout;
    private static String id_utilizador;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NotificationBadge badge;
    int id_compra;
    Cursor cursor;
    int id_user;
    ImageView cart_icon;
    int numCompras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home);
        getSupportActionBar().setElevation(0);
        //list = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.principal, homeFragment, homeFragment.getTag()).commit();

        //Intent intent = getIntent();
        id_utilizador = LoginActivity.getUserLogin();


        id_user = databaseHelper.getIdUserOnline(id_utilizador);
        Cursor cursor_carrinho = databaseHelper.getProdutctsCartUser(LoginActivity.getUserLogin());
        numCompras = cursor_carrinho.getCount();





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();

            } else {
                //super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        View view = menu.findItem(R.id.cart).getActionView();
        badge = (NotificationBadge) view.findViewById(R.id.badge);
        updateCartCount();
        cart_icon = (ImageView) view.findViewById(R.id.cardIcon);
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle(R.string.cart);
                CartFragment cartFragment = new CartFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_down, R.anim.slide_down)
                        .addToBackStack(null)
                        .replace(R.id.principal, cartFragment, cartFragment.getTag()).commit();
            }
        });
        return true;
    }

    private void updateCartCount() {

        if (badge == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(numCompras == 0){
                    badge.setVisibility(View.INVISIBLE);
                }
                else{
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(numCompras));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            //return true;
            getSupportActionBar().setTitle(R.string.cart);
            CartFragment cartFragment = new CartFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_down, R.anim.slide_down)
                    .addToBackStack(null)
                    .replace(R.id.principal, cartFragment, cartFragment.getTag()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, homeFragment, homeFragment.getTag()).commit();
            getSupportActionBar().setTitle(R.string.home);
        }

        if (id == R.id.nav_products) {


            ProductsFragment productsFragment = new ProductsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, productsFragment, productsFragment.getTag()).commit();
            getSupportActionBar().setTitle(R.string.products);
        }

        if (id == R.id.nav_wish) {
            WishFragment wishFragment = new WishFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, wishFragment, wishFragment.getTag()).commit();
            getSupportActionBar().setTitle(R.string.wish);
        }

        if (id == R.id.nav_compras) {
            ProductsBoughtFragment productsBoughtFragment = new ProductsBoughtFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, productsBoughtFragment, productsBoughtFragment.getTag()).commit();
        }

        if (id == R.id.nav_reserva) {
            ProductsReservedFragment productsReservedFragmentFragment = new ProductsReservedFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, productsReservedFragmentFragment, productsReservedFragmentFragment.getTag()).commit();
        }

        if (id == R.id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.principal, settingsFragment, settingsFragment.getTag()).commit();
        }

        if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String getUser() {
        return MainActivity.id_utilizador;
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateCartCount();
    }
}


