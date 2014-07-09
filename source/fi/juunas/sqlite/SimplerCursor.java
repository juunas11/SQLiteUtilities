package fi.juunas.sqlite;

import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/**
 * A cursor decorator offering simpler access
 * to column values
 * @author Joonas Westlin
 *
 */
public class SimplerCursor implements Cursor {
	
	private final Cursor mCursor; //Wrapped cursor
	private final Map<String, Integer> mCache; //Cache for column indexes
	
	public SimplerCursor(Cursor cursor){
		mCursor = cursor;
		mCache = new HashMap<String, Integer>();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getCount()
	 */
	@Override
	public int getCount() {
		return mCursor.getCount();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getPosition()
	 */
	@Override
	public int getPosition() {
		return mCursor.getPosition();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#move(int)
	 */
	@Override
	public boolean move(int offset) {
		return mCursor.move(offset);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#moveToPosition(int)
	 */
	@Override
	public boolean moveToPosition(int position) {
		return mCursor.moveToPosition(position);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#moveToFirst()
	 */
	@Override
	public boolean moveToFirst() {
		return mCursor.moveToFirst();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#moveToLast()
	 */
	@Override
	public boolean moveToLast() {
		return mCursor.moveToLast();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#moveToNext()
	 */
	@Override
	public boolean moveToNext() {
		return mCursor.moveToNext();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#moveToPrevious()
	 */
	@Override
	public boolean moveToPrevious() {
		return mCursor.moveToPrevious();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isFirst()
	 */
	@Override
	public boolean isFirst() {
		return mCursor.isFirst();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isLast()
	 */
	@Override
	public boolean isLast() {
		return mCursor.isLast();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isBeforeFirst()
	 */
	@Override
	public boolean isBeforeFirst() {
		return mCursor.isAfterLast();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isAfterLast()
	 */
	@Override
	public boolean isAfterLast() {
		return mCursor.isAfterLast();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getColumnIndex(java.lang.String)
	 */
	@Override
	public int getColumnIndex(String columnName) {
		Integer columnIndex;
		if((columnIndex = mCache.get(columnName)) == null){
			columnIndex = mCursor.getColumnIndexOrThrow(columnName);
			mCache.put(columnName, columnIndex);
		}
		return columnIndex;
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getColumnIndexOrThrow(java.lang.String)
	 */
	@Override
	public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
		Integer columnIndex;
		if((columnIndex = mCache.get(columnName)) == null){ //Assign whatever is in cache, and if null
			columnIndex = mCursor.getColumnIndexOrThrow(columnName); //Fetch it
			mCache.put(columnName, columnIndex); //Store in cache
		}
		return columnIndex;
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		return mCursor.getColumnName(columnIndex);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getColumnNames()
	 */
	@Override
	public String[] getColumnNames() {
		return mCursor.getColumnNames();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return mCursor.getColumnCount();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getBlob(int)
	 */
	@Override
	public byte[] getBlob(int columnIndex) {
		return mCursor.getBlob(columnIndex);
	}
	
	public byte[] getBlob(String columnName){
		return getBlob(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getString(int)
	 */
	@Override
	public String getString(int columnIndex) {
		return mCursor.getString(columnIndex);
	}
	
	public String getString(String columnName){
		return getString(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#copyStringToBuffer(int, android.database.CharArrayBuffer)
	 */
	@Override
	public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
		mCursor.copyStringToBuffer(columnIndex, buffer);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getShort(int)
	 */
	@Override
	public short getShort(int columnIndex) {
		return mCursor.getShort(columnIndex);
	}
	
	public short getShort(String columnName){
		return getShort(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getInt(int)
	 */
	@Override
	public int getInt(int columnIndex) {
		return mCursor.getInt(columnIndex);
	}
	
	public int getInt(String columnName){
		return getInt(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getLong(int)
	 */
	@Override
	public long getLong(int columnIndex) {
		return mCursor.getLong(columnIndex);
	}
	
	public long getLong(String columnName){
		return getLong(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getFloat(int)
	 */
	@Override
	public float getFloat(int columnIndex) {
		return mCursor.getFloat(columnIndex);
	}
	
	public float getFloat(String columnName){
		return getFloat(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getDouble(int)
	 */
	@Override
	public double getDouble(int columnIndex) {
		return mCursor.getDouble(columnIndex);
	}
	
	public double getDouble(String columnName){
		return getDouble(getColumnIndexOrThrow(columnName));
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getType(int)
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public int getType(int columnIndex) {
		return mCursor.getType(columnIndex);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isNull(int)
	 */
	@Override
	public boolean isNull(int columnIndex) {
		return mCursor.isNull(columnIndex);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#deactivate()
	 */
	@Override
	@Deprecated
	public void deactivate() {
		mCursor.deactivate();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#requery()
	 */
	@Override
	@Deprecated
	public boolean requery() {
		return mCursor.requery();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#close()
	 */
	@Override
	public void close() {
		mCursor.close();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return mCursor.isClosed();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#registerContentObserver(android.database.ContentObserver)
	 */
	@Override
	public void registerContentObserver(ContentObserver observer) {
		mCursor.registerContentObserver(observer);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#unregisterContentObserver(android.database.ContentObserver)
	 */
	@Override
	public void unregisterContentObserver(ContentObserver observer) {
		mCursor.unregisterContentObserver(observer);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#registerDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mCursor.registerDataSetObserver(observer);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#unregisterDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mCursor.unregisterDataSetObserver(observer);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#setNotificationUri(android.content.ContentResolver, android.net.Uri)
	 */
	@Override
	public void setNotificationUri(ContentResolver cr, Uri uri) {
		mCursor.setNotificationUri(cr, uri);
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getNotificationUri()
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public Uri getNotificationUri() {
		return mCursor.getNotificationUri();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getWantsAllOnMoveCalls()
	 */
	@Override
	public boolean getWantsAllOnMoveCalls() {
		return mCursor.getWantsAllOnMoveCalls();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#getExtras()
	 */
	@Override
	public Bundle getExtras() {
		return mCursor.getExtras();
	}

	/* (non-Javadoc)
	 * @see android.database.Cursor#respond(android.os.Bundle)
	 */
	@Override
	public Bundle respond(Bundle extras) {
		return mCursor.respond(extras);
	}
	
	public void clearCache(){
		mCache.clear();
	}
}
