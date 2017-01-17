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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class FilmeContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.jonass.filmespopulares";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FILME = "filme";

    /* Inner class that defines the table contents of the location table */
    public static final class FilmeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FILME;

        // Table name
        public static final String TABLE_NAME = "filme";

        public static final String COLUMN_FILME_ID = "filme_id";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_IMAGEM_PATH = "imagem_path";
        public static final String COLUMN_CAPA_PATH = "capa_path";
        public static final String COLUMN_SINOPSE = "sinopse";
        public static final String COLUMN_AVALIACAO = "avaliacao";
        public static final String COLUMN_LANCAMENTO = "lancamento";

        public static Uri buildFilmeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
