package com.example.francisco.proj_kcs;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.nex3z.notificationbadge.NotificationBadge;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<String> produtos;
    ArrayAdapter produtosAdapter;
    AutoCompleteTextView autoCompleteTextView;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    TextView textView;
    Button btn;
    Cursor cursor, cursor_tipos;
    DatabaseHelper databaseHelper;
    String id_produto;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.home);
        setHasOptionsMenu(true);

        autoCompleteTextView = (AutoCompleteTextView) relativeLayout.findViewById(R.id.autoComplete);

        btn = (Button) relativeLayout.findViewById(R.id.allProducts);
        textView = (TextView) relativeLayout.findViewById(R.id.preco1);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //cardView = (CardView) relativeLayout.findViewById(R.id.card1);
        //autoCompleteTextView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        databaseHelper = new DatabaseHelper(getActivity());
        loadProduts();
        //cardView.setOnClickListener(this);
        btn.setOnClickListener(this);

        cardView1 = relativeLayout.findViewById(R.id.card_view_1);
        cardView1.setOnClickListener(this);
        cardView2 = relativeLayout.findViewById(R.id.card_view_2);
        cardView2.setOnClickListener(this);
        cardView3 = relativeLayout.findViewById(R.id.card_view_3);
        cardView3.setOnClickListener(this);
        cardView4 = relativeLayout.findViewById(R.id.card_view_4);
        cardView4.setOnClickListener(this);
        cardView5 = relativeLayout.findViewById(R.id.card_view_5);
        cardView5.setOnClickListener(this);
        cardView6 = relativeLayout.findViewById(R.id.card_view_6);
        cardView6.setOnClickListener(this);

        this.setRetainInstance(true);


        autoCompleteTextView.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND) {
                    hideSoftKeyboard(getActivity());
                    searchProducts();
                    handled = true;
                }
                return handled;
            }
        });

        int id_user = databaseHelper.getIdUserOnline(MainActivity.getUser());

        Cursor compra_aberta = databaseHelper.getCompraAberta(id_user);

        if(compra_aberta.getCount() == 0){
            databaseHelper.createCompra(id_user);
        }

        compra_aberta.getCount();


        return relativeLayout;
    }


    public void loadProduts() {
        produtos = new ArrayList<String>();

        cursor_tipos = databaseHelper.getAllTypeProduct();

        cursor_tipos.moveToFirst();

        do {
            produtos.add(cursor_tipos.getString(cursor_tipos.getColumnIndex(databaseHelper.TIPO_PRODUTO)));
        } while (cursor_tipos.moveToNext());


        /*produtos.add("Calças");
        produtos.add("Tenis");
        produtos.add("Calções");*/

        for (int s = 0; s < produtos.size() - 1; s++) {
            String ori = produtos.get(s);
            for (int m = s + 1; m < produtos.size(); m++) {
                if (produtos.get(m).equals(ori)) {
                    produtos.set(m, "");
                }
            }
        }

        produtosAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_row, produtos);
        autoCompleteTextView.setAdapter(produtosAdapter);

    }


    public void setupUI(View view) {
        autoCompleteTextView.clearFocus();
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
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

    public static void hideSoftKeyboard(FragmentActivity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        FragmentActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onClick(View view) {
        /*if(cardView == view){
            RoupaMasculinaFragment roupaFragment = new RoupaMasculinaFragment();
            android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.principal, roupaFragment);
            fragmentTransactionHome.commit();
        }*/
        if (btn == view) {
            ProductsFragment productsFragment = new ProductsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.principal, productsFragment);
            fragmentTransactionHome.addToBackStack(null);
            fragmentTransactionHome.commit();
        } else {

            if (view == cardView1) {
                id_produto = "2";
            }
            if (view == cardView2) {
                id_produto = "4";
            }
            if (view == cardView3) {
                id_produto = "5";
            }
            if (view == cardView4) {
                id_produto = "7";
            }
            if (view == cardView5) {
                id_produto = "8";
            }
            if (view == cardView6) {
                id_produto = "3";
            }


            DetailsFragment detalheFragment = new DetailsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
            Bundle args = new Bundle();
            args.putString("ID", id_produto);
            detalheFragment.setArguments(args);
            fragmentTransaction.replace(R.id.principal, detalheFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }




    }

    public void searchProducts() {


        /*final Dialog lovelyProgressDialog = new LovelyProgressDialog(getActivity())
                .setCancelable(false)
                .setTopTitle(R.string.afiltrar)
                .show();*/

        /*Window window = lovelyProgressDialog.getWindow();
        window.setLayout(900, 500);*/

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.afiltrar));
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.cancel();
                cursor = databaseHelper.filterByTipo(autoCompleteTextView.getText().toString());

                if (cursor.getCount() != 0) {
                    FilterFragment filterFragment = new FilterFragment();
                    FilterFragment.setCursor(cursor);
                    android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("PROCURA", String.valueOf(autoCompleteTextView.getText().toString()));
                    filterFragment.setArguments(args);
                    fragmentTransactionHome.replace(R.id.principal, filterFragment);
                    fragmentTransactionHome.addToBackStack(null);
                    fragmentTransactionHome.commit();
                    autoCompleteTextView.setText("");
                } else {
                    Toast.makeText(getContext(), R.string.errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2000);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        View view = menu.findItem(R.id.cart).getActionView();
        final NotificationBadge badge;
        final Cursor cursor_carrinho = databaseHelper.getProdutctsCartUser(LoginActivity.getUserLogin());
        badge = (NotificationBadge) view.findViewById(R.id.badge);
        badge.setText(String.valueOf(cursor_carrinho.getCount()));

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cursor_carrinho.getCount() == 0){
                    badge.setVisibility(View.INVISIBLE);
                }
                else{
                    badge.setVisibility(View.VISIBLE);
                    badge.setText(String.valueOf(cursor_carrinho.getCount()));
                }
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }
}
