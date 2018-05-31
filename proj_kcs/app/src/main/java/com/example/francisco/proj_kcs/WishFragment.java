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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nex3z.notificationbadge.NotificationBadge;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishFragment extends Fragment implements AdapterView.OnItemClickListener {

    SimpleCursorAdapter simpleCursorAdapter;
    ListView myList;
    DatabaseHelper db;
    Cursor cursor, cursor_image;
    String id_produto;
    int id_user;

    public WishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_wish, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.wish);
        setHasOptionsMenu(true);
        myList = (ListView) relativeLayout.findViewById(R.id.wishListView);
        myList.setOnItemClickListener(this);
        myList.setDivider(null);
        myList.setDividerHeight(0);

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
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
        id_user = db.getIdUserOnline(MainActivity.getUser());

        criarList();


        return relativeLayout;

    }

    public void criarList() {

        cursor = db.getWishlist(id_user);


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
                if (cursor_carrinho.getCount() == 0) {
                    badge.setVisibility(View.INVISIBLE);
                } else {
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
