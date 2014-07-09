SQLiteUtilities
===============

Some utilities to use with SQLite

Using SimplerCursor:

SQLiteDatabase db;
SimplerCursor cur = new SimplerCursor(db.query(...));

while(cur.moveToNext()){
  String name = cur.getString(COL_NAME); //COL_NAME is a String constant containing the column name
}
cur.close();

Using CreateBuilder:

CreateBuilder bldr = new CreateBuilder(TABLE_TEST) //Table name as parameter
		                    .addPkColumn(COL_ID, "INT").addColumn(COL_FOREIGN, "INT") //Adding couple columns
		                    //Adding foreign key constraint (column, foreign table, foreign column, flags)
		                    .addForeignKey(COL_FOREIGN, TABLE_OTHERTABLE, KEY_ID, CreateBuilder.FK_DEL_CASCADE);
String sql = bldr.create();
