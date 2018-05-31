package com.example.francisco.proj_kcs;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    TextView nome, preco, desc;
    ImageView image, like;
    Button addCart;
    String id, corProduto;
    DatabaseHelper db;
    public static ViewPager viewPager;
    Cursor cursor, cursor_image, cursor_wishlist, cursor_compra, cursor_carrinho;
    ArrayList<String> produto;
    public ArrayList<Integer> imagens;
    int idImage, count, id_user, id_produto, id_compra;
    boolean inWishList;
    ExpandableTextView expandableTextView;
    View cor1, cor2, cor3;
    TextView tamanho_xs, tamanho_s, tamanho_m, tamanho_l;
    ViewPagerDetails viewPagerDetails;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    float precoCarrinho;
    int qtd;
    NotificationBadge badge;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.details);
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_details, container, false);
        expandableTextView = (ExpandableTextView) view.findViewById(R.id.textDesc);
        setHasOptionsMenu(true);

        id = getArguments().getString("ID");

        viewPager = (ViewPager) view.findViewById(R.id.viewPagerDetails);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        nome = (TextView) view.findViewById(R.id.DetailsNome);
        preco = (TextView) view.findViewById(R.id.DetailsPreco);
        //image = (ImageView) view.findViewById(R.id.imageDetails);
        desc = (TextView) view.findViewById(R.id.expandable_text);
        cor1 = (View) view.findViewById(R.id.azul);
        cor1.setOnClickListener(this);
        cor2 = (View) view.findViewById(R.id.rosa);
        cor2.setOnClickListener(this);
        cor3 = (View) view.findViewById(R.id.branco);
        cor3.setOnClickListener(this);
        like = (ImageView) view.findViewById(R.id.likeButton);
        like.setOnClickListener(this);
        tamanho_xs = (TextView) view.findViewById(R.id.xs);
        tamanho_xs.setOnClickListener(this);
        tamanho_s = (TextView) view.findViewById(R.id.s);
        tamanho_s.setOnClickListener(this);
        tamanho_m = (TextView) view.findViewById(R.id.m);
        tamanho_m.setOnClickListener(this);
        tamanho_l = (TextView) view.findViewById(R.id.l);
        tamanho_l.setOnClickListener(this);
        addCart = (Button) view.findViewById(R.id.addToCart);
        addCart.setOnClickListener(this);

        db = new DatabaseHelper(getActivity());


        cursor = db.getProduct(id);
        cursor_image = db.getImage(id);

        produto = new ArrayList<String>();
        cursor.moveToFirst();
        do {
            produto.add(cursor.getString(cursor.getColumnIndex(db.DESCRICAO)));
            produto.add(cursor.getString(cursor.getColumnIndex(db.NOME_PRODUTO)));
            produto.add(cursor.getString(cursor.getColumnIndex(db.PRECO)));
            produto.add(cursor.getString(cursor.getColumnIndex(db.COR)));
        }
        while (cursor.moveToNext());


        nome.setText(produto.get(1));
        preco.setText(produto.get(2) + " €");

        precoCarrinho = Float.parseFloat(produto.get(2));
        //image.setImageResource(idImage);


        imagens = new ArrayList<Integer>();
        cursor_image.moveToFirst();
        do {
            imagens.add(cursor_image.getInt(cursor_image.getColumnIndex(db.CAMINHO)));


            //idImage = cursor_image.getInt(cursor_image.getColumnIndex(db.CAMINHO));
        } while (cursor_image.moveToNext());
        //image.setImageResource(imagens.get(0));

        viewPagerDetails = new ViewPagerDetails(getContext(), imagens);

        viewPager.setAdapter(viewPagerDetails);
        dotsChange();

        expandableTextView.setText(produto.get(0) );

        expandableTextView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {

            }
        });

        corProduto = produto.get(3);

        if (corProduto.equals("azul")) {
            cor1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.azul));
        } else if (corProduto.equals("rosa")) {
            cor2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rosa));
        }

        id_user = db.getIdUserOnline(MainActivity.getUser());
        id_produto = Integer.parseInt(id);
        inWishList = db.isInWishList(id_user, id_produto);


        cursor_compra = db.getCompraAberta(id_user);

        cursor_compra.moveToFirst();
        do {
            id_compra = cursor_compra.getInt(cursor_compra.getColumnIndex(db.ID_COMPRA));
        } while (cursor_compra.moveToNext());


        if (inWishList) {
            like.setBackground(getResources().getDrawable(R.drawable.ic_likeenabled));
        } else {
            like.setBackground(getResources().getDrawable(R.drawable.ic_likedisabled));
        }

        cursor_carrinho = db.getProdutctsCartUser(LoginActivity.getUserLogin());

        return view;
    }

    @Override
    public void onClick(View view) {


        if (view == cor1) {
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.azul));
            cor2.setBackgroundColor(getResources().getColor(R.color.corRosa));
            cor3.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (view == cor2) {
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rosa));
            cor1.setBackgroundColor(getResources().getColor(R.color.corAzul));
            cor3.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (view == cor3) {
            view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.branco));
            cor1.setBackgroundColor(getResources().getColor(R.color.corAzul));
            cor2.setBackgroundColor(getResources().getColor(R.color.corRosa));
        } else if (view == like) {

            inWishList = db.isInWishList(id_user, id_produto);

            if (inWishList) {
                db.removeWishlist(id_user, id_produto);
                like.setBackground(getResources().getDrawable(R.drawable.ic_likedisabled));
                Toast.makeText(getContext(), R.string.rmFavorite, Toast.LENGTH_SHORT).show();

            } else {
                db.addWishlist(id_user, id_produto);
                like.setBackground(getResources().getDrawable(R.drawable.ic_likeenabled));
                Toast.makeText(getContext(), R.string.addFavorite, Toast.LENGTH_SHORT).show();
            }
        } else if (view == addCart) {


            if (db.isInCart(id_compra, id_produto)) {
                Toast.makeText(getContext(), getResources().getString(R.string.allreadyInCart), Toast.LENGTH_SHORT).show();
            } else {
                showDialog();



            }


        } else {

            if (view != tamanho_xs) {
                tamanho_xs.setBackground(getResources().getDrawable(R.drawable.size_form));
                ((TextView) tamanho_xs).setTextColor(getResources().getColor(R.color.TextHome));
            }

            if (view != tamanho_s) {
                tamanho_s.setBackground(getResources().getDrawable(R.drawable.size_form));
                ((TextView) tamanho_s).setTextColor(getResources().getColor(R.color.TextHome));

            }
            if (view != tamanho_m) {
                tamanho_m.setBackground(getResources().getDrawable(R.drawable.size_form));
                ((TextView) tamanho_m).setTextColor(getResources().getColor(R.color.TextHome));
            }
            if (view != tamanho_l) {
                tamanho_l.setBackground(getResources().getDrawable(R.drawable.size_form));
                ((TextView) tamanho_l).setTextColor(getResources().getColor(R.color.TextHome));
            }


            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));

        }

    }

    public void dotsChange() {

        dotscount = viewPagerDetails.getCount();
        dots = new ImageView[dotscount];


        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(10, 0, 0, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void showDialog() {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(10);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtd = np.getValue();
                db.createCarrinho(id_compra, id_produto, precoCarrinho, qtd);
                Toast.makeText(getContext(), getResources().getString(R.string.addedInCart), Toast.LENGTH_SHORT).show();
                badge.setVisibility(View.VISIBLE);
                badge.setText(String.valueOf(cursor_carrinho.getCount() +1));
                /*Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);*/
                //badge.setText(String.valueOf(cursor_carrinho.getCount() + 1));
                d.dismiss();
            }
        });
        d.show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        View view = menu.findItem(R.id.cart).getActionView();
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
