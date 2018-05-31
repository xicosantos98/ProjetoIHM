package com.example.francisco.proj_kcs;


import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoupaFemininaFragment extends Fragment implements AdapterView.OnItemClickListener {

    DatabaseHelper db;
    SimpleCursorAdapter simpleCursorAdapter;
    ArrayAdapter<String> tipoAdapter;
    ArrayList<String> tipoList;
    ListView myList;
    Cursor cursor, cursor_image, cursor_tipos;
    String id_produto;
    Dialog d;
    Spinner tipos;


    public RoupaFemininaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_roupafeminina, container, false);
        setHasOptionsMenu(true);
        /*((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.shoes);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.clothes);
        NavigationView navigationView = (NavigationView) ((MainActivity)getActivity()).findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_products);*/


        myList = (ListView) relativeLayout.findViewById(R.id.productsListView);

        myList.setDivider(null);
        myList.setDividerHeight(0);
        myList.setOnItemClickListener(this);

        tipoList = new ArrayList<>();


        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(800);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(300);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        myList.setLayoutAnimation(controller);


        db = new DatabaseHelper(getActivity());

        criarList();
        this.setRetainInstance(true);
        return relativeLayout;
    }


    public void criarList() {

        cursor = db.getAllRoupaFeminina();


        String[] columns = new String[]{
                DatabaseHelper.NOME_PRODUTO,
                DatabaseHelper.ID_PRODUTO,
                DatabaseHelper.PRECO,
        };

        int[] boundTo = new int[]{
                R.id.nomeProduto,
                R.id.imageProduto,
                R.id.preco
        };

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.pagina_produto, cursor, columns, boundTo, 0);
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
                    //image.setImageResource(id);
                    image.setImageResource(id);
                    return true;
                }

                if (columnIndex == 3 && view instanceof TextView) {

                    String valor = cursor.getString(cursor.getColumnIndex(db.PRECO));

                    TextView textView = (TextView) view;

                    System.out.println("VALOR: " + valor);
                    System.out.println(valor.toString() + "€");

                    textView.setText(valor.toString() + "€");

                    System.out.println("AQUI: " + textView.getText());
                    return true;

                }
                return false;
            }


        });
        myList.setAdapter(simpleCursorAdapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id_produto = cursor.getInt(cursor.getColumnIndex("_id"));

        DetailsFragment detalheFragment = new DetailsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putString("ID", String.valueOf(id_produto));
        detalheFragment.setArguments(args);
        fragmentTransaction.replace(R.id.principal, detalheFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void filtros() {

        tipos = d.findViewById(R.id.spinner_tipo);

        tipoList.add(getResources().getString(R.string.type));

        cursor_tipos = db.getAllTypeProduct();

        cursor_tipos.moveToFirst();

        do {
            tipoList.add(cursor_tipos.getString(cursor_tipos.getColumnIndex(db.TIPO_PRODUTO)));
        } while (cursor_tipos.moveToNext());

        //tipoAdapter = new ArrayAdapter<String>(getContext(), R.layout.spiner_tipoproduto, tipoList);


        tipoAdapter = new ArrayAdapter<String>(getContext(), R.layout.spiner_tipoproduto, tipoList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {

                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        tipos.setAdapter(tipoAdapter);

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
