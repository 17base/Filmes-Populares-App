/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jonass.filmespopulares.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FilmesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "filme.db";

    public FilmesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FILMES_TABLE = "CREATE TABLE " + FilmeContract.FilmeEntry.TABLE_NAME + " (" +
                FilmeContract.FilmeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FilmeContract.FilmeEntry.COLUMN_FILME_ID + " TEXT NOT NULL, " +
                FilmeContract.FilmeEntry.COLUMN_TITULO + " TEXT, " +
                FilmeContract.FilmeEntry.COLUMN_IMAGEM_PATH + " TEXT, " +
                FilmeContract.FilmeEntry.COLUMN_CAPA_PATH + " TEXT," +
                FilmeContract.FilmeEntry.COLUMN_SINOPSE + " TEXT, " +
                FilmeContract.FilmeEntry.COLUMN_AVALIACAO + " TEXT, " +
                FilmeContract.FilmeEntry.COLUMN_LANCAMENTO + " TEXT" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_FILMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FilmeContract.FilmeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
