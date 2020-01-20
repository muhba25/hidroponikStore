package com.andyadr.apps.hidroponikstore.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.andyadr.apps.hidroponikstore.model.Produk;
@Database(entities = {Produk.class}, version = 1)
public abstract class DatabaseProduk extends RoomDatabase {
	public abstract ProdukDao getProdukDAO();
}
