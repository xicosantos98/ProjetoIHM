package com.example.francisco.proj_kcs;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements AdapterView.OnItemClickListener {


    ListView myList;
    DatabaseHelper db;
    Cursor cursor, cursor_image, cursor_compra;
    SimpleCursorAdapter simpleCursorAdapter;
    ImageButton delete;
    int id_user, id_compra, id_produtoClick;
    String id_produto;
    TextView total, products;
    Button compra, reserva;
    float totalCarrinho;
    RelativeLayout view;
    private static int numCompras;
    NotificationBadge badge;
    Cursor cursor_carrinho;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (RelativeLayout) inflater.inflate(R.layout.fragment_cart, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.cart);
        setHasOptionsMenu(true);

        total = view.findViewById(R.id.total);
        products = view.findViewById(R.id.products);
        compra = view.findViewById(R.id.botaoCompra);
        reserva = view.findViewById(R.id.botaoReserva);
        reserva = view.findViewById(R.id.botaoReserva);

        myList = (ListView) view.findViewById(R.id.cartListView);
        myList.setOnItemClickListener(this);
        myList.setDivider(null);
        myList.setDividerHeight(0);


        db = new DatabaseHelper(getActivity());
        id_user = db.getIdUserOnline(MainActivity.getUser());
        cursor_carrinho = db.getProdutctsCartUser(LoginActivity.getUserLogin());

        cursor_compra = db.getCompraAberta(id_user);
        cursor_compra.moveToFirst();
        do {
            id_compra = cursor_compra.getInt(cursor_compra.getColumnIndex(db.ID_COMPRA));
        } while (cursor_compra.moveToNext());

        cursor = db.getProductsCart(id_compra);
        numCompras = cursor.getCount();


        if (cursor.getCount() == 0) {
            products.setText(getResources().getString(R.string.noProducts));
            compra.setVisibility(View.GONE);
            reserva.setVisibility(View.GONE);
            total.setVisibility(View.GONE);
        }

        totalCarrinho = db.getPrecoCompra(id_compra);

        criarList();

        compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateValueCompra(id_compra,totalCarrinho);
                db.updateTipoCompra(id_compra, "Compra");
                PagamentoFragment pagamentoFragment = new PagamentoFragment();
                android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putInt("ID", id_compra);
                pagamentoFragment.setArguments(args);
                fragmentTransactionHome.replace(R.id.principal, pagamentoFragment);
                fragmentTransactionHome.addToBackStack(null);
                fragmentTransactionHome.commit();
            }
        });

        reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.updateValueCompra(id_compra,0);
                db.updateTipoCompra(id_compra, "Reserva");
                ReservationFragment reservationFragment = new ReservationFragment();
                android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putInt("ID", id_compra);
                reservationFragment.setArguments(args);
                fragmentTransactionHome.replace(R.id.principal, reservationFragment);
                fragmentTransactionHome.addToBackStack(null);
                fragmentTransactionHome.commit();
            }
        });

        return view;
    }

    public void criarList() {


        total.setText("Total: " + totalCarrinho + " €");

        String[] columns = new String[]{
                DatabaseHelper.NOME_PRODUTO,
                DatabaseHelper.ID_PRODUTO,
                DatabaseHelper.PRECO,
                DatabaseHelper.TAMANHO
        };

        int[] boundTo = new int[]{
                R.id.productNameCart,
                R.id.imageCart,
                R.id.precoCart,
                R.id.qtdNumber
        };

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.pagina_carrinho, cursor, columns, boundTo, 0);
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

                if (columnIndex == 3 && view instanceof TextView) {

                    String valor = cursor.getString(cursor.getColumnIndex(db.PRECO));

                    TextView textView = (TextView) view;

                    textView.setText(valor.toString() + "€");

                    return true;

                }

                if (columnIndex == 5) {

                    int quantidade = db.getQuantityProduct(id_compra, Integer.parseInt(id_produto));

                    TextView textView = (TextView) view;

                    textView.setText(String.valueOf(quantidade));

                    return true;
                }


                return false;
            }

        });
        myList.setAdapter(simpleCursorAdapter);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        id_produtoClick = cursor.getInt(cursor.getColumnIndex("_id"));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.deleting));
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progressDialog.cancel();
                db.removeProductFromCart(id_produtoClick, id_compra);
                //badge.setText(String.valueOf(cursor_carrinho.getCount() + 1));
                CartFragment cartFragment = new CartFragment();
                android.support.v4.app.FragmentTransaction fragmentTransactionHome = getFragmentManager().beginTransaction();
                fragmentTransactionHome.replace(R.id.principal, cartFragment);
                fragmentTransactionHome.addToBackStack(null);
                fragmentTransactionHome.commit();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2000);

    }

    public static int getNumCompras(){
        return numCompras;
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
