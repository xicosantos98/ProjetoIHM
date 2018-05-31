package com.example.francisco.proj_kcs;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsBoughtFragment extends Fragment {


    DatabaseHelper db;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor, cursor_image, cursor_compra;
    ListView myList;
    TextView noProducts;
    String id_produto;
    int id_user, id_compra, id_produtoClick;


    public ProductsBoughtFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.purchases);
        RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.fragment_products_bought, container, false);
        setHasOptionsMenu(true);
        noProducts = view.findViewById(R.id.noProducts);

        myList = (ListView) view.findViewById(R.id.productsListViewComprados);
        myList.setDivider(null);
        myList.setDividerHeight(0);

        db = new DatabaseHelper(getActivity());
        id_user = db.getIdUserOnline(MainActivity.getUser());

        cursor = db.getProductsBought(id_user, "Compra");

        if(cursor.getCount() == 0){
            noProducts.setVisibility(View.VISIBLE);
        }

        criarList();

        return view;
    }


    public void criarList() {

        String[] columns = new String[]{
                DatabaseHelper.NOME_PRODUTO,
                DatabaseHelper.ID_PRODUTO,
                DatabaseHelper.PRECO,
                DatabaseHelper.QTD_COMPRA,
                DatabaseHelper.DATA_COMPRA,
        };

        int[] boundTo = new int[]{
                R.id.productNameBought,
                R.id.imageBought,
                R.id.precoBought,
                R.id.qtdNumberBought,
                R.id.dateBought
        };

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.pagina_produto_comprado, cursor, columns, boundTo, 0);
        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

                if (columnIndex == 0 && view instanceof ImageView) {

                    int id_imagem;

                    id_produto = cursor.getString(cursor.getColumnIndex(db.ID_PRODUTO));

                    cursor_image = db.getImage(id_produto);

                    cursor_image.moveToFirst();
                    id_imagem = cursor_image.getInt(cursor_image.getColumnIndex(db.CAMINHO));


                    int id = id_imagem;

                    ImageView image = (ImageView) view;
                    image.setImageResource(id);
                    return true;
                }


                if (columnIndex == 2 && view instanceof TextView) {

                    String valor = cursor.getString(cursor.getColumnIndex(db.PRECO));

                    TextView textView = (TextView) view;

                    textView.setText(valor.toString() + "€");

                    return true;

                }

                if (columnIndex == 5) {

                    String data = cursor.getString(cursor.getColumnIndex(db.DATA_COMPRA));

                    TextView textView = (TextView) view;

                    textView.setText("Data de compra: " + data);

                    return true;
                }


                return false;
            }

        });
        myList.setAdapter(simpleCursorAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        View view = menu.findItem(R.id.cart).getActionView();
        final NotificationBadge badge;
        final Cursor cursor_carrinho = db.getProdutctsCartUser(LoginActivity.getUserLogin());
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
