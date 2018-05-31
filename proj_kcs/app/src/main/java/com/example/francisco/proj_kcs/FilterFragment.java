package com.example.francisco.proj_kcs;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment implements AdapterView.OnItemClickListener{

    DatabaseHelper db;
    ListView myList;
    public static Cursor cursor;
    Cursor cursor_image;
    SimpleCursorAdapter simpleCursorAdapter;
    String id_produto, resultado = "";
    TextView titulo;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static void setCursor(Cursor cursor){
        FilterFragment.cursor = cursor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_filter, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.results);

        titulo = (TextView) relativeLayout.findViewById(R.id.searchProducts);
        resultado = getArguments().getString("PROCURA");

        titulo.setText(titulo.getText() + " '" + resultado + "'");


        myList = (ListView) relativeLayout.findViewById(R.id.searchListView);
        myList.setOnItemClickListener(this);
        myList.setDivider(null);
        myList.setDividerHeight(0);

        db = new DatabaseHelper(getActivity());
        criarList();

        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(), 0);

        return relativeLayout;
    }


    public void criarList() {


        String[] columns = new String[]{
                DatabaseHelper.NOME_PRODUTO,
                DatabaseHelper.ID_PRODUTO,
                DatabaseHelper.PRECO,
        };

        int[] boundTo = new int[]{
                R.id.nomeProduto,
                R.id.imageProduto,
                R.id.preco,
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

                    textView.setText(valor.toString() + "€");

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
}
