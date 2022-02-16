package com.luxc.moneymanager.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.luxc.moneymanager.entity.FamilyBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FAMILY_BEAN".
*/
public class FamilyBeanDao extends AbstractDao<FamilyBean, Long> {

    public static final String TABLENAME = "FAMILY_BEAN";

    /**
     * Properties of entity FamilyBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property FamilyId = new Property(0, Long.class, "familyId", true, "_id");
        public final static Property FamilyName = new Property(1, String.class, "familyName", false, "FAMILY_NAME");
        public final static Property Creater = new Property(2, String.class, "creater", false, "CREATER");
        public final static Property CreateUserId = new Property(3, Long.class, "createUserId", false, "CREATE_USER_ID");
    }


    public FamilyBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FamilyBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FAMILY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: familyId
                "\"FAMILY_NAME\" TEXT," + // 1: familyName
                "\"CREATER\" TEXT," + // 2: creater
                "\"CREATE_USER_ID\" INTEGER);"); // 3: createUserId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FAMILY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FamilyBean entity) {
        stmt.clearBindings();
 
        Long familyId = entity.getFamilyId();
        if (familyId != null) {
            stmt.bindLong(1, familyId);
        }
 
        String familyName = entity.getFamilyName();
        if (familyName != null) {
            stmt.bindString(2, familyName);
        }
 
        String creater = entity.getCreater();
        if (creater != null) {
            stmt.bindString(3, creater);
        }
 
        Long createUserId = entity.getCreateUserId();
        if (createUserId != null) {
            stmt.bindLong(4, createUserId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FamilyBean entity) {
        stmt.clearBindings();
 
        Long familyId = entity.getFamilyId();
        if (familyId != null) {
            stmt.bindLong(1, familyId);
        }
 
        String familyName = entity.getFamilyName();
        if (familyName != null) {
            stmt.bindString(2, familyName);
        }
 
        String creater = entity.getCreater();
        if (creater != null) {
            stmt.bindString(3, creater);
        }
 
        Long createUserId = entity.getCreateUserId();
        if (createUserId != null) {
            stmt.bindLong(4, createUserId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public FamilyBean readEntity(Cursor cursor, int offset) {
        FamilyBean entity = new FamilyBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // familyId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // familyName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // creater
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // createUserId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FamilyBean entity, int offset) {
        entity.setFamilyId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFamilyName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreater(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateUserId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FamilyBean entity, long rowId) {
        entity.setFamilyId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FamilyBean entity) {
        if(entity != null) {
            return entity.getFamilyId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FamilyBean entity) {
        return entity.getFamilyId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
