package com.andyadr.apps.hidroponikstore.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.List;


@Dao
public interface ProdukDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	void insert(Produk... produks);

	@Query("SELECT * FROM produk")
	Cursor selectAll();

	@Query("SELECT * FROM produk where uid = :uid")
	Cursor selectById(long uid);

	@Query("SELECT * FROM produk")
	List<Produk> getAllFavProduks();

	@Query("DELETE FROM produk WHERE uid = :uid")
	void deleteByUid(int uid);

	@Query("SELECT COUNT(kode_produk) FROM produk WHERE nama_produk = :kodepro")
	int getProdukByNama(String kodepro);

}
