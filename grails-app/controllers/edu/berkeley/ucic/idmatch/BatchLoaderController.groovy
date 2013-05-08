package edu.berkeley.ucic.idmatch

/**
 * load from csv
 * used to seed the backend
 */
class BatchLoaderController {

    def load() {
          File file = new File(params.file);
          def db  = ['db.url' : 'jdbc:hsqldb:mem:testDB', 
                     'db.user' : 'sa', 
                     'db.password' : '', 
                     'db.driver' : 'org.hsqldb.jdbc.JDBCDriver']

          def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
          
          file.eachLine() { line ->
                            def fields = line.split(',');
                            println fields;
                            return "";
                            sql.execute( "insert into User(attr1, attr2, attr3, attr4, attr5, attr6,"+
                                                                "attr7, attr8, attr9, attr10, attr11, attr12,"+
                                                                "attr13, attr14, attr15)"+ 
                                          "values (?, ?, ?, ...)", fields);
                           }

         sql.close();
       }
}
