package com.example.francisco.proj_kcs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.CameraUpdateFactory;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Francisco on 08/05/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sql;
    private DatabaseHelper db;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "kids.db";


    // TABELA DE UTILIZADORES
    public static final String TABELA_UTILIZADOR = "Utilizador";
    public static final String ID_UTILIZADOR = "_id";
    public static final String NOME_UTILIZADOR = "Nome_Utilizador";
    public static final String TELEMOVEL = "Tlm_Utilizador";
    public static final String MAIL = "Mail_Utilizador";
    public static final String USER = "User";
    public static final String PASSWORD = "Password";
    public static final String RUA = "Rua";
    public static final String CP = "codigo_postal";
    public static final String PAIS = "pais";
    public static final String DATABASE_CREATE_UTILIZADOR = "CREATE TABLE " + TABELA_UTILIZADOR + " ( " + ID_UTILIZADOR + " integer primary key autoincrement, " +
            NOME_UTILIZADOR + " text not null, " + TELEMOVEL + " text not null, " + MAIL + " text not null, " + USER + " text not null, " + PASSWORD + " text not null, "
            + RUA + " text not null, " + CP + " text not null, " + PAIS + " text not null);";

    // TABELA DE PRODUTOS
    public static final String TABELA_PRODUTO = "Produto";
    public static final String ID_PRODUTO = "_id";
    public static final String DESCRICAO = "descrição_produto";
    public static final String NOME_PRODUTO = "nome_produto";
    public static final String PRECO = "preço";
    public static final String COR = "cor";
    public static final String TAMANHO = "tamanho";
    public static final String TIPO_PRODUTO = "tipo_produto";
    public static final String GENERO = "genero";
    public static final String DATABASE_CREATE_PRODUTO = "CREATE TABLE " + TABELA_PRODUTO + " ( " + ID_PRODUTO + " integer primary key autoincrement, " +
            DESCRICAO + " text not null, " + NOME_PRODUTO + " text not null, " + PRECO + " real not null, " + COR + " text not null, "
            + TAMANHO + " text not null, " + TIPO_PRODUTO + " text not null, " + GENERO + " text not null);";

    // TABELA DE IMAGENS
    public static final String TABELA_IMAGEM = "Imagem";
    public static final String ID_IMAGEM = "_id";
    public static final String CAMINHO = "caminho_imagem";
    public static final String PRODUTO = "id_produto";
    public static final String DATABASE_CREATE_IMAGEM = "CREATE TABLE " + TABELA_IMAGEM + " ( " + ID_IMAGEM + " integer primary key autoincrement, " +
            CAMINHO + " integer not null, " + PRODUTO + " integer not null);";

    // TABELA WISHLIST
    public static final String TABELA_WISHLIST = "Wishlist";
    public static final String ID_WISHLIST = "_id";
    public static final String UTILIZADOR = "id_conta";
    public static final String PRODUCT = "id_produto";
    public static final String DATABASE_CREATE_WISHLIST = "CREATE TABLE " + TABELA_WISHLIST + " ( " + ID_WISHLIST + " integer primary key autoincrement, " +
            UTILIZADOR + " integer not null, " + PRODUCT + " integer not null);";


    // TABELA COMPRA
    public static final String TABELA_COMPRA = "Compra";
    public static final String ID_COMPRA = "_id";
    public static final String DATA_COMPRA = "data_compra";
    public static final String USER_COMPRA = "id_user";
    public static final String TIPO_COMPRA = "tipo";
    public static final String FINALIZADA = "finalizada";
    public static final String VALOR_COMPRA = "valor_compra";
    public static final String LOCAL_RESERVA = "local_reserva";
    public static final String DATABASE_CREATE_COMPRA = "CREATE TABLE " + TABELA_COMPRA + " ( " + ID_COMPRA + " integer primary key autoincrement, " +
            DATA_COMPRA + " integer not null, " + USER_COMPRA + " integer not null, " + TIPO_COMPRA + " text, " + FINALIZADA + " integer not null, " +
            VALOR_COMPRA + " real, " + LOCAL_RESERVA + " text);";


    // TABELA CARRINHO
    public static final String TABELA_CARRINHO = "Carrinho";
    public static final String ID_CARRINHO = "_id";
    public static final String COMPRA_CARRINHO = "compra_carrinho";
    public static final String PRODUTO_CARRINHO = "compra_produto";
    public static final String QTD_COMPRA = "qtd_compra";
    public static final String VALOR = "valor_carrinho";
    public static final String DATABASE_CREATE_CARRINHO = "CREATE TABLE " + TABELA_CARRINHO + " ( " + ID_CARRINHO + " integer primary key autoincrement,"
            + COMPRA_CARRINHO + " integer not null, " + PRODUTO_CARRINHO + " integer not null, " + QTD_COMPRA + " integer not null, " +
            VALOR + " real not null);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criar tabelas
        db.execSQL(DATABASE_CREATE_UTILIZADOR);
        db.execSQL(DATABASE_CREATE_PRODUTO);
        db.execSQL(DATABASE_CREATE_IMAGEM);
        db.execSQL(DATABASE_CREATE_WISHLIST);
        db.execSQL(DATABASE_CREATE_COMPRA);
        db.execSQL(DATABASE_CREATE_CARRINHO);

        //Inserir dados nas tabelas
        // -- Inserir utilizadores
        ContentValues user1 = new ContentValues();
        ContentValues user2 = new ContentValues();

        user1.put(NOME_UTILIZADOR, "Francisco Santos");
        user1.put(TELEMOVEL, "963184938");
        user1.put(MAIL, "franciscosantos@ipvc.pt");
        user1.put(USER, "xico");
        user1.put(PASSWORD, "1234");
        user1.put(RUA, "Rua do Recife Lote nº4 1ºEsquerdo");
        user1.put(CP, "Viana do Castelo, 4900-211");
        user1.put(PAIS, "Portugal");

        user2.put(NOME_UTILIZADOR, "Miguel Gramacho");
        user2.put(TELEMOVEL, "962786123");
        user2.put(MAIL, "miguelgramacho@ipvc.pt");
        user2.put(USER, "miguel");
        user2.put(PASSWORD, "1234");
        user2.put(RUA, "Rua do Meadela Lote nº4 1ºEsquerdo");
        user2.put(CP, "Viana do Castelo, 4900-111");
        user2.put(PAIS, "Portugal");

        db.insert(TABELA_UTILIZADOR, null, user1);
        db.insert(TABELA_UTILIZADOR, null, user2);


        // -- Inserir produtos
        ContentValues product1 = new ContentValues();
        ContentValues product2 = new ContentValues();
        ContentValues product3 = new ContentValues();
        ContentValues product4 = new ContentValues();
        ContentValues product5 = new ContentValues();
        ContentValues product6 = new ContentValues();
        ContentValues product7 = new ContentValues();
        ContentValues product8 = new ContentValues();


        product1.put(DESCRICAO, "Camisa de rapaz 8-12 anos com laço");
        product1.put(NOME_PRODUTO, "Camisa lisa para rapaz");
        product1.put(PRECO, 13.99);
        product1.put(COR, "azul");
        product1.put(TAMANHO, "S");
        product1.put(TIPO_PRODUTO, "Camisa");
        product1.put(GENERO, "Masculino");


        product2.put(DESCRICAO, "Casaco de rapariga 8-12 anos cor-de-rosa");
        product2.put(NOME_PRODUTO, "Casaco para criança");
        product2.put(PRECO, 15.99);
        product2.put(COR, "rosa");
        product2.put(TAMANHO, "S");
        product2.put(TIPO_PRODUTO, "Casaco");
        product2.put(GENERO, "Feminino");

        product3.put(DESCRICAO, "Jeans skinny desfiados e bordados");
        product3.put(NOME_PRODUTO, "Jeans azuis skinny");
        product3.put(PRECO, 15.74);
        product3.put(COR, "azul");
        product3.put(TAMANHO, "32");
        product3.put(TIPO_PRODUTO, "Calças");
        product3.put(GENERO, "Feminino");

        product4.put(DESCRICAO, "Camisola com capuz para rapaz com 8-12 anos de idade");
        product4.put(NOME_PRODUTO, "Camisola com capuz vermelha");
        product4.put(PRECO, 16.99);
        product4.put(COR, "vermelha");
        product4.put(TAMANHO, "M");
        product4.put(TIPO_PRODUTO, "Camisola");
        product4.put(GENERO, "Masculino");

        product5.put(DESCRICAO, "Casaco com forro para rapaz com 10-12 anos de idade");
        product5.put(NOME_PRODUTO, "Casaco azul");
        product5.put(PRECO, 29.99);
        product5.put(COR, "azul");
        product5.put(TAMANHO, "M");
        product5.put(TIPO_PRODUTO, "Casaco");
        product5.put(GENERO, "Masculino");

        product6.put(DESCRICAO, "Vestido branco comprido para rapariga com 8-10 anos de idade");
        product6.put(NOME_PRODUTO, "Vestido branco");
        product6.put(PRECO, 10.99);
        product6.put(COR, "branco");
        product6.put(TAMANHO, "S");
        product6.put(TIPO_PRODUTO, "Vestido");
        product6.put(GENERO, "Feminino");

        product7.put(DESCRICAO, "Camisola às riscas de variadas cores para rapariga com 10-12 anos de idade");
        product7.put(NOME_PRODUTO, "Camisola às riscas");
        product7.put(PRECO, 9.99);
        product7.put(COR, "azul");
        product7.put(TAMANHO, "L");
        product7.put(TIPO_PRODUTO, "Camisola");
        product7.put(GENERO, "Feminino");

        product8.put(DESCRICAO, "Camisola com capuz para rapaz com 12-14 anos de idade");
        product8.put(NOME_PRODUTO, "Camisola com capuz vermelho tinto");
        product8.put(PRECO, 16.99);
        product8.put(COR, "vermelha");
        product8.put(TAMANHO, "L");
        product8.put(TIPO_PRODUTO, "Camisola");
        product8.put(GENERO, "Masculino");


        db.insert(TABELA_PRODUTO, null, product1);
        db.insert(TABELA_PRODUTO, null, product2);
        db.insert(TABELA_PRODUTO, null, product3);
        db.insert(TABELA_PRODUTO, null, product4);
        db.insert(TABELA_PRODUTO, null, product5);
        db.insert(TABELA_PRODUTO, null, product6);
        db.insert(TABELA_PRODUTO, null, product7);
        db.insert(TABELA_PRODUTO, null, product8);


        //-- Inserir imagens
        ContentValues image1 = new ContentValues();
        ContentValues image1_1 = new ContentValues();
        ContentValues image1_2 = new ContentValues();

        ContentValues image2 = new ContentValues();
        ContentValues image2_1 = new ContentValues();
        ContentValues image2_2 = new ContentValues();

        ContentValues image3 = new ContentValues();
        ContentValues image3_1 = new ContentValues();

        ContentValues image4 = new ContentValues();
        ContentValues image4_1 = new ContentValues();
        ContentValues image4_2 = new ContentValues();

        ContentValues image5 = new ContentValues();
        ContentValues image5_1 = new ContentValues();

        ContentValues image6 = new ContentValues();
        ContentValues image6_1 = new ContentValues();
        ContentValues image6_2 = new ContentValues();

        ContentValues image7 = new ContentValues();
        ContentValues image7_1 = new ContentValues();
        ContentValues image7_2 = new ContentValues();

        ContentValues image8 = new ContentValues();
        ContentValues image8_1 = new ContentValues();

        image1.put(CAMINHO, R.drawable.fundo1);
        image1.put(PRODUTO, 1);

        image1_1.put(CAMINHO, R.drawable.fundo1_1);
        image1_1.put(PRODUTO, 1);

        image1_2.put(CAMINHO, R.drawable.fundo1_2);
        image1_2.put(PRODUTO, 1);


        image2.put(CAMINHO, R.drawable.fundo2);
        image2.put(PRODUTO, 2);

        image2_1.put(CAMINHO, R.drawable.fundo2_1);
        image2_1.put(PRODUTO, 2);

        image2_2.put(CAMINHO, R.drawable.fundo2_2);
        image2_2.put(PRODUTO, 2);


        image3.put(CAMINHO, R.drawable.fundo3);
        image3.put(PRODUTO, 3);

        image3_1.put(CAMINHO, R.drawable.fundo3_1);
        image3_1.put(PRODUTO, 3);

        image4.put(CAMINHO, R.drawable.fundo4);
        image4.put(PRODUTO, 4);

        image4_1.put(CAMINHO, R.drawable.fundo4_1);
        image4_1.put(PRODUTO, 4);

        image4_2.put(CAMINHO, R.drawable.fundo4_2);
        image4_2.put(PRODUTO, 4);

        image5.put(CAMINHO, R.drawable.fundo5);
        image5.put(PRODUTO, 5);

        image5_1.put(CAMINHO, R.drawable.fundo5_1);
        image5_1.put(PRODUTO, 5);

        image6.put(CAMINHO, R.drawable.fundo6);
        image6.put(PRODUTO, 6);

        image6_1.put(CAMINHO, R.drawable.fundo6_1);
        image6_1.put(PRODUTO, 6);

        image6_2.put(CAMINHO, R.drawable.fundo6_2);
        image6_2.put(PRODUTO, 6);

        image7.put(CAMINHO, R.drawable.fundo7);
        image7.put(PRODUTO, 7);

        image7_1.put(CAMINHO, R.drawable.fundo7_1);
        image7_1.put(PRODUTO, 7);

        image7_2.put(CAMINHO, R.drawable.fundo7_2);
        image7_2.put(PRODUTO, 7);

        image8.put(CAMINHO, R.drawable.fundo8);
        image8.put(PRODUTO, 8);

        image8_1.put(CAMINHO, R.drawable.fundo8_1);
        image8_1.put(PRODUTO, 8);

        db.insert(TABELA_IMAGEM, null, image1);
        db.insert(TABELA_IMAGEM, null, image1_1);
        db.insert(TABELA_IMAGEM, null, image1_2);
        db.insert(TABELA_IMAGEM, null, image2);
        db.insert(TABELA_IMAGEM, null, image2_1);
        db.insert(TABELA_IMAGEM, null, image2_2);
        db.insert(TABELA_IMAGEM, null, image3);
        db.insert(TABELA_IMAGEM, null, image3_1);
        db.insert(TABELA_IMAGEM, null, image4);
        db.insert(TABELA_IMAGEM, null, image4_1);
        db.insert(TABELA_IMAGEM, null, image4_2);
        db.insert(TABELA_IMAGEM, null, image5);
        db.insert(TABELA_IMAGEM, null, image5_1);
        db.insert(TABELA_IMAGEM, null, image6);
        db.insert(TABELA_IMAGEM, null, image6_1);
        db.insert(TABELA_IMAGEM, null, image6_2);
        db.insert(TABELA_IMAGEM, null, image7);
        db.insert(TABELA_IMAGEM, null, image7_1);
        db.insert(TABELA_IMAGEM, null, image7_2);
        db.insert(TABELA_IMAGEM, null, image8);
        db.insert(TABELA_IMAGEM, null, image8_1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_UTILIZADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_IMAGEM);
        this.onCreate(db);
    }


    //////////// -------------------   UTILIZADORES ---------------------  /////////////////

    public void createUser(String nome, String email, String tlm, String username, String passwd, String rua, String cp){
        sql = this.getReadableDatabase();
        String query = "INSERT INTO " + TABELA_UTILIZADOR + " (" + NOME_UTILIZADOR + "," + MAIL + "," + TELEMOVEL + "," + USER + "," + PASSWORD + "," + RUA + "," + CP + "," + PAIS + " )" +
                " VALUES (" + "'" + nome + "'" + "," + "'" + email + "'" + "," + "'" + tlm + "'" + "," + "'" + username + "'" + "," + "'" + passwd + "'" + "," + "'" + rua + "'" + "," +
                "'" + cp + "'" + "," + "'Portugal'" + ");";
        sql.execSQL(query);
    }

    public boolean verificaUser(String username){
        sql = this.getReadableDatabase();
        String query = "select " + USER + " from " + TABELA_UTILIZADOR;
        Cursor cursor = sql.rawQuery(query, null);
        String utilizador;


        if (cursor.moveToFirst()) {
            do {

                utilizador = cursor.getString(cursor.getColumnIndex(USER));

                if (utilizador.equals(username)) {
                    return true;
                }

            } while (cursor.moveToNext());
        }
        return false;
    }


    public Cursor getUsers() {
        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_UTILIZADOR + "," + NOME_UTILIZADOR + "," + TELEMOVEL + "," + MAIL + "," + USER + " FROM " + TABELA_UTILIZADOR;
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }


    public boolean verifiyLogin(String user, String password) {
        sql = this.getReadableDatabase();
        String query = "select " + USER + ", " + PASSWORD + " from " + TABELA_UTILIZADOR;
        Cursor cursor = sql.rawQuery(query, null);
        String utilizador, passwd;


        if (cursor.moveToFirst()) {
            do {

                utilizador = cursor.getString(cursor.getColumnIndex(USER));
                passwd = cursor.getString(cursor.getColumnIndex(PASSWORD));

                if (utilizador.equals(user) && passwd.equals(password)) {
                    return true;
                }

            } while (cursor.moveToNext());
        }
        return false;
    }

    public Cursor getUserByNome(String user){
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_UTILIZADOR + " WHERE " + USER + " LIKE '" + user + "'";
        Cursor cursor = sql.rawQuery(query,null);
        return cursor;
    }


    public int getIdUserOnline(String user) {
        sql = this.getReadableDatabase();
        String query = "select " + ID_UTILIZADOR + " from " + TABELA_UTILIZADOR + " WHERE " + USER + " LIKE '" + user + "'";
        Cursor cursor = sql.rawQuery(query, null);

        int id_user = 0;

        if (cursor.moveToFirst()) {
            do {
                id_user = cursor.getInt(cursor.getColumnIndex(ID_UTILIZADOR));
            } while (cursor.moveToNext());
        }

        return id_user;
    }


    ////////////////----------------------- PRODUTO -----------------------------/////////////////////

    public Cursor getProducts() {

        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_PRODUTO + "," + DESCRICAO + "," + NOME_PRODUTO + "," + PRECO
                + "," + COR + "," + TAMANHO + "," + TIPO_PRODUTO + "," + GENERO
                + " FROM " + TABELA_PRODUTO;
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getAllRoupaMasculina() {

        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_PRODUTO + "," + DESCRICAO + "," + NOME_PRODUTO + "," + PRECO
                + "," + COR + "," + TAMANHO + "," + TIPO_PRODUTO + "," + GENERO
                + " FROM " + TABELA_PRODUTO + " WHERE " + GENERO + " LIKE 'Masculino' ";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getAllRoupaFeminina() {

        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_PRODUTO + "," + DESCRICAO + "," + NOME_PRODUTO + "," + PRECO
                + "," + COR + "," + TAMANHO + "," + TIPO_PRODUTO + "," + GENERO
                + " FROM " + TABELA_PRODUTO + " WHERE " + GENERO + " LIKE 'Feminino' ";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getProduct(String id) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_PRODUTO + "," + DESCRICAO + "," + NOME_PRODUTO + "," + PRECO
                + "," + COR + "," + TAMANHO + "," + TIPO_PRODUTO + "," + GENERO
                + " FROM " + TABELA_PRODUTO + " WHERE " + ID_PRODUTO + " LIKE '" + id + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getImage(String id) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_IMAGEM + "," + CAMINHO + "," + ID_PRODUTO +
                " FROM " + TABELA_IMAGEM + " WHERE " + PRODUTO + " LIKE '" + id + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor filterByTipo(String tipo) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_PRODUTO + "," + DESCRICAO + "," + NOME_PRODUTO + "," + PRECO
                + "," + COR + "," + TAMANHO + "," + TIPO_PRODUTO + "," + GENERO
                + " FROM " + TABELA_PRODUTO + " WHERE " + TIPO_PRODUTO + " LIKE '" + tipo + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getAllTypeProduct() {
        sql = this.getReadableDatabase();
        String query = "SELECT " + TIPO_PRODUTO + " FROM " + TABELA_PRODUTO;
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    ////////////////----------------------- WISHLIST -----------------------------/////////////////////
    public void addWishlist(int id_user, int id_produto) {
        sql = this.getReadableDatabase();
        String query = "INSERT INTO " + TABELA_WISHLIST + " (" + UTILIZADOR + "," + PRODUCT + " )"
                + " VALUES ( " + id_user + "," + id_produto + ");";
        sql.execSQL(query);
    }

    public void removeWishlist(int id_user, int id_produto) {
        sql = this.getReadableDatabase();
        String query = "DELETE FROM " + TABELA_WISHLIST + " WHERE " + UTILIZADOR + " LIKE '" + id_user + "'"
                + " AND " + PRODUCT + " LIKE '" + id_produto + "'";
        sql.execSQL(query);
    }


    public Cursor getWishlist(int id_user) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + TABELA_PRODUTO + "." + ID_PRODUTO + "," + TABELA_PRODUTO + "." + DESCRICAO + "," + TABELA_PRODUTO + "." + NOME_PRODUTO + ","
                + TABELA_PRODUTO + "." + PRECO + "," + TABELA_PRODUTO + "." + COR + "," + TABELA_PRODUTO + "." + TAMANHO + "," + TABELA_PRODUTO + "." + TIPO_PRODUTO + ","
                + TABELA_PRODUTO + "." + GENERO + " FROM " + TABELA_PRODUTO + " INNER JOIN " + TABELA_WISHLIST +
                " ON " + TABELA_PRODUTO + "." + ID_PRODUTO + " = " + TABELA_WISHLIST + "." + PRODUCT
                + " WHERE " + TABELA_WISHLIST + "." + UTILIZADOR + " LIKE '" + id_user + "'";

        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }


    public boolean isInWishList(int id_user, int id_produto) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + ID_WISHLIST + "," + UTILIZADOR + "," + PRODUCT + " FROM " + TABELA_WISHLIST;
        Cursor cursor = sql.rawQuery(query, null);
        int user, produto;
        if (cursor.moveToFirst()) {
            do {

                user = cursor.getInt(cursor.getColumnIndex(UTILIZADOR));
                produto = cursor.getInt(cursor.getColumnIndex(PRODUCT));

                if (user == id_user && produto == id_produto) {
                    return true;
                }


            } while (cursor.moveToNext());
        }
        return false;
    }


    ////////////////----------------------- CARRINHO -----------------------------/////////////////////


    public Cursor getCompraAberta(int id_user) {
        sql = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABELA_COMPRA + " WHERE " + USER_COMPRA + " LIKE '" + id_user + "'"
                + " AND " + FINALIZADA + " LIKE " + 0;

        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }


    public void createCompra(int id_user) {

        String data = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        sql = this.getReadableDatabase();
        String query = "INSERT INTO " + TABELA_COMPRA + " (" + DATA_COMPRA + "," + USER_COMPRA + "," + TIPO_COMPRA + "," + FINALIZADA + "," + VALOR_COMPRA + " )" +
                " VALUES (" + "'" + data + "'" + "," + id_user + "," + null + "," + 0 + "," + null + ");";
        sql.execSQL(query);
    }

    public void createCarrinho(int id_compra, int id_produto, float valor, int qtd) {
        sql = this.getReadableDatabase();
        String query = "INSERT INTO " + TABELA_CARRINHO + " (" + COMPRA_CARRINHO + "," + PRODUTO_CARRINHO + "," + QTD_COMPRA + "," + VALOR + " )" +
                " VALUES ( " + id_compra + "," + id_produto + "," + qtd + "," + valor + ");";
        sql.execSQL(query);
    }

    public Cursor getCarrinho(int id_produto, int id_compra) {
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_CARRINHO + " WHERE " + COMPRA_CARRINHO + " LIKE '" + id_compra + "'"
                + " AND " + PRODUTO_CARRINHO + " LIKE '" + id_produto + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }


    public Cursor getCompra(int id_user) {
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_COMPRA + " WHERE " + USER_COMPRA + " LIKE '" + id_user + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public float getPrecoCompra(int id_compra) {
        float total = 0;
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_CARRINHO + " WHERE " + COMPRA_CARRINHO + " LIKE '" + id_compra + "'";
        Cursor cursor = sql.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                total += (cursor.getInt(cursor.getColumnIndex(QTD_COMPRA))) * (cursor.getFloat(cursor.getColumnIndex(VALOR)));
            } while (cursor.moveToNext());
        }

        return total;
    }


    public boolean isInCart(int id_compra, int id_produto) {
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_CARRINHO + " WHERE " + COMPRA_CARRINHO + " LIKE '" + id_compra + "'" +
                " AND " + PRODUTO_CARRINHO + " LIKE '" + id_produto + "'";
        Cursor cursor = sql.rawQuery(query, null);
        int compra, produto;

        if (cursor.moveToFirst()) {
            do {

                compra = cursor.getInt(cursor.getColumnIndex(COMPRA_CARRINHO));
                produto = cursor.getInt(cursor.getColumnIndex(PRODUTO_CARRINHO));

                if (compra == id_compra && id_produto == produto) {
                    return true;
                }

            } while (cursor.moveToNext());
        }
        return false;
    }


    public Cursor getProductsCart(int id_compra) {
        sql = this.getReadableDatabase();
        String query = "SELECT " + TABELA_PRODUTO + "." + ID_PRODUTO + "," + TABELA_PRODUTO + "." + DESCRICAO + "," + TABELA_PRODUTO + "." + NOME_PRODUTO + ","
                + TABELA_PRODUTO + "." + PRECO + "," + TABELA_PRODUTO + "." + COR + "," + TABELA_PRODUTO + "." + TAMANHO + "," + TABELA_PRODUTO + "." + TIPO_PRODUTO + ","
                + TABELA_PRODUTO + "." + GENERO + " FROM " + TABELA_PRODUTO + " INNER JOIN " + TABELA_CARRINHO +
                " ON " + TABELA_PRODUTO + "." + ID_PRODUTO + " = " + TABELA_CARRINHO + "." + PRODUTO_CARRINHO
                + " WHERE " + TABELA_CARRINHO + "." + COMPRA_CARRINHO + " LIKE '" + id_compra + "'";

        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public Cursor getProdutctsCartUser(String user){
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_CARRINHO + " INNER JOIN " + TABELA_COMPRA + " ON " + TABELA_COMPRA + "." + ID_COMPRA +
                " = " + TABELA_CARRINHO + "." + COMPRA_CARRINHO + " INNER JOIN " + TABELA_UTILIZADOR + " ON " + TABELA_COMPRA + "." + USER_COMPRA +
                " = " + TABELA_UTILIZADOR + "." + ID_UTILIZADOR + " WHERE " + TABELA_UTILIZADOR + "." + USER +  " LIKE '" + user + "'" +
                " AND " + TABELA_COMPRA + "." + FINALIZADA + " LIKE '" + 0 + "'";
        Cursor cursor = sql.rawQuery(query, null);
        return cursor;
    }

    public int getQuantityProduct(int id_compra, int id_produto) {
        int quantidade = 0;
        sql = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABELA_CARRINHO + " WHERE " + COMPRA_CARRINHO + " LIKE '" + id_compra + "'" +
                " AND " + PRODUTO_CARRINHO + " LIKE '" + id_produto + "'";

        Cursor cursor = sql.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                quantidade = cursor.getInt(cursor.getColumnIndex(db.QTD_COMPRA));


            } while (cursor.moveToNext());
        }

        return quantidade;
    }

    public Cursor getProductsBought(int id_user, String type){
        sql = this.getReadableDatabase();
        String query = "SELECT " + TABELA_PRODUTO + "." + ID_PRODUTO + "," + TABELA_PRODUTO + "." + NOME_PRODUTO + ","
                + TABELA_PRODUTO + "." + PRECO + "," + TABELA_CARRINHO + "." + COMPRA_CARRINHO + "," +
                TABELA_CARRINHO + "." + QTD_COMPRA + "," + TABELA_COMPRA + "." + DATA_COMPRA +
                " FROM " + TABELA_PRODUTO + " INNER JOIN " + TABELA_CARRINHO +
                " ON " + TABELA_PRODUTO + "." + ID_PRODUTO + " = " + TABELA_CARRINHO + "." + PRODUTO_CARRINHO +
                " INNER JOIN " + TABELA_COMPRA + " ON " + TABELA_CARRINHO + "." + COMPRA_CARRINHO + " = "
                + TABELA_COMPRA + "." + ID_COMPRA
                + " WHERE " + TABELA_COMPRA + "." + USER_COMPRA + " LIKE '" + id_user + "'" +
                " AND " + TABELA_COMPRA + "." + FINALIZADA +  " = " + 1 +
                " AND " + TABELA_COMPRA + "." + TIPO_COMPRA + " LIKE '" + type + "'" ;
        Cursor cursor = sql.rawQuery(query, null);
        return  cursor;
    }

    public Cursor getProductsReserved(int id_user, String type){
        sql = this.getReadableDatabase();
        String query = "SELECT " + TABELA_PRODUTO + "." + ID_PRODUTO + "," + TABELA_PRODUTO + "." + NOME_PRODUTO + ","
                + TABELA_CARRINHO + "." + COMPRA_CARRINHO + "," +
                TABELA_COMPRA + "." + LOCAL_RESERVA + "," + TABELA_COMPRA + "." + DATA_COMPRA +
                " FROM " + TABELA_PRODUTO + " INNER JOIN " + TABELA_CARRINHO +
                " ON " + TABELA_PRODUTO + "." + ID_PRODUTO + " = " + TABELA_CARRINHO + "." + PRODUTO_CARRINHO +
                " INNER JOIN " + TABELA_COMPRA + " ON " + TABELA_CARRINHO + "." + COMPRA_CARRINHO + " = "
                + TABELA_COMPRA + "." + ID_COMPRA
                + " WHERE " + TABELA_COMPRA + "." + USER_COMPRA + " LIKE '" + id_user + "'" +
                " AND " + TABELA_COMPRA + "." + FINALIZADA +  " = " + 1 +
                " AND " + TABELA_COMPRA + "." + TIPO_COMPRA + " LIKE '" + type + "'" ;
        Cursor cursor = sql.rawQuery(query, null);
        return  cursor;
    }


    public void removeProductFromCart(int id_produto, int id_compra) {
        sql = this.getReadableDatabase();
        String query = "DELETE FROM " + TABELA_CARRINHO + " WHERE " + COMPRA_CARRINHO + " LIKE '" + id_compra + "'" +
                " AND " + PRODUTO_CARRINHO + " LIKE '" + id_produto + "'";
        sql.execSQL(query);
    }

    public int updateValueCompra(int id_compra, float valor_compra) {
        try {
            sql = this.getReadableDatabase();
            String query = "UPDATE COMPRA SET " + VALOR_COMPRA + " = '" + valor_compra + "'" +
                    " WHERE " + ID_COMPRA + " = " + id_compra;
            sql.execSQL(query);

            return 1;
        } catch (SQLException e) {
            e.toString();
            return 0;
        }
    }

    public int updateEstadoCompra(int id_compra){
        try {
            sql = this.getReadableDatabase();
            String query = "UPDATE COMPRA SET " + FINALIZADA + " = " + 1 +
                    " WHERE " + ID_COMPRA + " = " + id_compra;
            sql.execSQL(query);

            return 1;
        } catch (SQLException e) {
            e.toString();
            return 0;
        }
    }

    public int updateTipoCompra(int id_compra, String tipo){
        try {
            sql = this.getReadableDatabase();
            String query = "UPDATE COMPRA SET " + TIPO_COMPRA + " = " + "'" + tipo + "'" +
                    " WHERE " + ID_COMPRA + " = " + id_compra;
            sql.execSQL(query);

            return 1;
        } catch (SQLException e) {
            e.toString();
            return 0;
        }
    }

    public int updateLocalizacao(int id_compra, String local){
        try {
            sql = this.getReadableDatabase();
            String query = "UPDATE COMPRA SET " + LOCAL_RESERVA + " = " + "'" + local + "'" +
                    " WHERE " + ID_COMPRA + " = " + id_compra;
            sql.execSQL(query);

            return 1;
        } catch (SQLException e) {
            e.toString();
            return 0;
        }
    }

    public int updateDataReserva(int id_compra, String dataReserva){
        try {
            sql = this.getReadableDatabase();
            String query = "UPDATE COMPRA SET " + DATA_COMPRA + " = " + "'" + dataReserva + "'" +
                    " WHERE " + ID_COMPRA + " = " + id_compra;
            sql.execSQL(query);

            return 1;
        } catch (SQLException e) {
            e.toString();
            return 0;
        }
    }


    public float getValueCompra(int id_compra){
        float valor = 0;
        sql = this.getReadableDatabase();
        String query = "SELECT " + VALOR_COMPRA + " FROM " + TABELA_COMPRA + " WHERE " + ID_COMPRA + " LIKE '" + id_compra + "'";

        Cursor cursor = sql.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{

                valor = cursor.getFloat(cursor.getColumnIndex(VALOR_COMPRA));

            }while (cursor.moveToNext());
        }

        return valor;
    }




}
